package com.clickandgolf

import grails.test.mixin.*
import org.junit.*
import org.joda.time.DateTime

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(GreenFee)
class GreenFeeTests {

    void testBuild() {
		GreenFeeTemplate template = new GreenFeeTemplate(	campo: new Campo(nombre: "campoA"), cantidadDiasGenerar: 30, descuento: 13,  
															golfistasMinimo: 2, hastaFechaGenerada: new DateTime().minusDays(2), info: "info",
															precio: BigDecimal.TEN, precioOriginal: BigDecimal.TEN )
		
		assert template != null
		
		DateTime aux = new DateTime()
		
		def greenFee = GreenFee.build(template, aux, new BigDecimal(12))
		assert greenFee != null
		assert aux == greenFee.diaHora
		assert template.descuento == greenFee.descuento 
		assert template.golfistasMinimo == greenFee.golfistasMinimo
		assert template.info == greenFee.info
		assert template.precio == greenFee.precio
		assert template.precioOriginal == greenFee.precioOriginal
		
		log.info("testBuild - OK")
    }
}
