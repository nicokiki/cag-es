package clickandgolf

class GolfStaticResourcesTagLib {
	
	// Change this to match your application
	static namespace = "wthr"
 
	def grailsApplication
	
	def staticResource = { attrs ->
		def dir = (attrs.id) ? attrs.id : ""
		def file = (attrs.nombre) ? attrs.nombre: ""
 
		def url = "${grailsApplication.config.staticresources.url}/${dir}/${file}"
		out << url
	}
 

}
