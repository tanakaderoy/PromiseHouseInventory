
public class Item {
	private int quantinty, upc;
	private String productName;
	//private Double price;
	
	public Item(int upc, String productName, int quantinty) {
		this.upc = upc;
		this.productName = productName;
		this.quantinty = quantinty;
		//this.price = price;
	}
	public int getUPC() {
		return upc;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public int getQuantinty() {
		return quantinty;
	}
	
	/*public Double getPrice() {
		return price;
	}*/

}
