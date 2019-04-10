package com.shop.servise;

import com.shop.entity.Goods;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class ReadFile
 * Create an entity sheet loaded sequentially in a predetermined order from the file  type goods.csv on the server Tomcat
 * in dir
 * "/data"
 * it is necessary to ensure the availability of the file for the program.
 * and observe the sequence of recording the types of the name, article, price and split " , "
 */

public class ReadFile implements Runnable{
	
	private static ReadFile instance;
	
	 private static List < Goods > listgoods = new LinkedList <> ( );
	
	public static ReadFile getInstance ( ) {
		if ( instance == null ) {
			instance = new ReadFile ( );
		}
		return instance;
	}
	
	
	private void readGoods ( ) {
		
		
			listgoods.clear(); 
		
		
		String webAppbase = System.getProperty ( "catalina.base" );
		
		String dir = webAppbase + "/data/";
		
		try ( Scanner scanner = new Scanner ( new File ( dir, "goods.csv" ) ) ) {
			
			while ( scanner.hasNext ( ) ) {
				
				Goods goods = null;
				
				String[] arr = scanner.nextLine ( ).split ( "," );
				
				try {
					goods = new Goods ( );
					
					for ( int i = 0 ; i < arr.length ; i++ ) {
						
						if ( i == 0 ) {
						
							goods.setName ( arr[ i ] );
							
							
						} else if ( i == 1 ) {
							
							goods.setArticle ( Long.parseLong ( arr[ i ] ) );
							
							
						} else if ( i == 2 ) {
							
							goods.setPrice ( Long.parseLong ( arr[ i ] ) );
							
						}
						
					}
				} catch ( NumberFormatException n ) {
					
					System.out.println ( " ******** Please use only numerical values for article, price  number.  This number " +
							                     "does not fit -" + Arrays.toString ( arr ) );
					n.printStackTrace ( );
				}
				
				if ( goods.getArticle ( ) != 0 && goods.getPrice ( ) != 0   && !goods.getName().isEmpty() ) {
					
					( ( LinkedList < Goods > ) listgoods ).addLast ( goods );
					
				}
			}
		} catch ( FileNotFoundException fnfe ) {
			System.out.println (  "Create file \"goods.csv\" in the Tomcat server \"/data\" folder.");
			System.out.println (  " Fill up product catalog in such a sequence (name, article, price) using comma - , - ");
			System.out.println (  "  In the Article / Price only use the numbers");
			System.out.println (  "    The catalog WEB/data has an example of a completed file. "     );
			System.out.println (  "  !!!!!!!!   And restart Tomcat! "     );
			fnfe.printStackTrace ( );
		}
		
		
	
	}

	@Override
	public void run() {	
		
		readGoods();
		
	}
	
	 public void startReadFile () {	
		 
		 
		  ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();		  
			scheduler.scheduleAtFixedRate(ReadFile.getInstance(), 0, 1, TimeUnit.MINUTES);
	 }
	 
	
	  public List<Goods> getListgoods() {  		

			if (listgoods.isEmpty()) {
				
				readGoods();
				
			}
			
		  return listgoods;
		  
	  }


	
	
	  
	 
	
}
