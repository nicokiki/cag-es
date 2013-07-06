package com.clickandgolf

import org.codehaus.groovy.grails.plugins.codecs.MD5Codec;

/**
 * Este es un controller para mostrar imagenes de campos
 * 
 * @author gonznic
 */
class ImagenCampoController {

	def utilService
	
    def index() { 
		redirect(action:"imagen")
	}
	
	/**
	 * Hay q pasarle el id de campo y el nombre entero de la imagen con extension incluida
	 * 
	 * Solo para PNGs
	 * 
	 * @return
	 */

//	Probar esto: withCacheHeaders(Closure dsl) en http://grails.org/plugin/cache-headers
//	Q es lo mismo q dice este otro pero mas elegantemente: http://grails.1312388.n4.nabble.com/How-can-I-cache-images-served-as-byte-stream-directly-from-a-Controller-td2238393.html 
//	En ese caso siempre lo traigo de disco (rapido!!!) pero quizas evite mandarlo x la red!! q es lo bueno de esto no>!>!>!!!! 
//		(
//			la idea es minimizar esto: 
//				response.outputStream << imagen.readBytes()
//			!!!!!
//		)	
//	def isRequestedFileModified(String lastModifiedString, Long lastMod) {
//		if (!lastModifiedString) {
//			return true
//		}
//		Long asLong = new Long(lastModifiedString)
//		
//		if (asLong != lastMod) {
//			return true
//		}
//		return false
//	}	

		
//	def imagen = {
//		def campoId = params.id
//		def nombre = params.nombre
//		
//		withCacheHeaders {
//			Campo campo = Campo.get(campoId)
//			if (!campo) {
//				log.info("El campo ${campoId} no existe ... no se muestra ninguna imagen ...")
//				return
//			}
//			Long lastModifiedLong = campo["${nombre}LastModified"]
//			Long lastModifiedLongAux = (lastModifiedLong ?: 0l )
//			delegate.lastModified {
//				lastModifiedLongAux
//			}
//			generate {
//				log.info("No se cacheo y el lastModified q se pone sera: ${lastModifiedLongAux}")
//				
//				def ubicacionImagenes = utilService.ubicacionImagenes()
//				def imagen = campo["${nombre}"]
//				def campoDirectorio = new File(ubicacionImagenes, File.separator + "campos" + File.separator + "${campoId}")
//				File imagenFile = new File(campoDirectorio, imagen)
//				if (!campoDirectorio || !campoDirectorio.exists() || !imagenFile || !imagenFile.exists()) {
//					log.info("El directorio '" + campoDirectorio?.getAbsolutePath() + "'. Imagen:${imagen} no existe ... no se muestra ninguna imagen ...")
//					return
//				}
//				log.info("No se cacheo y el lastModified q se pone sera: ${lastModifiedLong}")
//				response.outputStream << imagenFile.readBytes()
////				response.outputStream.flush()
//				return
//			}
//			
//		}
		
		
		
		
		
//		String lastModifiedString = request.getHeader("Last-Modified")
//		
//		log.info("lastModifiedString: ${lastModifiedString}")
//		
//		if (isRequestedFileModified(lastModifiedString, lastModifiedLong)) {
//			def imagen = campo["${params.nombre}"]
//			def campoDirectorio = new File(ubicacionImagenes, File.separator + "campos" + File.separator + "${params.id}")
//			File imagenFile = new File(campoDirectorio, imagen)
//			if (!campoDirectorio || !campoDirectorio.exists() || !imagenFile || !imagenFile.exists()) {
//				log.info("El directorio '" + campoDirectorio?.getAbsolutePath() + "'. Imagen:${imagen} no existe ... no se muestra ninguna imagen ...")
//				return
//			}
//			log.info("No se cacheo y el lastModified q se pone sera: ${lastModifiedLong}")
//			
//			Long lastModifiedLongAux = (lastModifiedLong ?: 0l )
//			
//			response.setDateHeader("Last-Modified", lastModifiedLongAux)
//			response.setHeader("ETag", "W/\"" + MD5Codec.encode( lastModifiedLongAux.toString() ) + "\"")
//			
//			response.outputStream << imagenFile.readBytes()
//			response.outputStream.flush()
//			return
//		}
//		else {
//			
//			log.info("Cached!!!!!")
//			
//			response.sendStatus(304)
//			return
//		}
		
	
		
//		Este tiene q recibir el nombre de la propiedad a usar y luego hare
//		campo[propiedad]
//		
//		def bbb = campoSantJoan["nombre"]
//		
//		Esto x ejemplo da "Sant Joan" ;)

	def imagen() {
		def ubicacionImagenes = utilService.ubicacionImagenes()
		
		def campoDirectorio = new File(ubicacionImagenes, File.separator + "campos" + File.separator + "${params.id}")
		def nombre = params.nombre;	
		File imagen = new File(campoDirectorio, nombre)
		if (!campoDirectorio || !campoDirectorio.exists() || !imagen || !imagen.exists()) {
			log.info("El directorio '" + campoDirectorio?.getAbsolutePath() + "'. Imagen:${nombre} no existe ... no se muestra ninguna imagen ...")
			return
		}
		
		try {
			response.outputStream << imagen.readBytes()
			response.outputStream.flush()
		}
		catch (Exception e) {
			log.warn("Ocurrio un problema mostrando la imagen de campo. Mensaje: ${e}")
		}
		return 
	}
	
}
