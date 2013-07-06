<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US"> 

	<head>
		<meta name="layout" content="main"/>

		<meta name="description" content="Reserva green fees de golf y pitch &amp; putt online, elige entre una variada cantidad de campos de golf y pitch &amp; putt en Espa&#241;a. Ahorra hasta un 75% en green fees y sin costos extra." />	
		
		<r:require module="core"/>

		<g:javascript>
			<g:if test="${promocionesGoldSize > 0}">
				/* Esto se ejecuta al final entonces se ejecutara si o si */ 
				$('.box_skitter_slider2').skitter({dots: true});
			</g:if>
		
			
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
    		
		</g:javascript>
	</head>

	<body>
	
		<!--======= BEGIN OF header (.main_header) ======-->
		<g:render template="/shared/header" />
		<!--======= END OF header (.main_header) ======-->
		
		<section class="container layout">

			<div class="grid_12">
				<img src="${resource(dir:'images/assets',file:'proximamente_final.png')}" class="home_static_image border" alt="Proximamente ..." />
				<div class="clear"></div>
			</div>
	
		</section> <!-- End section .container layout -->
		
	</body>

</html>
