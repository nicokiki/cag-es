<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />	
		
		<r:require module="core"/>

		<g:javascript>
			<g:if test="${promocionesGoldSize > 0}">
				/* Esto se ejecuta al final entonces se ejecutara si o si */ 
				$('.box_skitter_slider2').skitter({dots: true});
			</g:if>
		
			
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

			<div class="grid_12 video_home">
				<div class="grid_6 alpha margin_top_0">
					<h1 class="margin_top_0" style="margin-bottom: 0 !important; "><g:message code="home.titulo.label"/></h1>
					<h2 class="margin_top_0"><g:message code="home.subtitulo.label"/></h2>
					<div class="clear"></div>
                          
                   	<g:form class="form_place" method="POST" id='buscarGreenFeeForm' name="buscarGreenFeeForm" url="[controller:'greenFee', action:'busquedaDeCampos']">
						<div class="clear"></div>

						<%-- Primer fila --%>
						<div class="grid_4 form_row" >
                           	<div>
                               	<label><g:message code="home.lugar.label"/>:</label>
                                   <div class="block_content" >
									<golf:selectUbicaciones name="ubicacion" value="" dataList="${ubicaciones}" key="true" cssClass="chzn-select-deselect" style="width:100%;" placeHolderValue="${message(code: 'campo.edit.ubicacion', default: 'Elige una ubicacion')}" todosLabel="${message(code: 'home.ubicacion.todo.label', default: 'Todo')}" />
								</div>
                           	</div>
                          	</div>
                          	<div class="clear"></div>
                          	<%-- 2da fila --%>
                          	<div class="grid_2 alpha form_row" >
                           	<div>
                               	<label><g:message code="home.fecha.label"/>:</label>
                                   <div class="block_content" style="width: 110px;">
                                   	<input type="text" id="fecha" name="fecha" class="i-format" placeholder="dd/mm/yyyy" style="width: 70px;" />
                                   </div>
                               </div>
                              </div>
                              <div class="grid_2 omega"  >
							<div style="vertical-align: bottom; vertical-text: bottom; float: right; line-height: 82px;">
								<input style="margin-right: 0px !important;" class="button green embossed bigger" type="submit" id="submit" value='${message(code: "home.buscar.label")}' />
							</div>
						</div>
						<div class="clear"></div>
						<%-- ultima fila --%>
						<div class="grid_4 form_row" >
							<g:link action="busquedaDeCamposAvanzada" controller="greenFee" class="button small"><g:message code="home.busqueda.avanzada.label"/></g:link>
						</div>
						
					</g:form>	
					
				</div>
				
				<div class="grid_6 omega" style="margin-left: 0px;">

					<div class="box_skitter box_skitter_slider2">
						<ul>
							<g:each in="${promocionesGold}" status="i" var="promocion">
								<li>
									<g:link mapping="campo" params="[id:"${fieldValue(bean: promocion.campo, field: "id")}", nombre:"${fieldValue(bean: promocion.campo, field: "hyphenatedNombre")}"]"><img class="block" src="${wthr.staticResource(id:"${promocion.campo.id}",nombre:"${promocion.campo.imagenPromocionGold}")}" alt="${promocion.campo.imagenPromocionGold}" title="${promocion.campo.imagenPromocionGold}" /></g:link>
<%--									<a target="_blank" href="${createLink(mapping: 'campo', absolute: 'true', params:[id:"${promocion.campo.id}", nombre: "${promocion.campo.hyphenatedNombre}"])}"  >--%>
<%--										<img class="block" src="${wthr.staticResource(id:"${promocion.campo.id}",nombre:"${promocion.campo.imagenPromocionGold}")}" alt="${promocion.campo.imagenPromocionGold}" title="${promocion.campo.imagenPromocionGold}" />										--%>
<%--									</a>--%>
<%--								<img class="block" src="${wthr.staticResource(id:"${promocion.campo.id}",nombre:"${promocion.campo.imagenPromocionGold}")}" alt="${promocion.campo.imagenPromocionGold}" title="${promocion.campo.imagenPromocionGold}" />--%>
									<div class="label_text">
										<div class="caption" style="min-width: 408px;">
											<h4>${fieldValue(bean: promocion, field: "campo.nombre")}</h4>
											<p style="text-align: justify;">${promocion["${textoProperty}"]}</p>
										</div>
									</div>
								</li>
						    </g:each>
						</ul>
					</div><!-- End of .box_skitter-->
				</div>
				
				<div class="clear"></div>

			</div>
		
			<!-- *** Home Portfolio *** -->
			<h2 class="textcenter"> </h2>
			<section class="home_portfolio">
				<div class="in">
					<div class="inner">
						<div class="content">
							<div id="home_pf_items" class="items">
							
								<g:each in="${promocionesSilver}" status="i" var="promocion">
									<div class="grid_3">
										<div class="img_pf_hover">
<%--											<img class="img212x142" src="${createLink(controller: 'imagenCampo', action: 'imagen', id: "${promocion.campo.id}", params:[nombre: "${promocion.campo.imagenPromocionSilver}"])}" alt="${promocion.campo.imagenPromocionSilver}" title="${promocion.campo.imagenPromocionSilver}" />--%>
											<img class="img212x142" src="${wthr.staticResource(id:"${promocion.campo.id}",nombre:"${promocion.campo.imagenPromocionSilver}")}" alt="${promocion.campo.imagenPromocionSilver}" title="${promocion.campo.imagenPromocionSilver}" />
<%--											<a 	id="a_campo_${promocion.campo.id}"--%>
<%--												href="${createLink(controller: 'imagenCampo', action: 'imagen', id: "${promocion.campo.id}", params:[nombre: "${promocion.campo.imagenSecundaria}"])}" >--%>
											<a 	id="a_campo_${promocion.campo.id}"
												href="${wthr.staticResource(id:"${promocion.campo.id}",nombre:"${promocion.campo.imagenSecundaria}")}" >
												<span class="img_pf_icon zoom_in"></span>
											</a>
											
											<g:javascript>
											 	$('#a_campo_${promocion.campo.id}').lightbox({
               										title:'<g:link mapping="campo" params="[id:"${fieldValue(bean: promocion.campo, field: "id")}", nombre:"${fieldValue(bean: promocion.campo, field: "hyphenatedNombre")}"]"><h4 class="h4Center">${promocion.campo.nombre}</h4></g:link><div class="clear"></div><p class="pTextGold">${promocion["${textoProperty}"]}</p>',
												});
											</g:javascript>
											
										</div> 
									</div>
							    </g:each>							
							
								<div class="clear"></div>
							</div>  
						</div>  
					</div>
				</div>
			</section>
			
			<div class="clear"></div>
			<!-- *** Home Portfolio END *** -->
				
					
		</section> <!-- End section .container layout -->
		
	</body>

</html>
