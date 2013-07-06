<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>
		
		<title>Click &amp; Golf | <g:message code="campos.dashboard.title"/> ${fieldValue(bean: campo, field: "nombre")}</title>
		
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
				<div class="title"><g:message code="campos.dashboard.title"/> ${fieldValue(bean: campo, field: "nombre")}</div>
				<div class="slogan"><g:message code="campos.dashboard.description" /></div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorMessage">${flash.message}</div>
			</g:if>
					
			<section class="grid_8">
				<h3 class="margin_top_0"><g:message code="campos.dashboard.bookedGreenFees"/></h3> 
			
				<div id="grid">
   					<g:render template="greenFeesReservados" model="model" />
  				</div>
			</section>
			
			<section class="sidebar_r grid_4">
				<g:render template="greenFeesMenu" />
			</section>

		</section> <!-- End section .container layout -->
		
	</body>

</html>
