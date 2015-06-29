package com.gcit.training;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Librarian extends User {

	private Scanner sc;

	/** the libary that this librarian manages. */
	private LibraryBranch library;

	public Librarian() {
		super();

		sc = new Scanner(System.in);
		library = new LibraryBranch();
	}

	/** should we go back to the main menu? */
	private boolean startMainMenu;

	public void setStartMainMenu(boolean startMainMenu) {
		this.startMainMenu = startMainMenu;
	}

	public boolean isStartMainMenu() {
		return startMainMenu;
	}

	public void lib1Menu() {

		System.out.println("1) Enter Branch you manage");
		System.out.println("2) Quit to previous");

		int decision = Integer.parseInt(sc.nextLine());
		if (decision == 1) {
			lib2Menu();
		} else {
			this.startMainMenu = true;
		}
		// TODO handle wrong user input
	}

	private void lib2Menu() {
		int libraryCounter = displayLibraries();
		updateBranch(libraryCounter);
	}

	private void updateBranch(int libraryCounter) {
		int libraryChoice = Integer.parseInt(sc.nextLine());

		try {
			if (libraryChoice < libraryCounter) {
				String selectQuery = "SELECT * FROM tbl_library_branch ORDER BY branchId LIMIT 1 OFFSET ?";
				PreparedStatement pstmt = LibMangApp.conn
						.prepareStatement(selectQuery);
				pstmt.setInt(1, libraryChoice - 1);

				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {

					int branchId = rs.getInt("branchId");
					String branchName = rs.getString("branchName");
					String branchAddress = rs.getString("branchAddress");

					setProperties(branchId, branchName, branchAddress);
					System.out.println(branchName);
					lib3Menu();
				}
			} else {
				lib1Menu();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setProperties(int branchId, String branchName,
			String branchAddress) {
		library.setBranchId(branchId);
		library.setBranchName(branchName);
		library.setBranchAddress(branchAddress);
	}

	private void lib3Menu() {
		System.out.println("1) Update the details of the Library");
		System.out.println("2) Add copies of Book to the Branch");
		System.out.println("3) Quit to previous");

		int option = Integer.parseInt(sc.nextLine());

		if (option == 1) {
			option1Menu();
		} else if (option == 2) {
			option2Menu();
		} else {
			lib2Menu();
		}
	}

	private void option1Menu() {
		int branchId = library.getBranchId();
		String branchName = library.getBranchName();

		System.out
				.println("You have chosen to update the Branch with Branch Id: "
						+ branchId + " and Branch Name: " + branchName + ".");
		System.out.println("Enter 'quit' at any prompt to cancel operation.");
		System.out
				.println("Please enter new branch name or enter N/A for no change:");
		branchName = sc.nextLine();

		if (branchName.equals("quit")) {
			lib3Menu();
		}

		System.out
				.println("Please enter new branch address or enter N/A for no change:");
		String branchAddress = sc.nextLine();

		if (branchAddress.equals("quit")) {
			lib3Menu();
		}

		updateBranchInfo(branchName, branchAddress);

	}

	private void updateBranchInfo(String branchName, String branchAddress) {

		branchName = branchName.equals("N/A") ? library.getBranchName()
				: branchName;
		branchAddress = branchAddress.equals("N/A") ? library
				.getBranchAddress() : branchAddress;
		try {
			// Statement stmt = conn.createStatement();
			// String selectQuery = "UPDATE tbl_library_branch SET branchName='"
			// + branchName + "', branchAddress='" + branchAddress
			// + "' WHERE branchId=" + library.getBranchId() + ";";

			String selectQuery = "UPDATE tbl_library_branch SET branchName=?, branchAddress=? WHERE branchId=?";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);
			pstmt.setString(1, branchName);
			pstmt.setString(2, branchAddress);
			pstmt.setInt(3, library.getBranchId());

			pstmt.executeUpdate();
			// stmt.executeUpdate(selectQuery);

			System.out.println("\nsuccessfully updated!");
			System.out.println("\n----------------------------------------\n");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lib3Menu();

	}

	private void option2Menu() {
		int bookCounter = displayBooks(library.getBranchId());

		int bookChoice = Integer.parseInt(sc.nextLine());

		if (bookChoice == bookCounter) {
			// when the user wants to quit this menu
			lib3Menu();
		}

		Book b = getSelectedBook(bookChoice);

		displayBookCopies(b);

		// update the number of copies
		System.out.println("Enter new number of copies:");

		int newNoOfCopies = Integer.parseInt(sc.nextLine());

		updateBookCopies(newNoOfCopies, b.getBookId(), library.getBranchId());
		lib3Menu();

	}

	private void displayBookCopies(Book b) {
		try {
			String branchName = library.getBranchName();

			String selectQuery = "SELECT bookId, title, branchName, noOfCopies FROM ((tbl_book NATURAL JOIN tbl_book_copies) NATURAL JOIN tbl_library_branch) WHERE branchName=? AND title=? AND bookId=?";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);

			pstmt.setString(1, "" + branchName);
			pstmt.setString(2, "" + b.getTitle());
			pstmt.setInt(3, b.getBookId());

			ResultSet rs = pstmt.executeQuery();

			// display the number of copies for the selected book
			if (rs.next()) {
				String noOfCopies = rs.getString("noOfCopies");
				System.out.println("Existing number of copies: " + noOfCopies);
			} else {
				System.out.println("Existing number of copies: 0");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Book getSelectedBook(int bookChoice) {
		Book b = new Book();
		try {
			String selectQuery = "SELECT bookId, title, authorName FROM ((tbl_book NATURAL JOIN tbl_book_authors) NATURAL JOIN tbl_author) ORDER BY bookId LIMIT 1 OFFSET "
					+ (bookChoice - 1);

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);

			ResultSet rs = pstmt.executeQuery();

			int bookId = 0;
			String title = "";
			String authorName = "";

			// get the book that the user selected
			while (rs.next()) {
				bookId = rs.getInt("bookId");
				title = rs.getString("title");
				b.setBookId(bookId);
				b.setTitle(title);

				authorName = rs.getString("authorName");

				System.out.println(title + " by " + authorName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

}
