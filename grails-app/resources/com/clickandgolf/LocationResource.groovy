package com.clickandgolf

import javax.ws.rs.DefaultValue
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response

import com.clickandgolf.api.Location

@Path('/api/location')
class LocationResource {
	
	def ubicacionService

    @GET
	@Produces(['application/json'])
    Response getLocations(	@DefaultValue("10") @QueryParam('max') int max, 
							@DefaultValue("0") @QueryParam('offset') int offset) {
		log.info "Por devolver locations. Max: $max, Offset: $offset"
		
		def locations = new ArrayList<Location>()
		def ubicaciones = ubicacionService.all(max, offset) 
		
		ubicaciones.each { ubic ->
			def location = new Location(identifier: ubic?.id, city: ubic?.ciudad, province: ubic?.provincia, region: ubic?.region)
			
			locations.add(location);
		}
		
		log.info "#Locations a devolver: $locations.size"
		
		def locationsResponse = Response.status(Response.Status.OK).entity(locations).build();
		return locationsResponse
    }
}

	