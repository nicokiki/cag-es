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
class OlvideCommand {

	String j_username
	
	static constraints = {
		j_username email: true, blank: false
	}

}
