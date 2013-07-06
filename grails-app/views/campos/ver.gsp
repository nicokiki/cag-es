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
				<div class="title"><g:message code="campo.edit.label"/></div>
				<div class="slogan"><g:message code="campo.edit.description" /> '${fieldValue(bean: campo, field: "nombre")}'</div>
			</section>

			<g:if test='${flash.message}'>
				<div class="errorLoginMessage">${flash.message}</div>
			</g:if>
			
			<section class="grid_8">

				<g:uploadForm controller="campos" action="modificar" class="form_place margin_top_0" >
				
					<input type="hidden" name="id" value="${fieldValue(bean: campo, field: "id")}" />
				
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.nombre" default="Nombre"/>:</label>
							<div class="block_content">
								<input type="text" name="nombre" value="${fieldValue(bean: campo, field: "nombre")}" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label>Nombre Hyphenated para mostrar en el URL:</label>
							<div class="block_content">
								<input type="text" name="hyphenatedNombre" value="${fieldValue(bean: campo, field: "hyphenatedNombre")}" class="i-format" required="required" />
								<span class="input_tips">Es el nombre SEO friendly q mostraremos. NO usar acentos ni enies ni nada raro.</span>
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.email" default="Email"/>:</label>
							<div class="block_content">
								<input type="text" name="email" value="${fieldValue(bean: campo, field: "email")}" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.fee" default="Fee"/>:</label>
							<div class="block_content">
								<input type="text" name="fee" value="${fieldValue(bean: campo, field: "fee")}" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.require.licencia" default="Requiere Licencia"/>:</label>
							<div class="block_content">
								<g:if test="${campo.requiereLicencia}">
									<input type="checkbox" name="requiereLicencia" checked="checked" />
								</g:if>
								<g:else>
									<input type="checkbox" name="requiereLicencia" />
								</g:else>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.tipo" default="Tipo"/>:</label>
							<div class="block_content">
								<golf:select name="tipo" value="${campo?.tipo}" dataList="${['Pitch & Putt', 'Golf']}"  cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.tipo', default: 'Elige un tipo')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.estado" default="Estado"/>:</label>
							<div class="block_content">
								<golf:select name="estado" value="${campo?.estado}" dataList="${['ACTIVO', 'INACTIVO']}"  cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.estado', default: 'Elige un estado')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.ubicacion" default="Nombre"/>:</label>
							<div class="block_content">
								<golf:select name="ubicacion" value="${campo?.ubicacion?.id}" dataList="${ubicaciones}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.ubicacion', default: 'Elige una ubicacion')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.scorecardmetadata" default="Metadata Tarjeta Puntuacion"/>:</label>
							<div class="block_content">
								<golf:select name="scorecardmetadata" value="${campo?.scorecardmetadata?.id}" dataList="${scorecardsMetadata}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.scorecardmetadata', default: 'Elige una metadata de Tarjeta de Puntuacion')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.blancas" default="Tarjeta Blancas"/>:</label>
							<div class="block_content">
								<golf:select name="blancas" value="${campo?.blancas?.id}" dataList="${scorecards}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.blancas', default: 'Elige la Tarjeta de Puntuacion de las blancas')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.amarillas" default="Tarjeta Amarillas"/>:</label>
							<div class="block_content">
								<golf:select name="amarillas" value="${campo?.amarillas?.id}" dataList="${scorecards}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.amarillas', default: 'Elige la Tarjeta de Puntuacion de las amarillas')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.rojas" default="Tarjeta Rojas"/>:</label>
							<div class="block_content">
								<golf:select name="rojas" value="${campo?.rojas?.id}" dataList="${scorecards}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.rojas', default: 'Elige la Tarjeta de Puntuacion de las rojas')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.azules" default="Tarjeta Azules"/>:</label>
							<div class="block_content">
								<golf:select name="azules" value="${campo?.azules?.id}" dataList="${scorecards}" key="true" cssClass="chzn-select-deselect" style="width:350px;" placeHolderValue="${message(code: 'campo.edit.azules', default: 'Elige la Tarjeta de Puntuacion de las azules')}" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.latitud" default="Latitud"/>:</label>
							<div class="block_content">
								<input type="text" name="latitud" value="${fieldValue(bean: campo, field: "latitud")}" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.longitud" default="Longitud"/>:</label>
							<div class="block_content">
								<input type="text" name="longitud" value="${fieldValue(bean: campo, field: "longitud")}" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.linkCampo" default="Link Campo"/>:</label>
							<div class="block_content">
								<input type="text" name="linkCampo" value="${fieldValue(bean: campo, field: "linkCampo")}" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.linkExtra" default="Link Extra"/>:</label>
							<div class="block_content">
								<input type="text" name="linkExtra" value="${fieldValue(bean: campo, field: "linkExtra")}" class="i-format" required="required" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.telefono" />:</label>
							<div class="block_content">
								<input type="text" name="telefono" value="${fieldValue(bean: campo, field: "telefono")}" class="i-format"  />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.direccion" />:</label>
							<div class="block_content">
								<input type="text" name="direccion" value="${fieldValue(bean: campo, field: "direccion")}" class="i-format"  />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.emailContacto" />:</label>
							<div class="block_content">
								<input type="text" name="emailContacto" value="${fieldValue(bean: campo, field: "emailContacto")}" class="i-format"  />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					
					
					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.descripcion" default="Descripcion"/>:</label>
							<div class="block_content">
								<textarea type="textarea" rows="1" cols="20" name="descripcion" class="default">${fieldValue(bean: campo, field: "descripcion")}</textarea>
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.descripcionQuotted" default="Descripcion Quotted"/>:</label>
							<div class="block_content">
								<textarea type="textarea" rows="1" cols="5" name="descripcionQuotted" class="default">${fieldValue(bean: campo, field: "descripcionQuotted")}</textarea>
							</div>
						</div>
						<div class="clear"></div>
					</section>

					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.comoLlegar" default="Descripcion"/>:</label>
							<div class="block_content">
								<textarea type="textarea" rows="1" cols="5" name="comoLlegar" class="default">${fieldValue(bean: campo, field: "comoLlegar")}</textarea>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.coordenadasGps" default="Coordenadas GPS"/>:</label>
							<div class="block_content">
								<input type="text" name="coordenadasGps" value="${fieldValue(bean: campo, field: "coordenadasGps")}" class="i-format" />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.normasLink" default="Link Normas"/>:</label>
							<div class="block_content">
								<input type="text" name="normasLink" class="i-format" value="${fieldValue(bean: campo, field: "normasLink")}"  />
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.normasSeccion" default="Normas"/>:</label>
							<div class="block_content">
								<textarea type="textarea" rows="1" cols="20" name="normasSeccion" class="default">${fieldValue(bean: campo, field: "normasSeccion")}</textarea>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<hr />					
					<section class="form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.notasReserva" />:</label>
							<div class="block_content">
								<textarea type="textarea" rows="1" cols="20" name="notasReserva" class="default">${fieldValue(bean: campo, field: "notasReserva")}</textarea>
								<span class="input_tips">Son notas a poner cuando se envien emails de confirmacion.</span>
							</div>
						</div>
						<div class="clear"></div>
					</section>
					<hr />
					
					
					
					<section class="form_row">
						<label><g:message code="com.clickandgolf.Campo.imagenPrincipal" default="Imagen Principal"/>:</label>
						<g:if test="${campo.imagenPrincipal}">
							<img src="${wthr.staticResource(id:"${campo.id}",nombre:"${campo.imagenPrincipal}")}" alt="${campo.imagenPrincipal}" title="${campo.imagenPrincipal}" />
							<div class="clear"></div>
							<label><g:message code="com.clickandgolf.Campo.imagenPrincipal.cambiar" default="Cambiar Imagen Principal"/>:</label>
							<div class="block_content">
								<input type="file" id="imagenPrincipal" name="imagenPrincipal" value="${fieldValue(bean: campo, field: "imagenPrincipal")}" class="i-format"/>
								<span class="input_tips">Medidas axb.</span>
							</div>
							<div class="block_content">
								<input type="checkbox" name='imagenPrincipalBorrar' id='imagenPrincipalBorrar' /> <label style="display: inline; padding-left: 10px;"><g:message code="com.clickandgolf.Campo.imagen.borrar" default="Eliminar imagen asociada"/></label>
								<div class="clear"></div>
							</div>
						</g:if>
						<g:else>
							<!-- No hay imagen principal al momento -->
							<div class="block_content">
								<input type="file" id="imagenPrincipal" name="imagenPrincipal" value="${fieldValue(bean: campo, field: "imagenPrincipal")}" class="i-format"/>
								<span class="input_tips">Medidas axb.</span>
							</div>
						</g:else>
						<div class="clear"></div>
					</section>
	
					<!-- El hr va a dejar una separacion -->
					<hr />

					<section class="form_row">
						<label><g:message code="com.clickandgolf.Campo.imagenPromocionGold" default="Imagen Promocion Gold"/>:</label>
						<g:if test="${campo.imagenPromocionGold}">
							<img src="${wthr.staticResource(id:"${campo.id}",nombre:"${campo.imagenPromocionGold}")}" alt="${campo.imagenPromocionGold}" title="${campo.imagenPromocionGold}" />
									
							<div class="clear"></div>
							<label><g:message code="com.clickandgolf.Campo.imagenPromocionGold.cambiar" default="Cambiar Imagen Promocion Gold"/>:</label>
							<div class="block_content">
								<input type="file" id="imagenPromocionGold" name="imagenPromocionGold" value="${fieldValue(bean: campo, field: "imagenPromocionGold")}" class="i-format"/>
								<span class="input_tips">Medidas axb.</span>
							</div>
							<div class="block_content">
								<input type="checkbox" name='imagenPromocionGoldBorrar' id='imagenPromocionGoldBorrar' /> <label style="display: inline; padding-left: 10px;"><g:message code="com.clickandgolf.Campo.imagen.borrar" default="Eliminar imagen asociada"/></label>
								<div class="clear"></div>
							</div>
						</g:if>
						<g:else>
							<!-- No hay imagen de Promocion Gold al momento -->
							<div class="block_content">
								<input type="file" id="imagenPromocionGold" name="imagenPromocionGold" value="${fieldValue(bean: campo, field: "imagenPromocionGold")}" class="i-format"/>
								<span class="input_tips">Medidas axb.</span>
							</div>
						</g:else>
						<div class="clear"></div>
					</section>

					<!-- El hr va a dejar una separacion -->
					<hr />

					<section class="form_row">
						<label><g:message code="com.clickandgolf.Campo.imagenPromocionSilver" default="Imagen Promocion Silver"/>:</label>
						<g:if test="${campo.imagenPromocionSilver}">
							<img src="${wthr.staticResource(id:"${campo.id}",nombre:"${campo.imagenPromocionSilver}")}" alt="${campo.imagenPromocionSilver}" title="${campo.imagenPromocionSilver}" />
							<div class="clear"></div>
							<label><g:message code="com.clickandgolf.Campo.imagenPromocionSilver.cambiar" default="Cambiar Imagen Promocion Silver"/>:</label>
							<div class="block_content">
								<input type="file" id="imagenPromocionSilver" name="imagenPromocionSilver" value="${fieldValue(bean: campo, field: "imagenPromocionSilver")}" class="i-format"/>
								<span class="input_tips">Medidas axb.</span>
							</div>
							<div class="block_content">
								<input type="checkbox" name='imagenPromocionSilverBorrar' id='imagenPromocionSilverBorrar' /> <label style="display: inline; padding-left: 10px;"><g:message code="com.clickandgolf.Campo.imagen.borrar" default="Eliminar imagen asociada"/></label>
								<div class="clear"></div>
							</div>
						</g:if>
						<g:else>
							<!-- No hay imagen de Promocion Silver al momento -->
							<div class="block_content">
								<input type="file" id="imagenPromocionSilver" name="imagenPromocionSilver" value="${fieldValue(bean: campo, field: "imagenPromocionSilver")}" class="i-format"/>
								<span class="input_tips">Medidas axb.</span>
							</div>
						</g:else>
						<div class="clear"></div>
					</section>

					<!-- El hr va a dejar una separacion -->
					<hr />

					<section class="form_row">
						<label><g:message code="com.clickandgolf.Campo.imagenSecundaria" default="Imagen Secundaria"/>:</label>
						<g:if test="${campo.imagenSecundaria}">
							<img src="${wthr.staticResource(id:"${campo.id}",nombre:"${campo.imagenSecundaria}")}" alt="${campo.imagenSecundaria}" title="${campo.imagenSecundaria}" />
							<div class="clear"></div>
							<label><g:message code="com.clickandgolf.Campo.imagenSecundaria.cambiar" default="Cambiar Imagen Secundaria"/>:</label>
							<div class="block_content">
								<input type="file" id="imagenSecundaria" name="imagenSecundaria" value="${fieldValue(bean: campo, field: "imagenSecundaria")}" class="i-format"/>
								<span class="input_tips">Medidas axb.</span>
							</div>
							<div class="block_content">
								<input type="checkbox" name='imagenSecundariaBorrar' id='imagenSecundariaBorrar' /> <label style="display: inline; padding-left: 10px;"><g:message code="com.clickandgolf.Campo.imagen.borrar" default="Eliminar imagen asociada"/></label>
								<div class="clear"></div>
							</div>
						</g:if>
						<g:else>
							<!-- No hay imagen de Secundaria al momento -->
							<div class="block_content">
								<input type="file" id="imagenSecundaria" name="imagenSecundaria" value="${fieldValue(bean: campo, field: "imagenSecundaria")}" class="i-format"/>
								<span class="input_tips">Medidas axb.</span>
							</div>
						</g:else>
						<div class="clear"></div>
					</section>

					<!-- El hr va a dejar una separacion -->
					<hr />

					<section class="form_row">
						<label><g:message code="com.clickandgolf.Campo.imagenExtra" default="Imagen Extra"/>:</label>
						<g:if test="${campo.imagenExtra}">
							<img src="${wthr.staticResource(id:"${campo.id}",nombre:"${campo.imagenExtra}")}" alt="${campo.imagenExtra}" title="${campo.imagenExtra}" />
							<div class="clear"></div>
							<label><g:message code="com.clickandgolf.Campo.imagenExtra.cambiar" default="Cambiar Imagen Extra"/>:</label>
							<div class="block_content">
								<input type="file" id="imagenExtra" name="imagenExtra" value="${fieldValue(bean: campo, field: "imagenExtra")}" class="i-format"/>
								<span class="input_tips">Medidas axb.</span>
							</div>
							<div class="block_content">
								<input type="checkbox" name='imagenExtraBorrar' id='imagenExtraBorrar' /> <label style="display: inline; padding-left: 10px;"><g:message code="com.clickandgolf.Campo.imagen.borrar" default="Eliminar imagen asociada"/></label>
								<div class="clear"></div>
							</div>
						</g:if>
						<g:else>
							<!-- No hay imagen de Extra al momento -->
							<div class="block_content">
								<input type="file" id="imagenExtra" name="imagenExtra" value="${fieldValue(bean: campo, field: "imagenExtra")}" class="i-format"/>
								<span class="input_tips">Medidas axb.</span>
							</div>
						</g:else>
						<div class="clear"></div>
					</section>

					<!-- El hr va a dejar una separacion -->
					<hr />
					
					<section class="form_row">
						<div>
							<div class="block_content">
								<input class="button darkgray textcenter" type="submit" value='${message(code: "com.clickandgolf.Campo.modificar")}' />
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
