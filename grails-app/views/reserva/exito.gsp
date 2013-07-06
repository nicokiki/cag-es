<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />
		
		<title>Click &amp; Golf | <g:message code="greenfee.book.success.title"/></title>
		
		<r:require module="core"/>
		
		<r:script disposition="head">
			$(document).ready(function() {
				$('#grid_8_hidden').delay("300").fadeIn("slow");
				$('#grid_4_hidden').delay("350").fadeIn("slow");
				
			});
		</r:script>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<div class="title"><g:message code="greenfee.book.success.title" default="Reserva de Green Fee" /></div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorMessage">${flash.message}</div>
			</g:if>
					
			<section class="grid_8" id="grid_8_hidden" style="display: none">
				<g:render template="confirmacion" model="model" />		
				
			</section>
			
			<section class="sidebar_r grid_4" id="grid_4_hidden" style="display: none">
			
				<div class="service_block textcenter">
					<img src="${resource(dir:'images/big_icons',file:'4.png')}" alt="email" />
					<h3 class="margin_top_0"><g:message code="greenfee.book.success.email.confirmacion" args="["${usuario.email}"]" /></h3>
					<span class="input_tips"><g:message code="greenfee.book.success.email.confirmacion.tips"/></span>
					<div class="clear"></div>
					<span class="input_tips"><g:message code="greenfee.book.success.email.confirmacion.tips2"/></span>
				</div>
				<div class="clear"></div>			
			
			</section>
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
