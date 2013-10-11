package com.clickandgolf

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTimeZone; 

/**
 * Es un utility Service para cosas relacionadas con el tiempo y fechas y demas
 * 
 * @author gonznic
 */
class ClockService {
	
	static DateTimeZone spainTZ = DateTimeZone.forID("Europe/Madrid")
	
	static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy").withZone(spainTZ);

	static DateTimeFormatter dayFormatter = DateTimeFormat.forPattern("dd").withZone(spainTZ);
	static DateTimeFormatter monthFormatter = DateTimeFormat.forPattern("MM").withZone(spainTZ);
	static DateTimeFormatter yearFormatter = DateTimeFormat.forPattern("yyyy").withZone(spainTZ);
	static DateTimeFormatter hourMinuteFormatter = DateTimeFormat.forPattern("HH:mm").withZone(spainTZ);
	static DateTimeFormatter hourMinuteSecondFormatter = DateTimeFormat.forPattern("HH:mm:ss").withZone(spainTZ);
	static DateTimeFormatter fullFormatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").withZone(spainTZ);
	
	
	DateTime[] getStartEndDaysForOneWeek() {
		// Ahora quiero que tenga en cuenta la hora y el minuto de hoy
		// En 7 dias quiero la hora del fin del dia
		DateTime ahora = this.ahora()
		DateTime enSieteDias = ahora.plusDays(7).withTime(23, 59, 59, 999)

		return [ahora, enSieteDias]				
	}

	DateTime[] getStartEndDaysForOneMonth() {
		// Ahora quiero que tenga en cuenta la hora y el minuto de hoy
		// En un mes quiero la hora del fin del dia
		DateTime ahora = this.ahora()
		DateTime enUnMes = ahora.plusMonths(1).withTime(23, 59, 59, 999)

		return [ahora, enUnMes]
	}

	DateTime[] getStartEndDaysForEverTillToday() {
		// Ahora quiero que tenga en cuenta la hora y el minuto de hoy
		// En un mes quiero la hora del fin del dia
		DateTime haceUnMinuto = this.ahora().plusMinutes(-1)
		// La aplicacion saldra en 2013 asi q cualquier dia de 2012 esta bien ...
		DateTime desdeDosMilDoce = new DateTime(2012, 12, 1, 1, 0, localTimeZone()) 

		return [desdeDosMilDoce, haceUnMinuto]
	}
	
	DateTime comienzoHoy() {
		return this.ahora().withTime(0, 0, 0, 0);
	}
	
	DateTime finalHoy() {
		return this.ahora().withTime(23, 59, 59, 999);
	}
	
	DateTime finalDeFecha(DateTime fecha) {
		if (null == fecha) {
			fecha = this.ahora()
		}
		return fecha.withTime(23, 59, 59, 999);
	}
	
	DateTimeZone localTimeZone() {
		return ClockService.spainTZ;
	}
	
	DateTime ahora() {
		return new DateTime(localTimeZone())
	}
	
	DateTime enMinutos(int minutos) {
		return ahora().plusMinutes(minutos);
	}
	
	DateTime fromStringConHoraActual(String fechaString) {
		try {
			DateTime hoy = this.ahora()
			return formatter.parseDateTime(fechaString).withTime(hoy.getHourOfDay(), hoy.getMinuteOfHour(), hoy.getSecondOfMinute(), hoy.getMillisOfSecond());
		}
		catch (Exception e) {
			log.warn("La fecha: '" + fechaString + "' no respeta el formatter 'dd/MM/yyyy' o el problema es otro. Devuelve ahora(). Analizar el error ...", e)
			return this.ahora();
		}
	}

	DateTime fromStringConHoraComienzo(String fechaString) {
		try {
			return formatter.parseDateTime(fechaString).withTime(0, 0, 0, 0);
		}
		catch (Exception e) {
			log.warn("La fecha: '" + fechaString + "' no respeta el formatter 'dd/MM/yyyy' o el problema es otro. Devuelve ahora(). Analizar el error ...", e)
			return this.comienzoHoy();
		}
	}

	String formatted(DateTime fecha) {
		return formatter.print(fecha);
	}
	
	String formatted(DateTime fecha, DateTimeFormatter chosenFormatter) {
		return chosenFormatter.print(fecha);
	}
	
	
	boolean isToday(DateTime fecha) {
		if (null == fecha) {
			return false;
		}
		DateTime ahora = this.ahora();
		
		return (ahora.dayOfMonth == fecha.dayOfMonth && ahora.monthOfYear == fecha.monthOfYear && ahora.year == fecha.year);
	}
	
	boolean isBeforeToday(DateTime fecha) {
		if (null == fecha) {
			return false;
		}
		DateTime comienzoHoy = this.comienzoHoy();
		DateTime comienzoFecha = fecha.withTime(0, 0, 0, 0);
		// Ambas fechas estan en el minimo de hs/min/seg/mili => Si comienzoFecha es menor que comienzoHoy => es anterior a hoy
		if (comienzoFecha.compareTo(comienzoHoy) < 0) {
			return true;
		}
		return false;
	}

	DateTime fromStringAndHours(String fechaStr, int hour, int minute) {
		DateTime resp = fromStringConHoraActual(fechaStr);
		resp = resp.withTime(hour, minute, 0, 0);
		
		return resp;
	}
	
	boolean isBeforeTodayWithHours(DateTime fecha) {
		if (null == fecha) {
			return false;
		}
		DateTime ahora = this.ahora();
		// Ambas fechas estan en el minimo de hs/min/seg/mili => Si comienzoFecha es menor que comienzoHoy => es anterior a hoy
		if (fecha.compareTo(ahora) < 0) {
			return true;
		}
		return false;
	}

	DateTime enXHorasSiEsHoy(DateTime fecha) {
		if (isToday(fecha)) {
			// Si es hoy me aseguro q la hora es la de ahora + 4 horas por si era la del comienzo del dia ...
			return ahora().plusHours(4);
		}
		return fecha;
	}
	
	String getDiaTexto(DateTime fecha, Locale locale) {
		if (null == locale) {
			locale = new Locale("es","ES")
		}
		if (null == fecha) {
			return ""
		}
		return fecha.dayOfWeek().getAsText(locale);
	}
	
	
}
