package com.clickandgolf

class SitemapController {

    def sitemap() { 
		render(contentType: 'text/xml', encoding: 'UTF-8') {
			mkp.yieldUnescaped '<?xml version="1.0" encoding="UTF-8"?>'
			urlset(xmlns: "http://www.sitemaps.org/schemas/sitemap/0.9",
					'xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance",
					'xsi:schemaLocation': "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd") {
				url {
					loc(g.createLink(absolute: true, controller: 'home', action: 'index'))
					changefreq('weekly')
					priority(1.0)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'promociones', action: 'ultimoMomento'))
					changefreq('daily')
					priority(0.9)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'campo', action: 'listaDeCampos'))
					changefreq('weekly')
					priority(0.8)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'campo', action: 'listaDeCamposDeGolf'))
					changefreq('weekly')
					priority(0.8)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'campo', action: 'listaDeCamposDePitchAndPutt'))
					changefreq('weekly')
					priority(0.8)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'greenFee', action: 'busquedaCamposAvanzada'))
					changefreq('hourly')
					priority(0.7)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'greenFee', action: 'busquedaDeCampos'))
					changefreq('hourly')
					priority(0.5)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'info', action: 'contactanos'))
					changefreq('yearly')
					priority(0.1)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'info', action: 'condiciones'))
					changefreq('monthly')
					priority(0.5)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'info', action: 'quienesSomos'))
					changefreq('monthly')
					priority(0.5)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'info', action: 'condiciones', fragment: 'cancel'))
					changefreq('monthly')
					priority(0.5)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'info', action: 'politicaDePrivacidad'))
					changefreq('monthly')
					priority(0.5)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'info', action: 'faq'))
					changefreq('monthly')
					priority(0.6)
				}
				url {
					loc(g.createLink(absolute: true, controller: 'info', action: 'comoFunciona'))
					changefreq('monthly')
					priority(0.6)
				}
				// Ahora cada campo pongo aca asi estan todos!
				def campos = Campo.findAllByEstado(Campo.ACTIVO)
				log.info("#Campos: ${campos.size()}")
				campos.each { campo ->  
					url {
						loc(g.createLink(absolute: true, mapping: 'campo', params: [id:"${campo.id}", nombre:"${campo.hyphenatedNombre}"]))
						changefreq('monthly')
						priority(0.5)
					}
				}
			}
		}
	}
}
