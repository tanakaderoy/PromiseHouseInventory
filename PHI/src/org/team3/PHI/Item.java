package org.team3.PHI;

public class Item {
	private int quantity;
	private String productName, categoryName,upc;
	private String date;
	private String donor;
	private double weight;
	private double price;
	
	public Item(String upc, String productName,double price, double weight, int quantity, String donor, String categoryName, String date ) {
		this.upc = upc;
		this.productName = productName;
		this.quantity = quantity;
		this.categoryName = categoryName;
		this.date = date;
		this.price = price;
		this.weight= weight;
		this.donor = donor;
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
		//System.out.print(date);
		
		
		return date;
	}
	
	public Double getPrice() {
		return price;
	}

	public double getWeight() {
		return weight;
	}
	
	public String getDonor() {
		return donor;
	}
	
}


