
<%@ page import="com.clickandgolf.Scorecardmetadata" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-scorecardmetadata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-scorecardmetadata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list scorecardmetadata">
			
				<g:if test="${scorecardmetadataInstance?.nombre}">
				<li class="fieldcontain">
					<span id="nombre-label" class="property-label"><g:message code="scorecardmetadata.nombre.label" default="Nombre" /></span>
					
						<span class="property-value" aria-labelledby="nombre-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="nombre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo1}">
				<li class="fieldcontain">
					<span id="parHoyo1-label" class="property-label"><g:message code="scorecardmetadata.parHoyo1.label" default="Par Hoyo1" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo1-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo1"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo2}">
				<li class="fieldcontain">
					<span id="parHoyo2-label" class="property-label"><g:message code="scorecardmetadata.parHoyo2.label" default="Par Hoyo2" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo2-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo2"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo3}">
				<li class="fieldcontain">
					<span id="parHoyo3-label" class="property-label"><g:message code="scorecardmetadata.parHoyo3.label" default="Par Hoyo3" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo3-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo3"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo4}">
				<li class="fieldcontain">
					<span id="parHoyo4-label" class="property-label"><g:message code="scorecardmetadata.parHoyo4.label" default="Par Hoyo4" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo4-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo4"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo5}">
				<li class="fieldcontain">
					<span id="parHoyo5-label" class="property-label"><g:message code="scorecardmetadata.parHoyo5.label" default="Par Hoyo5" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo5-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo5"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo6}">
				<li class="fieldcontain">
					<span id="parHoyo6-label" class="property-label"><g:message code="scorecardmetadata.parHoyo6.label" default="Par Hoyo6" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo6-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo6"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo7}">
				<li class="fieldcontain">
					<span id="parHoyo7-label" class="property-label"><g:message code="scorecardmetadata.parHoyo7.label" default="Par Hoyo7" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo7-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo7"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo9}">
				<li class="fieldcontain">
					<span id="parHoyo9-label" class="property-label"><g:message code="scorecardmetadata.parHoyo9.label" default="Par Hoyo9" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo9-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo9"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo10}">
				<li class="fieldcontain">
					<span id="parHoyo10-label" class="property-label"><g:message code="scorecardmetadata.parHoyo10.label" default="Par Hoyo10" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo10-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo10"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo11}">
				<li class="fieldcontain">
					<span id="parHoyo11-label" class="property-label"><g:message code="scorecardmetadata.parHoyo11.label" default="Par Hoyo11" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo11-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo11"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo12}">
				<li class="fieldcontain">
					<span id="parHoyo12-label" class="property-label"><g:message code="scorecardmetadata.parHoyo12.label" default="Par Hoyo12" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo12-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo12"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo13}">
				<li class="fieldcontain">
					<span id="parHoyo13-label" class="property-label"><g:message code="scorecardmetadata.parHoyo13.label" default="Par Hoyo13" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo13-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo13"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo14}">
				<li class="fieldcontain">
					<span id="parHoyo14-label" class="property-label"><g:message code="scorecardmetadata.parHoyo14.label" default="Par Hoyo14" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo14-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo14"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo15}">
				<li class="fieldcontain">
					<span id="parHoyo15-label" class="property-label"><g:message code="scorecardmetadata.parHoyo15.label" default="Par Hoyo15" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo15-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo15"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo16}">
				<li class="fieldcontain">
					<span id="parHoyo16-label" class="property-label"><g:message code="scorecardmetadata.parHoyo16.label" default="Par Hoyo16" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo16-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo16"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo17}">
				<li class="fieldcontain">
					<span id="parHoyo17-label" class="property-label"><g:message code="scorecardmetadata.parHoyo17.label" default="Par Hoyo17" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo17-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo17"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo18}">
				<li class="fieldcontain">
					<span id="parHoyo18-label" class="property-label"><g:message code="scorecardmetadata.parHoyo18.label" default="Par Hoyo18" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo18-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo18"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardmetadataInstance?.parHoyo8}">
				<li class="fieldcontain">
					<span id="parHoyo8-label" class="property-label"><g:message code="scorecardmetadata.parHoyo8.label" default="Par Hoyo8" /></span>
					
						<span class="property-value" aria-labelledby="parHoyo8-label"><g:fieldValue bean="${scorecardmetadataInstance}" field="parHoyo8"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${scorecardmetadataInstance?.id}" />
					<g:link class="edit" action="edit" id="${scorecardmetadataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
