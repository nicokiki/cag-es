<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>
		<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
		<meta charset="UTF-8" />
		<meta name="description" content="Website the reserva online de green fees" />
		<meta name="keywords" content="Premium, HTML5, Template" />
		<meta name="author" content="Smartik" />
		
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
		
				<section class="container layoutLogin">
				
					<section class="page_title">
						<div class="title"><g:message code="campos.administrar.title"/></div>
						<div class="slogan"><g:message code="campos.administrar.descripcion"/></div>
					</section>

					<g:if test='${flash.message}'>
						<div class="errorLoginMessage">${flash.message}</div>
					</g:if>
					
					<div class="grid_12">
						<div id="grid">
           					<g:render template="campos" model="model" />
           				</div>
           				
           				<div class="clear"></div>
						<div>
							<div class="block_content">
								<g:link action="nuevo" controller="campos" class="button grey textcenter"><g:message code="com.clickandgolf.Campo.nuevo" default="Nuevo" /></g:link>
							</div>
						</div>           				
           				
        			</div>
        			

				</section> <!-- End section .container layout -->
					
		
	</body>

</html>
