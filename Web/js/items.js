$(window).on('load', function() {
	  $('#listGood').load("/myfurniture/shop/items");
	var refresh = function() {
        $.ajax({
            cache: false,
            success: function() {
                
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) { 
            	
            	$('#listGood').load('/myfurniture/shop/items');
                setTimeout(function() {
                    refresh();
                },  60000   );
            }  
        
        });
        return false;
    };
    refresh(); 
    });



  