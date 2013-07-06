<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Cont&aacute;ctanos" />	
		
		<title>Click &amp; Golf | <g:message code="contactanos.titulo" /></title>
		
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
                  	data:$('#contactanosForm').serialize(),        
                  	url:"${g.createLink(controller:'info',action:'contactado')}",
                  	beforeSend:function() {
                  		showLocalSpinner();
                  		jQuery('#contactoUpdate').html('');
                  		jQuery('#contactoUpdate').hide();
                  	},
                  	complete:function() {
                  		hideLocalSpinner();
                  	},
                  	success:function(data,textStatus){
                  		if (data.status == 'ERROR') {
                  			jQuery('#contactoUpdate').show();
                  			$.each( data.errors, function(i, n){
								jQuery('#contactoUpdate').append(n);
								jQuery('#contactoUpdate').append("<div class='clear'></div>");
						});
               			}
               			else {
               				jQuery('#successUpdate').show();
                  			jQuery('#successUpdate').html("<g:message code='contactanos.enviado'/>");
                  			jQuery('#successUpdate').append("<div class='clear'></div>");
                  			jQuery('#successUpdate').append("<g:message code='contactanos.enviado.extra'/>").fadeIn('slow');
                      			setTimeout(function(){
                      				window.location.href="${request.contextPath}/";
                       			}, 2500);
                       		}
                       	},
                   	error:function(XMLHttpRequest,textStatus,errorThrown){
                   			jQuery('#contactoUpdate').show();
	                   		jQuery('#contactoUpdate').html(textStatus);
                       	}
       				})									
			}
			
					
<%--			function runThis() {--%>
<%--				jQuery.ajax({--%>
<%--           			type:'POST',--%>
<%--           			dataType: 'json',--%>
<%--                  	data:$('#contactanosForm').serialize(),        --%>
<%--                  	url:"${g.createLink(controller:'info',action:'contactado')}",--%>
<%--                  	beforeSend:function() {--%>
<%--                  		showLocalSpinner();--%>
<%--                  		jQuery('#contactoUpdate').html('');--%>
<%--                  	},--%>
<%--                  	complete:function() {--%>
<%--                  		hideLocalSpinner();--%>
<%--                  	},--%>
<%--                  	success:function(data,textStatus){--%>
<%--                  		if (data.status == 'ERROR') {--%>
<%--                  			$.each( data.errors, function(i, n){--%>
<%--								jQuery('#contactoUpdate').append(n);--%>
<%--								jQuery('#contactoUpdate').append("<div class='clear'></div>");--%>
<%--						});--%>
<%--               			}--%>
<%--               			else {--%>
<%--                  			jQuery('#successUpdate').html('<g:message code="contactanos.enviado"/>');--%>
<%--                  			jQuery('#successUpdate').html('<div class='clear'></div>');--%>
<%--                  			jQuery('#successUpdate').html('<g:message code="contactanos.enviado.extra"/>');--%>
<%--                  			--%>
<%--                   			setTimeout(function() {--%>
<%--								window.location.href="${request.contextPath}";--%>
<%--                       			}, 3000);--%>
<%--                       		}--%>
<%--                       	},--%>
<%--                   	error:function(XMLHttpRequest,textStatus,errorThrown){--%>
<%--	                   		jQuery('#contactoUpdate').html(textStatus);--%>
<%--                       	}--%>
<%--       				})									--%>
<%--			}--%>
		</g:javascript>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<div class="title"><g:message code="contactanos.titulo" /></div>
				<div class="slogan"><g:message code="contactanos.titulo.descripcion" /></div>
			</section>

			<section class="grid_8">
				<div id="contact">				
					
					<g:form class="form_place margin_top_0" method="POST" id='contactanosForm' name="contactanosForm" url="[controller:'info', action:'contactado']">
						<fieldset class="fieldsetPanel">
							<div class="clear"></div>
							<div class="input_label user"><label><g:message code="contactanos.nombre" /></label></div>
							<input name="nombre" type="text" id="nombre" class="name" size="30" value="" />
							<div class="clear"></div>
							
							<div class="input_label user_email"><label><g:message code="contactanos.email" /></label></div>
							<input name="email" type="text" id="email" class="email" size="30" value="" />
							<div class="clear"></div>
							
							<div class="input_label user_subject"><label><g:message code="contactanos.asunto" /></label></div>
							<input name="asunto" type="text" id="asunto" class="subject" size="30" value="" />
							<div class="clear"></div>
							
							<textarea name="mensaje" cols="40" rows="10"  id="mensaje" style="min-width: 500px; min-height: 200px; max-width: 930px;"></textarea>
							<div class="clear"></div>
							
							<div class="grid_7 alpha">
								<div class="div_verify">
									<div class="input_label user_verify2"><label for="verify" accesskey="V">
									<img src="${createLink(controller: 'simpleCaptcha', action: 'captcha')}" class="c_img_verify" style="vertical-align: middle;"/>
									</label></div>
									<input name="captcha" type="text" id="captcha" size="4" value="" class="captcha" placeholder="???" />
								</div>
							</div>
							<div class="clear"></div>
							<input class="button darkgray textcenter pointeredMouse" type='button' id="submit" value='${message(code: "contactanos.envia")}'
								onclick="runThis()"	
							/>
							
						</fieldset>
						
						<%-- Esto esta aqui abajo asi tiene una usabilidad mejor --%>
						<div id="spinnerLocal" class="spinner" style="display: none;">
							<img src="${resource(dir:'images',file:'spinner.gif')}"
								alt="Spinner" />
						</div>

						<div class="alert error_msg" id="contactoUpdate" style="display: none;"></div>
						<div class="alert succes_msg" id="successUpdate" style="display: none;"></div>
						
					</g:form>
				</div>
			</section>
			
			<section class="sidebar_r grid_4">
				<div class="adress_info">
					<h4><g:message code="contactanos.medios.de.contacto" /></h4>
					<ul class="list15"><li>info@clickandgolf.es</li></ul>
					<ul class="list15"><li>comercial@clickandgolf.es</li></ul>
					<ul class="list15"><li>cancelaciones@clickandgolf.es</li></ul>
					<ul class="list15"><li>soporte@clickandgolf.es</li></ul>
				</div>
				<div class="clear"></div>
			</section><!-- End .grid_5 -->			

		</section> <!-- End section .container layout -->
		
	</body>

</html>
