package com.clickandgolf

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.clickandgolf.ClockService

/**
 * No solo hace de servicio para fiestas, sino tambien para saber si es finde o no.<br>
 * 
 * @author gonznic
 */
class FechaService {
	
	static FINDES = [DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY]
	
	def clockService

	/**
	 * Devuelve una lista de fechas que son fiesta para la ubicacion en cuestion.
	 * La ubicacion NO puede ser null. No falla si lo fuera pero no deberia serlo
	 */
	def getFiestas = { Ubicacion paraUbicacion ->
		if (null == paraUbicacion) {
			log.warn("Se recibio una ubicacion nula. Analizar por que ha ocurrido esto ...")
			paraUbicacion = new Ubicacion()
		}
		DateTime hoy = clockService.ahora()
		def anioHoy = hoy.year().get()
		
		def query = Fiesta.where {
			(anio in [anioHoy, anioHoy+1] // El anio siguiente por si ocurre cualquier dia de diciembre asi incluye las de enero 
				&&
				(
					ubicacion == null
					||
					ubicacion == paraUbicacion
				)
			)
		}
		def response = query.list(sort:"fecha", order:"asc")
		return response
    }
	
	boolean isFiesta(List<Fiesta> fiestas, DateTime fecha) {
		if (null == fecha || null == fiestas || fiestas.isEmpty()) {
			return false
		}
		def response = false
		// Aca voy a tener en cuenta solo la fecha pasada al principio del dia cosa q la comparacion sea siempre la misma
		fiestas.each { fiesta ->
			if (sonIguales(fecha, fiesta.fecha)) {
//			if (fecha.toLocalDate().compareTo(fiesta.fecha.toLocalDate()) == 0) {
				response = true
				return true // es como hacer un break en un loop normal de java
			}
		}
		if (!response) {
			log.debug("La fecha '" + fecha + "' no es fiesta ...")
		}
		return response;
	}
	
	// TODO SACARLO!!!
	boolean isFinDeSemana(DateTime fecha) {
		if (null == fecha) {
			return false;
		}
		return (FINDES.contains(fecha.getDayOfWeek()))
	}
	
	boolean sonIguales(DateTime fecha1, DateTime fecha2) {
		return (fecha1.dayOfMonth == fecha2.dayOfMonth && fecha1.monthOfYear == fecha2.monthOfYear && fecha1.year == fecha2.year);
	}
	
	/**
	 * Devuelve true si el tipo de dia (LU, MA, etc) es el dia de la fecha en cuestion.
	 * Ejemplo: Es el '30/01/2013' un MIERCOLES?
	 * @param fecha
	 * @param tipo
	 * @return
	 */
	boolean esFechaLaDelTipo(DateTime fecha, Integer tipo) {
		if (null == fecha) {
			return false
		}
		return tipo == fecha.getDayOfWeek()
	}
	
	/**
	 * 
	 * @param fecha NO PUEDE ser null
	 * @param template NO PUEDE ser null
	 * @return
	 */
	boolean seExcluye(DateTime fecha, GreenFeeTemplate template) {
		def listaExcluir = template.diasAExcluir()
		
		for (excluir in listaExcluir) {
			if (sonIguales(fecha, excluir)) {
				return true
			}
		}
		// Si llego hasta aca es xq no se excluye
		return false
	}

	boolean seIncluye(DateTime fecha, GreenFeeTemplate template) {
		def listaExcluir = template.diasAIncluir()
		
		for (excluir in listaExcluir) {
			if (sonIguales(fecha, excluir)) {
				return true
			}
		}
		// Si llego hasta aca es xq no se incluye
		return false
	}

	/**
	 * Solo devolvera true cuando:
	 * <ul>
	 * 	<li>la fecha sea del mismo tipo q el template <b>Y alguno de lo q viene sea cierto</b>
	 * 		<ul>
	 * 			<li>el template no excluya esa fecha</li>
	 * 			<li>el template la incluya si fuera torneo</li>
	 * 		</ul>
	 *  </li>
	 * </ul
	 * @param fecha
	 * @param template NO PUEDE ser null
	 * @param fiestas
	 * @return
	 */
	boolean tenerEnCuenta(DateTime fecha, GreenFeeTemplate template, List<Fiesta> fiestas) {
		boolean response = false
		if (null == fecha) {
			return response
		}
		if (!template.esTipoFestivo() && esFechaLaDelTipo(fecha, template.tipo) && !isFiesta(fiestas, fecha)) {
			/* Si Normal => se podria llegar a excluir
			 * Si es Torneo => se tiene q haber incluido
			 */
			if (!template.esTorneo() && !seExcluye(fecha, template)) {
				response = true
			}
			else if (template.esTorneo() && seIncluye(fecha, template)) {
				response = true
			}
		}
		else if (template.esTipoFestivo() && isFiesta(fiestas, fecha)) {
			if (!template.esTorneo() && !seExcluye(fecha, template)) {
				response = true
			}
			else if (template.esTorneo() && seIncluye(fecha, template)) {
				response = true
			}
		}
		
		return response
	}
	
}
