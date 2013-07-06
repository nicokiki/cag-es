<%@ page contentType="text/html"%>
	
<%--Esto es necesario para que funcione con los emails	--%>
<golflocale:resolveLanguage locale="${locale}" />
	
	
<div class="adress_info greenFeeReservadoVer" style="min-width: 400px;">
	<h4><g:message code="greenfee.book.cancel.descripcion"/></h4>
	<div class="clear"></div>
	
	<ul class="list16 greenFeeReservadoVer_ul" style="margin-top:20px;">
		<li>
			<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.campo"/>:</span>
			<g:link absolute="true" mapping="campo" params="[id:"${fieldValue(bean: greenFee, field: "campo.id")}", nombre:"${fieldValue(bean: greenFee, field: "campo.hyphenatedNombre")}"]">${fieldValue(bean: greenFee, field: "campo.nombre")}</g:link>
		</li>
	</ul> 
	<ul class="list14 greenFeeReservadoVer_ul" style="margin-top:20px;">
		<li>
			<span class="greenFeeReservadoVerTitulo"><g:message code="com.clickandgolf.Campo.telefono"/>:</span>
			&nbsp;${fieldValue(bean: greenFee, field: "campo.telefono")}
		</li>
	</ul> 	
	<ul class="list19 greenFeeReservadoVer_ul">
		<li>
			<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.dia"/>:</span>
			&nbsp;${fieldValue(bean: greenFee, field: "dia")}
		</li>
	</ul> 
	<ul class="list19 greenFeeReservadoVer_ul">
		<li>
			<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.hora"/>:</span>
			&nbsp;${fieldValue(bean: greenFee, field: "horaMinuto")}
		</li>
	</ul> 
	<ul class="list9 greenFeeReservadoVer_ul">
		<li>
			<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.found.hoyos"/>:</span>
			&nbsp;${fieldValue(bean: greenFee, field: "hoyosTexto")}
		</li>
	</ul> 
	<ul class="list18 greenFeeReservadoVer_ul">
		<li>
			<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.precio.individual"/>:</span>
			&nbsp;<g:formatNumber number="${greenFee.precio}" format="#0.00" /> &euro;
		</li>
	</ul> 
	<ul class="list9 greenFeeReservadoVer_ul">
		<li>
			<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.golfistas"/>:</span>
			&nbsp;${fieldValue(bean: greenFeeReservado, field: "golfistas")}
		</li>
	</ul> 

	<ul class="list18 greenFeeReservadoVer_ul">
		<li>
			<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.cancel.fee"/>:</span>
			&nbsp;<g:formatNumber number="${fieldValue(bean: greenFeeReservado, field: "feePagado")}" format="#0.00" /> &euro;
		</li>
	</ul> 
	<ul class="list9 greenFeeReservadoVer_ul">
		<li>
			<span class="greenFeeReservadoVerTitulo"><g:message code="greenfee.book.payment.transaction.id"/>:</span>
			&nbsp;${fieldValue(bean: payment, field: "transactionId")}
		</li>
	</ul> 
	<hr />
	
	<div> 
		<g:link mapping="campo" fragment="normasSeccion" absolute="true" params="[id:"${fieldValue(bean: greenFee, field: "campo.id")}", nombre:"${fieldValue(bean: greenFee, field: "campo.hyphenatedNombre")}"]"><h6><g:message code="greenfee.book.success.condiciones.campo" /></h6></g:link>
	</div> 
	<div> 
		<g:link action="condiciones" controller="info" absolute="true"><h6><g:message code="greenfee.book.success.condiciones.clickandgolf" /></h6></g:link> 
	</div> 
	<div>
		<g:link fragment="cancel" action="condiciones" controller="info" absolute="true"><h6><g:message code="greenfee.book.success.condiciones.cancelacion" /></h6></g:link>
	</div>
	
	<hr />
	<div class="clear"></div>
	<span class="input_tips">
		<g:message code="notificacion.email.info.general" args="[grailsApplication.config.email.notificaciones.info.to]"/>
	</span>
	
</div>
