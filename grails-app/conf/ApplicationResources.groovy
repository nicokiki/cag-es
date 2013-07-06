modules = {
    application {
        resource url:'js/application.js'
    }

	/* Los bundle los puse xq la caga! */
	core {
		resource url:'css/skitter.styles.css', disposition: 'head', bundle: '1'
		resource url:'css/config.css', disposition: 'head', bundle: '2'
		resource url:'css/ie7.css', disposition: 'head', bundle: '21', wrapper: { s -> "<!--[if IE 7]>$s<![endif]-->" }
		resource url:'css/ie8.css', disposition: 'head', bundle: '18', wrapper: { s -> "<!--[if IE 8]>$s<![endif]-->" }

		
		resource url:'js/jquery.js', disposition: 'head', bundle: '3'
		resource url:'js/plugins.js', disposition: 'head', bundle: '4'
		resource url:'js/share.cufonfonts.js', disposition: 'head', bundle: '5'
		resource url:'js/scripts_call_head.js', disposition: 'head', bundle: '6'
		
		resource url:'js/jquery.animate-colors-min.js', disposition: 'head', bundle: '7'
		resource url:'js/jquery.skitter-min.js', disposition: 'head', bundle: '8'
		
		resource url:'datepicker/themes/base/ui.all.css', disposition: 'head', bundle: '9'
		resource url:'datepicker/themes/base/ui.base.css', disposition: 'head', bundle: '9'
		resource url:'datepicker/themes/base/ui.theme.css', disposition: 'head', bundle: '9'
//		resource url:'datepicker/ui/ui.core.js', disposition: 'head', bundle: '10'
//		resource url:'datepicker/ui/ui.datepicker.js', disposition: 'head', bundle: '11'
		resource url:'datepicker/ui/minified/ui.core.min.js', disposition: 'head', bundle: '10'
		resource url:'datepicker/ui/minified/ui.datepicker.min.js', disposition: 'head', bundle: '11'

		resource url:'datepicker/ui/i18n/ui.datepicker-es.js', disposition: 'head', bundle: '12'
		resource url:'datepicker/ui/i18n/ui.datepicker-ca.js', disposition: 'head', bundle: '13'
		resource url:'datepicker/ui/i18n/ui.datepicker-de.js', disposition: 'head', bundle: '14'
		resource url:'datepicker/ui/i18n/ui.datepicker-fr.js', disposition: 'head', bundle: '15'
		resource url:'datepicker/ui/i18n/ui.datepicker-gl.js', disposition: 'head', bundle: '16'
		
		resource url:'js/scripts.js'
		
		resource url:'css/styles.css', disposition: 'head', bundle: '20'
		resource url:'js/jquery.selectoul.js', disposition: 'head', bundle: '21'

		resource url:'js/html5.js', disposition: 'head', bundle: '30',wrapper: { s -> "<!--[if IE]>$s<![endif]-->" }
		
		resource url:'js/common-scripts.js',  disposition: 'head', bundle: '95'
		
		resource url:'js/jquery.roundabout-min.js', disposition: 'head', bundle: '77'
		
	}
	
}