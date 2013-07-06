<%@ page import="com.clickandgolf.Ubicacion" %>



<div class="fieldcontain ${hasErrors(bean: ubicacionInstance, field: 'ciudad', 'error')} ">
	<label for="ciudad">
		<g:message code="ubicacion.ciudad.label" default="Ciudad" />
		
	</label>
	<g:textField name="ciudad" maxlength="200" value="${ubicacionInstance?.ciudad}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ubicacionInstance, field: 'provincia', 'error')} ">
	<label for="provincia">
		<g:message code="ubicacion.provincia.label" default="Provincia" />
		
	</label>
	<g:textField name="provincia" maxlength="200" value="${ubicacionInstance?.provincia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ubicacionInstance, field: 'region', 'error')} ">
	<label for="region">
		<g:message code="ubicacion.region.label" default="Region" />
		
	</label>
	<g:textField name="region" maxlength="200" value="${ubicacionInstance?.region}"/>
</div>
