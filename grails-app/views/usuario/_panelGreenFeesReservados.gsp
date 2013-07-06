<table class="ajax">
    <thead>
        <tr>
            <g:sortableColumn property="diaHora" title="${message(code: 'com.clickandgolf.GreenFeeReservado.diaHora', default: 'Dia y Hora')}" />
            <g:sortableColumn property="fechaReserva" title="${message(code: 'com.clickandgolf.GreenFeeReservado.fechaReserva', default: 'Fecha Reserva')}" />
            <g:sortableColumn property="golfistas" title="${message(code: 'com.clickandgolf.GreenFeeReservado.golfistas', default: 'Golfistas')}" />
            <g:sortableColumn property="descuento" title="${message(code: 'com.clickandgolf.GreenFeeReservado.descuento', default: 'Descuento')}" />
            <th>
            	<g:message code="com.clickandgolf.GreenFeeReservado.campo" default="Campo"/>
            </th>
       </tr>
    </thead>
    <tbody>
    <g:each in="${greenFeesReservados}" status="i" var="greenFeeReservado">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td class="greenFeeAdminFecha"><joda:format value="${greenFeeReservado.diaHora}" /></td>
            <td class="greenFeeAdminFecha"><joda:format value="${greenFeeReservado.fechaReserva}" /></td>
            <td>${fieldValue(bean: greenFeeReservado, field: "golfistas")}</td>
            <td>${fieldValue(bean: greenFeeReservado, field: "descuento")} &#37;</td>
            <td>${fieldValue(bean: greenFeeReservado, field: "campo.nombre")}</td>
         </tr>
    </g:each>
    </tbody>
</table>

<div class="clear"></div> 
<section id="pagination"> 
    <g:paginate total="${totalGreenFeesReservados}" />
</section>