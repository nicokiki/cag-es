<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>
		
		<title>Click &amp; Golf | <g:message code="campos.dashboard.greenfee.admin.label"/></title>
		
		<r:require module="core"/>
		
		<g:javascript>
			var showLocalSpinner = function() {
			    $('#spinnerLocal').show();
			}
			
			var hideLocalSpinner = function() {
			    $('#spinnerLocal').hide();
			}
					
			function runThis() {
				jQuery.ajax({
           			type:'POST',
           			dataType: 'json',
                  	data:$('#cambiarGreenFeeForm').serialize(),        
                  	url:"${g.createLink(controller:'dashboard',action:'modificar')}",
                  	beforeSend:function() {
                  		showLocalSpinner();
                  		jQuery('#greenFeeUpdate').html('');
                  		jQuery('#greenFeeUpdate').hide();
                  	},
                  	complete:function() {
                  		hideLocalSpinner();
                  	},
                  	success:function(data,textStatus){
                  		if (data.status == 'ERROR') {
                  			jQuery('#greenFeeUpdate').show();
                  			$.each( data.errors, function(i, n){
								jQuery('#greenFeeUpdate').append(n);
								jQuery('#greenFeeUpdate').append("<div class='clear'></div>");
						});
               			}
               			else {
               				jQuery('#successUpdate').show();
                  			jQuery('#successUpdate').html("<g:message code='campos.dashboard.greenfee.admin.modify.ok.message'/>");
                      			setTimeout(function(){
                      				window.location.href="${request.contextPath}/${controllerName}/administrar";
                       			}, 2500);
                       		}
                       	},
                   	error:function(XMLHttpRequest,textStatus,errorThrown){
                   			jQuery('#greenFeeUpdate').show();
	                   		jQuery('#greenFeeUpdate').html(textStatus);
                       	}
       				})									
			}
		</g:javascript>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<div class="title"><g:message code="campos.dashboard.greenfee.admin.label"/></div>
				<div class="slogan"><g:message code="campos.dashboard.greenfee.admin.description" /></div>
			</section>

			<section class="grid_8">

				<g:form class="form_place margin_top_0" method="POST" id='cambiarGreenFeeForm' name="cambiarGreenFeeForm" url="[controller:'dashboard', action:'modificar']">
<%--				<g:form controller="dashboard" action="modificar" class="form_place margin_top_0" >--%>

				
					<input type="hidden" name="id" value="${greenFee.id}" />
				
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.diaHora" default="Fecha"/>:</label>
							<div class="block_content">
								<input onclick="this.select()" readonly="readonly" type="text" name="nombre" value="${fieldValue(bean: greenFee, field: "diaHora")}" class="i-format" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.estado" default="Estado"/>:</label>
							<div class="block_content">
								<golf:select name="estado" value="${greenFee?.estado}" dataList="${['ACTIVO', 'INACTIVO']}"  cssClass="chzn-select-deselect" style="width:250px;" placeHolderValue="${message(code: 'com.clickandgolf.GreenFee.edit.estado', default: 'Elige un estado')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.precio" default="Precio"/>:</label>
							<div class="block_content">
								<input type="text" name="precio" value="${precioFormatted}" class="i-format" required="required" />
								<span class="input_tips"><g:message code="com.clickandgolf.GreenFee.precio.input.tips" default="El descuento y fee pueden cambiar al cambiar este valor"/></span>
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.disponibles" default="Disponibles"/>:</label>
							<div class="block_content">
								<input type="text" name="disponibles" value="${fieldValue(bean: greenFee, field: "disponibles")}" class="i-format" required="required" />
								<span class="input_tips"><g:message code="com.clickandgolf.GreenFee.disponibles.input.tips" default="Elija un valor entre 1 y 4"/></span>
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.golfistasMinimo" default="Jugadores.Mínimos"/>:</label>
							<div class="block_content">
								<golf:select name="golfistasMinimo" value="${greenFee?.golfistasMinimo}" dataList="${[1, 2, 4]}"  cssClass="chzn-select-deselect" style="width:100px;" placeHolderValue="${message(code: 'com.clickandgolf.GreenFee.edit.mininimos')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.info" default="Info"/>:</label>
							<div class="block_content">
								<input type="text" name="info" value="${fieldValue(bean: greenFee, field: "info")}" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<div class="block_content">
								<input 	class="button darkgray textcenter pointeredMouse" type='button' 
										id="submit" value='${message(code: "campos.dashboard.greenfee.admin.modificar")}'
										onclick="runThis()"	
								/>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<%-- Esto esta aqui abajo asi tiene una usabilidad mejor --%>
					<div id="spinnerLocal" class="spinner" style="display: none;">
						<img src="${resource(dir:'images',file:'spinner.gif')}"
							alt="Spinner" />
					</div>
					
					<div class="alert error_msg" id='greenFeeUpdate' style="display: none;"></div>
					<div class="alert saved_msg" id='successUpdate' style="display: none;"></div>
				
				</g:form>
			
			</section>
			
			<section class="sidebar_r grid_4">
				<g:render template="greenFeesMenu" />
			</section>

		</section> <!-- End section .container layout -->
		
	</body>

</html>