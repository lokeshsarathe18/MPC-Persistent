package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager implements DBConnectionUtil {
	private Connection conn = null;

	public DatabaseConnectionManager() {
	}

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
