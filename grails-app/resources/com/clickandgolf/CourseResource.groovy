package com.clickandgolf

import javax.ws.rs.DefaultValue
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response

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
			def course = Course.fromCampo(courseAsCampo, grailsLinkGenerator);
			
			courses.add(course)
		}
		
		log.info "#Campos a devolver: $courses.size"
		
		def coursesResponse = Response.status(Response.Status.OK).entity(courses).build();
		return coursesResponse
    }
	
	@GET
	@Path('/{id}')
	@Produces(['application/json'])
	Response getCourse(@PathParam('id') String id) {
		log.info "Por devolver el campo $id ..."
		
		def campo = Campo.get(id)
		def course = Course.fromCampo(campo, grailsLinkGenerator);
		
		def courseResponse = Response.status(Response.Status.OK).entity(course).build();
		return courseResponse
	}					
						
}

	