<%@ page import="com.clickandgolf.Scorecardmetadata" %>



<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="scorecardmetadata.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" maxlength="100" required="" value="${scorecardmetadataInstance?.nombre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo1', 'error')} required">
	<label for="parHoyo1">
		<g:message code="scorecardmetadata.parHoyo1.label" default="Par Hoyo1" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo1" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo1')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo2', 'error')} required">
	<label for="parHoyo2">
		<g:message code="scorecardmetadata.parHoyo2.label" default="Par Hoyo2" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo2" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo2')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo3', 'error')} required">
	<label for="parHoyo3">
		<g:message code="scorecardmetadata.parHoyo3.label" default="Par Hoyo3" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo3" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo3')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo4', 'error')} required">
	<label for="parHoyo4">
		<g:message code="scorecardmetadata.parHoyo4.label" default="Par Hoyo4" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo4" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo4')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo5', 'error')} required">
	<label for="parHoyo5">
		<g:message code="scorecardmetadata.parHoyo5.label" default="Par Hoyo5" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo5" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo5')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo6', 'error')} required">
	<label for="parHoyo6">
		<g:message code="scorecardmetadata.parHoyo6.label" default="Par Hoyo6" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo6" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo6')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo7', 'error')} required">
	<label for="parHoyo7">
		<g:message code="scorecardmetadata.parHoyo7.label" default="Par Hoyo7" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo7" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo7')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo9', 'error')} required">
	<label for="parHoyo9">
		<g:message code="scorecardmetadata.parHoyo9.label" default="Par Hoyo9" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo9" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo9')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo10', 'error')} required">
	<label for="parHoyo10">
		<g:message code="scorecardmetadata.parHoyo10.label" default="Par Hoyo10" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo10" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo10')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo11', 'error')} required">
	<label for="parHoyo11">
		<g:message code="scorecardmetadata.parHoyo11.label" default="Par Hoyo11" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo11" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo11')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo12', 'error')} required">
	<label for="parHoyo12">
		<g:message code="scorecardmetadata.parHoyo12.label" default="Par Hoyo12" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo12" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo12')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo13', 'error')} required">
	<label for="parHoyo13">
		<g:message code="scorecardmetadata.parHoyo13.label" default="Par Hoyo13" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo13" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo13')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo14', 'error')} required">
	<label for="parHoyo14">
		<g:message code="scorecardmetadata.parHoyo14.label" default="Par Hoyo14" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo14" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo14')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo15', 'error')} required">
	<label for="parHoyo15">
		<g:message code="scorecardmetadata.parHoyo15.label" default="Par Hoyo15" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo15" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo15')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo16', 'error')} required">
	<label for="parHoyo16">
		<g:message code="scorecardmetadata.parHoyo16.label" default="Par Hoyo16" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo16" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo16')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo17', 'error')} required">
	<label for="parHoyo17">
		<g:message code="scorecardmetadata.parHoyo17.label" default="Par Hoyo17" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo17" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo17')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo18', 'error')} required">
	<label for="parHoyo18">
		<g:message code="scorecardmetadata.parHoyo18.label" default="Par Hoyo18" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo18" min="0" max="5" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo18')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scorecardmetadataInstance, field: 'parHoyo8', 'error')} required">
	<label for="parHoyo8">
		<g:message code="scorecardmetadata.parHoyo8.label" default="Par Hoyo8" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="parHoyo8" required="" value="${fieldValue(bean: scorecardmetadataInstance, field: 'parHoyo8')}"/>
</div>

