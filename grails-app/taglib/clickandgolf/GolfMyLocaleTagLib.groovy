package clickandgolf

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest;
import org.codehaus.groovy.grails.web.util.WebUtils;
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Creado en base a http://grails.1312388.n4.nabble.com/PageRenderer-and-Locale-td4637420.html 
 * para poner en los .gsps de email
 * 
 * @author gonznic
 */
class GolfMyLocaleTagLib {

	static namespace = "golflocale"
	
	private static final String LOCALE_RESOLVER_KEY ="org.springframework.web.servlet.DispatcherServlet.LOCALE_RESOLVER"
	
	def resolveLanguage = {
			attrs ->
			
	   def lang = attrs.locale?:pageScope.locale
			
		if(lang) {
			GrailsWebRequest grailsWebRequest = WebUtils.retrieveGrailsWebRequest()
			def request = grailsWebRequest.request
			def response = grailsWebRequest.response
//			def localeResolver = RequestContextUtils.getLocaleResolver(request)
//			if(localeResolver == null) {
				def localeResolver = new CustomLocaleResolver()
				request.setAttribute(LOCALE_RESOLVER_KEY,localeResolver)
				localeResolver.setLocale(request,response,lang)
				// Esta es la manera en la q funciona y punto
//			}
//			localeResolver.setLocale(request,response,lang)
			
		}
	
	}

//	def resolveLanguage = { attrs ->
//		def locale  = attrs.locale ?: pageScope.locale
//
//		if(locale) {
//			def grailsWebRequest = WebUtils.retrieveGrailsWebRequest()
//			def request = grailsWebRequest.request
//			def response = grailsWebRequest.response
//			def localeResolver = RequestContextUtils.getLocaleResolver(request)
//			if(localeResolver == null) {
//				localeResolver = new CustomLocaleResolver()
//				request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, localeResolver)
//			}
//			localeResolver.setLocale(request, response, locale)
//		}
//	}

}
	private class CustomLocaleResolver implements LocaleResolver {
		Locale locale

		Locale resolveLocale(HttpServletRequest request) {
			return locale
		}

		void setLocale(HttpServletRequest request, HttpServletResponse httpServletRequest, Locale locale){
			this.locale = locale
		}
	}

