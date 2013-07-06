package clickandgolf

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.web.context.request.RequestContextHolder as RCH
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import com.clickandgolf.seguridad.User

class GolfFacebookAuthTagLib {

	static namespace = "fb"

	static final String MARKER = 'com.the6hours.grails.springsecurity.facebook.FacebookAuthTagLib#init'

	/** Dependency injection for springSecurityService. */
	def springSecurityService
	def messageSource

	// Este es xa la nueva version del plugin de facebook (spring-security-facebook:0.10.3) q usa Server Side
	Closure serverSideConnect = { attrs, body ->
		log.debug("apply server side connect")
		def writer = getOut()
		def conf = SpringSecurityUtils.securityConfig.facebook
		String contextPath = request.getContextPath()
//		String target = contextPath + conf.filter.redirectFromUrl Cambiado por el paso de la 0.10.3 a la 0.14.2
		String target = contextPath + conf.filter.redirect.redirectFromUrl
		
		def locale = RCH.currentRequestAttributes()?.locale ?: Locale.ES
		String fbLang = assembleFacebookLang(locale)
		def connectImg = "connect_" + fbLang + ".png"
		if ("true".equals(attrs.register)) {
			connectImg = "register_" + fbLang + ".png"
		}
		
		String imgFbUrl = resource(dir: 'images/facebook', file: connectImg)
		
		// SI es parent lo pone en window.parent.location, caso contrario en window.location
		if ("true".equals(attrs.parent)) {
			writer << "<input name=\"fb_name\" type=\"image\" onclick=\"window.parent.location='${target}'\" src=\"${imgFbUrl}\" border=\"0\" alt=\"facebook login\" />"
		}
		else {
			writer << "<input name=\"fb_name\" type=\"image\" onclick=\"window.location='${target}'\" src=\"${imgFbUrl}\" border=\"0\" alt=\"facebook login\" />"
		}
		
		
//		writer << link([uri: target, absolute: true], newBody)
//		
//		String bodyValue = body()
//		if (bodyValue == null || bodyValue.trim().length() == 0) {
//			String imgUrl = resource(dir: 'images/facebook', file: 'connect_en_GB.png')
//			
////			if (attrs.img) {
////				imgUrl = attrs.img
////			} else if (conf.taglib.button.img) {
////				imgUrl = resource(file: conf.taglib.button.img)
////			} else {
////				imgUrl = resource(file: conf.taglib.button.defaultImg, plugin: 'spring-security-facebook')
////			}
//			log.info("imgUrl: ${imgUrl}")
//			
//			bodyValue = img(attrs, imgUrl)
//			log.info("bodyValue: ${bodyValue}")
//			
//			def a = img(dir: 'images/facebook', file: 'connect_en_GB.png')
//			log.info("a: ${a}")
//			
//			
//		}
//		Closure newBody = {
//			return bodyValue
//		}
//		writer << link([uri: target, absolute: true], newBody)
//		
	}
	
	
	def showUserEmail = { attrs ->
		def user = User.get(springSecurityService.principal.id)

		if (null != user) {
			out << user.email

		}
		else {
			log.info("El usuario no se pudo encontrar ...")
		}
	}



	def connect = { attrs, body ->
		def conf = SpringSecurityUtils.securityConfig.facebook

		String contextPath = request.getContextPath()
		// Se puede hacer el login desde el home entonces no hay otro URL donde ir ...
		String requestUrl = contextPath
		// Ese me agarra el requestUrl xa ser usado despues
		SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
		if (null != savedRequest) {
			// Se intentaba ir a otro lado entonces se obtiene ese URL
			requestUrl = savedRequest.getRedirectUrl()
		}

		def locale = RCH.currentRequestAttributes()?.locale ?: Locale.ES

		Boolean init = request.getAttribute(MARKER)
		if (attrs.requirejs != 'false' && (init == null || !init)) {
			//				String lang = conf.language
			String fbLang = assembleFacebookLang(locale)

			def appId = conf.appId
			out << '<div id="fb-root"></div>\n'

			out << '<script>\n'

			out << "window.fbAsyncInit = function() {\n"
			out << "  FB.init({\n"
			out << "    appId  : '${appId}',\n"
			out << "    status : true,\n"
			out << "    cookie : true,\n"
			out << "    xfbml  : true,\n"
			out << "    oauth  : true\n"
			out << "  });\n"

			//				out << "};\n"
			//				out << "FB.Event.subscribe('auth.loginp', function(response) { alert('You liked the URL: ${requestUrl}'); window.location = '${requestUrl}'; }	);\n"

			if ("true".equals(attrs.parent)) {
				out << "FB.Event.subscribe('auth.login', function(response) { window.parent.location='${contextPath}'; }	);\n"
			}
			else {
				out << "FB.Event.subscribe('auth.login', function(response) { window.location='${requestUrl}'; }	);\n"
			}


			out << "};\n"

			out << '(function(d){'
			out << "var js, id = 'facebook-jssdk'; if (d.getElementById(id)) {return;}"
			out << "js = d.createElement('script'); js.id = id; js.async = true;"
			out << "js.src = \"//connect.facebook.net/${fbLang}/all.js\";"
			out << "d.getElementsByTagName('head')[0].appendChild(js);"
			out << '}(document));\n'

			out << '</script>\n'

			request.setAttribute(MARKER, true)
		}
		//			String buttonText = conf.button.text
		String buttonText = message(code: "security.facebook.button.text")
		if (attrs.text) {
			buttonText = attrs.text
		}

		List permissions = []
		if (attrs.permissions) {
			if (attrs.permissions instanceof Collection) {
				permissions = attrs.permissions.findAll {
					it != null
				}.collect {
					it.toString().trim()
				}.findAll {
					it.length() > 0
				}
			} else {
				permissions = attrs.permissions.toString().split(',').collect { it.trim() }
			}
		}

		boolean showFaces = false

		out << "<div class=\"fb-login-button\" data-scope=\"${permissions.join(', ')}\" data-show-faces=\"${showFaces}\">$buttonText</div>"
	}
	
	def socialFbLoader = { attrs ->
		def locale = RCH.currentRequestAttributes()?.locale ?: Locale.ES
		String fbLang = assembleFacebookLang(locale)
		
		def href = attrs.href
		
		out << "<div id=\"fb-root\"></div>"
		out << "	<script>(function(d, s, id) {"
		out << "  		var js, fjs = d.getElementsByTagName(s)[0];"
		out << "	  	if (d.getElementById(id)) return;"
		out << "		js = d.createElement(s); js.id = id;"
		out << " 		js.src = \"//connect.facebook.net/${fbLang}/all.js#xfbml=1\";"
		out << " 		fjs.parentNode.insertBefore(js, fjs);"
		out << "		}(document, 'script', 'facebook-jssdk'));"
		out << "	</script>"
		
		out << "	<div class=\"fb-like\" data-send=\"false\" data-href=\"${href}\" data-layout=\"button_count\" data-width=\"100\" data-show-faces=\"true\"></div>"
	}
	
	def socialTwitter = { attrs ->
		def locale = RCH.currentRequestAttributes()?.locale ?: Locale.ES
		def href = attrs.href
		
		out << "<a href=\"https://twitter.com/share\" class=\"twitter-share-button\" data-url=\"${href}\" data-via=\"clickandgolf_es\" "
		out << assembleTwitterLangPart(locale)
		out << "<script>!function(d,s,id){"
		out << "	var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;"
		out << "js.src=\"//platform.twitter.com/widgets.js\";fjs.parentNode.insertBefore(js,fjs);}}(document,\"script\",\"twitter-wjs\");</script>"
	}
	
	def socialGooglePlus =  { attrs ->
		def locale = RCH.currentRequestAttributes()?.locale ?: Locale.ES
		def href = attrs.href
		
		String googlePlusLang = assembleGooglePlusLang(locale)
		
		out << "<div class=\"g-plus\" data-action=\"share\" data-href=\"${href}\" data-width=\"180\" ></div>"
		out << "<script type=\"text/javascript\">"
		out << "	window.___gcfg = {lang: ${googlePlusLang}};"
		out << " "
		out << "(function() {"
		out << "	var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;"
		out << "	po.src = 'https://apis.google.com/js/plusone.js';"
		out << "	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);"
		out << "})();"
		out << "</script>"
	}

	private String assembleFacebookLang(Locale locale) {
		String lang = locale
		// Por defecto es en castellano
		String fbLang = "es_ES"
		if (!"es".equalsIgnoreCase(lang)) {
			if ("fr".equalsIgnoreCase(lang)) {
				fbLang = "fr_FR";
			}
			else if ("de".equalsIgnoreCase(lang)) {
				fbLang = "de_DE";
			}
			else if ("ca".equalsIgnoreCase(lang)) {
				fbLang = "ca_ES";
			}
			else if ("gl".equalsIgnoreCase(lang)) {
				fbLang = "gl_ES";
			}
			else if ("en".equalsIgnoreCase(lang)) {
				fbLang = "en_GB";
			}
		}
		return fbLang
	}

	private String assembleTwitterLangPart(Locale locale) {
		String lang = locale
		// Por defecto es en castellano
		String twitterLangPart = " data-lang=\"es\">Twittear</a>"
		if (!"es".equalsIgnoreCase(lang)) {
			if ("fr".equalsIgnoreCase(lang)) {
				twitterLangPart = " data-lang=\"fr\">Twetter</a>"
			}
			else if ("de".equalsIgnoreCase(lang)) {
				twitterLangPart = " data-lang=\"de\">Twittern</a>"
			}
			else if ("ca".equalsIgnoreCase(lang)) {
				twitterLangPart = " data-lang=\"ca\">Tuiteja</a>"
			}
			else if ("gl".equalsIgnoreCase(lang)) {
				// Como en castellano
				twitterLangPart = " data-lang=\"es\">Twittear</a>"
			}
			else if ("en".equalsIgnoreCase(lang)) {
				twitterLangPart = " >Tweet</a>"
			}
		}
		return twitterLangPart
	}
	
	private String assembleGooglePlusLang(Locale locale) {
		String lang = locale
		// Por defecto es en castellano
		String googlePlusLang = "'es'"
		if (!"es".equalsIgnoreCase(lang)) {
			if ("fr".equalsIgnoreCase(lang)) {
				googlePlusLang = "'fr'";
			}
			else if ("de".equalsIgnoreCase(lang)) {
				googlePlusLang = "'de'";
			}
			else if ("ca".equalsIgnoreCase(lang)) {
				googlePlusLang = "'ca'";
			}
			else if ("gl".equalsIgnoreCase(lang)) {
				googlePlusLang = "'gl'";
			}
			else if ("en".equalsIgnoreCase(lang)) {
				googlePlusLang = "'en-GB'";
			}
		}
		return googlePlusLang
	}

}
