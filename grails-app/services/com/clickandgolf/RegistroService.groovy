package com.clickandgolf

import org.springframework.transaction.annotation.Transactional;

import com.clickandgolf.seguridad.User
import com.clickandgolf.seguridad.UserRole
import com.clickandgolf.seguridad.Role

@Transactional
class RegistroService {

	/** El registro ya ha sido validado. Lo unico que hace es salvar un nuevo usuario 
	 *  en base al registro */
    def save(RegistroCommand registro, String transactionId) {
		// El username es el email
		def nuevoUsuario = new User(username: registro.email, email: registro.email, password: registro.password, enabled: false, validCode: transactionId).save(failOnError: true)
		def userRole = Role.findByAuthority('ROLE_USER')
		log.debug("Dando ROLE_USER al nuevo Usuario ...")
		UserRole.create(nuevoUsuario, userRole, true)
		log.info("Se creo el usuario con el rol ROLE_USER en la BD ...")
		return nuevoUsuario
    }
	
}
