package com.clickandgolf.providers

import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

import com.sun.jersey.api.ParamException

/**
 * http://stackoverflow.com/questions/583973/jax-rs-jersey-how-to-customize-error-handling
 * 
 * @author ngonzalez
 */
@Provider
@Produces(['text/plain'])
class ParamExceptionMapperProvider implements ExceptionMapper<ParamException> {

	public Response toResponse(ParamException exception){
		def msg = "Param-name:'" + exception.getParameterName() + "', Param-type:'" + exception.getParameterType() + "' incorrect. Please analyse the incompatibility with the latest docs."
		return Response.status(Response.Status.BAD_REQUEST).
		entity(msg).
		build();
	}
}
