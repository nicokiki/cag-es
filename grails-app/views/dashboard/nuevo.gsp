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
                  	data:$('#nuevoGreenFeeForm').serialize(),        
                  	url:"${g.createLink(controller:'dashboard',action:'guardar')}",
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
                  			jQuery('#successUpdate').html("<g:message code='campos.dashboard.greenfee.admin.save.ok.message'/>");
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
			
			$("#fecha").datepicker({
        		minDate: new Date() 
    		});
    		
    		if ($("#fecha").val().length == 0) {
    			var myDate = new Date();
				myDate.setDate(myDate.getDate()+1);
				
			 	var month = myDate.getMonth() + 1;
			 	var mm = month < 10 ? "0" + month : month;
			    var day = myDate.getDate();
				var dd = day < 10 ? "0" + day : day;
			    var year = myDate.getFullYear();
				
    			$("#fecha").val(dd + "/" + mm + "/" + year);
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

				<g:form class="form_place margin_top_0" method="POST" id='nuevoGreenFeeForm' name="nuevoGreenFeeForm" url="[controller:'dashboard', action:'guardar']">
<%--				<g:form controller="dashboard" action="modificar" class="form_place margin_top_0" >--%>

				
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.diaHora" default="Fecha"/>:</label>
							<div class="block_content">
								<input type="text" id="fecha" name="fecha" class="i-format" placeholder="dd/mm/yyyy" style="width: 70px; min-width: 70px;" />
								<input type="text" id="hora" name="hora" maxlength="2" class="i-format" placeholder="hh" style="width: 30px;min-width: 30px;" />
								<input type="text" id="minuto" name="minuto" maxlength="2" class="i-format" placeholder="mm" style="width: 50px;min-width: 50px;" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.precio.en.campo" />:</label>
							<div class="block_content">
								<input type="text" name="precioOriginal" class="i-format" required="required" style="width: 70px; min-width: 70px;" />
								<span class="input_tips"><g:message code="com.clickandgolf.GreenFee.precio.input.tips" default="El descuento y fee pueden cambiar al cambiar este valor"/></span>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.precio.clickandgolf" />:</label>
							<div class="block_content">
								<input type="text" name="precio" class="i-format" required="required" style="width: 70px; min-width: 70px;"/>
								<span class="input_tips"><g:message code="com.clickandgolf.GreenFee.precio.input.tips" default="El descuento y fee pueden cambiar al cambiar este valor"/></span>
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.disponibles" default="Disponibles"/>:</label>
							<div class="block_content">
								<input type="text" name="disponibles" class="i-format" value="4" required="required" style="width: 70px; min-width: 70px;"/>
								<span class="input_tips"><g:message code="com.clickandgolf.GreenFee.disponibles.input.tips" default="Elija un valor entre 1 y 4"/></span>
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.golfistasMinimo" default="Jugadores.Mínimos"/>:</label>
							<div class="block_content">
								<golf:select name="golfistasMinimo" value="1" dataList="${['1', '2', '4']}"  cssClass="chzn-select-deselect" style="width:220px; min-width: 220px;" placeHolderValue="${message(code: 'com.clickandgolf.GreenFee.edit.mininimos')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="greenfee.found.hoyos" />:</label>
							<div class="block_content">
								<golf:select name="hoyos" value="18" dataList="${['18', '9']}"  cssClass="chzn-select-deselect" style="width:80px; min-width: 80px;" placeHolderValue="${message(code: 'greenfee.found.hoyos')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.GreenFee.info" default="Info"/>:</label>
							<div class="block_content">
								<input type="text" name="info" class="i-format" required="required" style="width: 150px; min-width: 150px;"/>
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<div class="block_content">
								<input 	class="button darkgray textcenter pointeredMouse" type='button' 
										id="submit" value='${message(code: "campos.dashboard.greenfee.admin.save")}'
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