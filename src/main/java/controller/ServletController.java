package controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClaimDAO;
import dao.ProductDAO;
import dao.ProductRegistrationDAO;
import dao.UserDAO;
import model.Claim;
import model.Product;
import model.ProductRegistration;
import model.User;

/**
 * Servlet implementation class ControllerProcess
 */
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String authorized_username = "";
	private ClaimDAO claimDAO;
	private ProductDAO productDAO;
	private ProductRegistrationDAO productRegistrationDAO;
	private UserDAO userDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

		claimDAO = new ClaimDAO(jdbcURL, jdbcUsername, jdbcPassword);
		productDAO = new ProductDAO(jdbcURL, jdbcUsername, jdbcPassword);
		productRegistrationDAO = new ProductRegistrationDAO(jdbcURL, jdbcUsername, jdbcPassword);
		userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("in doGet of servket controller >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// TODO Auto-generated method stub
		String action = request.getServletPath();
		System.out.println("action :::::::::::::::::::::::::" + action);
		

		try {
			switch (action) {
			case "/login":
				login(request, response);
				break;
			case "/logout":
				logout(request, response);
				break;
			case "/insertProduct":
				addProduct(request, response);
				break;
			case "/register":
				register(request, response);
				break;
			case "/changePermission":
				changePermission(request, response);
				break;
			case "/listUser":
				listUser(request, response);
				break;
			case "/listProduct":
				listProduct(request, response);
				break;
			case "/listClaim":
				listClaim(request, response);
				break;
			case "/listClaimCustomer":
				listClaimCustomer(request, response);
				break;
			case "/listDevice":
				listDevice(request, response);
				break;
			case "/delete":
				deleteProduct(request, response);
				break;
			case "/edit":
				editProduct(request, response);
				break;
			case "/update":
				updateProduct(request, response);
				break;
			case "/approve":
				approveClaim(request, response);
				break;
			case "/insertDevice":
				addDevice(request, response);
				break;
			case "/newDeviceReg":
				selectProducts(request, response);
				break;
			case "/insertClaim":
				addClaim(request, response);
				break;
			case "/newClaim":
				selectProductsRegistered(request, response);
				break;
			case "/findUserByName":
				listSearchedUser(request, response);
				break;
			case "/findProductByName":
				listSearchedProduct(request, response);
				break;
			case "/findProductRegisteredByName":
				listDevicesSearched(request, response);
				break;
			case "/findClaimByName":
				listClaimSearched(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("inside post >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/* Java PBKDF2WithHmacSHA1 Hash */
	private static boolean validatePassword(String originalPassword, String storedPassword) 
		    throws NoSuchAlgorithmException, InvalidKeySpecException
		{
		    String[] parts = storedPassword.split(":");
		    int iterations = Integer.parseInt(parts[0]);

		    byte[] salt = fromHex(parts[1]);
		    byte[] hash = fromHex(parts[2]);

		    PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), 
		        salt, iterations, hash.length * 8);
		    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		    byte[] testHash = skf.generateSecret(spec).getEncoded();

		    int diff = hash.length ^ testHash.length;
		    for(int i = 0; i < hash.length && i < testHash.length; i++)
		    {
		        diff |= hash[i] ^ testHash[i];
		    }
		    return diff == 0;
		}
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
	{
	    byte[] bytes = new byte[hex.length() / 2];
	    for(int i = 0; i < bytes.length ;i++)
	    {
	        bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return bytes;
	}
	
	private static String generateStorngPasswordHash(String password) 
		    throws NoSuchAlgorithmException, InvalidKeySpecException
		{
		    int iterations = 1000;
		    char[] chars = password.toCharArray();
		    byte[] salt = getSalt();

		    PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		    byte[] hash = skf.generateSecret(spec).getEncoded();
		    return iterations + ":" + toHex(salt) + ":" + toHex(hash);
		}

	private static byte[] getSalt() throws NoSuchAlgorithmException
	{
	    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	    byte[] salt = new byte[16];
	    sr.nextBytes(salt);
	    return salt;
	}

	private static String toHex(byte[] array) throws NoSuchAlgorithmException
	{
	    BigInteger bi = new BigInteger(1, array);
	    String hex = bi.toString(16);
	    
	    int paddingLength = (array.length * 2) - hex.length();
	    if(paddingLength > 0)
	    {
	        return String.format("%0"  +paddingLength + "d", 0) + hex;
	    }else{
	        return hex;
	    }
	}
	
	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<User> listUser= userDAO.listAllUsers(authorized_username);
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listSearchedUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String userName = request.getParameter("uname");
		List<User> listUser= userDAO.listSearchedUser(userName, authorized_username);
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listProduct(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Product> listProduct= productDAO.listAllProducts(authorized_username);
		request.setAttribute("listProduct", listProduct);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ProductList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listSearchedProduct(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String productName = request.getParameter("pname");
		List<Product> listProduct= productDAO.listSearchedProducts(productName, authorized_username);
		request.setAttribute("listProduct", listProduct);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ProductList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listDevice(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<ProductRegistration> listProductRegistered = null;
		int role = userDAO.roleCheck(authorized_username);
		listProductRegistered = productRegistrationDAO.listAllProductsRegistered(authorized_username, role);
		listProductRegistered = claimDAO.getClaim(listProductRegistered, role);
		request.setAttribute("productRegistered", listProductRegistered);
		RequestDispatcher dispatcher = request.getRequestDispatcher("RegisteredDevicesList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listDevicesSearched(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String productRegisteredName = request.getParameter("pname");
		List<ProductRegistration> listProductRegistered = null;
		int role = userDAO.roleCheck(authorized_username);
		listProductRegistered = productRegistrationDAO.listSearchedProductsRegistered(productRegisteredName, authorized_username, role);
		listProductRegistered = claimDAO.getClaim(listProductRegistered, role);
		request.setAttribute("productRegistered", listProductRegistered);
		RequestDispatcher dispatcher = request.getRequestDispatcher("RegisteredDevicesList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listClaim(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Claim> listClaim= claimDAO.listAllClaims(authorized_username);
		request.setAttribute("listClaim", listClaim);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ClaimList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listClaimCustomer(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Claim> listClaim= claimDAO.listCustomerClaims(authorized_username);
		request.setAttribute("listClaim", listClaim);
		RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerClaimList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listClaimSearched(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String productRegisteredName = request.getParameter("cname");
		List<Claim> listClaim= claimDAO.listCustomerClaimsSearched(productRegisteredName, authorized_username);
		request.setAttribute("listClaim", listClaim);
		RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerClaimList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("login called >>>>>>>>>>>>>>>>>>>>>>.>>>>>");
		//UserDAO userDao = null;
		boolean result = true;
		String login_error = "";
		String username = request.getParameter("uname");
		String password = request.getParameter("upassword");
		if((username.equals("") || username == null)) {
			result = false;
			login_error += "Please enter the Name";
		}else if((password.equals("") || password == null)) {
			result = false;
			login_error += "Please enter the Password";
		}
		User user = new User(username, password);
		if(result) {
			 {
				try {
					
					String storedPassword = userDAO.getPasswordByUsername(user.getUsername());
					boolean matched = validatePassword(password, storedPassword);
					
					if(matched) {
						this.authorized_username = username;
						user = new User(username, password, authorized_username);
						request.setAttribute("user", user);
						int role = userDAO.roleCheck(username);
						if(role == 0) {
							RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerForm.jsp");
							dispatcher.forward(request, response);
						}else {
							RequestDispatcher dispatcher = request.getRequestDispatcher("AdminForm.jsp");
							dispatcher.forward(request, response);
						}
						
					}else {
						request.setAttribute("error", "Password or Login is incorrect");
						request.setAttribute("user", user);
						RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
						dispatcher.forward(request, response);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			
			}
		}else {
			request.setAttribute("error", login_error);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.authorized_username = "";
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}
	
	private void register(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		String login_error = "";
		boolean result = true;
		String username = request.getParameter("uname");
		String password = request.getParameter("upassword");
		try {
			password = generateStorngPasswordHash(password);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String phone = request.getParameter("uphone");
		String email = request.getParameter("uemail");
		String address = request.getParameter("uaddress");
		if((username.equals("") || username == null)) {
			result = false;
			login_error += "Please enter the Name";
		}else if((password.equals("") || password == null)) {
			result = false;
			login_error += "Please enter the Password";
		}
		else if((email.equals("") || email == null)) {
			result = false;
			login_error += "Please enter the Email";
		}else if((phone.equals("") || phone == null)) {
			result = false;
			login_error += "Please enter the Phone number";
		}
		else if((address.equals("") || address == null)) {
			result = false;
			login_error += "Please enter the Address";
		}
		User user = new User(username, email, password, phone, address);
		if(result) {
			try {
				this.authorized_username = username;
				
				userDAO.register(user);
				user = new User(username, email, password, phone, address, authorized_username);
				request.setAttribute("user", user);
				int role = userDAO.roleCheck(username);
				if(role == 0) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerForm.jsp");
					dispatcher.forward(request, response);
				}else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("AdminForm.jsp");
					dispatcher.forward(request, response);
				}
			} catch (Exception e) {
				request.setAttribute("error", "Incorrect input" );
				request.setAttribute("user", user);
				RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
				dispatcher.forward(request, response);
			} 
		}else {
			request.setAttribute("error", login_error);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);
		}
		
	}
	
	private void changePermission(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			boolean update_res = userDAO.changePermission(id);
			if (update_res) {
				response.sendRedirect("listUser");
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				dispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void editProduct(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			Product existingProduct = productDAO.getProduct(id, authorized_username);
			RequestDispatcher dispatcher = request.getRequestDispatcher("EditProduct.jsp");
			request.setAttribute("product", existingProduct);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateProduct(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		boolean result = true;
		String login_error = "";
		String product_name = request.getParameter("pname");
		String product_brand = request.getParameter("pbrand");
		String product_category = request.getParameter("pcat");
		if((product_name.equals("") || product_name == null)) {
			result = false;
			login_error += "Please enter the Product Name";
		}else if((product_brand.equals("") || product_brand == null)) {
			result = false;
			login_error += "Please enter the Brand";
		}
		else if((product_category.equals("") || product_category == null)) {
			result = false;
			login_error += "Please enter the Category";
		}
		try {
			if(result) {
				int role = userDAO.roleCheck(authorized_username);
				if(role == 1) {
					int id = Integer.parseInt(request.getParameter("id"));
					Product product = new Product(id, product_name, product_brand, product_category);
					boolean found = productDAO.updateProduct(product);
					if(found) {
						response.sendRedirect("listProduct");
					}else {
						RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
						dispatcher.forward(request, response);
					}
					
				}else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
					dispatcher.forward(request, response);
				}
			}else {
				request.setAttribute("errors", login_error);
				RequestDispatcher dispatcher = request.getRequestDispatcher("AdminForm.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void approveClaim(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		try {
			if(this.authorized_username.equals("admin")) {
				int id = Integer.parseInt(request.getParameter("id"));
				boolean found = claimDAO.approveClaim(id);
				if(found) {
					response.sendRedirect("listClaim");
				}else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
					dispatcher.forward(request, response);
				}
				
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		boolean result = true;
		String login_error = "";
		String product_name = request.getParameter("pname");
		String product_brand = request.getParameter("pbrand");
		String product_category = request.getParameter("pcat");
		if((product_name.equals("") || product_name == null)) {
			result = false;
			login_error += "Please enter the Product Name";
		}else if((product_brand.equals("") || product_brand == null)) {
			result = false;
			login_error += "Please enter the Brand";
		}
		else if((product_category.equals("") || product_category == null)) {
			result = false;
			login_error += "Please enter the Category";
		}
		if(result) {
			int role = userDAO.roleCheck(authorized_username);
			if(role == 1) {
				try {
					Product newproduct = new Product(product_name, product_brand, product_category);
					productDAO.addProduct(newproduct);
					response.sendRedirect("listProduct");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				dispatcher.forward(request, response);
			}
		}else {
			request.setAttribute("errors", login_error);
			RequestDispatcher dispatcher = request.getRequestDispatcher("AdminForm.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void addDevice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		boolean result = true;
		String login_error = "";
		String product_name = request.getParameter("pname");
		String serial_no = request.getParameter("serialno");
		String purchase_date = request.getParameter("purchase_date");
		if((product_name.equals("") || product_name == null)) {
			result = false;
			login_error += "Please enter the Product Name";
		}else if((serial_no.equals("") || serial_no == null)) {
			result = false;
			login_error += "Please enter the Serial Number";
		}
		else if((purchase_date.equals("") || purchase_date == null)) {
			result = false;
			login_error += "Please enter the Purchase Date";
		}
		ProductRegistration newProductRegistered = new ProductRegistration(authorized_username, product_name, serial_no, purchase_date);
		if(result) {
			int role = userDAO.roleCheck(authorized_username);
			if(role == 0) {
				try {
					productRegistrationDAO.addDevice(newProductRegistered);
					response.sendRedirect("listDevice");
				} catch (Exception e) {
					request.setAttribute("error", "Please, Check the requirements");
					request.setAttribute("productRegistered", newProductRegistered);
					request.setAttribute("auth_user", authorized_username);
					RequestDispatcher dispatcher = request.getRequestDispatcher("insertDevice.jsp");
					dispatcher.forward(request, response);
				} 
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				dispatcher.forward(request, response);
			}
		}else {
			request.setAttribute("error", login_error);
			request.setAttribute("productRegistered", newProductRegistered);
			request.setAttribute("auth_user", authorized_username);
			List<Product> listProduct= productDAO.listAllProducts(authorized_username);
			request.setAttribute("listProduct", listProduct);
			RequestDispatcher dispatcher = request.getRequestDispatcher("insertDevice.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void selectProducts(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Product> listProduct= productDAO.listAllProducts(authorized_username);
		request.setAttribute("listProduct", listProduct);
		RequestDispatcher dispatcher = request.getRequestDispatcher("insertDevice.jsp");
		dispatcher.forward(request, response);
	}
	
	private void selectProductsRegistered(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		ProductRegistration productRegistration = productRegistrationDAO.getProductById(id, authorized_username);
		boolean checking = claimDAO.checkPlan(productRegistration.getAuthorizedUser(), productRegistration.getProduct_name(), productRegistration.getPurchase_date());
		if(checking) {
			request.setAttribute("product", productRegistration);
			RequestDispatcher dispatcher = request.getRequestDispatcher("AddClaim.jsp");
			dispatcher.forward(request, response);
		}else {
			request.setAttribute("user", authorized_username);
			request.setAttribute("error", "You cannot add new Claim. Check the rules. ");
			request.setAttribute("file", "listDevice");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			dispatcher.forward(request, response);
		}
		
	}
	
	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			Product product = new Product(id);
			productDAO.deleteProduct(product);
			response.sendRedirect("listProduct");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void addClaim(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean result = true;
		String login_error = "";
		String product_name = request.getParameter("pname");
		String claim_desc = request.getParameter("desc");
		if((product_name.equals("") || product_name == null)) {
			result = false;
			login_error += "Please enter the Product Name";
		}else if((claim_desc.equals("") || claim_desc == null)) {
			result = false;
			login_error += "Please enter the Description";
		}
		
		if(result) {
			if(!this.authorized_username.equals("admin")) {
				try {
					long millis=System.currentTimeMillis();  
					Date date=new Date(millis);  
					Claim newClaim = new Claim(authorized_username, product_name, claim_desc, date.toString(), "N");
					String purchase_date = productRegistrationDAO.getPurchaseDate(authorized_username, product_name);
					boolean check = claimDAO.checkPlan(authorized_username, product_name, purchase_date);
					if(check) {
						claimDAO.addClaim(newClaim);
						response.sendRedirect("listDevice");
					}else {
						request.setAttribute("errors", "You cannot add a Claim to this product");
						RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerForm.jsp");
						dispatcher.forward(request, response);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				dispatcher.forward(request, response);
			}
		}else {
			request.setAttribute("errors", login_error);
			RequestDispatcher dispatcher = request.getRequestDispatcher("AdminForm.jsp");
			dispatcher.forward(request, response);
		}
	}

}
