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

		return Response.status(Response.Status.BAD_REQUEST).
		entity("'" + exception.getParameterName() + "' -> incorrect type. Please analyse the incompatibility with the latest docs").
		build();
	}
}
