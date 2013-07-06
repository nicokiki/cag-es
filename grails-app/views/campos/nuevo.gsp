<!DOCTYPE html>
<%@page import="com.clickandgolf.Ubicacion"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>
		<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
		<meta charset="UTF-8" />
		<meta name="description" content="Website the reserva online de green fees" />
		<meta name="keywords" content="Premium, HTML5, Template" />
		<meta name="author" content="Smartik" />
		
		<r:require module="core"/>
		
	</head>

	<body>
		
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->

		<section class="container layoutLogin">
		
			<section class="page_title">
				<div class="title"><g:message code="campo.new.label"/></div>
				<div class="slogan"><g:message code="campo.new.description" /></div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorLoginMessage">${flash.message}</div>
			</g:if>
			
			<section class="grid_8">

				<g:uploadForm controller="campos" action="guardar" class="form_place margin_top_0" >
				
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.nombre" default="Nombre"/>:</label>
							<div class="block_content">
								<input type="text" name="nombre" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label>Nombre Hyphenated para mostrar en el URL:</label>
							<div class="block_content">
								<input type="text" name="hyphenatedNombre" class="i-format" required="required" />
								<span class="input_tips">Es el nombre SEO friendly q mostraremos. NO usar acentos ni enies ni nada raro.</span>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.email" default="Email"/>:</label>
							<div class="block_content">
								<input type="text" name="email" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>					
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.fee" default="Fee"/>:</label>
							<div class="block_content">
								<input type="text" name="fee" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.require.licencia" default="Requiere Licencia"/>:</label>
							<div class="block_content">
								<input type="checkbox" name="requiereLicencia" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.tipo" default="Tipo"/>:</label>
							<div class="block_content">
								<golf:select name="tipo" value="Golf" dataList="${['Pitch & Putt', 'Golf']}"  cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.tipo', default: 'Elige un tipo')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.estado" default="Estado"/>:</label>
							<div class="block_content">
								<golf:select name="estado" value="ACTIVO" dataList="${['ACTIVO', 'INACTIVO']}"  cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.estado', default: 'Elige un estado')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.ubicacion" default="Nombre"/>:</label>
							<div class="block_content">
								<golf:select name="ubicacion" value="" dataList="${ubicaciones}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.ubicacion', default: 'Elige una ubicacion')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.scorecardmetadata" default="Metadata Tarjeta Puntuacion"/>:</label>
							<div class="block_content">
								<golf:select name="scorecardmetadata" value="" dataList="${scorecardsMetadata}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.scorecardmetadata', default: 'Elige una metadata de Tarjeta de Puntuacion')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.blancas" default="Tarjeta Blancas"/>:</label>
							<div class="block_content">
								<golf:select name="blancas" value="" dataList="${scorecards}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.blancas', default: 'Elige la Tarjeta de Puntuacion de las blancas')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.amarillas" default="Tarjeta Amarillas"/>:</label>
							<div class="block_content">
								<golf:select name="amarillas" value="" dataList="${scorecards}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.amarillas', default: 'Elige la Tarjeta de Puntuacion de las amarillas')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.rojas" default="Tarjeta Rojas"/>:</label>
							<div class="block_content">
								<golf:select name="rojas" value="" dataList="${scorecards}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.rojas', default: 'Elige la Tarjeta de Puntuacion de las rojas')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.azules" default="Tarjeta Azules"/>:</label>
							<div class="block_content">
								<golf:select name="azules" value="" dataList="${scorecards}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.azules', default: 'Elige la Tarjeta de Puntuacion de las azules')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.latitud" default="Latitud"/>:</label>
							<div class="block_content">
								<input type="text" name="latitud" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.longitud" default="Longitud"/>:</label>
							<div class="block_content">
								<input type="text" name="longitud" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.linkCampo" default="Link Campo"/>:</label>
							<div class="block_content">
								<input type="text" name="linkCampo" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.linkExtra" default="Link Extra"/>:</label>
							<div class="block_content">
								<input type="text" name="linkExtra" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.telefono"/>:</label>
							<div class="block_content">
								<input type="text" name="telefono" class="i-format" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.direccion"/>:</label>
							<div class="block_content">
								<input type="text" name="direccion" class="i-format" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.emailContacto"/>:</label>
							<div class="block_content">
								<input type="text" name="emailContacto" class="i-format" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.descripcion" default="Descripcion"/>:</label>
							<div class="block_content">
								<textarea type="textarea" rows="1" cols="20" name="descripcion" class="default"></textarea>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.descripcionQuotted" default="Descripcion Quotted"/>:</label>
							<div class="block_content">
								<textarea type="textarea" rows="1" cols="5" name="descripcionQuotted" class="default"></textarea>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.comoLlegar" default="Como Llegar"/>:</label>
							<div class="block_content">
								<textarea type="textarea" rows="1" cols="5" name="comoLlegar" class="default"></textarea>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.coordenadasGps" default="Coordenadas GPS"/>:</label>
							<div class="block_content">
								<input type="text" name="coordenadasGps" class="i-format" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.normasLink" default="Link Normas"/>:</label>
							<div class="block_content">
								<input type="text" name="normasLink" class="i-format"  />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.normasSeccion" default="Normas"/>:</label>
							<div class="block_content">
								<textarea type="textarea" rows="1" cols="20" name="normasSeccion" class="default"></textarea>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<hr />
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.notasReserva" />:</label>
							<div class="block_content">
								<textarea type="textarea" rows="1" cols="20" name="notasReserva" class="default"></textarea>
								<span class="input_tips">Son notas a poner cuando se envien emails de confirmacion.</span>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<hr />
					
					<section class="form_row">
						<div>
							<div class="block_content">
								<input class="button darkgray textcenter" type="submit" value='${message(code: "com.clickandgolf.Campo.guardar")}' />
								<g:link action="listar" controller="campos" class="button grey textcenter"><g:message code="campo.list.label" default="Listar" /></g:link>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
				</g:uploadForm>
				
			</section>

		</section> <!-- End section .container layout -->
		
	</body>

</html>
