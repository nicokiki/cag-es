grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
	
//	legacyResolve true
	
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()

        // uncomment these to enable remote dependency resolution from public Maven repositories
        //mavenCentral()
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
//        mavenRepo "http://repository.codehaus.org"
		mavenRepo "http://repo.grails.org/grails/plugins"
		mavenRepo "http://repository.springsource.com/maven/bundles/release/"
		mavenRepo "http://repository.springsource.com/maven/bundles/external/"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
//		compile ":geolocation:0.4.1"
		compile "org.jadira.usertype:usertype.jodatime:1.9"
		
        runtime 'mysql:mysql-connector-java:5.1.16'
    }

    plugins {
		
		
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.7.1"
        runtime ":resources:1.1.6"

//		compile ":spring-security-facebook:0.8"		
//		compile "org.grails.plugins:spring-security-facebook:0.10.3"
		compile ":spring-security-facebook:0.14.2"
		compile ":simple-captcha:0.9.1"
		// https://github.com/krasserm/grails-jaxrs/wiki/Installation-Instructions
//		compile ":jaxrs:0.7"
		
        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        build ":tomcat:$grailsVersion"
    }
}

grails.tomcat.jvmArgs = ["-Duser.timezone='Europe/Madrid'"]
