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
class OlvideCambioCommand {

	// El email estara ahi como hidden
	String email
	String passwordNew
	String passwordNewConfirm
	
	static constraints = {
		passwordNew blank: false
		passwordNewConfirm blank: false, validator:{val, obj ->
			if(!(val == obj.passwordNew)) {
				return 'password.match.message'
			}
		}
	}

}
