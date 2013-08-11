/**
 * 
 */
package com.clickandgolf.api

import java.math.BigDecimal;

import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import org.joda.time.DateTime;


/**
 * @author ngonzalez
 *
 */
class GreenFee {

	String courseIdentifier
	String courseURI
	
	String hourMinute
	String info
	Integer holes
	BigDecimal price
	Integer discount
	Integer available

	static GreenFee fromGreenFee(com.clickandgolf.GreenFee greenFeeOriginal, LinkGenerator grailsLinkGenerator) {
		def greenFee = new GreenFee(courseIdentifier: greenFeeOriginal?.campo?.id,
									hourMinute: greenFeeOriginal?.getHoraMinuto(),
									info: greenFeeOriginal?.info, 
									holes: greenFeeOriginal?.hoyos,
									price: greenFeeOriginal?.precio,
									discount: greenFeeOriginal?.descuento,
									available: greenFeeOriginal?.disponibles)
		
		greenFee.courseURI = grailsLinkGenerator.link(mapping: 'campo', params: [id:greenFeeOriginal?.campo?.id, nombre:greenFeeOriginal?.campo?.hyphenatedNombre])
		
		return greenFee; 
	}
	
}
