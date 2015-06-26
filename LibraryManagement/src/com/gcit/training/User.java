package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	public int displayLibraries() {
		int libraryCounter = 0;
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/library", "root", "");
			String selectQuery = "select * from tbl_library_branch";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			// pstmt.setString(1, "1");
			// pstmt.setString(1, "2");

			ResultSet rs = pstmt.executeQuery();

			libraryCounter = 1;
			while (rs.next()) {
				String branchName = rs.getString("branchName");
				String branchAddress = rs.getString("branchAddress");

				System.out.println(libraryCounter + ") " + branchName + ", "
						+ branchAddress);
				libraryCounter++;
			}

			System.out.println(libraryCounter + ") Quit to previous");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return libraryCounter;
	}

}
