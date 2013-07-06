
<%@ page import="com.clickandgolf.Promocion" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'promocion.label', default: 'Promocion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-promocion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-promocion" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="desde" title="${message(code: 'promocion.desde.label', default: 'Desde')}" />
					
						<g:sortableColumn property="hasta" title="${message(code: 'promocion.hasta.label', default: 'Hasta')}" />
					
						<th><g:message code="promocion.campo.label" default="Campo" /></th>
					
						<g:sortableColumn property="tipo" title="${message(code: 'promocion.tipo.label', default: 'Tipo')}" />
					
						<g:sortableColumn property="estado" title="${message(code: 'promocion.estado.label', default: 'Estado')}" />
					
						<g:sortableColumn property="texto_es" title="Texto ES" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${promocionInstanceList}" status="i" var="promocionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${promocionInstance.id}"><joda:format value="${promocionInstance.desde}" /></g:link></td>
					
						<td><joda:format value="${promocionInstance.hasta}" /></td>
					
						<td>${fieldValue(bean: promocionInstance, field: "campo.nombre")}</td>
					
						<td>${fieldValue(bean: promocionInstance, field: "tipo")}</td>
					
						<td>${fieldValue(bean: promocionInstance, field: "estado")}</td>
					
						<td>${fieldValue(bean: promocionInstance, field: "texto_es")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${promocionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
