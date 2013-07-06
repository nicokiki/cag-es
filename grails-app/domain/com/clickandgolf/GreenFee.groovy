package com.clickandgolf

import java.math.MathContext
import org.joda.time.DateTime
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;


class GreenFee {
	// Todo esto esta repetido en ClockService y quizas en el template pero es lo q hay ...

	static DateTimeZone spainTZ = DateTimeZone.forID("Europe/Madrid")
	
	static DateTimeFormatter hourMinuteFormatter = DateTimeFormat.forPattern("HH:mm").withZone(spainTZ);
	static DateTimeFormatter dayFormatter = DateTimeFormat.forPattern("dd/MM/yyyy").withZone(spainTZ);
	
	static DateTimeFormatter diaFormatter = DateTimeFormat.forPattern("dd").withZone(spainTZ);
	static DateTimeFormatter mesFormatter = DateTimeFormat.forPattern("MM").withZone(spainTZ);
	static DateTimeFormatter anioFormatter = DateTimeFormat.forPattern("yyyy").withZone(spainTZ);


	static final ACTIVO = "ACTIVO" // Disponible
	static final INACTIVO = "INACTIVO" // NO Disponible
	static final PARCIAL = "PARCIAL" // Parcialmente disponible porque se reservo alguno pero todavia esta disponible
	static final RESERVADO = "RESERVADO" // Totalmente reservado
	
	static final ESTADOS_RESERVADOS = [PARCIAL, RESERVADO]
	static final ESTADOS_DISPONIBLES = [PARCIAL, ACTIVO]
	
	static final NORMAL = 0
	static final TORNEO = 1
	
	static final HOYOS_18 = 0
	static final HOYOS_9 = 1
	
	static final String HOYOS_18_TEXTO = "18"
	static final String HOYOS_9_TEXTO = "9"
	
	// Campo
	Campo campo

	DateTime diaHora
	String info
	
	// El precio es por golfista / El fee es por golfista
	BigDecimal precio
	BigDecimal precioOriginal
	Integer descuento
	BigDecimal fee
	
	/** 
	 * 1=Se puede reservar de a uno
	 * 2=Se puede reservar de a dos
	 * 4=Se puede reservar todo junto nada mas 
	 */
	Integer golfistasMinimo
	/**
	 * Cantidad de green fees disponibles (Valores posibles: 1, 2, 3, 4 / '0' puede ser xa INACTIVO o RESERVADO)
	 */
	Integer disponibles
	
	String estado = ACTIVO
	Integer tipo = NORMAL
	
	Integer hoyos = HOYOS_18 // Por defecto
	
	// Lo uso en el GUI para mostrar el horario
	String getHoraMinuto() {
		if (null == this.diaHora) {
			return "";
		}
		return hourMinuteFormatter.print(this.diaHora);
	}

	String getDia() {
		if (null == this.diaHora) {
			return "";
		}
		return dayFormatter.print(this.diaHora);
	}

	String getDiaMes() {
		if (null == this.diaHora) {
			return "";
		}
		return diaFormatter.print(this.diaHora);
	}

	String getMes() {
		if (null == this.diaHora) {
			return "";
		}
		return mesFormatter.print(this.diaHora);
	}

	String getAnio() {
		if (null == this.diaHora) {
			return "";
		}
		return anioFormatter.print(this.diaHora);
	}
	
	String getHoyosTexto() {
		if (is18Hoyos()) {
			return HOYOS_18_TEXTO
		}
		return HOYOS_9_TEXTO
	}

	BigDecimal getFeeToPayUpFront(Integer golfistas) {
		return fee.multiply(new BigDecimal(golfistas));
	}

	BigDecimal getFeeToPayAtCourse(Integer golfistas) {
		return (precio.subtract(fee)).multiply(new BigDecimal(golfistas));
	}

	boolean isTorneo() {
		return tipo == GreenFee.TORNEO
	}

	boolean inEstadoDisponibles() {
		return (this.estado in ESTADOS_DISPONIBLES);
	}
	
	boolean is18Hoyos() {
		return hoyos == GreenFee.HOYOS_18
	}
	
	static transients = ['horaMinuto', 'dia', 'feeToPayUpFront', 'diaMes', 'mes', 'anio', 'hoyosTexto']
	
    static constraints = {
		campo nullable: false
		diaHora nullable: false

		info blank: false, maxSize: 100
		
		precio (nullable: false, min: 5.0, max: 999.99, scale: 2)
		precioOriginal (nullable: false, min: 5.0, max: 999.99, scale: 2)
		fee (nullable: false, min: 0.0, max: 999.99, scale: 2)
		descuento (nullable: false, min: 0, max: 100)
		
		golfistasMinimo (nullable: false, inList: [1, 2, 4])
		disponibles (nullable: false, min: 0, max: 4, validator:{valor, obj ->
			if ( (obj.estado == GreenFee.ACTIVO || obj.estado == GreenFee.PARCIAL) && valor == 0) {
				return 'input.tips'
			}
			if (obj.estado != GreenFee.RESERVADO && obj.estado != GreenFee.INACTIVO && valor < obj.golfistasMinimo) {
				// Cuando esta RESERVADO o INACTIVO, los disponibles pueden ser 0
				return 'menor.que.minimos'
			}
		})
		
		estado inList: [GreenFee.ACTIVO, GreenFee.INACTIVO, GreenFee.PARCIAL, GreenFee.RESERVADO]
		tipo inList: [GreenFee.NORMAL, GreenFee.TORNEO]
		hoyos inList: [GreenFee.HOYOS_18, GreenFee.HOYOS_9]
    }
	
	static mapping = {
		cache 'read-write'
	}
	
	List<Integer> comboGolfistas() {
		List<Integer> response = new ArrayList<Integer>();
		switch (golfistasMinimo) {
		case 4:
			response.add(4);
			break;
		case 2:
			if (disponibles == 4) {
				response.add(2);
				response.add(4);
			}
			else if (disponibles == 2) {
				response.add(2);
			}
			break;
		default:
			// golfistasMinimo= 1
			int x = 1
			while (x <= disponibles) {
				response.add(x)
				x++
			}
			break;
		}
		
		
		return response
	}
	
	void reset(Integer golfistas) {
		if (null == golfistas) {
			return 
		}
		// Los golfistas NUNCA seran mayores que los disponibles que haya en el green fee
		int aux = disponibles - golfistas
		disponibles = aux
		if ( (aux == 0)) {
			log.info("No quedan golfistas disponibles => Pasando el estado del green fee a RESERVADO (Disponibles:'" + disponibles + "') ...")
			estado = GreenFee.RESERVADO
		}
		else {
			log.info("Quedan golfistas disponibles => Pasando el estado del green fee a PARCIAL (Disponibles:'" + disponibles + "')  ...")
			estado = GreenFee.PARCIAL
		}
	}
	
	void reset(GreenFeeReservado greenFeeReservado) {
		int aux = disponibles + greenFeeReservado.golfistas
		if ((estado == GreenFee.ACTIVO || estado == GreenFee.PARCIAL) && aux > 4) {
			// O se reseteo anteriormente o algo paso, pero no se puede agregar mas disponibles. Nunca deberia pasar pero con Paypal no se sabe ...
			log.warn("La cantidad de disponibles seria mayor a 4 y no es posible. Seguramente se haya reactivado anteriormente, pero no se puede volver a hacer. Analizar ...")
			return
		}
		disponibles = aux
		if ( (aux == 4)) {
			log.info("Quedan todos los golfistas disponibles => Pasando el estado del green fee a ACTIVO ...")
			estado = GreenFee.ACTIVO
		}
		else if (aux > 0 && aux < 4) {
			log.info("Quedan golfistas disponibles => Pasando el estado del green fee a PARCIAL ...")
			estado = GreenFee.PARCIAL
		}
	}
	

	static GreenFee build(GreenFeeTemplate template, DateTime diaHora, BigDecimal fee) {
		// El fee q se recibe es el fee del campo. Si se aplica 12% entonces el fee recibido es 12 de manera q
		// el fee q tendra el green fee sera:
		//		fee * precio / 100
		BigDecimal feeCalculado = (template.precio.multiply(fee)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		return new GreenFee(campo: template.campo, diaHora: diaHora, info: template.info,  
							precio: template.precio, precioOriginal: template.precioOriginal, fee: feeCalculado, descuento: template.descuento, 
							golfistasMinimo: template.golfistasMinimo, disponibles: template.disponibles,
							tipo: template.tipoExtra, hoyos: template.hoyos)
	}
	
}
