/**
 * 
 */
package com.clickandgolf.api

import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import com.clickandgolf.Campo;

/**
 * @author ngonzalez
 *
 */
class Course {
	

	String identifier
	
	String name
	String uri

	String type

	Location location

	String latitude
	String longitude

	static Course fromCampo(Campo campo, LinkGenerator grailsLinkGenerator) {
		def course = new Course(name: campo?.nombre, type: campo?.tipo,
			location: new Location(identifier: campo?.ubicacion?.id, city: campo?.ubicacion?.ciudad, province: campo?.ubicacion?.provincia, region: campo?.ubicacion?.region),
			latitude: campo?.latitud, longitude: campo?.longitud,
			identifier: campo?.id)
		course.uri = grailsLinkGenerator.link(mapping: 'campo', params: [id:campo?.id, nombre:campo?.hyphenatedNombre])
		return course;
	}
	
}
