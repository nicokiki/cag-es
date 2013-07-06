<!DOCTYPE html>
<html>
	<head>
		<canonical:show />

		<%-- SEO tags --%>
		<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
		<meta charset="UTF-8" />
		<meta name="robots" content="index, follow" />
		<meta name="keywords" content="golf, pitch &amp; putt, green fee, reserva green fees, book tee times, espa&ntilde;a, spain" />
		<meta name="language" content="Spanish">
		<meta name="google-site-verification" content="k-mmQp5ZxeLEHnFSQFgzpG5fKBh5clybIEMFkSrv1Ec" />

		<%-- Para Facebook --%>
		<meta property="og:title" content="Click & Golf | Reserva tus green fees online" />
		<meta property="og:url" content="http://www.clickandgolf.es" />
		<meta property="og:description" content="Reserva green fees de golf y pitch & putt online, elige entre una variada cantidad de campos de golf y pitch & putt en España. Ahorra hasta un 75% en green fees y sin costos extra." />
		<meta property="og:image" content="${resource(absolute: 'true', dir:'images/facebook', file:'logo_bandera_200.png')}" />
		<%-- Fin para Facebook --%>
		 
		
		<link rel="shortcut icon" href="${resource(dir:'images', file:'favicon.ico')}" type="image/x-icon">
		
		<g:layoutHead />
		
		<title><g:layoutTitle default="${message(code:'page.title', default:'Click &amp; Golf | Reserva tus green fees online')}" /></title>
		
		<r:layoutResources />
	</head>
	<body>

		<div id="spinner" class="spinner" style="display: none;">
			<img src="${resource(dir:'images',file:'spinner.gif')}"
				alt="Spinner" />
		</div>
	
		<section id="wrap">
			<section class="wrap_block" style="background: #eee;">
	
				<g:layoutBody />
	
				<footer class="main_footer">
					<section class="container">
	
						<div class="grid_3">
							<h3 class="ft_title">&nbsp;</h3>
							<ul class="vnav">
								<li><g:link  action="faq" controller="info">FAQs</g:link></a></li>
								<li><g:link action="comoFunciona" controller="info"><g:message code="footer.como.funciona" /></g:link></li>
								<li><g:link action="condiciones" controller="info"><g:message code="footer.condiciones" /></g:link></li>
								<li><g:link fragment="cancel" action="condiciones" controller="info"><g:message code="politica.de.cancelacion" /></g:link></li>
								<li><g:link action="politicaDePrivacidad" controller="info"><g:message code="footer.privacy" /></g:link></li>
							</ul>
						</div>
	
						<div class="grid_3">
							<h3 class="ft_title"><g:message code="footer.who.we.are" default="Quienes somos"/></h3>
							<p><g:message code="footer.who.we.are.answer" default="Somos apasionados del golf que tenemos por objetivo acercar a todos a este bellisimo deporte."/></p>
							<ul class="vnav">
								<li>
									<g:link action="quienesSomos" controller="info"><g:message code="footer.who.we.are.more" /></g:link>
								</li>
							</ul>
						</div>
						<div class="grid_3">
							<h3 class="ft_title"><g:message code="footer.contact" default="Contacto"/></h3>
							<p>
								Email: <a href="#">info@clickandgolf.es</a>
							</p>
							<ul class="vnav">
								<li>
									<g:link action="contactanos" controller="info"><g:message code="header.contacto.label" default="Cont&aacute;ctanos" /></g:link>
								</li>
							</ul>
						</div>
	
						<div class="grid_3">
							<h3 class="ft_title"><g:message code="footer.social.find.us" default="Encuentranos en"/></h3>
							<p><g:message code="footer.social.find.us.more" default="Seguinos en las redes sociales, contactanos por email, skype o simplemente subscribite a nuestro RSS!"/></p>
							<div class="social">
								<a href="http://twitter.com/clickandgolf_es" class="twitter" target="_blank"></a> 
								<a href="http://www.facebook.com/clickandgolf.es" class="facebook" target="_blank"></a>
								<a href="https://plus.google.com/u/0/105045050369363320503" class="google" target="_blank"></a>
								<a href="mailto:info@clickandgolf.es" class="email" ></a> 
								<a href="callto://clickandgolf" class="skype"></a> 
							</div>
	
	
						</div>
					</section>
	
					<section class="mini_footer">
						<div class="container">
							<div class="alignleft copyright">&copy; Click &amp; Golf, 2013. Todos los derechos reservados</div>
							<nav id="ft_links" style="top: 20px;">
								<ul id="nav_footer" >
									<li style="padding: 0 12px; margin-top: 5px;">
										<img src="https://www.paypalobjects.com/webstatic/es_ES/i/es-cardpayment-options.png">
									</li>
									<li><a href="/"><g:message code="header.home.label" /></a></li>
									<li>
										<g:link action="quienesSomos" controller="info"><g:message code="footer.mini.acercade.label" /></g:link>
									</li>
									<li>
										<a href="${createLink(controller:'sitemap', action: 'sitemap')}" target="_blank">Sitemap</a>
									</li>
								</ul>
							</nav>
						</div>
					</section>
				</footer>
				<!-- End .main_footer -->
	
	
			</section>
			<!-- End section .wrap_block -->
		</section>
		<!-- End section #wrap -->
	
			<script type="text/javascript">
<%--				Hecho por un bug de facebook NO solucionado todavia --%>
<%--				http://stackoverflow.com/questions/7131909/facebook-callback-appends-to-return-url --%>
				if (window.location.hash == '#_=_') {
				    window.location.hash = ''; // for older browsers, leaves a # behind
				    history.pushState('', document.title, window.location.pathname); // nice and clean
				    e.preventDefault(); // no page reload
				}
			</script>
	
		<r:layoutResources />
		
	</body>
</html>