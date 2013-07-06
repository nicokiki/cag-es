package com.clickandgolf

import grails.plugins.springsecurity.Secured;
import com.clickandgolf.seguridad.User
import com.clickandgolf.GreenFeeReservado
import com.clickandgolf.GreenFeeService
import org.joda.time.DateTime;
import java.math.BigDecimal
import java.math.MathContext
import java.text.NumberFormat;
import java.text.DecimalFormat;
import org.springframework.context.i18n.LocaleContextHolder as LCH
import org.springframework.web.context.request.RequestContextHolder as RCH
import org.springframework.context.MessageSource

/**
 * Solo los Admin de Campo pueden acceder aqui.<br> El ADMIN ira a la BD por ahora ...
 *  
 * @author gonznic
 */
@Secured(['ROLE_CAMPO'])
class DashboardController {

	// Cambiar el pwd solo por POST
	static allowedMethods = [confirmarpassword:'POST']
	
	def springSecurityService
	def clockService
	def messageSource
	def greenFeeService
	def utilService
	
	
    def index() { 
		redirect(action: "principal")
	}
	
	def principal() {
		def user = User.get(springSecurityService.principal.id)
		log.info("Por listar el UNICO q campo q puede administrar el usuario '" + user?.email + "' ...")
			
		def campo = user.getCampo()
		if (!campo) {
			flash.message = message(code: 'default.not.found.message', default: 'El usuario no tiene campo asignado al cual administrar')
			return
		}
		
		// Ahora tengo que buscar los Green Fees para la proxima semana para el campo en cuestion. Lo hago aqui y no en otro lado
		def hoyHastaUnaSemana = clockService.startEndDaysForOneWeek
		params.max = Math.min(params.max ? params.int('max') : 5, 100)
		params.offset = params.offset ? params.int('offset') : 0
		params.sort = params.sort ? params.sort : 'diaHora'
		params.order = params.order ? params.order : 'asc'
		
		def resultado = GreenFeeReservado.createCriteria().list(max:params.max, offset:params.offset) {
			maxResults(params.max)
			firstResult(params.offset)
			eq("campo", campo)
			between("diaHora", hoyHastaUnaSemana[0], hoyHastaUnaSemana[1])
			'in'("estado", GreenFeeReservado.ESTADOS_RESERVADOS_Y_CONFIRMADOS)
			order(params.sort, params.order)
		}
		def totalGreenFeesReservados = resultado.getTotalCount()
		log.info("totalGreenFeesReservados: " + totalGreenFeesReservados)
		
		def model = [campo: campo, greenFeesReservados: resultado, totalGreenFeesReservados: totalGreenFeesReservados]
		
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "greenFeesReservados", model: model)
		}
		else {
			log.debug("No AJAX ...")
			model
		}
	}
	
	
	def ver = {
		def greenFeeReservado = GreenFeeReservado.get(params.id)
		log.info("Por ver un green fee reservado ...")
		
		if (!greenFeeReservado) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'greenfeereservado.label', default: 'Green Fee Reservado'), params.id])
			redirect(action: "principal")
			return
		}

		[greenFeeReservado: greenFeeReservado]
	}

	def administrar = {
		def user = User.get(springSecurityService.principal.id)
		def campo = user.getCampo()
		if (!campo) {
			flash.message = message(code: 'default.not.found.message', default: 'El usuario no tiene campo asignado al cual administrar')
			return
		}
		log.info("Por buscar los green fees del campo '" + campo?.nombre + "' que puede administrar el usuario '" + user?.email + "' ...")
		// Ahora tengo que buscar los Green Fees hasta el proximo mes para el campo en cuestion.
		
		def hoyHastaUnMes = clockService.getStartEndDaysForOneMonth()
		params.max = Math.min(params.max ? params.int('max') : 10, 100) // 10 de minimo
		params.offset = params.offset ? params.int('offset') : 0
		params.sort = params.sort ? params.sort : 'diaHora'
		params.order = params.order ? params.order : 'asc'

		def resultado = GreenFee.createCriteria().list(max:params.max, offset:params.offset) {
			maxResults(params.max)
			firstResult(params.offset)
			eq("campo", campo)
			between("diaHora", hoyHastaUnMes[0], hoyHastaUnMes[1])
			'in'("estado", GreenFee.ESTADOS_DISPONIBLES)
			order(params.sort, params.order)
		}
		def totalGreenFees = resultado.getTotalCount()
		log.info("totalGreenFees: " + totalGreenFees)
		
		def model = [campo: campo, greenFees: resultado, totalGreenFees: totalGreenFees]
		
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "greenFeesAdmin", model: model)
		}
		else {
			log.debug("No AJAX ...")
			model
		}
	}
	
	/**
	 * Este se llama desde administrar para posiblemente cambiar algo del green fee en cuestion.<br>
	 * Por el momento (v_1.0) no se puede cambiar los ya reservados.
	 */
	def cambiar = {
		def greenFee = GreenFee.get(params.id)
		log.info("Por mostrar un green fee para cambiarle algo ...")
		
		if (!greenFee) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'greenfee.label', default: 'Green Fee'), params.id])
			redirect(action: "administrar")
			return
		}
		
		// EL precio lo pondre formatted para que la gente respete el uso de puntos y no de comas
		def locale = RCH.currentRequestAttributes()?.locale ?: Locale.ES.locale
		
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		DecimalFormat df = (DecimalFormat)nf;
		df.applyPattern("#0.00");

		String precioFormatted = df.format(greenFee?.precio?.doubleValue());
		
		[greenFee: greenFee, precioFormatted: precioFormatted]
	}

	/**
	 * Se ejecuta via AJAX
	 */
	def modificar = {
		def greenFeePreModificado = GreenFee.get(params.id)
		log.info("Por modificar un green fee cambiandole algo ...")
		
		if (!greenFeePreModificado) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'greenfee.label', default: 'Green Fee'), params.id])
			redirect(action: "administrar")
			return
		}
		BigDecimal nuevoPrecio = null
		Integer nuevoDisponibles = null
		try {
			if (params.precio) {
				log.info("params.precio:" + params.precio)
				// EL precio lo pondre formatted para que la gente respete el uso de puntos y no de comas
				def locale = RCH.currentRequestAttributes()?.locale ?: Locale.ES.locale
				
				NumberFormat nf = NumberFormat.getNumberInstance(locale);
				DecimalFormat df = (DecimalFormat)nf;
				df.applyPattern("#0.00");
				
				Double d = (Double)df.parse(params.precio);
				log.info("d:" + d)
				
				nuevoPrecio = BigDecimal.valueOf(d)
//				 new BigDecimal(d, new MathContext(2))
				log.info("nuevoPrecio: " + nuevoPrecio)
			}
			else {
				nuevoPrecio = greenFeePreModificado.precio
			}
			nuevoDisponibles = params.disponibles ? params.int('disponibles') : greenFeePreModificado.disponibles
		}
		catch (Exception e) {
			def errors = new ArrayList<String>()
			errors.add(message(code: 'greenfee.precio.minimos.type'))
			log.info("Errores de tipo de datos:" + e.getMessage())
			
			render(contentType: 'text/json') {[
				'errors': errors,
				'status': "ERROR"
			]}
			return
		}
		Integer nuevoMinimos = params.golfistasMinimo ? params.int('golfistasMinimo') : greenFeePreModificado.golfistasMinimo
		String nuevoEstado = params.estado
		String nuevoInfo = params.info

		greenFeePreModificado.precio = nuevoPrecio
		greenFeePreModificado.disponibles = nuevoDisponibles
		greenFeePreModificado.estado = nuevoEstado
		greenFeePreModificado.info = nuevoInfo
		greenFeePreModificado.golfistasMinimo = nuevoMinimos
		
		// El precio puede haber cambiado => hay q recalcular el descuento y fee (y quizas el precioOriginal deba cambiar si hubiera subido el precio x encima del anterior)
		greenFeePreModificado = greenFeeService.reCalculateValues(greenFeePreModificado)
		// Antes de validar, se re-calcularan los valores de precios y demas. Luego se validara. Si se pasa la validacion entonces se seguira adelante.
		if (!greenFeePreModificado.validate()) {
			def errors = new ArrayList<String>()
			greenFeePreModificado.errors.allErrors.each {
				errors.add(messageSource.getMessage(it, LCH.getLocale()))
				log.info("Errores de validacion: " + messageSource.getMessage(it, LCH.getLocale()))
			}
			render(contentType: 'text/json') {[
				'errors': errors,
				'status': "ERROR"
			]}
			return
		}
		else {
			// Salvar los cambios.
			greenFeePreModificado.save(true)
			log.info("Se modifico el green fee '" + greenFeePreModificado + "' ...")
			
			render(contentType: 'text/json') {[
				'status': "OK"
			]}
			return
		}
	}
	
	def nuevo = {
	}
	
	def guardar = { NewGreenFeeCommand cmd ->
		if(cmd.hasErrors()) {
			def errors = new ArrayList<String>()
			cmd.errors.allErrors.each {
				errors.add(messageSource.getMessage(it, LCH.getLocale()))
				log.info("Errores de validacion al crear un nuevo green fee: " + messageSource.getMessage(it, LCH.getLocale()))
			}
			render(contentType: 'text/json') {[
				'errors': errors,
				'status': "ERROR"
			]}
			return
		}
		else {
			def user = User.get(springSecurityService.principal.id)
			def campo = user.getCampo()
			if (!campo) {
				def errors = new ArrayList<String>()
				errors.add(message(code: 'default.not.found.message'))
				render(contentType: 'text/json') {[
					'errors': errors,
					'status': "ERROR"
				]}
				return
			}
			// Ahora tengo que ver que la fecha es a posteriori de hoy!
			DateTime chosenDate = clockService.fromStringAndHours(cmd.fecha, cmd.hora, cmd.minuto)
			if (clockService.isBeforeTodayWithHours(chosenDate)) {
				def errors = new ArrayList<String>()
				errors.add(message(code: 'com.clickandgolf.NewGreenFeeCommand.fecha.menor.que.hoy'))
				render(contentType: 'text/json') {[
					'errors': errors,
					'status': "ERROR"
				]}
				return
			}
			GreenFee greenFee = cmd.createGreenFee(campo, chosenDate)
			
			greenFee = greenFeeService.reCalculateValues(greenFee)
			// Antes de validar, se re-calcularan los valores de precios y demas. Luego se validara. Si se pasa la validacion entonces se seguira adelante.
			if (!greenFee.validate()) {
				def errors = new ArrayList<String>()
				greenFee.errors.allErrors.each {
					errors.add(messageSource.getMessage(it, LCH.getLocale()))
					log.info("Errores de validacion: " + messageSource.getMessage(it, LCH.getLocale()))
				}
				render(contentType: 'text/json') {[
					'errors': errors,
					'status': "ERROR"
				]}
				return
			}
			// Ahora tengo q ver q no exista un Green Fee igual a la misma hora
			if (utilService.existeMismaHoraCampoTipo(greenFee)) {
				def errors = new ArrayList<String>()
				errors.add(message(code: 'com.clickandgolf.NewGreenFeeCommand.otro.igual'))
				render(contentType: 'text/json') {[
					'errors': errors,
					'status': "ERROR"
				]}
				return
			}
			// Ahora estoy seguro que es unico!
			greenFee.save(true)
			log.info("Se creo un nuevo green fee '" + greenFee + "' ...")
			
			render(contentType: 'text/json') {[
				'status': "OK"
			]}
			return
		}
	}
	
	def anteriores = {
		def user = User.get(springSecurityService.principal.id)
		def campo = user.getCampo()
		if (!campo) {
			flash.message = message(code: 'default.not.found.message', default: 'El usuario no tiene campo asignado al cual administrar')
			return
		}
		log.info("Por buscar los green fees que se reservaron del campo '" + campo?.nombre + "' que puede administrar el usuario '" + user?.email + "' ...")
		// Ahora tengo que buscar los Green Fees Reservados del pasado. Por ahora NO se filtra nada
		
		def desdeSiempreHastaHoy = clockService.getStartEndDaysForEverTillToday()
		params.max = Math.min(params.max ? params.int('max') : 10, 100) // 10 de minimo
		params.offset = params.offset ? params.int('offset') : 0
		params.sort = params.sort ? params.sort : 'diaHora'
		params.order = params.order ? params.order : 'asc'
		
		def resultado = GreenFeeReservado.createCriteria().list(max:params.max, offset:params.offset) {
			maxResults(params.max)
			firstResult(params.offset)
			eq("campo", campo)
			between("diaHora", desdeSiempreHastaHoy[0], desdeSiempreHastaHoy[1])
			order(params.sort, params.order)
		}

		def totalGreenFeesAnteriores = resultado.getTotalCount()
		log.info("totalGreenFeesAnteriores: " + totalGreenFeesAnteriores)
		
		def model = [campo: campo, greenFeesAnteriores: resultado, totalGreenFeesAnteriores: totalGreenFeesAnteriores]
		
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "greenFeesAnteriores", model: model)
		}
		else {
			log.debug("No AJAX ...")
			model
		}
	}
	
	def cambiarpassword() {
		
	}
	
	def confirmarpassword = { RegistroCampoCommand cmd ->
		if(cmd.hasErrors()) {
			def errors = new ArrayList<String>()
			cmd.errors.allErrors.each {
				errors.add(messageSource.getMessage(it, LCH.getLocale()))
				log.info("Errores de validacion al cambiar el password de un usuario de campo: " + messageSource.getMessage(it, LCH.getLocale()))
			}
			render(contentType: 'text/json') {[
				'errors': errors,
				'status': "ERROR"
			]}
			return
		}
		else {
			def user = User.get(springSecurityService.principal.id)
			if (user.password != springSecurityService.encodePassword(cmd.passwordOld)) {
				render(contentType: 'text/json') {[
					'errors': [g.message(code:'campos.dashboard.password.old.not.this.one')],
					'status': "ERROR"
				]}
				return
			}			
			user.password = cmd.passwordNew
			
			if (!user.save()) {
				def errors = new ArrayList<String>()
				log.info("No se pudo salvar el usuario ...")
				
				user.errors.allErrors.each {
					errors.add(messageSource.getMessage(it, LCH.getLocale()))
					log.info("Errores de validacion al cambiar el password de un usuario de campo: " + messageSource.getMessage(it, LCH.getLocale()))
				}
				render(contentType: 'text/json') {[
					'errors': errors,
					'status': "ERROR"
				]}
				return
			}
			springSecurityService.reauthenticate user.username
			log.info("El password del usuario de campo se cambio satisfactoriamente y se reautentico con el nuevo password ...")
			
			render(contentType: 'text/json') {[
				'status': "OK"
			]}
			return
		}
		
	}
	
}
