$(document).ready(function(){

// =============================
// = Converting SELECTs to ULs =
// =============================
//Initial setup
var selectClass = "convertToUl"; //the class of SELECT element to be converted [select]
var ulWrapClass = "fancyDrpDwn"; //choose a class for the genereated's list wrapper [div]
var ulClass = "exSelect"; //choose a class for the genereated's list [ul]
var selectedItemClass = "selectedFromList"; //choose a class for the selected item box [span]


function convertSelectToUl(theSelectID){
	$('#' + theSelectID + ' option').each(function(index) {
	 	var  lista = '<li>' + $(this).text() + "</li>";
		$("."+ theSelectID +"").append(lista);
	});
	$('#' + theSelectID).remove();
}

function findTheSelect () {
	$("[class*="+selectClass+"]").each(function(){
		var theSelectID = $("." + selectClass).attr("id");
		var theSelectName = $("." + selectClass).attr("name");
		var selectedItem = $('#' + theSelectID + ' :selected').text();

		$("." + selectClass).parent().append("<div class='"+ulWrapClass+"'><input class='hidval' name='"+theSelectName+"' type='hidden' value='"+selectedItem+"'><span class='"+selectedItemClass+"'><b>" + selectedItem + "</b></span><ul class='"+ ulClass +" "+ theSelectID + "'></ul></div>");
		
		convertSelectToUl(theSelectID)
});	

}
findTheSelect ()


/* the dropdown effect */
$('.'+ ulClass).hide(); //hiding the already converded dropdowns
$('.'+ selectedItemClass).click(function(){
		$(this).parent().find("ul").show();
		$(this).parent().find("ul").mouseover(function(){
			$(this).parent().find("ul").show();
		}).mouseout(function(){
			$(this).parent().find("ul").hide();
			
		});
	});


//clicking on the dropdown's elements
$('.'+ ulClass +' li').click(function(){
	clickedItem = $(this).text();
	$(this).parent().parent().find(".hidval").val(clickedItem);
	$(this).parent().parent().find("." + selectedItemClass + " b").html(clickedItem);
})


// END Convert a SELECT to UL

});