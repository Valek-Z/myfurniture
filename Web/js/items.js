
$(window).on('load', function() {
    var refresh = function() {
        $.ajax({
            cache: false,
            success: function() {
                $('#listGood').load('/myfurniture/shop/items');
                setTimeout(function() {
                    refresh();
                },  300000   );
            }
        });
        return false;
    };
    refresh();
    return false;
});



       
       
