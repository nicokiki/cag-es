<%--		
	No es una pagina normal entera a pesar de q se abre con lightbox evolution.
	El tema es q caso contrario IE se vuelve loco al NO ser un iframe, lo cual no puede
	pasar por un tema de seguridad con Paypal
--%>
		
		<section id="wrapReserva">
			<section class="wrap_blockReserva">
			
				<section class="containerReserva layoutLogin">
				
					<g:if test='${flash.message}'>
						<div class="clear"></div>
						<div class="alert error_msg">${flash.message}</div>
						<div class="clear"></div>
						
						<div class="centerAligned">
							<input 	class="button textcenter pointeredMouse" 
									type="button" 
									onclick="window.parent.jQuery.lightbox().close();" 
									value='${message(code: "default.button.cerrar.label")}' />
						</div>
					</g:if>
					<g:else>
					
						<div id="grid_9_id">
						
							<section class="grid_9">
							
								<div class="adress_info greenFeeReservadoVer" style="min-width: 500px;">
									<h4><g:message code="greenfee.book.label"/></h4>
									<div class="clear"></div>
									
									<ul class="list16 greenFeeReservadoVer_ul" style="margin-top:20px;">
										<li>
											<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.campo"/>:</span>
											&nbsp;${fieldValue(bean: greenFee, field: "campo.nombre")}
										</li>
									</ul> 
									<ul class="list19 greenFeeReservadoVer_ul">
										<li>
											<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.dia"/>:</span>
											&nbsp;${fieldValue(bean: greenFee, field: "dia")}
										</li>
									</ul> 
									<ul class="list19 greenFeeReservadoVer_ul">
										<li>
											<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.hora"/>:</span>
											&nbsp;${fieldValue(bean: greenFee, field: "horaMinuto")}
										</li>
									</ul> 
									<ul class="list9 greenFeeReservadoVer_ul">
										<li>
											<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.found.hoyos"/>:</span>
											&nbsp;${fieldValue(bean: greenFee, field: "hoyosTexto")}
										</li>
									</ul> 
									<ul class="list18 greenFeeReservadoVer_ul">
										<li>
											<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.precio.individual"/>:</span>
											&nbsp;<g:formatNumber number="${greenFee.precio}" format="#0.00" /> &euro;
										</li>
									</ul> 
									<ul class="list9 greenFeeReservadoVer_ul">
										<li>
											<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.golfistas"/>:</span>
											&nbsp;${golfistas}
										</li>
									</ul> 
		
									<ul class="list18 greenFeeReservadoVer_ul">
										<li>
											<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.fee"/>:</span>
											&nbsp;<g:formatNumber number="${feeToPayUpFront}" format="#0.00" /> &euro;
										</li>
									</ul> 
									<ul class="list18 greenFeeReservadoVer_ul">
										<li>
											<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.pagar.en.campo"/>:</span>
											&nbsp;<g:formatNumber number="${feeToPayAtCourse}" format="#0.00" /> &euro;
										</li>
									</ul> 
									
								</div>
								
								<g:if test='${warningMessage}'>
									<div class="alert exclamation_msg">
										${warningMessage}
									</div>
								</g:if>
								
								<g:if test="${greenFee.campo.requiereLicencia}">
									<div class="adress_info greenFeeReservadoVer" style="min-width: 500px; min-height: 50px;">
										<div class="grid_4">
											<span style="font-size: smaller;"><g:message code="greenfee.book.licencia" />&nbsp;1:</span>&nbsp;
											<input style="padding: 2px; width: 100px; font-size: smaller;" name="licencia1" type="text" placeholder="${message(code:'greenfee.book.licencia.ingrese')}" class="email" id="licencia1" value="${licencia}" size="30" tabindex="1" />
											<div class="clear"></div>
											<g:if test="${golfistas > 1}">
												<span style="font-size: smaller;"><g:message code="greenfee.book.licencia" />&nbsp;2:</span>&nbsp;
												<input style="padding: 2px; width: 100px; font-size: smaller;" name="licencia2" type="text" placeholder="${message(code:'greenfee.book.licencia.ingrese')}" class="email" id="licencia2" size="30" tabindex="1" />
												<div class="clear"></div>
											</g:if>
										</div>
										<div class="grid_4 push_4">
											<g:if test="${golfistas > 2}">
												<span style="font-size: smaller;"><g:message code="greenfee.book.licencia" />&nbsp;3:</span>&nbsp;
												<input style="padding: 2px; width: 100px; font-size: smaller;" name="licencia3" type="text" placeholder="${message(code:'greenfee.book.licencia.ingrese')}" class="email" id="licencia3" size="30" tabindex="3" />
												<div class="clear"></div>
											</g:if>
											<g:if test="${golfistas > 3}">
												<span style="font-size: smaller;"><g:message code="greenfee.book.licencia" />&nbsp;4:</span>&nbsp;
												<input style="padding: 2px; width: 100px; font-size: smaller;" name="licencia4" type="text" placeholder="${message(code:'greenfee.book.licencia.ingrese')}" class="email" id="licencia4" size="30" tabindex="4" />
												<div class="clear"></div>
											</g:if>
										</div>
									</div>
									<div class="clear"></div>
								</g:if>

								<div class="block_content label400">
									<input type="checkbox" name="acepta" checked="checked" onclick="hideShowPaypal(this.checked)" /> <label><g:message code="greenfee.book.success.condiciones.acepta" />&nbsp;<a href="${createLink(controller:'info', action: 'condiciones', absolute: 'true')}" target="_blank"><g:message code="greenfee.book.success.condiciones.clickandgolf" /></a>&nbsp;<g:message code="greenfee.book.success.condiciones.acepta.y" />&nbsp;<a href="${createLink(controller:'info', action: 'condiciones', absolute: 'true', fragment: 'cancel')}" target="_blank"><g:message code="greenfee.book.success.condiciones.cancelacion" /></a></label><br />
								</div>

								<div class="centerAligned" id="buttonHolderId">
									<golf:paypal
										itemName="${itemDescription}"
										itemNumber="${greenFee.id}"
										amount="${feeToPayUpFront}"
						        		discountAmount="0"
										buyerId="${user.id}"
										currency="EUR"
										buttonSrc="${buttonSrc}"
										params="[lc:"${lc}",golfistas:"${golfistas}",langSaved:"${langSaved}"]"
										returnAction="exito"
										returnController="reserva"
										cancelAction="cancelacion"
										cancelController="reserva"
									/>
								</div>
								
							</section>
						</div>	
						
						<div style="display: none;" id="grid_9_hidden">
							<section class="grid_8"  >
								<div class="adress_info greenFeeReservadoVer" style="min-width: 400px;">
									<p class="justifyAligned">
										<g:message code="paypal.info.message1"/>
									</p>	
										<div class="clear"></div>
									<p  class="justifyAligned">	
										<g:message code="paypal.info.message2"/>
									</p>
									<div class="clear"></div>
								</div>
							</section>
						</div>
						
					</g:else>
					
				</section> <!-- End section .containerReserva layout -->
				
			</section>
			<!-- End section .wrap_blockReserva -->
		</section>
		
		<script type="text/javascript">

			function showMessage() {
				$('#grid_9_id').slideUp("slow")
				$('#grid_9_hidden').slideDown("");


				var lic1 = $('#licencia1').val();
				var lic2 = $('#licencia2').val();
				var lic3 = $('#licencia3').val();
				var lic4 = $('#licencia4').val();
				
				$('#paypalFormId').append('<input type="hidden" name="licencia1" value="' + lic1 + '" />');
				$('#paypalFormId').append('<input type="hidden" name="licencia2" value="' + lic2 + '" />');
				$('#paypalFormId').append('<input type="hidden" name="licencia3" value="' + lic3 + '" />');
				$('#paypalFormId').append('<input type="hidden" name="licencia4" value="' + lic4 + '" />');

				return true;
       		}

			function hideShowPaypal(val) {
				if (val == false) {
					$('#buttonHolderId').hide();
				}
				else {
					$('#buttonHolderId').show();
				}
			}

		</script>

		