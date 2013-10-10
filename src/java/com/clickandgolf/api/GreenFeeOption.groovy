/**
 * 
 */
package com.clickandgolf.api

import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import com.clickandgolf.GreenFeeCommand

/**
 * @author ngonzalez
 *
 */
class GreenFeeOption {

	String courseIdentifier
	String courseURI
	
	String day
	String month
	String year
	
	String detailedGreenFeesURI

	BigDecimal minPriceRangeZero
	BigDecimal minPriceRangeOne
	BigDecimal minPriceRangeTwo
	BigDecimal minPriceRangeThree


	static GreenFeeOption fromGreenFeeCommand(GreenFeeCommand greenFeeCommand, LinkGenerator grailsLinkGenerator) {
		def greenFeeOption = new GreenFeeOption(courseIdentifier: greenFeeCommand?.campo?.id,
									day: greenFeeCommand?.dia, month: greenFeeCommand?.mes, year: greenFeeCommand?.anio,
									minPriceRangeZero: greenFeeCommand?.precioRangoCero, minPriceRangeOne: greenFeeCommand?.precioRangoUno, minPriceRangeTwo: greenFeeCommand?.precioRangoDos,
									minPriceRangeThree: greenFeeCommand?.precioRangoTres)
		
		greenFeeOption.courseURI = grailsLinkGenerator.link(mapping: 'campo', params: [id:greenFeeCommand?.campo?.id, nombre:greenFeeCommand?.campo?.hyphenatedNombre])
		greenFeeOption.detailedGreenFeesURI = "/api/greenfee/course/" + greenFeeCommand?.campo?.id + "?dateAsText=" + greenFeeOption.day + "/" + greenFeeOption.month + "/" + greenFeeOption.year  
		
		return greenFeeOption; 
	}
	
}
