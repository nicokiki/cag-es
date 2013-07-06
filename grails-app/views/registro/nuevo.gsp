<!--[if IE]><!DOCTYPE html><![endif]-->
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />

		<r:require module="core"/>

		<r:layoutResources />		

								<g:javascript>
									$('#email').focus();
									
									function runThis() {
										jQuery.ajax({
		                              			type:'POST',
		                              			dataType: 'json',
				                              	data:$('#ajaxRegisterForm').serialize(),        
				                              	url:"${g.createLink(controller:'registro',action:'registrar')}",
				                              	beforeSend:function() {
				                              		showSpinner();
				                              		jQuery('#registerUpdate').html('');
				                              		jQuery('#registerUpdate').hide();
				                              	},
				                              	complete:function() {
				                              		hideSpinner();
				                              	},
				                              	success:function(data,textStatus){
				                              		if (data.status == 'ERROR') {
				                              			jQuery('#registerUpdate').show();
				                              			$.each( data.errors, function(i, n){
												  			jQuery('#registerUpdate').append(n);
												  			jQuery('#registerUpdate').append("<div class='clear'></div>");
														});
				                              		}
				                              		else {
				                              			jQuery('#successUpdate').show();
				                              			jQuery('#successUpdate').append("<g:message code='register.new.ok.message'/>");
				                              			jQuery('#successUpdate').append("<div class='clear'></div>");
				                              			jQuery('#successUpdate').append("<g:message code='register.new.ok.message.2'/>");
				                              			setTimeout(function(){
				                              				window.parent.jQuery.lightbox().close();
				                              			}, 5500);
				                              		}
				                              	},
				                              	error:function(XMLHttpRequest,textStatus,errorThrown){
				                              		jQuery('#registerUpdate').show();
				                              		jQuery('#registerUpdate').html(textStatus);
				                              	}
				                 				})									
									
									
									}
									
									$('#verifyConfirm').keypress(function(e) {
							      			if(e.which == 13) {
							           		jQuery(this).blur();
							           		jQuery('#submit').focus().click();
							       		}
							   		});
									
									function hideShowButtons(val) {
										if (val == false) {
											$('#submit').hide();
											$('#submitNo').show();
											$("input[name=fb_name]").hide();
											$('#fbNo').show();
										}
										else {
											$('#submit').show();
											$('#submitNo').hide();
											$("input[name=fb_name]").show();
											$('#fbNo').hide();
										}
									}
								</g:javascript>


	</head>

	<body class="bodyRegistro">
		<div id="spinner" class="spinner" style="display: none;">
			<img src="${resource(dir:'images',file:'spinner.gif')}"
				alt="Spinner" />
		</div>
		
		<section id="wrapRegistro">
			<section class="wrap_blockRegistro">
		
				<section class="containerRegistro layoutLogin">
				
					<section class="grid_7">
				
						<div id="contactRegistro">
						
							<div class="title loginTitle textcenter"><g:message code="register.option.title1"/></div>
							
							<div class="alert error_msg" id='registerUpdate' style="display: none;"></div>
							<div class="alert succes_msg" id='successUpdate' style="display: none;"></div>
						
							<g:form class="form_place" method="POST" id='ajaxRegisterForm' name="ajaxRegisterForm" url="[controller:'registro', action:'registrar']">
								<fieldset class="fieldsetRegistro"> 
									<div class="clear"></div>
									
										<div class="input_label user_email"><label><g:message code="springSecurity.login.username.label"/></label></div>
										<input name="email" type="text" id="email" class="email" size="30" />
										<div class="clear"></div>
		
										<div class="div_verify">
											<div class="input_label user_verify"><label for="verify" accesskey="V"><g:message code="springSecurity.login.password.label"/></label></div>
											<input name="password" type="password" id="verify" size="30" value="" class="pwd"  />
										</div>
										<div class="clear"></div>
		
										<div class="div_verify">
											<div class="input_label user_verify"><label for="verify" accesskey="V"><g:message code="springSecurity.login.password.label"/></label></div>
											<input name="passwordConfirm" type="password" id="verifyConfirm" size="30" value="" class="pwd"  />
										</div>
								</fieldset>			
								
								<div class="clear"></div>
								
								<div class="textcenter">
									<input class="button textcenter pointeredMouse" type='button' id="submit" value='${message(code: "springSecurity.register.button")}'
										onclick="runThis()"	
									/>
									<input class="button textcenter " style="display:none;" type='button' id="submitNo" value='${message(code: "register.acepta")}' />
								</div>
							</g:form>
						</div> <!--  contact -->
		
						<hr />
						
						<div class="title loginTitle textcenter"><g:message code="register.option.title2"/></div>
							
							<div class="textcenter" style="margin-top: 10px;">
								<sec:ifNotGranted roles="ROLE_FACEBOOK">
									<fb:serverSideConnect parent="true" register="true" />
<%--									<fb:connect permissions="${['email', 'user_about_me', 'publish_stream', 'user_photos']}" parent="true" text="${message(code: "register.facebook")}" />--%>

									<input class="button darkblue textcenter " style="display:none;" type='button' id="fbNo" value='${message(code: "register.acepta")}' />
					
								</sec:ifNotGranted>
							</div>
		
							<section class="form_row margin_top_0">
								<div>
									<div class="block_content label400" >
										<input type="checkbox" id="acepta" name="acepta" onclick="hideShowButtons(this.checked)" /> <label style="font-size: smaller;"><g:message code="greenfee.book.success.condiciones.acepta" />&nbsp;<a href="${createLink(controller:'info', action: 'condiciones', absolute: 'true')}" target="_blank"><g:message code="greenfee.book.success.condiciones.clickandgolf" /></a>&nbsp;<g:message code="greenfee.book.success.condiciones.acepta.y" />&nbsp;<a href="${createLink(controller:'info', action: 'politicaDePrivacidad', absolute: 'true')}" target="_blank"><g:message code="footer.privacy" /></a></label>
										<g:javascript>
											$('#acepta').prop('checked', true);
										</g:javascript>
									</div>
								</div>
								<div class="clear"></div>
							</section>
		
					</section>
				</section> <!-- End section .containerRegistro layout -->
				

			</section>
			<!-- End section .wrap_blockRegistro -->
		</section>
		<!-- End section #wrapRegistro -->			
		
		<r:layoutResources />		
	</body>

</html>
