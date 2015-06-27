package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Borrower extends User {

	private Scanner sc;

	private boolean startMainMenu;

	private ResultSet rs;

	/** the library branch that this user selects. */
	private Library library;

	/** The book that this borrower wants to check out or return. */
	private Book book;

	private int cardNo;
	private String name;
	private String address;
	private String phone;

	private Connection conn;

	public Borrower() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/library", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
		// TODO handle invalid input from user
	}

	private void checkOutBook() {
		System.out.println("Pick the Branch you want to check out from:");

		int libraryCounter = displayLibraries();

		int choice = Integer.parseInt(sc.nextLine());

		// user wants to quit this menu
		if (choice == libraryCounter) {
			borr1Menu();
		}

		updateLibraryInfo(choice);

		displayBooks();

		int bookChoice = Integer.parseInt(sc.nextLine());

		updateBookInfo(bookChoice);

		addToLoan();

		borr1Menu();

	}

	/** add the book to the book loans table. */
	private void addToLoan() {
		try {
			String selectQuery = "insert into tbl_book_loans values (?, ?, ?, ?, ?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);

			pstmt.setInt(1, book.getBookId());
			pstmt.setInt(2, library.getBranchId());
			pstmt.setInt(3, cardNo);

			pstmt.setString(4, dateAfterToday(0));
			pstmt.setString(5, dateAfterToday(7));
			pstmt.setString(6, null);

			pstmt.executeUpdate();

			// let the user know that the check out was successful.
			System.out.println("Book loan successful!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param dayOffset
	 *            number of days from today
	 * @return date corresponding to offset days after today
	 */
	private String dateAfterToday(int dayOffset) {
		Date date = new Date();
		java.sql.Date today = new java.sql.Date(date.getTime());

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_YEAR, dayOffset);

		java.sql.Date nextWeek = new java.sql.Date(cal.getTimeInMillis());

		return nextWeek.toString();
	}

	private void updateBookInfo(int bookChoice) {
		try {
			while (rs.previous()) {

			}

			int counter = 1;
			while (rs.next()) {
				if (counter == bookChoice) {
					int bookId = rs.getInt("bookId");
					String title = rs.getString("title");
					String authorName = rs.getString("authorName");

					book = new Book(bookId, title, authorName);

				}

				counter++;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Only display books that are available in this library. */
	private int displayBooks() {
		int bookCounter = 0;
		System.out
				.println("Pick the Book you want to add copies of, to your branch:");

		try {
			String selectQuery = "select bookId, title, authorName from (((((tbl_author NATURAL JOIN tbl_book_authors) NATURAL JOIN tbl_book) NATURAL JOIN tbl_book_loans) NATURAL JOIN tbl_borrower) NATURAL JOIN tbl_library_branch) where branchId=?";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setInt(1, library.getBranchId());

			rs = pstmt.executeQuery();

			bookCounter = 1;
			while (rs.next()) {
				String title = rs.getString("title");
				String authorName = rs.getString("authorName");

				System.out.println(bookCounter + ") " + title + " by "
						+ authorName);
				bookCounter++;
			}

			System.out.println(bookCounter + ") Quit to cancel operation");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookCounter;
	}

	/** update the library that the user wants to select. */
	private void updateLibraryInfo(int choice) {

		try {
			String selectQuery = "select * from tbl_library_branch";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);

			ResultSet rs = pstmt.executeQuery();

			int counter = 1;
			while (rs.next()) {

				if (counter == choice) {
					int branchId = rs.getInt("branchId");
					String branchName = rs.getString("branchName");
					String branchAddress = rs.getString("branchAddress");

					library = new Library(branchId, branchName, branchAddress);

					return;
				}
				counter++;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
