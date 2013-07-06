package com.clickandgolf

import static org.quartz.JobBuilder.*
import static org.quartz.SimpleScheduleBuilder.*
import static org.quartz.TriggerBuilder.*
import static org.quartz.DateBuilder.*

import com.clickandgolf.GreenFee
import com.clickandgolf.GreenFeeReservado
import com.clickandgolf.seguridad.User

import grails.plugin.quartz2.InvokeMethodJob
import org.quartz.*
import org.grails.paypal.Payment;
import org.joda.time.DateTime

import org.springframework.context.i18n.LocaleContextHolder as LCH


class PaypalFilters {
	
	def grailsApplication
	def paypalFilterService
	def messageSource
	def utilService
	
    def filters = {
		def buyFilter = afterBuyFilter(controller:'paypal', action:'buy') {
			before = {
				// Lo pongo para que Paypal siempre anteponga ese base-url a la hora de responder al server de Click&Golf (http://grails.org/plugin/paypal)
				params.baseUrl = grailsApplication.config.paypal.base.url
				
				/* Analizo si todavia estan disponibles esos green fees xq alguien puede q los haya reservado mientras estaba
				 * mirando la pantalla de reservar.
				 * En ese caso lo devuelvo a la busqueda y dejo un mensaje de error explicando que paso
				 * (esto es bien robusto)
				 */
				Integer golfistas = params.int('golfistas')
				def greenFeeId = params.long('itemNumber') // id del green fee
				
				// Aca tengo q ver si un gemelo se reservo, x lo cual no lo dejaria reservarlo!!!
				def greenFee = GreenFee.get(greenFeeId)
				GreenFee gemelo = utilService.gemelo(greenFee)
				if ((greenFee.inEstadoDisponibles() && greenFee.disponibles >= golfistas)
					&&
					(!gemelo || (gemelo.estado == GreenFee.ACTIVO) )) {
					// Los gemelos no pueden tener otros estados q no sean activos.
					log.debug("El green fee tiene cantidad suficiente de disponibles (${greenFee.disponibles}) para los ${golfistas} golfistas. Puede seguir adelante ...")
				}
				else {
					log.info("El green fee NO tiene cantidad suficiente de disponibles (${greenFee.disponibles}) o esta reservado (${greenFee.estado}) para los ${golfistas} golfistas. " +
						"O hay un gemelo q se acaba de reservar " + (gemelo ?: "<no hay gemelo>") + " No se puede seguir ...")

					Locale locale = LCH.getLocale();
					Object[] argsLocale = [	greenFee.getDia(), greenFee.getHoraMinuto() ];
					flash.message = messageSource.getMessage("greenfee.book.warning.stale", argsLocale, locale) 

					redirect(mapping:"greenfeeCampo", params:[idCampo:"${greenFee.campo.id}", nombreCampo:"${greenFee.campo.hyphenatedNombre}", dia:"${greenFee.diaMes}", mes:"${greenFee.mes}", anio:"${greenFee.anio}"])
					return false // Esto es necesario para evitar un redirect de un redirect ...
				}
				
			}
			after = {
				log.info("Por crear un Green Fee Reservado y lanzar un job para analizar si en X minutos sigue PENDING ...")
				
				def golfistas = params.int('golfistas')
				def greenFeeId = params.long('itemNumber') // id del green fee
				def buyerId = params.long('buyerId') // id del usuario a la hora de armar el boton
				def payment = request.payment
				def langSaved = params.langSaved
				
				def licencia1 = params.licencia1 ? (params.licencia1 == "undefined" ? null : params.licencia1) : null
				def licencia2 = params.licencia2 ? (params.licencia2 == "undefined" ? null : params.licencia2) : null
				def licencia3 = params.licencia3 ? (params.licencia3 == "undefined" ? null : params.licencia3) : null
				def licencia4 = params.licencia4 ? (params.licencia4 == "undefined" ? null : params.licencia4) : null
				
				paypalFilterService.doPostBuyFilter(golfistas, greenFeeId, buyerId, payment, langSaved ?: "es", licencia1, licencia2, licencia3, licencia4)
								
				log.info("Se llamo a paypalFilterService.doPostBuyFilter")
			}
		}
		
		def afterSuccessFilter = afterSuccessFilter(controller:'paypal', action:'success') {
			after = {
				def greenFeeId = params.long('itemNumber') // id del green fee
				def payment = request.payment
				
				log.info("Por llamar a paypalFilterService.doPostSuccessFilter ...")
				paypalFilterService.doPostSuccessFilter(payment, greenFeeId);
				log.info("Se llamo a paypalFilterService.doPostSuccessFilter ...")
			}
		}

		afterCancelFilter(controller:'paypal', action:'cancel') {
			after = {
				def payment = request.payment

				log.info("Por llamar a paypalFilterService.doPostCancelFilter ...")
				paypalFilterService.doPostCancelFilter(payment);
				log.info("Se llamo a paypalFilterService.doPostCancelFilter ...")
			}
		}
		
		afterNotifyPaypalFilter(controller:'paypal', action:'notifyPaypal') {
			after = {
				log.info("Por empezar a ejecutar el after-notifyPaypal-filter. Params: ${params} ...")
				
				def greenFeeId = params.long('itemNumber') // id del green fee
				Payment payment = request.payment
				// Tipos de status posibles: https://www.x.com/developers/paypal/documentation-tools/ipn/integration-guide/IPNandPDTVariables
				def status = params.payment_status
				String reasonCode = params.reason_code
				
				if (status == "Completed") {
					log.info("Por llamar a paypalFilterService.doCompleteNotifyPaypal ...")
					paypalFilterService.doCompleteNotifyPaypal(payment, greenFeeId)
					log.info("Se llamo a paypalFilterService.doCompleteNotifyPaypal ...")
				}
//				else if (status == "Reversed") {
//				El REVERSED no lo hago xq no lo deberia usar xa nada ....
//					log.info("Por llamar a paypalFilterService.doReversedNotifyPaypal ...")
//					paypalFilterService.doReversedNotifyPaypal(payment, greenFeeId, locale, reasonCode)
//					log.info("Se llamo a paypalFilterService.doReversedNotifyPaypal ...")
//				}
				else if (status == "Failed") {
					log.info("Por llamar a paypalFilterService.doFailedNotifyPaypal ...")
					paypalFilterService.doFailedNotifyPaypal(payment)
					log.info("Se llamo a paypalFilterService.doFailedNotifyPaypal ...")
				}
				else if (status == "Refunded" || (status == "Pending" && reasonCode == "refund")) {
					// Si es en la misma moneda => el refunded es inmediato. Si es GBP/EUR entonces llega como Pending y refund y lo trato todo bien 
					log.info("Por llamar a paypalFilterService.doRefundedNotifyPaypal ...")
					paypalFilterService.doRefundedNotifyPaypal(payment)
					log.info("Se llamo a paypalFilterService.doRefundedNotifyPaypal ...")
				}
				else {
					log.info("No se hace nada con el status:'" + status + "' ...")
				}
			}
		}

		
    }
}
