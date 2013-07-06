// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
import org.apache.log4j.DailyRollingFileAppender;

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']


// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// En cuanto tiempo se lanzara el job para analizar green fees abandonados
abandoned.enMinutos = 20
paypal.base.url = "http://www.clickandgolf.es"
email.notificaciones.from="Click & Golf <no-responder@clickandgolf.es>"
email.notificaciones.info.to="info@clickandgolf.es"
email.notificaciones.soporte.to="soporte@clickandgolf.es"
email.notificaciones.cancelaciones.to="cancelaciones@clickandgolf.es"

grails.app.context = "/clickandgolf"
// El q viene es el de prod
staticresources.url="http://www.clickandgolf.es/images-campos/imagenes/campos"

simpleCaptcha {
	// font size used in CAPTCHA images
	fontSize = 15
	height = 36
	width = 90
	// number of characters in CAPTCHA text
	length = 7

	// amount of space between the bottom of the CAPTCHA text and the bottom of the CAPTCHA image
	bottomPadding = 10

	// distance between the diagonal lines used to obfuscate the text
	lineSpacing = 10

	// the charcters shown in the CAPTCHA text must be one of the following
	chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

	// this param will be passed as the first argument to this java.awt.Font constructor
	// http://docs.oracle.com/javase/6/docs/api/java/awt/Font.html#Font(java.lang.String,%20int,%20int)
	font = "Serif"
}


// Inicio .::. log4j PRE-configuration
def log4jConsoleLogLevel = org.apache.log4j.Level.WARN
def log4jAppFileLogLevel = org.apache.log4j.Level.INFO

def catalinaBase = System.properties.getProperty('catalina.base')
if (!catalinaBase) {
	catalinaBase = '.'   // just in case
}
def logDirectory = "${catalinaBase}/logs"
// Fin .::. log4j PRE-configuration

// set per-environment serverURL stem for creating absolute links
environments {
	
    development {
		// Inicio.::.LOG4J
		log4jConsoleLogLevel = org.apache.log4j.Level.INFO
		log4jAppFileLogLevel = org.apache.log4j.Level.INFO
		
		// Si hay algo aca es xq quiero q se loggee en el dir de tomcat usando development tuneado
		def dontLogIntoTomcat = System.properties.getProperty('dontLogIntoTomcat') 
		if (dontLogIntoTomcat) {
			logDirectory = "C:\\dev\\golf\\logs"
			println "Se paso el logDirectory a ${logDirectory}"
		}
		
		// Fin.::.LOG4J
		
        grails.logging.jul.usebridge = true
		
		grails.paypal.server = "https://www.sandbox.paypal.com/cgi-bin/webscr"
//		grails.paypal.email = "golf_1336379375_biz@gmail.com"
		grails.paypal.email = "nicogo_1358615748_biz@gmail.com"
		
//		grails.serverURL = "http://83.52.31.1:8080/clickandgolf"
//		grails.serverURL = "http://clickandgolf.no-ip.org/clickandgolf"
//		grails.serverURL = "https://83.52.31.1/clickandgolf"
		
//		grails.serverURL = "http://clickandgolf.es:8080/clickandgolf" // Esto funciona xq tengo eso en /system32/drivers/etc/host
		grails.serverURL = "http://localhost:8080/clickandgolf" // Esto funciona xq tengo eso en /system32/drivers/etc/host
		
		abandoned.enMinutos = 3
		paypal.base.url = "https://83.52.31.1/clickandgolf" // Deberia ser siempre lo mismo q serverURL
		
		imagenes.ubicacion="c:\\dev\\golf\\imagenes\\campos"
		
		staticresources.url="http://www.clickandgolf.es/images-campos/test-imagenes/campos"
		// Configuracion para el Job q genera Green Fees
		grails.plugin.xyz.greenFeeTriggerConfig = {
			cron name:'greenFeeTrigger', startDelay:5000, cronExpression: "0 0/2 * 1/1 * ? *" 
		}
		
		grails {
//			mail {
//			  host = "127.0.0.1"
//			  port = 9990
//			}
			mail {
			  host = "mailtrap.io"
			  port = 2525
			  username = "test-golf"
			  password = "97bdb70c834b9abd"
//			  overrideAddress="test-dev@address.com" // Le cambia todas las direcciones y pone esta antes de mandarlo
			  overrideAddress="Click & Golf <test-dev@address.com>" // Le cambia todas las direcciones y pone esta antes de mandarlo
			}
		 }
		
    }
	
	test {
		grails.logging.jul.usebridge = false
		
		log4jConsoleLogLevel = org.apache.log4j.Level.INFO
		
		// Configuracion para el Job q genera Green Fees
		grails.plugin.xyz.greenFeeTriggerConfig = {
			// Espera 45 segundos para empezar por primera vez
			// Se ejecuta cada 5 minutos
			cron name:'greenFeeTrigger', startDelay:4500, cronExpression: "0 0/5 * 1/1 * ? *"
		}
		// Es necesario armar ROOT.war
		grails.app.context = "/"
		grails.paypal.server = "https://www.sandbox.paypal.com/cgi-bin/webscr"
		grails.paypal.email = "nicogo_1358615748_biz@gmail.com"
				
		grails.serverURL = "http://www.clickandgolf.es" // Esto funciona xq tengo eso en /system32/drivers/etc/host
				
		abandoned.enMinutos = 5
		paypal.base.url = "http://www.clickandgolf.es" // Deberia ser siempre lo mismo q serverURL
				
		imagenes.ubicacion="/home/test-imagenes"
		staticresources.url="http://www.clickandgolf.es/images-campos/test-imagenes/campos"

		// Usa el de gmail
		grails {
			mail {
			  host = "smtp.gmail.com"
			  port = 465
			  username = "no-responder@clickandgolf.es"
			  password = "ClickAndGolfNo1234"
			  props = [ 	"mail.smtp.auth":"true",
				  			"mail.smtp.socketFactory.port":"465",
							"mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
						    "mail.smtp.socketFactory.fallback":"false"  ]
//			  overrideAddress="info@clickandgolf.es" // Le cambia todas las direcciones y pone 'info@clickandgolf.es' antes de mandarlo
			}
		 }
			
	}
    production {
        grails.logging.jul.usebridge = false
		log4jConsoleLogLevel = org.apache.log4j.Level.INFO
		
		// Configuracion para el Job q genera Green Fees
		grails.plugin.xyz.greenFeeTriggerConfig = {
			// Espera 60 segundos para empezar por primera vez
			// Se ejecuta al minuto despues de las 12 de la noche diariamente
			
			cron name:'greenFeeTrigger', startDelay:6000, cronExpression: "0 1 0 1/1 * ? *"
//			cron name:'greenFeeTrigger', startDelay:4500, cronExpression: "0 0/5 * 1/1 * ? *"
		}
		
		// Es necesario armar ROOT.war
		grails.app.context = "/"
		grails.paypal.server = "https://www.paypal.com/cgi-bin/webscr"
		grails.paypal.email = "info@clickandgolf.es"
				
		grails.serverURL = "http://www.clickandgolf.es"
				
		abandoned.enMinutos = 15
		paypal.base.url = "http://www.clickandgolf.es" // Deberia ser siempre lo mismo q serverURL
				
		imagenes.ubicacion="/home/imagenes"

		grails {
			mail {
			  host = "smtp.gmail.com"
			  port = 465
			  username = "no-responder@clickandgolf.es"
			  password = "ClickAndGolfNo1234"
			  props = [ 	"mail.smtp.auth":"true",
				  			"mail.smtp.socketFactory.port":"465",
							"mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
						    "mail.smtp.socketFactory.fallback":"false"  ]
			}
		 }
			
    }
}

log4j = {
	appenders {
		appender new DailyRollingFileAppender(
					name: 'appFile',
					datePattern: "'.'yyyy-MM-dd",  // See the API for all patterns.
					fileName: "${logDirectory}/click_and_golf.log".toString(),
					threshold: log4jAppFileLogLevel,
					layout: pattern(conversionPattern:'%d{dd-MM-yyyy HH:mm:ss,SSS} %5p %c{1} -> %m%n'
				)
		)
//		rollingFile name:'appFile',
//					file:"${logDirectory}/click_and_golf.log".toString(),
//					maxFileSize:'2MB',
//					layout:pattern(conversionPattern: '%d{dd-MM-yyyy HH:mm:ss,SSS} %5p %c{1} -> %m%n'),
//					threshold: log4jAppFileLogLevel,
//					maxBackupIndex: 7
		rollingFile name:'stacktrace',
					file:"${logDirectory}/_click_and_golf_stack.log".toString(),
					maxFileSize:'1MB',
					layout:pattern(conversionPattern: '%d{dd-MM-yyyy HH:mm:ss,SSS} %5p %c{1} -> %m%n'),
					threshold: log4jAppFileLogLevel
		console 	name:'stdout',
					layout:pattern(conversionPattern: '%d{dd-MM-yyyy HH:mm:ss,SSS} %5p %c{1} -> %m%n'),
					threshold: log4jConsoleLogLevel
	}

	error  	'org.codehaus.groovy.grails.web.servlet',  //  controllers
			'org.codehaus.groovy.grails.web.pages', //  GSP
			'org.codehaus.groovy.grails.web.sitemesh', //  layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping', // URL mapping
			'org.codehaus.groovy.grails.commons', // core / classloading
			'org.codehaus.groovy.grails.plugins', // plugins
			'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
			'org.codehaus.groovy.grails',
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate',
			'org.springframework.security'
	
	warn  	'com.the6hours',
			'grails.app.taglib.com.the6hours'

	info 	'com.clickandgolf',
			'clickandgolf',
			'org.grails.paypal.PaypalController'
	
	root {
		error 'appFile', 'stdout'
		info 'appFile', 'stdout'
		warn 'appFile', 'stdout'
		debug 'appFile', 'stdout'
		additivity = true
	}
}

grails.gorm.default.mapping = {
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateTime, class: org.joda.time.DateTime
}

jodatime {  
	format.org.joda.time.DateTime = "dd/MM/YYYY HH:mm" 
}

// Para que se generen los URLs en SEO friendly (tiene problemas con la seguridad)
grails.web.url.converter = 'hyphenated'

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.clickandgolf.seguridad.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.clickandgolf.seguridad.UserRole'
grails.plugins.springsecurity.authority.className = 'com.clickandgolf.seguridad.Role'


// Para conectividad con Facebook
grails.plugins.springsecurity.facebook.domain.classname='com.clickandgolf.seguridad.FacebookUser'
grails.plugins.springsecurity.facebook.appId='299259360167091'
grails.plugins.springsecurity.facebook.secret='8046043db65c80c6c86b8dcc7e7791a6'

grails.plugins.springsecurity.facebook.permissions='email,user_about_me,publish_stream,user_photos'
//grails.plugins.springsecurity.facebook.filter.type='transparent,cookieDirect'

// Spring social 
grails.plugins.springsocial.facebook.clientId='299259360167091'
grails.plugins.springsocial.facebook.clientSecret='8046043db65c80c6c86b8dcc7e7791a6'

grails.plugins.springsecurity.ui.encodePassword=false