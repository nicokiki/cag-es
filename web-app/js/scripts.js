/* ====== MENU ====== */
$(document).ready(function() {
var site = function() {
	this.navLi = $('#nav li').children('ul').hide().end();
	this.init();
};

site.prototype = {
 	
 	init : function() {
 		this.setMenu();
 	},
 	
 	setMenu : function() {
 	
 	$.each(this.navLi, function() {
 		if ( $(this).children('li ul li ul')[0] ) {
 			$(this)
 				.append('<span />')
 				.children('span')
 					.addClass('menuChildren')
 		}
 	});
 	
 		this.navLi.hover(function() {
 			// mouseover
			$(this).find('> ul').stop(true, true).fadeIn({duration:200});
			$(this).children('a:first').addClass("hov");
 		}, function() {
 			// mouseout
 			$(this).find('> ul').stop(true, true).hide(); 
			$(this).children('a:first').removeClass("hov");			
		});
 		
 	}
 
};
new site();
});

/* ====== LOGIN REGISTER  ====== */
$(".lr_content").hide();
$(".lr_button").click(function(){
	$(".lr_content").slideToggle(300);
});
/* ====== LIGHTBOX ====== */
  jQuery(document).ready(function($){
    $('.lightbox').lightbox();
  });

/* ======== Thumb Scroll ======== */
(function($) {
	$(document).ready(function() {
		
		$("#home_pf_items").ThumbScroll({
			position: '0',
			speed: '10',
			marginLeft: '5',
			marginRight: '5'
		});
		
	});
})(jQuery);

/* ====== TABS ====== */
$(document).ready(function() {

	$(".tab_content").hide(); 
	$("ul.tabs li:first").addClass("active").show();
	$(".tab_content:first").show(); 

	$("ul.tabs li").click(function() {

		$("ul.tabs li").removeClass("active");
		$(this).addClass("active");
		$(".tab_content").hide();

		var activeTab = $(this).find("a").attr("href"); 
		$(activeTab).fadeIn();
		return false; 
	});

});


/* ====== ACCORDION ====== */
$(document).ready(function() {
	 
	$('.accordion_button').click(function() {
	
		$('.accordion_button').removeClass('acdn_on');
	 	$('.accordion_container').slideUp('normal');
		
		if($(this).next().is(':hidden') == true) {
			$(this).addClass('acdn_on');
			$(this).next().slideDown('normal');
			$('.accordion_button span').addClass('minus');
		 } 
		  
	 });
	$('.accordion_button').mouseover(function() {
		$(this).addClass('acdn_over');
		
	}).mouseout(function() {
		$(this).removeClass('acdn_over');										
	});
	;
		
	$('.accordion_container').hide();

});


/* ====== TOGGLE  ====== */
$(".toggle_content").hide();

$(".toggle_block h4").click(function(){
	$(this).toggleClass("tgg_close").next().slideToggle("medium");
});

/* ====== ALERTS ====== */
$('.alert').click(function() {
	 	$(this).hide('normal');		  
});

/* =========== FOOTER MENU ============ */
  $('.vnav li a').hover(function() {
	$(this).animate({ paddingLeft: '10px' }, 150);
  }, function() {
	$(this).animate({ paddingLeft: '0' }, 150); 	
  });

 /* ========== IMAGE HOVER ======== */
$(function() {
	$('.img_pf_hover').hover(function(){
		$('img',this).animate({"opacity": "0.6"},{queue:true,duration:200});
		
	}, function() {
		$('img',this).animate({"opacity": "1"},{queue:true,duration:300});
	});
});	

 /* ========== FLICKR IMAGE HOVER ======== */
$(function() {
	$('.flickr_photos img').hover(function(){
		$(this).animate({"opacity": "0.6"},{queue:true,duration:200});
		
	}, function() {
		$(this).animate({"opacity": "1"},{queue:true,duration:300});
	});
});

/* ======== FOOTER SOCIAL ICONS ======*/
$(function() {
	$('.social a').hover(function(){
		$(this).animate({"opacity": "0.6"},{queue:true,duration:200});
		
	}, function() {
		$(this).animate({"opacity": "1"},{queue:true,duration:300});
	});
});

/* ======== FORMS ======*/
$(function(){
    $("input:checkbox, input:radio, input:file").uniform();
});
$(".chzn-select").chosen(); 
$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 


/*========= PORTFOLIO ========*/
	var galleryData = jQuery(".portfolio_content").clone();
	
	jQuery('.filter li a').click(function() {
		jQuery(".filter li a").removeClass("active_cat").stop();
		
		var filterClass = jQuery(this).attr('id');
		
		if (filterClass == 'all') {
			var filteredData = galleryData.find('.item');
		} else {
			var filteredData = galleryData.find('.item[data-type~=' + filterClass + ']');
		}
		jQuery(".portfolio_content").quicksand(filteredData, {
			adjustHeight: 'dynamic',
			duration: 700,
			easing: 'swing',
			enhancement: function() {
				Cufon.replace('h1, h2, h3, h4, h5, h6', { fontFamily: 'Share-Regular', hover: true }),
				
				$('.lightbox').lightbox(),
				
				$('.img_pf_hover').hover(function(){
					$('img',this).animate({"opacity": "0.6"},{queue:true,duration:150});
					$('.img_pf_icon, .img_pf_icon2, .img_pf_text',this).css("display","block");
				}, function() {
					$('img',this).animate({"opacity": "1"},{queue:true,duration:300});
					$('.img_pf_icon, .img_pf_icon2, .img_pf_text',this).css("display","none");
				});
			}
		});
		jQuery(this).addClass("active_cat").stop();
		
		return false;
	});