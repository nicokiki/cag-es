<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Busca tus green fees por ubicaci&#243;n y fecha. Elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />	
		
		<title>Click &amp; Golf | <g:message code="greenfee.found.titulo.label" /></title>
		
		<r:require module="core"/>
		
		<r:script disposition="head">
			$(document).ready(function() {
			    setupGridAjax();
			});
			
			// Turn all sorting and paging links into ajax requests for the grid
			function setupGridAjax() {
				$("#grid").find("th.sortable a").live('click', function(event) {
				event.preventDefault();
				var url = $(this).attr('href');
				
				var closestDiv = $(this).closest('div');
				
				$(closestDiv).html($("#spinner").html());
				
				$.ajax({
				type: 'POST',
				url: url,
				success: function(data) {
				$(closestDiv).fadeOut('fast', function() {$(this).html(data).fadeIn('slow');});
				}
				})
				});			
			

				$("#pagination").find("a").live('click', function(event) {
				event.preventDefault();
				var url = $(this).attr('href');
				
				var closestDiv = $(this).closest('div');
				
				$(closestDiv).html($("#spinner").html());
				
				$.ajax({
				type: 'POST',
				url: url,
				success: function(data) {
				$(closestDiv).fadeOut('fast', function() {$(this).html(data).fadeIn('slow');});
				}
				})
				});			
			}
			
		</r:script>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<div class="title"><g:message code="greenfee.found.titulo.label" default="Reserva tu Green Fee" /></div>
				<div class="slogan"><g:link mapping="campo" params="[id:"${fieldValue(bean: campo, field: "id")}", nombre:"${fieldValue(bean: campo, field: "hyphenatedNombre")}"]">${fieldValue(bean: campo, field: "nombre")}</g:link></div>
				<div class="slogan"><span class="fontWeighted">${diaEnTexto}</span>&nbsp;<span class="fontWeighted">${fecha}</span></div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorMessage">${flash.message}</div>
			</g:if>
					
			<section class="columns_demo">
				<div class="grid_4">
					<div>
        				<h2 class="margin_top_0"><g:message code="greenfee.found.subtitulo.label" default="Elige el horario"/></h2> 
					</div>				
				</div>
					<div class="grid_4 push_2">
	      				<g:if test="${includeYesterday == 'YES'}">
							<g:link class="button small blue rightFloat" mapping="greenfeeCampo" params="[idCampo:"${fieldValue(bean: campo, field: "id")}", nombreCampo:"${fieldValue(bean: campo, field: "hyphenatedNombre")}", dia:"${yesterday.dia}", mes:"${yesterday.mes}", anio:"${yesterday.anio}"]">
								&lt;&lt;&nbsp;&nbsp;<g:message code="campos.todos.anterior" />
							</g:link>
          				</g:if>
          				<g:else>
          					&nbsp;
           				</g:else>
           			</div>
				<div class="grid_2 push_2">
					<g:link class="button small blue rightFloat" mapping="greenfeeCampo" params="[idCampo:"${fieldValue(bean: campo, field: "id")}", nombreCampo:"${fieldValue(bean: campo, field: "hyphenatedNombre")}", dia:"${tomorrow.dia}", mes:"${tomorrow.mes}", anio:"${tomorrow.anio}"]">
						<g:message code="campos.todos.siguiente" />&nbsp;&nbsp;&gt;&gt;
					</g:link>
				</div>
			</section>	
								
			<section class="grid_12">
				<div id="grid">
   					<g:render template="greenFeesEncontrados" model="model" />
  				</div>
			</section>
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
