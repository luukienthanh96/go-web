(function($) {
	"use strict";
	
	$(document).ready(function() {
		
		$("#pills-guide-tab1").click(function(){
			$("#home-guide-tab1").show();
			$("#home-guide-tab2").hide();
			$("#home-guide-tab3").hide();
		});
		
		$("#pills-guide-tab2").click(function(){
			$("#home-guide-tab1").hide();
			$("#home-guide-tab2").show();
			$("#home-guide-tab3").hide();
		});
		
		$("#pills-guide-tab3").click(function(){
			$("#home-guide-tab1").hide();
			$("#home-guide-tab2").hide();
			$("#home-guide-tab3").show();
		});
	});
	
})(jQuery);