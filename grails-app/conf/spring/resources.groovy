// Place your Spring DSL code here
beans = {
	
	// Por defecto ira en castellano
	localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
		defaultLocale = new Locale("es","ES")
		java.util.Locale.setDefault(defaultLocale)
	}
	
}
