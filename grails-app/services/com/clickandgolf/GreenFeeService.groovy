package com.clickandgolf

import java.math.MathContext

import org.grails.paypal.Payment;
import org.joda.time.DateTime
import org.joda.time.Days
import org.springframework.transaction.annotation.Transactional

import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers
import grails.gorm.DetachedCriteria;

import groovy.sql.Sql



/**
 * Service para Green Fees.
 * 
 * @author gonznic
 */
class GreenFeeService {
	
	
	def fechaService
	def clockService
	
	def sessionFactory
	def propertyInstanceMap = org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP
	
	
	// Nuevos
	def utilService
	
	def busquedaDeCampos(String ubicacion, String fecha, Locale locale) {
		/* Fecha de busqueda por defecto sera hoy
		 * Ubicacion por defecto sera todo (es ciudad:nombre o region:nombre)
		 */
		def ubicacionAux = ubicacion
		String nombreUbicacion = ("null" == ubicacionAux) ? null : ubicacionAux
		DateTime desde = clockService.ahora()
		if (fecha) {
			desde = clockService.fromStringConHoraComienzo(fecha)
		}
		DateTime hasta = clockService.finalDeFecha(desde) // Busca por un dia
		DateTime desdeAux = clockService.enXHorasSiEsHoy(desde);
		log.debug("Buscando desde:'" + desdeAux + "', hasta:'" + hasta + "' ...")
		
		def detachedCriteria = utilService.parseUbicacion(nombreUbicacion, null)
		def ubicacionSplitted = utilService.splitUbicacion(nombreUbicacion)
		if (ubicacionSplitted[1].contains("Españ")) {
			// Esto es feo pero no importa es x el encoding q no lo muestra bien :(
			ubicacionSplitted[1] = "Espa&ntilde;a"
		}
		
		def resultado = null
		def campos = detachedCriteria.list()
		if (!campos || campos.isEmpty()) {
			resultado = new ArrayList<GreenFee>();
		}
		else {
			// Hay un defect http://jira.grails.org/browse/GRAILS-8114
			resultado = GreenFee.createCriteria().list() {
				between("diaHora", desdeAux, hasta)
				'in'("estado", GreenFee.ESTADOS_DISPONIBLES)
				'in'("campo", campos)
				fetchMode("campo", org.hibernate.FetchMode.JOIN)
			}
		}
		
		def diaEnTexto = clockService.getDiaTexto(desde, locale)
		def formattedDate = clockService.formatted(desde)
		def dia = clockService.formatted(desde, ClockService.dayFormatter)
		def mes = clockService.formatted(desde, ClockService.monthFormatter)
		def anio = clockService.formatted(desde, ClockService.yearFormatter)
		
		/* Ahora tengo un monton de Green Fees pero los tengo q "acumular"
		 */
		 def greenFeesInfo = this.decorateGreenFeeInfo(resultado, dia, mes, anio)
		log.info("#greenFeeInfos encontrados: " + greenFeesInfo.size())
						
		def includeYesterday = (clockService.isToday(desde) ? "NO" : "YES");
		String yesterdayFrom = clockService.formatted(desde.plusDays(-1));
		String tomorrowFrom = clockService.formatted(desde.plusDays(1));
		
		def model = [	greenFeesInfo: greenFeesInfo, formattedDate: formattedDate, ubicacionSplitted: ubicacionSplitted[1],
						includeYesterday: includeYesterday, nombreUbicacion: nombreUbicacion,
						yesterdayFrom: yesterdayFrom, tomorrowFrom: tomorrowFrom, diaEnTexto: diaEnTexto];
		
		return model;
	}

	def busquedaDeCamposAvanzada(BigDecimal precioMax, String ubicacion, String fecha, String tipo, String hoyosAux, Locale locale) {
		String nombreUbicacion = ("null" == ubicacion) ? null : ubicacion
		DateTime desde = clockService.ahora()
		if (fecha) {
			desde = clockService.fromStringConHoraComienzo(fecha)
		}
		DateTime hasta = clockService.finalDeFecha(desde) // Busca por un dia
		DateTime desdeAux = clockService.enXHorasSiEsHoy(desde);
		
		log.debug("Buscando desde:'" + desdeAux + "', hasta:'" + hasta + "' ...")
		
		def tipoAux = tipo ?: ""
		def detachedCriteria = utilService.parseUbicacion(nombreUbicacion, tipoAux)
		def ubicacionSplitted = utilService.splitUbicacion(nombreUbicacion)
		if (ubicacionSplitted[1].contains("Españ")) {
			// Esto es feo pero no importa es x el encoding q no lo muestra bien :(
			ubicacionSplitted[1] = "Espa&ntilde;a"
		}
		
		def resultado = null
		def campos = detachedCriteria.list()
		if (!campos || campos.isEmpty()) {
			resultado = new ArrayList<GreenFee>();
		}
		else {
			def hoyos = [GreenFee.HOYOS_18, GreenFee.HOYOS_9]
			if ("18".equals(hoyosAux)) {
				hoyos = [GreenFee.HOYOS_18]
			}
			else if ("9".equals(hoyosAux)) {
				hoyos = [GreenFee.HOYOS_9]
			}
			
			resultado = GreenFee.createCriteria().list() {
				between("diaHora", desdeAux, hasta)
				'in'("estado", GreenFee.ESTADOS_DISPONIBLES)
				le("precio", new BigDecimal(precioMax))
				'in'("campo", campos)
				'in'("hoyos", hoyos)
				fetchMode("campo", org.hibernate.FetchMode.JOIN)
			}
		}
		def diaEnTexto = clockService.getDiaTexto(desde, locale)
		def formattedDate = clockService.formatted(desde)
		def dia = clockService.formatted(desde, ClockService.dayFormatter)
		def mes = clockService.formatted(desde, ClockService.monthFormatter)
		def anio = clockService.formatted(desde, ClockService.yearFormatter)
		
		/* Ahora tengo un monton de Green Fees pero los tengo q "acumular"
		 */
		 def greenFeesInfo = this.decorateGreenFeeInfo(resultado, dia, mes, anio)
		log.info("#greenFeeInfos encontrados: " + greenFeesInfo.size())
						
		def includeYesterday = (clockService.isToday(desde) ? "NO" : "YES");
		String yesterdayFrom = clockService.formatted(desde.plusDays(-1));
		String tomorrowFrom = clockService.formatted(desde.plusDays(1));
		
		def model = [	greenFeesInfo: greenFeesInfo, formattedDate: formattedDate, ubicacionSplitted: ubicacionSplitted[1],
						includeYesterday: includeYesterday, nombreUbicacion: nombreUbicacion,
						yesterdayFrom: yesterdayFrom, tomorrowFrom: tomorrowFrom, diaEnTexto: diaEnTexto,
						precioMax: precioMax]
		
		return model;
	}
	
	def busquedaGreenFees(Campo campo, String fecha) {
		DateTime desde = clockService.fromStringConHoraComienzo(fecha)
		DateTime hasta = clockService.finalDeFecha(desde) // Busca por un dia
		DateTime desdeAux = clockService.enXHorasSiEsHoy(desde);
		log.info("Por listar los green-fees del campo '" + campo + "' recibido para la fecha " + fecha + "...")

		def resultado = GreenFee.createCriteria().list() {
			between("diaHora", desdeAux, hasta)
			'in'("estado", GreenFee.ESTADOS_DISPONIBLES)
			eq("campo", campo)
			order("diaHora")
			order("hoyos", "desc")
		}

		return resultado
	}
	
	
	/**
	 * Se puede dar el caso de que al abandonarlo, otro lo tome y luego el primero lo haya modificado correctamente.
	 * En ese caso habria q avisar a los campos de lo q paso, q es una posibilidad en miles pero q puede pasar. Pedir disculpas por
	 * la doble reserva.
	 * 
	 * @param paymentId
	 * @param greenFeeId
	 */
	@Transactional
	void analyseAbandoned(long paymentId, long greenFeeId) {
		log.info("Por analizar si el payment '" + paymentId + "' para el greenFeeId '" + greenFeeId + "' ha sido abandonado ...")
		
		Payment payment = Payment.get(paymentId);
		if (null == payment) {
			log.warn("No se encontro el payment en cuestion ... deberia existir en nuestra BD ... Analizar ...");
			return
		}
		def greenFeeReservado = GreenFeeReservado.findByPayment(payment)
		// Se valida que el green fee id sea el mismo
		if (greenFeeReservado.greenFee.id != greenFeeId) {
			log.warn(	"El Green Fee Reservado (id'" + greenFeeReservado.id + "') en nuestra BD esta relacionado con otro green-fee-id " +
						"('" + greenFeeReservado.greenFee.id + "') que NO es el recibido ('" + greenFeeId + "'). Analizar ...");
			return
		}
		/* Si llegue hasta aca es xq la informacion aparenta ser buena pero se analiza lo siguiente
		 * Si el Payment sigue en PENDING significa que el golfista no lo compro en Paypal entonces lo volvere a poner habilitado
		 */
		if (payment.status == Payment.PENDING) {
			log.info("El Payment sigue en PENDING luego de 5 minutos desde que el golfista tuvo la intencion de comprarlo => Re-habilitandolo ...")
			
			def greenFee = GreenFee.get(greenFeeId)
			greenFee.reset(greenFeeReservado)
			
			if (!greenFee.save()) {
				log.error("No se pudo salvar el greenFee - Solucionar URGENTE")
				greenFee.errors.each {
					log.error("Error: " + it)
				}
			}
			
			payment.status = Payment.INVALID
			payment.save()
			
			greenFeeReservado.estado = GreenFeeReservado.ABANDONED;
			String ahoraStr = clockService.formatted(clockService.ahora(), ClockService.fullFormatter)
			String obs = greenFeeReservado.observaciones
			obs += "\n"
			obs += (ahoraStr + "-> El pago seguia PENDING y pasaron los 5 minutos. Se cambia a ABANDONED y se actualiza el Green Fee en cuestion y el pago se pasa a INVALID") 
			greenFeeReservado.observaciones = obs
			
			greenFeeReservado.save()
		}
	}

	GreenFee reCalculateValues(GreenFee greenFee) {
		if (!greenFee) {
			log.info("No hay que recalcular porque el greenFee recibido es nulo")
			return greenFee
		}
		/* Hay que recalcular el fee y el descuento en base al precio (que puede haber cambiado) y al precioOriginal
		 * Reglas: Si el precio es mayor que el precioOriginal entonces se modificara tambien el precioOriginal y valdra lo mismo que el precio 
		 */
		BigDecimal precio = greenFee.precio
		BigDecimal precioOriginal = greenFee.precioOriginal
		BigDecimal feePorcentaje = greenFee.campo.fee // Siempre estara!
		
		if (!precio || !precioOriginal) {
			log.info("El precio o precioOriginal del greenFee son nulos - no se puede recalcular nada ...")
			return greenFee
		}
		if (precio.compareTo(precioOriginal) > 0) {
			// precio > precioOriginal
			log.info("El precio es ahora mayor que el original => Se cambiara el original al precio")
			precioOriginal = precio
			greenFee.precioOriginal = precioOriginal
		}
		
		greenFee.fee = this.calculateFee(precio, feePorcentaje)
		greenFee.descuento = this.calculateDescuento(precio, precioOriginal)
				
		return greenFee
	}
	
	/**
	 * Puede devolver cero, lo cual de alguna manera implicaria que el green fee es gratis!
	 * 
	 * @param precio
	 * @param feePorcentaje
	 * @return
	 */
	BigDecimal calculateFee(BigDecimal precio, BigDecimal feePorcentaje) {
		if (!precio || BigDecimal.ZERO.compareTo(precio) == 0 || !feePorcentaje || BigDecimal.ZERO.compareTo(feePorcentaje) == 0) {
			// Devuelve CERO cuando el precio es nulo o cero o cuando el fee fuera nulo o cero (caso gratis)
			log.debug("El fee calculado sera cero porque precio/feePorcentaje son null o cero ...")
			return BigDecimal.ZERO
		}
		// La scale=2 en la definicion del dominio de los campo de tipo BigDecimal 
		return (precio.multiply(feePorcentaje)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)
	}

	/**
	 * = [ 100 - (Precio x 100 / PrecioOriginal) ]
	 * Puede ser 100	
	 * @param precio
	 * @param precioOriginal
	 * @return
	 */
	Integer calculateDescuento(BigDecimal precio, BigDecimal precioOriginal) {
		if (!precio || BigDecimal.ZERO.compareTo(precio) == 0 || !precioOriginal || BigDecimal.ZERO.compareTo(precioOriginal) == 0) {
			// Devuelve 100 cuando el precio es nulo o cero o cuando el precioOriginal fuera nulo o cero (caso gratis)
			log.debug("El descuento calculado sera 'cien' porque precio/precioOriginal son null o cero ...")
			return 100
		}
		return ( 100 - (precio.multiply(new BigDecimal(100, new MathContext(2)))).divide(precioOriginal, 2, BigDecimal.ROUND_HALF_UP).intValue())
	}
	
	/**
	 * Crea los {@link GreenFee}s para el {@link Campo} recibido.<br><br>
	 * 
	 * Con el campo se pueden obtener los {@link GreenFeeTemplate}s, de los cuales se calcularan las fechas a ser generadas
	 * de acuerdo al tipo de dia en cuestion.<br> 
	 */
	@Transactional
	def createGreenFees(Campo campo) {
		// No se puede definir como closure al usar @Transactional (http://rethrowexception.wordpress.com/2010/04/27/grails-transaction-injection-doesnt-work-if-you-declare-your-methods-as-closures/)
		if (null == campo || !campo.isActivo()) {
			log.warn("No se puede crear green fees de un campo nulo o INACTIVO. Analizar por que se ha recibido esto ...")
			return
		}
		log.info("Por generar los proximos green fees del campo'" + campo?.nombre + "' ...")
		def fiestas = fechaService.getFiestas(campo.ubicacion)
		
		def templates = getTemplates(campo)
		templates.each { template ->
			def fechasParaCrear = collectDatesToCreate(template, fiestas)
			log.info("Por crear '" + fechasParaCrear?.size + "' green fees usando el template-id '" + template?.id + "' de tipo '" + template?.tipo + "' y tipo-especial:'" + template?.tipoExtra + "' para el campo '" + campo?.nombre + "' ...")
			
			int i = 0
			fechasParaCrear.each { nuevoDiaHora ->
				GreenFee greenFee = GreenFee.build(template, nuevoDiaHora, campo.fee) // El fee se guarda en el campo
				greenFee.save()
				i++
				
				if (i == 50) {
					cleanUpGorm()
					log.info("Se hizo el flush de GORM a la BD ...")
					i = 0 // reset
				}
			}
			cleanUpGorm() // Si quedo algo por insertar
			log.info("Se hizo el ultimo flush de GORM a la BD ...")
			
			// Se actualiza el template en cuestion tambien
			template.save(flush: true)
			log.info("Se hizo el update del template")
		}
		
		log.info("Se generaron los proximos green fees del campo'" + campo?.nombre + "' satisfactoriamente ...")
		
	}
	
	def cleanUpGorm() {
		// http://naleid.com/blog/2009/10/01/batch-import-performance-with-grails-and-mysql/
		def session = sessionFactory.currentSession
		session.flush()
		session.clear()
		propertyInstanceMap.get().clear()
	}
	
		
	@Transactional(readOnly = true)
	def getTemplates(Campo campo) {
		def crit = GreenFeeTemplate.createCriteria()
		def response = crit.list {
			eq ("campo", campo)
			fetchMode("campo", org.hibernate.FetchMode.JOIN)
		}
		
		return response
	}
	
	
	/**
	 * Devuelve una lista de fechas para las cuales se requieren nuevos green fees. Las fechas tienen dia, mes, anio, hora y minuto<br>
	 * Solo hace eso y no tiene en cuenta el resto de cosas del green-fee en si, sino solo las fechas.
	 * [diaHora1, diaHora2, diaHora3, etc].
	 * 
	 * <ul>
	 * 	<li>Si la fecha es anterior a hoy, significa que hay que generar si o si por los proximos 'cantidadDiasGenerar' dias. Esto es para la primera vez q se ejecute.</li> 
	 * 	<li>Si la fecha es a futuro, entonces solo se generaran si desde 'hoy' a 'hastaFechaGenerada' hay menos de 'cantidadDiasGenerar' dias.<br>
	 * 		Ejemplo:
	 * 			<ul> 
	 * 				<li>'hoy':14/11/2012</li>
	 * 				<li>'hastaFechaGenerada':08/12/2012</li>
	 * 				<li>'cantidadDiasGenerar':30</li>
	 * 			</ul>
	 * 			Desde 'hoy' a 'hastaFechaGenerada' hay 24 dias. 24 < 30 => se generan 6 (=30-24) nuevas fechas:
	 * 				09/12, 10/12, 11/12, 12/12, 13/12, 14/12 
	 * 	</li>
	 * </ul>
	 * 
	 * <b>MUY IMPORTANTE</b>: La fecha 'hastaFechaGenerada' del template recibido se actualiza con la ultima generada. Si luego no se utilizara, porque la fecha 
	 * en cuestion no es del TIPO (LABORABLE, etc), no importaria xq fechas para "atras" no molestarian. Siempre es modificable desde la BD de todas maneras.<br>
	 * <b>HAY</b> que persistir el template una vez llamado este metodo!
	 */
    def collectDatesToCreate = { GreenFeeTemplate template, List<Fiesta> fiestas ->
		def response = []
		if (null == template) {
			log.info("El template es null ... los dias y horas a colectar devolvera una lista vacia ... ")
			return response
		}
		DateTime ahora = clockService.ahora()
		DateTime hoy = ahora.toDateMidnight().toInterval().getStart();
		DateTime hastaFechaGeneradaComienzo = template.hastaFechaGenerada ?: clockService.ahora().toDateMidnight().toInterval().getStart();

		def listaHorasMinutos = template.horariosEnListaDeListas()
		def cantidadDiasGenerar = template.cantidadDiasGenerar
		
		if (hastaFechaGeneradaComienzo.isBefore(hoy) || hastaFechaGeneradaComienzo.isEqual(hoy)) {
			// Tengo que generar hasta: 'cantidadDiasGenerar'
			def i = 0
			while (i <= cantidadDiasGenerar && i < 31) { // el '31' es x si se pone cualquiera y q frene en algun monento
				DateTime fechaAux = hoy.plusDays(i)
				int anio = fechaAux.year().get()
				int mes = fechaAux.monthOfYear().get()
				int dia = fechaAux.dayOfMonth().get()
				
				listaHorasMinutos.each { horaMinuto ->  
					def hora = horaMinuto[0]
					def minuto = horaMinuto[1]
					
					DateTime nuevo = new DateTime(anio, mes, dia, hora, minuto, clockService.localTimeZone())
					
					if (fechaService.tenerEnCuenta(nuevo, template, fiestas)) {
						log.debug("La fecha ${nuevo} para el template-id/tipo/tipo-extra: ${template.id}/${template.tipo}/${template.tipoExtra} se tendra en cuenta ...")
						response.add(nuevo)
						// TODO Cambiar a debug
					}
					/* Antes de decidir si se debe tener en cuenta hay q ver q 
					 * 	si es FESTIVO => tiene q ser fin de semana y no tiene q ser fiesta el nuevo (sabado puede q sea fiesta!)
					 *  si es FIESTA => tiene q ser fiesta el nuevo
					 *  si es LABORABLE => no puede ser fin de semana ni fiesta el nuevo
					 */
//					switch (template.tipo) {
//						case GreenFeeTemplate.FESTIVO:
//							if (fechaService.isFinDeSemana(nuevo) && !fechaService.isFiesta(fiestas, nuevo)) {
//								response.add(nuevo)
//							}
//							break;
//						case GreenFeeTemplate.FIESTA:
//							if (fechaService.isFiesta(fiestas, nuevo)) {
//								response.add(nuevo)
//							}
//							break;
//						default:
//							// Es LABORABLE
//							if (!fechaService.isFinDeSemana(nuevo) && !fechaService.isFiesta(fiestas, nuevo)) {
//								response.add(nuevo)
//							}
//							break;
//					}
					/* Actualizo el nuevo aunq el nuevo no se haya agregado porque de todas maneras, lo volveria a validar y validar
					 * sin sentido. Una vez validado es suficiente. Esto verdaderamente indica hasta q fecha fue procesada y analizada */
					template.hastaFechaGenerada = nuevo 
				}
				i++
			}
		}
		else if (hoy.isBefore(hastaFechaGeneradaComienzo)) {
			// Solo se generaran si desde 'hoy' a 'hastaFechaGenerada' hay menos de 'cantidadDiasGenerar' dias. 
			Days dias = Days.daysBetween(hoy, hastaFechaGeneradaComienzo)
			def diasDiferencia = dias.getDays()
			if (diasDiferencia < cantidadDiasGenerar) {
				def cantidadDiasGenerarNuevo = (cantidadDiasGenerar - diasDiferencia)
				
				def i = 1
				while (i < (cantidadDiasGenerarNuevo + 1) && i < 31) { // el '31' es x si se pone cualquiera y q frene en algun monento
					DateTime fechaAux = hastaFechaGeneradaComienzo.plusDays(i)
					int anio = fechaAux.year().get()
					int mes = fechaAux.monthOfYear().get()
					int dia = fechaAux.dayOfMonth().get()
					
					listaHorasMinutos.each { horaMinuto ->
						def hora = horaMinuto[0]
						def minuto = horaMinuto[1]
						
						DateTime nuevo = new DateTime(anio, mes, dia, hora, minuto, clockService.localTimeZone())
						
						if (fechaService.tenerEnCuenta(nuevo, template, fiestas)) {
							log.debug("La fecha ${nuevo} para el template-id/tipo/tipo-extra: ${template.id}/${template.tipo}/${template.tipoExtra} se tendra en cuenta ...")
							response.add(nuevo)
							// TODO Cambiar a debug
						}
						
						/* Antes de decidir si se debe tener en cuenta hay q ver q
					 * 	si es FESTIVO => tiene q ser fin de semana y no tiene q ser fiesta el nuevo (sabado puede q sea fiesta!)
					 *  si es FIESTA => tiene q ser fiesta el nuevo
					 *  si es LABORABLE => no puede ser fin de semana ni fiesta el nuevo
						 */
//						switch (template.tipo) {
//							case GreenFeeTemplate.FESTIVO:
//								if (fechaService.isFinDeSemana(nuevo) && !fechaService.isFiesta(fiestas, nuevo)) {
//									response.add(nuevo)
//								}
//								break;
//							case GreenFeeTemplate.FIESTA:
//								if (fechaService.isFiesta(fiestas, nuevo)) {
//									response.add(nuevo)
//								}
//								break;
//							default:
//								// Es LABORABLE
//								if (!fechaService.isFinDeSemana(nuevo) && !fechaService.isFiesta(fiestas, nuevo)) {
//									response.add(nuevo)
//								}
//								break;
//						}
						template.hastaFechaGenerada = nuevo // Actualizo el nuevo
					}
					i++
				}
			}
		}	
		
		return response
    }
		
	public List<GreenFeeCommand> decorateGreenFeeInfo(List<GreenFee> greenFees, String dia, String mes, String anio) { 
//	def decorateGreenFeeInfo = { List<GreenFee> greenFees, DateTime fecha ->
		if (null == greenFees) {
			log.debug("No hay green fees para decorar")
			return new ArrayList<GreenFeeCommand>()
		}
		/* key = nombre Campo
		 * value = GreenFeeCommand
		 * 
		 */
		Map<String, GreenFeeCommand> map = new HashMap<String, GreenFeeCommand>()
		greenFees.each { greenFee ->
			String nombre = greenFee.campo.nombre
			FranjaHoraria franjaHoraria = FranjaHoraria.fromDate(greenFee.diaHora)
			if (map.containsKey(nombre)) {
				GreenFeeCommand cmd = map.get(nombre)
				
				franjaHoraria.doProcessMinimum(cmd, greenFee.precio)
			}  
			else {
				GreenFeeCommand cmd = new GreenFeeCommand(campo: greenFee.campo, dia: dia, mes: mes, anio: anio)
				franjaHoraria.setPrecio(cmd, greenFee.precio)
				map.put(nombre, cmd)
			}
		}
		List<GreenFeeCommand> response = new ArrayList<GreenFeeCommand>();
		Set<String> set =  map.keySet();
		for (String key : set) {
			response.add(map.get(key));
		}
	
		Collections.sort(response, 
			new Comparator<GreenFeeCommand>() {
				@Override
				public int compare(GreenFeeCommand o1, GreenFeeCommand o2) {
					return o1.campo.nombre.compareTo(o2.campo.nombre);
				}
			} );
		
		
		return response;
	}

		
}
