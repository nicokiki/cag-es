				<!--======= BEGIN OF header (.main_header) ======-->
				<header class="main_header">
					<section class="container">
						<div class="headerLoginHorizontal" >
							<div class="grid_12">
							
								<%-- 
									La idea es armarlo en base a saber si esta loggeado o no
										Si esta logeado entonces tengo q dejar 5 a la izquierda
										Si no lo esta   entonces tengo q dejar 7 a la izquierda
								--%>
								<sec:ifLoggedIn>
									<%-- El orden es: bienvenido-logout-lang_selector --%>
									<%-- Lo q viene es el Bienvenido --%>
									<div class="grid_3 prefix_5 omega rightAligned">
										<sec:ifAllGranted roles="ROLE_FACEBOOK">
											<div class="welcomeHeaderText headerButtonsNew"><g:message code="security.welcome.text" default="Bienvenido"/> <fb:showUserEmail /></div>
										</sec:ifAllGranted>
										<sec:ifNotGranted roles="ROLE_FACEBOOK">
											<div class="welcomeHeaderText headerButtonsNew"><g:message code="security.welcome.text" default="Bienvenido"/> <sec:username /></div>								
										</sec:ifNotGranted>
									</div>		
													
									<%-- Lo q viene es el logout --%>
									<div class="grid_2 omega rightAligned" >
										<sec:ifAllGranted roles="ROLE_FACEBOOK">
											<%-- No importa cual sea el q se importa de Facebook, lo importante es q se haga un import y listo --%>
											<script src="http://connect.facebook.net/es_ES/all.js"></script>
											<g:javascript> <!-- for client-side authentication -->
												FB.init({ appId:"${grailsApplication.config.grails.plugins.springsecurity.facebook.appId}",});
								                function doLogout() {
								                    if (typeof(FB) === 'object') {
								                		//FB.getLoginStatus(handleSessionResponse);    	
								                    	
								                        FB.logout(function() {
								                        	// No llamo directamente a Spring, sino a mi logout controller quien luego lo hara. Lo dejo x si acaso
								                            // window.location.href = "${createLink(uri: '/j_spring_security_logout')}";
								                            window.location.href = "${createLink(controller: 'logout')}";
								                        });
								                        return false;
								                    }
								                    return true;
								                }
								                
								                
								                function handleSessionResponse(response) {
								                	if (response.status !== 'connected' && response.status !== 'not_authorized') {
												    	window.location.href = "${createLink(controller: 'logout')}";
												    	return;
								                	}
													else {
													    FB.logout(function() {
								                        	// No llamo directamente a Spring, sino a mi logout controller quien luego lo hara. Lo dejo x si acaso
								                        	window.location.href = "${createLink(controller: 'logout')}";
								                        });
													    // Puede q el FB.logout haya fallado si el usuario se deslogueo a mano de facebook => hago el redirect igual 
													    window.location.href = "${createLink(controller: 'logout')}";
								                        return false;
							                        }
											    }
							                </g:javascript>										
											<a href="#" 
											   id="auth-logoutlink"
											   class="button darkblue small headerButtonsNew"
											   onclick="doLogout()" >
											  <g:message code="security.logout.text" />
											</a>
										</sec:ifAllGranted>
										<sec:ifNotGranted roles="ROLE_FACEBOOK">
											<g:link controller="logout" class="button darkblue small headerButtonsNew"><g:message code="security.logout.text" /></g:link>									
										</sec:ifNotGranted>
									</div>	
									
									<%-- Lo q viene es el language selector --%>										
									<div class="grid_2 omega rightAligned">
										<g:javascript>
											$().ready(function() {
											 	$("body").selectoul(); 
											 	
											 	/* Esto lo hago aca porque es donde puedo hacerlo por el selectoul() */
											 	/* Para poner el idioma, necesito saber el locale. Si es ingles entonces se pone '' 
											 	 * firstDay: 1 significa que el primero es Lunes y NO Domingo
											 	 */
											 	var localeAux = '${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}';
											 	if (localeAux == '') {
											 		/* La primera vez esta vacio y hay q asignarle un valor x defecto */ 
											 		localeAux = 'es';
											 	}
											 	if (localeAux == 'en') {
											 		localeAux = '';
											 	} 
											 	$.datepicker.setDefaults($.datepicker.regional[localeAux]); 
											 	$.datepicker.setDefaults({ firstDay: 1, dateFormat: 'dd/mm/yy' });
										  });
									  	</g:javascript>
								
										<golf:selector/>									
									</div>
									<%-- Fin para los logeados --%>
								</sec:ifLoggedIn>
								<sec:ifNotLoggedIn>
									<%-- El orden es: login-register-lang_selector --%>
									
									<div class="grid_3 prefix_7 omega">
										<g:link action="panel" controller="login" class="lightboxi button grey small headerButtonsNew"><g:message code="security.login.text" /></g:link>
										<g:link action="nuevo" controller="registro" class="lightboxiRegister button grey small headerButtonsNew"><g:message code="security.register.text" /></g:link>
					    				<g:javascript>
						    				jQuery('.lightboxi').lightbox({
											    width:570,
											    height:390,
											    modal:true,
											    iframe: true
										    });
						    				jQuery('.lightboxiRegister').lightbox({
											    width:570,
											    height:490,
											    modal:true,
											    iframe: true
										    });
					    				</g:javascript>
									</div>
	
									<%-- Lo q viene es el language selector --%>										
									<div class="grid_2 omega rightAligned">
										<g:javascript>
											$().ready(function() {
											 	$("body").selectoul(); 
											 	
											 	/* Esto lo hago aca porque es donde puedo hacerlo por el selectoul() */
											 	/* Para poner el idioma, necesito saber el locale. Si es ingles entonces se pone '' 
											 	 * firstDay: 1 significa que el primero es Lunes y NO Domingo
											 	 */
											 	var localeAux = '${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}';
											 	if (localeAux == '') {
											 		/* La primera vez esta vacio y hay q asignarle un valor x defecto */ 
											 		localeAux = 'es';
											 	}
											 	if (localeAux == 'en') {
											 		localeAux = '';
											 	} 
											 	$.datepicker.setDefaults($.datepicker.regional[localeAux]); 
											 	$.datepicker.setDefaults({ firstDay: 1, dateFormat: 'dd/mm/yy' });
										  });
									  	</g:javascript>
								
										<golf:selector/>									
									</div>
								
								</sec:ifNotLoggedIn>
								
						    </div>  
					    </div>
						
						<nav id="horizontal">
							<a class="logo" href="/"></a>
							<ul id="nav">
								<li>
									<a href="/"><g:message code="header.home.label" default="Home"/><br /><em><g:message code="header.home.under.label" default="Ir a inicio"/></em></a>
                                    <ul>
                                        <li>
                                            <g:link action="busquedaDeCamposAvanzada" controller="greenFee"><g:message code="home.busqueda.avanzada.label"/></g:link>
                                        </li>
                                    </ul>
								</li>
								<li>
									<g:link action="listaDeCampos" controller="campo"><g:message code="header.campos.label" /><br /><em><g:message code="header.campos.under.label" default="Ver todos" /></em></g:link>
									<ul>
										<li>
											<g:link action="listaDeCamposDeGolf" controller="campo"><g:message code="header.golf.label" /></g:link>
										</li>
										<li>
											<g:link action="listaDeCamposDePitchAndPutt" controller="campo"><g:message code="header.pp.label" /></g:link>
										</li>
									</ul>	
								</li>
								<li>
									<g:link action="ultimoMomento" controller="promociones"><g:message code="header.promocion.label" default="Promociones" /><br /><em><g:message code="header.promocion.under.label" default="Ver &uacute;ltimas ofertas" /></em></g:link>
								</li>
								<li>
									<g:link action="contactanos" controller="info"><g:message code="header.contacto.label" default="Cont&aacute;ctanos" /><br /><em><g:message code="header.contacto.under.label" default="Ponte en contacto" /></em></g:link>
								</li>
								<sec:ifLoggedIn>
									<%-- Lo q viene no tiene i18n xq es solo para ADMINs --%>
									<sec:ifAllGranted roles="ROLE_ADMIN">
										<li>
											<g:link controller="campos" action="listar" >Listar Campos<br /></g:link>
										</li>
										<li>
											<g:link controller="promocion" action="index" >Promociones<br /></g:link>
										</li>
									</sec:ifAllGranted>								
									<%-- Lo q viene SI tiene i18n xq es solo para ADMINs de CAMPO --%>
									<sec:ifAllGranted roles="ROLE_CAMPO">
										<li>
											<g:link controller="dashboard" action="principal" >
												<g:message code="header.dashboard.label" default="Administre su campo" /><br /><em><g:message code="header.dashboard.under.label" default="Green Fees Reservados, Modificaciones, etc" /></em>
											</g:link>
										</li>
									</sec:ifAllGranted>								
									<sec:ifAllGranted roles="ROLE_USER">
										<li>
											<g:link controller="usuario" >
												<g:message code="header.perfil.label" /><br /><em><g:message code="header.perfil.under.label" default="Mi cuenta" /></em>
											</g:link>
										</li>
									</sec:ifAllGranted>								
								</sec:ifLoggedIn>
								
							</ul><!-- End of #nav-->
						</nav><!-- End of menu nav#horizontal -->
						<div class="clear"></div>
					</section>
					
				</header>
