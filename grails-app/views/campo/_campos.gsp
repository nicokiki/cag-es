<g:each in="${campos}" status="i" var="campo">
	<article class="style1">
		<div class="grid_3 img_pf_hover alpha">
			<g:link mapping="campo" params="[id:"${campo.id}", nombre:"${campo.hyphenatedNombre}"]">
<%--				<img class="img212x142" src="${createLink(controller: 'imagenCampo', action: 'imagen', id: "${campo.id}", params:[nombre: "${campo.imagenSecundaria}"])}" alt="${campo.imagenSecundaria}" title="${campo.imagenSecundaria}" />--%>
				<img class="img212x142" src="${wthr.staticResource(id:"${campo.id}",nombre:"${campo.imagenSecundaria}")}" alt="${campo.imagenSecundaria}" title="${campo.imagenSecundaria}" />
			</g:link>
        </div>
		<!-- Article thumbnail end -->
		
    		<div class="grid_9 alpha">
    			<div class="title"><g:link mapping="campo" params="[id:"${campo.id}", nombre:"${campo.hyphenatedNombre}"]"><span class="titledName">${campo.nombre}</span></g:link></div>
    			<div class="clear"></div>
    			<div class="meta_data">${campo.ubicacion.ciudad}, ${campo.ubicacion.provincia}, ${campo.ubicacion.region}.&nbsp;<a href="${campo.linkCampo}" target="_blank"><g:message code="campos.todos.website.official" default="Sitio Web del campo"/></a></div>
    			<div class="clear"></div>
    			
    			<p>${campo.trimmedDescription}</p>
    			<p></p>
    			<div class="clear"></div><!-- Article description end -->

                <div class="rightAligned">
                    <g:link class="button blue large" mapping="greenfeeCampo" params="[idCampo:"${campo.id}", nombreCampo:"${campo.hyphenatedNombre}", dia:"${fecha.dia}", mes:"${fecha.mes}", anio:"${fecha.anio}"]">
                        <g:message code="com.clickandgolf.Campo.greenfees" />
                    </g:link>
                </div>
    		</div>
        
		<div class="clear"></div>
	</article>
</g:each>

<div class="clear"></div> 
<section id="pagination"> 
    <g:paginate total="${totalCampos}" />
</section>