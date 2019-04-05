package com.shop.servlets;

import com.shop.entity.Goods;
import com.shop.servise.ReadFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Servlet implementation class buyService
 * this servlet data filters the availability request and gives the client a response based on the result
 */

@WebServlet ( asyncSupported = true, urlPatterns = { "/shop/basket" } )
public class Basket extends HttpServlet {
	private static final long serialVersionUID = 2L;
	
	
	public Basket ( ) {
		super ( );
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet ( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
			                                                                                         IOException {
		
		ReadFile.getInstance ( );
		
		String goodsToBuy = request.getParameter ( "items" );
		if ( goodsToBuy != "" ) {
			String[] goodsToBuyGet = goodsToBuy.split ( "," );
			List < String > goodsToBuyServise = new ArrayList < String > ( );
			List < String > errorgood = new ArrayList < String > ( );
			
			if ( goodsToBuyGet.length != 0 ) {
				Set < Long > goodArticle =
						ReadFile.readGoods ( ).stream ( ).map ( Goods :: getArticle ).collect ( Collectors.toSet ( ) );
				goodsToBuyServise =
						Arrays.stream ( goodsToBuyGet ).filter ( g -> goodArticle.contains ( Long.parseLong ( g ) ) ).collect ( Collectors.toList ( ) );
				errorgood =
						Arrays.stream ( goodsToBuyGet ).filter ( p -> ! goodArticle.contains ( Long.parseLong ( p ) ) ).collect ( Collectors.toList ( ) );
				
			}
			request.setAttribute ( "goodsToBuyServise", goodsToBuyServise );
			request.setAttribute ( "errorgood", errorgood );
		}
		
		
		request.setAttribute ( "listGoods", ReadFile.readGoods ( ) );
		
		RequestDispatcher dispatcher = request.getRequestDispatcher ( "/jsp/basket.jsp" );
		dispatcher.forward ( request, response );
	}
}
