package com.clickandgolf

import grails.plugin.asyncmail.AsynchronousMailService;
import org.springframework.context.i18n.LocaleContextHolder as LCH
import org.springframework.context.MessageSource

class InfoController {
	
	def simpleCaptchaService
	def messageSource
	AsynchronousMailService asynchronousMailService
	
	def index() {
		redirect(action:"quienesSomos")
	}

    def condiciones() { 
		
	}
	
	def politicaDePrivacidad() {
		
	}
	
	def quienesSomos() {
		
	}
	
	def faq() {
		
	}
	
	def comoFunciona() {
		
	}
	
	def contactanos() {
		/* Voy a poner un captcha xa q no me llenen de emails siguiendo un mini:
		 * 
		 */
	}
	
	def contactado = { ContactoCommand cmd ->
		if(cmd.hasErrors()) {
			def errors = new ArrayList<String>()
			cmd.errors.allErrors.each {
				errors.add(messageSource.getMessage(it, LCH.getLocale()))
				log.debug("Errores de validacion al intentar contactarnos: " + messageSource.getMessage(it, LCH.getLocale()))
			}
			render(contentType: 'text/json') {[
				'errors': errors,
				'status': "ERROR"
			]}
			return
		}
		else if (!simpleCaptchaService.validateCaptcha(cmd.captcha)) {
			log.info("El captcha de contactanos no es valido ...")
			render(contentType: 'text/json') {[
				'errors': [g.message(code:'com.clickandgolf.ContactoCommand.captcha.invalido')],
				'status': "ERROR"
			]}
			return
		}
		else {
			// Todo estaria bien ...
			asynchronousMailService.sendAsynchronousMail {
				to "info@clickandgolf.es"
				from cmd.email
				subject "Contactados desde la pagina - Asunto:${cmd.asunto}"
				html "<b>${cmd.nombre}</b> nos mando este email.<br>Asunto:${cmd.asunto}.<br>" +
					 "Email:${cmd.email}.<br>Mensaje:${cmd.mensaje} ";
			    maxAttemptsCount 1;
				delete true;
			}
			
			log.info("Email enviado y eliminado de nuestro sistema al ser enviado ...") 
			render(contentType: 'text/json') {[
				'status': "OK"
			]}
			return
		}
	}

	def sorteo() {
	}

		
}
