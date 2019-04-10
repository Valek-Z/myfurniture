package com.shop.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.servise.ReadFile;


/**
 * Servlet implementation class Items
 * This servlet implements the creation of a list of goods entities and the transfer of data to the client
 *
 * @author Valentin Zhuravel
 */

@WebServlet ( name = "items", urlPatterns = { "/shop/items" } )
@WebListener
public class Items extends HttpServlet implements ServletContextListener {
	private static final long serialVersionUID = 1L;
	
	public Items ( ) {
		
		super ( );
		
		
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 * response)
	 */
	
	protected void doGet ( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
			                                                                                         IOException {			
			
		request.setAttribute ( "listGoods", ReadFile.getInstance().getListgoods() );
		RequestDispatcher dispatcher = request.getRequestDispatcher ( "/jsp/items.jsp" );
		dispatcher.forward ( request, response );
		
	}
	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		System.out.println ( "  To start the app create csv file in the Tomcat / data server folder, fill up product catalog in such a sequence (name, article, price) using  comma - , - ");
	
		ReadFile.getInstance().startReadFile();
		
	}
	
}
