<table class="ajax">
    <thead>
        <tr>
            <g:sortableColumn property="diaHora" title="${message(code: 'com.clickandgolf.GreenFeeReservado.diaHora', default: 'Dia y Hora')}" />
            <g:sortableColumn property="golfistas" title="${message(code: 'com.clickandgolf.GreenFeeReservado.golfistas', default: 'Golfistas')}" />
            <g:sortableColumn property="fechaReserva" title="${message(code: 'com.clickandgolf.GreenFeeReservado.fechaReserva', default: 'Fecha Reserva')}" />
            <g:sortableColumn property="descuento" title="${message(code: 'com.clickandgolf.GreenFeeReservado.descuento', default: 'Descuento')}" />
            <th>
            	<g:message code="com.clickandgolf.GreenFeeReservado.usuario.email" default="Email golfista"/>
            </th>
       </tr>
    </thead>
    <tbody>
    <g:each in="${greenFeesAnteriores}" status="i" var="greenFeeReservado">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td class="greenFeeAdminFecha"><joda:format value="${greenFeeReservado.diaHora}" /></td>
            <td>${fieldValue(bean: greenFeeReservado, field: "golfistas")}</td>
            <td class="greenFeeAdminFecha"><joda:format value="${greenFeeReservado.fechaReserva}" /></td>
            <td>${fieldValue(bean: greenFeeReservado, field: "descuento")}</td>
            <td>${fieldValue(bean: greenFeeReservado, field: "usuario.email")}</td>
         </tr>
    </g:each>
    </tbody>
</table>

<div class="clear"></div> 
<section id="pagination"> 
    <g:paginate total="${totalGreenFeesAnteriores}" />
</section>