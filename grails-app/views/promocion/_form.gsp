<%@ page import="com.clickandgolf.Promocion" %>



<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'desde', 'error')} required">
	<label for="desde">
		<g:message code="promocion.desde.label" default="Desde" />
		<span class="required-indicator">*</span>
	</label>
	<joda:dateTimePicker name="desde" value="${promocionInstance.desde}" years="${2012..2020}" />
</div>

<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'hasta', 'error')} required">
	<label for="hasta">
		<g:message code="promocion.hasta.label" default="Hasta" />
		<span class="required-indicator">*</span>
	</label>
	<joda:dateTimePicker name="hasta" value="${promocionInstance.hasta}" years="${2012..2020}" />
</div>

<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'campo', 'error')} required">
	<label for="campo">
		<g:message code="promocion.campo.label" default="Campo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="campo" name="campo.id" from="${com.clickandgolf.Campo.list()}" optionKey="id" optionValue="nombre" required="" value="${promocionInstance?.campo?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'tipo', 'error')} ">
	<label for="tipo">
		<g:message code="promocion.tipo.label" default="Tipo" />
		
	</label>
	<g:select name="tipo" from="${promocionInstance.constraints.tipo.inList}" value="${promocionInstance?.tipo}" valueMessagePrefix="promocion.tipo" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'estado', 'error')} ">
	<label for="estado">
		<g:message code="promocion.estado.label" default="Estado" />
		
	</label>
	<g:select name="estado" from="${promocionInstance.constraints.estado.inList}" value="${promocionInstance?.estado}" valueMessagePrefix="promocion.estado" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'texto_es', 'error')} required">
	<label for="texto_es">
		Texto ES
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="texto_es" maxlength="150" required="" value="${promocionInstance?.texto_es}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'texto_fr', 'error')} required">
	<label for="texto_fr">
		Texto FR
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="texto_fr" maxlength="150" required="" value="${promocionInstance?.texto_fr}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'texto_de', 'error')} required">
	<label for="texto_de">
		Texto DE
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="texto_de" maxlength="150" required="" value="${promocionInstance?.texto_de}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'texto_ca', 'error')} required">
	<label for="texto_ca">
		Texto CA
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="texto_ca" maxlength="150" required="" value="${promocionInstance?.texto_ca}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'texto_gl', 'error')} required">
	<label for="texto_gl">
		Texto GL
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="texto_gl" maxlength="150" required="" value="${promocionInstance?.texto_gl}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: promocionInstance, field: 'texto_en', 'error')} required">
	<label for="texto_en">
		Texto EN
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="texto_en" maxlength="150" required="" value="${promocionInstance?.texto_en}"/>
</div>

<div class="clear"></div>
<div>
	<label>Es banner?</label>
	<div class="block_content">
		<g:if test="${promocionInstance?.esBanner}">
			<input type="checkbox" name="esBanner" checked="checked"/>
		</g:if>
		<g:else>
			<input type="checkbox" name="esBanner" />
		</g:else>
	</div>
</div>
<div class="clear"></div>

<div>
	<label>Titulo Banner:</label>
	<div class="block_content">
		<input type="text" name="tituloBanner" value="${promocionInstance?.tituloBanner}" class="i-format" />
	</div>
</div>
<div class="clear"></div>

<div>
	<label>Texto Banner:</label>
	<div class="block_content">
		<input type="text" name="textoBanner" value="${promocionInstance?.textoBanner}" class="i-format" />
	</div>
</div>
<div class="clear"></div>

<div>
	<label>Link Banner:</label>
	<div class="block_content">
		<input type="text" name="linkBanner" value="${promocionInstance?.linkBanner}" class="i-format" />
	</div>
</div>
<div class="clear"></div>

<div>
	<label>Imagen Banner:</label>
	<div class="block_content">
		<input type="text" name="imgBanner" value="${promocionInstance?.imgBanner}" class="i-format" />
	</div>
</div>
<div class="clear"></div>

<div>
	<label>Imagen Grande Banner:</label>
	<div class="block_content">
		<input type="text" name="imgGrandeBanner" value="${promocionInstance?.imgGrandeBanner}" class="i-format" />
	</div>
</div>
<div class="clear"></div>

