<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />
		
		<title>Click &amp; Golf | <g:message code="user.titulo.label"/></title>
		
		<r:require module="core"/>
		
		<r:script disposition="head">
			$(document).ready(function() {
			    setupGridAjaxUsuario();
			});
			
			// Turn all sorting and paging links into ajax requests for the grid
			function setupGridAjaxUsuario() {
				$("#gridUsuario").find("th.sortable a").live('click', function(event) {
				event.preventDefault();
				var url = $(this).attr('href');
				
				var closestDiv = $(this).closest('div');
				
				$(closestDiv).html($("#spinner").html());
				
				$.ajax({
				type: 'POST',
				url: url,
				success: function(data) {
				$(closestDiv).fadeOut('fast', function() {$(this).html(data).fadeIn('slow');});
				}
				})
				});			
			

				$("#pagination").find("a").live('click', function(event) {
				event.preventDefault();
				var url = $(this).attr('href');
				
				var closestDiv = $(this).closest('div');
				
				$(closestDiv).html($("#spinner").html());
				
				$.ajax({
				type: 'POST',
				url: url,
				success: function(data) {
				$(closestDiv).fadeOut('fast', function() {$(this).html(data).fadeIn('slow');});
				}
				})
				});			
			}
			
		</r:script>
		
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
                  	url:"${g.createLink(controller:'usuario',action:'confirmarpassword')}",
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
				<div class="title"><g:message code="user.titulo.label" default="Perfil" /></div>
				<div class="slogan"><g:message code="user.titulo.under.label" default="Detalles del usuario" /></div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorMessage">${flash.message}</div>
			</g:if>
					
			<div class="grid_12">
			
				<ul class="tabs">
					 <li><a href="#tab1"><g:message code="user.titulo.under.label" default="Detalles del usuario" /></a></li>
					 <li><a href="#tab2"><g:message code="user.tab.greenfee.reservados.label" default="Green Fees reservados" /></a></li>
					 <sec:ifNotGranted roles="ROLE_FACEBOOK">
						<%-- Los usuarios no pueden cambiar su pwd ... --%>
						<li><a href="#tab3"><g:message code="campos.dashboard.menu.cambiar.password"  /></a></li>
					 </sec:ifNotGranted>
				</ul>
				
				<div class="tab_container">
					 <div id="tab1" class="tab_content">
						
						<section class="grid_6">
							<div class="clear"></div>
			
							<g:form class="form_place" id='modificarUsuarioForm' name="modificarUsuarioForm" url="[controller:'usuario', action:'modificar']">
								
								<section class="form_row">
									<div>
										<label><g:message code="user.email.label" default="Email" />:</label>
										<div class="block_content">
											<input onclick="this.select()" readonly="readonly" type="text" name="email" value="${fieldValue(bean: usuario, field: "email")}" class="i-format" />
										</div>
									</div>
									<div class="clear"></div>
								</section>
								<section class="form_row">
									<div>
										<label><g:message code="user.nombre.label" default="Nombre" />:</label>
										<div class="block_content">
											<input type="text" name="nombre" value="${fieldValue(bean: usuario, field: "nombre")}" class="i-format" />
										</div>
									</div>
									<div class="clear"></div>
								</section>
								<section class="form_row">
									<div>
										<label><g:message code="user.apellido.label" default="Apellido" />:</label>
										<div class="block_content">
											<input type="text" name="apellidos" value="${fieldValue(bean: usuario, field: "apellidos")}" class="i-format" />
										</div>
									</div>
									<div class="clear"></div>
								</section>
								
								<hr />
								<section class="form_row">
									<div>
										<label><g:message code="user.licencia.golf.label" default="Licencia golf" />:</label>
										<div class="block_content">
											<input type="text" name="licenciaGolf" maxlength="10" value="${fieldValue(bean: usuario, field: "licenciaGolf")}" class="i-format" />
										</div>
									</div>
									<div class="clear"></div>
								</section>
								<section class="form_row">
									<div>
										<label><g:message code="user.licencia.pp.label" default="Licencia P&P" />:</label>
										<div class="block_content">
											<input type="text" name="licenciaPP" maxlength="10" value="${fieldValue(bean: usuario, field: "licenciaPP")}" class="i-format" />
										</div>
									</div>
									<div class="clear"></div>
								</section>
								<hr />
								
								<section class="form_row">
									<div>
										<label><g:message code="user.telefono.label" default="Tel&eacute;fono" />:</label>
										<div class="block_content">
											<input type="text" name="telefono" value="${fieldValue(bean: usuario, field: "telefono")}" class="i-format" />
										</div>
									</div>
									<div class="clear"></div>
								</section>
								<section class="form_row">
									<div>
										<label><g:message code="user.ciudad.label" default="Ciudad" />:</label>
										<div class="block_content">
											<input type="text" name="ciudad" value="${fieldValue(bean: usuario, field: "ciudad")}" class="i-format" />
										</div>
									</div>
									<div class="clear"></div>
								</section>
								<sec:ifAllGranted roles="ROLE_FACEBOOK">
									<section class="form_row">
										<div>
											<label><g:message code="user.facebook.profile.picture" default="Foto de perfil en Facebook" />:</label>
											<div class="block_content">
												<img src="${createLink(action: 'facebookProfilePicture')}" alt="profile"/>
											</div>
										</div>
										<div class="clear"></div>
									</section>
								</sec:ifAllGranted>							
							
								<section class="form_row">
									<div>
										<div class="block_content">
											<input class="button darkgray textcenter" type="submit" value='${message(code: "user.edit.submit.label")}' />
										</div>
									</div>
									<div class="clear"></div>
								</section>
							
							</g:form>
						</section>
						
					</div>
					<div id="tab2" class="tab_content">
					
						<section class="grid_8">
						
							<h2><g:message code="user.tab.greenfee.reservados.subtitle" default="Reservas anteriores" /></h2>
							
							<div id="gridUsuario">
			   					<g:render template="panelGreenFeesReservados" model="model" />
			  				</div>
			
						</section>
					
					</div>
					
					<div id="tab3" class="tab_content">
					
						<%-- Tab para cambiar el password --%>
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
									
									<div class="textcenter">
										<input class="button darkblue textcenter pointeredMouse" type='button' id="submit" value='${message(code: "campos.dashboard.password.modify.actualizar")}'
											onclick="runThis()"	
										/>
									</div>
								
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
						
					</div>	
  				</div>
  				
			</div>
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
