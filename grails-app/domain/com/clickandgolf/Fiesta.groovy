package com.clickandgolf

import org.joda.time.DateTime

/**
 * Clase de dominio que solo indica las fechas que son fiestas para una cierta ubicacion
 * 
 * @author gonznic
 */
class Fiesta {

	DateTime fecha
	Ubicacion ubicacion // Si la ubicacion es null => Es xa todas las ubicaciones (feriados generales de Espania)
	Integer anio // El anio es necesario
	
    static constraints = {
		fecha nullable: false
		ubicacion nullable: true
		anio (nullable: false, min: 2012, max: 2050)
    }
	
	static mapping = {
		cache 'read-only'
	}
	
	
}
