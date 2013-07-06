<table class="ajax">
    <thead>
        <tr>
            <th class="textcenter">
            	<g:message code="search.campo.label" default="Campo"/>
            </th>
            <th class="textcenter">
            	<g:message code="search.ciudad.label" default="Ciudad"/>
            </th>
            <th class="textcenter">
            	<g:message code="search.provincia.label" default="Provincia"/>
            </th>
            <th class="textcenter">
            	07:00 - 10:00
            </th>
            <th class="textcenter">
            	10:00 - 13:00
            </th>
            <th class="textcenter">
            	13:00 - 16:00
            </th>
            <th class="textcenter">
            	16:00 - 19:00
            </th>
       </tr>
    </thead>
    <tbody>
    <g:each in="${greenFeesInfo}" status="i" var="greenFee">
    	<g:if test="${i != 0 && (i % 10) == 0}">
	        <tr>
	            <th class="textcenter">
	            	<g:message code="search.campo.label" default="Campo"/>
	            </th>
	            <th class="textcenter">
	            	<g:message code="search.ciudad.label" default="Ciudad"/>
	            </th>
	            <th class="textcenter">
	            	<g:message code="search.provincia.label" default="Provincia"/>
	            </th>
	            <th class="textcenter">
	            	07:00 - 10:00
	            </th>
	            <th class="textcenter">
	            	10:00 - 13:00
	            </th>
	            <th class="textcenter">
	            	13:00 - 16:00
	            </th>
	            <th class="textcenter">
	            	16:00 - 19:00
	            </th>
	       	</tr>
    	</g:if>
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        	<td><g:link mapping="campo" params="[id:"${fieldValue(bean: greenFee, field: "campo.id")}", nombre:"${fieldValue(bean: greenFee, field: "campo.hyphenatedNombre")}"]">${fieldValue(bean: greenFee, field: "campo.nombre")}</g:link> </td>
            <td>${fieldValue(bean: greenFee, field: "campo.ubicacion.ciudad")}</td>
            <td>${fieldValue(bean: greenFee, field: "campo.ubicacion.provincia")}</td>
            <td class="textcenter">
            	<g:if test="${greenFee.precioRangoCero}">
					<g:link mapping="greenfeeCampo" params="[idCampo:"${fieldValue(bean: greenFee, field: "campo.id")}", nombreCampo:"${fieldValue(bean: greenFee, field: "campo.hyphenatedNombre")}", dia:"${fieldValue(bean: greenFee, field: "dia")}", mes:"${fieldValue(bean: greenFee, field: "mes")}", anio:"${fieldValue(bean: greenFee, field: "anio")}"]">
						<g:message code="search.desde.label" default="desde"/>&nbsp;<g:formatNumber number="${greenFee.precioRangoCero}" format="#0.00" /> &euro;
					</g:link>
            	</g:if>
            </td>
            <td class="textcenter">
            	<g:if test="${greenFee.precioRangoUno}">
					<g:link mapping="greenfeeCampo" params="[idCampo:"${fieldValue(bean: greenFee, field: "campo.id")}", nombreCampo:"${fieldValue(bean: greenFee, field: "campo.hyphenatedNombre")}", dia:"${fieldValue(bean: greenFee, field: "dia")}", mes:"${fieldValue(bean: greenFee, field: "mes")}", anio:"${fieldValue(bean: greenFee, field: "anio")}"]">
			            <g:message code="search.desde.label" default="desde"/>&nbsp;<g:formatNumber number="${greenFee.precioRangoUno}" format="#0.00" /> &euro;
					</g:link>
            	</g:if>
            </td>
            <td class="textcenter">
            	<g:if test="${greenFee.precioRangoDos}">
					<g:link mapping="greenfeeCampo" params="[idCampo:"${fieldValue(bean: greenFee, field: "campo.id")}", nombreCampo:"${fieldValue(bean: greenFee, field: "campo.hyphenatedNombre")}", dia:"${fieldValue(bean: greenFee, field: "dia")}", mes:"${fieldValue(bean: greenFee, field: "mes")}", anio:"${fieldValue(bean: greenFee, field: "anio")}"]">
			            <g:message code="search.desde.label" default="desde"/>&nbsp;<g:formatNumber number="${greenFee.precioRangoDos}" format="#0.00" /> &euro;
					</g:link>
            	</g:if>
            </td>
            <td class="textcenter">
            	<g:if test="${greenFee.precioRangoTres}">
					<g:link mapping="greenfeeCampo" params="[idCampo:"${fieldValue(bean: greenFee, field: "campo.id")}", nombreCampo:"${fieldValue(bean: greenFee, field: "campo.hyphenatedNombre")}", dia:"${fieldValue(bean: greenFee, field: "dia")}", mes:"${fieldValue(bean: greenFee, field: "mes")}", anio:"${fieldValue(bean: greenFee, field: "anio")}"]">
			            <g:message code="search.desde.label" default="desde"/>&nbsp;<g:formatNumber number="${greenFee.precioRangoTres}" format="#0.00" /> &euro;
					</g:link>
            	</g:if>
            </td>
         </tr>
    </g:each>
    </tbody>
</table>

<g:if test="${greenFeesInfo?.size() == 0}">
	<div class="fixed_alert tip_msg" >
		<g:message code="general.no.results" />
	</div>
</g:if>

<div class="clear"></div> 
