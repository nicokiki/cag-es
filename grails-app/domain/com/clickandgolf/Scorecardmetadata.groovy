package com.clickandgolf

// El nombre es en minuscula x la seguridad
class Scorecardmetadata {

	// Sirve para identificarlo facilmente (<campo>_sc-md)
	String nombre

	Integer parHoyo1 = 4
	Integer parHoyo2 = 4
	Integer parHoyo3 = 4
	Integer parHoyo4 = 4
	Integer parHoyo5 = 4
	Integer parHoyo6 = 4
	Integer parHoyo7 = 4
	Integer parHoyo8 = 4
	Integer parHoyo9 = 4
	Integer parHoyo10 = 0
	Integer parHoyo11 = 0
	Integer parHoyo12 = 0
	Integer parHoyo13 = 0
	Integer parHoyo14 = 0
	Integer parHoyo15 = 0
	Integer parHoyo16 = 0
	Integer parHoyo17 = 0
	Integer parHoyo18 = 0
	
    static constraints = {
		nombre (blank: false, maxSize:100)
		
		parHoyo1(nullable: false, min: 0, max: 5)
		parHoyo2(nullable: false, min: 0, max: 5)
		parHoyo3(nullable: false, min: 0, max: 5)
		parHoyo4(nullable: false, min: 0, max: 5)
		parHoyo5(nullable: false, min: 0, max: 5)
		parHoyo6(nullable: false, min: 0, max: 5)
		parHoyo7(nullable: false, min: 0, max: 5)
		parHoyo7(nullable: false, min: 0, max: 5)
		parHoyo9(nullable: false, min: 0, max: 5)
		parHoyo10(nullable: false, min: 0, max: 5)
		parHoyo11(nullable: false, min: 0, max: 5)
		parHoyo12(nullable: false, min: 0, max: 5)
		parHoyo13(nullable: false, min: 0, max: 5)
		parHoyo14(nullable: false, min: 0, max: 5)
		parHoyo15(nullable: false, min: 0, max: 5)
		parHoyo16(nullable: false, min: 0, max: 5)
		parHoyo17(nullable: false, min: 0, max: 5)
		parHoyo18(nullable: false, min: 0, max: 5)
    }
	
	String toString() {return nombre};
	
	static mapping = {
		cache true
	}
	
	boolean is18() {
		return (parHoyo10 != 0);
	}
	
}
