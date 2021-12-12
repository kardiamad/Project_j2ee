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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Claim;
import model.Product;
import model.ProductRegistration;

public class ProductRegistrationDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public ProductRegistrationDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
	
	public List<ProductRegistration> listAllProductsRegistered(String authorizedUser, int role) throws SQLException {
		List<ProductRegistration> listProductRegistered = new ArrayList<>();
		String sql = "SELECT * FROM product_reg";
		connect();
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			int id = resultSet.getInt("product_reg_id");
			String username = resultSet.getString("username");
			String product_name = resultSet.getString("product_name");
			String serial_no = resultSet.getString("serial_no");
			String purchase_date = resultSet.getString("purchase_date");
			ProductRegistration productRegistration = new ProductRegistration(id, username, product_name, serial_no, purchase_date, authorizedUser, role);
			listProductRegistered.add(productRegistration);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listProductRegistered;
	}
	
	public List<ProductRegistration> listSearchedProductsRegistered(String productRegistered, String authorizedUser, int role) throws SQLException {
		List<ProductRegistration> listProductRegistered = new ArrayList<>();
		String sql = "SELECT * FROM product_reg where product_name like ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, "%" + productRegistered + "%");
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			int id = resultSet.getInt("product_reg_id");
			String username = resultSet.getString("username");
			String product_name = resultSet.getString("product_name");
			String serial_no = resultSet.getString("serial_no");
			String purchase_date = resultSet.getString("purchase_date");
			ProductRegistration productRegistration = new ProductRegistration(id, username, product_name, serial_no, purchase_date, authorizedUser, role);
			listProductRegistered.add(productRegistration);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listProductRegistered;
	}
	
	public boolean addDevice(ProductRegistration productRegistration) throws SQLException {
		String sql = "INSERT INTO product_reg (username, product_name, serial_no, purchase_date) VALUES (?, ?, ?, ?)";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, productRegistration.getUsername());
		statement.setString(2, productRegistration.getProduct_name());
		statement.setString(3, productRegistration.getSerialno());
		statement.setString(4, productRegistration.getPurchase_date());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public String getPurchaseDate(String username, String product_name) throws SQLException {
		String purchase_date = "";
		String sql = "SELECT * FROM product_reg WHERE username = ? and product_name = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, product_name);
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			purchase_date = resultSet.getString("purchase_date");
		}
		
		return purchase_date;
	}
	
	public ProductRegistration getProductById(int id, String authorized_username) throws SQLException {
		ProductRegistration productRegistration = new ProductRegistration();
		
		String sql = "SELECT * FROM product_reg WHERE product_reg_id = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			productRegistration.setProduct_reg_id(resultSet.getInt("product_reg_id"));
			productRegistration.setUsername(resultSet.getString("username"));
			productRegistration.setProduct_name(resultSet.getString("product_name"));
			productRegistration.setSerialno(resultSet.getString("serial_no"));
			productRegistration.setPurchase_date(resultSet.getString("purchase_date"));
			productRegistration.setAuthorizedUser(authorized_username);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return productRegistration;
	}
	
	public List<ProductRegistration> getProductByUser(String username, int role) throws SQLException {
		List<ProductRegistration> listProductRegistered = new ArrayList<>();
		
		String sql = "SELECT * FROM product_reg WHERE username = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, username);
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			ProductRegistration productRegistration = new ProductRegistration();
			productRegistration.setProduct_reg_id(resultSet.getInt("product_reg_id"));
			productRegistration.setUsername(resultSet.getString("username"));
			productRegistration.setProduct_name(resultSet.getString("product_name"));
			productRegistration.setSerialno(resultSet.getString("serial_no"));
			productRegistration.setPurchase_date(resultSet.getString("purchase_date"));
			productRegistration.setAuthorizedUser(username);
			productRegistration.setRole(role);
			listProductRegistered.add(productRegistration);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listProductRegistered;
	}
}
