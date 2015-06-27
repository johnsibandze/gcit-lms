package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	private Connection conn;

	public User() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/library", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int displayLibraries() {
		int libraryCounter = 0;
		try {
			String selectQuery = "select * from tbl_library_branch";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);

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

	public void updateBookCopies(int newNoOfCopies, int bookId, int branchId) {
		try {
			String updateQuery = "UPDATE tbl_book_copies SET noOfCopies=? WHERE bookId=? and branchId=?";

			PreparedStatement pstmt = conn.prepareStatement(updateQuery);
			pstmt.setInt(1, newNoOfCopies);
			pstmt.setInt(2, bookId);
			pstmt.setInt(3, branchId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
