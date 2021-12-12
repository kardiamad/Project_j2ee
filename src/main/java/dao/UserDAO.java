package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
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

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import model.Product;
import model.User;

public class UserDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public UserDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
	
	public boolean register(User user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		String sql = "INSERT INTO user (username, email, password, phoneno, address) VALUES (?, ?, ?, ?, ?)";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getEmail());
		statement.setString(3, user.getPassword());
		statement.setString(4, user.getPhoneno());
		statement.setString(5, user.getAddress());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}


	
//	public boolean login(User user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
//		String sql = "Select password from user where username = ?";
//		connect();
//		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//		statement.setString(1, user.getUsername());
//		
//		ResultSet resultSet = statement.executeQuery();
//		while(resultSet.next()) {
//			savedPassword = resultSet.getString("password");
//		}
//		
//		statement.close();
//		disconnect();
//		return matched;
//	}
	
	public String getPasswordByUsername(String username) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		String savedPassword = "";
		String sql = "Select password from user where username = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, username);
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()) {
			savedPassword = resultSet.getString("password");
		}
		statement.close();
		disconnect();
		return savedPassword;
	}
	
	public int roleCheck(String username) throws SQLException {
		int role = 0;
		String sql = "Select * from user where username = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, username);
		
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			role = resultSet.getInt("admin");
		}
		
		statement.close();
		disconnect();
		return role;
	}
	
	public int getUserById(int id) throws SQLException {
		int role = 0;
		String sql = "Select * from user where user_id = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			role = resultSet.getInt("admin");
		}
		
		statement.close();
		disconnect();
		return role;
	}
	
	public boolean changePermission(int id) throws SQLException {
		int role = getUserById(id);
		String sql = "UPDATE user SET admin = ?";
		sql += " WHERE user_id = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		if(role == 1) { 
			statement.setInt(1, 0);
		}
		else{
			statement.setInt(1, 1);
		}
		statement.setInt(2, id);
		
		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowUpdated;		
	}

	public List<User> listAllUsers(String authorized_username) throws SQLException {
		List<User> listUser = new ArrayList<>();
		
		String sql = "SELECT * FROM user";
		connect();
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			int id = resultSet.getInt("user_id");
			String username = resultSet.getString("username");
			String email = resultSet.getString("email");
			String password = "********";
			String phoneno = resultSet.getString("phoneno");
			String address = resultSet.getString("address");
			int role = resultSet.getInt("admin");
			
			User user = new User(id, username, email, password, phoneno, address, role, authorized_username);
			listUser.add(user);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listUser;
	}
	
	public List<User> listSearchedUser(String userName, String authorized_username) throws SQLException {
		List<User> listUser = new ArrayList<>();
		
		String sql = "SELECT * FROM user where username like ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, "%" + userName + "%");
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			int id = resultSet.getInt("user_id");
			String username = resultSet.getString("username");
			String email = resultSet.getString("email");
			String password = "********";
			String phoneno = resultSet.getString("phoneno");
			String address = resultSet.getString("address");
			int role = resultSet.getInt("admin");
			
			User user = new User(id, username, email, password, phoneno, address, role, authorized_username);
			listUser.add(user);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		return listUser;
	}

}
