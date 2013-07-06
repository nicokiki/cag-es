<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="FAQ, Preguntas Frecuentes de Click &amp; Golf" />	
		
		<title>Click &amp; Golf | FAQ</title>
		
		<r:require module="core"/>
		
		<g:javascript>
		</g:javascript>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<div class="title"><g:message code="faq.titulo" /></div>
			</section>
			
			<section class="grid_12">
			
				<div class="toggle_block question">
					<h4><g:message code="faq.costo.adicional.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.costo.adicional.rta1" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="toggle_block question">
					<h4><g:message code="faq.como.se.reserva.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.como.se.reserva.rta1" />
							</p>
							<div class="clear"></div>
							<p>
								<span class="fontWeighted">1.-</span>&nbsp;
								<g:message code="faq.como.se.reserva.rta2" />&nbsp;
								<g:link controller="home"><g:message code="header.home.label" /></g:link>
							</p>
							<div class="clear"></div>
							<p>
								<div class="img_pf_hover alignleft">
									<a href="${resource(dir:'images/assets/faq',file:'buscando_grande.png')}" class="lightbox" title="Zoom">
										<img src="${resource(dir:'images/assets/faq',file:'buscando_chica.png')}" alt="buscando_chica.png" style="opacity: 0.6;">
										<span class="img_pf_icon2 zoom"></span>
									</a>
								</div>
								<div class="middleAligned">
									<span class="fontWeighted">2.-</span>&nbsp;
									<g:message code="faq.como.se.reserva.rta3"/>
								</div>
							</p>
							<div class="clear"></div>
							<p>
								<div class="img_pf_hover alignleft">
									<a href="${resource(dir:'images/assets/faq',file:'jugadores_grande.png')}" class="lightbox" title="Zoom">
										<img src="${resource(dir:'images/assets/faq',file:'jugadores_chica.png')}" alt="jugadores_chica.png" style="opacity: 0.6;">
										<span class="img_pf_icon2 zoom"></span>
									</a>
								</div>
								<div class="middleAligned">
									<span class="fontWeighted">3.-</span>&nbsp;
									<g:message code="faq.como.se.reserva.rta4"/>
								</div>
							</p>
							<div class="clear"></div>
							<p>
								<span class="fontWeighted">4.-</span>&nbsp;
								<g:message code="faq.como.se.reserva.rta5" />&nbsp;
							</p>
							<div class="clear"></div>
							<p>
								<g:message code="faq.como.se.reserva.rta6" />&nbsp;
							</p>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="toggle_block question">
					<h4><g:message code="faq.debo.pagar.online.todo.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.debo.pagar.online.todo.rta1" />
							</p>
							<div class="clear"></div>
							<p>
								<g:message code="faq.debo.pagar.online.todo.rta2" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>

				<div class="toggle_block question">
					<h4><g:message code="faq.debo.imprimir.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.debo.imprimir.rta1" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="toggle_block question">
					<h4><g:message code="faq.tiempo.antelacion.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.tiempo.antelacion.rta1" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="toggle_block question">
					<h4><g:message code="faq.llegar.tarde.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.llegar.tarde.rta1" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="toggle_block question">
					<h4><g:message code="faq.email.confirmacion.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.email.confirmacion.rta1" />
								<g:link action="contactanos" controller="info"><g:message code="header.contacto.label" default="Cont&aacute;ctanos" /></g:link>
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="toggle_block question">
					<h4><g:message code="faq.cambiar.dia.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.cambiar.dia.rta1" />
								<g:link fragment="cancel" action="condiciones" controller="info"><g:message code="politica.de.cancelacion" /></g:link>
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>

				<div class="toggle_block question">
					<h4><g:message code="faq.cancelar.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.cancelar.rta1" />
								<g:link fragment="cancel" action="condiciones" controller="info"><g:message code="politica.de.cancelacion" /></g:link>
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="toggle_block question">
					<h4><g:message code="faq.cancelar.cobrar.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.cancelar.cobrar.rta1" />
								<g:link fragment="cancel" action="condiciones" controller="info"><g:message code="politica.de.cancelacion" /></g:link>
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
			
				<div class="toggle_block question">
					<h4><g:message code="faq.cancelar.12.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.cancelar.12.rta1" />
								<g:link fragment="cancel" action="condiciones" controller="info"><g:message code="politica.de.cancelacion" /></g:link>
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>

				<div class="toggle_block question">
					<h4><g:message code="faq.normas.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.normas.rta1" />
								<g:link action="listaDeCampos" controller="campo"><g:message code="header.campos.label" default="Campos de golf" /></g:link>
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>

				<div class="toggle_block question">
					<h4><g:message code="faq.tiempo.antes.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.tiempo.antes.rta1" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
			
				<div class="toggle_block question">
					<h4><g:message code="faq.requiere.licencia.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<div class="img_pf_hover alignleft">
									<a href="${resource(dir:'images/assets/faq',file:'alerta_grande.png')}" class="lightbox" title="Zoom">
										<img src="${resource(dir:'images/assets/faq',file:'alerta_chica.png')}" alt="alerta_chica.png" style="opacity: 0.6;">
										<span class="img_pf_icon2 zoom"></span>
									</a>
								</div>
								<div class="middleAligned">
									<g:message code="faq.requiere.licencia.rta1"/>
								</div>
							</p>
							<div class="clear"></div>
							<p>
								<g:message code="faq.requiere.licencia.rta2" />
								<g:link action="listaDeCampos" controller="campo"><g:message code="header.campos.label" default="Campos de golf" /></g:link>
							</p>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				<div class="clear"></div>
			
				<div class="toggle_block question">
					<h4><g:message code="faq.clima.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.clima.rta1" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="toggle_block question">
					<h4><g:message code="faq.contactarme.por.clima.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.contactarme.por.clima.rta1" />
							</p>
							<div class="clear"></div>
							<p>
								<g:message code="faq.contactarme.por.clima.rta2" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="toggle_block question">
					<h4><g:message code="faq.clima.cierra.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.clima.cierra.rta1" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>

				<div class="toggle_block question">
					<h4><g:message code="faq.como.registrar.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.como.registrar.rta1" />
							</p>
							<div class="clear"></div>
							<p>
								<g:message code="faq.como.registrar.rta2" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>

				<div class="toggle_block question">
					<h4><g:message code="faq.email.no.recibido.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.email.no.recibido.rta1" />
								<g:link action="contactanos" controller="info"><g:message code="header.contacto.label" default="Cont&aacute;ctanos" /></g:link>
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="toggle_block question">
					<h4><g:message code="faq.contrasenia.olvide.titulo" /></h4>
					<div class="toggle_content">
						<div class="inner">	
							<p>
								<g:message code="faq.contrasenia.olvide.rta1" />
								<g:link action="contactanos" controller="info"><g:message code="header.contacto.label" default="Cont&aacute;ctanos" /></g:link>
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				
				
			</section>		

		</section> <!-- End section .container layout -->
		
	</body>

</html>
