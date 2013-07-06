package com.clickandgolf

import java.math.BigDecimal;
import java.util.Comparator;

import org.joda.time.DateTime;

// Es para las promociones que pueden ver todos
class PromocionesController {
	
	def clockService
	def utilService

    def index() { 
		redirect(action: "ultimoMomento")
	}
	
	
	def ultimoMomento() {
		// Desde es maniana siempre
		DateTime desde = clockService.comienzoHoy().plusDays(1)
		DateTime hasta = clockService.finalDeFecha(desde)

		// Busco los campos con los mejores 10 descuentos para maniana. Solo los 10. El resultado NO viene ordenado por descuento entonces lo ordeno
		// a mano y armo el hyphenated nombre para armar los links correctamente
		def critGreenFees = GreenFee.createCriteria()
		def res = critGreenFees.list {
			createAlias("campo", "campoAlias")
			projections {
				property("campoAlias.nombre", "campo_nombre")
				property("campoAlias.imagenSecundaria", "imagenSecundaria")
				property("campoAlias.hyphenatedNombre", "campo_hyphenatedNombre")
				max("descuento")
				groupProperty("campoAlias.id")
			}
			between("diaHora", desde, hasta)
			order("descuento", "desc")
			maxResults(10)
		}
		
		List<CampoView> campos = new ArrayList<CampoView>();
		res.each { data ->
			campos.add(new CampoView(nombreCampo:data[0], imagenSecundaria:data[1], descuento: data[3], idCampo: data[4], hyphenatedNombre: data[2]));
		}
		// Ordeno de mayor a menor por el descuento!
		Collections.sort(campos,
			new Comparator<CampoView>() {
				@Override
				public int compare(CampoView o1, CampoView o2) {
					return o2.descuento.compareTo(o1.descuento);
				}
			} );
		log.info("Por mostrar '" + campos.size() + "' ofertas TOP-10 ...")
		
		// La fecha es xa armar el link a reservar los green fees
		def fecha = [dia:clockService.formatted(desde, ClockService.dayFormatter), mes:clockService.formatted(desde, ClockService.monthFormatter), anio:clockService.formatted(desde, ClockService.yearFormatter)]
		
		def model = [campos: campos, fecha: fecha]
		
		if (request.xhr) {
			log.debug("AJAX request recibido ...")
			render(template: "ofertas", model: model)
		}
		else {
			log.debug("No AJAX ...")
			model
		}
	}
	
	static class CampoView {
		String nombreCampo
		String imagenSecundaria
		Integer idCampo
		Integer descuento
		String hyphenatedNombre
		
		@Override
		public String toString() {
			return "CampoView [nombreCampo=" + nombreCampo
					+ ", imagenSecundaria=" + imagenSecundaria + ", idCampo="
					+ idCampo + ", descuento=" + descuento
					+ ", hypenatedNombre=" + hypenatedNombre + "]";
		}

	}
	
}
