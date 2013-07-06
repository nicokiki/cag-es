package clickandgolf

import static org.junit.Assert.*

//import grails.test.mixin.*
import grails.test.mixin.support.*
//import org.junit.*

import org.joda.time.DateTime;
import org.joda.time.LocalDate
import org.joda.time.Days


/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class TestNicoTests {

	
	
    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testSomething() {
		DateTime hoy = new DateTime();
		DateTime hoyComienzo = hoy.toDateMidnight().toInterval().getStart();

		DateTime hastaFechaGenerada = new DateTime(2012, 12, 8, 16, 7)
		DateTime hastaFechaGeneradaComienzo = hastaFechaGenerada.toDateMidnight().toInterval().getStart();
		
		log.info("hoyComienzo: " + hoyComienzo)
		log.info("hoy: " + hoy)
		log.info("hastaFechaGeneradaComienzo: " + hastaFechaGeneradaComienzo)
		log.info("hastaFechaGenerada: " + hastaFechaGenerada)
		
		Days dias = Days.daysBetween(hoyComienzo, hastaFechaGeneradaComienzo)
		log.info("dias.getDays(): " + dias.getDays())
		
		
		
		assert true
		
//		def map = [:]
//		def horarios = "12:34, 12:48,13:5, 15:21,  12:67, 25:12, &3:43"
//		horarios.split(",").each() { param ->
//			def horaMinuto = param.trim()
//			if (null == horaMinuto || horaMinuto.size() != 5) {
//				log.warn("La hora-minuto '" + horaMinuto + "' almacenada en horarios se descarta porque no cumple con hh:mm")
//			}
//			else {
//				def nameAndValue = horaMinuto.split(":")
//				try {
//					def hora = nameAndValue[0].toInteger()
//					def minuto = nameAndValue[1].toInteger()
//					if (hora > 0 && hora <= 24 && minuto > 0 && minuto <= 59 ) {
//						map[hora] = minuto
//					}
//					else {
//						log.warn("La hora-minuto '" + horaMinuto + "' no es valida ...")
//					}
//					
//				}
//				catch (NumberFormatException nfe) {
//					log.warn("La hora-minuto '" + horaMinuto + "' no es valida ...")
//				}
//			}
//		}
//		assert true 
    }
}
