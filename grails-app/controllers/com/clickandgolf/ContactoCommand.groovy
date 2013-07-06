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
class ContactoCommand {

	String email
	String nombre
	String asunto
	String mensaje
	String captcha
	
	static constraints = {
		email email: true, blank: false
		nombre blank: false
		asunto blank: false
		mensaje blank: false
		captcha blank: false
	}

}
