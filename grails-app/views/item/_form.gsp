<%@ page import="com.clickandgolf.ie.Item" %>



<div class="fieldcontain ${hasErrors(bean: itemInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="item.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${itemInstance?.nombre}"/>
</div>

