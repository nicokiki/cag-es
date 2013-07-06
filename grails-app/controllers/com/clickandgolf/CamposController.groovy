package com.clickandgolf

import grails.plugins.springsecurity.Secured

import com.clickandgolf.seguridad.User


/**
 * Solo los ADMIN pueden autorizar esto a mi criterio.<br>
 * 
 * Lo unico q pueden administrar los CAMPO son los green-fees de los campos pero no dar de alta los campos ni 
 * modificar la descripcion ni nada.
 *  
 * @author manzano
 */
//@Secured(['ROLE_ADMIN', 'ROLE_CAMPO', 'ROLE_FACEBOOK'])
@Secured(['ROLE_ADMIN'])
class CamposController {
	
	def springSecurityService
	def campoService
	def utilService
	

	/* Este es el controller de campos para admins de campos o admin
	 * Es MUY importante q NO se usen nombres compuestos xq el security plugin NO funciona 
	 * con hyphenated (http://jira.grails.org/browse/GPSPRINGSECURITYCORE-162)
	 */
	
	// NO SE USA
	def editar = {
		log.info("Por editar un campo ... metodo seguro ...")
		
		def user = User.get(springSecurityService.principal.id)
		
		log.info(user.username)
		
	}
	
	/**
	 * Esto es solo xa ser direccionado a la vista en particular
	 */
	def nuevo = {
		String view = 'nuevo'
		render view: view, model: [ubicaciones: Ubicacion.all, scorecardsMetadata: Scorecardmetadata.all, scorecards: Scorecard.all]
	}

	
	def listar = {
		log.info("Por listar los campos para poder administrarlos ...")
		
		def user = User.get(springSecurityService.principal.id)
		if (user.isAdmin()) {
			log.info("Por listar todos los campos por ser ADMIN ...")
		
			params.max = Math.min(params.max ? params.int('max') : 5, 100)
			def model = [campos: Campo.list(params), camposTotal: Campo.count()]
			
			if (request.xhr) {
				log.debug("AJAX request recibido ...")
				render(template: "campos", model: model)
			}
			else {
				log.debug("No AJAX ...")
				model
			}
		}
		else {
			// Nunca deberia entrar aca xq es solo xa ADMINs este controller
			log.info("Por listar el UNICO q campo q puede administrar xq NO es ADMIN ...")
			
			def campo = user.getCampo()
			List<Campo> campos = new ArrayList<Campo>();
			campos.add(campo);
			
			def model = [campos: campos, camposTotal: 1]
			
			if (request.xhr) {
				log.debug("AJAX request recibido ...")
				render(template: "campos", model: model)
			}
			else {
				log.debug("No AJAX ...")
				model
			}
			
		}
	}
	
	
	def ver = {
		def campo = Campo.get(params.id)
		log.info("Por ver el campo '" + campo.getNombre() + "' para poder administrarlo ...")
		
		if (!campo) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'campo.label', default: 'Campo'), params.id])
			redirect(action: "listar")
			return
		}

		[campo: campo, ubicaciones: Ubicacion.all, scorecardsMetadata: Scorecardmetadata.all, scorecards: Scorecard.all]
	}
	
	def modificar = {
		def campo = Campo.get(params.id)
		def campoCambios = new Campo(params)
		
		def ubicacionImagenes = utilService.ubicacionImagenes()
		
		def uploadedImagenPrincipal = request.getFile('imagenPrincipal')
		if (null != uploadedImagenPrincipal && !uploadedImagenPrincipal.empty) {
			log.info("Size de la imagen principal: " + uploadedImagenPrincipal.size)

			def campoDirectorio = new File(ubicacionImagenes, File.separator + "campos" + File.separator + "${params.id}")
			campoDirectorio.mkdirs()
			log.info("Se creo el directorio con path absoluto: '" + campoDirectorio.absolutePath + "' ...")
			File newFile = new File( campoDirectorio, uploadedImagenPrincipal.originalFilename)
			uploadedImagenPrincipal.transferTo( newFile )
			
			log.info("uploadedImagenPrincipal.originalFilename: '" + uploadedImagenPrincipal.originalFilename + "' ...");
			campo.imagenPrincipal = uploadedImagenPrincipal.originalFilename
			campo.imagenPrincipalLastModified = newFile.lastModified()
		}
		if (params.imagenPrincipalBorrar) {
			log.info("La imagen principal asociada se eliminara ...")
			campo.imagenPrincipal = null
			campo.imagenPrincipalLastModified = null
		}
		def uploadedImagenPromocionSilver = request.getFile('imagenPromocionSilver')
		if (null != uploadedImagenPromocionSilver && !uploadedImagenPromocionSilver.empty) {
			log.info("Size de la imagen uploadedImagenPromocionSilver: " + uploadedImagenPromocionSilver.size)

			def campoDirectorio = new File(ubicacionImagenes, File.separator + "campos" + File.separator + "${params.id}")
			campoDirectorio.mkdirs()
			log.info("Se creo el directorio con path absoluto: '" + campoDirectorio.absolutePath + "' ...")
			
			File newFile = new File( campoDirectorio, uploadedImagenPromocionSilver.originalFilename) 
			uploadedImagenPromocionSilver.transferTo(newFile )
			
			log.info("uploadedImagenPromocionSilver.originalFilename: '" + uploadedImagenPromocionSilver.originalFilename + "' ...");
			campo.imagenPromocionSilver = uploadedImagenPromocionSilver.originalFilename
			campo.imagenPromocionSilverLastModified = newFile.lastModified()
		}
		if (params.imagenPromocionSilverBorrar) {
			log.info("La imagen promocion Silver asociada se eliminara ...")
			campo.imagenPromocionSilver = null
			campo.imagenPromocionSilverLastModified = null
		}
		def uploadedImagenPromocionGold = request.getFile('imagenPromocionGold')
		if (null != uploadedImagenPromocionGold && !uploadedImagenPromocionGold.empty) {
			log.info("Size de la imagen uploadedImagenPromocionGold: " + uploadedImagenPromocionGold.size)

			def campoDirectorio = new File(ubicacionImagenes, File.separator + "campos" + File.separator + "${params.id}")
			campoDirectorio.mkdirs()
			log.info("Se creo el directorio con path absoluto: '" + campoDirectorio.absolutePath + "' ...")
			
			File newFile = new File( campoDirectorio, uploadedImagenPromocionGold.originalFilename)
			uploadedImagenPromocionGold.transferTo(newFile )
			
			log.info("uploadedImagenPromocionGold.originalFilename: '" + uploadedImagenPromocionGold.originalFilename + "' ...");
			campo.imagenPromocionGold = uploadedImagenPromocionGold.originalFilename
			campo.imagenPromocionGoldLastModified = newFile.lastModified()
		}
		if (params.imagenPromocionGoldBorrar) {
			log.info("La imagen promocion gold asociada se eliminara ...")
			campo.imagenPromocionGold = null
			campo.imagenPromocionGoldLastModified = null
		}
		def uploadedImagenSecundaria = request.getFile('imagenSecundaria')
		if (null != uploadedImagenSecundaria && !uploadedImagenSecundaria.empty) {
			log.info("Size de la imagen uploadedImagenSecundaria: " + uploadedImagenSecundaria.size)

			def campoDirectorio = new File(ubicacionImagenes, File.separator + "campos" + File.separator + "${params.id}")
			campoDirectorio.mkdirs()
			log.info("Se creo el directorio con path absoluto: '" + campoDirectorio.absolutePath + "' ...")
			
			File newFile = new File( campoDirectorio, uploadedImagenSecundaria.originalFilename)
			uploadedImagenSecundaria.transferTo( newFile)
			
			log.info("uploadedImagenSecundaria.originalFilename: '" + uploadedImagenSecundaria.originalFilename + "' ...");
			campo.imagenSecundaria = uploadedImagenSecundaria.originalFilename
			campo.imagenSecundariaLastModified = newFile.lastModified()
		}
		if (params.imagenSecundariaBorrar) {
			log.info("La imagen secundaria asociada se eliminara ...")
			campo.imagenSecundaria = null
			campo.imagenSecundariaLastModified = null
		}
		def uploadedImagenExtra = request.getFile('imagenExtra')
		if (null != uploadedImagenExtra && !uploadedImagenExtra.empty) {
			log.info("Size de la imagen uploadedImagenExtra: " + uploadedImagenExtra.size)

			def campoDirectorio = new File(ubicacionImagenes, File.separator + "campos" + File.separator + "${params.id}")
			campoDirectorio.mkdirs()
			log.info("Se creo el directorio con path absoluto: '" + campoDirectorio.absolutePath + "' ...")
			
			uploadedImagenExtra.transferTo( new File( campoDirectorio, uploadedImagenExtra.originalFilename))
			
			log.info("uploadedImagenExtra.originalFilename: '" + uploadedImagenExtra.originalFilename + "' ...");
			campo.imagenExtra = uploadedImagenExtra.originalFilename
		}
		if (params.imagenExtraBorrar) {
			log.info("La imagen extra asociada se eliminara ...")
			campo.imagenExtra = null
		}

		def ubicacion = Ubicacion.get(params.ubicacion)
		def scorecardmetadata = Scorecardmetadata.get(params.scorecardmetadata)
		def blancas = null
		if (params.blancas && !"null".equals(params.blancas)) {
			blancas = Scorecard.get(params.blancas)
		}
		def amarillas = null
		if (params.amarillas && !"null".equals(params.amarillas)) {
			amarillas = Scorecard.get(params.amarillas)
		}
		def rojas = null
		if (params.rojas && !"null".equals(params.rojas)) {
			rojas = Scorecard.get(params.rojas)
		}
		def azules = null
		if (params.azules && !"null".equals(params.azules)) {
			azules = Scorecard.get(params.azules)
		}

		campo.ubicacion = ubicacion
		campo.scorecardmetadata = scorecardmetadata
		campo.blancas = blancas
		campo.amarillas = amarillas
		campo.rojas = rojas
		campo.azules = azules
		campo.tipo = campoCambios.tipo
		campo.email = campoCambios.email
		campo.estado = campoCambios.estado
		campo.nombre = campoCambios.nombre
		campo.hyphenatedNombre = campoCambios.hyphenatedNombre
		campo.descripcion = campoCambios.descripcion
		campo.descripcionQuotted = campoCambios.descripcionQuotted
		campo.comoLlegar = campoCambios.comoLlegar
		campo.coordenadasGps = campoCambios.coordenadasGps
		campo.linkCampo = campoCambios.linkCampo
		campo.linkExtra = campoCambios.linkExtra
		campo.longitud = campoCambios.longitud
		campo.latitud = campoCambios.latitud
		campo.fee = campoCambios.fee
		campo.requiereLicencia = campoCambios.requiereLicencia
		campo.normasLink = campoCambios.normasLink
		campo.normasSeccion = campoCambios.normasSeccion
		campo.notasReserva = campoCambios.notasReserva
		campo.telefono = campoCambios.telefono
		campo.direccion = campoCambios.direccion
		campo.emailContacto = campoCambios.emailContacto
		
		campo.save(true)
		
		log.info("Campo '" + campo + "' modidicado ...")
		redirect(action:listar)
	}

	def guardar = {
		def campo = new Campo(params)
		if (!campo.descripcion) {
			campo.descripcion = "-"
		}
		def ubicacion = Ubicacion.get(params.ubicacion)
		if (!ubicacion) {
			log.warn("No se encontro la ubicacion ...")
		}
		campo.setUbicacion(ubicacion)

		def scorecardmetadata = Scorecardmetadata.get(params.scorecardmetadata)
		if (!scorecardmetadata) {
			log.warn("No se encontro la scorecardmetadata ...")
		}
		campo.setScorecardmetadata(scorecardmetadata)

		def blancas = null
		if (params.blancas && !"null".equals(params.blancas)) {
			blancas = Scorecard.get(params.blancas)
		}
		campo.setBlancas(blancas)
		def amarillas = null
		if (params.amarillas && !"null".equals(params.amarillas)) {
			amarillas = Scorecard.get(params.amarillas)
		}
		campo.setAmarillas(amarillas)
		def rojas = null
		if (params.rojas && !"null".equals(params.rojas)) {
			rojas = Scorecard.get(params.rojas)
		}
		campo.setRojas(rojas)
		def azules = null
		if (params.azules && !"null".equals(params.azules)) {
			azules = Scorecard.get(params.azules)
		}
		campo.setAzules(azules)

		// Salvo el campo para obtener un ID, luego le hare un update con los nombres de las imagenes!
		campo.save(flush: true)
//		campoService.save(campo)
		log.info("Campo '" + campo + "' guardado ...")
		
		boolean hasAlgunaImagen = false
		boolean hasPrincipal = false
		def uploadedImagenPrincipal = request.getFile('imagenPrincipal')
		if (null != uploadedImagenPrincipal && !uploadedImagenPrincipal.empty) {
			log.info("Size de la imagen principal: " + uploadedImagenPrincipal.size)
			hasAlgunaImagen = true
			hasPrincipal = true
			
			log.info("uploadedImagenPrincipal.originalFilename: '" + uploadedImagenPrincipal.originalFilename + "' ...");
			campo.imagenPrincipal = uploadedImagenPrincipal.originalFilename
		}
		boolean hasSilver = false
		def uploadedImagenPromocionSilver = request.getFile('imagenPromocionSilver')
		if (null != uploadedImagenPromocionSilver && !uploadedImagenPromocionSilver.empty) {
			log.info("Size de la imagen uploadedImagenPromocionSilver: " + uploadedImagenPromocionSilver.size)
			hasAlgunaImagen = true
			hasSilver = true
			
			log.info("uploadedImagenPromocionSilver.originalFilename: '" + uploadedImagenPromocionSilver.originalFilename + "' ...");
			campo.imagenPromocionSilver = uploadedImagenPromocionSilver.originalFilename
		}
		boolean hasGold = false
		def uploadedImagenPromocionGold = request.getFile('imagenPromocionGold')
		if (null != uploadedImagenPromocionGold && !uploadedImagenPromocionGold.empty) {
			log.info("Size de la imagen uploadedImagenPromocionGold: " + uploadedImagenPromocionGold.size)
			hasAlgunaImagen = true
			hasGold = true
			
			log.info("uploadedImagenPromocionGold.originalFilename: '" + uploadedImagenPromocionGold.originalFilename + "' ...");
			campo.imagenPromocionGold = uploadedImagenPromocionGold.originalFilename
		}
		boolean hasSecundaria = false
		def uploadedImagenSecundaria = request.getFile('imagenSecundaria')
		if (null != uploadedImagenSecundaria && !uploadedImagenSecundaria.empty) {
			log.info("Size de la imagen uploadedImagenSecundaria: " + uploadedImagenSecundaria.size)
			hasAlgunaImagen = true
			hasSecundaria = true
			
			log.info("uploadedImagenSecundaria.originalFilename: '" + uploadedImagenSecundaria.originalFilename + "' ...");
			campo.imagenSecundaria = uploadedImagenSecundaria.originalFilename
		}
		boolean hasExtra = false
		def uploadedImagenExtra = request.getFile('imagenExtra')
		if (null != uploadedImagenExtra && !uploadedImagenExtra.empty) {
			log.info("Size de la imagen uploadedImagenExtra: " + uploadedImagenExtra.size)
			hasAlgunaImagen = true
			hasExtra = true
			
			log.info("uploadedImagenExtra.originalFilename: '" + uploadedImagenExtra.originalFilename + "' ...");
			campo.imagenExtra = uploadedImagenExtra.originalFilename
		}
		
		
		
		campo.save()
		log.info("Campo '" + campo + "' modidicado con imagenes ya ...")
		
		// Ahora guardo efectivamente las imagenes
		if (hasAlgunaImagen) {
			def ubicacionImagenes = utilService.ubicacionImagenes()
			def campoDirectorio = new File(ubicacionImagenes, File.separator + "campos" + File.separator + "${params.id}")
			campoDirectorio.mkdirs()
			log.info("Se creo el directorio con path absoluto: '" + campoDirectorio.absolutePath + "' ...")
			
			if (hasPrincipal) {
				File newUploadedImagenPrincipal = new File( campoDirectorio, uploadedImagenPrincipal.originalFilename)
				uploadedImagenPrincipal.transferTo(newUploadedImagenPrincipal)
				campo.imagenPrincipalLastModified = newUploadedImagenPrincipal.lastModified()
				log.info("Se movio la imagen principal ...")
			}
			if (hasSilver) {
				File newUploadedImagenPromocionSilver = new File( campoDirectorio, uploadedImagenPromocionSilver.originalFilename)
				uploadedImagenPromocionSilver.transferTo( newUploadedImagenPromocionSilver)
				campo.imagenPromocionSilverLastModified = newUploadedImagenPromocionSilver.lastModified()
				log.info("Se movio la imagen promocion Silver ...")
			}
			if (hasGold) {
				File newUploadedImagenPromocionGold = new File( campoDirectorio, uploadedImagenPromocionGold.originalFilename)
				uploadedImagenPromocionGold.transferTo( newUploadedImagenPromocionGold)
				campo.imagenPromocionGoldLastModified = newUploadedImagenPromocionGold.lastModified()
				log.info("Se movio la imagen promocion gold ...")
			}
			if (hasSecundaria) {
				File newUploadedImagenSecundaria = new File( campoDirectorio, uploadedImagenSecundaria.originalFilename)
				uploadedImagenSecundaria.transferTo(newUploadedImagenSecundaria )
				campo.imagenSecundariaLastModified = newUploadedImagenSecundaria.lastModified()
				log.info("Se movio la imagen secundaria ...")
			}
			if (hasExtra) {
				uploadedImagenExtra.transferTo( new File( campoDirectorio, uploadedImagenExtra.originalFilename))
				log.info("Se movio la imagen extra ...")
			}
		}
		
		redirect(action:listar)
	}

}
