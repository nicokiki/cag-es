package com.clickandgolf

class Scorecard {

	static final BLANCO = "BLANCO"
	static final AMARILLO = "AMARILLO"
	static final ROJO = "ROJO"
	static final AZUL = "AZUL"

	// Sirve para identificarlo facilmente (<campo>_sc-<color>)
	String nombre
	String color = BLANCO
	
	String metrosHoyo1
	String metrosHoyo2
	String metrosHoyo3
	String metrosHoyo4
	String metrosHoyo5
	String metrosHoyo6
	String metrosHoyo7
	String metrosHoyo8
	String metrosHoyo9
	String metrosHoyo10
	String metrosHoyo11
	String metrosHoyo12
	String metrosHoyo13
	String metrosHoyo14
	String metrosHoyo15
	String metrosHoyo16
	String metrosHoyo17
	String metrosHoyo18

	
    static constraints = {
		nombre (blank: false, maxSize:100)
		
		metrosHoyo1 (blank: true, maxSize:5)
		metrosHoyo2 (blank: true, maxSize:5)
		metrosHoyo3 (blank: true, maxSize:5)
		metrosHoyo4 (blank: true, maxSize:5)
		metrosHoyo5 (blank: true, maxSize:5)
		metrosHoyo6 (blank: true, maxSize:5)
		metrosHoyo7 (blank: true, maxSize:5)
		metrosHoyo8 (blank: true, maxSize:5)
		metrosHoyo9 (blank: true, maxSize:5)
		metrosHoyo10 (blank: true, maxSize:5)
		metrosHoyo11 (blank: true, maxSize:5)
		metrosHoyo12 (blank: true, maxSize:5)
		metrosHoyo13 (blank: true, maxSize:5)
		metrosHoyo14 (blank: true, maxSize:5)
		metrosHoyo15 (blank: true, maxSize:5)
		metrosHoyo16 (blank: true, maxSize:5)
		metrosHoyo17 (blank: true, maxSize:5)
		metrosHoyo18 (blank: true, maxSize:5)
		
		color inList: [Scorecard.BLANCO, Scorecard.AMARILLO, Scorecard.ROJO , Scorecard.AZUL]
    }
	
	static mapping = {
		cache true
	}
	
	String toString() {return nombre};
	
}
