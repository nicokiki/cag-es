package com.clickandgolf

import org.joda.time.DateTime


class Promocion {

	static final ACTIVO = "ACTIVO" // Disponible
	static final INACTIVO = "INACTIVO" // NO Disponible
	
	static final GOLD = "GOLD"
	static final SILVER = "SILVER"

	DateTime desde
	DateTime hasta
	Campo campo
	String tipo = GOLD // Por defecto es Gold y NO Silver
	String estado = ACTIVO
	
	String texto_es
	String texto_fr
	String texto_de
	String texto_ca
	String texto_gl
	String texto_en
	
	// Para que sea facil deje un String porque es de una version nueva
	Boolean esBanner = Boolean.FALSE
	String tituloBanner
	String textoBanner
	String linkBanner
	String imgBanner
	String imgGrandeBanner
	
    static constraints = {
		desde (nullable: false)
		hasta (nullable: false, validator:{valor, obj ->
			if (valor.isBefore(obj.desde)) {
				return 'menor.que.desde'
			}
		})
		
		campo (nullable: false)

		tipo inList: [Promocion.GOLD, Promocion.SILVER]
		estado inList: [Promocion.ACTIVO, Promocion.INACTIVO]
		
		texto_es (blank: false, maxSize: 150)
		texto_fr (blank: false, maxSize: 150)
		texto_de (blank: false, maxSize: 150)
		texto_ca (blank: false, maxSize: 150)
		texto_gl (blank: false, maxSize: 150)
		texto_en (blank: false, maxSize: 150)
		
		esBanner (nullable: true)
		tituloBanner (nullable:true, blank:true)
		textoBanner (nullable:true, blank:true)
		linkBanner (nullable:true, blank:true)
		imgBanner (nullable:true, blank:true)
		imgGrandeBanner (nullable:true, blank:true)
    }
	
	static mapping = {
		cache 'read-write'
	}
	
	boolean isForBanner() {
		return (null != esBanner && Boolean.TRUE.equals(esBanner));
	}
	
}
