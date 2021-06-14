package dto;

/**
 * @author kuritaakinari
 *商品用のDTOクラス
 */
public class Product {
	
//	private int id;
	private String title;
	private double price;
	
	public Product() {}
	
	public Product(String title,double price) {
//		this.id = id;
		this.title = title;
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
		
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
