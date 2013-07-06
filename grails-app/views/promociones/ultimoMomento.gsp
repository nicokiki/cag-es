<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US" > 

	<head>
		<meta name="layout" content="main"/>

		<title>Click &amp; Golf | <g:message code="ofertas.titulo" /></title>
		
		<meta name="description" content="Mira el top-10 de ofertas de green fee por descuento. Muchas de estas ofertas son exclusivas de nuestro sitio web." />
		
		<r:require module="core"/>
		
		<r:script disposition="head">
			$(document).ready(function() {
			    setupGridAjax();
			});
			
			// Turn all sorting and paging links into ajax requests for the grid
			function setupGridAjax() {
				$("#blog").find("th.sortable a").live('click', function(event) {
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
				<div class="title"><g:message code="ofertas.titulo" /></div>
				<div class="slogan"><g:message code="ofertas.titulo.descripcion" /></div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorMessage">${flash.message}</div>
			</g:if>
					
			<section class="grid_9">
				<div id="blog">
   					<g:render template="ofertas" model="model" />
  				</div>
			</section>
			
			<section class="sidebar_r grid_3">
				<g:render template="/shared/social" model="['href':"${createLink(absolute: 'true', controller: 'promociones', action: 'ultimoMomento')}"]" />
			</section>

		</section> <!-- End section .container layout -->
		
	</body>

</html>
