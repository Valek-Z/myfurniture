$(document).ready(function(){

	$("#sendGoods").click(function () {
		
		$.ajax("/myfurniture/buyService", {
			   type: "POST",
			   data: $('#goodsToBuy').serialize()
		})
			.done(function(response) {
				
				window.location.replace ( "/myfurniture/shop/success");
			   
			})
			.fail(function(jqXHR, textStatus, errorThrown) {
				
			
				window.location.replace("/myfurniture/shop/failure");
			});
		
					
		return false;
	  });
});
