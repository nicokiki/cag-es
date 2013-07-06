package clickandgolf

import org.springframework.web.context.request.RequestContextHolder as RCH

/**
 * Basado en http://devll.wordpress.com/2012/10/11/grails-simple-localelanguage-selector/
 * 
 * Para el primer release le saque el galego porque no es necesario por ahora
 * 
 * @author gonznic
 */
class GolfLocaleTagTagLib {

	static namespace = "golf"
	
//	def localesMap = [ 	"es" : "Espa&ntilde;ol", "en" : "English", "fr" : "Fran&ccedil;ais",
//						"de" : "Deutsch", "ca" : "Catal&agrave;", "gl" : "Galego" ];
	def localesMap = [ 	"es" : "Espa&ntilde;ol",  "ca" : "Catal&agrave;", "en" : "English", 
						"fr" : "Fran&ccedil;ais"];


	def selector = {attrs, body ->
		Locale selectedLocale = RCH.currentRequestAttributes()?.locale ?: Locale.ES
		String selectedLocaleLang = selectedLocale.getLanguage()
		
		String contextPath = request.getContextPath()
		
		log.debug("Por mostrar el lang selector. Seleccionado:'" + selectedLocaleLang + "' ...")

		String langValue = localesMap.get(selectedLocaleLang.toLowerCase())
		
		
		out << '<div class="fancyDrpDwn">'
		out << '	<input class="hidval" name="languageSelect" type="hidden" value="' << langValue << '">'
		out << '	<span class="selectedFromList"><b>' << langValue << '</b></span>'
		out << '	<ul class="exSelect id1" style="display: none;">'
		localesMap.each() { key, value ->
			if (selectedLocaleLang.equalsIgnoreCase(key)) {
				out << '<li style="cursor: default;">'<< value << '</li>'
			}
			else {
//				log.info( link( style: 'color: inherit;', controller: controllerName, action: actionName, params: params + [lang: key], { value } ).toString() )
				out << "<li>" << link( style: 'color: inherit;', controller: controllerName, action: actionName, params: params + [lang: key], { value } ).toString() << "</li>" 
//				out << "<li><a style='color: inherit;' href='${contextPath}/${controllerName}/${actionName}?lang=" << key << "' >"<< value << "</a></li>"
			}
		};
		out << '	</ul>'
		out << '</div>'
	}
					
					
					
	// Este no se usa pero queda por si sirviera mas adelante!				
	def selectorOld = {attrs, body ->
		Locale selectedLocale = RCH.currentRequestAttributes()?.locale ?: Locale.ES
		String selectedLocaleLang = selectedLocale.getLanguage()
		
		log.info("Por mostrar el lang selector. Seleccionado:'" + selectedLocaleLang + "' ...")
		
		out << '<select id="id1" name="languageSelect" class="convertToUl" >'
		
		localesMap.each() { key, value ->
			if (selectedLocaleLang.equalsIgnoreCase(key)) {
				out << '<option value="' << key << '" selected="selected">' << value << '</option>'
			}
			else {
				out << '<option value="' << key << '" onclick="alert("'<< key << '");">' << value << '</option>'
			}
		};
		out << '</select>'
		
		/*
		 * <select id="id1" name="name1" class="convertToUl" >
		 * <option value="Romania" selected="selected">Romania</option> 
		 */
	}
	
}
