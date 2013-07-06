<%@ page contentType="text/html"%>
	
<%--Esto es necesario para que funcione con los emails	--%>
<golflocale:resolveLanguage locale="${locale}" />
	
	
<div class="adress_info greenFeeReservadoVer" style="min-width: 400px;">
	<div class="clear"></div>
	<p>
		<g:message code="notificacion.email.registro.descripcion.1"/>:
	</p>
	<g:link absolute="true" controller="registro" action="activar" params="[email:"${nuevoUsuario.email}", transactionId:"${transactionId}"]"><g:message code="notificacion.email.registro.click"/></g:link>
	<div class="clear"></div>
	
	<p style="margin-top:30px;">
		<g:message code="notificacion.email.registro.descripcion.2"/>
	</p>
	<span>${createLink(absolute: 'true', controller: 'registro', action: 'activar', params:[email:"${nuevoUsuario.email}", transactionId:"${transactionId}"] )}</span>
	<div class="clear"></div>
	
	<p style="margin-top:40px;">
		<g:message code="notificacion.email.registro.descripcion.3"/>
	</p>
	<div class="clear"></div>
	<hr />

	<div> 
		<g:link action="condiciones" controller="info" absolute="true"><h6><g:message code="greenfee.book.success.condiciones.clickandgolf" /></h6></g:link> 
	</div> 
	
	<hr />
	<div class="clear"></div>
	<span class="input_tips">
		<g:message code="notificacion.email.info.general" args="[grailsApplication.config.email.notificaciones.info.to]"/>
	</span>
	
</div>
