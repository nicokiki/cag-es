<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />
		
		<title>Click &amp; Golf | <g:message code="greenfee.book.cancel.title"/></title>
		
		<r:require module="core"/>
		
		<r:script disposition="head">
			$(document).ready(function() {
				$('#grid_8_hidden').delay("300").fadeIn("slow");
				
				var linkAssembled = '${createLink(absolute: 'true', mapping: 'greenfeeCampo' , action:'preparar', params: [idCampo: "${fieldValue(bean: greenFee, field: "campo.id")}", nombreCampo:"${fieldValue(bean: greenFee, field: "campo.hyphenatedNombre")}", dia: "${fieldValue(bean: greenFee, field: "diaMes")}", mes: "${fieldValue(bean: greenFee, field: "mes")}", anio: "${fieldValue(bean: greenFee, field: "anio")}"])}';
				setTimeout(function(){
                      window.location.href=linkAssembled;
				}, 3000);
			});
		</r:script>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<div class="title"><g:message code="greenfee.book.cancel.title" default="Siga buscando!" /></div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorMessage">${flash.message}</div>
			</g:if>
					
			<section class="grid_8" id="grid_8_hidden" style="display: none">
						
				<div class="adress_info" style="min-width: 400px;">
					<h2 class="margin_top_0"><g:message code="greenfee.book.cancel.descripcion"/></h2>
					<div class="clear"></div>
					<p>
						<g:message code="greenfee.book.cancel.redirect.search"/>
					</p>
				</div>
	
			</section>
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
