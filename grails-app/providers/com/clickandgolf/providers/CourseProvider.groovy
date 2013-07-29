package com.clickandgolf.providers

import grails.converters.JSON
import grails.converters.XML

import javax.ws.rs.Produces
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.Provider

import org.grails.jaxrs.support.MessageBodyWriterSupport

import com.clickandgolf.api.Course

@Provider
@Produces(['application/json'])
class CourseProvider extends MessageBodyWriterSupport<Course> {

	void writeTo(Course entity, MultivaluedMap httpHeaders, OutputStream entityStream) {
		// Uso x default UTF-8 sin importar lo q sea x default de grails xq NO quiero configurarlo
		def writer = new OutputStreamWriter(entityStream, "UTF-8")

		def converter = new JSON(entity)
		converter.render(writer)

		// Ver xa mas ejemplos: https://github.com/krasserm/grails-jaxrs/blob/master/src/groovy/org/grails/jaxrs/support/ConverterUtils.groovy
		// No tengo otros Accept (json o xml) xq no llega en httpHeaders :S
	}

}
