package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {

	public static Connection createConnection() throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		String connection = "jdbc:mysql://localhost:3306/library";
		String user = "root";
		String pass = "root";
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(connection, user, pass);
		conn.setAutoCommit(false);
		return conn;

	}

}
