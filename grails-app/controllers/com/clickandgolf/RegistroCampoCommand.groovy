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
class RegistroCampoCommand {

	String passwordOld
	String passwordNew
	String passwordNewConfirm
	
	static constraints = {
		passwordOld blank: false
		passwordNew blank: false
		passwordNewConfirm blank: false, validator:{val, obj ->
			if(!(val == obj.passwordNew)) {
				return 'password.match.message'
			}
		}
	}

}
