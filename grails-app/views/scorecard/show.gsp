
<%@ page import="com.clickandgolf.Scorecard" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'scorecard.label', default: 'Scorecard')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-scorecard" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-scorecard" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list scorecard">
			
				<g:if test="${scorecardInstance?.nombre}">
				<li class="fieldcontain">
					<span id="nombre-label" class="property-label"><g:message code="scorecard.nombre.label" default="Nombre" /></span>
					
						<span class="property-value" aria-labelledby="nombre-label"><g:fieldValue bean="${scorecardInstance}" field="nombre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo1}">
				<li class="fieldcontain">
					<span id="metrosHoyo1-label" class="property-label"><g:message code="scorecard.metrosHoyo1.label" default="Metros Hoyo1" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo1-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo1"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo2}">
				<li class="fieldcontain">
					<span id="metrosHoyo2-label" class="property-label"><g:message code="scorecard.metrosHoyo2.label" default="Metros Hoyo2" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo2-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo2"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo3}">
				<li class="fieldcontain">
					<span id="metrosHoyo3-label" class="property-label"><g:message code="scorecard.metrosHoyo3.label" default="Metros Hoyo3" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo3-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo3"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo4}">
				<li class="fieldcontain">
					<span id="metrosHoyo4-label" class="property-label"><g:message code="scorecard.metrosHoyo4.label" default="Metros Hoyo4" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo4-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo4"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo5}">
				<li class="fieldcontain">
					<span id="metrosHoyo5-label" class="property-label"><g:message code="scorecard.metrosHoyo5.label" default="Metros Hoyo5" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo5-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo5"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo6}">
				<li class="fieldcontain">
					<span id="metrosHoyo6-label" class="property-label"><g:message code="scorecard.metrosHoyo6.label" default="Metros Hoyo6" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo6-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo6"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo7}">
				<li class="fieldcontain">
					<span id="metrosHoyo7-label" class="property-label"><g:message code="scorecard.metrosHoyo7.label" default="Metros Hoyo7" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo7-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo7"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo8}">
				<li class="fieldcontain">
					<span id="metrosHoyo8-label" class="property-label"><g:message code="scorecard.metrosHoyo8.label" default="Metros Hoyo8" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo8-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo8"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo9}">
				<li class="fieldcontain">
					<span id="metrosHoyo9-label" class="property-label"><g:message code="scorecard.metrosHoyo9.label" default="Metros Hoyo9" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo9-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo9"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo10}">
				<li class="fieldcontain">
					<span id="metrosHoyo10-label" class="property-label"><g:message code="scorecard.metrosHoyo10.label" default="Metros Hoyo10" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo10-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo10"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo11}">
				<li class="fieldcontain">
					<span id="metrosHoyo11-label" class="property-label"><g:message code="scorecard.metrosHoyo11.label" default="Metros Hoyo11" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo11-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo11"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo12}">
				<li class="fieldcontain">
					<span id="metrosHoyo12-label" class="property-label"><g:message code="scorecard.metrosHoyo12.label" default="Metros Hoyo12" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo12-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo12"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo13}">
				<li class="fieldcontain">
					<span id="metrosHoyo13-label" class="property-label"><g:message code="scorecard.metrosHoyo13.label" default="Metros Hoyo13" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo13-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo13"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo14}">
				<li class="fieldcontain">
					<span id="metrosHoyo14-label" class="property-label"><g:message code="scorecard.metrosHoyo14.label" default="Metros Hoyo14" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo14-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo14"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo15}">
				<li class="fieldcontain">
					<span id="metrosHoyo15-label" class="property-label"><g:message code="scorecard.metrosHoyo15.label" default="Metros Hoyo15" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo15-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo15"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo16}">
				<li class="fieldcontain">
					<span id="metrosHoyo16-label" class="property-label"><g:message code="scorecard.metrosHoyo16.label" default="Metros Hoyo16" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo16-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo16"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo17}">
				<li class="fieldcontain">
					<span id="metrosHoyo17-label" class="property-label"><g:message code="scorecard.metrosHoyo17.label" default="Metros Hoyo17" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo17-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo17"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.metrosHoyo18}">
				<li class="fieldcontain">
					<span id="metrosHoyo18-label" class="property-label"><g:message code="scorecard.metrosHoyo18.label" default="Metros Hoyo18" /></span>
					
						<span class="property-value" aria-labelledby="metrosHoyo18-label"><g:fieldValue bean="${scorecardInstance}" field="metrosHoyo18"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${scorecardInstance?.color}">
				<li class="fieldcontain">
					<span id="color-label" class="property-label"><g:message code="scorecard.color.label" default="Color" /></span>
					
						<span class="property-value" aria-labelledby="color-label"><g:fieldValue bean="${scorecardInstance}" field="color"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${scorecardInstance?.id}" />
					<g:link class="edit" action="edit" id="${scorecardInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
