package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LibMangApp {

	private Scanner sc;

	private User user;

	public LibMangApp() {
		sc = new Scanner(System.in);
	}

	public static void main(String[] args) {
		new LibMangApp().run();
	}

	public void run() {
		mainMenu();
	}

	private void mainMenu() {
		System.out
				.println("Welcome to the GCIT Library Management System. Which category of a user are you\n");

		System.out.println("1) Librarian");
		System.out.println("2) Administrator");
		System.out.println("3) Borrower");

		int userInt = Integer.parseInt(sc.nextLine());

		switch (userInt) {
		case 1:
			lib1Menu();
			break;
		case 2:
			// TODO handle administrator
			// user = new Administrator();
			break;
		case 3:
			// TODO handle borrower
			// user = new Borrower();
			break;
		}

	}

	private void lib1Menu() {
		user = new Librarian();
		System.out.println("1) Enter Branch you manage");
		System.out.println("2) Quit to previous");

		int decision = Integer.parseInt(sc.nextLine());
		if (decision == 1) {
			lib2Menu();
		} else {
			mainMenu();
		}
		// TODO handle wrong input
	}

	private void lib2Menu() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/library", "root", "");
			Statement stmt = conn.createStatement();
			String selectQuery = "select * from tbl_library_branch";
			ResultSet rs = stmt.executeQuery(selectQuery);

			int libraryCounter = 1;
			while (rs.next()) {
				String branchName = rs.getString("branchName");
				String branchAddress = rs.getString("branchAddress");

				System.out.println(libraryCounter + ") " + branchName + ", "
						+ branchAddress);
				libraryCounter++;
			}

			int libraryChoice = Integer.parseInt(sc.nextLine());

			if (libraryChoice < libraryCounter) {
				selectQuery = "SELECT * FROM tbl_library_branch ORDER BY branchId LIMIT 1 OFFSET "
						+ (libraryChoice - 1);
				rs = stmt.executeQuery(selectQuery);
				while (rs.next()) {
					String branchName = rs.getString("branchName");
					String branchAddress = rs.getString("branchAddress");
					int branchId = rs.getInt("branchId");
					((Librarian) user).setBranchName(branchName);
					((Librarian) user).setBranchAddress(branchAddress);
					((Librarian) user).setBranchId(branchId);
					System.out.println(branchName);
					lib3Menu();
				}
			} else {
				lib1Menu();
			}
			// TODO handle wrong input

			// Scanner scan = new Scanner(System.in);
			// System.out.println("Enter a new author: ");
			// String authorName = scan.nextLine();
			//
			// String createQuery =
			// "insert into tbl_author (authorName) values('"
			// + authorName + "')";
			// stmt.executeUpdate(createQuery);

			// deletion
			// PreparedStatement st =
			// conn.prepareStatement("DELETE FROM tbl_author WHERE authorName = ?");
			// st.setString(1,"dsfdsa");
			// st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		int branchId = ((Librarian) user).getBranchId();
		String branchName = ((Librarian) user).getBranchName();
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
		branchName = branchName.equals("N/A") ? ((Librarian) user)
				.getBranchName() : branchName;
		branchAddress = branchAddress.equals("N/A") ? ((Librarian) user)
				.getBranchAddress() : branchAddress;
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/library", "root", "");
			Statement stmt = conn.createStatement();
			String selectQuery = "UPDATE tbl_library_branch SET branchName='"
					+ branchName + "', branchAddress='" + branchAddress
					+ "' WHERE branchId=" + ((Librarian) user).getBranchId()
					+ ";";
			stmt.executeUpdate(selectQuery);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("\nsuccessfully updated!");
		System.out.println("\n----------------------------------------\n");

		lib3Menu();

	}

	private void option2Menu() {
		System.out
				.println("Pick the Book you want to add copies of, to your branch:");

		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/library", "root", "");
			Statement stmt = conn.createStatement();
			String selectQuery = "SELECT title, authorName FROM ((tbl_book NATURAL JOIN tbl_book_authors) NATURAL JOIN tbl_author) ORDER BY bookId";
			ResultSet rs = stmt.executeQuery(selectQuery);

			int bookCounter = 1;
			while (rs.next()) {
				String title = rs.getString("title");
				String authorName = rs.getString("authorName");

				System.out.println(bookCounter + ") " + title + " by "
						+ authorName);
				bookCounter++;
			}

			int bookChoice = Integer.parseInt(sc.nextLine());

			// selectQuery =
			// "SELECT * FROM tbl_library_branch ORDER BY branchId LIMIT 1 OFFSET "
			// + (bookChoice - 1);

			selectQuery = "SELECT bookId, title, authorName FROM ((tbl_book NATURAL JOIN tbl_book_authors) NATURAL JOIN tbl_author) ORDER BY bookId LIMIT 1 OFFSET "
					+ (bookChoice - 1);
			rs = stmt.executeQuery(selectQuery);

			int bookId = 0;
			String title = "";
			String authorName = "";

			// get the book that the user selected
			while (rs.next()) {
				bookId = rs.getInt("bookId");
				title = rs.getString("title");
				authorName = rs.getString("authorName");

				System.out.println(title + " by " + authorName);
			}

			String branchName = ((Librarian) user).getBranchName();

			selectQuery = "SELECT bookId, title, branchName, noOfCopies FROM ((tbl_book NATURAL JOIN tbl_book_copies) NATURAL JOIN tbl_library_branch) WHERE branchName='"
					+ branchName
					+ "' AND title='"
					+ title
					+ "' AND bookId="
					+ bookId;

			rs = stmt.executeQuery(selectQuery);

			// now display the number of copies of the book
			while (rs.next()) {
				String noOfCopies = rs.getString("noOfCopies");
				System.out.println("Existing number of copies: " + noOfCopies);
			}

			// update the number of copies
			System.out.println("Enter new number of copies:");

			int newNoOfCopies = Integer.parseInt(sc.nextLine());

			updateBook(newNoOfCopies, bookId);
			lib3Menu();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updateBook(int newNoOfCopies, int bookId) {

		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/library", "root", "");
			Statement stmt = conn.createStatement();
			String selectQuery = "UPDATE tbl_book_copies SET noOfCopies="
					+ newNoOfCopies + " WHERE bookId=" + bookId;
			stmt.executeUpdate(selectQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
