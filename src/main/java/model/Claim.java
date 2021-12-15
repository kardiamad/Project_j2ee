package model;

public class Claim {
	private int claim_id;
	private String username;
	private int product_id;
	private String product_name;
	private String claim_desc;
	private String claim_date;
	private String claim_approval;
	private String authorizedUser;
	private int role;
	
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public Claim(int claim_id, String username, int product_id, String product_name, String claim_desc,
			String claim_date, String claim_approval, String authorizedUser, int role) {
		super();
		this.claim_id = claim_id;
		this.username = username;
		this.product_id = product_id;
		this.product_name = product_name;
		this.claim_desc = claim_desc;
		this.claim_date = claim_date;
		this.claim_approval = claim_approval;
		this.authorizedUser = authorizedUser;
		this.role = role;
	}

	public Claim(int claim_id, String username, String product_name, String claim_desc, String claim_date,
			String claim_approval, String authorizedUser, int role) {
		super();
		this.claim_id = claim_id;
		this.username = username;
		this.product_name = product_name;
		this.claim_desc = claim_desc;
		this.claim_date = claim_date;
		this.claim_approval = claim_approval;
		this.authorizedUser = authorizedUser;
		this.role = role;
	}
	
	public Claim() {
		super();
	}
	
	public String getAuthorizedUser() {
		return authorizedUser;
	}

	public void setAuthorizedUser(String authorizedUser) {
		this.authorizedUser = authorizedUser;
	}

	public Claim(int claim_id, String username, String product_name, String claim_desc, String claim_date,
			String claim_approval, String authorizedUser) {
		super();
		this.claim_id = claim_id;
		this.username = username;
		this.product_name = product_name;
		this.claim_desc = claim_desc;
		this.claim_date = claim_date;
		this.claim_approval = claim_approval;
		this.authorizedUser = authorizedUser;
	}

	public Claim(String username, String product_name, String claim_desc, String claim_approval) {
		super();
		this.username = username;
		this.product_name = product_name;
		this.claim_desc = claim_desc;
		this.claim_approval = claim_approval;
	}
	
	public Claim(String username, String product_name, String claim_desc, String claim_date, String claim_approval) {
		super();
		this.username = username;
		this.product_name = product_name;
		this.claim_desc = claim_desc;
		this.claim_date = claim_date;
		this.claim_approval = claim_approval;
	}
	public Claim(int claim_id, String username, String product_name, String claim_desc, String claim_date, String claim_approval) {
		super();
		this.claim_id = claim_id;
		this.username = username;
		this.product_name = product_name;
		this.claim_desc = claim_desc;
		this.claim_date = claim_date;
		this.claim_approval = claim_approval;
	}
	public Claim(int id) {
		super();
		this.claim_id = claim_id;
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
	public String getClaim_desc() {
		return claim_desc;
	}
	public void setClaim_desc(String claim_desc) {
		this.claim_desc = claim_desc;
	}
	public String getClaim_approval() {
		return claim_approval;
	}
	public void setClaim_approval(String claim_approval) {
		this.claim_approval = claim_approval;
	}
	public int getClaim_id() {
		return claim_id;
	}
	public void setClaim_id(int claim_id) {
		this.claim_id = claim_id;
	}
	public String getClaim_date() {
		return claim_date;
	}
	public void setClaim_date(String claim_date) {
		this.claim_date = claim_date;
	}
	
}
