<html>
	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />	
		
		<title>Click &amp; Golf | <g:message code="springSecurity.login.title"/></title>
		
		<r:require module="core"/>
		
		<g:javascript>
			function runThis() {
				jQuery.ajax({
          			type:'POST',
           			dataType: 'json',
                  	data:$('#loginForm').serialize(),  
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
                 			jQuery('#loginSuccess').append("<div class='clear'></div>");
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

	<body>
		
				<!--======= BEGIN OF header (.main_header) ======-->
				<g:render template="/shared/header" />
				<!--======= END OF header (.main_header) ======-->

				<section class="container layoutLogin">
				
					<section class="page_title">
						<div class="title"><g:message code="springSecurity.login.title"/></div>
						<div class="slogan"><g:message code="login.option.title"/></div>
					</section>
					
					<div class="grid_12">
						<div class="grid_7 alpha">
						
							<div id="contact">
						
								<div class="title loginTitle textcenter"><g:message code="login.option.title1"/></div>
								
								<div class="alert error_msg" id='loginUpdate' style="display: none;"></div>
								<div class="alert succes_msg" id='loginSuccess' style="display: none;"></div>
								
								
								<form action='${postUrl}' method='POST' id='loginForm' class='form_place' >
								
									<fieldset class="fieldsetPanel"> 
										<div class="clear"></div>
										
											<div class="input_label user_email"><label><g:message code="springSecurity.login.username.label"/></label></div>
											<input name="j_username" type="text" id="username" class="email" size="30" tabindex="1" autocomplete="on"/>
											<div class="clear"></div>

											<div class="input_label user_verify"><label><g:message code="springSecurity.login.password.label"/></label></div>
											<input name="j_password" type="password" id="password" class="email" size="30" value="" tabindex="2" />
											<div class="clear"></div>
											
									</fieldset>			
											
									<g:if test='${flash.message}'>
										<div class="errorLoginMessage">${flash.message}</div>
									</g:if>
				
									<section class="form_row">
										<div>
											<div class="block_content textcenter">
												<input class="button" type='submit' id="submit" value='${message(code: "springSecurity.login.button")}' tabindex="3"/>
											</div>
										</div>
										<div class="clear"></div>
										<div class="leftAligned">
											<input class="smaller green button textcenter pointeredMouse" type="button" id="forgot" value='${message(code: "springSecurity.forgot.pwd")}' tabindex="4"
												onclick="runThis()" />
										</div>
									</section>
								</form>
							
							</div>
						</div>
						
						<div class="grid_1 alpha" >
							<div class="clear"></div>
						</div>
						
						<div class="grid_4 omega">
							<div class="title loginTitle textcenter"><g:message code="login.option.title2"/></div>
							<div class="textcenter" style="margin-top: 100px;">
								<sec:ifNotGranted roles="ROLE_FACEBOOK">
									<fb:serverSideConnect parent="false" />
<%--									<facebookAuth:connect permissions="${['email', 'user_about_me', 'publish_stream', 'user_photos']}" />--%>
<%--									<fb:connect permissions="${['email', 'user_about_me', 'publish_stream']}" />--%>
								</sec:ifNotGranted>
							</div>						
						</div>
					</div>
					
				</section> <!-- End section .container layoutLogin -->
				
		</body>

</html>
