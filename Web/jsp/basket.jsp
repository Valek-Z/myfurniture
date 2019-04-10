<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.LinkedList" %>
    <%@ page import="java.util.List" %>
    <%@ page import= "java.io.IOException" %>
<%@ page import="com.shop.entity.Goods" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>A purchase confirmation page </title>
<link href="/myfurniture/css/myfurniture.css" type="text/css" rel="stylesheet" />
<script src="/myfurniture/js/jquery-3.js" type="text/javascript"></script>
<script src="/myfurniture/js/buyservice.js" type="text/javascript"></script> 

</head>
<body>
  <div class="myfurniture">
  
   <div class="confirm">
 <h1>Please confirmation the products you want to buy: </h1>
  </div>  
  
 <div class="goodsToBuyServise">
 
 <%
 
 List <String> goodsToBuyServise = (List <String> ) request.getAttribute("goodsToBuyServise");
 
 List <String> errorgood = (List <String> ) request.getAttribute("errorgood");
 
 LinkedList <Goods> goods = (LinkedList <Goods> ) request.getAttribute("listGoods");
 
 
 if (goodsToBuyServise != null && !goodsToBuyServise.isEmpty()) {
	 out.println( "<form class=\"goodsToBuy\"  id=\"goodsToBuy\" method = \"POST\" action = \"/myfurniture/buyService\"  > <h4> This furniture available to buy </h4>  ");
	 
	 for (String goodsArticle : goodsToBuyServise) {
		 for (Goods g : goods) {
			 if (Long.parseLong(goodsArticle) == ( g.getArticle() ) ){	
				 
			  out.println("<p>   <input class=\"goodsToBuy\"  type=\"hidden\"  id=\""  +g.getName() +"\" name=\"goodsToBuy\" value=\""			 			 
			     +g.getArticle() + "\"> " +g.getName() + " - " + g.getPrice() +" </p> " );
			  }
		  }
	  }	
	 out.println("  <input class=\"sendGoods\"  id=\"sendGoods\" type = \"submit\" value = \"Buy\"  /> </form> "); 
	 
	 
 } else {
	  out.println(   "<div class=\"sorry\">\n"+
				 "   <span onclick=\"this.parentElement.style.display='none'\"\n" 
			   +"   class=\"no furniture \">×</span>\n" 
				 +"   <h5>There are no furniture to buy , sorry !</h5>\n"  +  "<a href=\"/myfurniture/shop\">Let's try again ? </a>" +"</div>") ; 
 }
 

 
 if(errorgood != null && !errorgood.isEmpty()){
	 
 out.println("   <div class=\"errorgoods\">   <h4> This furniture  NOT available to buy </h4> ");
 

 for (String errorgoods : errorgood) {
				  
		  out.println(" <p> <text class=\"errorgood\"  id=\"" 
		  + errorgoods +"\" name=\"errorgoods\" value=\""			 			 
				     +errorgoods + "\"> "  +errorgoods +   " </text> </p>");
		  }

	  
 }
 out.println("   </div>");
 %>

</div>  
</div>  

</body>
</html>
 
     
