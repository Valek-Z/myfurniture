package com.shop.servise;

import com.shop.entity.Goods;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Class ReadFile
 * Create an entity sheet loaded sequentially in a predetermined order from the file  type .csv on the server Tomcat
 * in dir
 * "/data"
 * it is necessary to ensure the availability of the file for the program.
 * and observe the sequence of recording the types of the name, article, price and split " , "
 */
public class ReadFile {
	
	private static ReadFile instance;
	
	public static ReadFile getInstance ( ) {
		if ( instance == null ) {
			instance = new ReadFile ( );
		}
		return instance;
	}
	
	
	public static LinkedList < Goods > readGoods ( ) {
		
		List < Goods > listgoods = new LinkedList <> ( );
		
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
					
					System.out.println ( " Please use only numerical values for article, price  number.  This number " +
							                     "does not fit -" + Arrays.toString ( arr ) );
					n.printStackTrace ( );
				}
				if ( goods.getArticle ( ) != 0 || goods.getPrice ( ) != 0 ) {
					
					( ( LinkedList < Goods > ) listgoods ).addLast ( goods );
				}
			}
		} catch ( FileNotFoundException fnfe ) {
			fnfe.printStackTrace ( );
		}
		
		return ( LinkedList < Goods > ) listgoods;
	}
	
}
