<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>
		
		<title>Click &amp; Golf | <g:message code="campos.dashboard.greenfee.booked.label"/></title>
		
		<r:require module="core"/>
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<div class="title"><g:message code="campos.dashboard.greenfee.booked.label"/></div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorMessage">${flash.message}</div>
			</g:if>

			<section class="grid_8">
			
				<div class="adress_info greenFeeReservadoVer">
					<h4><g:message code="campos.dashboard.greenfee.booked.detail"/></h4>
					<ul class="list19 greenFeeReservadoVer_ul">
						<li>
							<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.diaHora"/>:</span>
							&nbsp;<joda:format value="${greenFeeReservado.diaHora}" />
						</li>
					</ul> 
					<ul class="list9 greenFeeReservadoVer_ul">
						<li>
							<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.golfistas"/>:</span>
							&nbsp;${fieldValue(bean: greenFeeReservado, field: "golfistas")}
						</li>
					</ul> 
					<g:if test='${greenFeeReservado.licencia1}'>
						<ul class="list9 greenFeeReservadoVer_ul">
							<li>
								<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.licencia" />&nbsp;1:</span>
								&nbsp;${fieldValue(bean: greenFeeReservado, field: "licencia1")}
							</li>
						</ul> 
					</g:if>
					<g:if test='${greenFeeReservado.licencia2}'>
						<ul class="list9 greenFeeReservadoVer_ul">
							<li>
								<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.licencia" />&nbsp;2:</span>
								&nbsp;${fieldValue(bean: greenFeeReservado, field: "licencia2")}
							</li>
						</ul> 
					</g:if>
					<g:if test='${greenFeeReservado.licencia3}'>
						<ul class="list9 greenFeeReservadoVer_ul">
							<li>
								<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.licencia" />&nbsp;3:</span>
								&nbsp;${fieldValue(bean: greenFeeReservado, field: "licencia3")}
							</li>
						</ul> 
					</g:if>
					<g:if test='${greenFeeReservado.licencia4}'>
						<ul class="list9 greenFeeReservadoVer_ul">
							<li>
								<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.licencia" />&nbsp;4:</span>
								&nbsp;${fieldValue(bean: greenFeeReservado, field: "licencia4")}
							</li>
						</ul> 
					</g:if>
					
					<ul class="list18 greenFeeReservadoVer_ul">
						<li>
							<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.feePagado"/>:</span>
							&nbsp;<g:formatNumber number="${greenFeeReservado.feePagado}" format="#0.00" /> &euro;
						</li>
					</ul> 
					<ul class="list18 greenFeeReservadoVer_ul">
						<li>
							<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.precioPorPagar"/>:</span>
							&nbsp;<g:formatNumber number="${greenFeeReservado.precioPorPagar}" format="#0.00" /> &euro;
						</li>
					</ul> 
					<ul class="list9 greenFeeReservadoVer_ul">
						<li>
							<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.descuento"/>:</span>
							&nbsp;${fieldValue(bean: greenFeeReservado, field: "descuento")} &#37;
						</li>
					</ul> 
					<ul class="list19 greenFeeReservadoVer_ul">
						<li>
							<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.fechaReserva"/>:</span>
							&nbsp;<joda:format value="${greenFeeReservado.fechaReserva}" />
						</li>
					</ul> 
					<ul class="list15 greenFeeReservadoVer_ul">
						<li>
							<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.usuario.email"/>:</span>
							&nbsp;${fieldValue(bean: greenFeeReservado, field: "usuario.email")}
						</li>
					</ul> 
					<g:if test='${greenFeeReservado.usuario.nombre}'>
						<ul class="list9 greenFeeReservadoVer_ul">
							<li>
								<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.usuario.nombre"/>:</span>
								&nbsp;${fieldValue(bean: greenFeeReservado, field: "usuario.nombre")}
							</li>
						</ul> 
					</g:if>
					<g:if test='${greenFeeReservado.usuario.apellidos}'>
						<ul class="list9 greenFeeReservadoVer_ul">
							<li>
								<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.usuario.apellidos"/>:</span>
								&nbsp;${fieldValue(bean: greenFeeReservado, field: "usuario.apellidos")}
							</li>
						</ul> 
					</g:if>
					<g:if test='${greenFeeReservado.usuario.licenciaGolf}'>
						<ul class="list9 greenFeeReservadoVer_ul">
							<li>
								<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.usuario.licenciaGolf"/>:</span>
								&nbsp;${fieldValue(bean: greenFeeReservado, field: "usuario.licenciaGolf")}
							</li>
						</ul> 
					</g:if>
					<g:if test='${greenFeeReservado.usuario.licenciaPP}'>
						<ul class="list9 greenFeeReservadoVer_ul">
							<li>
								<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.GreenFeeReservado.usuario.licenciaPP"/>:</span>
								&nbsp;${fieldValue(bean: greenFeeReservado, field: "usuario.licenciaPP")}
							</li>
						</ul> 
					</g:if>
					
				</div>
				<div class="clear"></div>
			
			</section>
			
			<section class="sidebar_r grid_4">
				<g:render template="greenFeesMenu" />
			</section>

		</section> <!-- End section .container layout -->
		
	</body>

</html>
