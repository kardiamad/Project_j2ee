package model;

public class User {
	private int user_id;
	private String username;
	private String password;
	private String email;
	private String phoneno;
	private String address;
	private int role;
	private String authorizedUser;
	
	public User() {
		super();
	}
	
	
	public User(int user_id, String username, String password, String email, String phoneno, String address, int role) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneno = phoneno;
		this.address = address;
		this.role = role;
	}


	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String username, String email, String password,  String phoneno, String address) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneno = phoneno;
		this.address = address;
	}
	
	public User(int user_id, String username, String password, String email, String phoneno, String address) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneno = phoneno;
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public int getRole() {
		return role;
	}


	public void setRole(int role) {
		this.role = role;
	}


	public String getAuthorizedUser() {
		return authorizedUser;
	}


	public void setAuthorizedUser(String authorizedUser) {
		this.authorizedUser = authorizedUser;
	}


	public User(String username, String password, String authorizedUser) {
		super();
		this.username = username;
		this.password = password;
		this.authorizedUser = authorizedUser;
	}


	public User(String username, String password, String email, String phoneno, String address, String authorizedUser) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneno = phoneno;
		this.address = address;
		this.authorizedUser = authorizedUser;
	}


	public User(int user_id, String username, String password, String email, String phoneno, String address, int role,
			String authorizedUser) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneno = phoneno;
		this.address = address;
		this.role = role;
		this.authorizedUser = authorizedUser;
	}
	
}
