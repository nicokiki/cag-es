package com.clickandgolf.seguridad

import com.clickandgolf.Campo;

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	String email
	String uid // Para usuarios de Facebook
	
	// Los admins de campo administran un campo - este es ese campo!
//	static hasOne = [campo:Campo]
	Campo campo
	
	String licenciaGolf
	String licenciaPP

	String telefono
	String nombre
	String apellidos
	String ciudad
	
	// Solo para fb users
	Date accessTokenExpires
	
	/**
	 * Cdo se creen los usuarios, estaran en enabled:false y luego xa pasar a ese enabled se espera
	 * q este en enabled:false && validCode == al recibido
	 */
	String validCode
	
	static constraints = {
		username blank: false, unique: true
		password blank: false
		email(email: true, blank: false)
		
		uid nullable:true
		campo nullable:true, unique:true
		
		licenciaGolf(nullable:true, maxSize: 10)
		licenciaPP(nullable:true, maxSize: 10)
		telefono (nullable: true)
		nombre (nullable: true)
		apellidos (nullable: true)
		ciudad (nullable: true)
		
		accessTokenExpires(nullable: true)
		
		validCode (nullable: true)
	}

	static mapping = {
		password column: '`password`'
	}
	
	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
	
	boolean isAdmin() {
		Set<Role> roles = this.getAuthorities()
		for (Role role : roles) {
			if ('ROLE_ADMIN'.equals(role.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	
	boolean isFacebookUser() {
		Set<Role> roles = this.getAuthorities()
		for (Role role : roles) {
			if ('ROLE_FACEBOOK'.equals(role.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	
	boolean tieneLicenciaGolf() {
		if (licenciaGolf?.trim()) {
			return true
		}
		return false 
	}

	boolean tieneLicenciaPP() {
		if (licenciaPP?.trim()) {
			return true
		}
		return false
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
