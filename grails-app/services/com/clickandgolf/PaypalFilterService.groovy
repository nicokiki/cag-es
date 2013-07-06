package com.clickandgolf

import static org.quartz.JobBuilder.*
import static org.quartz.SimpleScheduleBuilder.*
import static org.quartz.TriggerBuilder.*
import static org.quartz.DateBuilder.*

import com.clickandgolf.GreenFee
import com.clickandgolf.GreenFeeReservado
import com.clickandgolf.seguridad.User
import com.clickandgolf.seguridad.FacebookUser
import com.clickandgolf.ClockService

import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.FacebookLink
import org.springframework.social.facebook.api.impl.FacebookTemplate

import grails.plugin.asyncmail.AsynchronousMailService;
import grails.plugin.quartz2.InvokeMethodJob
import org.quartz.*
import org.springframework.transaction.annotation.Transactional;
import org.grails.paypal.Payment;
import org.joda.time.DateTime


class PaypalFilterService {

	def clockService
	def quartzScheduler
	def grailsApplication
	AsynchronousMailService asynchronousMailService
	def messageSource
	def utilService
	
//	void testEmail(String transactionId, def locale) {
//		def payment = Payment.findByTransactionId(transactionId)
//		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
//		def greenFee = GreenFee.get(greenFeeReservado.greenFee.id)
//		def usuario = greenFeeReservado.usuario
//		// El usuario lo necesitamos por el email
//
//		def model = [greenFee: greenFee, greenFeeReservado: greenFeeReservado, payment: payment, usuario: usuario, locale: locale]
//		
//		asynchronousMailService.sendAsynchronousMail {
//			to "test@testssss.com"
//			from "clickandgolf-do-not-reply@clickandgolf.es"
//			subject "Test con HTML"
//			
//			body( view:"/reserva/_confirmacion",
//				model:model)
//		}
//		
//		
//
//	}
	
	@Transactional
	void doPostBuyFilter(Integer golfistas, Long greenFeeId, Long buyerId, Payment payment, String langSaved, String licencia1, String licencia2, String licencia3, String licencia4) {
		def greenFee = GreenFee.get(greenFeeId)
		def usuario = User.get(buyerId)
		
		BigDecimal feeToPayUpFront = greenFee.getFeeToPayUpFront(golfistas)
		BigDecimal feeToPayAtCourse = greenFee.getFeeToPayAtCourse(golfistas)
		
		greenFee.reset(golfistas)
		if (!greenFee.save()) {
			log.error("No se pudo salvar el greenFee - Solucionar URGENTE")
			greenFee.errors.each {
				log.error("Error: " + it)
			}
		}
		
		// Tengo q buscar un posible gemelo!!!
		GreenFee gemelo = utilService.gemelo(greenFee)
		if (gemelo && gemelo.estado != GreenFee.INACTIVO) { // Quizas lo inactive anteriormente porque estaba reservando 2 de 4 de 18 hoyos (la primera vez ya lo hubiera inactivado)
			log.info("Hay un gemelo => lo inactivo directamente ...")
			gemelo.estado = GreenFee.INACTIVO
			if (!gemelo.save()) {
				log.error("No se pudo salvar el greenFee gemelo - Solucionar URGENTE")
				gemelo.errors.each {
					log.error("Error: " + it)
				}
			}
			else {
				log.info("El Green Fee gemelo paso a INACTIVO ...")
			}
		}
		
		def greenFeeReservado = new GreenFeeReservado(usuario: usuario, fechaReserva: clockService.ahora(),
				feePagado: feeToPayUpFront, descuento: greenFee.descuento,
				precioPorPagar: feeToPayAtCourse, campo: greenFee.campo, diaHora: greenFee.diaHora,
				golfistas: golfistas, greenFee: greenFee,
				payment:payment, langSaved: langSaved,
				licencia1: licencia1, licencia2: licencia2, licencia3: licencia3, licencia4: licencia4,
				greenFeeGemelo: gemelo)
		if (!greenFeeReservado.save()) {
			log.error("No se pudo salvar el GreenFeeReservado - Solucionar URGENTE")
			greenFeeReservado.errors.each {
				log.error("Error: " + it)
			}
		}
		// Se obtiene uno nuevo porque es de 'prototype' scope
		def paymentService = grailsApplication.mainContext.paymentService
		
		// Se guardo el GFR => ahora se lanza un job en 5 minutos que decida si tirar todo xa atras o no ...
		def jobDataMap = new JobDataMap([targetObject:paymentService, targetMethod:'analyseAbandoned',arguments:[payment.id, greenFeeId]]);

		def enMinutos = grailsApplication.config.abandoned.enMinutos
		
		DateTime enXMinutos = clockService.enMinutos(enMinutos);
		String horaMinutoSegundosEnCinco = clockService.formatted(enXMinutos, ClockService.hourMinuteSecondFormatter);
		String idAux = "analyseAbandoned-" + payment.id + "-" + greenFeeId + "-" + horaMinutoSegundosEnCinco;
		
		JobDetail jobAbandoned = newJob(InvokeMethodJob.class)
			.withIdentity(idAux)
			.usingJobData(jobDataMap)
			.build()
			
		log.info("Se creo el job '" + idAux + "' para ejecutarse a las: " + enXMinutos)
			
		idAux = "trigger-" + idAux;
		Trigger triggerAbandoned = newTrigger().withIdentity(idAux)
				.startAt(enXMinutos.toDate())
				.build()
		
		quartzScheduler.scheduleJob(jobAbandoned, triggerAbandoned)
		log.info("Se creo un green fee reservado en after-buy-paypal para " + golfistas + " golfistas y se hizo un schedule de un job para anlizarlo en 5 minutos ...")
	}
	
	@Transactional
	void doPostSuccessFilter(Payment payment, Long greenFeeId) {
		log.info("Post-Success-Filter-Service -- Por pasar un Green Fee Reservado a COMPLETE post-success ...")
		
		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
		def estadoAnterior = greenFeeReservado.estado
		if (estadoAnterior == GreenFeeReservado.CONFIRMED) {
			log.info("El post-complete-notify se ejecuto antes que el post-success => No hacemos nada ...")
		}
		else if (estadoAnterior == GreenFeeReservado.COMPLETE) {
			log.info("El estado ya es COMPLETE. No hacemos nada para evitar duplicados ...")
		}
		else {
			greenFeeReservado.estado = GreenFeeReservado.COMPLETE
			String ahoraStr = clockService.formatted(clockService.ahora(), ClockService.fullFormatter)
			String obs = greenFeeReservado.observaciones ?: ""
			obs += "\n"
			obs += (ahoraStr + " -> Pasando de '" + estadoAnterior + "' a '" + GreenFeeReservado.COMPLETE + "'")
			greenFeeReservado.observaciones = obs
			greenFeeReservado.save()
			log.info("Se actualizo el GreenFeeReservado('" + greenFeeReservado.id + "', estado -> COMPLETE) ...")
		}
	}

	@Transactional
	void doPostCancelFilter(Payment payment) {
		log.info("Post-Cancel-Filter-Service -- Por pasar un Green Fee Reservado a CANCELLED post-cancel...")
		
		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
		def estadoAnterior = greenFeeReservado.estado
		greenFeeReservado.estado = GreenFeeReservado.CANCELLED
		String ahoraStr = clockService.formatted(clockService.ahora(), ClockService.fullFormatter)
		String obs = greenFeeReservado.observaciones ?: ""
		obs += "\n"
		obs += (ahoraStr + " -> Pasando de '" + estadoAnterior + "' a '" + GreenFeeReservado.CANCELLED + "'")
		greenFeeReservado.observaciones = obs
		greenFeeReservado.save()
		
		// Se reactivara el Green Fee
		def greenFee = GreenFee.get(greenFeeReservado.greenFee.id)
		greenFee.reset(greenFeeReservado)
		greenFee.save()
		
		// El gemelo solo se re-activa si el estado del green fee reseteado vuelve a ACTIVO! ya que si queda en PARCIAL tiene q seguir inactivado ...
		def gemelo = greenFeeReservado.greenFeeGemelo
		if (gemelo && greenFee.estado == GreenFee.ACTIVO) {
			log.info("Se reactivara el gemelo ...")
			gemelo.estado = GreenFee.ACTIVO
			
			if (!gemelo.save()) {
				log.error("No se pudo salvar el greenFee gemelo - Solucionar URGENTE")
				gemelo.errors.each {
					log.error("Error: " + it)
				}
			}
		}
		
		log.info("Se hicieron cambios en GreenFee(id='" + greenFee.id + "', estado -> Re-activado o re-parcializado) y GreenFeeReservado(id='" + greenFeeReservado?.id + "', estado -> '" +  GreenFeeReservado.CANCELLED + "') ...")
	}
	
	
	@Transactional
	void doCompleteNotifyPaypal(Payment payment, Long greenFeeId) {
		log.info("Post-Complete-NotifyPaypal-Filter-Service -- Por pasar un Green Fee Reservado a CONFIRMED y notificar al campo ...")
		
		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
		def estadoAnterior = greenFeeReservado.estado
		
		if (estadoAnterior == GreenFeeReservado.CONFIRMED && greenFeeReservado.golfistaConfirmedNotificado && greenFeeReservado.campoConfirmedNotificado) {
			log.info("El GreenFeeReservado ya esta en CONFIRMED y se ha notificado al campo y golfistas => no hago nada para evitar duplicaciones ...")
		}
		else {
			greenFeeReservado.estado = GreenFeeReservado.CONFIRMED
			String ahoraStr = clockService.formatted(clockService.ahora(), ClockService.fullFormatter)
			String obs = greenFeeReservado.observaciones ?: ""
			obs += "\n"
			obs += (ahoraStr + " -> Pasando de '" + estadoAnterior + "' a '" + GreenFeeReservado.CONFIRMED + "'")
			greenFeeReservado.observaciones = obs
			
			def locale = greenFeeReservado.getLangSavedAsLocale()
			
			def emailFrom = utilService.emailFrom()
			def greenFee = GreenFee.get(greenFeeReservado.greenFee.id)
			def usuario = greenFeeReservado.usuario
			// El usuario lo necesitamos por el email y otras cosas
			def model = [greenFee: greenFee, greenFeeReservado: greenFeeReservado, payment: payment, usuario: usuario, locale: locale]
			
			asynchronousMailService.sendAsynchronousMail {
				to "${greenFeeReservado.campo.email}"
				from emailFrom
				subject messageSource.getMessage("notificacion.email.confirmacion.campo.subject", null, locale)
				body( view:"/reserva/_confirmacionCampo", model:model)
			}
			greenFeeReservado.campoConfirmedNotificado = true
			greenFeeReservado.fechaCampoConfirmed = clockService.ahora()
			
			asynchronousMailService.sendAsynchronousMail {
				to "${greenFeeReservado.usuario.email}"
				from emailFrom
				subject messageSource.getMessage("notificacion.email.confirmacion.golfista.subject", null, locale)
				body( view:"/reserva/_confirmacion", model:model)
			}

			greenFeeReservado.golfistaConfirmedNotificado = true
			greenFeeReservado.fechaGolfistaConfirmed = clockService.ahora()
			
			greenFeeReservado.save()

			log.info("Se mando el email de notificacion al campo y al golfista ...")
			log.info("Se actualizo el GreenFeeReservado('" + greenFeeReservado.id + "', estado -> 'CONFIRMED') ...")
			
			doSocial(greenFeeReservado)
			
		}
	}
	
	def doSocial(GreenFeeReservado greenFeeReservado) {
		log.debug("Por intentar ver si el usuario es de facebook cosa de poder postear que es lo que hizo ...")

		User usuario = greenFeeReservado.usuario
		if (usuario.isFacebookUser()) {
			def locale = greenFeeReservado.getLangSavedAsLocale()
			
			FacebookUser fbUser = FacebookUser.findByUser(usuario)
			Facebook facebook = new FacebookTemplate(fbUser.accessToken)
			
			def linkClickAndGolf = grailsApplication.config.grails.serverURL

			FacebookLink link = new FacebookLink(linkClickAndGolf,
				"Click & Golf",
				 messageSource.getMessage("facebook.clickandgolf.caption", null, locale),
				 messageSource.getMessage("facebook.clickandgolf.descripcion", null, locale));
			try {
				Object[] argsLocale = [	usuario.nombre ?: "", 
										greenFeeReservado.campo.nombre, 
										greenFeeReservado.greenFee.getDia() ];
				def msg = messageSource.getMessage("facebook.post.message", argsLocale, locale)
				facebook.feedOperations().postLink(msg, link)
				log.info("Se posteo en el muro de facebook del usuario ...")
			}
			catch (Exception e) {
				log.info("Puede fallar porque no tenemos el permiso en fb: ${e}")
			}
		}
		else {
			log.debug("El usuario '${usuario.email}' NO es de facebook entonces no podemos postear en su nombre ...")
		}
		log.debug("Termino de ejecutarse el doSocial ...")
	}

//	@Transactional
//	void doReversedNotifyPaypal(Payment payment, Long greenFeeId, Locale locale, String reasonCode) {
//		log.info("Post-Reversed-NotifyPaypal-Filter-Service -- Por pasar un Green Fee Reservado a REVERSED y notificar al golfista y re-activar el green fee ...")
//		
//		/* 
//		 * Si el GFR habia notificado al campo => se le envia un email de Cancelacion de Reserva al campo
//		 * Si el GFR habia notificado al golfista => se le envia un email de Cancelacion de Reserva al golfista
//		 * 
//		 * 	Es importante decir q el Payment no se cambia en PaypalController xa no agregar mucha logica al overridden y evitar problemas de upgrade. Aqui se pasa a INVALID
//		 *  siempre y cuando no se haya ya recibido la notificacion del reversed (xa evitar duplicados)
//		 */
//				
//		boolean eraPaymentComplete = false
//		if (payment.status == Payment.COMPLETE) {
//			eraPaymentComplete = true
//		}
//		payment.status = Payment.INVALID
//		payment.save()
//				
//		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
//		def estadoAnterior = greenFeeReservado.estado
//		greenFeeReservado.estado = GreenFeeReservado.REVERSED
//		String ahoraStr = clockService.formatted(clockService.ahora(), ClockService.fullFormatter)
//		String obs = greenFeeReservado.observaciones ?: ""
//		obs += "\n"
//		obs += (ahoraStr + " -> Pasando de '" + estadoAnterior + "' a '" + GreenFeeReservado.REVERSED + "'. Reason_code:'" + reasonCode + "'")
//		greenFeeReservado.observaciones = obs
//		greenFeeReservado.save()
//		
//		// Se reactivara el Green Fee
//		def greenFee = GreenFee.get(greenFeeId)
//		greenFee.reset(greenFeeReservado)
//		greenFee.save()
//
//		if (eraPaymentComplete) {
//			// TODO Hacer esto bien
//			asynchronousMailService.sendAsynchronousMail {
//				to "golfista@golfista.com"
//				from "clickandgolf-do-not-reply@clickandgolf.es"
//				subject "Notificacion de REVERSED al golfista - Reserva de Green Fee - Reason Code:" + reasonCode
//				html '<b>Hello</b> <br> golfista notificacion';
//			}
//			
//			log.info("Se mando el email al golfista notificando de q fue REVERSED ....")
//		}
//
//		log.info(	"Se hicieron cambios en GreenFee(id='" + greenFeeId + "', estado -> Re-activado o re-parcializado) y GreenFeeReservado(id='" + greenFeeReservado?.id + "', estado -> '" +  GreenFeeReservado.REVERSED + "'), " +
//			 		" Payment(id='" + payment.id + "', status -> '" + Payment.INVALID + "') ...")
//
//	}
	
	@Transactional
	void doFailedNotifyPaypal(Payment payment) {
		log.info("Post-Failed-NotifyPaypal-Filter-Service -- Por pasar un Green Fee Reservado a FAILED y notificar al golfista y re-activar el green fee ...")
		/* El Payment ya estar en FAILED (hecho en PaypalController)
		 * Se pasa el GFR a FAILED y se re-activa el GF y se notifica al golfista del failed x si acaso
		 */
		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
		def estadoAnterior = greenFeeReservado.estado
		if (estadoAnterior == GreenFeeReservado.FAILED && greenFeeReservado.golfistaFailedNotificado) {
			log.info("El GreenFeeReservado ya esta en FAILED y se ha notificado al golfistas => no hago nada para evitar duplicaciones ...")
		}
		else {
			greenFeeReservado.estado = GreenFeeReservado.FAILED
			String ahoraStr = clockService.formatted(clockService.ahora(), ClockService.fullFormatter)
			String obs = greenFeeReservado.observaciones ?: ""
			obs += "\n"
			obs += (ahoraStr + " -> Pasando de '" + estadoAnterior + "' a '" + GreenFeeReservado.FAILED + "'")
			greenFeeReservado.observaciones = obs
		
			// Se reactivara el Green Fee
			def greenFee = GreenFee.get(greenFeeReservado.greenFee.id)
			greenFee.reset(greenFeeReservado)
			greenFee.save()
			
			// El gemelo solo se re-activa si el estado del green fee reseteado vuelve a ACTIVO! ya que si queda en PARCIAL tiene q seguir inactivado ...
			def gemelo = greenFeeReservado.greenFeeGemelo
			if (gemelo && greenFee.estado == GreenFee.ACTIVO) {
				log.info("Se reactivara el gemelo ...")
				gemelo.estado = GreenFee.ACTIVO
				
				if (!gemelo.save()) {
					log.error("No se pudo salvar el greenFee gemelo - Solucionar URGENTE")
					gemelo.errors.each {
						log.error("Error: " + it)
					}
				}
			}
	
			def locale = greenFeeReservado.getLangSavedAsLocale()
			
			def emailFrom = utilService.emailFrom()
			def usuario = greenFeeReservado.usuario
			// El usuario lo necesitamos por el email y otras cosas
			def model = [greenFee: greenFee, greenFeeReservado: greenFeeReservado, payment: payment, usuario: usuario, locale: locale]
			
			asynchronousMailService.sendAsynchronousMail {
				to "${greenFeeReservado.usuario.email}"
				from emailFrom
				subject messageSource.getMessage("notificacion.email.fail.golfista.subject", null, locale)
				body( view:"/reserva/_falla", model:model)
			}
			greenFeeReservado.golfistaFailedNotificado = true
			greenFeeReservado.fechaGolfistaFailed = clockService.ahora()

			greenFeeReservado.save()
			
			log.info(	"Se hicieron cambios en GreenFee(id='" + greenFee.id + "', estado -> Re-activado o re-parcializado) y GreenFeeReservado(id='" + greenFeeReservado?.id + "', estado -> '" +  GreenFeeReservado.FAILED + "'). " +
						"Se mando un email al golfista para notificarle el failed ..." )
		}
	}
	
	@Transactional
	void doRefundedNotifyPaypal(Payment payment) {
		log.info("Post-Refunded-NotifyPaypal-Filter-Service -- Por pasar un Green Fee Reservado a REFUNDED y notificar al golfista y al campo y re-activar el green fee ...")
		
		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
		def estadoAnterior = greenFeeReservado.estado
		if (estadoAnterior == GreenFeeReservado.REFUNDED && greenFeeReservado.golfistaRefundedNotificado && greenFeeReservado.campoRefundedNotificado) {
			log.info("El GreenFeeReservado ya esta en REFUNDED y se ha notificado al campo y golfistas => no hago nada para evitar duplicaciones ...")
		}
		else {
			/* El Payment lo pasamos a INVALID
			 */
			payment.status = Payment.INVALID
			payment.save()
			
			greenFeeReservado.estado = GreenFeeReservado.REFUNDED
			String ahoraStr = clockService.formatted(clockService.ahora(), ClockService.fullFormatter)
			String obs = greenFeeReservado.observaciones ?: ""
			obs += "\n"
			obs += (ahoraStr + " -> Pasando de '" + estadoAnterior + "' a '" + GreenFeeReservado.REFUNDED + "'")
			greenFeeReservado.observaciones = obs
			
			// Se reactivara el Green Fee
			def greenFee = GreenFee.get(greenFeeReservado.greenFee.id)
			greenFee.reset(greenFeeReservado)
			greenFee.save()
			
			// El gemelo solo se re-activa si el estado del green fee reseteado vuelve a ACTIVO! ya que si queda en PARCIAL tiene q seguir inactivado ...
			def gemelo = greenFeeReservado.greenFeeGemelo
			if (gemelo && greenFee.estado == GreenFee.ACTIVO) {
				log.info("Se reactivara el gemelo ...")
				gemelo.estado = GreenFee.ACTIVO
				
				if (!gemelo.save()) {
					log.error("No se pudo salvar el greenFee gemelo - Solucionar URGENTE")
					gemelo.errors.each {
						log.error("Error: " + it)
					}
				}
			}
	
			def locale = greenFeeReservado.getLangSavedAsLocale()
			
			def emailFrom = utilService.emailFrom()
			def usuario = greenFeeReservado.usuario
			// El usuario lo necesitamos por el email y otras cosas
			def model = [greenFee: greenFee, greenFeeReservado: greenFeeReservado, payment: payment, usuario: usuario, locale: locale]
			
			asynchronousMailService.sendAsynchronousMail {
				to "${greenFeeReservado.campo.email}"
				from emailFrom
				subject messageSource.getMessage("notificacion.email.cancelacion.reserva.campo.subject", null, locale)
				body( view:"/reserva/_cancelacionCampo", model:model)
			}
			greenFeeReservado.golfistaRefundedNotificado = true
			greenFeeReservado.fechaGolfistaRefunded = clockService.ahora()
			
			asynchronousMailService.sendAsynchronousMail {
				to "${greenFeeReservado.usuario.email}"
				from emailFrom
				subject messageSource.getMessage("notificacion.email.cancelacion.reserva.golfista.subject", null, locale)
				body( view:"/reserva/_cancelacion", model:model)
			}
			greenFeeReservado.campoRefundedNotificado = true
			greenFeeReservado.fechaCampoRefunded = clockService.ahora()

			greenFeeReservado.save()
			
			log.info("Se mando el email al golfista notificando de q fue REFUNDED y al campo diciendo q se CANCELO la reserva ....")
			log.info(	"Se hicieron cambios en GreenFee(id='" + greenFee.id + "', estado -> Re-activado o re-parcializado) y GreenFeeReservado(id='" + greenFeeReservado?.id + "', estado -> '" +  GreenFeeReservado.REFUNDED + "'), " +
				" Payment(id='" + payment.id + "', status -> '" + Payment.INVALID + "') ...")
		}
		
	}

}
