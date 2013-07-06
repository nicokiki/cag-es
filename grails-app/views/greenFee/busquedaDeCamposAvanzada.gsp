<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Busca de manera avanzada tus green fees por ubicaci&#243;n, fecha, precio y tipo. Elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />	
		
		<title>Click &amp; Golf | <g:message code="search.campos.encontrados.avanzada.label" /></title>
		
		<r:require module="core"/>
		
		<r:script disposition="head">
			$(document).ready(function() {
			    setupGridAjax();
			});
			
			// Turn all sorting and paging links into ajax requests for the grid
			function setupGridAjax() {
				$("#grid").find("th.sortable a").live('click', function(event) {
				event.preventDefault();
				var url = $(this).attr('href');
				
				var closestDiv = $(this).closest('div');
				
				$(closestDiv).html($("#spinner").html());
				
				$.ajax({
				type: 'POST',
				url: url,
				success: function(data) {
				$(closestDiv).fadeOut('fast', function() {$(this).html(data).fadeIn('slow');});
				}
				})
				});			
			

				$("#pagination").find("a").live('click', function(event) {
				event.preventDefault();
				var url = $(this).attr('href');
				
				var closestDiv = $(this).closest('div');
				
				$(closestDiv).html($("#spinner").html());
				
				$.ajax({
				type: 'POST',
				url: url,
				success: function(data) {
				$(closestDiv).fadeOut('fast', function() {$(this).html(data).fadeIn('slow');});
				}
				})
				});			
			}
			
			
		</r:script>
		
		<g:javascript>
			$("#fecha").datepicker({
        		minDate: new Date() 
    		});
    		
    		if ($("#fecha").val().length == 0) {
    			var myDate = new Date();
				myDate.setDate(myDate.getDate()+1);
				
			 	var month = myDate.getMonth() + 1;
			 	var mm = month < 10 ? "0" + month : month;
			    var day = myDate.getDate();
				var dd = day < 10 ? "0" + day : day;
			    var year = myDate.getFullYear();
				
    			$("#fecha").val(dd + "/" + mm + "/" + year);
    		}		
    		
			var showLocalSpinner = function() {
			    $('#spinnerLocal').show();
			}
			
			var hideLocalSpinner = function() {
			    $('#spinnerLocal').hide();
			}
			    		
			function runThis() {
				jQuery.ajax({
           			type:'POST',
                  	data:$('#buscarGreenFeeForm').serialize(),        
                  	url:"${g.createLink(controller:'greenFee',action:'busquedaDeCamposAvanzada')}",
                  	beforeSend:function() {
                  		$('#grid').html();
                  		showLocalSpinner();
                  	},
                  	complete:function() {
                  		hideLocalSpinner();
                  	},
					success:function(data,textStatus) {
						$('#grid').html(data).slideDown('slow');
                   	},
     			})		                  	
			}
			
			$(document).ready(function() {
			    runThis();
			});
    			
		</g:javascript>
		
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">
				
			<section class="page_title" style="margin-bottom: 5px;">
				<div class="title"><g:message code="search.campos.encontrados.avanzada.label" /></div>
			</section>	
				
			<section class="grid_12 margin_top_0">
				
				<g:form class="form_place margin_top_0" method="POST" id='buscarGreenFeeForm' name="buscarGreenFeeForm" url="[controller:'greenFee', action:'busquedaDeCamposAvanzada']">
					<section class="grid_3 form_row">
                      	<div>
                           	<label><g:message code="home.lugar.label"/>:</label>
                            <div class="block_content">
								<golf:selectUbicaciones name="ubicacion" value="${nombreUbicacion}" dataList="${ubicaciones}" key="true" cssClass="chzn-select-deselect" style="width:215px;" placeHolderValue="${message(code: 'campo.edit.ubicacion', default: 'Elige una ubicacion')}" todosLabel="${message(code: 'home.ubicacion.todo.label', default: 'Todo')}" />
							</div>
                      	</div>
                  	</section>
					<section class="grid_2 form_row">
						<div>
							<label><g:message code="com.clickandgolf.Campo.tipo" default="Tipo"/>:</label>
							<div class="block_content">
								<golf:select name="tipo" value="" dataList="${['Pitch & Putt', 'Golf']}"  cssClass="chzn-select-deselect" style="width:120px;" placeHolderValue="${message(code: 'campo.edit.tipo', default: 'Elige un tipo')}" />
							</div>
						</div>
                  	</section>
					<section class="grid_2 form_row">
						<div>
							<label><g:message code="greenfee.found.hoyos" default="Hoyos"/>:</label>
							<div class="block_content">
								<golf:select name="hoyos" value="" dataList="${['18', '9']}"  cssClass="chzn-select-deselect" style="width:80px;" placeHolderValue="${message(code: 'greenfee.found.hoyos')}" />
							</div>
						</div>
                  	</section>
					<section class="grid_2 form_row">
                        <div>
                            <label><g:message code="home.fecha.label"/>:</label>
                            <div class="block_content" style="width: 40%;">
                            	<input type="text" id="fecha" name="fecha" class="i-format" placeholder="dd/mm/yyyy" style="height: 10px; width: 100px;" />
                        	</div>
                    	</div>
					</section>                  	
                  	
					<section class="grid_2 form_row">
						<div>
							<label><g:message code="search.avanzada.precio.maximo" />:&nbsp;</label><label id="maxPrecioLabelId">${precioMax} &euro;</label>
							<div class="block_content">
								<input name="precioMax" type="range" min="5" max="250" value="${precioMax}" step="5" onchange="showValue(this.value)" />
								<script type="text/javascript">
									function showValue(newValue)
									{
										document.getElementById("maxPrecioLabelId").innerHTML=newValue + " &euro;";
									}
								</script> 
							</div>
						</div>
                  	</section>

					<section class="grid_2 form_row rightAligned" >
						<div>
							<label>&nbsp;</label>
							<div class="block_content rightAligned">
								<input 	class="button blue middleAligned pointeredMouse" type='button' 
										id="submit" value='${message(code: "search.avanzada.refrescar")}'
										onclick="runThis()"	
								/>
							</div>
						</div>
					</section>                  	
				</g:form>

				
			</section>

			<g:if test='${flash.message}'>
				<div class="errorMessage">${flash.message}</div>
			</g:if>
					
			<section class="grid_12">
			
				<%-- Esto esta aqui abajo asi tiene una usabilidad mejor --%>
				<div id="spinnerLocal" class="spinner" style="display: none;">
					<img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
				</div>
			
				<div id="grid">
   					<g:render template="greenFees" model="model" />
  				</div>
			</section>
			
		</section> <!-- End section .container layout -->
		
	</body>

</html>
