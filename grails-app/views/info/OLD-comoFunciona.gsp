<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>
		<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
		<meta charset="UTF-8" />
		<meta name="description" content="Politica de cancelacion de reservas de Click &amp; Golf" />
		<meta name="keywords" content="legal, condiciones" />
		
		<r:require module="core"/>
		
		<r:script disposition="defer">
			$(document).ready(function() {
				$('ul#roundabt').roundabout({
					easing: 'easeInOutBack',
					duration: 1000,
					triggerFocusEvents: true,
					clickToFocus: true
				}).
				focus(function() { 
					var aux = $('.roundabout-in-focus').attr('id');
					$('#rab1-h1').hide();
					$('#rab2-h1').hide();
					$('#rab3-h1').hide();
					$('#rab4-h1').hide();
					
					$('#' + aux +  '-h1').show();
				})
				;
			});
		</r:script>		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
			
			<div id="rab1-h1" class="page_title noMarginNoPadding" >
				<h1 class="textcenter noMargin"><g:message code="como.funciona.busca.titulo" /></h1>
				<h2 class="textcenter noMargin"><g:message code="como.funciona.busca.descripcion" /></h2>
			</div>	
			<div id="rab2-h1" class="page_title noMarginNoPadding" style="display:none;">
				<h1 class="textcenter noMargin"><g:message code="como.funciona.compara.titulo" /></h1>
				<h2 class="textcenter noMargin"><g:message code="como.funciona.compara.descripcion" /></h2>
			</div>	
			<div id="rab3-h1" class="page_title noMarginNoPadding" style="display:none;">
				<h1 class="textcenter noMargin"><g:message code="como.funciona.elige.titulo" /></h1>
				<h2 class="textcenter noMargin"><g:message code="como.funciona.elige.descripcion" /></h2>
			</div>	
			<div id="rab4-h1" class="page_title noMarginNoPadding" style="display:none;">
				<h1 class="textcenter noMargin"><g:message code="como.funciona.juega.titulo" /></h1>
				<h2 class="textcenter noMargin"><g:message code="como.funciona.juega.descripcion" /></h2>
			</div>	
				
			<section class="grid_12 placeslider" style="min-height: 480px; margin-top:0px;">
			
				<ul id="roundabt">
					<!--[if gt IE 7]>
						<li id="rab1" ><a href="#"><img  style="height: 200px; width: 250px;" src="${resource(dir:'images/assets',file:'busca.png')}" alt="busca.png"  /></a></li>					
						<li id="rab4" ><a href="#"><img  style="height: 200px; width: 250px;" src="${resource(dir:'images/assets',file:'juega.png')}" alt="juega.png" /></a></li>
						<li id="rab3" ><a href="#"><img  style="height: 200px; width: 250px;" src="${resource(dir:'images/assets',file:'reserva.png')}" alt="reserva.png" /></a></li>
						<li id="rab2" ><a href="#"><img  style="height: 200px; width: 250px;" src="${resource(dir:'images/assets',file:'compara.png')}" alt="compara.png" /></a></li>
					<![endif]-->
					<!--[if !IE]><!-->
						<li id="rab1" ><a href="#"><img  src="${resource(dir:'images/assets',file:'busca.png')}" alt="busca.png"  /></a></li>
						<li id="rab4" ><a href="#"><img  src="${resource(dir:'images/assets',file:'juega.png')}" alt="juega.png" /></a></li>
						<li id="rab3" ><a href="#"><img  src="${resource(dir:'images/assets',file:'reserva.png')}" alt="reserva.png" /></a></li>
						<li id="rab2" ><a href="#"><img  src="${resource(dir:'images/assets',file:'compara.png')}" alt="compara.png" /></a></li>
					<!--<![endif]-->
				</ul>
			</section><!-- End of .placeslider-->
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
