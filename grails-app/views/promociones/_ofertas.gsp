<%-- Los campos recibidos son del tipo CampoView q esta definido en PromocionesController
	 No hay paginacion xq siempre se mostraran solo los primeros 10 y no el resto --%>
<g:each in="${campos}" status="i" var="campo">
	<article class="style1">
		<div class="grid_3 img_pf_hover alpha">
			<g:link mapping="campo" params="[id:"${campo.idCampo}", nombre:"${campo.hyphenatedNombre}"]">
				<img class="img212x142" src="${wthr.staticResource(id:"${campo.idCampo}",nombre:"${campo.imagenSecundaria}")}" alt="${campo.imagenSecundaria}" title="${campo.imagenSecundaria}" />
			</g:link></div>
			<!-- Article thumbnail end -->
		
		<div class="grid_6 omega">
<%--			<div class="title"><h2><g:link mapping="campo" params="[id:"${campo.idCampo}", nombre:"${campo.hyphenatedNombre}"]"><span class="titledName">${campo.nombreCampo}</span></g:link></h2></div>--%>
			<div class="title loginTitle"><g:link mapping="campo" params="[id:"${campo.idCampo}", nombre:"${campo.hyphenatedNombre}"]"><span class="titledName">${campo.nombreCampo}</span></g:link></div>
			<div class="clear"></div>
			<div>
				<p>
					<g:message code="ofertas.consigue" args="["${campo.descuento}"]" />&nbsp;
					<g:if test="${campo.descuento > 0 && campo.descuento <= 15}">
						<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
					</g:if>
					<g:elseif test="${campo.descuento > 15 && campo.descuento < 40}">
						<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
						<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
					</g:elseif>
					<g:elseif test="${campo.descuento >= 40}">
						<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
						<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
						<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
					</g:elseif>
				</p>
			 </div>
			<div class="clear"></div>
				
				<g:link class="button blue bigger" mapping="greenfeeCampo" params="[idCampo:"${campo.idCampo}", nombreCampo:"${campo.hyphenatedNombre}", dia:"${fecha.dia}", mes:"${fecha.mes}", anio:"${fecha.anio}"]">
					<g:message code="com.clickandgolf.Campo.greenfees" />
				</g:link>
				
			<div class="clear"></div><!-- Article description end -->
		</div>
		<div class="clear"></div>
	</article>
</g:each>
