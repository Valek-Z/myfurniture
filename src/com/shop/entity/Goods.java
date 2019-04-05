package com.shop.entity;


/**
 * @author valek
 */

/**
 * This class creates the essence of the goods from the name, article and price
 *
 */
public class Goods {
	
	private  long article;
	private  String name;
	private  long price;
	
	public Goods ( ) {
	}
	
	public Goods ( long article, String name, long price ) {
		this.article = article;
		this.name = name;
		this.price = price;
	}
	
	@Override
	public int hashCode ( ) {
		int result = getName ( ).hashCode ( );
		result = 31 * result + ( int ) ( getArticle ( ) ^ ( getArticle ( ) >>> 32 ) );
		result = 31 * result + ( int ) ( getPrice ( ) ^ ( getPrice ( ) >>> 32 ) );
		return result;
	}
	
	@Override
	public boolean equals ( Object o ) {
		if ( this == o ) return true;
		if ( ! ( o instanceof Goods ) ) return false;
		
		Goods goods = ( Goods ) o;
		
		if ( getArticle ( ) != goods.getArticle ( ) ) return false;
		if ( getPrice ( ) != goods.getPrice ( ) ) return false;
		return getName ( ).equals ( goods.getName ( ) );
	}
	
	public long getArticle ( ) {
		return article;
	}
	
	public void setArticle ( long article ) {
		this.article = article;
	}
	
	public long getPrice ( ) {
		return price;
	}
	
	public String getName ( ) {
		return name;
	}
	
	public void setName ( String name ) {
		this.name = name;
	}
	
	public void setPrice ( long price ) {
		this.price = price;
	}
	
	@Override
	public String toString ( ) {
		return "Goods{" + "name='" + name + '\'' + ", article=" + article + ", price=" + price + '}';
	}
	
}
