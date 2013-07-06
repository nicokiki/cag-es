/**
 * 
 */
package com.clickandgolf

import java.math.BigDecimal;

import org.joda.time.DateTime;

import grails.validation.Validateable;

/**
 * @author gonzalez
 *
 */
@Validateable
class NewGreenFeeCommand {

	String fecha
	Integer hora
	Integer minuto
	
	// El precio es por golfista / El fee es por golfista
	BigDecimal precio
	BigDecimal precioOriginal
	
	/**
	 * 1=Se puede reservar de a uno
	 * 2=Se puede reservar de a dos
	 * 4=Se puede reservar todo junto nada mas
	 */
	Integer golfistasMinimo
	/**
	 * Cantidad de green fees disponibles (Valores posibles: 1, 2, 3, 4 / '0' puede ser xa INACTIVO o RESERVADO)
	 */
	Integer disponibles
	
	String hoyos
	String info

	
	static constraints = {
		fecha nullable: false
		
		hora (nullable: false, min: 7, max: 19)
		minuto (nullable: false, min: 0, max: 59)
		
		precio (nullable: false, min: 5.0, max: 999.99, scale: 2)
		precioOriginal (nullable: false, min: 5.0, max: 999.99, scale: 2)
		golfistasMinimo (nullable: false, inList: [1, 2, 4])
		disponibles (nullable: false, min: 0, max: 4, validator:{valor, obj ->
			if (valor < obj.golfistasMinimo) {
				return 'menor.que.minimos'
			}
		})
		hoyos inList: ["18", "9"]
		info blank: false, maxSize: 35
	}
	
	GreenFee createGreenFee(Campo campo, DateTime diaHora) {
		GreenFee resp = new GreenFee();
		// El descuento por ahora no se calcula. Se llamara luego a recalc ...
		int tipoHoyos = GreenFee.HOYOS_18
		if ("9".equals(hoyos)) {
			tipoHoyos = GreenFee.HOYOS_9
		}
		BigDecimal feeCalculado = (precio.multiply(campo.fee)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		return new GreenFee(campo: campo, diaHora: diaHora, info: info,
							precio: precio, precioOriginal: precioOriginal, fee: feeCalculado, 
							golfistasMinimo: golfistasMinimo, disponibles: disponibles,
							hoyos: tipoHoyos)
		
		return resp;
	}

}
