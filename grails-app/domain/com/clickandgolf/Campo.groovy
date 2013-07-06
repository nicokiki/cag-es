package com.clickandgolf

import com.clickandgolf.seguridad.User;
import java.math.BigDecimal

// No se cachea x problemas con los jobs!
class Campo {

	static final ACTIVO = "ACTIVO"
	static final INACTIVO = "INACTIVO"
	
	static final PITCH_AND_PUTT = "Pitch & Putt"
	static final GOLF = "Golf"
	
	String nombre
	// Este lo necesitamos xq es muchooo mas facil q calcularlo nosotros.
	String hyphenatedNombre
	String descripcion = ""
	String descripcionQuotted = ""
	// Por defecto esta disponible
	String estado = ACTIVO
	String tipo = GOLF
	String email
	
	/* Este es el fee que se le aplicara a cada green fee. Una vez generados los green fees, solo se puede cambiar desde la BD.
	 * Es el porcentaje a aplicar. ej: 12 implica q es 12%  
	 */
	BigDecimal fee = new BigDecimal("12")
	
	// Ubicacion
	Ubicacion ubicacion
	// Georeferencia
	String longitud
	String latitud
	
	// Link a sitio del campo
	String linkCampo
	// Link extra a otro sitio "amigo"
	String linkExtra
	
	// Imagenes / Son los nombres de los archivos xq siempre estaran guardados en <path>/campos/id/<nombre>
	String imagenPrincipal
	Long imagenPrincipalLastModified
	String imagenPromocionGold
	Long imagenPromocionGoldLastModified
	String imagenPromocionSilver
	Long imagenPromocionSilverLastModified
	// La secundaria sera la secundaria de la Gold
	String imagenSecundaria
	Long imagenSecundariaLastModified
	String imagenExtra // No se usa x ahora ...
	
	String comoLlegar = ""
	String coordenadasGps
	
	Scorecardmetadata scorecardmetadata
	Scorecard blancas
	Scorecard amarillas
	Scorecard rojas
	Scorecard azules
	
	// SOLO se cambia en la BD a mano y listo por ahora
	Boolean requiereLicencia = Boolean.FALSE
	
	String normasLink
	String normasSeccion
	
	// Algunos campos podran poner una nota excepcional xa mandar en caso de ser requerido
	String notasReserva
	
	// Para la seccion contacto
	String telefono
	String direccion
	String emailContacto
	

	String getTrimmedDescription() {
		if (null == this.descripcion) {
			return "";
		}
		if (this.descripcion.length() >= 351) {
			return this.descripcion.substring(0, 350) + " ...";
		}
		return this.descripcion;
	}
	
	static transients = ['trimmedDescription']
	
    static constraints = {
		nombre blank: false, unique: true
		hyphenatedNombre blank: false, maxSize: 100
		descripcion blank: false, maxSize: 3000
		descripcionQuotted(nullable:true, maxSize: 500)
		comoLlegar(nullable:true, maxSize: 500)
		coordenadasGps(nullable:true, maxSize: 100)
		
		fee (nullable: false, min: 0.0, max: 100.00, scale: 2)
		
		estado inList: [Campo.ACTIVO, Campo.INACTIVO]
		tipo inList: [Campo.PITCH_AND_PUTT, Campo.GOLF]
		email(email: true, blank: false)
		ubicacion nullable: false
		longitud maxSize: 100
		latitud maxSize: 100
		
		linkCampo nullable: true
		linkExtra nullable: true
		
		imagenPrincipal nullable: true, maxSize: 100
		imagenPromocionSilver nullable: true, maxSize: 100
		imagenPromocionGold nullable: true, maxSize: 100
		imagenSecundaria nullable: true, maxSize: 100
		imagenExtra nullable: true, maxSize: 100
		
		imagenPrincipalLastModified(nullable: true)
		imagenPromocionSilverLastModified(nullable: true)
		imagenPromocionGoldLastModified(nullable: true)
		imagenSecundariaLastModified(nullable: true)
		
		scorecardmetadata nullable: false
		blancas nullable: true
		amarillas nullable: true
		rojas nullable: true
		azules nullable: true
		
		requiereLicencia(nullable:false)
		normasLink(nullable: true)
		normasSeccion(nullable: true, maxSize: 3000)
		notasReserva(nullable: true, maxSize: 300)
		
		telefono(nullable: true, maxSize: 60)
		direccion(nullable: true, maxSize: 200)
		emailContacto(nullable: true, email: true)

    }
	
	boolean isActivo() {
		return estado == Campo.ACTIVO
	}
	
}

