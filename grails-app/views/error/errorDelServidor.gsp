<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>
		
		<title>Click &amp; Golf | <g:message code="general.500.titulo" /></title>
		
		<r:require module="core"/>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<div class="title"><g:message code="general.500.titulo" /></div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorMessage">${flash.message}</div>
			</g:if>
					
			<section class="grid_12">
				<p class="text404 justifyAligned">
					<g:message code="general.500.texto" args="["${trans}"]" />
				</p>
			</section>
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
