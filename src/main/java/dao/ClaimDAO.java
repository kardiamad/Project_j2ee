package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Claim;
import model.Product;
import model.ProductRegistration;
import model.User;

public class ClaimDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public ClaimDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}
	
	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(
										jdbcURL, jdbcUsername, jdbcPassword);
		}
	}
	
	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	
	public List<Claim> listAllClaims(String authorized_username) throws SQLException {
		List<Claim> listClaim = new ArrayList<>();
		
		String sql = "SELECT * FROM claim";
		connect();
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			int claim_id = resultSet.getInt("claim_id");
			String username = resultSet.getString("username");
			String product_name = resultSet.getString("product_name");
			String desc = resultSet.getString("claim_desc");
			String date = resultSet.getString("claim_date");
			String approval = resultSet.getString("claim_approval");
			
			Claim claim = new Claim(claim_id, username, product_name, desc, date, approval, authorized_username);
			listClaim.add(claim);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listClaim;
	}
	
	public List<Claim> listCustomerClaims(String username) throws SQLException {
		List<Claim> listClaim = new ArrayList<>();
		
		String sql = "SELECT * FROM claim where username = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, username);
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			int claim_id = resultSet.getInt("claim_id");
			String product_name = resultSet.getString("product_name");
			String desc = resultSet.getString("claim_desc");
			String date = resultSet.getString("claim_date");
			String approval = resultSet.getString("claim_approval");
			
			Claim claim = new Claim(claim_id, username, product_name, desc, date, approval);
			listClaim.add(claim);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listClaim;
	}
	
	public List<Claim> listCustomerClaimsSearched(String productName, String username) throws SQLException {
		List<Claim> listClaim = new ArrayList<>();
		
		String sql = "SELECT * FROM claim where product_name like ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, "%" + productName + "%");
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			int claim_id = resultSet.getInt("claim_id");
			String product_name = resultSet.getString("product_name");
			String desc = resultSet.getString("claim_desc");
			String date = resultSet.getString("claim_date");
			String approval = resultSet.getString("claim_approval");
			
			Claim claim = new Claim(claim_id, username, product_name, desc, date, approval);
			listClaim.add(claim);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listClaim;
	}
	
	public List<ProductRegistration> getClaim(List<ProductRegistration> listProduct, int role) throws SQLException {
		List<ProductRegistration> listProductRegistered = new ArrayList<>();
		List<Claim> claimList = new ArrayList<>();
		ResultSet resultSet = null;
		
		PreparedStatement statement = null;
		for (int i=0; i<listProduct.size(); i++){
			   System.out.println("Element "+i+listProduct.get(i));
			   String sql = "SELECT * FROM claim where username = ? and product_name = ?";
				connect();
				statement = jdbcConnection.prepareStatement(sql);
				statement.setString(1, listProduct.get(i).getUsername());
				statement.setString(2, listProduct.get(i).getProduct_name());
				resultSet = statement.executeQuery();
				
				while (resultSet.next()) {
					int claim_id = resultSet.getInt("claim_id");
					String claim_username = resultSet.getString("username");
					String claim_product_name = resultSet.getString("product_name");
					String claim_desc = resultSet.getString("claim_desc");
					String claim_date = resultSet.getString("claim_date");
					String claim_approval = resultSet.getString("claim_approval");
					Claim claim = new Claim(claim_id, claim_username, claim_product_name, claim_desc, claim_date, claim_approval, listProduct.get(i).getAuthorizedUser(), role);
					claimList.add(claim);
				}
				ProductRegistration productRegistration = new ProductRegistration();
				productRegistration.setProduct_reg_id(listProduct.get(i).getProduct_reg_id());
				productRegistration.setUsername(listProduct.get(i).getUsername());
				productRegistration.setProduct_name(listProduct.get(i).getProduct_name());
				productRegistration.setSerialno(listProduct.get(i).getSerialno());
				productRegistration.setPurchase_date(listProduct.get(i).getPurchase_date());
				productRegistration.setRole(listProduct.get(i).getRole());
				productRegistration.setAuthorizedUser(listProduct.get(i).getAuthorizedUser());
				if(claimList != null) {
					productRegistration.setClaim(claimList);
				}
				listProductRegistered.add(productRegistration);
			}
		
		
		resultSet.close();
		statement.close();
		disconnect();
		return listProductRegistered;
	}
	
	public boolean checkPlan(String username, String product_name, String purchase_date, int id)  throws SQLException{
		boolean result = true;
		Date due_date = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" ); 
			Calendar cal = Calendar.getInstance();    
			cal.setTime( dateFormat.parse(purchase_date));    
			cal.add( Calendar.YEAR, 5 );    
			due_date = cal.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] dates = new String[3];
		String sql = "SELECT claim_date FROM claim where product_id = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet resultSet = statement.executeQuery();
		resultSet.last();
		int size = resultSet.getRow();
		if(size < 3) {
			long millis=System.currentTimeMillis();  
			Date date=new Date(millis); 
			if(date.after(due_date)) {
				result = false;
			}else {
				result = true;
			}
		}else {
			result = false;
		}
		
		return result;
	}
	
	public boolean approveClaim(int id) throws SQLException {
		String sql = "UPDATE claim SET claim_approval = ?";
		sql += " WHERE claim_id = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, "Y");
		statement.setInt(2, id);
		
		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowUpdated;		
	}
	
	public boolean addClaim(Claim claim) throws SQLException {
		String sql = "INSERT INTO claim (username, product_id, product_name, claim_desc, claim_date, claim_approval) VALUES (?, ?, ?, ?, ?, ?)";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, claim.getUsername());
		statement.setInt(2, claim.getProduct_id());
		statement.setString(3, claim.getProduct_name());
		statement.setString(4, claim.getClaim_desc());
		statement.setString(5, claim.getClaim_date());
		statement.setString(6, claim.getClaim_approval());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
}
