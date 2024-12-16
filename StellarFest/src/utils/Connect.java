package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	private final String userName = "root";
	private final String passWord = "";
	private final String DATABASE = "stellar_fest_db";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST,DATABASE);
	public ResultSet resultSet;
	public ResultSetMetaData resultSetMetaData;
	
	private Connection connection;
	private Statement state;
	private static Connect connect;
	
	public static Connect getInstance() {
		if(connect == null) return new Connect();
		
		return connect;
	}
	
	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(CONNECTION, userName, passWord); 
			state = connection.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//di gunakan untuk viewing / SELECT
	public ResultSet execute(String query) {
		try {
			resultSet = state.executeQuery(query);
			resultSetMetaData = resultSet.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	
	}
	
	//di gunakan untuk CREATE / DELETE / UPDATE
	public int executeUpdate(String query) {
		int rowsAffected = 0;
		
		try {
			rowsAffected = state.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}
	
	public PreparedStatement prepareStatement (String query) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
	public Connection getConnection() {
	    return connection;
	}
}
