package com.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnectionUtil {
	
	String URL = "jdbc:mysql://localhost:3306/questiondb?allowPublicKeyRetrieval=true&useSSL=false";
	String DRIVER = "com.mysql.jdbc.Driver";
	String USER = "root";
	String PASSWORD = "Password";
	
	public Connection getConnection() throws ClassNotFoundException, SQLException;
	public void closeConnection() throws SQLException;
}
