package com.clickandgolf

class Ubicacion {

	static TODO_EL_PAIS = "España"
	
	String ciudad
	String provincia
	String region
	// Pais existe para que se pueda buscar por todo el pais. Andorra figura como Region = Andorra 
	String pais = Ubicacion.TODO_EL_PAIS

    static constraints = {
		ciudad maxSize: 200
		provincia maxSize: 200
		region maxSize: 200
		pais maxSize: 40
    }
	
	static mapping = {
		cache 'read-write'
	}
	
	String toString() {return ciudad};
	 
}
