class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/"(controller:'home',action:'index')
//		"/"(view:"/index")
		
		"404"(controller: "error", action: "notFound")
		"405"(controller: "error", action: "noPermitido")
		"500"(controller: "error", action: "errorDelServidor")
	
		"/sitemap"{
			controller = 'sitemap'
			action = 'sitemap'
		}
		
		name campo: "/campo/$id/$nombre" {
			controller = "campo"
			action = "ver"
		}
		
		name greenfeeCampo: "/green-fees/$dia/$mes/$anio/$idCampo/$nombreCampo" {
			controller = "greenFee"
			action = "greenFees"
		}

	}
}
