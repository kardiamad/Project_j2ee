package model;

import java.util.Date;
import java.util.List;

public class ProductRegistration {
	private int product_reg_id;
	private String username;
	private String product_name;
	private String serialno;
	private String purchase_date;
	private List<Claim> claim;
	private String authorizedUser;
	private int role;
	public int getRole() {
		return role;
	}

	public ProductRegistration(int product_reg_id, String username, String product_name, String serialno,
			String purchase_date, String authorizedUser, int role) {
		super();
		this.product_reg_id = product_reg_id;
		this.username = username;
		this.product_name = product_name;
		this.serialno = serialno;
		this.purchase_date = purchase_date;
		this.authorizedUser = authorizedUser;
		this.role = role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public ProductRegistration(int product_reg_id, String username, String product_name, String serialno,
			String purchase_date, List<Claim> claim, String authorizedUser, int role) {
		super();
		this.product_reg_id = product_reg_id;
		this.username = username;
		this.product_name = product_name;
		this.serialno = serialno;
		this.purchase_date = purchase_date;
		this.claim = claim;
		this.authorizedUser = authorizedUser;
		this.role = role;
	}

	public ProductRegistration(int product_reg_id, String username, String product_name, String serialno,
			String purchase_date, List<Claim> claim, String authorizedUser) {
		super();
		this.product_reg_id = product_reg_id;
		this.username = username;
		this.product_name = product_name;
		this.serialno = serialno;
		this.purchase_date = purchase_date;
		this.claim = claim;
		this.authorizedUser = authorizedUser;
	}
	
	public String getAuthorizedUser() {
		return authorizedUser;
	}

	public void setAuthorizedUser(String authorizedUser) {
		this.authorizedUser = authorizedUser;
	}

	public ProductRegistration(int product_reg_id, String username, String product_name, String serialno,
			String purchase_date, String authorizedUser) {
		super();
		this.product_reg_id = product_reg_id;
		this.username = username;
		this.product_name = product_name;
		this.serialno = serialno;
		this.purchase_date = purchase_date;
		this.authorizedUser = authorizedUser;
	}

	public ProductRegistration() {
		super();
	}
	
	public ProductRegistration(int product_reg_id, String username, String product_name, String serialno,
			String purchase_date, List<Claim> claim) {
		super();
		this.product_reg_id = product_reg_id;
		this.username = username;
		this.product_name = product_name;
		this.serialno = serialno;
		this.purchase_date = purchase_date;
		this.claim = claim;
	}

	public ProductRegistration(String username, String product_name, String serialno, String purchase_date) {
		super();
		this.username = username;
		this.product_name = product_name;
		this.serialno = serialno;
		this.purchase_date = purchase_date;
	}
	
	public ProductRegistration(int product_reg_id, String username, String product_name, String serialno,
			String purchase_date) {
		super();
		this.product_reg_id = product_reg_id;
		this.username = username;
		this.product_name = product_name;
		this.serialno = serialno;
		this.purchase_date = purchase_date;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public String getPurchase_date() {
		return purchase_date;
	}
	public void setPurchase_date(String purchase_date) {
		this.purchase_date = purchase_date;
	}
	public int getProduct_reg_id() {
		return product_reg_id;
	}
	public void setProduct_reg_id(int product_reg_id) {
		this.product_reg_id = product_reg_id;
	}

	public List<Claim> getClaim() {
		return claim;
	}

	public void setClaim(List<Claim> claim) {
		this.claim = claim;
	}
	
}
