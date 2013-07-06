import com.clickandgolf.seguridad.Role
import com.clickandgolf.seguridad.User
import com.clickandgolf.seguridad.UserRole

import com.clickandgolf.ie.Item
import com.clickandgolf.Campo
import com.clickandgolf.Ubicacion
import com.clickandgolf.GreenFee
import com.clickandgolf.GreenFeeTemplate
import com.clickandgolf.Fiesta
import com.clickandgolf.FechaService
import com.clickandgolf.Promocion
import com.clickandgolf.Scorecardmetadata
import com.clickandgolf.Scorecard

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter;
import org.hibernate.transform.Transformers

import org.joda.time.format.DateTimeFormat;
import org.joda.time.DateTimeZone;

import grails.util.Environment

import java.math.BigDecimal

class BootStrap {
	
	def clockService

    def init = { servletContext ->
		
		Environment.executeForCurrentEnvironment {
			production {
				log.info("Ejecutando para PRODUCTION ...")
				def userTZ = System.properties.getProperty('user.timezone')
				log.info("Veamos si agarro el user.timezone de los parametros q le pase -> userTZ:${userTZ}")
				
				log.info("Seteando por default el timezone de Madrid ...")
				TimeZone.setDefault(clockService.spainTZ.toTimeZone());
				log.info("Timezone de Madrid seteado correctamente ...")
				
				// Valida que esten los roles minimos
				def adminRole = Role.findByAuthority("ROLE_ADMIN") ?: new Role(authority: "ROLE_ADMIN").save(failOnError: true)
				def campoRole = Role.findByAuthority("ROLE_CAMPO") ?: new Role(authority: "ROLE_CAMPO").save(failOnError: true)
				def userRole = Role.findByAuthority("ROLE_USER") ?: new Role(authority: "ROLE_USER").save(failOnError: true)
				def facebookRole = Role.findByAuthority("ROLE_FACEBOOK") ?: new Role(authority: "ROLE_FACEBOOK").save(failOnError: true)

				log.info("Lo de los roles fue verificado ...")
				
				// Valida q el usuario de admin este siempre tambien
				def adminUser = User.findByUsername("nicolas.gonzalez@clickandgolf.es") ?: new User(username: "nicolas.gonzalez@clickandgolf.es", enabled: true, password: "GolfNicoPia", email: "nicolas.gonzalez@clickandgolf.es").save(failOnError: true)
				if (!adminUser.authorities.contains(adminRole)) {
					UserRole.create(adminUser, adminRole, true)
					log.info("Dando ROLE_ADMIN a nicolas.gonzalez@clickandgolf.es ...")
				}
				log.info("Usuario ADMIN creado y/o verificado")
				
				// Me voy a crear un campo de prueba exclusivamente para tener de testing
				def barcelona = Ubicacion.findByCiudad("Barcelona") ?: new Ubicacion(ciudad: "Barcelona", provincia: "Barcelona", region: "Catalunya").save(failOnError: true)
				def scMetadataTest = Scorecardmetadata.findByNombre("TESTscorecardmetadata-sc-md") ?: new Scorecardmetadata(nombre: "TESTscorecardmetadata-sc-md").save(failOnError: true)
				def blancasTest = Scorecard.findByNombre("TESTscorecard_sc-blancas") ?: new Scorecard(nombre: "TESTscorecard_sc-blancas", color: Scorecard.BLANCO, metrosHoyo1: "230", metrosHoyo2: "130",metrosHoyo3: "290",metrosHoyo4: "430",metrosHoyo5: "230",metrosHoyo6: "230",metrosHoyo7: "230",metrosHoyo8: "230",metrosHoyo9: "230",metrosHoyo10: "230",metrosHoyo11: "230",metrosHoyo12: "230",metrosHoyo13: "230",metrosHoyo14: "230",metrosHoyo15: "230",metrosHoyo16: "230",metrosHoyo17: "230",metrosHoyo18: "230").save(failOnError: true)
				def campoTest = Campo.findByNombre("Test Campo") ?: new Campo(estado: Campo.INACTIVO, email: "info@clickandgolf.es", emailContacto: "info@clickandgolf.es", telefono: "55533333", direccion: "direccion", descripcionQuotted: "aqui iria la descripcion del campo especifica", nombre: "Test Campo", hyphenatedNombre: 'test-campo', scorecardmetadata: scMetadataTest, blancas: blancasTest, fee: new BigDecimal(12), descripcion: "Campo Test", ubicacion: barcelona, latitud: "41.499691", longitud: "2.055098", imagenPromocionGold: "test.jpg", linkCampo: "http://www.clickandgolf.es", linkExtra: "http://www.clickandgolf.es", imagenPrincipal: "test.jpg", imagenSecundaria: "test.jpg").save(failOnError: true)
				
				def campoUser = User.findByUsername("info@clickandgolf.es") ?: new User(username: "info@clickandgolf.es", enabled: true, password: "GolfNicoPia", email: "info@clickandgolf.es", campo: campoTest).save(failOnError: true)
				if (!campoUser.authorities.contains(campoRole)) {
					log.info("Dando ROLE_CAMPO a info@clickandgolf.es ...")
					UserRole.create(campoUser, campoRole, true)
				}
				log.info("Lo del usuario info fue verificado ...")
				
				def ubicaciones = Ubicacion.createCriteria().list() {
					order("region")
					order("ciudad")
				}

				ubicaciones.each { ubi ->
					log.info("id:'" + ubi.id + "' / ciudad:'" + ubi.ciudad + "' / prov:'" + ubi.provincia + "' / region:'" + ubi.region + "'")
				}
				
				log.info("El BootStrap para PRODUCTION termino de ejecutarse ...")
			}
			
			test {
				log.info("Ejecutando para TEST que es como PRE-PRODUCTION porque usa la bd de clickandgolf-test ...")
				def userTZ = System.properties.getProperty('user.timezone')
				log.info("El user.timezone de los parametros q le pase es -> userTZ:${userTZ}")
				
				log.info("Seteando por default el timezone de Madrid ...")
				TimeZone.setDefault(clockService.spainTZ.toTimeZone());
				log.info("Timezone de Madrid seteado correctamente ...")
				
				
				try {

					// Valida que esten los roles minimos
					def adminRole = Role.findByAuthority("ROLE_ADMIN") ?: new Role(authority: "ROLE_ADMIN").save(failOnError: true)
					def campoRole = Role.findByAuthority("ROLE_CAMPO") ?: new Role(authority: "ROLE_CAMPO").save(failOnError: true)
					def userRole = Role.findByAuthority("ROLE_USER") ?: new Role(authority: "ROLE_USER").save(failOnError: true)
					def facebookRole = Role.findByAuthority("ROLE_FACEBOOK") ?: new Role(authority: "ROLE_FACEBOOK").save(failOnError: true)
	
					log.info("Lo de los roles fue verificado ...")
					
					// Valida q el usuario de admin este siempre tambien
					def adminUser = User.findByEmail("nicolas.gonzalez@clickandgolf.es") ?: new User(username: "nicolas.gonzalez@clickandgolf.es", enabled: true, password: "Nico1137", email: "nicolas.gonzalez@clickandgolf.es").save(failOnError: true)
					if (!adminUser.authorities.contains(adminRole)) {
						UserRole.create(adminUser, adminRole, true)
						log.info("Dando ROLE_ADMIN a nicolas.gonzalez@clickandgolf.es")
					}
					
					log.info("Lo del usuario admin fue verificado ...")
					
					// Lista las ubicaciones que estan
					def ubicaciones = Ubicacion.createCriteria().list() {
						order("region")
						order("ciudad")
					}
					
					ubicaciones.each { ubi ->
						log.info("id:'" + ubi.id + "' / ciudad:'" + ubi.ciudad + "' / prov:'" + ubi.provincia + "' / region:'" + ubi.region + "'")
					}
				}
				catch (Exception e) {
					log.error("Algo se fue al diablo!!! ${e}", e)
				}
								
				log.info("El BootStrap para TEST termino de ejecutarse ...")
			}
			
			development {
				
				log.warn("Ejecutando para Production")
				
				def userTZ = System.properties.getProperty('user.timezone')
				log.info("El user.timezone del server es -> userTZ:${userTZ}")
				
				log.info("Seteando por default el timezone de Madrid ...")
				TimeZone.setDefault(clockService.spainTZ.toTimeZone());
				log.info("Timezone de Madrid seteado correctamente ...")
				
				/*
				
				def barcelona = new Ubicacion(ciudad: "Barcelona", provincia: "Barcelona", region: "Catalunya").save(failOnError: true)
				def tarragona = new Ubicacion(ciudad: "Tarragona", provincia: "Tarragona", region: "Catalunya").save(failOnError: true)
				def lleida = new Ubicacion(ciudad: "Lleida", provincia: "Lleida", region: "Catalunya").save(failOnError: true)
				def sevilla = new Ubicacion(ciudad: "Sevilla", provincia: "Sevilla", region: "Andalucia").save(failOnError: true)
				def valencia = new Ubicacion(ciudad: "Valencia", provincia: "Valencia", region: "Comunidad Valenciana").save(failOnError: true)
				def madrid = new Ubicacion(ciudad: "Madrid", provincia: "Madrid", region: "Madrid").save(failOnError: true)
				def alicante = new Ubicacion(ciudad: "Alicante", provincia: "Alicante", region: "Comunidad Valenciana").save(failOnError: true)
				def andorra = new Ubicacion(ciudad: "Ordino", provincia: "Andorra", region: "Andorra").save(failOnError: true)
				
				def scMetadataSantJoan = new Scorecardmetadata(nombre: 'santJoan-sc-md', parHoyo10: 3).save(failOnError: true)
				def scMetadataBarcelona = new Scorecardmetadata(nombre: 'golfclubbarcelona-sc-md').save(failOnError: true)
				def scMetadataPrat = new Scorecardmetadata(nombre: 'realclubdelprat-sc-md', parHoyo10: 3, parHoyo11: 4, parHoyo12: 4, parHoyo13: 5, parHoyo14: 3, parHoyo15: 5, parHoyo16: 3, parHoyo17: 5, parHoyo18: 5).save(failOnError: true)
				def scMetadataTgna = new Scorecardmetadata(nombre: 'tarragonagolf-sc-md', parHoyo10: 3, parHoyo11: 4, parHoyo12: 4, parHoyo13: 5, parHoyo14: 3, parHoyo15: 4, parHoyo16: 4, parHoyo17: 4, parHoyo18: 5).save(failOnError: true)
				def scMetadataSotomayor = new Scorecardmetadata(nombre: 'sotomayorgolfclub-sc-md', parHoyo10: 3, parHoyo11: 4, parHoyo12: 4, parHoyo13: 5, parHoyo14: 3, parHoyo15: 5, parHoyo16: 4, parHoyo17: 3, parHoyo18: 5).save(failOnError: true)
				def scMetadataSevilla = new Scorecardmetadata(nombre: 'sevillarealclubdegolf-sc-md', parHoyo10: 3, parHoyo11: 4, parHoyo12: 4, parHoyo13: 5, parHoyo14: 3, parHoyo15: 3, parHoyo16: 4, parHoyo17: 3, parHoyo18: 5).save(failOnError: true)
				def scMetadataAndalucia = new Scorecardmetadata(nombre: 'golfclubandalucia-sc-md', parHoyo10: 3, parHoyo11: 4, parHoyo12: 4, parHoyo13: 5, parHoyo14: 3, parHoyo15: 4, parHoyo16: 5, parHoyo17: 3, parHoyo18: 3).save(failOnError: true)
				def scMetadataSantCugat = new Scorecardmetadata(nombre: 'clubdegolfsantcugat-sc-md', parHoyo10: 3, parHoyo11: 4, parHoyo12: 5, parHoyo13: 5, parHoyo14: 3, parHoyo15: 4, parHoyo16: 5, parHoyo17: 3, parHoyo18: 4).save(failOnError: true)
				
				def blancasSantCugat = new Scorecard(nombre: "clubdegolfsantcugat_sc-blancas", color: Scorecard.BLANCO, metrosHoyo1: "230", metrosHoyo2: "130",metrosHoyo3: "290",metrosHoyo4: "430",metrosHoyo5: "230",metrosHoyo6: "230",metrosHoyo7: "230",metrosHoyo8: "230",metrosHoyo9: "230",metrosHoyo10: "230",metrosHoyo11: "230",metrosHoyo12: "230",metrosHoyo13: "230",metrosHoyo14: "230",metrosHoyo15: "230",metrosHoyo16: "230",metrosHoyo17: "230",metrosHoyo18: "230").save(failOnError: true)
				def amarillasSantCugat = new Scorecard(nombre: "clubdegolfsantcugat_sc-amarillas", color: Scorecard.AMARILLO, metrosHoyo1: "210", metrosHoyo2: "130",metrosHoyo3: "290",metrosHoyo4: "430",metrosHoyo5: "230",metrosHoyo6: "230",metrosHoyo7: "230",metrosHoyo8: "230",metrosHoyo9: "230",metrosHoyo10: "230",metrosHoyo11: "230",metrosHoyo12: "230",metrosHoyo13: "230",metrosHoyo14: "230",metrosHoyo15: "230",metrosHoyo16: "230",metrosHoyo17: "230",metrosHoyo18: "230").save(failOnError: true)
				def azulesSantCugat = new Scorecard(nombre: "clubdegolfsantcugat_sc-azules", color: Scorecard.AZUL, metrosHoyo1: "250", metrosHoyo2: "130",metrosHoyo3: "290",metrosHoyo4: "430",metrosHoyo5: "230",metrosHoyo6: "230",metrosHoyo7: "230",metrosHoyo8: "230",metrosHoyo9: "230",metrosHoyo10: "230",metrosHoyo11: "230",metrosHoyo12: "230",metrosHoyo13: "230",metrosHoyo14: "230",metrosHoyo15: "230",metrosHoyo16: "230",metrosHoyo17: "230",metrosHoyo18: "230").save(failOnError: true)
				def rojasSantCugat = new Scorecard(nombre: "clubdegolfsantcugat_sc-rojas", color: Scorecard.ROJO, metrosHoyo1: "240", metrosHoyo2: "130",metrosHoyo3: "290",metrosHoyo4: "430",metrosHoyo5: "230",metrosHoyo6: "230",metrosHoyo7: "230",metrosHoyo8: "230",metrosHoyo9: "230",metrosHoyo10: "230",metrosHoyo11: "230",metrosHoyo12: "230",metrosHoyo13: "230",metrosHoyo14: "230",metrosHoyo15: "230",metrosHoyo16: "230",metrosHoyo17: "230",metrosHoyo18: "230").save(failOnError: true)
				
				// Campos
				def campoSantJoan = new Campo(email: 'sjcampo@campo.com', nombre: 'Sant Joan', hyphenatedNombre: 'sant-joan', scorecardmetadata: scMetadataSantJoan,  fee: new BigDecimal(12), descripcion: 'Campo Sant Joan', ubicacion: barcelona, latitud: "41.499691", longitud: "2.055098", imagenPromocionGold: 'barcelona.jpg', linkCampo: 'http://www.golfsantjoan.com/', linkExtra: 'http://mipuntuacion.com/campos/Sant+Joan', imagenPrincipal: 'xF9000002_1.jpg', imagenSecundaria: 'sant-joan-secundaria.jpg').save(failOnError: true)
				def campo2 = new Campo(email: 'gcbcampo@campo.com', nombre: 'Golf Club Barcelona', hyphenatedNombre: 'golf-club-barcelona', scorecardmetadata: scMetadataBarcelona, descripcion: '222222222222', ubicacion: barcelona, latitud: "41.493206", longitud: "1.850241", imagenPromocionGold: 'xF9000002AB.jpg', linkCampo: 'http://www.golfdebarcelona.com', linkExtra: 'http://mipuntuacion.com/campos/Barcelona+%2818%29', imagenPrincipal: 'barcelona.jpg', imagenSecundaria: 'barcelona-secundaria.jpg').save(failOnError: true)
				def campo3 = new Campo(email: 'rcdpcampo@campo.com', nombre: 'Real Club del Prat', hyphenatedNombre: 'real-club-del-prat', scorecardmetadata: scMetadataPrat, descripcion: '3333333333', ubicacion: barcelona, latitud: "1", longitud: "3", imagenPromocionSilver: '200_200_xF9000002.jpg', imagenSecundaria: '500_400_xF9000002.jpg', linkCampo: 'http://www.rcgep.com', linkExtra: 'http://mipuntuacion.com/campos/El+Prat+%28Open%29', imagenPrincipal: '500_400_xF9000002.jpg').save(failOnError: true)
				def campo4 = new Campo(email: 'tgcampo@campo.com', nombre: 'Tarragona Golf', hyphenatedNombre: 'tarragona-golf', scorecardmetadata: scMetadataTgna, descripcion: '444444', ubicacion: tarragona, latitud: "1", longitud: "4", imagenPromocionSilver: '200_200_xF9000002AB.jpg', imagenSecundaria: '500_400_xF9000002AB.jpg', linkCampo: 'http://www.golfcostadoradatarragona.com/', linkExtra: 'http://mipuntuacion.com/campos/Costa+Dorada', imagenPrincipal: '500_400_xF9000002.jpg').save(failOnError: true)
				def campo5 = new Campo(email: 'sgccampo@campo.com', nombre: 'Sotomayor Golf Club', hyphenatedNombre: 'sotomayor-golf-club', scorecardmetadata: scMetadataSotomayor, descripcion: '5555555555555555', ubicacion: sevilla, latitud: "1", longitud: "5", imagenPromocionSilver: '200_200_xF9000002.jpg', imagenSecundaria: '500_400_xF9000002.jpg', linkCampo: 'http://www.golfdebarcelona.com', linkExtra: 'http://mipuntuacion.com/campos/Barcelona+%2818%29', imagenPrincipal: '500_400_xF9000002.jpg').save(failOnError: true)
				def campo6 = new Campo(email: 'srcgcampo@campo.com', nombre: 'Sevilla Real Club de Golf', hyphenatedNombre: 'sevilla-real-club-de-golf', scorecardmetadata: scMetadataSevilla, descripcion: '6666666', ubicacion: sevilla, latitud: "1", longitud: "6", imagenPromocionSilver: '200_200_xF9000002AB.jpg', imagenSecundaria: '500_400_xF9000002AB.jpg', linkCampo: 'http://www.golfdebarcelona.com', linkExtra: 'http://mipuntuacion.com/campos/Barcelona+%2818%29', imagenPrincipal: '500_400_xF9000002.jpg').save(failOnError: true)
				def campo7 = new Campo(email: 'gcacampo@campo.com', nombre: 'Golf Club Andalucia', hyphenatedNombre: 'golf-club-andalucia', scorecardmetadata: scMetadataAndalucia, descripcion: '7777777777777', ubicacion: sevilla, latitud: "1", longitud: "7", imagenPromocionGold: 'xF9000002_1.jpg', imagenPrincipal: 'andalucia-golf.jpg', imagenSecundaria: 'andalucia-golf-secundaria.jpg').save(failOnError: true)
				def campoSantCugat = new Campo(email: 'sccampo@campo.com', nombre: 'Club de Golf Sant Cugat', hyphenatedNombre: 'club-de-golf-sant-cugat', scorecardmetadata: scMetadataSantJoan, blancas: blancasSantCugat, amarillas: amarillasSantCugat, azules: azulesSantCugat, rojas: rojasSantCugat, requiereLicencia: true, fee: new BigDecimal(12), descripcion: 'bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla ', ubicacion: barcelona, latitud: "41.499691", longitud: "2.055098", imagenPromocionSilver: 'xF9000002_1.jpg', linkCampo: 'http://www.golfsantjoan.com/', linkExtra: 'http://mipuntuacion.com/campos/Sant+Joan', imagenPrincipal: 'sant-cugat.jpg', imagenSecundaria: 'sant-cugat-secundaria.jpg').save(failOnError: true)
				def campoAA = new Campo(email: 'aaacampo@campo.com', nombre: 'Campo de Golf AAA', hyphenatedNombre: 'campo-de-golf-aaa', scorecardmetadata: scMetadataAndalucia, descripcion: '7777777777777', ubicacion: sevilla, latitud: "1", longitud: "7", imagenPromocionSilver: 'xF9000002_1.jpg').save(failOnError: true)
				def campoSantCugatPP = new Campo(email: 'sccampo_pp@campo.com', nombre: 'Pitch & Putt Sant Cugat', hyphenatedNombre: 'pitch-and-putt-sant-cugat', scorecardmetadata: scMetadataSantJoan, blancas: blancasSantCugat, amarillas: amarillasSantCugat, azules: azulesSantCugat, rojas: rojasSantCugat, requiereLicencia: false, tipo: Campo.PITCH_AND_PUTT, fee: new BigDecimal(12), descripcion: 'bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla ', ubicacion: barcelona, latitud: "41.499691", longitud: "2.055098", imagenPromocionSilver: 'xF9000002_1.jpg', linkCampo: 'http://www.golfsantjoan.com/', linkExtra: 'http://mipuntuacion.com/campos/Sant+Joan', imagenPrincipal: '500_400_xF9000002.jpg').save(failOnError: true)
				
				def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
				def campoRole = Role.findByAuthority('ROLE_CAMPO') ?: new Role(authority: 'ROLE_CAMPO').save(failOnError: true)
				def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save(failOnError: true)
				def facebookRole = Role.findByAuthority('ROLE_FACEBOOK') ?: new Role(authority: 'ROLE_FACEBOOK').save(failOnError: true)
		  
				def testUser = User.findByUsername('test') ?: new User(username: 'test@test.com', enabled: true, password: 'password', email: 'test@test.com').save(failOnError: true)
				if (!testUser.authorities.contains(userRole)) {
					log.info("Dando ROLE_USER a test")
					UserRole.create(testUser, userRole, true)
				}

				def campoUser = User.findByUsername('campo') ?: new User(username: 'campo@campo.com', enabled: true, password: 'password', email: 'campo@campo.com', campo: campoSantJoan).save(failOnError: true)
				if (!campoUser.authorities.contains(campoRole)) {
					log.info("Dando ROLE_CAMPO a campo")
					UserRole.create(campoUser, campoRole, true)
				}
				
				def adminUser = User.findByUsername('admin') ?: new User(username: 'admin@admin.com', enabled: true, password: 'password', email: 'admin@admin.com').save(failOnError: true)
				if (!adminUser.authorities.contains(adminRole)) {
					UserRole.create(adminUser, adminRole, true)
					log.info("Dando ROLE_ADMIN a admin")
				}
				
				def user1 = new User( username: 'recepcion@golflespalmeres.com', enabled: true, password: 'Golf0Les2PalmeresP', email: 'recepcion@golflespalmeres.com').save(failOnError: true)
				def user2 = new User( username: 'recepcio@golfdebarcelona.com', enabled: true, password: 'Golf0De3BarcelonaP', email: 'recepcio@golfdebarcelona.com').save(failOnError: true)
				def user3 = new User( username: 'info@golfdaro.com', enabled: true, password: 'Golf0D4AroP', email: 'info@golfdaro.com').save(failOnError: true)
				def user4 = new User( username: 'info@golfdecaldes.com', enabled: true, password: 'Golf0De5CaldesP', email: 'info@golfdecaldes.com').save(failOnError: true)
				def user5 = new User( username: 'golfpublictaradell@yahoo.es', enabled: true, password: 'Golf0Public6TaradellP', email: 'golfpublictaradell@yahoo.es').save(failOnError: true)
				def user6 = new User( username: 'info@golfteia.com', enabled: true, password: 'Golf0Teia7P', email: 'info@golfteia.com').save(failOnError: true)
				def user7 = new User( username: 'info@pitchandputtvallromanes.com', enabled: true, password: 'PitchAndPutt0Vallromanes8P', email: 'info@pitchandputtvallromanes.com').save(failOnError: true)
				def user8 = new User( username: 'info@golfrioja.com', enabled: true, password: 'Golf0Rioja9P', email: 'info@golfrioja.com').save(failOnError: true)
				def user9 = new User( username: 'recepcio@golfsantjoan.com', enabled: true, password: 'Golf1Sant0JoanP', email: 'recepcio@golfsantjoan.com').save(failOnError: true)
				def user10 = new User(username: 'club@golfllavaneras.com', enabled: true, password: 'Golf1Llavaneras1P', email: 'club@golfllavaneras.com').save(failOnError: true)
				def user11 = new User(username: 'info@golfsantcugat.com', enabled: true, password: 'Golf1Sant2CugatP', email: 'info@golfsantcugat.com').save(failOnError: true)
				def user12 = new User(username: 'reserves@golfmontanya.com', enabled: true, password: 'Golf1Montanya3P', email: 'reserves@golfmontanya.com').save(failOnError: true)
				def user13 = new User(username: 'golflogrono@fcc.es', enabled: true, password: 'Golf1Logrono4P', email: 'golflogrono@fcc.es').save(failOnError: true)
				def user14 = new User(username: 'info@golfmontseny.com', enabled: true, password: 'Golf1Montseny5P', email: 'info@golfmontseny.com').save(failOnError: true)
				def user15 = new User(username: 'info@santcugatpitchputt.com', enabled: true, password: 'Sant1Cugat6PPuttP', email: 'info@santcugatpitchputt.com').save(failOnError: true)
				def user16 = new User(username: 'pitchandputtmasnou@golfdaro.com', enabled: true, password: 'PitchAnd1PMas7NouP', email: 'pitchandputtmasnou@golfdaro.com').save(failOnError: true)
				def user20 = new User(username: 'golfsantfeliu@grupbroquetas.com', enabled: true, password: 'Golf20SantFeliu', email: 'golfsantfeliu@grupbroquetas.com').save(failOnError: true)
				

				assert User.count() == 20
				assert Role.count() == 4
				assert UserRole.count() == 3
				
				new Item(nombre: 'uno').save(failOnError: true)
				new Item(nombre:'dos').save(failOnError: true)
				new Item(nombre:'tres').save(failOnError: true)
				new Item(nombre:'cuatro').save(failOnError: true)
				
				def fiestaBarna = new Fiesta(anio: 2013,  ubicacion: barcelona, fecha: clockService.ahora().plusDays(8)).save(failOnError: true)
				def fiestaBarnaFuturo = new Fiesta(anio: 2013,  ubicacion: barcelona, fecha: clockService.ahora().plusMonths(2)).save(failOnError: true)
				def fiestaTgna = new Fiesta(anio: 2013,  ubicacion: tarragona, fecha: clockService.ahora().plusDays(6)).save(failOnError: true)
				def fiestaEspania = new Fiesta(anio: 2014,  ubicacion: null, fecha: clockService.ahora().plusMonths(14)).save(failOnError: true)

				
				def martes9HoyosNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
					tipo:GreenFeeTemplate.MARTES, tipoExtra: GreenFeeTemplate.NORMAL, hoyos: GreenFeeTemplate.HOYOS_9,
					horarios: "13:20, 14:35,14:45, 14:55, 16:20",
					excluir: "05/02/2013",
					campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Juega los martes 9 HOYOS',
					precio: new BigDecimal(20), precioOriginal: new BigDecimal(40), descuento: 50,
					golfistasMinimo: 1, disponibles: 4).save(failOnError: true);

				
				def martesNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.MARTES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "10.03, 13:20, 14:35,14:45, 14:55, 16:20",
						excluir: "05/02/2013,19/02/2013",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Juega los martes',
						precio: new BigDecimal(40), precioOriginal: new BigDecimal(60), descuento: 34,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def martesNormalEnOtrosHorariosTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.MARTES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "14:55, 16:20",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Juega los martes pre torneo',
						precio: new BigDecimal(40), precioOriginal: new BigDecimal(60), descuento: 34,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def martesTorneoTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.MARTES, tipoExtra: GreenFeeTemplate.TORNEO,
						horarios: "13:20, 14:35,14:45",
						incluir: "05/02/2013,19/02/2013",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Juega el torneo Yardas Tour',
						precio: new BigDecimal(50), precioOriginal: new BigDecimal(60), descuento: 17,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true)
				def miercolesNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.MIERCOLES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "10:00, 10:30, 11:00,13:20, 14:25, 15:30, 15:45, 16:00, 16:10",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Miercoles de lujo',
						precio: new BigDecimal(30), precioOriginal: new BigDecimal(60), descuento: 50,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def juevesNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.JUEVES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "14:25, 15:30, 15:45, 16:00, 16:10",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Jueves de amigos',
						precio: new BigDecimal(20), precioOriginal: new BigDecimal(60), descuento: 73,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def viernesNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.VIERNES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "14:25, 15:30, 15:45, 16:00, 16:10",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Viernes de amigos',
						precio: new BigDecimal(20), precioOriginal: new BigDecimal(60), descuento: 73,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def sabadoNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.SABADO, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "12:00, 13:00",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Sabados dificiles',
						precio: new BigDecimal(50), precioOriginal: new BigDecimal(60), descuento: 17,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def sabadoNormalTemplate2 = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.SABADO, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "14:20, 14:40, 15:00, 15:20, 15:40, 16:00",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Sabados mas accesibles',
						precio: new BigDecimal(40), precioOriginal: new BigDecimal(60), descuento: 34,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def domingoNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.DOMINGO, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "12:00, 12:20, 12:40, 13:00",
						excluir: "03/02/2013, 17/02/2013",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Pre torneo domingo',
						precio: new BigDecimal(50), precioOriginal: new BigDecimal(60), descuento: 17,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def domingoTorneoTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.DOMINGO, tipoExtra: GreenFeeTemplate.TORNEO,
						horarios: "10:00, 10:20, 10:40",
						incluir: "03/02/2013, 17/02/2013",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Torneo domingo Ford',
						precio: new BigDecimal(54), precioOriginal: new BigDecimal(60), descuento: 10,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def festivoTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.FESTIVO, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "11:25, 11:35, 11:45,11:55",
						campo: campoSantJoan, cantidadDiasGenerar: 30, info: 'Juega los festivos al 50%',
						precio: new BigDecimal(30), precioOriginal: new BigDecimal(60), descuento: 50,
						golfistasMinimo: 2, disponibles: 4).save(failOnError: true);

					
				def lunesSevillaNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.LUNES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 09:40, 12:10, 14:10",
						campo: campo5, cantidadDiasGenerar: 30, info: 'Juega los lunes',
						precio: new BigDecimal(20), precioOriginal: new BigDecimal(30), descuento: 34,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def martesSevillaNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.MARTES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 09:40, 12:10, 14:10, 15:00",
						campo: campo5, cantidadDiasGenerar: 30, info: 'Juega los martes',
						precio: new BigDecimal(20), precioOriginal: new BigDecimal(30), descuento: 34,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def miercSevillaNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.MIERCOLES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 09:40, 12:10, 14:10",
						campo: campo5, cantidadDiasGenerar: 30, info: 'Juega los miercoles',
						precio: new BigDecimal(15), precioOriginal: new BigDecimal(30), descuento: 50,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def juevesSevillaNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.JUEVES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 09:40, 12:10, 14:10",
						campo: campo5, cantidadDiasGenerar: 30, info: 'Juega los jueves casi gratis!',
						precio: new BigDecimal(10), precioOriginal: new BigDecimal(30), descuento: 66,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def viernesSevillaNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.VIERNES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 09:40, 12:10, 14:10",
						campo: campo5, cantidadDiasGenerar: 30, info: 'Juega los viernes',
						precio: new BigDecimal(20), precioOriginal: new BigDecimal(30), descuento: 34,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def domSevillaTorneoTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.DOMINGO, tipoExtra: GreenFeeTemplate.TORNEO,
						horarios: "09:10, 09:20, 09:40, 12:10, 14:10",
						incluir: "03/02/2013, 17/02/2013",
						campo: campo5, cantidadDiasGenerar: 30, info: 'Juega torneos de domingo',
						precio: new BigDecimal(25), precioOriginal: new BigDecimal(30), descuento: 17,
						golfistasMinimo: 2, disponibles: 4).save(failOnError: true);

					
				def martesSCugatNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.MARTES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:00, 09:20, 09:50, 11:20, 13:40",
						campo: campoSantCugat, cantidadDiasGenerar: 30, info: 'Juega los martes',
						precio: new BigDecimal(50), precioOriginal: new BigDecimal(70), descuento: 29,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def miercSCugatNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.MIERCOLES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:00, 09:20, 09:50, 11:20, 13:40, 15:20",
						campo: campoSantCugat, cantidadDiasGenerar: 30, info: 'Juega los miercoles',
						precio: new BigDecimal(40), precioOriginal: new BigDecimal(70), descuento: 43,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def juevesSCugatNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.JUEVES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "08:10, 08:20, 14:10, 14:20, 14:30",
						campo: campoSantCugat, cantidadDiasGenerar: 30, info: 'Juega los jueves al 50!',
						precio: new BigDecimal(35), precioOriginal: new BigDecimal(70), descuento: 50,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def viernesSCugatNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.VIERNES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 09:40, 12:10, 14:10",
						campo: campoSantCugat, cantidadDiasGenerar: 30, info: 'Juega los viernes',
						precio: new BigDecimal(80), precioOriginal: new BigDecimal(100), descuento: 20,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);

				def juevesPPSCugatNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.JUEVES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "08:10, 08:20, 14:10, 14:20, 14:30",
						campo: campoSantCugatPP, cantidadDiasGenerar: 30, info: 'Juega los jueves al 50!',
						precio: new BigDecimal(10), precioOriginal: new BigDecimal(20), descuento: 50,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def viernesPPSCugatNormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.VIERNES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 12:10, 14:10",
						campo: campoSantCugatPP, cantidadDiasGenerar: 30, info: 'Juega los viernes PP',
						precio: new BigDecimal(15), precioOriginal: new BigDecimal(20), descuento: 25,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);

				def lunesAANormalTemplate1 = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.LUNES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "10:20, 10:30, 10:40",
						campo: campoAA, cantidadDiasGenerar: 30, info: 'Juega los lunes al 34',
						precio: new BigDecimal(20), precioOriginal: new BigDecimal(30), descuento: 34,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def lunesAANormalTemplate2 = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.LUNES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "14:50, 15:00, 15:10",
						campo: campoAA, cantidadDiasGenerar: 30, info: 'Lunes increibles',
						precio: new BigDecimal(15), precioOriginal: new BigDecimal(30), descuento: 50,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def martesAANormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.MARTES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 09:40, 10:20, 10:30, 10:40, 12:10, 14:10, 15:00",
						campo: campoAA, cantidadDiasGenerar: 30, info: 'Juega los martes',
						precio: new BigDecimal(20), precioOriginal: new BigDecimal(30), descuento: 34,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def miercAANormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.MIERCOLES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 09:40, 10:20, 10:30, 10:40, 12:10, 14:10, 15:00",
						campo: campoAA, cantidadDiasGenerar: 30, info: 'Juega los miercoles',
						precio: new BigDecimal(20), precioOriginal: new BigDecimal(30), descuento: 34,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def juevesAANormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.JUEVES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "10:40, 12:10, 14:10, 15:00",
						campo: campoAA, cantidadDiasGenerar: 30, info: 'Juega los jueves',
						precio: new BigDecimal(25), precioOriginal: new BigDecimal(30), descuento: 17,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);
				def viernesAANormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.VIERNES, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 09:40, 10:20, 10:30, 10:40, 12:10, 14:10, 15:00",
						campo: campoAA, cantidadDiasGenerar: 30, info: 'Juega los viernes',
						precio: new BigDecimal(28), precioOriginal: new BigDecimal(30), descuento: 7,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);

				def sabadoAANormalTemplate = new GreenFeeTemplate(hastaFechaGenerada: clockService.ahora().minusDays(1),
						tipo:GreenFeeTemplate.SABADO, tipoExtra: GreenFeeTemplate.NORMAL,
						horarios: "09:10, 09:20, 09:40, 10:20, 10:30, 10:40, 12:10, 14:10, 15:00",
						campo: campoAA, cantidadDiasGenerar: 30, info: 'Juega los sabado',
						precio: new BigDecimal(28), precioOriginal: new BigDecimal(30), descuento: 7,
						golfistasMinimo: 1, disponibles: 4).save(failOnError: true);

				*/						
				
//				def user1 = new User( username: 'reservas@golfcostadoradatarragona.com', enabled: true, password: 'Golf1CostaDorada9', email: 'reservas@golfcostadoradatarragona.com').save(failOnError: true)
				
				
				def ubicaciones = Ubicacion.createCriteria().list() {
					order("region")
					order("ciudad")
				}

				ubicaciones.each { ubi ->
					log.info("id:'" + ubi.id + "' / ciudad:'" + ubi.ciudad + "' / prov:'" + ubi.provincia + "' / region:'" + ubi.region + "'")
				}
				
			}

		}
		
    }
    def destroy = {
    }
}
