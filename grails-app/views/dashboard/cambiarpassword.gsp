<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>
		
		<title>Click &amp; Golf | <g:message code="campos.dashboard.password.label"/></title>
		
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
                  	data:$('#cambiarPasswordForm').serialize(),        
                  	url:"${g.createLink(controller:'dashboard',action:'confirmarpassword')}",
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
                  			jQuery('#successUpdate').html("<g:message code='campos.dashboard.password.modify.ok.message'/>");
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
				<div class="title"><g:message code="campos.dashboard.password.label"/></div>
			</section>

			<section class="grid_8">
			
				<div id="contact">	
				
					<g:form class="form_place margin_top_0" method="POST" id='cambiarPasswordForm' name="cambiarPasswordForm" url="[controller:'dashboard', action:'confirmarpassword']">

						<fieldset class="fieldsetRegistro"> 
							<div class="clear"></div>
									
							<div class="div_verify">
								<div class="input_label user_verify"><label for="verify" accesskey="V"><g:message code="campos.dashboard.password.modify.anterior"/></label></div>
								<input name="passwordOld" type="password" id="verify" size="30" value="" class="pwd"  />
							</div>
	
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
						
						<div class="alert error_msg" id='greenFeeUpdate'  style="display: none;"></div>
						<div class="alert saved_msg" id='successUpdate'  style="display: none;"></div>
						
					</g:form>
				</div>
				
			</section>
			
			<section class="sidebar_r grid_4">
				<g:render template="greenFeesMenu" />
			</section>

		</section> <!-- End section .container layout -->
		
	</body>

</html>