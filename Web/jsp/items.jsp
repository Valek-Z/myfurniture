<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.shop.entity.Goods" %>


 
 <%

  List <Goods> goods = (List <Goods> ) request.getAttribute("listGoods");
 
   out.println( "  <form class=\"FormBasket\"  >"); 
   
    if ( goods != null && !goods.isEmpty() ) {	
    	
     out.println("<p> <span class=\"name\" >" + "Name furniture  - price" + " </span> </p>");	
     
	  for (Goods g : goods) {	
		  
	 	 out.println("<p> <input class=\"checkbox\"  id="+g.getName() +" type=\"checkbox\"  name=\"items\" value=\"" 
	     +g.getArticle() + "\">" +"<label for=" +g.getName()    +" \">" +g.getName() + " - " + g.getPrice() +" </label> </p> " );
	    		 
		 }	
	  out.println("  <input class=\"Basket\"  id=\"Basket\" type = \"button\" value = \"To Basket\" /> </form> ");  
	 } 
    
   else out.println("<div class=\"Sorry\">\n"
	 +"   <span onclick=\"this.parentElement.style.display='none'\"\n" 
     +"   class=\"no furniture \">×</span>\n" 
	 +"   <h5>There are no furniture to buy , sorry !</h5>\n" +"</div>") ;
	    		 
 
	 %>
 <script type="text/javascript">
$(document).ready(function(){
	$('#Basket').on ( 'click' ,function (e) {
		 e.preventDefault(); 
		$.ajax( {
			 type: "GET",
			  data: $('#FormBasket').serialize(),
			  beforeSend: function(jqXHR, settings) {				
				var url="/myfurniture/shop/basket?items=";
				  var i = 0;
                      $("input:checked").each(function(){
                         url +=  $(this).val() + ",";
                                   i++;
                             });
                      if(url.length>0){
                    	  url=url.substring(0,url.length-1)
                    	  };				
			       
			        parent.window.location.href = url;			    		        
			        $('#FormBasket').submit();
                   }                 
        });
		return false;
    });
});

</script>
	
      


      
