<table class="ajax">
    <thead>
        <tr>
            <g:sortableColumn property="diaHora" title="${message(code: 'com.clickandgolf.GreenFeeReservado.diaHora', default: 'Dia y Hora')}" />
            <g:sortableColumn property="golfistas" title="${message(code: 'com.clickandgolf.GreenFeeReservado.golfistas', default: 'Golfistas')}" />
            <g:sortableColumn property="precioPorPagar" title="${message(code: 'com.clickandgolf.GreenFeeReservado.precioPorPagar', default: 'A pagar')}" />
            <g:sortableColumn property="fechaReserva" title="${message(code: 'com.clickandgolf.GreenFeeReservado.fechaReserva', default: 'Fecha Reserva')}" />
       </tr>
    </thead>
    <tbody>
    <g:each in="${greenFeesReservados}" status="i" var="greenFeeReservado">
    
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	    	<g:if test="${greenFeeReservado?.isComplete()}">
	            <td title="${message(code: 'com.clickandgolf.GreenFeeReservado.complete.title')}" class="italicText"><joda:format value="${greenFeeReservado.diaHora}" /></td>
    		</g:if>
	    	<g:else>
	            <td><g:link action="ver" id="${greenFeeReservado.id}"><joda:format value="${greenFeeReservado.diaHora}" /></g:link></td>
	    	</g:else>
            <td>${fieldValue(bean: greenFeeReservado, field: "golfistas")}</td>
            <td><g:formatNumber number="${greenFeeReservado.precioPorPagar}" format="#0.00" /> &euro;</td>
            <td><joda:format value="${greenFeeReservado.fechaReserva}" /></td>
         </tr>
    </g:each>
    </tbody>
</table>

<div class="clear"></div> 
<section id="pagination"> 
    <g:paginate total="${totalGreenFeesReservados}" />
</section>