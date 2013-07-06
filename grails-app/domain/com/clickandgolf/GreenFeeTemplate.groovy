package com.clickandgolf

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

/**
 * Clase de template para armar los GreenFees (NO tiene ni controller ni view de manera q se guarda "a mano" en la BD).<br>
 * 
 * La idea es que cada Campo tendra asociada una serie de templates q generaran automaticamente los green fees en base
 * a los valores aqui guardados (tipo = FESTIVO/LABORABLE/FIESTA).<br> 
 * 
 * Los jobs que se ejecutan no lo haran a diario, sino cada un par de dias xa minimizar la cantidad de inserts a realizar.
 * De esta manera, se necesita metadata xa saber que y hasta cuando generar. Tambien se soluciona el hecho de generar mensualmente 
 * y q el 29 de un mes solo se pueda ver hasta el 30 ...
 * 
 * @author gonznic
 */
class GreenFeeTemplate {
	
	static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy").withZone(DateTimeZone.forID("Europe/Madrid"));
	
	static final LUNES = 1
	static final MARTES = 2
	static final MIERCOLES = 3
	static final JUEVES = 4
	static final VIERNES = 5
	static final SABADO = 6
	static final DOMINGO = 7
	static final FESTIVO = 100
	
	static final TIPOS_POSIBLES = [LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO, FESTIVO]
	
	static final NORMAL = GreenFee.NORMAL
	static final TORNEO = GreenFee.TORNEO
	
	static final HOYOS_18 = GreenFee.HOYOS_18
	static final HOYOS_9 = GreenFee.HOYOS_9
	
	// Campo
	Campo campo
	
	/**
	 * Es la fecha hasta la cual se generaron los green-fees para este template en particular.<br>
	 * 
	 * <ul>
	 * 	<li>Si la fecha es anterior a hoy, significa que hay que generar si o si por los proximos 'cantidadDiasGenerar' dias. Esto es para la primera vez q se ejecute.</li> 
	 * 	<li>Si la fecha es a futuro, entonces solo se generaran si desde 'hoy' a 'hastaFechaGenerada' hay menos de 'cantidadDiasGenerar' dias.<br>
	 * 		Ejemplo:
	 * 			<ul> 
	 * 				<li>'hoy':14/11/2012</li>
	 * 				<li>'hastaFechaGenerada':08/12/2012</li>
	 * 				<li>'cantidadDiasGenerar':30</li>
	 * 			</ul>
	 * 			Desde 'hoy' a 'hastaFechaGenerada' hay 24 dias. 24 < 30 => se generan 6 (=30-24) nuevas fechas:
	 * 				09/12, 10/12, 11/12, 12/12, 13/12, 14/12 
	 * 	</li>
	 * </ul>
	 */
	DateTime hastaFechaGenerada
	Integer cantidadDiasGenerar = 30

	/**
	 * Es una lista de horarios separados por coma con <b>hh:mm</b><br>
	 * ie: <code>10:04,10:24,12:32</code>
	 */
	String horarios
	String info
	
	// El precio es por golfista / El fee es por golfista
	BigDecimal precio
	BigDecimal precioOriginal
	Integer descuento
	
	/**
	 * 1=Se puede reservar de a uno
	 * 2=Se puede reservar de a dos
	 * 4=Se puede reservar todo junto nada mas
	 */
	Integer golfistasMinimo
	/**
	 * Cantidad de green fees disponibles (Valores posibles: 1, 2, 3, 4 / '0' no xq el estado no deberia ser ACTIVO, PARCIAL)
	 */
	Integer disponibles
	
	Integer tipo
	Integer tipoExtra = GreenFeeTemplate.NORMAL // por defecto
	
	/**
	 * Para tipoExtra = NORMAL puede q haya dias a excluir xq caen torneos ese mismo dia.
	 * Es una lista separada por ',' con fechas en este formato solamente: dd/MM/yyyy
	 */
	String excluir
	/**
	 * Para tipoExtra = TORNEO TIENE q haber dias a incluir q no importa si son festivos etc.
	 * Es una lista separada por ',' con fechas en este formato solamente: dd/MM/yyyy
	 */
	String incluir
	
	Integer hoyos = HOYOS_18 // Por defecto
	
	
	static constraints = {
		campo nullable: false
		hastaFechaGenerada (nullable: false)
		cantidadDiasGenerar (nullable: false,  min: 10, max: 30)
		
		horarios nullable: false, maxSize: 1000

		info blank: false, maxSize: 100
		
		precio (nullable: false, min: 5.0, max: 999.99, scale: 2)
		precioOriginal (nullable: false, min: 5.0, max: 999.99, scale: 2)
		descuento (nullable: false, min: 0, max: 100)
		
		golfistasMinimo (nullable: false, inList: [1, 2, 4])
		disponibles (nullable: false, min: 1, max: 4)
		
		tipo inList: TIPOS_POSIBLES
		tipoExtra inList: [GreenFeeTemplate.NORMAL, GreenFeeTemplate.TORNEO]
		
		excluir(nullable: true, maxSize: 1000)
		incluir(nullable: true, maxSize: 1000)
		hoyos inList: [GreenFeeTemplate.HOYOS_18, GreenFeeTemplate.HOYOS_9]
	}
	
	boolean is18Hoyos() {
		return hoyos == GreenFeeTemplate.HOYOS_18
	}
	
	def diasAIncluir = {
		return diasAUtilizar(incluir)
	}
	
	def diasAExcluir = {
		return diasAUtilizar(excluir)
	}
	
	def diasAUtilizar = { dias ->
		def response = []
		if (null == dias) {
			return response
		}
		dias.split(",").each() { dia ->
			def diaAux = dia.trim()
			// Intento convertirlo e ignoro los q esten mal
			try {
				response.add(formatter.parseDateTime(diaAux))
			}
			catch (Exception e) {
				log.info("Problemas al convertir la fecha ${diaAux} a incluir/excluir. No respeta el formato? (dd/MM/yyy). Se ignora. Mensaje: ${e}")	
			}
		}
		
		return response
	}
	
	/**
	 * @return Devuelve un array de arrays [hora,minuto] a ser utilizados en base a this.horario / Los "malos" los descarta loggeando los problemas<br>
	 * Ejemplo: [[12,34],[14,17],[15,05]]
	 */
	def horariosEnListaDeListas = {
		def response = []
		if (null == horarios) {
			log.warn("No hay horarios guardados para el campo '" + campo?.nombre + "'. Analizar si la informacion es correcta en la BD ...")
			return response
		}
		horarios.split(",").each() { param ->
			def horaMinuto = param.trim()
			if (null == horaMinuto || horaMinuto.size() != 5) {
				log.info("La hora-minuto '" + horaMinuto + "' para el campo '" + campo?.nombre + "' almacenada en horarios se descarta porque no cumple con 'hh:mm' ...")
			}
			else {
				def nameAndValue = horaMinuto.split(":")
				try {
					def hora = nameAndValue[0].toInteger()
					def minuto = nameAndValue[1].toInteger()
					if (hora > 0 && hora <= 24 && minuto >= 0 && minuto <= 59 ) {
						response.add([hora, minuto])
					}
					else {
						log.info("La hora-minuto '" + horaMinuto + "' para el campo '" + campo?.nombre + "' no es valida ...")
					}
					
				}
				catch (NumberFormatException nfe) {
					log.info("La hora-minuto '" + horaMinuto + "' para el campo '" + campo?.nombre + "' no es valida ...")
				}
			}
		}
		return response
	}
	
	boolean esTipoFestivo() {
		return this.tipo == GreenFeeTemplate.FESTIVO;	
	}
	
	boolean esTorneo() {
		return this.tipoExtra == GreenFeeTemplate.TORNEO
	}
	
}
