package com.clickandgolf.api

class RestAPIFilters {

    def filters = {
        analyseAnyAPI(uri: '/api/**') {
            before = {
				log.info "Usuario '${request?.remoteUser}' invocando API: '${request?.forwardURI}'. Parametros: ${params} ..."
//				log.warn "El request tiene: $request"
            }
        }
    }
}
