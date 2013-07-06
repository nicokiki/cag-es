package com.clickandgolf



import grails.test.mixin.*
import org.junit.*
import grails.test.*
import org.joda.time.DateTime;

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(GreenFeeTemplate)
class GreenFeeTemplateTests {

	void testEsTipoFestivo() {
		def obj = new GreenFeeTemplate()
		
		boolean res = obj.esTipoFestivo()
		assertEquals false, res
		
		obj.tipo = GreenFeeTemplate.LUNES
		res = obj.esTipoFestivo()
		assertEquals false, res

		obj.tipo = GreenFeeTemplate.FESTIVO
		res = obj.esTipoFestivo()
		assertEquals true, res

		log.info("testEsTipoFestivo - OK")
	}
	
	void testExcluirOk() {
		def obj = new GreenFeeTemplate()
		obj.excluir = ",14/10/2013, 15/11/2013,19/11/2013, 12/13/2013, 13:01/2014"
		
		def lista = obj.diasAExcluir()
		assertNotNull lista
		assertEquals 3, lista.size()
		assert lista[0].dayOfMonth == 14
		assert lista[0].monthOfYear == 10
		assert lista[0].year == 2013
		assert lista[1].dayOfMonth == 15
		assert lista[1].monthOfYear == 11
		assert lista[1].year == 2013
		assert lista[2].dayOfMonth == 19
		assert lista[2].monthOfYear == 11
		assert lista[2].year == 2013

		log.info("testExcluirOk - OK")
	}

	void testIncluirOk() {
		def obj = new GreenFeeTemplate()
		obj.incluir = ",14/10/2013, 15/11/2013,19/11/2013, 12/13/2013, 13:01/2014"
		
		def lista = obj.diasAIncluir()
		assertNotNull lista
		assertEquals 3, lista.size()
		assert lista[0].dayOfMonth == 14
		assert lista[0].monthOfYear == 10
		assert lista[0].year == 2013
		assert lista[1].dayOfMonth == 15
		assert lista[1].monthOfYear == 11
		assert lista[1].year == 2013
		assert lista[2].dayOfMonth == 19
		assert lista[2].monthOfYear == 11
		assert lista[2].year == 2013

		log.info("testIncluirConNull - OK")
	}

	
	void testIncluirEmpty() {
		def obj = new GreenFeeTemplate()
		obj.incluir = ""
		
		def lista = obj.diasAIncluir()
		assertNotNull lista
		
		log.info("testIncluirConNull - OK")
	}
	
	void testIncluirConNull() {
		def obj = new GreenFeeTemplate()
		obj.incluir = null
		
		def lista = obj.diasAIncluir()
		assertNotNull lista
		
		log.info("testIncluirConNull - OK")
	}
	
    void testHorariosEnMapConNullEmpty() {
		def obj = new GreenFeeTemplate()
		obj.horarios = null
		
		def lista = obj.horariosEnListaDeListas()
		assertNotNull lista
		
		// Ahora q tenga un string vacio
		obj = new GreenFeeTemplate()
		obj.horarios = ""
		
		lista = obj.horariosEnListaDeListas()
		assertNotNull lista
		assertEquals 0, lista.size()
		
		log.info("testHorariosEnMapConNullEmpty - OK") 
    }
	
	void testHorariosOk() {
		def obj = new GreenFeeTemplate()
		obj.horarios = "12:34, 12:48,13:5, 15:21,  12:67, 25:12, &3:43,13:00"
		
		def lista = obj.horariosEnListaDeListas()
		assertNotNull lista
		assert lista.size == 4
		assert lista[0] == [12,34]
		assert lista[1] == [12,48]
		assert lista[2] == [15,21]
		assert lista[3] == [13,0]
		
		log.info("testHorariosOk - OK")
	}

}
