package com.clickandgolf

import javax.ws.rs.DefaultValue
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response

import com.sun.jersey.api.ParamException.QueryParamException;

import com.clickandgolf.api.GreenFeeOption
import com.clickandgolf.api.GreenFee


@Path('/api/greenfee')
class GreenFeeResource {
	
	static final typeValues = ['city', 'province', 'region']
	static final typeMappings = [city:'ciudad', province:'provincia', region:'region']
	
	
	def greenFeeService
	def grailsLinkGenerator

    @GET
	@Path('/options')
	@Produces(['application/json'])
    Response getOptions(@DefaultValue("250") @QueryParam('maxPrice') double maxPrice,
						@QueryParam('locationType') String locationType,
						@QueryParam('locationName') String locationName,
						@QueryParam('dateAsText') String dateAsText,
						@QueryParam('courseType') String courseType,
						@QueryParam('courseHoles') String courseHoles) {
		/*
		 * locationName tiene que ser:
		 * 	region:nombre-region
		 * 		o
		 * 	ciudad:nombre-ciudad
		 * 		0
		 * 	provincia:nombre-provincia				
		 */
		def locationAux = ""
		if (locationType && !(locationType in typeValues) ) {
			log.info "La locationType recibida ($locationType) no es una de $typeValues. Exception lanzada. Cannot proceed."
			throw new QueryParamException(null, "locationType", "Possible values: $typeValues")
		}
		else if (locationType && (locationType in typeValues)) {
			// Vino entonces el campo locationName no puede ser vacio
			if (!locationName || locationName.isEmpty()) {
				log.info "La locationName recibida ($locationName) es vacia. Exception lanzada. Cannot proceed."
				throw new QueryParamException(null, "locationName", "Cannot be empty if locationType is not empty")
			}
			else {
				// Se recibieron ambas y estan bien entonces se arma correctamente lo que espera el servicios, q es lo q esta mas arriba en los comentarios
				locationAux = typeMappings[locationType] + ":" + locationName
			}
		}
										
		log.info "Por devolver opciones de green fees. maxPrice:$maxPrice, locationAux:$locationAux, dateAsText:$dateAsText, courseType:$courseType, courseHoles:$courseHoles"
		def locale = new Locale("es","ES")

		def model = greenFeeService.busquedaDeCamposAvanzada(maxPrice, locationAux, dateAsText, courseType,  courseHoles, locale)
		def greenFeeCommands = model['greenFeesInfo']		
		def options = new ArrayList<GreenFeeOption>()
		greenFeeCommands.each { greenFeeCmd ->
			def option = GreenFeeOption.fromGreenFeeCommand(greenFeeCmd, grailsLinkGenerator)
			
			options.add(option)
		}
		
		def coursesResponse = Response.status(Response.Status.OK).entity(options).build();
		return coursesResponse
    }
						
	@GET
	@Path('/course/{courseId}')
	@Produces(['application/json'])
	Response getGrenFees(
						@PathParam('courseId') String courseId,
						@QueryParam('dateAsText') String dateAsText) {
		if (!dateAsText || dateAsText.isEmpty()) {
			log.info "La dateAsText recibida es null. Exception lanzada. Cannot proceed."
			throw new QueryParamException(null, "dateAsText", "CANNOT be null")
		}
		def campo = Campo.get(courseId)
		
		log.info "Por buscar green fees para el campo $campo para la fecha $dateAsText"
		
		def greenFeesOriginales = greenFeeService.busquedaGreenFees(campo, dateAsText)
		def greenFees = new ArrayList<GreenFee>()
		
		greenFeesOriginales.each { greenFeeOrig ->
			def greenFee = GreenFee.fromGreenFee(greenFeeOrig, grailsLinkGenerator)
			
			greenFees.add(greenFee)
		}
		
		def coursesResponse = Response.status(Response.Status.OK).entity(greenFees).build();
		return coursesResponse
	}
	
}

	