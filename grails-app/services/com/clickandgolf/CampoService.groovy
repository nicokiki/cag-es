package com.clickandgolf

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


class CampoService {

	/**
	 * Este metodo de servicio existe de manera que sea transaccional y se obtenga un id, el cual
	 * es necesario desde el invoker.
	 * @param campo (no puede ser null)
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    def save(Campo campo) {
		campo.save(flush: true)
		log.info("El campo de nombre: '" + campo.nombre + "' se ha salvado correctamente ...")
    }
	
	def getCamposActivos() {
		def crit = Campo.createCriteria()
		def campos = crit.list {
			eq ("estado", Campo.ACTIVO)
			fetchMode("ubicacion", org.hibernate.FetchMode.JOIN)
		} 
		
		return campos
	}
	
}
