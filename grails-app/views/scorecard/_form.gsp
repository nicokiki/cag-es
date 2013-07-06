<%@ page import="com.clickandgolf.Scorecard" %>



<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="scorecard.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" maxlength="100" required="" value="${scorecardInstance?.nombre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo1', 'error')} ">
	<label for="metrosHoyo1">
		<g:message code="scorecard.metrosHoyo1.label" default="Metros Hoyo1" />
		
	</label>
	<g:textField name="metrosHoyo1" maxlength="5" value="${scorecardInstance?.metrosHoyo1}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo2', 'error')} ">
	<label for="metrosHoyo2">
		<g:message code="scorecard.metrosHoyo2.label" default="Metros Hoyo2" />
		
	</label>
	<g:textField name="metrosHoyo2" maxlength="5" value="${scorecardInstance?.metrosHoyo2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo3', 'error')} ">
	<label for="metrosHoyo3">
		<g:message code="scorecard.metrosHoyo3.label" default="Metros Hoyo3" />
		
	</label>
	<g:textField name="metrosHoyo3" maxlength="5" value="${scorecardInstance?.metrosHoyo3}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo4', 'error')} ">
	<label for="metrosHoyo4">
		<g:message code="scorecard.metrosHoyo4.label" default="Metros Hoyo4" />
		
	</label>
	<g:textField name="metrosHoyo4" maxlength="5" value="${scorecardInstance?.metrosHoyo4}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo5', 'error')} ">
	<label for="metrosHoyo5">
		<g:message code="scorecard.metrosHoyo5.label" default="Metros Hoyo5" />
		
	</label>
	<g:textField name="metrosHoyo5" maxlength="5" value="${scorecardInstance?.metrosHoyo5}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo6', 'error')} ">
	<label for="metrosHoyo6">
		<g:message code="scorecard.metrosHoyo6.label" default="Metros Hoyo6" />
		
	</label>
	<g:textField name="metrosHoyo6" maxlength="5" value="${scorecardInstance?.metrosHoyo6}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo7', 'error')} ">
	<label for="metrosHoyo7">
		<g:message code="scorecard.metrosHoyo7.label" default="Metros Hoyo7" />
		
	</label>
	<g:textField name="metrosHoyo7" maxlength="5" value="${scorecardInstance?.metrosHoyo7}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo8', 'error')} ">
	<label for="metrosHoyo8">
		<g:message code="scorecard.metrosHoyo8.label" default="Metros Hoyo8" />
		
	</label>
	<g:textField name="metrosHoyo8" maxlength="5" value="${scorecardInstance?.metrosHoyo8}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo9', 'error')} ">
	<label for="metrosHoyo9">
		<g:message code="scorecard.metrosHoyo9.label" default="Metros Hoyo9" />
		
	</label>
	<g:textField name="metrosHoyo9" maxlength="5" value="${scorecardInstance?.metrosHoyo9}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo10', 'error')} ">
	<label for="metrosHoyo10">
		<g:message code="scorecard.metrosHoyo10.label" default="Metros Hoyo10" />
		
	</label>
	<g:textField name="metrosHoyo10" maxlength="5" value="${scorecardInstance?.metrosHoyo10}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo11', 'error')} ">
	<label for="metrosHoyo11">
		<g:message code="scorecard.metrosHoyo11.label" default="Metros Hoyo11" />
		
	</label>
	<g:textField name="metrosHoyo11" maxlength="5" value="${scorecardInstance?.metrosHoyo11}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo12', 'error')} ">
	<label for="metrosHoyo12">
		<g:message code="scorecard.metrosHoyo12.label" default="Metros Hoyo12" />
		
	</label>
	<g:textField name="metrosHoyo12" maxlength="5" value="${scorecardInstance?.metrosHoyo12}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo13', 'error')} ">
	<label for="metrosHoyo13">
		<g:message code="scorecard.metrosHoyo13.label" default="Metros Hoyo13" />
		
	</label>
	<g:textField name="metrosHoyo13" maxlength="5" value="${scorecardInstance?.metrosHoyo13}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo14', 'error')} ">
	<label for="metrosHoyo14">
		<g:message code="scorecard.metrosHoyo14.label" default="Metros Hoyo14" />
		
	</label>
	<g:textField name="metrosHoyo14" maxlength="5" value="${scorecardInstance?.metrosHoyo14}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo15', 'error')} ">
	<label for="metrosHoyo15">
		<g:message code="scorecard.metrosHoyo15.label" default="Metros Hoyo15" />
		
	</label>
	<g:textField name="metrosHoyo15" maxlength="5" value="${scorecardInstance?.metrosHoyo15}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo16', 'error')} ">
	<label for="metrosHoyo16">
		<g:message code="scorecard.metrosHoyo16.label" default="Metros Hoyo16" />
		
	</label>
	<g:textField name="metrosHoyo16" maxlength="5" value="${scorecardInstance?.metrosHoyo16}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo17', 'error')} ">
	<label for="metrosHoyo17">
		<g:message code="scorecard.metrosHoyo17.label" default="Metros Hoyo17" />
		
	</label>
	<g:textField name="metrosHoyo17" maxlength="5" value="${scorecardInstance?.metrosHoyo17}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'metrosHoyo18', 'error')} ">
	<label for="metrosHoyo18">
		<g:message code="scorecard.metrosHoyo18.label" default="Metros Hoyo18" />
		
	</label>
	<g:textField name="metrosHoyo18" maxlength="5" value="${scorecardInstance?.metrosHoyo18}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardInstance, field: 'color', 'error')} ">
	<label for="color">
		<g:message code="scorecard.color.label" default="Color" />
		
	</label>
	<g:select name="color" from="${scorecardInstance.constraints.color.inList}" value="${scorecardInstance?.color}" valueMessagePrefix="scorecard.color" noSelection="['': '']"/>
</div>

