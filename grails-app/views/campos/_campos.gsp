<table class="ajax">
    <thead>
        <tr>
            <g:sortableColumn property="nombre" title="${message(code: 'com.clickandgolf.Campo.nombre', default: 'Nombre')}" />
            <g:sortableColumn property="tipo" title="${message(code: 'com.clickandgolf.Campo.tipo', default: 'Tipo')}" />
            <g:sortableColumn property="estado" title="${message(code: 'com.clickandgolf.Campo.estado', default: 'Estado')}" />
            <g:sortableColumn property="fee" title="${message(code: 'com.clickandgolf.Campo.fee', default: 'Fee')}" />
            <g:sortableColumn property="ubicacion" title="${message(code: 'com.clickandgolf.Campo.ubicacion', default: 'Ubicacion')}" />
       </tr>
    </thead>
    <tbody>
    <g:each in="${campos}" status="i" var="campo">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="ver" id="${campo.id}">${fieldValue(bean: campo, field: "nombre")}</g:link></td>
            <td>${fieldValue(bean: campo, field: "tipo")}</td>
            <td>${fieldValue(bean: campo, field: "estado")}</td>
            <td><g:formatNumber number="${campo.fee}" format="#0.00" />  &#37;</td>
            <td>${fieldValue(bean: campo, field: "ubicacion")}</td>
         </tr>
    </g:each>
    </tbody>
</table>

<div class="clear"></div> 
<section id="pagination"> 
    <g:paginate total="${camposTotal}" />
</section>