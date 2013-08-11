package com.clickandgolf

class UbicacionService {

    def all(int max, int offset) {
		def ubicaciones = Ubicacion.createCriteria().list(max:max, offset:offset) {
			maxResults(max)
			firstResult(offset)
			order("region")
			order("ciudad")
		}
		return ubicaciones;
    }
	
}
