
<%@ page import="com.clickandgolf.Promocion" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'promocion.label', default: 'Promocion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-promocion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-promocion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list promocion">
			
				<g:if test="${promocionInstance?.desde}">
				<li class="fieldcontain">
					<span id="desde-label" class="property-label"><g:message code="promocion.desde.label" default="Desde" /></span>
					
						<span class="property-value" aria-labelledby="desde-label"><joda:format value="${promocionInstance.desde}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${promocionInstance?.hasta}">
				<li class="fieldcontain">
					<span id="hasta-label" class="property-label"><g:message code="promocion.hasta.label" default="Hasta" /></span>
					
						<span class="property-value" aria-labelledby="hasta-label"><joda:format value="${promocionInstance.hasta}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${promocionInstance?.campo}">
				<li class="fieldcontain">
					<span id="campo-label" class="property-label"><g:message code="promocion.campo.label" default="Campo" /></span>
					
						<span class="property-value" aria-labelledby="campo-label"><g:link controller="campo" action="show" id="${promocionInstance?.campo?.id}">${promocionInstance?.campo?.nombre}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${promocionInstance?.tipo}">
				<li class="fieldcontain">
					<span id="tipo-label" class="property-label"><g:message code="promocion.tipo.label" default="Tipo" /></span>
					
						<span class="property-value" aria-labelledby="tipo-label"><g:fieldValue bean="${promocionInstance}" field="tipo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${promocionInstance?.estado}">
				<li class="fieldcontain">
					<span id="estado-label" class="property-label"><g:message code="promocion.estado.label" default="Estado" /></span>
					
						<span class="property-value" aria-labelledby="estado-label"><g:fieldValue bean="${promocionInstance}" field="estado"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${promocionInstance?.texto_es}">
				<li class="fieldcontain">
					<span id="texto-label" class="property-label">Texto ES</span>
					
						<span class="property-value" aria-labelledby="texto-label"><g:fieldValue bean="${promocionInstance}" field="texto_es"/></span>
					
				</li>
				</g:if>
				<g:if test="${promocionInstance?.texto_fr}">
				<li class="fieldcontain">
					<span id="texto-label" class="property-label">Texto FR</span>
					
						<span class="property-value" aria-labelledby="texto-label"><g:fieldValue bean="${promocionInstance}" field="texto_fr"/></span>
					
				</li>
				</g:if>
				<g:if test="${promocionInstance?.texto_de}">
				<li class="fieldcontain">
					<span id="texto-label" class="property-label">Texto DE</span>
					
						<span class="property-value" aria-labelledby="texto-label"><g:fieldValue bean="${promocionInstance}" field="texto_de"/></span>
					
				</li>
				</g:if>
				<g:if test="${promocionInstance?.texto_ca}">
				<li class="fieldcontain">
					<span id="texto-label" class="property-label">Texto CA</span>
					
						<span class="property-value" aria-labelledby="texto-label"><g:fieldValue bean="${promocionInstance}" field="texto_ca"/></span>
					
				</li>
				</g:if>
				<g:if test="${promocionInstance?.texto_gl}">
				<li class="fieldcontain">
					<span id="texto-label" class="property-label">Texto GL</span>
					
						<span class="property-value" aria-labelledby="texto-label"><g:fieldValue bean="${promocionInstance}" field="texto_gl"/></span>
					
				</li>
				</g:if>
				<g:if test="${promocionInstance?.texto_en}">
				<li class="fieldcontain">
					<span id="texto-label" class="property-label">Texto EN</span>
					
						<span class="property-value" aria-labelledby="texto-label"><g:fieldValue bean="${promocionInstance}" field="texto_en"/></span>
					
				</li>
				</g:if>

				<g:if test="${promocionInstance?.esBanner}">
				<li class="fieldcontain">
					<span id="texto-label" class="property-label">Es Banner</span>
					<span class="property-value" aria-labelledby="texto-label"><g:fieldValue bean="${promocionInstance}" field="esBanner"/></span>
				</li>
				</g:if>
				<g:else>
					<span id="texto-label" class="property-label">NO Es Banner</span>
				</g:else>

			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${promocionInstance?.id}" />
					<g:link class="edit" action="edit" id="${promocionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
