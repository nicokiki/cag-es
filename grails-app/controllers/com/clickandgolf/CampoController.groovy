package com.clickandgolf

import org.joda.time.DateTime;

/**
 * Es el listado de campos. No es segurizado xq no necesita serlo.
 * 
 * @author gonznic
 */
class CampoController {
	
	def clockService

	def index = {
		redirect(action: "listaDeCampos")
	}
	
	/**
	 * Pagina cada 10 campos xa q no haya muchisimos
	 * @return
	 */
    def listaDeCampos() {
		log.info("Por empezar con lista de campos ...")
		
		params.max = params.max ? params.int('max') : 5
		params.offset = params.offset ? params.int('offset') : 0
		params.sort = params.sort ? params.sort : 'nombre'
		params.order = params.order ? params.order : 'asc'
		
		def resultado = Campo.createCriteria().list(max:params.max, offset:params.offset) {
			maxResults(params.max)
			firstResult(params.offset)
			eq("estado", Campo.ACTIVO)
			order(params.sort, params.order)
		}
		def totalCampos = resultado.getTotalCount()
		log.info("totalCampos: " + totalCampos)

		def model = [campos: resultado, totalCampos: totalCampos]
		
		
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "campos", model: model)
		}
		else {
			log.debug("No AJAX ...")
			model
		}
	}
	
	/**
	 * Pagina cada 10 campos xa q no haya muchisimos
	 * @return
	 */
	def listaDeCamposDeGolf() {
		log.info("Por empezar con lista de campos ...")
		
		params.max = params.max ? params.int('max') : 5
		params.offset = params.offset ? params.int('offset') : 0
		params.sort = params.sort ? params.sort : 'nombre'
		params.order = params.order ? params.order : 'asc'
		
		def resultado = Campo.createCriteria().list(max:params.max, offset:params.offset) {
			maxResults(params.max)
			firstResult(params.offset)
			eq("estado", Campo.ACTIVO)
			eq("tipo", Campo.GOLF)
			order(params.sort, params.order)
		}
		def totalCampos = resultado.getTotalCount()
		log.info("totalCampos: " + totalCampos)

		def model = [campos: resultado, totalCampos: totalCampos]
		
		
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "campos", model: model)
		}
		else {
			log.debug("No AJAX ...")
			model
		}
	}

	/**
	 * Pagina cada 10 campos xa q no haya muchisimos
	 * @return
	 */
	def listaDeCamposDePitchAndPutt() {
		
		log.info("Por empezar con lista de campos ...")
		
		params.max = params.max ? params.int('max') : 5
		params.offset = params.offset ? params.int('offset') : 0
		params.sort = params.sort ? params.sort : 'nombre'
		params.order = params.order ? params.order : 'asc'
		
		def resultado = Campo.createCriteria().list(max:params.max, offset:params.offset) {
			maxResults(params.max)
			firstResult(params.offset)
			eq("estado", Campo.ACTIVO)
			eq("tipo", Campo.PITCH_AND_PUTT)
			order(params.sort, params.order)
		}
		def totalCampos = resultado.getTotalCount()
		log.info("totalCampos: " + totalCampos)

		def model = [campos: resultado, totalCampos: totalCampos]
		
		
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "campos", model: model)
		}
		else {
			log.debug("No AJAX ...")
			model
		}
	}

	
	def ver() {
		def campo = Campo.get(params.id)
		if (!campo) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'campo.label', default: 'Campo'), params.id])
			redirect(action: "listaDeCampos")
			return
		}
		log.debug("Por mostrar el campo '" + campo.getNombre() + "' ...")

		// El link mostrara los green-fees de maniana siempre por defecto
		DateTime tomorrowAux = clockService.ahora().plusDays(1)
		def tomorrow = [dia:clockService.formatted(tomorrowAux, ClockService.dayFormatter), mes:clockService.formatted(tomorrowAux, ClockService.monthFormatter), anio:clockService.formatted(tomorrowAux, ClockService.yearFormatter)]
		
		def linkSocial = g.createLink(absolute: true, mapping:'campo', params:[id:"${params.id}", nombre: "${campo.hyphenatedNombre}"])
		linkSocial = linkSocial.encodeAsHTML()
		log.info("linkSocial: ${linkSocial}")		
		
		[campo: campo, tomorrow: tomorrow, linkSocial: linkSocial]
	}
	
}
