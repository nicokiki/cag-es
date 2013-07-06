				<!--======= BEGIN OF header (.main_header) ======-->
				<header class="main_header">
					<section class="container">
						<div class="headerLoginHorizontal" >
							<div class="grid_12">
								
						    </div>  
					    </div>
						
						<nav id="horizontal">
							<a class="logo" href="/"></a>
							<ul id="nav">
								<li>
									<a href="/"><g:message code="header.home.label" default="Home"/><br /><em><g:message code="header.home.under.label" default="Ir a inicio"/></em></a>
								</li>
								<li>
									<a href="/"><g:message code="header.campos.label" /><br /><em><g:message code="header.campos.under.label" default="Ver todos" /></em></a>
									<ul>
										<li>
											<a href="/"><g:message code="header.golf.label" /></a>
										</li>
										<li>
											<a href="/"><g:message code="header.pp.label" /></a>
										</li>
									</ul>	
								</li>
								<li>
									<a href="/"><g:message code="header.promocion.label" default="Promociones" /><br /><em><g:message code="header.promocion.under.label" default="Ver &uacute;ltimas ofertas" /></em></a>
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
