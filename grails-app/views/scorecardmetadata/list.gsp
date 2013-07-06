
<%@ page import="com.clickandgolf.Scorecardmetadata" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-scorecardmetadata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-scorecardmetadata" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="nombre" title="${message(code: 'scorecardmetadata.nombre.label', default: 'Nombre')}" />
					
						<g:sortableColumn property="parHoyo1" title="${message(code: 'scorecardmetadata.parHoyo1.label', default: 'Par Hoyo1')}" />
					
						<g:sortableColumn property="parHoyo2" title="${message(code: 'scorecardmetadata.parHoyo2.label', default: 'Par Hoyo2')}" />
					
						<g:sortableColumn property="parHoyo3" title="${message(code: 'scorecardmetadata.parHoyo3.label', default: 'Par Hoyo3')}" />
					
						<g:sortableColumn property="parHoyo4" title="${message(code: 'scorecardmetadata.parHoyo4.label', default: 'Par Hoyo4')}" />
					
						<g:sortableColumn property="parHoyo5" title="${message(code: 'scorecardmetadata.parHoyo5.label', default: 'Par Hoyo5')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${scorecardmetadataInstanceList}" status="i" var="scorecardmetadataInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${scorecardmetadataInstance.id}">${fieldValue(bean: scorecardmetadataInstance, field: "nombre")}</g:link></td>
					
						<td>${fieldValue(bean: scorecardmetadataInstance, field: "parHoyo1")}</td>
					
						<td>${fieldValue(bean: scorecardmetadataInstance, field: "parHoyo2")}</td>
					
						<td>${fieldValue(bean: scorecardmetadataInstance, field: "parHoyo3")}</td>
					
						<td>${fieldValue(bean: scorecardmetadataInstance, field: "parHoyo4")}</td>
					
						<td>${fieldValue(bean: scorecardmetadataInstance, field: "parHoyo5")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${scorecardmetadataInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
