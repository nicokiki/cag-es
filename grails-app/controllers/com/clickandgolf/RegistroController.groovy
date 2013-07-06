package com.clickandgolf

import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.context.i18n.LocaleContextHolder as LCH
import org.springframework.context.MessageSource
import grails.plugin.asyncmail.AsynchronousMailService;

import com.clickandgolf.seguridad.User;
import com.clickandgolf.seguridad.UserRole;
import com.clickandgolf.seguridad.Role;
import com.clickandgolf.RegistroService


class RegistroController {

	/**
	 * Dependency injection for the springSecurityService.
	 */
	def springSecurityService
	def messageSource
	def registroService
	AsynchronousMailService asynchronousMailService
	def utilService

	/**
	 * Default action; redirects to 'defaultTargetUrl' if logged in, /registro/nuevo otherwise.
	 */
	def index = {
		if (springSecurityService.isLoggedIn()) {
			redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
		}
		else {
			redirect action: 'nuevo', params: params
		}
	}

	def nuevo = {
		def config = SpringSecurityUtils.securityConfig
		
		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}
		
		String view = 'nuevo'
		String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
		render view: view, model: [postUrl: postUrl,
								   rememberMeParameter: config.rememberMe.parameter]
	}

	/**
	 * Para registrar se tiene q validar los datos y que el email no exista ya	
	 */
	def registrar = { RegistroCommand cmd ->
		log.info("Por registrar un nuevo usuario con email: '" + cmd.email + "' ...")
		
//		log.warn("a:" + g.message(code:'springSecurity.login.title'))
//		log.warn("b:" +  messageSource.getMessage("springSecurity.login.title", null, LCH.getLocale()))
//		log.warn("c:" + g.message(code:'register.email.already.exist', args:['a@a.com']))

		/* Asi usa el locale: 
		 * 	messageSource.getMessage(it, LCH.getLocale())
		 * Si se tienen q pasar params entonces usar este q pone el locale solito:
		 * 	g.message(code:'register.email.already.exist', args:[cmd.email])
		 */
		if(cmd.hasErrors()) {
			def errors = new ArrayList<String>()
			cmd.errors.allErrors.each {
				errors.add(messageSource.getMessage(it, LCH.getLocale()))
				log.info("Errores de validacion al registrar un usuario: " + messageSource.getMessage(it, LCH.getLocale()))
			}
			render(contentType: 'text/json') {[
				'errors': errors,
				'status': "ERROR"
			]}
			return
		}
		else {
			// No hay errores a priori pero puede que el email ya esta registrado => no se deberia poder
			User existingUser = User.findByEmail(cmd.email)
			if (null != existingUser) {
				render(contentType: 'text/json') {[
					'errors': [g.message(code:'register.email.already.exist', args:[cmd.email])],
					'status': "ERROR"
				]}
				return
				
			}
			// Si llegue aca es xq deberia estar todo bien! => Intento guardar el nuevo usuario y le asigno un rol (aca y no en un service)
			// Se genera un ID unico q es el cual se usara en la creacion del usuario y q luego se enviara x email!
			def transactionId = "email-${cmd.email}-${System.currentTimeMillis()}".encodeAsHTML()
			def nuevoUsuario = registroService.save(cmd, transactionId)
			render(contentType: 'text/json') {[
				'status': "OK"
			]}
			// Ahora mando el email si estuvo todo bien ...
			def emailFrom = utilService.emailFrom()
			def locale = LCH.getLocale()
			def model = [nuevoUsuario: nuevoUsuario, locale: locale, transactionId: transactionId]
			asynchronousMailService.sendAsynchronousMail {
				to "${cmd.email}"
				from emailFrom
				subject messageSource.getMessage("notificacion.email.registro.email.confirmacion", null, locale)
				html g.render(template:"emailConfirmacion", model:model)
			}
			log.info("Se envio un email para activacion a ${cmd.email} ...")
			
			return
		}
	}
	
	def activar = {
		log.info("activando ...")
		def email = params.email
		def transactionId = params.transactionId
		if (!email || !transactionId) {
			flash.message = message(code: 'registro.flash.message')
			return
		} 

		User usuario = User.findByEmail(email)
		if (!usuario) {
			flash.message = message(code: 'registro.flash.message')
			return
		}
		def model = [usuario: usuario]
		if (usuario.isEnabled()) {
			flash.message = message(code: 'registro.usuario.activado.anteriormente', args: [usuario.email])
			model
			return
		}
		else if (usuario.validCode != transactionId) {
			flash.message = message(code: 'registro.usuario.invalid.transactionId', args: [usuario.email])
			model
			return
		}
		else {
			// Esta enabled:false y el codigo de transaction es el mismo => Se desbloquea!
			usuario.enabled = true
			usuario.merge()
			log.info("Se desbloqueo el usuario ${usuario.email} ...")
			model
			
		}		
	}
	
	def olvide = { OlvideCommand cmd ->
		if(cmd.hasErrors()) {
			def errors = new ArrayList<String>()
			cmd.errors.allErrors.each {
				errors.add(messageSource.getMessage(it, LCH.getLocale()))
				log.debug("Errores de validacion al intentar olvidar el password: " + messageSource.getMessage(it, LCH.getLocale()))
			}
			render(contentType: 'text/json') {[
				'errors': errors,
				'status': "ERROR"
			]}
			return
		}
		// No hay errores a priori pero puede que el email ya esta registrado => no se deberia poder
		User existingUser = User.findByEmail(cmd.j_username)
		if (!existingUser) {
			render(contentType: 'text/json') {[
				'errors': [g.message(code:'security.forgot.email.does.not.exist', args:[cmd.j_username])],
				'status': "ERROR"
			]}
			return
		}
		if (existingUser.isFacebookUser()) {
			render(contentType: 'text/json') {[
				'errors': [g.message(code:'security.forgot.facebook.user', args:[cmd.j_username])],
				'status': "ERROR"
			]}
			return
		}
		if (existingUser.isAccountLocked()) {
			render(contentType: 'text/json') {[
				'errors': [g.message(code:'security.forgot.user.blocked', args:[cmd.j_username])],
				'status': "ERROR"
			]}
			return
		}
		// Si llego aca entonces estaria todo bien!
		// Se genera un ID unico q es el cual se usara en la creacion del usuario y q luego se enviara x email!
		def transactionId = "email-${cmd.j_username}-${System.currentTimeMillis()}".encodeAsHTML()
		existingUser.validCode = transactionId
		existingUser.merge()
		log.info("Guardado en el usuario un nuevo validCode ...")
		render(contentType: 'text/json') {[
			'status': "OK"
		]}
		// Ahora mando el email si estuvo todo bien ...
		def emailFrom = utilService.emailFrom()
		def locale = LCH.getLocale()
		def model = [user: existingUser, locale: locale, transactionId: transactionId]
		asynchronousMailService.sendAsynchronousMail {
			to "${cmd.j_username}"
			from emailFrom
			subject messageSource.getMessage("security.forgot.notificacion.email.1", null, locale)
			html g.render(template:"emailOlvidePrimerPaso", model:model)
		}
		log.info("Se envio un email para recuperar el pwd a ${cmd.j_username} ...")
		
		return
	}
	
	def preCambiar = {
		log.info("Por mostrar la pantalla para cambiar el password ...")
		def email = params.email
		def transactionId = params.transactionId
		if (!email || !transactionId) {
			flash.message = message(code: 'security.forgot.precambiar.flash.message')
			return
		}

		User usuario = User.findByEmail(email)
		if (!usuario) {
			flash.message = message(code: 'security.forgot.precambiar.flash.message')
			return
		}
		def model = [usuario: usuario]
		if (usuario.validCode != transactionId) {
			flash.message = message(code: 'security.forgot.precambiar.invalid.transactionId', args: [usuario.email])
			model
			return
		}
		else {
			// Esta todo bien => Se muestra la pantalla xa cambiar el pwd.
			log.info("Se muestra la pantalla xa cambiar el pwd del usuario ${usuario.email} ...")
			model
		}
	}
	
	def confirmarPassword = { OlvideCambioCommand cmd ->
		if(cmd.hasErrors()) {
			def errors = new ArrayList<String>()
			cmd.errors.allErrors.each {
				errors.add(messageSource.getMessage(it, LCH.getLocale()))
				log.info("Errores de validacion al cambiar el password de un usuario: " + messageSource.getMessage(it, LCH.getLocale()))
			}
			render(contentType: 'text/json') {[
				'errors': errors,
				'status': "ERROR"
			]}
			return
		}
		else {
			User existingUser = User.findByEmail(cmd.email)
			if (!existingUser) {
				render(contentType: 'text/json') {[
					'errors': [g.message(code:'security.forgot.email.does.not.exist', args:[cmd.email])],
					'status': "ERROR"
				]}
				return
			}
			// Le cambio el password ahora mismo!!!
			existingUser.password = cmd.passwordNew
			existingUser.enabled = true // por si acaso
			if (!existingUser.save()) {
				def errors = new ArrayList<String>()
				log.info("No se pudo salvar el usuario ...")
				
				existingUser.errors.allErrors.each {
					errors.add(messageSource.getMessage(it, LCH.getLocale()))
					log.info("Errores de validacion al cambiar el password de un usuario: " + messageSource.getMessage(it, LCH.getLocale()))
				}
				render(contentType: 'text/json') {[
					'errors': errors,
					'status': "ERROR"
				]}
				return
			}
			log.info("El password del usuario se cambio satisfactoriamente ...")
			
			render(contentType: 'text/json') {[
				'status': "OK"
			]}
			return
		}
		
	}

	
}
