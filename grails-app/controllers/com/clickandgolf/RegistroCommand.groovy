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
class RegistroCommand {

	String email
	String password
	String passwordConfirm
	
	static constraints = {
		email email: true, blank: false
		password blank: false
		passwordConfirm blank: false, validator:{val, obj ->
			if(!(val == obj.password)) {
				return 'password.match.message'
			}
		}
	}

}
