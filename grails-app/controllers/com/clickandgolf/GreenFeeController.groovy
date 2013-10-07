package com.clickandgolf

import java.math.BigDecimal;

import javax.persistence.IdClass;

import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers
import grails.gorm.DetachedCriteria;
import org.joda.time.DateTime;

import groovy.sql.Sql

import com.clickandgolf.ClockService
import com.clickandgolf.UtilService
import org.springframework.web.context.request.RequestContextHolder as RCH

/**
 * Controller para la busqueda de green-fees.<br>
 * NO tiene seguridad porque es publico.<br>
 * 
 * @author gonznic
 */
class GreenFeeController {
	
	def clockService
	def utilService
	def dataSource
	def greenFeeService
	

    def index() { 
		redirect(action: "busquedaDeCampos")
	}

	def busquedaDeCampos() {
		/* Fecha de busqueda por defecto sera hoy
		 * Ubicacion por defecto sera todo (es ciudad:nombre o region:nombre)
		 */
		
		def ubicacionAux = params?.ubicacion
		def fecha = params?.fecha
		def locale = RCH.currentRequestAttributes()?.locale
		
		log.info "$ubicacionAux, $fecha, $locale"
		
		def model = greenFeeService.busquedaDeCampos(ubicacionAux, fecha, locale)
		
		model
		
//		def ubicacionAux = params.ubicacion
//		String nombreUbicacion = ("null" == ubicacionAux) ? null : ubicacionAux
//		DateTime desde = clockService.ahora()
//		if (params.fecha) {
//			desde = clockService.fromStringConHoraComienzo(params.fecha)
//		}
//		DateTime hasta = clockService.finalDeFecha(desde) // Busca por un dia
//		DateTime desdeAux = clockService.enXHorasSiEsHoy(desde);
//		log.debug("Buscando desde:'" + desdeAux + "', hasta:'" + hasta + "' ...")
//		
//		def detachedCriteria = utilService.parseUbicacion(nombreUbicacion, null)
//		def ubicacionSplitted = utilService.splitUbicacion(nombreUbicacion)
//		if (ubicacionSplitted[1].contains("Españ")) {
//			// Esto es feo pero no importa es x el encoding q no lo muestra bien :(
//			ubicacionSplitted[1] = "Espa&ntilde;a"
//		}
//		
//		def resultado = null
//		def campos = detachedCriteria.list()
//		if (!campos || campos.isEmpty()) {
//			resultado = new ArrayList<GreenFee>();
//		}
//		else {
//			// Hay un defect http://jira.grails.org/browse/GRAILS-8114			
//			resultado = GreenFee.createCriteria().list() {
//				between("diaHora", desdeAux, hasta)
//				'in'("estado", GreenFee.ESTADOS_DISPONIBLES)
//				'in'("campo", campos)
//				fetchMode("campo", org.hibernate.FetchMode.JOIN)
//			}
//		}
//		
//		def locale = RCH.currentRequestAttributes()?.locale
//		def diaEnTexto = clockService.getDiaTexto(desde, locale)
//		def formattedDate = clockService.formatted(desde)
//		def dia = clockService.formatted(desde, ClockService.dayFormatter)
//		def mes = clockService.formatted(desde, ClockService.monthFormatter)
//		def anio = clockService.formatted(desde, ClockService.yearFormatter)
//		
//		/* Ahora tengo un monton de Green Fees pero los tengo q "acumular"
//		 */
//	 	def greenFeesInfo = greenFeeService.decorateGreenFeeInfo(resultado, dia, mes, anio)
//		log.info("#greenFeeInfos encontrados: " + greenFeesInfo.size())
//						
//		def includeYesterday = (clockService.isToday(desde) ? "NO" : "YES");
//		String yesterdayFrom = clockService.formatted(desde.plusDays(-1));
//		String tomorrowFrom = clockService.formatted(desde.plusDays(1));
//		
//		def model = [	greenFeesInfo: greenFeesInfo, formattedDate: formattedDate, ubicacionSplitted: ubicacionSplitted[1], 
//						includeYesterday: includeYesterday, nombreUbicacion: nombreUbicacion, 
//						yesterdayFrom: yesterdayFrom, tomorrowFrom: tomorrowFrom, diaEnTexto: diaEnTexto]
//		model
	}

	def greenFees = {
		def campo = Campo.get(params.idCampo)
		def fecha = params.dia + "/" + params.mes + "/" + params.anio
		// Tengo q ver q la fecha sea mayor o igual a hoy
		
		if (!campo) {
			log.info("No se encontro ningun campo porque el id es incorrecto o faltante ...")
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'campo.label', default: 'Campo'), params.id])
			redirect(action: "busquedaDeCampos")
			return
		}
		DateTime desde = clockService.fromStringConHoraComienzo(fecha)
		if (clockService.isBeforeToday(desde)) {
			log.info("No se pueden buscar green fess anteriores a hoy ...")
			flash.message = message(code: 'greenfee.anterior.a.hoy')
			redirect(action: "busquedaDeCampos")
			return
		}
		def resultado = greenFeeService.busquedaGreenFees(campo, fecha)
		
		def locale = RCH.currentRequestAttributes()?.locale
		def diaEnTexto = clockService.getDiaTexto(desde, locale)
		
		def includeYesterday = (clockService.isToday(desde) ? "NO" : "YES");
		DateTime yesterdayAux = desde.plusDays(-1)
		DateTime tomorrowAux = desde.plusDays(1)
		def yesterday = [dia:clockService.formatted(yesterdayAux, ClockService.dayFormatter), mes:clockService.formatted(yesterdayAux, ClockService.monthFormatter), anio:clockService.formatted(yesterdayAux, ClockService.yearFormatter)]
		def tomorrow = [dia:clockService.formatted(tomorrowAux, ClockService.dayFormatter), mes:clockService.formatted(tomorrowAux, ClockService.monthFormatter), anio:clockService.formatted(tomorrowAux, ClockService.yearFormatter)]
		
		log.info("# greenFees encontrados: " + resultado.size())
		def model = [	greenFees: resultado, fecha: fecha, campo: campo, diaEnTexto: diaEnTexto, includeYesterday: includeYesterday, yesterday: yesterday, tomorrow: tomorrow]
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "greenFeesEncontrados", model: model)
		}
		else {
			log.debug("No AJAX ...")
			model
		}
	}

	def busquedaAvanzadaHiddenDoesntWork() {
		/*
		 * Precio maximo por defecto sera 250
		 * Campo por defecto no hay
		 * Descuento por defecto es 100 entonces se pide q el descuento sea meno*r o igual que
		 * Golfistas Minimos es x defecto 1 (y se pide q sea mayor o igual)
		 * Disponibles por defecto es 1 (y se pide q sea mayor o igual)
		 *
		 */

		def ubicaciones = Ubicacion.createCriteria().list() {
			order("region")
			order("ciudad")
		}

		def precioMax = params.precioMax ? params.double('precioMax') : 250
		def ubicacionAux = params.ubicacion
		def locale = RCH.currentRequestAttributes()?.locale
		
		def model = greenFeeService.busquedaDeCamposAvanzada(precioMax, ubicacionAux, params.fecha, params.tipo ?: "",  params.hoyos ?: "", locale)
		model[ubicaciones] = ubicaciones
		
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "greenFees", model: model)
		}
		else {
			log.info("No AJAX ...")
			model
		}
	}

	def busquedaDeCamposAvanzada() {
		/*
		 * La deje como antes porque fallaba por algun maldito motivo
		 * Precio maximo por defecto sera 10000
		 * Campo por defecto no hay
		 * Descuento por defecto es 100 entonces se pide q el descuento sea meno*r o igual que
		 * Golfistas Minimos es x defecto 1 (y se pide q sea mayor o igual)
		 * Disponibles por defecto es 1 (y se pide q sea mayor o igual)
		 *
		 */
		def ubicaciones = Ubicacion.createCriteria().list() {
			order("region")
			order("ciudad")
		}
		
		def precioMax = params.precioMax ? params.double('precioMax') : 250
		def ubicacionAux = params.ubicacion
		String nombreUbicacion = ("null" == ubicacionAux) ? null : ubicacionAux
		DateTime desde = clockService.ahora()
		if (params.fecha) {
			desde = clockService.fromStringConHoraComienzo(params.fecha)
		}
		DateTime hasta = clockService.finalDeFecha(desde) // Busca por un dia
		DateTime desdeAux = clockService.enXHorasSiEsHoy(desde);
		
		log.debug("Buscando desde:'" + desdeAux + "', hasta:'" + hasta + "' ...")
		
		def tipo = params.tipo ?: ""
		def detachedCriteria = utilService.parseUbicacion(nombreUbicacion, tipo)
		def ubicacionSplitted = utilService.splitUbicacion(nombreUbicacion)
		if (ubicacionSplitted[1].contains("Españ")) {
			// Esto es feo pero no importa es x el encoding q no lo muestra bien :(
			ubicacionSplitted[1] = "Espa&ntilde;a"
		}
		
		def resultado = null
		def campos = detachedCriteria.list()
		if (!campos || campos.isEmpty()) {
			resultado = new ArrayList<GreenFee>();
		}
		else {
			def hoyos = [GreenFee.HOYOS_18, GreenFee.HOYOS_9]
			def hoyosAux = params.hoyos ?: ""
			if ("18".equals(hoyosAux)) {
				hoyos = [GreenFee.HOYOS_18]
			}
			else if ("9".equals(hoyosAux)) {
				hoyos = [GreenFee.HOYOS_9]
			}
			
			resultado = GreenFee.createCriteria().list() {
				between("diaHora", desdeAux, hasta)
				'in'("estado", GreenFee.ESTADOS_DISPONIBLES)
				le("precio", new BigDecimal(precioMax))
				'in'("campo", campos)
				'in'("hoyos", hoyos)
				fetchMode("campo", org.hibernate.FetchMode.JOIN)
			}
		}
		def locale = RCH.currentRequestAttributes()?.locale
		def diaEnTexto = clockService.getDiaTexto(desde, locale)
		def formattedDate = clockService.formatted(desde)
		def dia = clockService.formatted(desde, ClockService.dayFormatter)
		def mes = clockService.formatted(desde, ClockService.monthFormatter)
		def anio = clockService.formatted(desde, ClockService.yearFormatter)
		
		/* Ahora tengo un monton de Green Fees pero los tengo q "acumular"
		 */
		 def greenFeesInfo = greenFeeService.decorateGreenFeeInfo(resultado, dia, mes, anio)
		log.info("#greenFeeInfos encontrados: " + greenFeesInfo.size())
						
		def includeYesterday = (clockService.isToday(desde) ? "NO" : "YES");
		String yesterdayFrom = clockService.formatted(desde.plusDays(-1));
		String tomorrowFrom = clockService.formatted(desde.plusDays(1));
		
		def model = [	greenFeesInfo: greenFeesInfo, formattedDate: formattedDate, ubicacionSplitted: ubicacionSplitted[1],
						includeYesterday: includeYesterday, nombreUbicacion: nombreUbicacion,
						yesterdayFrom: yesterdayFrom, tomorrowFrom: tomorrowFrom, diaEnTexto: diaEnTexto, ubicaciones: ubicaciones,
						precioMax: precioMax]
		
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "greenFees", model: model)
		}
		else {
			log.info("No AJAX ...")
			model
		}

		
	}

	
}
