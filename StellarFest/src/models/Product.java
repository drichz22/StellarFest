package models;

import utils.Connect;

public class Product {
	private String product_id;
	private String vendor_id;
	private String product_name;
	private String product_description;
	protected static Connect connect = Connect.getInstance();
	
	public Product(String product_id, String vendor_id, String product_name, String product_description) {
		super();
		this.product_id = product_id;
		this.vendor_id = vendor_id;
		this.product_name = product_name;
		this.product_description = product_description;
	}
	
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getVendor_id() {
		return vendor_id;
	}
	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	public static Connect getConnect() {
		return connect;
	}
	public static void setConnect(Connect connect) {
		Product.connect = connect;
	}
	
	
}
