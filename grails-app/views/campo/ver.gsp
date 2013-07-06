<!DOCTYPE html>
<%@page import="com.clickandgolf.Ubicacion"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />		
		<title>Click &amp; Golf | ${fieldValue(bean: campo, field: "nombre")}</title>
		
		<r:require module="core"/>
		
	</head>

	<body>
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->

		<section class="container layoutLogin">
		
			<section class="page_title">
				<div class="title">${fieldValue(bean: campo, field: "nombre")}</div>
				<div class="slogan">${fieldValue(bean: campo, field: "ubicacion.ciudad")}, ${fieldValue(bean: campo, field: "ubicacion.provincia")}, ${fieldValue(bean: campo, field: "ubicacion.region")}</div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorLoginMessage">${flash.message}</div>
			</g:if>
			
			<section class="grid_9">

				<section id="blog">
					<!--============= ARTICLE BEGIN ===========-->
					<article class="full_entry">
						<div>
							<img class="featured_img border" src="${wthr.staticResource(id:"${campo.id}",nombre:"${campo.imagenPrincipal}")}" alt="${campo.imagenPrincipal}" title="${campo.imagenPrincipal}" />
							<div class="clear"></div>
							<h3><g:message code="campo.descripcion" /></h3>
							<p>${campo.descripcion}</p>
							<div class="clear"></div>
							<div class="clear"></div>
							
							<blockquote><p>${campo.descripcionQuotted}</p></blockquote>
							<div class="clear"></div><!-- Article description end -->
						</div><!--.grid_8-->
						<div class="clear"></div>
						<div class="clear"></div>
						<!-- Author info -->
						<section class="author_info">
							<div class="author_details">
								<strong><g:message code="campo.tarjeta" default="Tarjeta de Puntuacion"/></strong>

								<table class="ajax" style="font-size: smaller;">
								    <tbody>	
								    	<tr style="font-weight:bold;">
								    		<td style="text-align: center;padding: 4px;"><g:message code="campo.hoyo"/></td>
								    		<td style="text-align: center;padding: 4px;">1</td>
								    		<td style="text-align: center;padding: 4px;">2</td>
								    		<td style="text-align: center;padding: 4px;">3</td>
								    		<td style="text-align: center;padding: 4px;">4</td>
								    		<td style="text-align: center;padding: 4px;">5</td>
								    		<td style="text-align: center;padding: 4px;">6</td>
								    		<td style="text-align: center;padding: 4px;">7</td>
								    		<td style="text-align: center;padding: 4px;">8</td>
								    		<td style="text-align: center;padding: 4px;">9</td>
											<g:if test="${campo.scorecardmetadata?.is18()}">
									    		<td style="text-align: center;padding: 4px;">10</td>
									    		<td style="text-align: center;padding: 4px;">11</td>
									    		<td style="text-align: center;padding: 4px;">12</td>
									    		<td style="text-align: center;padding: 4px;">13</td>
									    		<td style="text-align: center;padding: 4px;">14</td>
									    		<td style="text-align: center;padding: 4px;">15</td>
									    		<td style="text-align: center;padding: 4px;">16</td>
									    		<td style="text-align: center;padding: 4px;">17</td>
									    		<td style="text-align: center;padding: 4px;">18</td>
											</g:if>    		
								    	</tr>
								    	<tr style="border-bottom: medium;">
								    		<td style="text-align: center;padding: 4px; font-weight:bold;">Par</td>
								    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo1}</td>
								    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo2}</td>
								    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo3}</td>
								    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo4}</td>
								    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo5}</td>
								    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo6}</td>
								    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo7}</td>
								    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo8}</td>
								    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo9}</td>
											<g:if test="${campo.scorecardmetadata?.is18()}">
									    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo10}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo11}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo12}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo13}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo14}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo15}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo16}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo17}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.scorecardmetadata?.parHoyo18}</td>
											</g:if>    		
								    	</tr>
								    	<g:if test="${campo.blancas}">
									    	<tr style="font-size: smaller;">
									    		<td style="text-align: center;padding: 4px; font-weight:bold;">Mts.</td>
									    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo1}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo2}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo3}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo4}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo5}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo6}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo7}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo8}</td>
									    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo9}</td>
												<g:if test="${campo.scorecardmetadata?.is18()}">
										    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo10}</td>
										    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo11}</td>
										    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo12}</td>
										    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo13}</td>
										    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo14}</td>
										    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo15}</td>
										    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo16}</td>
										    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo17}</td>
										    		<td style="text-align: center;padding: 4px;">${campo.blancas?.metrosHoyo18}</td>
												</g:if>    		
									    	</tr>
								    	</g:if>
								    	<g:if test="${campo.amarillas}">
									    	<tr style="font-size: smaller;">
									    		<td style="text-align: center;padding: 4px; font-weight:bold;">Mts.</td>
									    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo1}</td>
									    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo2}</td>
									    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo3}</td>
									    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo4}</td>
									    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo5}</td>
									    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo6}</td>
									    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo7}</td>
									    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo8}</td>
									    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo9}</td>
												<g:if test="${campo.scorecardmetadata?.is18()}">
										    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo10}</td>
										    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo11}</td>
										    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo12}</td>
										    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo13}</td>
										    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo14}</td>
										    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo15}</td>
										    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo16}</td>
										    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo17}</td>
										    		<td style="text-align: center;padding: 4px; background-color: yellow;">${campo.amarillas?.metrosHoyo18}</td>
												</g:if>    		
									    	</tr>
								    	</g:if>
								    	<g:if test="${campo.rojas}">
									    	<tr style="font-size: smaller; ">
									    		<td style="text-align: center;padding: 4px; font-weight:bold;">Mts.</td>
									    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo1}</td>
									    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo2}</td>
									    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo3}</td>
									    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo4}</td>
									    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo5}</td>
									    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo6}</td>
									    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo7}</td>
									    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo8}</td>
									    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo9}</td>
												<g:if test="${campo.scorecardmetadata?.is18()}">
										    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo10}</td>
										    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo11}</td>
										    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo12}</td>
										    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo13}</td>
										    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo14}</td>
										    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo15}</td>
										    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo16}</td>
										    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo17}</td>
										    		<td style="text-align: center;padding: 4px; background-color: red;">${campo.rojas?.metrosHoyo18}</td>
												</g:if>    		
									    	</tr>
								    	</g:if>
								    	<g:if test="${campo.azules}">
									    	<tr style="font-size: smaller;">
									    		<td style="text-align: center;padding: 4px; font-weight:bold;">Mts.</td>
									    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo1}</td>
									    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo2}</td>
									    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo3}</td>
									    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo4}</td>
									    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo5}</td>
									    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo6}</td>
									    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo7}</td>
									    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo8}</td>
									    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo9}</td>
												<g:if test="${campo.scorecardmetadata?.is18()}">
										    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo10}</td>
										    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo11}</td>
										    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo12}</td>
										    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo13}</td>
										    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo14}</td>
										    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo15}</td>
										    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo16}</td>
										    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo17}</td>
										    		<td style="text-align: center;padding: 4px; background-color: blue;">${campo.azules?.metrosHoyo18}</td>
												</g:if>    		
									    	</tr>
								    	</g:if>
								    </tbody>
							    </table>
							</div>
						</section>
						<!-- Author info -->
						<div class="clear"></div>
						<hr/>
	
						<div>
							<div class="clear"></div>
							<h3><g:message code="campo.contacto" /></h3>
								<ul class="list14">
									<li>
										<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.Campo.telefono"/>:</span>
										&nbsp;${campo.telefono}
									</li>
								</ul> 
								<ul class="list11">
									<li>
										<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.Campo.direccion"/>:</span>
										&nbsp;${campo.direccion}
									</li>
								</ul> 
								<ul class="list15">
									<li>
										<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.Campo.emailContacto"/>:</span>
										&nbsp;${campo.emailContacto}
									</li>
								</ul> 
							<div class="clear"></div>
						
						
							<div class="clear"></div>
							<h3><g:message code="campo.como.llegar" default="Direcciones"/></h3>
							<p>${campo.comoLlegar}</p>
							<div class="clear"></div>
							
							<g:if test="${campo.coordenadasGps}">
								<h3><g:message code="campo.coordenadasGps" default="Coordenadas GPS"/></h3>
								<p>${campo.coordenadasGps}</p>
								<div class="clear"></div>
							</g:if>
							
							<div class="clear"></div><!-- Article description end -->
							
							
						</div><!--.grid_9-->
						<div class="clear"></div>
	
						<iframe width="98%" height="400" class="contact_map border" src="http://maps.google.com/maps?q=${campo.latitud},${campo.longitud}&num=1&vpsrc=0&hl=${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}&ie=UTF8&t=m&z=14&ll=${campo.latitud},${campo.longitud}&output=embed"></iframe>					

						<g:if test="${campo.normasSeccion || campo.normasLink}">
							<hr />
							<h3 id="normasSeccion"><g:message code="campo.normas" default="Normas / Reglas del campo"/></h3>
							<p>${campo.normasSeccion}</p>			
							<div class="clear"></div>
							<g:if test="${campo.normasLink}">
								<a href="#normasSeccion" class="button white small" onclick="window.open('${campo.normasLink}')" title='${message(code: "campo.normas.link")}''><g:message code="campo.normas.link" default="Más información"/></a>
							</g:if>
							<div class="clear"></div>
						</g:if>				

					</article>
					<div class="clear"></div>
					<!--============= ARTICLE END ===========-->
					
				</section>				
				
			</section>
			
			<section class="sidebar_r grid_3">
				<section class="widget">
					<h2><g:message code="com.clickandgolf.Campo.widget.titulo" /></h2>
					<div class="wgt_in">
						<ul class="vertical-menu" style="padding-left: 0px;">
							<li>
								<g:link class="button blue" mapping="greenfeeCampo" params="[idCampo:"${fieldValue(bean: campo, field: "id")}", nombreCampo:"${fieldValue(bean: campo, field: "hyphenatedNombre")}", dia:"${tomorrow.dia}", mes:"${tomorrow.mes}", anio:"${tomorrow.anio}"]">
									<g:message code="com.clickandgolf.Campo.greenfees" />
								</g:link>
							</li>
							<li><a href="${campo.linkCampo}" target="_blank"><g:message code="campo.website.official" default="Sitio Web"/></a></li>
							<li><a href="${campo.linkExtra}" target="_blank"><g:message code="campo.website.extra" default="Mas Informacion"/></a></li>
							<li>
								<a href="http://maps.google.com/maps?hl=${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}&ll=${campo.latitud},${campo.longitud}&spn=0.005666,0.009624&t=k&z=15&lightbox[width]=610&lightbox[height]=360" class="lightbox" >
									Google Maps
								</a>							
							</li>
						</ul>
					</div>
					<div class="clear"></div>
				</section>	
				
				<g:render template="/shared/social" model="['href':"${linkSocial}"]" />
			</section>

		</section> <!-- End section .container layout -->
		
	</body>

</html>
