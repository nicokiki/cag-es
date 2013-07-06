<g:javascript>
	function submitPreparar(id) {
		var aux = "golfistas_" + id;
		var valAux = $("[name=" + aux + "]").val();
		if (valAux != "1" && valAux != "2" && valAux != "3" && valAux != "4") {
			alert('<g:message code="greenfee.found.golfistas.elija.alguno" default="Elija la cantidad de golfistas"/>');
			return;
		}
		var linkAssembled = '${createLink(controller: 'reserva' , action:'preparar')}?id=' + escape(id) + "&golfistas=" + escape($("[name=" + aux + "]").val());
		$("#link").attr('href', linkAssembled);
		/* No tiene iframe porque con Paypal no funciona */
		
		/* Simula haber hecho click en el hidden */
		$("#link").click();
	}
	
		
</g:javascript>


<table class="ajax">
    <thead>
        <tr>
            <th class="centerAligned">
            	<g:message code="greenfee.found.horario" default="Horario"/>
            </th>
            <th class="centerAligned">
            	<g:message code="greenfee.found.campo" default="Campo"/>
            </th>
            <th class="centerAligned">
            	<g:message code="greenfee.found.info" default="Informacion"/>
            </th>
            <th class="centerAligned">
            	<g:message code="greenfee.found.hoyos" default="Hoyos"/>
            </th>
            <th class="centerAligned">
            	<g:message code="greenfee.found.descuento" default="Descuento"/>
            </th>
            <th class="centerAligned">
            	<g:message code="greenfee.found.precio" default="Precio"/>
            </th>
            <th class="centerAligned">
            	<g:message code="greenfee.found.golfistas" default="Golfistas"/>
            </th>
            <th class="centerAligned">
            	<g:message code="greenfee.found.reserva" default="Reserva"/>
            </th>
       </tr>
    </thead>
    <tbody>
    <g:each in="${greenFees}" status="i" var="greenFee">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}" >
            <td class="textcenter">${fieldValue(bean: greenFee, field: "horaMinuto")}</td>
        	<td><g:link mapping="campo" params="[id:"${fieldValue(bean: campo, field: "id")}", nombre:"${fieldValue(bean: campo, field: "hyphenatedNombre")}"]">${fieldValue(bean: campo, field: "nombre")}</g:link> </td>
            <td id="a" >${fieldValue(bean: greenFee, field: "info")}</td>
            <td class="textcenter">${fieldValue(bean: greenFee, field: "hoyosTexto")}</td>
            <td class="textcenter">${fieldValue(bean: greenFee, field: "descuento")} &#37;&nbsp;  
				<g:if test="${greenFee.descuento > 0 && greenFee.descuento <= 15}">
					<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
				</g:if>
				<g:elseif test="${greenFee.descuento > 15 && greenFee.descuento < 40}">
					<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
					<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
				</g:elseif>
				<g:elseif test="${greenFee.descuento >= 40}">
					<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
					<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
					<img src="${resource(dir:'images/fugue',file:'star.png')}" alt="star" />  	
				</g:elseif>
			</td>
			<td class="textcenter"><g:formatNumber number="${greenFee.precio}" format="#0.00" /> &euro;</td>
            <td class="centerAligned">
				<%-- Por defecto lo seleccionado es lo minimo q se puede seleccionar --%>
	            <golf:select name="golfistas_${greenFee.id}" value="${greenFee.golfistasMinimo}" dataList="${greenFee.comboGolfistas()}"  cssClass="chzn-select-deselect" style="width:80px;" placeHolderValue="${message(code: 'greenfee.found.golfistas.elija', default: 'Elija')}" />
            </td>
            <td class="centerAligned middleAligned">
   				<sec:ifLoggedIn>
   					<sec:ifAnyGranted roles="ROLE_USER,ROLE_FACEBOOK">
						<input class="button blue fontWeighted pointeredMouse buttonNoMarginBottom" type="button" onclick="submitPreparar('${greenFee.id}')" value="${message(code: 'greenfee.found.reserva', default: 'Reserva')}" />
   					</sec:ifAnyGranted>
   					<sec:ifNotGranted roles="ROLE_USER,ROLE_FACEBOOK">
   						<g:message code="greenfee.found.not.golfista" default="Solo Para golfistas"/>
   					</sec:ifNotGranted>
   				</sec:ifLoggedIn>
   				<sec:ifNotLoggedIn>
   					<g:link action="panel" controller="login" class="lightboxi button grey small buttonNoMarginBottom"><g:message code="security.login.text" /></g:link>
   				</sec:ifNotLoggedIn>
            </td>
         </tr>
    </g:each>
    </tbody>
</table>

<g:if test="${greenFees?.size() == 0}">
	<div class="fixed_alert tip_msg" >
		<g:message code="general.no.results" />
	</div>
</g:if>

<div class="clear"></div> 

<a class="lightbox_book button blue" href="" style="display: none;" id="link" ></a>

<g:javascript>
		jQuery('.lightbox_book').lightbox({
 			width:580,
 			height:550,
 			modal:true
		});
		
		jQuery('.lightboxi').lightbox({
		    width:570,
		    height:390,
		    modal:true,
		    iframe: true
	    });
</g:javascript>
