package clickandgolf

import com.the6hours.grails.springsecurity.facebook.FacebookAuthToken
import com.clickandgolf.seguridad.User;
import com.clickandgolf.seguridad.FacebookUser
import com.clickandgolf.seguridad.UserRole;
import com.clickandgolf.seguridad.Role;

import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.FacebookProfile
import org.springframework.social.facebook.api.FacebookLink
import org.springframework.social.facebook.api.impl.FacebookTemplate

class FacebookAuthService {

	// Basado en https://github.com/splix/s2-facebook-example/blob/master/grails-app/services/FacebookAuthService.groovy
	
	def grailsApplication
	
	/**
	* !!! remove X_ to use this method
	*
	* Called when we have a new facebook user, called on first login to create all required
	* data structures
	*
	* @param token facebook authentication token
	*/
   FacebookUser create(FacebookAuthToken token) {
	   log.info("Create domain for facebook user $token.uid")
	   
	   Facebook facebook = new FacebookTemplate(token.accessToken.accessToken)
	   FacebookProfile fbProfile = facebook.userOperations().userProfile
	   String email = fbProfile.getEmail()
	   String nombre = fbProfile.getName()
	   String apellidos = fbProfile.getLastName()
	   String ciudad = ""
	   if (null != fbProfile.getLocation()) {
		   ciudad = fbProfile.getLocation().getName() 
	   } 
	   log.info("Email del usuario de facebook: " + email)
	   
	   User person = new User(
		   email: email,
		   uid : "$token.uid",
//		   username: "$token.uid",
		   username: email,
		   password: '********',
		   enabled: true,
		   accountExpired:  false,
		   accountLocked: false,
		   passwordExpired: false,
		   
		   nombre: nombre,
		   apellidos: apellidos,
		   ciudad: ciudad,
		   
		   accessTokenExpires: token.accessToken.expireAt
	   )
	   person.save(failOnError: true)
	   UserRole.create(person, Role.findByAuthority('ROLE_USER'), true)
	   UserRole.create(person, Role.findByAuthority('ROLE_FACEBOOK'), true)
	   FacebookUser fbUser = new FacebookUser(
			   uid: token.uid,
			   accessToken: token.accessToken.accessToken,
			   accessTokenExpires: token.accessToken.expireAt, 
			   user: person
	   )
	   fbUser.save(failOnError: true)
	   log.info("El usuario de facebook: '" + email + "' ha sido creado con ROLE_USER y ROLE_FACEBOOK")
	   
	   try {
		   def linkClickAndGolf = grailsApplication.config.grails.serverURL
		   // No tengo manera de saber el idioma => lo hago en castellano y al diablo
		   FacebookLink link = new FacebookLink(linkClickAndGolf,
			   "Click & Golf",
			   "El sitio web de Click & Golf",
			   "Click & Golf es un servicio web de reserva de green fees online para campos de Golf y Pitch & Putt.");
		   def msg = "${person.nombre} se ha registrado en Click & Golf!"
		   
		   facebook.feedOperations().postLink(msg, link)
		   log.info("Se posteo en el muro de facebook del usuario en castellano...")
	   }
	   catch (Exception e) {
		   log.info("Puede fallar porque no tenemos el permiso en fb: ${e}")
	   }
	   
	   return fbUser
   }
}
