<!--[if IE]><!DOCTYPE html><![endif]-->
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />	

		<r:require module="core"/>

		<r:layoutResources />		

		<g:javascript>
			$('#username').focus();
		
			$('#verify').keypress(function(e) {
       			if(e.which == 13) {
            		jQuery(this).blur();
            		jQuery('#submit').focus().click();
        		}
    		});
    		
			function runThis() {
				jQuery.ajax({
          			type:'POST',
           			dataType: 'json',
                  	data:$('#ajaxLoginForm').serialize(),  
                  	url:"${g.createLink(controller:'registro',action:'olvide')}",      
                  	beforeSend:function() {
                  		jQuery('#loginUpdate').html('');
                  		jQuery('#loginUpdate').hide();
                  		showSpinner();
                  	},
                  	complete:function() {
                  		hideSpinner();
                  	},
                  	success:function(data,textStatus){
                 		if (data.status == 'ERROR') {
                 			jQuery('#loginUpdate').show();
                 			$.each( data.errors, function(i, n){
								jQuery('#loginUpdate').append(n);
								jQuery('#loginUpdate').append('<div class="clear"></div>');
							});
              			}
              			else {
              				jQuery('#loginSuccess').show();
                 			jQuery('#loginSuccess').html("<g:message code='security.forgot.email.enviado'/>");
                 			jQuery('#loginSuccess').append('<div class="clear"></div>');
                 			jQuery('#loginSuccess').append("<g:message code='security.forgot.email.extra'/>").fadeIn('slow');
                  			setTimeout(function(){
                     				window.parent.location=window.parent.location;
                     				window.parent.jQuery.lightbox().close();
                      			}, 2500);
                      		}
                      	},
                  	error:function(XMLHttpRequest,textStatus,errorThrown){
	               			jQuery('#loginUpdate').show();
	                  		jQuery('#loginUpdate').html(textStatus);
                       	}
   				});
   				return false;
   			}			
		</g:javascript>
	</head>

	<body class="bodyPanel">
		<div id="spinner" class="spinner" style="display: none;">
			<img src="${resource(dir:'images',file:'spinner.gif')}"
				alt="Spinner" />
		</div>
		
		<section id="wrapPanel">
			<section class="wrap_blockPanel">
		
				<section class="containerPanel layoutLogin">
				
					<section class="grid_7">
				
						<div id="contactPanel">
						
							<div class="title loginTitle textcenter"><g:message code="login.option.title1"/></div>
							
							<div class="alert error_msg" id='loginUpdate' style="display: none;"></div>
							<div class="alert succes_msg" id='loginSuccess' style="display: none;"></div>
						
							<form action='${request.contextPath}/j_spring_security_check' method='POST' id='ajaxLoginForm' name='ajaxLoginForm' class='form_place' >
								<fieldset class="fieldsetPanel"> 
									<div class="clear"></div>
									
										<div class="input_label user_email"><label><g:message code="springSecurity.login.username.label"/></label></div>
										<input name="j_username" type="text" id="username" class="email" size="30" tabindex="1" autocomplete="on"/>
										<div class="clear"></div>
		
										<div class="div_verify">
											<div class="input_label user_verify"><label for="verify" accesskey="V"><g:message code="springSecurity.login.password.label"/></label></div>
											<input name="j_password" type="password" id="verify" size="30" value="" class="pwd" tabindex="2"  />
										</div>
		
								</fieldset>			
								
								<div class="clear"></div>
								
								<div class="textcenter">
									<input class="button textcenter pointeredMouse" type='button' id="submit" value='${message(code: "springSecurity.login.button")}' tabindex="3"
										onclick="	jQuery.ajax({
			                              			type:'POST',
			                              			dataType: 'json',
					                              	data:$('#ajaxLoginForm').serialize()  + '&spring-security-redirect=/login/ajaxSuccess',        
					                              	url:'${request.contextPath}/j_spring_security_check',
					                              	beforeSend:function() {
					                              		jQuery('#loginUpdate').html('');
					                              		jQuery('#loginUpdate').hide();
					                              		showSpinner();
					                              	},
					                              	complete:function() {
					                              		hideSpinner();
					                              	},
					                              	success:function(data,textStatus){
					                              		if (data.error) {
					                              			jQuery('#loginUpdate').show();
					                              			jQuery('#loginUpdate').html(data.error);
					                              		}
					                              		else {
				                              				// Lo llevara al home de la pagina
				                              				window.parent.location=window.parent.location;
															<%--window.parent.location='${request.contextPath}';--%>
				                              				window.parent.jQuery.lightbox().close();
					                              		}
					                              	
			                              				/*
			                              				// Lo llevara al home de la pagina
			                              				window.parent.location=window.parent.location;
														<%--window.parent.location='${request.contextPath}';--%>
			                              				window.parent.jQuery.lightbox().close();
					                              		if (data.error) {
					                              			jQuery('#loginUpdate').html(data.error);
				                              			}
				                              			else {
				                              				// Lo llevara al home de la pagina
				                              				window.parent.location='${request.contextPath}';
				                              				window.parent.jQuery.lightbox().close();
			                              				}
			                              				*/
					                              	},
					                              	error:function(XMLHttpRequest,textStatus,errorThrown){ }
					                 				});return false" 
									/>
								</div>
								
								<div class="clear"></div>
								<div class="rightAligned">
									<input class="smaller green button textcenter pointeredMouse" type="button" id="forgot" value='${message(code: "springSecurity.forgot.pwd")}' tabindex="4"
										onclick="runThis()" />
								</div>
								
								
							</form>
						</div> <!--  contact -->
		
						<!-- El hr va a dejar una separacion -->
						<hr />
					
							<div class="title loginTitle textcenter"><g:message code="login.option.title2"/></div>
							
							<div class="textcenter" style="margin-top: 10px;">
								<sec:ifNotGranted roles="ROLE_FACEBOOK">
									<fb:serverSideConnect parent="true" />
<%--									<facebookAuth:connect permissions="${['email', 'user_about_me', 'publish_stream', 'user_photos']}" />--%>
<%--									<fb:connect permissions="${['email', 'user_about_me', 'publish_stream', 'user_photos']}" parent="true" />--%>
								</sec:ifNotGranted>
							</div>
						
					</section>
				</section> <!-- End section .container layout -->
				

			</section>
			<!-- End section .wrap_blockPanel -->
		</section>
		<!-- End section #wrapPanel -->			
		
		<r:layoutResources />		
	</body>

</html>
