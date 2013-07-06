
<%@ page import="com.clickandgolf.Ubicacion" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ubicacion.label', default: 'Ubicacion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-ubicacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-ubicacion" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="ciudad" title="${message(code: 'ubicacion.ciudad.label', default: 'Ciudad')}" />
					
						<g:sortableColumn property="provincia" title="${message(code: 'ubicacion.provincia.label', default: 'Provincia')}" />
					
						<g:sortableColumn property="region" title="${message(code: 'ubicacion.region.label', default: 'Region')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ubicacionInstanceList}" status="i" var="ubicacionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${ubicacionInstance.id}">${fieldValue(bean: ubicacionInstance, field: "ciudad")}</g:link></td>
					
						<td>${fieldValue(bean: ubicacionInstance, field: "provincia")}</td>
					
						<td>${fieldValue(bean: ubicacionInstance, field: "region")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ubicacionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
