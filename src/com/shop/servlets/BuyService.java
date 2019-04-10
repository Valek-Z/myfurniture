package com.shop.servlets;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.entity.Goods;
import com.shop.servise.ReadFile;

/**
 * Servlet implementation class buyService
 * This servlet implements the creation of a shopping list for the article of goods and recording they to file on the
 * server,
 * response to a client with different  HTTP statuses depending on processing results
 * you must have permission to write files to the directory
 */
@WebServlet ( name = "BuyService", asyncSupported = true, urlPatterns = { "/buyService" } )

public class BuyService extends HttpServlet {
	
	private static final long serialVersionUID = 3L;
	
	public BuyService ( ) {
		super ( );
		
	}
	
	
	protected void doPost ( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
			                                                                                          IOException {
		
		String webAppbase = System.getProperty ( "catalina.base" );
		String dir = webAppbase + "/data/";	
			
		String[] articleToFile = request.getParameterValues ( "goodsToBuy" );
		
		if ( articleToFile.length != 0 ) {				
		
			List < String > errorArticle = new ArrayList < String > ( );
			 Set< Long >  rightArticle =  new HashSet< Long > ();
			
			for ( String goodsToArticle : articleToFile) {
				try {
					rightArticle.add (Long.parseLong ( goodsToArticle));
					} catch ( NumberFormatException n ) {
						errorArticle.add(goodsToArticle);
						}
				}
			
			List < String > goodsToFile = new ArrayList < String > ( );
			
			List < String > errorArticleToFile = new ArrayList < String > ( );
			
			Set < Long > setGoodArticle = ReadFile.getInstance().getListgoods() 
					.stream ( )
					.map ( Goods :: getArticle )
					.collect ( Collectors.toSet ( ) );
			
			
			if ( !rightArticle.isEmpty() ) {	
				
				goodsToFile = rightArticle
						 .stream()
						 .filter(g -> setGoodArticle.contains (  g  ) )
						 .map(Object::toString)
						 .collect(Collectors.toList());
				
				errorArticleToFile = rightArticle
						 .stream()
						 .filter(g -> !setGoodArticle.contains (  g  ) )
						 .map(Object::toString)
						 .collect(Collectors.toList());
				}					
			
			
				
				errorArticleToFile.addAll( errorArticle );			
			
			
			
			if ( ! goodsToFile.isEmpty ( ) ) {
				try {
					File file = new File ( dir, "order-" + getCurrentTime ( ) + ".csv" );
					file.createNewFile ( );
					FileWriter fileWriter = new FileWriter ( file );
					PrintWriter printWriter = new PrintWriter ( fileWriter );
					for ( String article : goodsToFile ) {
						printWriter.println ( article );
					}
					response.setStatus ( HttpServletResponse.SC_CREATED );
					printWriter.close ( );
					
				} catch ( Exception e ) {
					
					response.setStatus ( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
				}
			}
			if ( ! errorArticleToFile.isEmpty ( ) ) {
				
				response.setStatus ( HttpServletResponse.SC_BAD_REQUEST );
				
			}
		}
	}
	
	
	private static String getCurrentTime ( ) {
		
		SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy_MM_dd_HH_mm_ss" );
		Date resultDate = new Date ( System.currentTimeMillis ( ) );
		return sdf.format ( resultDate );
	}
	
}
