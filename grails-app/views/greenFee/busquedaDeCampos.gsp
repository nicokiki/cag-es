<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Busca tus green fees por ubicaci&#243;n y fecha. Elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />	
		
		<title>Click &amp; Golf | <g:message code="search.campos.encontrados.label" /></title>
		
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
			
			function submitYesterday() {
				 $("#buscarGreenFeeFormYesterday").submit(); 
			}
			function submitTomorrow() {
				 $("#buscarGreenFeeFormTomorrow").submit(); 
			}
		</r:script>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<div class="title"><g:message code="search.campos.encontrados.label" default="Campos encontrados" /></div>
				<div class="slogan"><g:message code="search.greenfees.label" default="Green fees en" />&nbsp;<span class="fontWeighted">${ubicacionSplitted}</span>&nbsp;<g:message code="search.greenfees.el.label" default="el" />&nbsp;<span class="fontWeighted">${diaEnTexto}</span>&nbsp;<span class="fontWeighted">${formattedDate}</span></div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorMessage">${flash.message}</div>
			</g:if>
					
			<section class="columns_demo">
				  <div class="grid_3">
				  	<g:if test="${includeYesterday == 'YES'}">
					  	<input class="button blue embossed large pointeredMouse" type="button" onclick="submitYesterday()" value="&lt;&lt;  ${message(code: 'campos.todos.anterior', default: 'D&iacutea Anterior')}" />
				  	</g:if>
				  	<g:else>
				  		&nbsp;
				  	</g:else>
				  </div>
				  <div class="grid_6">
				  	&nbsp;
				  </div>
				  <!-- end .grid_6 -->
				  <div class="grid_3">
       				<input class="button blue embossed large pointeredMouse rightFloat" type="button" onclick="submitTomorrow()" value="${message(code: 'campos.todos.siguiente', default: 'D&iacutea Siguiente')}  &gt;&gt;" />
				  </div>
			</section>		  
					
			<section class="grid_12">
				<div id="grid">
   					<g:render template="greenFees" model="model" />
  				</div>
			</section>
			
			<g:form method="POST" name='buscarGreenFeeFormYesterday' url="[controller:'greenFee', action:'busquedaDeCampos']">
				<input type="hidden" name="fecha" value="${yesterdayFrom}" />					
				<input type="hidden" name="ubicacion" value="${nombreUbicacion}" />
			</g:form>
			<g:form method="POST" name='buscarGreenFeeFormTomorrow' url="[controller:'greenFee', action:'busquedaDeCampos']">
				<input type="hidden" name="fecha" value="${tomorrowFrom}" />					
				<input type="hidden" name="ubicacion" value="${nombreUbicacion}" />
			</g:form>
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
