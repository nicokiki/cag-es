/**
 * 
 */
package com.clickandgolf

import grails.validation.Validateable;

/**
 * @author manzano
 *
 */
@Validateable
class UsuarioCommand {

	String email
	
	String telefono
	String nombre
	String apellidos
	String ciudad
	
	String licenciaGolf
	String licenciaPP
		
	static constraints = {}

}
