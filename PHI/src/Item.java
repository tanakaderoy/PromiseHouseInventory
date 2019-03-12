import java.sql.Date;

public class Item {
	private int quantinty, upc;
	private String productName, categoryName;
	private String date;

	private Double price;
	
	public Item(int upc, String productName,Double price, int quantinty, String categoryName, String date) {
		this.upc = upc;
		this.productName = productName;
		this.quantinty = quantinty;
		this.categoryName = categoryName;
		this.date = date;
		this.price = price;
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
	public String getCategory() {
		return categoryName;
	}
	public String getdate() {
		System.out.print(date);
		
		
		return date;
	}
	
	public Double getPrice() {
		return price;
	}

}
