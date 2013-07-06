<table class="ajax">
    <thead>
        <tr>
            <g:sortableColumn property="diaHora" title="${message(code: 'com.clickandgolf.GreenFee.diaHora', default: 'Dia y Hora')}" />
            <g:sortableColumn property="descuento" title="${message(code: 'com.clickandgolf.GreenFee.descuento', default: 'Descuento')}" />
            <g:sortableColumn property="precio" title="${message(code: 'com.clickandgolf.GreenFee.precio', default: 'Precio')}" />
            <g:sortableColumn property="golfistasMinimo" title="${message(code: 'com.clickandgolf.GreenFee.golfistasMinimo', default: 'Golfistas Mínimos')}" />
            <g:sortableColumn property="disponibles" title="${message(code: 'com.clickandgolf.GreenFee.disponibles', default: 'Disponibles')}" />
            <g:sortableColumn property="estado" title="${message(code: 'com.clickandgolf.GreenFee.estado', default: 'Estado')}" />
       </tr>
    </thead>
    <tbody>
    <g:each in="${greenFees}" status="i" var="greenFee">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link class="greenFeeAdminFecha" action="cambiar" id="${greenFee.id}"><joda:format value="${greenFee.diaHora}" /></g:link></td>
            <td>${fieldValue(bean: greenFee, field: "descuento")} &#37;</td>
            <td><g:formatNumber number="${greenFee.precio}" format="#0.00" /> &euro;</td>
            <td>${fieldValue(bean: greenFee, field: "golfistasMinimo")}</td>
            <td>${fieldValue(bean: greenFee, field: "disponibles")}</td>
            <td>${fieldValue(bean: greenFee, field: "estado")}</td>
         </tr>
    </g:each>
    </tbody>
</table>

<div class="clear"></div> 
<section id="pagination"> 
    <g:paginate total="${totalGreenFees}" />
</section>