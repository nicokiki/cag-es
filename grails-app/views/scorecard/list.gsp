
<%@ page import="com.clickandgolf.Scorecard" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'scorecard.label', default: 'Scorecard')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-scorecard" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-scorecard" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="nombre" title="${message(code: 'scorecard.nombre.label', default: 'Nombre')}" />
					
						<g:sortableColumn property="metrosHoyo1" title="${message(code: 'scorecard.metrosHoyo1.label', default: 'Metros Hoyo1')}" />
					
						<g:sortableColumn property="metrosHoyo2" title="${message(code: 'scorecard.metrosHoyo2.label', default: 'Metros Hoyo2')}" />
					
						<g:sortableColumn property="metrosHoyo3" title="${message(code: 'scorecard.metrosHoyo3.label', default: 'Metros Hoyo3')}" />
					
						<g:sortableColumn property="metrosHoyo4" title="${message(code: 'scorecard.metrosHoyo4.label', default: 'Metros Hoyo4')}" />
					
						<g:sortableColumn property="metrosHoyo5" title="${message(code: 'scorecard.metrosHoyo5.label', default: 'Metros Hoyo5')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${scorecardInstanceList}" status="i" var="scorecardInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${scorecardInstance.id}">${fieldValue(bean: scorecardInstance, field: "nombre")}</g:link></td>
					
						<td>${fieldValue(bean: scorecardInstance, field: "metrosHoyo1")}</td>
					
						<td>${fieldValue(bean: scorecardInstance, field: "metrosHoyo2")}</td>
					
						<td>${fieldValue(bean: scorecardInstance, field: "metrosHoyo3")}</td>
					
						<td>${fieldValue(bean: scorecardInstance, field: "metrosHoyo4")}</td>
					
						<td>${fieldValue(bean: scorecardInstance, field: "metrosHoyo5")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${scorecardInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
