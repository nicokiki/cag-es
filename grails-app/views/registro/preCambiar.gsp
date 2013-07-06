<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />
		
		<r:require module="core"/>
		
		<r:script disposition="head">
			$(document).ready(function() {
				$('#grid_8_hidden').delay("500").fadeIn("slow");
				
			});
			
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
                  	data:$('#cambiarPasswordForm').serialize(),        
                  	url:"${g.createLink(controller:'registro',action:'confirmarPassword')}",
                  	beforeSend:function() {
                  		showLocalSpinner();
                  		jQuery('#pwdError').html('');
                  		jQuery('#pwdError').hide();
                  	},
                  	complete:function() {
                  		hideLocalSpinner();
                  	},
                  	success:function(data,textStatus){
                  		if (data.status == 'ERROR') {
                  			jQuery('#pwdError').show();
                  			$.each( data.errors, function(i, n){
								jQuery('#pwdError').append(n);
								jQuery('#pwdError').append('<div class="clear"></div>');
						});
               			}
               			else {
               				jQuery('#pwdSuccess').show();
                  			jQuery('#pwdSuccess').append("<g:message code='security.forgot.cambiando.ok.message'/>");
                  			jQuery('#pwdSuccess').append('<div class="clear"></div>');
                  			jQuery('#pwdSuccess').append("<g:message code='contactanos.enviado.extra'/>");
                  			jQuery('#pwdSuccess').append('<div class="clear"></div>');
                  			jQuery('#pwdSuccess').append("<g:message code='security.forgot.cambiando.ok.otro'/>");
							setTimeout(function(){
                      				window.location.href="${request.contextPath}/";
                      			}, 3000);                  			
                  			
                       	}
                    },
                   	error:function(XMLHttpRequest,textStatus,errorThrown){
                   			jQuery('#pwdError').show();
	                   		jQuery('#pwdError').html(textStatus);
                       	}
       				})									
			}
			
		</r:script>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title">
				<g:if test='${flash.message}'>
					<div class="title"><g:message code="security.forgot.cambiando.error" /></div>
				</g:if>
				<g:else>
					<div class="title"><g:message code="security.forgot.cambiando.success" args="["${usuario?.email}"]" /></div>
				</g:else>
			</section>
			
			<g:if test='${flash.message}'>
				<div class="grid_8" >
					<div class="alert exclamation_msg" style="min-width: 400px;">${flash.message}</div>
				</div>
			</g:if>
					
			<section class="grid_8" id="grid_8_hidden" style="display: none">
				<g:if test='${usuario}'>
					<div id="contact">	
					
						<g:form class="form_place margin_top_0" method="POST" id='cambiarPasswordForm' name="cambiarPasswordForm" url="[controller:'registro', action:'confirmarPassword']">
							<input type="hidden" name="email" value="${usuario.email}" />	
							<fieldset class="fieldsetRegistro"> 
								<div class="clear"></div>
										
								<div class="div_verify">
									<div class="input_label user_verify"><label for="verify" accesskey="V"><g:message code="campos.dashboard.password.modify.nueva"/></label></div>
									<input name="passwordNew" type="password" id="verify" size="30" value="" class="pwd"  />
								</div>
								<div class="clear"></div>
	
								<div class="div_verify">
									<div class="input_label user_verify"><label for="verify" accesskey="V"><g:message code="campos.dashboard.password.modify.confirme"/></label></div>
									<input name="passwordNewConfirm" type="password" id="verifyConfirm" size="30" value="" class="pwd"  />
								</div>
							</fieldset>			
									
							<div class="clear"></div>
									
							<input class="button darkblue textcenter pointeredMouse" type='button' id="submit" value='${message(code: "campos.dashboard.password.modify.actualizar")}'
								onclick="runThis()"	
							/>
						
							<%-- Esto esta aqui abajo asi tiene una usabilidad mejor --%>
							<div id="spinnerLocal" class="spinner" style="display: none;">
								<img src="${resource(dir:'images',file:'spinner.gif')}"
									alt="Spinner" />
							</div>
							
							<div class="alert error_msg" id='pwdError' style="display: none;"></div>
							<div class="alert saved_msg" id='pwdSuccess' style="display: none;"></div>
							
						</g:form>
					</div>
				</g:if>
			</section>
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
