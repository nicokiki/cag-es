import java.util.regex.Matcher;

import javax.servlet.http.Cookie;

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import com.the6hours.grails.springsecurity.facebook.FacebookAuthUtils;

class LogoutController {

	FacebookAuthUtils facebookAuthUtils
	
	/**
	 * Index action. Redirects to the Spring security logout uri.
	 */
	def index = {
		// TODO put any pre-logout code here
		
//		String baseDomain = null
//		List<Cookie> cookies = request.cookies.findAll { 
//			//FacebookAuthUtils.log.debug("Cookier $it.name, expected $cookieName")
//			return it.name ==~ /fb\w*_$facebookAuthUtils.applicationId/
//		}
//  
//		baseDomain = cookies.find {
//		  return it.name == "fbm_\$facebookAuthUtils.applicationId" && it.value ==~ /base_domain=.+/
//		}?.value?.split('=')?.last()
//  
//		if (!baseDomain) {
//		  //Facebook uses invalid cookie format, so sometimes we need to parse it manually
//		  String rawCookie = request.getHeader('Cookie')
//		  log.info("raw cookie: $rawCookie")
//		  if (rawCookie) {
//			Matcher m = rawCookie =~ /fbm_$facebookAuthUtils.applicationId=base_domain=(.+?);/
//			if (m.find()) {
//			  baseDomain = m.group(1)
//			}
//		  }
//		}
//  
//		if (!baseDomain) {
//			def conf = SpringSecurityUtils.securityConfig.facebook
//			if (conf.host && conf.host.length() > 0) {
//				baseDomain = conf.host
//			}
//			log.info("Can't find base domain for Facebook cookie. Use '$baseDomain'")
//		}
//  
//		cookies.each { cookie ->
//		  cookie.maxAge = 0
//		  cookie.path = '/'
//		  cookie.value = ''
//		  if (baseDomain) {
//			cookie.domain = baseDomain
//		  }
//		  response.addCookie(cookie)
//		}
		
		session.invalidate()
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
	}
}
