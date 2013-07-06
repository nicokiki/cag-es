<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />
		
		<r:require module="core"/>
		
		<r:script disposition="head">
			$(document).ready(function() {
				$('#grid_8_hidden').delay("300").fadeIn("slow");
				
				<g:if test='${usuario}'>
					setTimeout(function() {
						window.location.href = "${createLink(controller:'home', action: 'index')}";
					}, 2500);
				</g:if>
				
			});
		</r:script>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<g:if test='${flash.message}'>
					<div class="title"><g:message code="registro.usuario.verificado.error" /></div>
				</g:if>
				<g:else>
					<div class="title"><g:message code="registro.usuario.verificado.success" /></div>
				</g:else>
			</section>
			
			<g:if test='${flash.message}'>
				<div class="grid_8" >
					<div class="alert exclamation_msg" style="min-width: 400px;">${flash.message}</div>
				</div>
			</g:if>
					
			<section class="grid_8" id="grid_8_hidden" style="display: none">
				<g:if test='${usuario}'>
					<div class="alert succes_msg margin_top_0" style="min-width: 400px;">
						<g:message code="registro.usuario.verificado.success.more" args="["${usuario?.email}"]" />
						<div class="clear"></div>
						<g:message code="contactanos.enviado.extra"  />
					</div>
				</g:if>
			</section>
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
