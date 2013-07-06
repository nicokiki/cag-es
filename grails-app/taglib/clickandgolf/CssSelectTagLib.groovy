package clickandgolf

class CssSelectTagLib {
	
	static namespace = "golf"
	
	/**
	 * @attr value REQUIRED Value del objeto
	 * @attr dataList REQUIRED Lista a iterar
	 * @attr cssClass class CSS
	 * @attr style style CSS
	 * @attr name REQUIRED es el nombre 
	 * @attr placeHolderValue es lo q aparece cuando no hay seleccion
	 * @attr key Si esta entonces compara con .id y usa el toString()
	 */
	def select = { attrs, body ->
		def valueId =  attrs.value
		def dataList = attrs.dataList
		def cssClass = attrs.cssClass
		def style = attrs.style
		def name = attrs.name
		def placeHolderValue = attrs.placeHolderValue //i18n se resuelve en la pagina y no aca 
		def key = attrs.key
		
		// Para obtener el valor de i18n seria asi: g.message(code: placeHolderCode, default: 'Eligeaa')
		
		out << '<select name="' << name << '" data-placeholder="' << placeHolderValue << '" class="' << cssClass << '" style="' << style << '" >'
		out << '<option value="null"></option>'
			dataList.each { data ->
				if (key) {
					if (data.id == valueId) {
						out << '<option value="' << data.id << '" selected="selected">' << data.toString() << '</option>'
					}
					else {
						out << '<option value="' << data.id << '" >' << data.toString() << '</option>'
					}
	
				}
				else {
					if (data == valueId) {
						out << '<option value="' << data << '" selected="selected">' << data << '</option>'
					}
					else {
						out << '<option value="' << data << '" >' << data << '</option>'
					}
				}

			}
		out << '</select>'
	}

	
	/**
	 * Este es SOLO xa Ubicaciones para que muestre con <optgroup>
	 * El value sera:
	 * 	region:nombre_region
	 *  ciudad:nombre_ciudad
	 * 
	 * @attr value REQUIRED Value del objeto
	 * @attr dataList REQUIRED Lista de ubicaciones a iterar. IMPORTANTE: Tiene q estar ordenado por region y luego x ciudad
	 * @attr cssClass class CSS
	 * @attr style style CSS
	 * @attr name REQUIRED es el nombre
	 * @attr placeHolderValue REQUIRED es lo q aparece cuando no hay seleccion (i18n)
	 * @attr todosLabel REQUIRED es lo q dice con la region. ej: Todo Catalunya (i18n)
	 */
	def selectUbicaciones = { attrs, body ->
		def valueId =  attrs.value
		def dataList = attrs.dataList
		def cssClass = attrs.cssClass
		def style = attrs.style
		def name = attrs.name
		def placeHolderValue = attrs.placeHolderValue //i18n se resuelve en la pagina y no aca
		def todosLabel = attrs.todosLabel //i18n se resuelve en la pagina y no aca
		
		out << '<select name="' << name << '" data-placeholder="' << placeHolderValue << '" class="' << cssClass << '" style="' << style << '" >'
		out << '	<option value=""></option>'
//		out << '	<option value="null"></option>'
		
		def region = ""
		boolean faltaCerrar = false
		
		dataList.each { data ->
			def regionActual = data.region
			// Analizo si tengo q cerrar el optgroup o no
			if (faltaCerrar && !region.equals(regionActual)) {
				faltaCerrar = false
				out << '</optgroup>'
			}
			if (!region.equals(regionActual) && !faltaCerrar) {
				faltaCerrar = true
				region = regionActual
				out << '<optgroup label="' << regionActual << '">'
				
				def valorRegionAux = "region:" + regionActual
				if (valorRegionAux.equals(valueId)) {
					out << '	<option value="' << valorRegionAux << '" selected="selected">' <<todosLabel << ' ' << regionActual << '</option>'
				}
				else {
					out << '	<option value="' << valorRegionAux << '" >' <<todosLabel << ' ' << regionActual << '</option>'
				}
			}
			def ciudadActual = data.ciudad
			def valorAux = "ciudad:" + ciudadActual
			if (valorAux.equals(valueId)) {
				out << '<option value="' << valorAux << '" selected="selected">' << ciudadActual << '</option>'
			}
			else {
				out << '<option value="' << valorAux << '" >' << ciudadActual << '</option>'
			}
		}
		if (faltaCerrar) {
			// Falta el ultimo!
			out << '</optgroup>'
		}
		out << '</select>'
	}

	
}
