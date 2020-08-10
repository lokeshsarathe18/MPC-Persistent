package com.util;

import java.sql.*;


//Override and implement all the methods of DBConnectionUtil Interface in this class 
public class DatabaseConnectionManager implements DBConnectionUtil {
	private Connection conn = null;
	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			System.out.println("Exception in getConnection: " + e);
		}
		return conn;
	}

	@Override
	public void closeConnection() throws SQLException {
		conn.close();		
	}
			
}
