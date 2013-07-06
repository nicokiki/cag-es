package com.clickandgolf

import grails.plugins.springsecurity.Secured

import org.springframework.context.i18n.LocaleContextHolder as LCH
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.FacebookLink
import org.springframework.social.facebook.api.ImageType
import org.springframework.social.facebook.api.impl.FacebookTemplate

import com.clickandgolf.seguridad.FacebookUser
import com.clickandgolf.seguridad.User

@Secured(['ROLE_USER', 'ROLE_FACEBOOK'])
class UsuarioController {

	def springSecurityService
	def messageSource
	
    def index() { 
		redirect(action: "panel")
	}
	
	def panel() {
		def user = User.get(springSecurityService.principal.id)
		log.info("El usuario encontrado es: " + user.email)
		
		def usuario = new UsuarioCommand(email: user.email, nombre: user.nombre, apellidos: user.apellidos, telefono: user.telefono, ciudad: user.ciudad,
										licenciaGolf: user.licenciaGolf, licenciaPP: user.licenciaPP)
		
		if (user.isFacebookUser()) {
			log.info("El usuario es usuario de facebook entonces se mostrara su foto de perfil ...")
		}
		// Ahora busco los green fees reservados
		params.max = params.max ? params.int('max') : 10
		params.offset = params.offset ? params.int('offset') : 0
		params.sort = params.sort ? params.sort : 'diaHora'
		params.order = params.order ? params.order : 'desc'
		
		def resultado = GreenFeeReservado.createCriteria().list(max:params.max, offset:params.offset) {
			maxResults(params.max)
			firstResult(params.offset)
			eq("usuario", user)
			'in'("estado", GreenFeeReservado.ESTADOS_RESERVADOS_Y_CONFIRMADOS)
			order(params.sort, params.order)
		}
		def totalGreenFeesReservados = resultado.getTotalCount()
		log.info("totalGreenFeesReservados del usuario: " + totalGreenFeesReservados)

		def model = [usuario: usuario, greenFeesReservados: resultado, totalGreenFeesReservados: totalGreenFeesReservados]
		
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "panelGreenFeesReservados", model: model)
		}
		else {
			log.debug("No AJAX ...")
			model
		}
	}
	
	// Solo se llama desde el GUI cuando el usuario es de tipo FACEBOOK
	def facebookProfilePicture = {
		def user = User.get(springSecurityService.principal.id)
		FacebookUser fbUser = FacebookUser.findByUser(user)
		Facebook facebook = new FacebookTemplate(fbUser.accessToken)
		
		log.info("facebook.isAuthorized():" + facebook.isAuthorized())
		
//		FacebookLink link = new FacebookLink("http://lanacion.com.ar",
//			"Click & Golf",
//			"El sitio de Click & Golf",
//			"Click & Golf es un servicio web de reserva de green fees online para campos de Golf y Pitch & Putt.");
//		try {
//			facebook.feedOperations().postLink("Nicolas reservo un green fee en el campo X utilizando Click & Golf!" + new Date().toString(), link)
//		}
//		catch (Exception e) {
//			log.info("Puede fallar porque no tenemos el permiso en fb: ${e}")
//			
//		}
		def img = facebook.userOperations().getUserProfileImage(ImageType.SMALL)
		
		response.outputStream << img
	}
	
	def modificar = { UsuarioCommand cmd -> 
		log.info("Por modificar el usuario con email: '" + cmd.email + "' ...")
		
		User existingUser = User.findByEmail(cmd.email)
		if (null != existingUser) {
			existingUser.telefono = cmd.telefono
			existingUser.nombre = cmd.nombre
			existingUser.apellidos = cmd.apellidos
			existingUser.ciudad = cmd.ciudad
			
			existingUser.licenciaGolf = cmd.licenciaGolf
			existingUser.licenciaPP = cmd.licenciaPP
			
			if (!existingUser.save(flush: true)) {
				String error = ""				
				existingUser.errors.each {
					error += messageSource.getMessage(it, LCH.getLocale())
				}
				// flash.message = message(code: 'user.edit.general.failure')
				flash.message = error
			}
			redirect(action: "panel")
			return
		}
		else {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'Usuario')])
			redirect(action: "panel")
			return
		}
	}
	
	// Reuso el mismo command xq es lo mismo q xa cambiar el pwd de los campos ...
	def confirmarpassword = { RegistroCampoCommand cmd ->
		if(cmd.hasErrors()) {
			def errors = new ArrayList<String>()
			cmd.errors.allErrors.each {
				errors.add(messageSource.getMessage(it, LCH.getLocale()))
				log.info("Errores de validacion al cambiar el password de un usuario : " + messageSource.getMessage(it, LCH.getLocale()))
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
					'errors': [g.message(code:'usuario.password.old.not.this.one')],
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
					log.info("Errores de validacion al cambiar el password de un usuario de: " + messageSource.getMessage(it, LCH.getLocale()))
				}
				render(contentType: 'text/json') {[
					'errors': errors,
					'status': "ERROR"
				]}
				return
			}
			springSecurityService.reauthenticate user.username
			log.info("El password del usuario se cambio satisfactoriamente y se reautentico con el nuevo password ...")
			
			render(contentType: 'text/json') {[
				'status': "OK"
			]}
			return
		}
		
	}

	
}
