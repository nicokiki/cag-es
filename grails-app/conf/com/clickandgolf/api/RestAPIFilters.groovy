package com.clickandgolf.api

class RestAPIFilters {

    def filters = {
        analyseAnyAPI(uri: '/api/**') {
            before = {
				log.info "Se esta llamando el BEFORE-FILTER para cada llamada de API"
//				log.warn "El request tiene: $request"
            }
        }
    }
}
