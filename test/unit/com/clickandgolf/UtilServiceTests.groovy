package com.clickandgolf



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UtilService)
class UtilServiceTests {

    void testSomething() {
		def utilService = new UtilService()
		
		def a = null
		def b = null

		def criteria = utilService.parseUbicacion(a, b)
		assert criteria != null
		
		a = ""
		criteria = utilService.parseUbicacion(a, b)
		assert criteria != null
		
		b = ""
		criteria = utilService.parseUbicacion(a, b)
		assert criteria != null

		b = Campo.GOLF
		criteria = utilService.parseUbicacion(a, b)
		assert criteria != null

		b = Campo.PITCH_AND_PUTT
		criteria = utilService.parseUbicacion(a, b)
		assert criteria != null

		b = Campo.GOLF
		a = "region:Andalucia"
		criteria = utilService.parseUbicacionOld(a)
		assert criteria != null

		log.info("testSomething - OK")
    }
}
