package com.clickandgolf

class ErrorController {

    def notFound() { 
	}
	
	// No hace nada pero al menos lo atrapamos
	def noPermitido() {
		
	}
	
	def errorDelServidor() {
		try {
			def trans = System.currentTimeMillis()
			def exception = request.exception
			
			log.error("Error del servidor - ID_error:'${trans}' - Mensaje excepcion: ${exception}", exception)
			
			// Se pone 'trans' para indicarle al usuario que si pasase algo mande un email con ese codigo!
			def model = [exception: exception, trans: trans] 
			model
		}
		catch (Exception e) {
			log.error("Error en el handle de error - evitando un stackoverflow. Mensaje: ${e}", e)			
		}
	}
	
}
