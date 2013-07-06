package com.clickandgolf

import java.math.MathContext

import org.grails.paypal.Payment;
import org.joda.time.DateTime
import org.joda.time.Days
import org.springframework.transaction.annotation.Transactional


class PaymentService {
	
	static scope = "prototype"
	
	def clockService
	def grailsApplication
	

	/**
	 * Se puede dar el caso de que al abandonarlo, otro lo tome y luego el primero lo haya modificado correctamente.
	 * En ese caso habria q avisar a los campos de lo q paso, q es una posibilidad en miles pero q puede pasar. Pedir disculpas por
	 * la doble reserva.
	 *
	 * @param paymentId
	 * @param greenFeeId
	 */
	@Transactional
	void analyseAbandoned(long paymentId, long greenFeeId) {
		log.info("Por analizar si el payment '" + paymentId + "' para el greenFeeId '" + greenFeeId + "' ha sido abandonado ...")
		
		Payment payment = Payment.get(paymentId);
		if (null == payment) {
			log.warn("No se encontro el payment en cuestion ... deberia existir en nuestra BD ... Analizar ...");
			return
		}
		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
		// Se valida que el green fee id sea el mismo
		if (greenFeeReservado.greenFee.id != greenFeeId) {
			log.warn(	"El Green Fee Reservado (id'" + greenFeeReservado.id + "') en nuestra BD esta relacionado con otro green-fee-id " +
						"('" + greenFeeReservado.greenFee.id + "') que NO es el recibido ('" + greenFeeId + "'). Analizar ...");
			return
		}
		/* Si llegue hasta aca es xq la informacion aparenta ser buena pero se analiza lo siguiente
		 * Si el Payment sigue en PENDING significa que el golfista no lo compro en Paypal entonces lo volvere a poner habilitado
		 */
		if (payment.status == Payment.PENDING) {
			def enMinutos = grailsApplication.config.abandoned.enMinutos
			log.info("El Payment sigue en PENDING luego de " + enMinutos + " minutos desde que el golfista tuvo la intencion de comprarlo => Re-habilitandolo ...")
			
			// El green fee se desbloquea con los golfistas q se habia reservado 
			def greenFee = GreenFee.get(greenFeeId)
			greenFee.reset(greenFeeReservado)
			if (!greenFee.save()) {
				log.error("No se pudo salvar el greenFee - Solucionar URGENTE")
				greenFee.errors.each {
					log.error("Error: " + it)
				}
			}
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
			
			// Se pasa el Payment a INVALID (cosa q luego se pueda analizar via IPN o Success)
			payment.status = Payment.INVALID
			payment.save()
			
			// Se pasa el estado a ABANDONED  (cosa q luego se pueda analizar via IPN o Success)
			greenFeeReservado.estado = GreenFeeReservado.ABANDONED;
			String ahoraStr = clockService.formatted(clockService.ahora(), ClockService.fullFormatter)
			String obs = greenFeeReservado.observaciones ?: "" 
			obs += "\n"
			obs += (ahoraStr + " -> El pago seguia en PENDING y pasaron los " + enMinutos + " minutos. Se cambia a ABANDONED y se actualiza el Green Fee en cuestion y el pago se pasa a INVALID")
			greenFeeReservado.observaciones = obs
			greenFeeReservado.save()
		
			log.info("Se termino de analizar y se hicieron cambios en GreenFee(id='" + greenFeeId + "'), GreenFeeReservado(id='" + greenFeeReservado?.id + "') y Payment(id='" + paymentId + "') ...")	
		}
	}

}
