package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.Connect;

public class User {
	
	//Atribut dari User
	private String user_id;
	private String user_email;
	private String user_name;
	private String user_password;
	private String user_role;
	protected static Connect connect = Connect.getInstance();
	
	//Constructor User
	public User(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super();
		this.user_id = user_id;
		this.user_email = user_email;
		this.user_name = user_name;
		this.user_password = user_password;
		this.user_role = user_role;
	}

	//getter setter User karena atribut User access modifiernya private
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	
	//Untuk fitur Register
	public static boolean insertUser(User newUser) {
		String query = "INSERT INTO user (user_id, user_email, user_name, user_password, user_role) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = connect.prepareStatement(query);
		int rowsAffected = 0;
		
		try {
			ps.setString(1, newUser.getUser_id());
			ps.setString(2, newUser.getUser_email());
			ps.setString(3, newUser.getUser_name());
			ps.setString(4, newUser.getUser_password());
			ps.setString(5, newUser.getUser_role());
			rowsAffected = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsAffected > 0;
	}
	
	//Untuk pembuatan userId dan pemastian Id tidak konflik
	public static ArrayList<User> getAllUsers(){
		ArrayList<User> userList = new ArrayList<>();
		String query = "SELECT * FROM user";
		PreparedStatement ps = connect.prepareStatement(query);
		
		try {
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				String userId = resultSet.getString("user_id");
				String userEmail = resultSet.getString("user_email");
				String userName = resultSet.getString("user_name");
				String userPassword = resultSet.getString("user_password");
				String userRole = resultSet.getString("user_role");
				
				User user = new User(userId, userEmail, userName, userPassword, userRole);
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
	//Untuk keperluan validasi fitur Login
	public static User getUserByEmailPassword(String email, String password) {
		String query = "SELECT * FROM user WHERE user_email = ? AND user_password = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		User user = null;
		
		try {
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				String userId = resultSet.getString("user_id");
				String userEmail = resultSet.getString("user_email");
				String userName = resultSet.getString("user_name");
				String userPassword = resultSet.getString("user_password");
				String userRole = resultSet.getString("user_role");
				
				user = new User(userId, userEmail, userName, userPassword, userRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	//Untuk validasi apakah Email unique atau tidak
	public static User getUserByEmail(String email) {
		String query = "SELECT * FROM user WHERE user_email = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		User user = null;
		
		try {
			ps.setString(1, email);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				String userId = resultSet.getString("user_id");
				String userEmail = resultSet.getString("user_email");
				String userName = resultSet.getString("user_name");
				String userPassword = resultSet.getString("user_password");
				String userRole = resultSet.getString("user_role");
				
				user = new User(userId, userEmail, userName, userPassword, userRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	//Untuk validasi apakah Username unique atau tidak
	public static User getUserByUsername(String username) {
		String query = "SELECT * FROM user WHERE user_name = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		User user = null;
		
		try {
			ps.setString(1, username);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				String userId = resultSet.getString("user_id");
				String userEmail = resultSet.getString("user_email");
				String userName = resultSet.getString("user_name");
				String userPassword = resultSet.getString("user_password");
				String userRole = resultSet.getString("user_role");
				
				user = new User(userId, userEmail, userName, userPassword, userRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	//Untuk fitur admin dapat login hanya dengan input 'admin' untuk email dan password
	public static User getAdmin() {
		String query = "SELECT * FROM user WHERE user_role = 'Admin'";
		PreparedStatement ps = connect.prepareStatement(query);
		User user = null;
		try {
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				String userId = resultSet.getString("user_id");
				String userEmail = resultSet.getString("user_email");
				String userName = resultSet.getString("user_name");
				String userPassword = resultSet.getString("user_password");
				String userRole = resultSet.getString("user_role");
				
				user = new User(userId, userEmail, userName, userPassword, userRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	//Untuk validasi input old password == password dari user sedang menjalankan session
	public static String getPasswordFromUser(String userId) {
		String query = "SELECT user_password FROM user WHERE user_id = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		String password = null;
		
		try {
			ps.setString(1, userId);
			ResultSet resultSet = ps.executeQuery();		
			if(resultSet.next()) {
				password = resultSet.getString("user_password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return password;
	}
	
	//Untuk keperluan fitur Change Profile
	public static boolean updateUser(User oldUser) {
		String query = "UPDATE user SET user_id = ?, user_email = ?, user_name = ?, user_password = ?, user_role = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		int rowsAffected = 0;
		
		try {
			ps.setString(1, oldUser.getUser_id());
			ps.setString(2, oldUser.getUser_email());
			ps.setString(3, oldUser.getUser_name());
			ps.setString(4, oldUser.getUser_password());
			ps.setString(5, oldUser.getUser_role());
			rowsAffected = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsAffected > 0;
	}

}
