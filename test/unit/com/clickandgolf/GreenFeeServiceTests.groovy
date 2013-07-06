package com.clickandgolf

import grails.test.mixin.*
import org.junit.*

import org.joda.time.DateTime;
import java.math.BigDecimal
import java.math.MathContext

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(GreenFeeService)
class GreenFeeServiceTests {

//	void testDecorateGreenFeeInfoDevuelveAlgo() {
//		def greenFees = new ArrayList<GreenFee>()
//		greenFees.add(new GreenFee(campo: new Campo(nombre:'Golf A'), diaHora: new DateTime().withTime(9, 0, 0, 0), precio: new BigDecimal("20")))
//		greenFees.add(new GreenFee(campo: new Campo(nombre:'Golf A'), diaHora: new DateTime().withTime(9, 15, 0, 0), precio: new BigDecimal("30")))
//		greenFees.add(new GreenFee(campo: new Campo(nombre:'Golf A'), diaHora: new DateTime().withTime(9, 30, 0, 0), precio: new BigDecimal("30")))
//		greenFees.add(new GreenFee(campo: new Campo(nombre:'Golf A'), diaHora: new DateTime().withTime(11, 0, 0, 0), precio: new BigDecimal("40")))
//		greenFees.add(new GreenFee(campo: new Campo(nombre:'Golf A'), diaHora: new DateTime().withTime(14, 10, 0, 0), precio: new BigDecimal("50")))
//		greenFees.add(new GreenFee(campo: new Campo(nombre:'Golf A'), diaHora: new DateTime().withTime(14, 30, 0, 0), precio: new BigDecimal("45")))
//		greenFees.add(new GreenFee(campo: new Campo(nombre:'Golf A'), diaHora: new DateTime().withTime(17, 0, 0, 0), precio: new BigDecimal("20")))
//
//		greenFees.add(new GreenFee(campo: new Campo(nombre:'Golf B'), diaHora: new DateTime().withTime(9, 0, 0, 0), precio: new BigDecimal("45")))
//		greenFees.add(new GreenFee(campo: new Campo(nombre:'Golf B'), diaHora: new DateTime().withTime(15, 0, 0, 0), precio: new BigDecimal("50")))
//		greenFees.add(new GreenFee(campo: new Campo(nombre:'Golf B'), diaHora: new DateTime().withTime(15, 20, 0, 0), precio: new BigDecimal("55")))
//		
//		assert greenFees != null
//		def fecha = new DateTime()
//		
//		def service = new GreenFeeService()
//		def response = service.decorateGreenFeeInfo(greenFees, fecha)
//		
//		assert response != null
//		assert response.size == 2
//		
//		log.info("testDecorateGreenFeeInfoDevuelveAlgo - OK")
//	}
//
//	
//	void testDecorateGreenFeeInfoNulls() {
//		def greenFees = null
//		assert greenFees == null
//		def fecha = null
//		assert fecha == null
//		
//		def service = new GreenFeeService()
//		def response = service.decorateGreenFeeInfo(greenFees, fecha)
//		
//		assert response != null
//		
//		log.info("testDecorateGreenFeeInfoNulls - OK")
//	}
	
    void testNull() {
		def lista = new GreenFeeService().collectDatesToCreate(null, null)
		assertNotNull lista
		
        log.info("testNull - OK")
    }

	void testReCalculateValuesNormalOk() {
		def service = new GreenFeeService()
		
		def mathCtx = new MathContext(2)
		
		def campo = new Campo(fee: BigDecimal.TEN)
		def greenFee = new GreenFee(precio: new BigDecimal(8, mathCtx), precioOriginal: new BigDecimal(9, mathCtx), campo: campo)
		assert greenFee != null
		
		def modificado = service.reCalculateValues(greenFee)
		assert modificado != null
		// Precio y original no se modifican / quedan tal cual estaban
		assert modificado.precio.compareTo(new BigDecimal(8, mathCtx))  == 0
		assert modificado.precioOriginal.compareTo(new BigDecimal(9, mathCtx))  == 0
		
		assert modificado.descuento != null
		assert modificado.descuento == 12
		
		assert modificado.fee != null
		assert modificado.fee.compareTo(new BigDecimal(0.8, new MathContext(2))) == 0
		
		log.info("testReCalculateValuesNormalOk - OK")
	}

	
	void testReCalculateValuesPrecioMayorQueOriginalOk() {
		def service = new GreenFeeService()
		
		def mathCtx = new MathContext(2)
		
		def campo = new Campo(fee: BigDecimal.TEN)
		def greenFee = new GreenFee(precio: new BigDecimal(10, mathCtx), precioOriginal: new BigDecimal(9, mathCtx), campo: campo)
		assert greenFee != null		
		
		def modificado = service.reCalculateValues(greenFee)
		assert modificado != null
		// Precio y original no se modifican / quedan tal cual estaban
		assert modificado.precio.compareTo(new BigDecimal(10, mathCtx))  == 0
		assert modificado.precioOriginal.compareTo(new BigDecimal(10, mathCtx))  == 0
		
		assert modificado.descuento != null
		assert modificado.descuento == 0
		
		assert modificado.fee != null
		assert modificado.fee.compareTo(new BigDecimal(1, new MathContext(2))) == 0
		
		log.info("testReCalculateValuesPrecioMayorQueOriginalOk - OK")
	}

		
	void testReCalculateValuesNullsOk() {
		def service = new GreenFeeService()
		def greenFee = null
		
		assert greenFee == null
		
		def modificado = service.reCalculateValues(greenFee)
		assert modificado == null
		
		log.info("testReCalculateValuesNullsOk - OK")
	}

	
	void testCalculateDescuentoOk() {
		def service = new GreenFeeService()
		
		def mathCtx = new MathContext(2)
		BigDecimal precio = new BigDecimal(80, mathCtx)
		BigDecimal precioOriginal = new BigDecimal(100, mathCtx)
		
		assert precio.compareTo(new BigDecimal(80, mathCtx)) == 0
		assert precioOriginal.compareTo(new BigDecimal(100, mathCtx)) == 0
		
		Integer calculado = service.calculateDescuento(precio, precioOriginal)
		assert calculado != null
		assert calculado == 20

		// Redondea siempre xa arriba
		precio = new BigDecimal(23, mathCtx)
		precioOriginal = new BigDecimal(51, mathCtx)
		
		calculado = service.calculateDescuento(precio, precioOriginal)
		assert calculado != null
		assert calculado == 55

				
		log.info("testCalculateDescuentoNullsOk - OK")
	}

	
	void testCalculateDescuentoNullsOk() {
		def service = new GreenFeeService()
		
		BigDecimal precio = null
		BigDecimal precioOriginal = null
		
		assert precio == null
		assert precioOriginal == null
		
		Integer calculado = service.calculateDescuento(precio, precioOriginal)
		assert calculado != null
		assert calculado == 100
		
		log.info("testCalculateDescuentoNullsOk - OK")
	}
	
	void testCalculateFeeOk() {
		def service = new GreenFeeService()
		
		BigDecimal precio = BigDecimal.TEN
		BigDecimal feePorcentaje = new BigDecimal(2)
		
		assert precio != null
		assert feePorcentaje != null
		
		def calculado = service.calculateFee(precio, feePorcentaje)
		assert calculado != null
		assert calculado.compareTo(new BigDecimal(0.2, new MathContext(2))) == 0

		log.info("testCalculateFeeOk - OK")
	}
	
		
	void testCalculateFeeNullsOk() {
		def service = new GreenFeeService()
		
		BigDecimal precio = null
		BigDecimal feePorcentaje = null
		
		assert precio == null
		assert feePorcentaje == null
		
		def calculado = service.calculateFee(precio, feePorcentaje)
		assert calculado != null
		assert calculado.compareTo(BigDecimal.ZERO) == 0
		
		// Ahora precio es un valor X
		precio = BigDecimal.TEN
		assert precio != null
		calculado = service.calculateFee(precio, feePorcentaje)
		assert calculado != null
		assert calculado.compareTo(BigDecimal.ZERO) == 0
		
		// Ahora precio es null de nuevo y feePorcentaje es X
		precio = null
		feePorcentaje = BigDecimal.TEN
		assert precio == null
		assert feePorcentaje != null
		
		calculado = service.calculateFee(precio, feePorcentaje)
		assert calculado != null
		assert calculado.compareTo(BigDecimal.ZERO) == 0
		
		log.info("testCalculateFeeNullsOk - OK")
	}
	
	
//	void testNuncaGenerado() {
//		// El test lo voy a comentar xq "ahora" cambia dia a dia y no voy a hacer un interface tipo Clock para mockearlo! Lo pruebo el dia q se como vienen las cosas!
//		// laborable
//		def template = new GreenFeeTemplate(cantidadDiasGenerar: 3, hastaFechaGenerada: new DateTime().minusDays(2), horarios: "12:34, 16:03", tipo: GreenFeeTemplate.LABORABLE)
//		
//		def fiestas = []
//		
//		def greenFeeService = new GreenFeeService()
//		greenFeeService.fiestaService = new FiestaService()
//		def lista = greenFeeService.collectDatesToCreate(template, fiestas)
//		assertNotNull lista
//		assert lista.size == 6
//		
//		DateTime nuevaFechaGenerada = new DateTime().plusDays(2) // Es hoy, maniana y pasado (los 3 dias de 'cantidadDiasGenerar')
//		DateTime nuevaFechaGeneradaComienzo = nuevaFechaGenerada.toDateMidnight().toInterval().getStart();
//		
//		DateTime hastaFechaGeneradaAux = template.hastaFechaGenerada
//		assertNotNull hastaFechaGeneradaAux
//		
//		assert nuevaFechaGeneradaComienzo.year().get() == hastaFechaGeneradaAux.year().get()
//		assert nuevaFechaGeneradaComienzo.monthOfYear().get() == hastaFechaGeneradaAux.monthOfYear().get()
//		assert nuevaFechaGeneradaComienzo.dayOfMonth().get() == hastaFechaGeneradaAux.dayOfMonth().get()
//		
//		lista.each { elemento ->  
//			log.info("Fecha generada: '" + elemento + "' ...")
//		}
//		log.info("Nueva hastaFechaGenerada: '" + hastaFechaGeneradaAux + "' ...")
//
//		// Ahora pruebo lo mismo pero con fiestas
//		template = new GreenFeeTemplate(cantidadDiasGenerar: 3, hastaFechaGenerada: new DateTime().minusDays(2), horarios: "12:34, 16:03", tipo: GreenFeeTemplate.LABORABLE)
//		
//		fiestas.add(new Fiesta(fecha: new DateTime(), anio: 2012)) // hoy sera fiesta entonces no lo deberia generar xq el template es xa LABORABLES
//		
//		lista = greenFeeService.collectDatesToCreate(template, fiestas)
//		assertNotNull lista
//		assert lista.size == 4
//			
//		log.info("testNuncaGenerado - OK")
//	}
//	
//	void testGenerarFiestasOk() {
//		// Deberia generar 9 mas de los q ya hay!
//		def template = new GreenFeeTemplate(cantidadDiasGenerar: 10, hastaFechaGenerada: new DateTime().plusDays(1), horarios: "12:34", tipo: GreenFeeTemplate.FIESTA)
//
//		// En 2 dias hay una fiesta y no cae finde, en 5 dias hay y cae domingo
//		def fiestas = [new Fiesta(fecha: new DateTime().plusDays(2)), new Fiesta(fecha: new DateTime().plusDays(5))]
//		
//		def greenFeeService = new GreenFeeService()
//		greenFeeService.fiestaService = new FiestaService()
//		def lista = greenFeeService.collectDatesToCreate(template, fiestas)
//		assertNotNull lista
//		assert lista.size == 2
//		
//		DateTime nuevaFechaGenerada = new DateTime().plusDays(10)
//		DateTime nuevaFechaGeneradaComienzo = nuevaFechaGenerada.toDateMidnight().toInterval().getStart();
//		
//		DateTime hastaFechaGeneradaAux = template.hastaFechaGenerada
//		assertNotNull hastaFechaGeneradaAux
//		
//		assert nuevaFechaGeneradaComienzo.year().get() == hastaFechaGeneradaAux.year().get()
//		assert nuevaFechaGeneradaComienzo.monthOfYear().get() == hastaFechaGeneradaAux.monthOfYear().get()
//		assert nuevaFechaGeneradaComienzo.dayOfMonth().get() == hastaFechaGeneradaAux.dayOfMonth().get()
//		
//		log.info("testGenerarFiestasOk - OK")
//	}
//	
//	void testGenerarMas() {
//		// laborable. Genero hasta maniana y tiene q generar 3 => generara dos dias mas!
//		def template = new GreenFeeTemplate(cantidadDiasGenerar: 3, hastaFechaGenerada: new DateTime().plusDays(1), horarios: "12:34, 16:03, 17:22", tipo: GreenFeeTemplate.LABORABLE)
//		
//		// no hay fiestas
//		def fiestas = []
//		
//		def greenFeeService = new GreenFeeService()
//		greenFeeService.fiestaService = new FiestaService()
//		def lista = greenFeeService.collectDatesToCreate(template, fiestas)
//		assertNotNull lista
//		assert lista.size == 6
//		
//		DateTime nuevaFechaGenerada = new DateTime().plusDays(3)
//		DateTime nuevaFechaGeneradaComienzo = nuevaFechaGenerada.toDateMidnight().toInterval().getStart();
//		
//		DateTime hastaFechaGeneradaAux = template.hastaFechaGenerada
//		assertNotNull hastaFechaGeneradaAux
//		
//		assert nuevaFechaGeneradaComienzo.year().get() == hastaFechaGeneradaAux.year().get()
//		assert nuevaFechaGeneradaComienzo.monthOfYear().get() == hastaFechaGeneradaAux.monthOfYear().get()
//		assert nuevaFechaGeneradaComienzo.dayOfMonth().get() == hastaFechaGeneradaAux.dayOfMonth().get()
//		
//		lista.each { elemento ->
//			log.info("Fecha generada: '" + elemento + "' ...")
//		}
//		log.info("Nueva hastaFechaGenerada: '" + hastaFechaGeneradaAux + "' ...")
//
//		
//		// Ahora le voy a pedir q genere 2 dias mas pero solo si es finde y no lo va a ser entonces no generara nada!
//		DateTime hastaGeneradaAux = new DateTime().plusDays(1)
//		template = new GreenFeeTemplate(cantidadDiasGenerar: 3, hastaFechaGenerada: new DateTime(hastaGeneradaAux), horarios: "12:34, 16:03, 17:22", tipo: GreenFeeTemplate.FESTIVO)
//		
//		// no hay fiestas
//		lista = greenFeeService.collectDatesToCreate(template, fiestas)
//		assertNotNull lista
//		assert lista.size == 0
//		// la generada se modifica de todas maneras
//
//		nuevaFechaGenerada = new DateTime().plusDays(3)
//		nuevaFechaGeneradaComienzo = nuevaFechaGenerada.toDateMidnight().toInterval().getStart();
//		
//		hastaFechaGeneradaAux = template.hastaFechaGenerada
//		assertNotNull hastaFechaGeneradaAux
//		
//		assert nuevaFechaGeneradaComienzo.year().get() == hastaFechaGeneradaAux.year().get()
//		assert nuevaFechaGeneradaComienzo.monthOfYear().get() == hastaFechaGeneradaAux.monthOfYear().get()
//		assert nuevaFechaGeneradaComienzo.dayOfMonth().get() == hastaFechaGeneradaAux.dayOfMonth().get()
//
//				
//		log.info("testGenerarMas - OK")
//	}

}
