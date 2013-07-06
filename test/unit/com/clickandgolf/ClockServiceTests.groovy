package com.clickandgolf


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ClockService)
class ClockServiceTests {

	
	
    void testSomething() {
		def clockService = new ClockService()
		def array = clockService.startEndDaysForOneWeek
		
		assert array != null
		assert array.size() == 2
		
        log.info("testSomething - OK")
    }
	
	void testFromFechaOk() {
		String fecha = "10/04/2013"
		
		def clockService = new ClockService()
		
		DateTime aux = clockService.fromStringConHoraActual(fecha);
		log.info("aux: " + aux)
		
		assert aux != null
		
        log.info("testFromFechaOk - OK")
	}
	
	
	
	void testA() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("EEEE dd MM yyyy");
		log.info(formatter.print(new DateTime()));
	}
	
}
