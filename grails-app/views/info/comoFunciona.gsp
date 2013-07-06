<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="C&#243;mo utilizar Click &amp; Golf para poder buscar vuestros green fees." />	
		
		<title>Click &amp; Golf | <g:message code="como.funciona.titulo" /></title>
		
		<r:require module="core"/>
		
		<r:script disposition="defer">
		</r:script>		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
		
			<section class="page_title">
				<div class="title"><g:message code="como.funciona.titulo" /></div>
			</section>
			<div class="clear"></div>
			
			<div class="portfolio_content">
				
				<div data-id="item1" data-type="webdesign graphics" class="grid_3 item" >
					<div class="pf_inner" style="min-height: 300px;">
						<div>
							<img src="${resource(dir:'images/assets',file:'busca.png')}" alt="busca.png"  />
						</div>
						<div class="clear"></div>
						<h2 class="textcenter"><g:message code="como.funciona.busca.titulo" /></h2>
						<p><g:message code="como.funciona.busca.descripcion" /></p>
					</div>
				</div>

				<div data-id="item2" data-type="webdesign graphics" class="grid_3 item">
					<div class="pf_inner" style="min-height: 300px;">
						<div>
							<img src="${resource(dir:'images/assets',file:'compara.png')}" alt="compara.png"  />
						</div>
						<div class="clear"></div>
						<h2 class="textcenter"><g:message code="como.funciona.compara.titulo" /></h2>
						<p><g:message code="como.funciona.compara.descripcion" /></p>
					</div>
				</div>
				
				<div data-id="item3" data-type="webdesign graphics" class="grid_3 item">
					<div class="pf_inner" style="min-height: 300px;">
						<div>
							<img src="${resource(dir:'images/assets',file:'reserva.png')}" alt="reserva.png"  />
						</div>
						<div class="clear"></div>
						<h2 class="textcenter"><g:message code="como.funciona.elige.titulo" /></h2>
						<p><g:message code="como.funciona.elige.descripcion" /></p>
					</div>
				</div>
				
				<div data-id="item4" data-type="webdesign graphics" class="grid_3 item">
					<div class="pf_inner" style="min-height: 300px;">
						<div style="text-align: center;">
							<img src="${resource(dir:'images/assets',file:'juega.png')}" alt="juega.png" style="width: 130px;"  />
						</div>
						<div class="clear"></div>
						<h2 class="textcenter"><g:message code="como.funciona.juega.titulo" /></h2>
						<p><g:message code="como.funciona.juega.descripcion" /></p>
					</div>
				</div>
				
			</div>
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
