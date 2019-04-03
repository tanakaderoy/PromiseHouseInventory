package org.team3.PHI;

public class Item {
	private int quantity;
	private String productName, categoryName,upc;
	private String date;

	private double price;
	
	public Item(String upc, String productName,double price, int quantity, String categoryName, String date) {
		this.upc = upc;
		this.productName = productName;
		this.quantity = quantity;
		this.categoryName = categoryName;
		this.date = date;
		this.price = price;
	}
	public String getUPC() {
		return upc;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public String getCategory() {
		return categoryName;
	}
	public String getDate() {
		System.out.print(date);
		
		
		return date;
	}
	
	public Double getPrice() {
		return price;
	}

}
