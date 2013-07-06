package com.clickandgolf

import grails.plugins.springsecurity.Secured

import com.clickandgolf.seguridad.User
import com.clickandgolf.GreenFee
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.DecimalFormat;

import org.grails.paypal.Payment;
import org.springframework.web.context.request.RequestContextHolder as RCH

/**
 * Controller para efectuar las reservas.
 * Solo los "Golfistas" tienen acceso a este sitio
 * 
 * @author gonznic
 */
@Secured(['ROLE_USER', 'ROLE_FACEBOOK'])
class ReservaController {

	def springSecurityService
	
    def index() { 
		redirect(action: "preparar")
	}

	/**
	 * Es el q prepara la pantalla con la info necesaria para antes de ir a Paypal	
	 * @return
	 */
	def preparar() {
		flash.clear()
		def greenFeeId = params.id
		Integer golfistas = params.int('golfistas')
		if (!golfistas) {
			flash.message = message(code: 'greenfee.found.golfistas.elija.alguno')
			return
		}		
		GreenFee greenFee = GreenFee.get(greenFeeId)
		if (!greenFee) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'greenfee.label', default: 'Green Fee'), greenFeeId])
			return
		}
		// Puede q si no refresco la pantalla, no haya suficientes golfistas disponibles ...
		if (golfistas > greenFee.disponibles) {
			flash.message = message(code: 'greenfee.book.no.hay.disponibles', args: [golfistas, greenFee.disponibles])
			return
		}
		User user = User.get(springSecurityService.principal.id)
		String licencia = ""
		String warningMessage = null
		if (greenFee.campo?.requiereLicencia) {
			// Tengo q ver q el golfista tiene la licencia
			if (greenFee.campo.tipo == Campo.GOLF) {
				warningMessage =  message(code: 'greenfee.book.licencia.necesaria', args: ["Golf"])
				licencia = user.licenciaGolf
			}
			else {
				warningMessage =  message(code: 'greenfee.book.licencia.necesaria', args: ["Pitch & Putt".encodeAsHTML()])
				licencia = user.licenciaPP
			}
		}
		log.info("Por ver preparar una reserva para el green fee '" +greenFeeId + "' ...")
		
		def locale = RCH.currentRequestAttributes()?.locale ?: Locale.ES.locale
		
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		DecimalFormat df = (DecimalFormat)nf;
		df.applyPattern("#0.00");
		BigDecimal feeToPayUpFront = greenFee.getFeeToPayUpFront(golfistas)
		BigDecimal feeToPayAtCourse = greenFee.getFeeToPayAtCourse(golfistas)
		
		String feeToPayUpFrontFormatted = df.format(feeToPayUpFront.doubleValue());
		String feeToPayAtCourseFormatted = df.format(feeToPayAtCourse.doubleValue());
		log.info("feeToPayUpFront formatted: " + feeToPayUpFrontFormatted + ", feeToPayAtCourseFormatted: " + feeToPayAtCourseFormatted)
		
		String itemDescription = message(code: 'greenfee.label') + " - " + message(code: 'greenfee.book.campo') + " " + greenFee.campo.nombre + " - " +
								greenFee.getDia() + " - " + greenFee.getHoraMinuto() + " - " + golfistas + " " + message(code:'greenfee.book.golfistas')
		
		String lang = locale
		log.debug("Locale: " + locale)
		String langSaved = lang.toLowerCase()
		
		String buttonSrc = "https://www.paypal.com/";
		String paypalLang = "es_ES"
		if (!"es".equalsIgnoreCase(lang)) {
			if ("fr".equalsIgnoreCase(lang)) {
				paypalLang = "fr_FR";
			}
			else if ("de".equalsIgnoreCase(lang)) {
				paypalLang = "de_DE";
			}
			else if ("ca".equalsIgnoreCase(lang)) {
				paypalLang = "es_ES";
			}
			else if ("gl".equalsIgnoreCase(lang)) {
				paypalLang = "es_ES";
			}
			else if ("en".equalsIgnoreCase(lang)) {
				paypalLang = "en_GB";
				lang = "gb"; // Segun https://cms.paypal.com/au/cgi-bin/?cmd=_render-content&content_ID=developer/e_howto_html_Appx_websitestandard_htmlvariables#id08A6HI0J0VU es GB y no EN
			}
		}
		buttonSrc += paypalLang + "/i/btn/btn_paynow_SM.gif"; 
		String lc = lang.toUpperCase()
		
		// Todo lo q viene era xa armar mas valores xa pasarle a Paypal pero no los usa bien y asume como q queres abrir una cuenta nueva ...
		// Investigar mas adelante. En preparar.gsp tenia esto:
		// 		params="[lc:"${lc}",golfistas:"${golfistas}",address_override:"1",first_name:"${first_name}",last_name:"${last_name}",address1:"${address1}",city:"${city}",email:"${email}",night_phone_b:"${night_phone_b}"]"
		//			en vez de lo q hay ahora para params:
		//		params="[lc:"${lc}",golfistas:"${golfistas}"]"	
		// Armo unas variables mas para pre-popular campos para paypal / No sera exacto si hay no-alfanumericos ... para otra version
//		String first_name = (user.nombre ?: "sin nombre registrado")
//		String last_name = (user.apellidos?: "sin apellido registrado")
//		String address1 = ("mi direccion")
//		String city = (user.ciudad?: "sin ciudad registrada")
//		// state, zip no lo mando 
//		String email = user.email // el email es obligatorio
//		String night_phone_b = (user.telefono?: "666000000")
		

		def model = [	greenFee: greenFee, golfistas: golfistas, feeToPayUpFront: feeToPayUpFrontFormatted, feeToPayAtCourse: feeToPayAtCourseFormatted,
						itemDescription: itemDescription, user: user, buttonSrc: buttonSrc, lc: lc, warningMessage: warningMessage, langSaved: langSaved,
						licencia: licencia ]
//						, first_name: first_name, last_name: last_name, address1: address1, city: city, email: email, night_phone_b: night_phone_b]
		model
	}
	
	
	/**
	 * PaypalController redirecciona aqui luego del post-success-filter y es para mostrar la informacion en pantalla de lo realizado.
	 *  
	 * @return gonznic
	 */
	def exito() {
		log.info("Por ejecutar el action de exito de la reserva para el payment-transaction-id ${params.transactionId} ...")
		
		def payment = Payment.findByTransactionId(params.transactionId)
		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
		def greenFee = GreenFee.get(greenFeeReservado.greenFee.id)
		def usuario = greenFeeReservado.usuario
		// El usuario lo necesitamos por el email

		def model = [greenFee: greenFee, greenFeeReservado: greenFeeReservado, payment: payment, usuario: usuario]
		model
	}
	
	/**
	 * PaypalController redirecciona aqui luego del post-cancel-filter y es para mostrar la informacion en pantalla de lo NO realizado.
	 *  
	 * @return gonznic
	 */
	def cancelacion() {
		log.info("Por ejecutar el action de la cancelacion de la reserva para el payment-transaction-id ${params.transactionId} ...")
		
		def payment = Payment.findByTransactionId(params.transactionId)
		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
		def greenFee = GreenFee.get(greenFeeReservado.greenFee.id)
		
		def model = [greenFee: greenFee]
		model
	}
	
	
	
	
	
	
//	def borrar() {
//		
//		def locale = RCH.currentRequestAttributes()?.locale
//		
//		paypalFilterService.testEmail(params.transactionId, locale)
//	}
	
}
