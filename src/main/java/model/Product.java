package model;

public class Product {
	
	private int product_id;
	private String product_name;
	private String product_brand;
	private String product_category;
	private String authorized_username;
	
	public String getAuthorized_username() {
		return authorized_username;
	}

	public void setAuthorized_username(String authorized_username) {
		this.authorized_username = authorized_username;
	}

	public Product(int product_id, String product_name, String product_brand, String product_category,
			String authorized_username) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_brand = product_brand;
		this.product_category = product_category;
		this.authorized_username = authorized_username;
	}

	public Product() {
		super();
	}
	
	public Product(int product_id) {
		super();
		this.product_id = product_id;
	}

	public Product(int product_id, String product_name, String product_brand, String product_category) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_brand = product_brand;
		this.product_category = product_category;
	}
	
	public Product(String product_name, String product_brand, String product_category) {
		super();
		this.product_name = product_name;
		this.product_brand = product_brand;
		this.product_category = product_category;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}
	public String getProduct_category() {
		return product_category;
	}
	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}
	
}
