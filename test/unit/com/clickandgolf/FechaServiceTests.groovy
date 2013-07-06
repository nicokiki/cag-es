package com.clickandgolf


import grails.test.mixin.*
import org.joda.time.DateTime;
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(FechaService)
class FechaServiceTests {

	void testTenerEnCuentaSinFiestasOK() {
		def fechaService = new FechaService()
		
		def fiestas = []
		assert fiestas != null
		assert fiestas.size() == 0
		
		// Es NORMAL y NO torneo x defecto
		def template = new GreenFeeTemplate(tipo: GreenFeeTemplate.LUNES)
		assert template != null
		assert template.tipo != GreenFeeTemplate.FESTIVO
		
		String fecha = "30/01/2013" // Miercoles
		def clockService = new ClockService()
		DateTime aux = clockService.fromStringConHoraActual(fecha);
		
		def res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == false

		fecha = "31/01/2013" // Jueves
		aux = clockService.fromStringConHoraActual(fecha);
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == false

		fecha = "04/02/2013" // Es lunes :-)
		aux = clockService.fromStringConHoraActual(fecha);
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == true
		
		// Ahora voy a excluir esa misma fecha en el template
		template.excluir = "04/02/2013, 11/03/2013" // Excluyo esos DOS lunes de febrero y marzo
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == false
		
		// Ahora no se excluyen otras fechas q no son las mismas
		template.excluir = "11/03/2013" // Excluyo un lunes de marzo q no es la fecha en cuestion
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == true

		// Ahora no excluyo pero sera fiesta
		template.excluir = ""
		fiestas.add(new Fiesta(fecha: aux))
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == false
		
		// Ahora testeo de tipo TORNEO SIN fiestas
		template.tipoExtra = GreenFeeTemplate.TORNEO
		template.incluir = "04/02/2013, 11/03/2013" // Solo estos dos son los usados
		fiestas = [] 
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == true

		// Ahora testeo de tipo TORNEO SIN fiestas pero NO esta en la lista de incluidos
		template.tipoExtra = GreenFeeTemplate.TORNEO
		template.incluir = "" // Solo estos dos son los usados
		fiestas = []
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == false

		// Ahora testeo de tipo TORNEO CON fiestas
		template.tipoExtra = GreenFeeTemplate.TORNEO
		template.incluir = "04/02/2013, 11/03/2013" // Solo estos dos son los usados
		fiestas.add(new Fiesta(fecha: aux))
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == false
		
		// Ahora xa los festivos
		// Ahora voy a poner de tipo feriado/NORMAL/SIN-EXCLUSION/SIN-Fiestas
		template.tipo = GreenFeeTemplate.FESTIVO
		template.tipoExtra = GreenFeeTemplate.NORMAL
		template.excluir = ""
		fiestas = []
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == false

		// Ahora voy a poner de tipo feriado/NORMAL/SIN-EXCLUSION/CON-Fiestas
		template.tipo = GreenFeeTemplate.FESTIVO
		template.tipoExtra = GreenFeeTemplate.NORMAL
		template.excluir = ""
		fiestas.add(new Fiesta(fecha: aux))
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == true

		// Ahora voy a poner de tipo feriado/NORMAL/CON-EXCLUSION/CON-Fiestas
		template.tipo = GreenFeeTemplate.FESTIVO
		template.tipoExtra = GreenFeeTemplate.NORMAL
		template.excluir = "04/02/2013, 11/03/2013"
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == false

		// Ahora voy a poner de tipo feriado/TORNEO/CON-INCLUSION-VACIA/CON-Fiestas
		template.tipo = GreenFeeTemplate.FESTIVO
		template.tipoExtra = GreenFeeTemplate.TORNEO
		template.excluir = ""
		template.incluir = ""
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == false

		// Ahora voy a poner de tipo feriado/TORNEO/CON-INCLUSION-BIEN/CON-Fiestas
		template.tipo = GreenFeeTemplate.FESTIVO
		template.tipoExtra = GreenFeeTemplate.TORNEO
		template.excluir = ""
		template.incluir = "04/02/2013, 11/03/2013"
		res = fechaService.tenerEnCuenta(aux, template, fiestas)
		assert res == true

		log.info("testTenerEnCuentaNulls - OK")
	}

	
	void testTenerEnCuentaNulls() {
		def fechaService = new FechaService()
		
		def res = fechaService.tenerEnCuenta(null, null, null)
		assert res == false

		log.info("testTenerEnCuentaNulls - OK")		
	}
	
	void testEsFechaLaDelTipoOk() {
		def clockService = new ClockService()
		def fechaService = new FechaService()
		
		def fecha = null
		assert fecha == null
		
		def res = fechaService.esFechaLaDelTipo(fecha, GreenFeeTemplate.LUNES)
		assertEquals false, res
		
		String treintaDeEnero13 = "30/01/2013"
		fecha = clockService.fromStringConHoraActual(treintaDeEnero13)
		assert fecha != null
		
		res = fechaService.esFechaLaDelTipo(fecha, GreenFeeTemplate.MIERCOLES)
		assertEquals true, res
		
		log.info("testEsFechaLaDelTipoOk - OK")
	}
	
	void testIsFiestasNull() {
		def fechaService = new FechaService()
		def isFiesta = fechaService.isFiesta(null, null)
		
		assert isFiesta == false
		
		def fiestas = []
		fiestas.add(new Fiesta(anio: 2012, fecha: new DateTime()))
		
		isFiesta = fechaService.isFiesta(fiestas, null)
		
		assert isFiesta == false
		
		isFiesta = fechaService.isFiesta(null, new DateTime())
		
		assert isFiesta == false
				
		log.info("isFiesta - OK")
	}
	
	void testIsFiestasOk() {
		def fechaService = new FechaService()
		def fiestas = []
		fiestas.add(new Fiesta(anio: 2012, fecha: new DateTime())) // hoy es fiesta

		def isFiesta = fechaService.isFiesta(fiestas, new DateTime())
		
		assert isFiesta == true
		
		log.info("OK")
	}
	
    void testIsFinDeSemanaNull() {
		def isFinDeSemana = new FechaService().isFinDeSemana(null)
		
		assert isFinDeSemana == false
		
		log.info("testIsFinDeSemanaNull - OK")
    }
	
	void testIsFinDeSemanaOk() {
		// 21/11/2012 fue Miercoles
		DateTime noFinde = new DateTime(2012, 11, 21, 0, 0)
		def isFinDeSemana = new FechaService().isFinDeSemana(noFinde)
		
		assert isFinDeSemana == false
		
		// 25/11/2012 fue Domingo
		DateTime finde = new DateTime(2012, 11, 25, 0, 0)
		isFinDeSemana = new FechaService().isFinDeSemana(finde)
		
		assert isFinDeSemana == true
		
		log.info("testIsFinDeSemanaOk - OK")
	}

}
