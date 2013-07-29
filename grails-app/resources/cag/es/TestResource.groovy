package cag.es

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response
import javax.ws.rs.core.Request

import com.clickandgolf.Campo
import com.sun.jersey.api.core.HttpRequestContext;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl

import com.clickandgolf.GreenFeeService

import com.clickandgolf.api.Course

@Path('/api/test')
class TestResource {
	
	def greenFeeService

	@Path('/greenFees')
	@GET
	@Produces(['application/xml','application/json'])
	Response findGreenFees(@QueryParam('location') String location, @QueryParam('dateAsText') String dateAsText) {
		def locale = new Locale("es","ES")
		
		log.info "Se llamoooo location: $location, dateAsText: $dateAsText, locale: $locale"
		
		def model = greenFeeService.busquedaDeCampos(location, dateAsText, locale)
		def greenFeesInfo = model.greenFeesInfo
		
		def response1 = Response.status(200).entity(greenFeesInfo).build();
		return response1
	}
	
    @GET
    @Produces(['application/xml','application/json'])
    Response getTestRepresentation(@Context HttpServletRequest context) {
		
		log.info("sss " + context.getAuthType())
		
		
//		Response.status(201);
		
		def campo = new Campo(nombre: 'nico');
		
		def response1 = Response.status(200).entity(campo).build();
		
//		def response1 = new ResponseBuilderImpl().status(201).entity(campo).build();
		
		return response1
//		return Response.ok(new Campo(nombre: 'nico'));
    }
	
	@Path('/pepe')
	@GET
	@Produces(['application/xml','application/json'])
	Response getPepe() {
		log.info "llamando a pepe"
		
		def campo = new Course(name: 'nico', latitud: 'saa' );
//		campo.setName("nico");
		
		def response1 = Response.status(200).entity(campo).build();
		
		return response1
	}

}
