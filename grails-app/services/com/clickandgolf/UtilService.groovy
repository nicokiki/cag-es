package com.clickandgolf

import grails.gorm.DetachedCriteria

import com.clickandgolf.Ubicacion

class UtilService {
	
	def grailsApplication

	/**
	 * Devuelve en un array de Strings lo siguiente: [nombre-de-columna-de-ubicacion valor de la columna]<br>
	 * Ejemplo: 
	 * <ul>
	 * 	<li>[ciudad, Barcelona]</li>  
	 * 	<li>[region, Catalunya] </li> 
	 * 	<li>[pais, Esp](en caso de lo recibido no sea correcto) </li>
	 * </ul>  
	 */
	def splitUbicacion = { String nombreUbicacion ->
		def response = ["pais", Ubicacion.TODO_EL_PAIS]
		
		if (null != nombreUbicacion && !nombreUbicacion.isEmpty()) {
			String[] codigos = nombreUbicacion.split(":")
			if (null != codigos && codigos.size() == 2) {
				String atributo = codigos[0];
				String valor = codigos[1];
				
//				response = alias + "." + atributo + "='" + valor + "' " 
				response = [atributo, valor] 
			}
		}
		else {
			log.info("Se usaran todas las ubicaciones para buscar campos ...")
		}
		
		return response
	}
	
	
	/**
	 * Arma un detachedCriteria para ser usado como subquery
	 */
    DetachedCriteria parseUbicacion(String nombreUbicacion, String tipo) {
		log.debug("parseUbicacion -> nombreUbicacion: ${nombreUbicacion}, tipo:${tipo} ...")
		// No falla entonces devuelve todo por defecto
		DetachedCriteria response = null
		if (null == nombreUbicacion || nombreUbicacion.isEmpty()) {
			if (null == tipo || tipo.isEmpty() || "null".equals(tipo)) {
				response = new DetachedCriteria(Campo).build {
					isNotNull('ubicacion')
				}
				return response;
			}
			if (Campo.GOLF.equals(tipo)) {
				response = new DetachedCriteria(Campo).build {
					eq "tipo", Campo.GOLF
					isNotNull('ubicacion')
				}
			}
			else {
				response = new DetachedCriteria(Campo).build {
					eq "tipo", Campo.PITCH_AND_PUTT
					isNotNull('ubicacion')
				}
			}
			return response;	
		}
		if (null != nombreUbicacion && !nombreUbicacion.isEmpty()) {
			String[] codigos = nombreUbicacion.split(":")
			if (null != codigos && codigos.size() == 2) {
				String atributo = codigos[0];
				String valor = codigos[1];
				
				if (null == tipo || tipo.isEmpty() || "null".equals(tipo)) {
					response = new DetachedCriteria(Campo).build {
						'in'("ubicacion", new DetachedCriteria(Ubicacion).build {
							eq(atributo, valor)
						}.list())
					}
				}
				else {
					if (Campo.GOLF.equals(tipo)) {
						response = new DetachedCriteria(Campo).build {
							eq("tipo", Campo.GOLF)			
							and {
								'in'("ubicacion", new DetachedCriteria(Ubicacion).build {
									eq(atributo, valor)
								}.list())
							}
						}
					}
					else {
						response = new DetachedCriteria(Campo).build {
							eq("tipo", Campo.PITCH_AND_PUTT)			
							and {
								'in'("ubicacion", new DetachedCriteria(Ubicacion).build {
									eq(atributo, valor)
								}.list())
							}
						}
					}
				}
				return response;
			}
		}
		else {
			log.info("Se usaran todas las ubicaciones para buscar campos ...")
		}
		
		return response
    }
	
	def emailFrom() {
		return grailsApplication.config.email.notificaciones.from
	}
	
	def ubicacionImagenes() {
		return grailsApplication.config.imagenes.ubicacion
	}
	
	
	/**
	 * Busca un green fee gemelo. Si no lo encuentra entonces devuelve null
	 * 
	 * @param greenFee
	 * @return Puede devolver null
	 */
	GreenFee gemelo(GreenFee greenFee) {
		if (!greenFee) {
			return null
		}
		
		GreenFee gemelo = GreenFee.createCriteria().get() {
			eq("campo", greenFee.campo)
			eq("diaHora", greenFee.diaHora)
			ne("id", greenFee.id)
			ne("hoyos", greenFee.hoyos)
			eq("estado", GreenFee.ACTIVO)
		}
		
		return gemelo
	}
	
	boolean existeMismaHoraCampoTipo(GreenFee greenFee) {
		if (!greenFee) {
			return false
		}
		
		GreenFee casiMismo = GreenFee.createCriteria().get() {
			eq("campo", greenFee.campo)
			eq("diaHora", greenFee.diaHora)
			eq("hoyos", greenFee.hoyos)
			eq("estado", GreenFee.ACTIVO)
		}
		
		if (null != casiMismo) {
			return true;
		
		}
		return false
	} 
	
	
}
