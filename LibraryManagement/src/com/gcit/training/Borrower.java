package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Borrower extends User {

	private Scanner sc;

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

		} else if (choice == 2) {

		} else {
			return;
		}
		// TODO handle invalid input
	}

}
