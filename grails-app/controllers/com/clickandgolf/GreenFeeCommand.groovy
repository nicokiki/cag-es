/**
 * 
 */
package com.clickandgolf

import org.joda.time.DateTime;

import grails.validation.Validateable;
import com.clickandgolf.Campo;

/**
 * Es un Value Object que almacena datos de green fees indicando
 * el campo sobre el cual se requiere el green fee y la fecha y ademas los precios minimos en los horarios:
 * 
 * 7-10 // 10-13 // 13-16 // 16-19
 * 
 * @author gonznic
 */
@Validateable
class GreenFeeCommand {
	
	Campo campo
	// Me va a servir para luego recuperar la info mas adelante xq tendre que hacer otra consulta.
	String dia
	String mes
	String anio
	
	public BigDecimal precioRangoCero
	public BigDecimal precioRangoUno
	public BigDecimal precioRangoDos
	public BigDecimal precioRangoTres
	
	static constraints = {}
	
	def isRangoMenor = { BigDecimal valor, BigDecimal aComparar ->
		return (null == valor || aComparar.compareTo(valor) < 0)
	}

	public boolean isMenorQueRangoCero(BigDecimal aComparar) {
		return this.isRangoMenor(this.precioRangoCero, aComparar)
	}

	public boolean isMenorQueRangoUno(BigDecimal aComparar) {
		return this.isRangoMenor(this.precioRangoUno, aComparar)
	}
	
	public boolean isMenorQueRangoDos(BigDecimal aComparar) {
		return this.isRangoMenor(this.precioRangoDos, aComparar)
	}
	
	public boolean isMenorQueRangoTres(BigDecimal aComparar) {
		return this.isRangoMenor(this.precioRangoTres, aComparar)
	}

}
