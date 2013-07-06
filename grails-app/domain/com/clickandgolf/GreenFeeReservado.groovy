package com.clickandgolf

import java.math.BigDecimal;

import org.grails.paypal.Payment;
import org.joda.time.DateTime

import com.clickandgolf.seguridad.User

/**
 * Se crea en un POST-BUY-filter de Paypal.<br>
 * 	Cuando lo creo, se queda en estado PENDING y se cambia el estado del green fee a PARCIAL o RESERVADO xa bloquear futuras compras del mismo
 * 	Cuando se completa no pasa nada con el green fee asociado (se completa pero puede q no este 100% confirmado)
 * 	Cuando se cancela, se vuelve a poner el green fee en PARCIAL O ACTIVO en base a lo q hay. Se le SUMAN los golfistas q tenia el green fee reservado ...
 * 	Cuando se abandona, falla o reverse actua igual q cuando se cancela
 * 
 * @author gonznic
 */
class GreenFeeReservado {
	
	static final PENDING = "PENDING" // 
	static final COMPLETE = "COMPLETE" // Cuando se recibe el success de Paypal
	static final CANCELLED = "CANCELLED" // Cuando se recibe el cancel de Paypal
	static final CONFIRMED = "CONFIRMED" // Cuando se recibe el Completed en el notifyPal via IPN
	static final FAILED = "FAILED" // Cuando se recibe el fail de Paypal
	static final ABANDONED = "ABANDONED" // Abandonado post 5' desde su creacion (cuando se crea en el post-buy filter de paypal, largamos un job q a los 5' checkea si sigue en PENDING y lo pasa a ABANDONED
	static final REFUNDED = "REFUNDED" // Se le devolvio el dinero
	static final REVERSED = "REVERSED" // Se hizo un reversed en Paypal
	
	static final ESTADOS_RESERVADOS = [CONFIRMED]
	static final ESTADOS_RESERVADOS_Y_CONFIRMADOS = [COMPLETE, CONFIRMED] 
	
	String estado = PENDING
	
	// Usuario que hizo el booking
	User usuario
	DateTime fechaReserva
	
	// feePagado es el total. Si el fee eran 3 € x golfista y habia 2 golfistas => feePagado = 6 €
	BigDecimal feePagado
	// Es el descuento original. No tiene en cuenta la cantidad de golfistas
	Integer descuento
	// Es el precio por pagar total. Igual a lo de feePagado
	BigDecimal precioPorPagar
	
	// Esto es x seguridad interna xa evitar doble mensajeria
	Boolean golfistaConfirmedNotificado = false
	DateTime fechaGolfistaConfirmed = null
//	Boolean golfistaReversedNotificado = false
//	DateTime fechaGolfistaReversed = null
	Boolean golfistaFailedNotificado = false
	DateTime fechaGolfistaFailed = null
	Boolean golfistaRefundedNotificado = false
	DateTime fechaGolfistaRefunded = null
	Boolean campoConfirmedNotificado = false
	DateTime fechaCampoConfirmed = null
	Boolean campoRefundedNotificado = false
	DateTime fechaCampoRefunded = null
	
	// Campo
	Campo campo
	DateTime diaHora
	
	// Cantidad de golfistas
	Integer golfistas
	
	// Referencia al green fee
	GreenFee greenFee
	// Es un green fee xa el mismo horario pero 9 hoyos si el otro es 18 o al reves. Al hacer cambios en un green fee, se bloquea completamente el otro ...
	GreenFee greenFeeGemelo
	
	Payment payment
	
	// Aca se puede guardar info en caso de cancelacion por ejemplo
	String observaciones
	
	// Lo uso para en el futuro notificar y mas cosas teniendo en cuenta este lenguaje
	String langSaved
	
	String licencia1
	String licencia2
	String licencia3
	String licencia4
	
	
    static constraints = {
		estado inList: [GreenFeeReservado.PENDING, GreenFeeReservado.COMPLETE, GreenFeeReservado.CANCELLED, GreenFeeReservado.CONFIRMED, GreenFeeReservado.FAILED, GreenFeeReservado.ABANDONED, GreenFeeReservado.REFUNDED, GreenFeeReservado.REVERSED]
		usuario(nullable: false)
		fechaReserva(nullable: false)
		
		feePagado(nullable: false, min: 0.0, max: 999.99, scale: 2)
		descuento(nullable: false, min: 0, max: 100)
		precioPorPagar(nullable: false, min: 0.0, max: 999.99, scale: 2)
		
		golfistaConfirmedNotificado(nullable:true)
		fechaGolfistaConfirmed(nullable:true)
		golfistaFailedNotificado(nullable:true)
		fechaGolfistaFailed(nullable:true)
		golfistaRefundedNotificado(nullable:true)
		fechaGolfistaRefunded(nullable:true)
		campoConfirmedNotificado(nullable:true)
		fechaCampoConfirmed(nullable:true)
		campoRefundedNotificado(nullable:true)
		fechaCampoRefunded(nullable:true)
		
		campo(nullable: false)
		diaHora(nullable: false)

		golfistas (nullable: false, min: 1, max: 4)
		
		greenFee(nullable: false)
		greenFeeGemelo(nullable: true)
		payment(nullable: false)

		observaciones(nullable: true, maxSize: 1000)
		langSaved(nullable:false, maxSize:5)
		
		licencia1(nullable:true, maxSize:10)
		licencia2(nullable:true, maxSize:10)
		licencia3(nullable:true, maxSize:10)
		licencia4(nullable:true, maxSize:10)
    }
	
	boolean isComplete() {
		return estado == GreenFeeReservado.COMPLETE
	}
	
	Locale getLangSavedAsLocale() {
		if (null == langSaved) {
			return new Locale("es","ES")
		}
		//
		if ("fr".equalsIgnoreCase(langSaved)) {
			return Locale.FRANCE
		}
		else if ("de".equalsIgnoreCase(langSaved)) {
			return Locale.GERMANY
		}
		else if ("ca".equalsIgnoreCase(langSaved)) {
			return new Locale("ca","ES")
		}
		else if ("gl".equalsIgnoreCase(langSaved)) {
			return new Locale("gl","ES")
		}
		else if ("en".equalsIgnoreCase(langSaved)) {
			return Locale.UK
		}
		else if ("es".equalsIgnoreCase(langSaved)) {
			return new Locale("es","ES")
		}
		return new Locale("es","ES")
	}
	
//	def booobeforeInsert() {
//		/* Solo se llamara para cuando se crea que es desde el post-buy-filter
//		 * Lo que hago es validar q el estado sea PENDING para cambiar el estado del green fee a PARCIAL o RESERVADO xa bloquear futuras compras del mismo 
//		 */
//		if (GreenFeeReservado.PENDING == estado) {
//			GreenFee.withNewSession {
//				
//				// TODO Aca puede q haya q hacerlo de otra manera ....
//				
//				greenFee.reset(golfistas)
//				greenFee.merge()
//			}
//		}
//	}
//	
//	def booooobeforeUpdate() {
//		/* Solo se llamara cuando el job de abandoned lo encuentre.
//		 * Se resetea el green fee y pasa a ACTIVO o se queda en PARCIAL dependiendo de los golfistas que tiene disponibles.
//		 * Se cambia el estado del Payment a INVALID
//		 */
//		if (GreenFeeReservado.ABANDONED == estado) {
//			GreenFee.withNewSession {
//				greenFee.reset(this)
//				greenFee.merge()
//			}
//			Payment.withNewSession {
//				payment.status = Payment.INVALID
//				payment.merge() 
//			}
//		}
//	}
	
}
