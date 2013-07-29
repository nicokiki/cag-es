package com.clickandgolf

import groovy.util.ObjectGraphBuilder.IdentifierResolver;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response

import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import com.clickandgolf.api.Course

@Path('/api/course')
class CourseResource {
	
	def campoService
	def grailsLinkGenerator

    @GET
	@Produces(['application/json'])
    Response getCourses(@DefaultValue("5") @QueryParam('max') int max, 
						@DefaultValue("0") @QueryParam('offset') int offset) {
		log.info "Por devolver campos. Max: $max, Offset: $offset"
		
		def courses = new ArrayList<Course>()
		def coursesAsCampos = campoService.getCampos(max, offset)
		coursesAsCampos.each { courseAsCampo ->
			def course = new Course(name: courseAsCampo?.nombre, type: courseAsCampo?.tipo, city: courseAsCampo?.ubicacion?.ciudad, province: courseAsCampo?.ubicacion?.provincia, region: courseAsCampo?.ubicacion?.region, 
									latitud: courseAsCampo?.latitud, longitud: courseAsCampo?.longitud,
									identifier: courseAsCampo?.id)
			course.uri = grailsLinkGenerator.link(absolute: true, mapping: 'campo', params: [id:courseAsCampo?.id, nombre:courseAsCampo?.hyphenatedNombre])
		
			courses.add(course)
		}
		
		log.info "#Campos a devolver: $courses.size"
		
		def coursesResponse = Response.status(200).entity(courses).build();
		return coursesResponse
    }
}

	