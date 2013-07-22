package com.clickandgolf

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path('/api/campo')
class CampoResource {

    @GET
    @Produces('text/plain')
    String getCampoRepresentation() {
        'Campo'
    }
}
