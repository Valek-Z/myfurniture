package com.shop.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.entity.Goods;
import com.shop.servise.ReadFile;


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
		
		String goodsToBuy = request.getParameter ( "items" );
		if ( goodsToBuy != "" ) {
			
			String[] goodsToBuyGet = goodsToBuy.split ( "," );
			
			List < String > errorArticleToBuy = new ArrayList < String > ( );
			Set < Long > rightArticleToBuy = new HashSet< Long > ()   ;
			
			for ( String articleToParse : goodsToBuyGet) {
				try {
					rightArticleToBuy.add (Long.parseLong ( articleToParse));
					
					} catch ( NumberFormatException n ) {
						errorArticleToBuy.add(articleToParse);
						}
				}
			
			List < String > goodsToBuyServise = new ArrayList < String > ( );
			
			List < String > errorgood = new ArrayList < String > ( );
			
			Set < Long > setGoodArticle = ReadFile.getInstance().getListgoods() 
					.stream ( )
					.map ( Goods :: getArticle )
					.collect ( Collectors.toSet ( ) );
			
			if ( ! rightArticleToBuy.isEmpty() ) {	
				
				 goodsToBuyServise = rightArticleToBuy
						 .stream()
						 .filter(g -> setGoodArticle.contains (  g  ) )
						 .map(Object::toString)
						 .collect(Collectors.toList());
				
				 errorgood = rightArticleToBuy
						 .stream()
						 .filter(g -> !setGoodArticle.contains (  g  ) )
						 .map(Object::toString)
						 .collect(Collectors.toList());
				}
			
			if ( !errorArticleToBuy.isEmpty() ) {
				
				errorgood.addAll( errorArticleToBuy );
				
			}						
			
			request.setAttribute ( "goodsToBuyServise", goodsToBuyServise );
			request.setAttribute ( "errorgood", errorgood );
		}
		
		
		request.setAttribute ( "listGoods", ReadFile.getInstance().getListgoods());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher ( "/jsp/basket.jsp" );
		dispatcher.forward ( request, response );
	}
}
