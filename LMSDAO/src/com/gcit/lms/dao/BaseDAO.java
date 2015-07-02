package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {

	String driver = "com.mysql.jdbc.Driver";
	String connection = "jdbc:mysql://localhost:3306/library";
	String user = "root";
	String pass = "root";

	public Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(connection, user, pass);
		return conn;
	}

	public void save(String query, Object[] vals) throws Exception {
		Connection conn = getConnection();

		PreparedStatement stmt = conn.prepareStatement(query);
		int count = 1;
		for (Object o : vals) {
			stmt.setObject(count, o);
			count++;
		}

		stmt.executeUpdate();
	}

	public List<T> read(String query, Object[] vals) throws Exception {
		// List<T> objects = new ArrayList<T>();
		Connection conn = getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);

		if (vals != null) {
			int count = 1;
			for (Object o : vals) {
				stmt.setObject(count, o);
				count++;
			}
		}
		ResultSet rs = stmt.executeQuery();
		return extractData(rs);
	}

	public abstract List<T> extractData(ResultSet rs) throws Exception;
}
