package com.clickandgolf

import com.clickandgolf.ClockService
import org.hibernate.transform.Transformers
import org.springframework.web.context.request.RequestContextHolder as RCH

/**
 * Idea tomada de aca:
 * 	http://blog.peterdelahunty.com/2009/01/grails-tips-for-homepage-url-mapping.html
 * 	http://stackoverflow.com/questions/198936/best-practices-for-grails-index-page
 * 
 * @author gonznic
 */
class HomeController {

	def clockService
	
    def index() {
		// Se redirecciona desde UrlMappings 
		/* Esto existe xa poder buscar info de la BD y cargar en el model y demas lo siguiente:
		 * 	promociones PREMIUM
		 *  promociones GOLD
		 *  ubicaciones
		 */
		def comienzoHoy = clockService.comienzoHoy()
		def finalHoy = clockService.finalHoy()
		def criteriaPromoPremium = Promocion.createCriteria()
		def promocionesGold = criteriaPromoPremium.list {
			le("desde", finalHoy)
			ge("hasta", comienzoHoy)
			eq("estado", Promocion.ACTIVO)
			eq("tipo", Promocion.GOLD)
			fetchMode("campo", org.hibernate.FetchMode.JOIN)
		}
		log.info("# Promociones GOLD encontradas: " + promocionesGold.size() + " ...")

		def criteriaPromoGold = Promocion.createCriteria()
		def promocionesSilver = criteriaPromoGold.list {
			le("desde", finalHoy)
			ge("hasta", comienzoHoy)
			eq("estado", Promocion.ACTIVO)
			eq("tipo", Promocion.SILVER)
			fetchMode("campo", org.hibernate.FetchMode.JOIN)
		}
		log.info("# Promociones SILVER encontradas: " + promocionesSilver.size() + " ...")
		
//		def criteriaUbicaciones = Ubicacion.createCriteria()
//		List<Ubicacion> ubicaciones = criteriaUbicaciones {
//			projections {
//				property("id", "id")
//				property("provincia", "provincia")
//				groupProperty("ciudad", "ciudad")
//				groupProperty("region", "region")
//			}
//			order("region")
//			order("ciudad")
//			resultTransformer(Transformers.aliasToBean(Ubicacion.class))
//		}
		
		def locale = RCH.currentRequestAttributes()?.locale ?: Locale.ES
		String lang = locale.getLanguage()
		def textoProperty = "texto_" + lang.toLowerCase()
		
		def ubicaciones = Ubicacion.createCriteria().list() {
			order("region")
			order("ciudad")
		}

		[promocionesGold: promocionesGold, promocionesSilver: promocionesSilver, ubicaciones: ubicaciones, promocionesGoldSize: promocionesGold.size(), textoProperty: textoProperty]
	}
		
}