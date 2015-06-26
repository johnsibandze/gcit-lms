package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Borrower extends User {

	private Scanner sc;

	private boolean startMainMenu;

	private int cardNo;
	private String name;
	private String address;
	private String phone;

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isStartMainMenu() {
		return startMainMenu;
	}

	public void setStartMainMenu(boolean startMainMenu) {
		this.startMainMenu = startMainMenu;
	}

	/**
	 * Validate the card number of borrower
	 */
	public boolean validateCardNo(int cardNo) {

		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/library", "root", "");

			String selectQuery = "select * from tbl_borrower WHERE cardNo=?";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setString(1, "" + cardNo);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				if (cardNo == rs.getInt("cardNo")) {
					this.cardNo = cardNo;
					this.name = rs.getString("name");
					this.address = rs.getString("address");
					this.phone = rs.getString("phone");
					return true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	public void enterCardNo() {
		sc = new Scanner(System.in);
		System.out.println("Enter the your Card Number:");
		int cardNo = Integer.parseInt(sc.nextLine());

		while (!validateCardNo(cardNo)) {
			System.out.println("Please enter a valid card number");
			cardNo = Integer.parseInt(sc.nextLine());
			validateCardNo(cardNo);
		}

		// for debugging
		System.out.println("Updated successfully");

		borr1Menu();
	}

	private void borr1Menu() {
		System.out.println("1) Check out a book");
		System.out.println("2) Return a Book");
		System.out.println("3) Quit to Previous");

		int choice = Integer.parseInt(sc.nextLine());

		if (choice == 1) {
			checkOutBook();
		} else if (choice == 2) {

		} else {
			startMainMenu = true;
		}
		// TODO handle invalid input
	}

	private void checkOutBook() {
		System.out.println("Pick the Branch you want to check out from:");

		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/library", "root", "");

			String selectQuery = "select * from tbl_library_branch";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			// pstmt.setString(1, "1");
			// pstmt.setString(1, "2");

			ResultSet rs = pstmt.executeQuery();

			// TODO This method will be made in the User class
			int libraryCounter = 1;
			while (rs.next()) {
				String branchName = rs.getString("branchName");
				String branchAddress = rs.getString("branchAddress");

				System.out.println(libraryCounter + ") " + branchName + ", "
						+ branchAddress);
				libraryCounter++;
			}

			System.out.println(libraryCounter + " Quit to previous");

			int choice = Integer.parseInt(sc.nextLine());

			if (choice == libraryCounter) {
				borr1Menu();
			}
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
