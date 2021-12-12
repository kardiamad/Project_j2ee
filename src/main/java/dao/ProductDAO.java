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
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Product;
import model.User;

public class ProductDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public ProductDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
	
	public List<Product> listAllProducts(String authorized_username) throws SQLException {
		List<Product> listProduct = new ArrayList<>();
		
		String sql = "SELECT * FROM product";
		connect();
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			int id = resultSet.getInt("product_id");
			String name = resultSet.getString("product_name");
			String brand = resultSet.getString("product_brand");
			String category = resultSet.getString("product_category");
			
			Product product = new Product(id, name, brand, category, authorized_username);
			listProduct.add(product);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listProduct;
	}
	
	public List<Product> listSearchedProducts(String productName, String authorized_username) throws SQLException {
		List<Product> listProduct = new ArrayList<>();
		
		String sql = "SELECT * FROM product where product_name like ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, "%" + productName + "%");
		ResultSet resultSet = statement.executeQuery();		
		
		while (resultSet.next()) {
			int id = resultSet.getInt("product_id");
			String name = resultSet.getString("product_name");
			String brand = resultSet.getString("product_brand");
			String category = resultSet.getString("product_category");
			
			Product product = new Product(id, name, brand, category, authorized_username);
			listProduct.add(product);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listProduct;
	}
	
	public boolean addProduct(Product product) throws SQLException {
		String sql = "INSERT INTO product (product_name, product_brand, product_category) VALUES (?, ?, ?)";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, product.getProduct_name());
		statement.setString(2, product.getProduct_brand());
		statement.setString(3, product.getProduct_category());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public Product getProduct(int id, String authorized_username) throws SQLException {
		Product product = null;
		String sql = "SELECT * FROM product WHERE product_id = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String name = resultSet.getString("product_name");
			String brand = resultSet.getString("product_brand");
			String category = resultSet.getString("product_category");
			
			product = new Product(id, name, brand, category, authorized_username);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return product;
	}
	
	public boolean updateProduct(Product product) throws SQLException {
		String sql = "UPDATE product SET product_name = ?, product_brand = ?, product_category = ?";
		sql += " WHERE product_id = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, product.getProduct_name());
		statement.setString(2, product.getProduct_brand());
		statement.setString(3, product.getProduct_category());
		statement.setInt(4, product.getProduct_id());
		
		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		return rowUpdated;		
	}
	
	public boolean deleteProduct(Product product) throws SQLException {
		String sql = "DELETE FROM product where product_id = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, product.getProduct_id());
		
		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;		
	}

}
