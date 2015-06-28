package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Administrator extends User {

	Connection conn;

	private Author author;
	private Book book;
	private LibraryBranch libraryBranch;
	private ArrayList<Author> authors;
	private Publisher publisher;
	private ResultSet rs;

	private Scanner sc;

	private boolean startMainMenu;

	public boolean isStartMainMenu() {
		return startMainMenu;
	}

	public void setStartMainMenu(boolean startMainMenu) {
		this.startMainMenu = startMainMenu;
	}

	public Administrator() {
		sc = new Scanner(System.in);
		authors = new ArrayList<Author>();

		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/library", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void mainMenu() {
		System.out.println("1) Add/Update/Delete Book and Author");
		System.out.println("2) Add/Update/Delete Publishers");
		System.out.println("3) Add/Update/Delete Library Branches ");
		System.out.println("4) Add/Update/Delete Borrowers");
		System.out.println("5) Over-ride Due Date for a Book Loan");
		System.out.println("6) Quit to main menu");

		int decision = Integer.parseInt(sc.nextLine());

		if (decision == 1) {
			handleBookAndAuthor();
		} else if (decision == 2) {
			// TODO handle publishers
			handlePublisher();
		} else if (decision == 3) {
			// TODO handle library branches
		} else if (decision == 4) {
			// TODO handle borrowers
		} else if (decision == 5) {
			// TODO handle overriding due date for a book loan
		} else {
			this.startMainMenu = true;
		}

	}

	private void handlePublisher() {
		System.out.println("1) Add Publisher");
		System.out.println("2) Update Publisher");
		System.out.println("3) Delete Publisher");
		System.out.println("4) Quit to previous");

		int choice = Integer.parseInt(sc.nextLine());

		if (choice == 1) {
			addPublisher();
		} else if (choice == 2) {
			// TODO update publisher
		} else if (choice == 3) {
			// TODO delete publisher
		} else {
			mainMenu();
			return;
		}

	}

	private void addPublisher() {
		// int publisherChoice = displayPublishers();
		// updatePublisherInfo(publisherChoice);

		publisher = new Publisher();

		System.out.println("Please Enter Publisher Name");
		String publisherName = sc.nextLine();

		System.out.println("Please Enter Publisher Address");
		String publisherAddress = sc.nextLine();

		//TODO validate phone number
		System.out.println("Please Enter Publisher Phone number");
		String publisherPhone = sc.nextLine();

		publisher = new Publisher();
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhone(publisherPhone);

		insertPublisherToDatabase();

		System.out.println("Successfully Added Publisher");
	}

	private void insertPublisherToDatabase() {
		try {
			String selectQuery = "insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?, ?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setString(1, publisher.getPublisherName());
			pstmt.setString(2, publisher.getPublisherAddress());
			pstmt.setString(3, publisher.getPublisherPhone());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int displayPublishers() {
		int publisherCounter = 0;
		try {
			String selectQuery = "select * from tbl_publisher";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);

			rs = pstmt.executeQuery();

			publisherCounter = 1;
			while (rs.next()) {
				String publisherName = rs.getString("publisherName");
				String publisherAddress = rs.getString("publisherAddress");

				System.out.println(publisherCounter + ") " + publisherName
						+ ", " + publisherAddress);
				publisherCounter++;
			}

			System.out.println(publisherCounter + ") Quit to previous");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return publisherCounter;
	}

	private void handleBookAndAuthor() {
		System.out.println("1) Add Book and Author");
		System.out.println("2) Update Book and Author");
		System.out.println("3) Delete Book and Author");
		System.out.println("4) Quit to previous");

		int choice = Integer.parseInt(sc.nextLine());

		if (choice == 1) {
			addBookAndAuthor();
		} else if (choice == 2) {
			updateBookAndAuthor();
		} else if (choice == 3) {
			deleteBookAndAuthor();
		} else {
			mainMenu();
			return;
		}

		mainMenu();

	}

	private void deleteBookAndAuthor() {
		selectLibrary();

		selectBook();

		findAuthors();

		deleteBookFromDatabase();
		System.out.println("sucessfully deleted book!");
	}

	private void updateBookAndAuthor() {

		selectLibrary();

		selectBook();

		updateBookInDatabase();
		System.out.println("sucessfully updated book!");
	}

	private void addBookAndAuthor() {
		System.out
				.println("You want to enter an new book and author. Follow the instructions below. Enter quit any time to go to previous menu");

		System.out.println("Please enter title of book:");
		String title = sc.nextLine();

		if (title.equals("quit")) {
			handleBookAndAuthor();
			return;
		}

		System.out.println("Please enter author name");
		String authorName = sc.nextLine();
		if (authorName.equals("quit")) {
			handleBookAndAuthor();
			return;
		}

		System.out.println("Please enter branch ID");
		String branchIdString = sc.nextLine();
		if (branchIdString.equals("quit")) {
			handleBookAndAuthor();
			return;
		}

		int branchId = Integer.parseInt(branchIdString);
		libraryBranch = new LibraryBranch();
		libraryBranch.setBranchId(branchId);
		findLibraryBranchInfo();

		// TODO handle cases where the author already exists. If the author
		// exists, we will ask for their ID.

		// add to author table
		author = new Author();
		author.setAuthorName(authorName);
		addToAuthor();
		findAuthorId();

		// add to book table
		book = new Book();
		book.setAuthorName(authorName);
		book.setTitle(title);
		addToBook();
		findLastBookId();

		// add book id and author id to book authors
		addToBookAuthors();

		// add copies of this book to the chosen library branch
		addToBookCopies();
		System.out.println("sucessfully added book!");
	}

	private void deleteBookFromDatabase() {
		try {
			// delete book from book authors table
			String deleteQuery = "delete from tbl_book_authors where bookId=?";
			PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
			pstmt.setInt(1, book.getBookId());
			pstmt.executeUpdate();

			// delete book from book table
			deleteQuery = "delete from tbl_book where bookId=?";
			pstmt = conn.prepareStatement(deleteQuery);
			pstmt.setInt(1, book.getBookId());
			pstmt.executeUpdate();

			// delete book from book copies table
			deleteQuery = "delete from tbl_book_copies where bookId=?";
			pstmt = conn.prepareStatement(deleteQuery);
			pstmt.setInt(1, book.getBookId());
			pstmt.executeUpdate();

			// only delete authors if they don't have other books
			deleteAuthors();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteAuthors() {
		for (Author author : authors) {
			if (!author.hasBooks()) {
				deleteAuthor(author);
			}
		}
	}

	private void deleteAuthor(Author author) {
		try {
			String deleteQuery = "delete from tbl_author where authorId=?";
			PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
			pstmt.setInt(1, author.getAuthorId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void findAuthors() {
		try {
			String selectQuery = "SELECT * FROM (tbl_author NATURAL JOIN tbl_book_authors) where bookId=?";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setInt(1, book.getBookId());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int authorId = rs.getInt("authorId");
				String authorName = rs.getString("authorName");

				Author author = new Author(authorId, authorName);

				authors.add(author);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateBookInDatabase() {
		System.out.println("enter new book title");
		String title = sc.nextLine();

		System.out.println("enter author name");
		String authorName = sc.nextLine();

		try {
			String updateQuery = "update ((tbl_book natural join tbl_book_authors) natural join tbl_author) set title=?, authorName=? where bookId=?";

			PreparedStatement pstmt = conn.prepareStatement(updateQuery);

			pstmt.setString(1, title);
			pstmt.setString(2, authorName);
			pstmt.setInt(3, book.getBookId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void selectBook() {
		int bookCounter = displayBooks(libraryBranch.getBranchId());

		int bookChoice = Integer.parseInt(sc.nextLine());

		if (bookCounter == bookChoice) {
			handleBookAndAuthor();
			return;
		}

		updateBookInfo(bookChoice);
	}

	private void selectLibrary() {
		int libraryCounter = displayLibraries();

		int libraryChoice = Integer.parseInt(sc.nextLine());

		if (libraryChoice == libraryCounter) {
			handleBookAndAuthor();
			return;
		}

		updateLibraryInfo(libraryChoice);

	}

	private void updateBookInfo(int bookChoice) {

		try {
			String selectQuery = "SELECT * FROM ((((tbl_book NATURAL JOIN tbl_book_authors) NATURAL JOIN tbl_author) natural join tbl_book_copies) natural join tbl_library_branch) where branchId=?";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setInt(1, libraryBranch.getBranchId());

			ResultSet rs = pstmt.executeQuery();

			int bookCounter = 1;
			while (rs.next()) {
				if (bookCounter == bookChoice) {
					int bookId = rs.getInt("bookId");
					String title = rs.getString("title");
					String authorName = rs.getString("authorName");
					int pubId = rs.getInt("pubId");

					book = new Book(bookId, title, authorName);
					book.setPubId(pubId);

					return;
				}

				bookCounter++;
			}

			System.out.println(bookCounter + ") Quit to cancel operation");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Update the details of brach to the chosen branch. */
	private void updateLibraryInfo(int libraryChoice) {

		try {
			String selectQuery = "select * from tbl_library_branch";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);

			ResultSet rs = pstmt.executeQuery();

			int libraryCounter = 1;
			while (rs.next()) {
				if (libraryCounter == libraryChoice) {
					int branchId = rs.getInt("branchId");
					String branchName = rs.getString("branchName");
					String branchAddress = rs.getString("branchAddress");

					libraryBranch = new LibraryBranch(branchId, branchName,
							branchAddress);

					System.out.println(branchName + ", " + branchAddress);

					return;
				}

				libraryCounter++;
			}

			System.out.println(libraryCounter + ") Quit to previous");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addToBookCopies() {
		try {
			String selectQuery = "insert into tbl_book_copies values (?, ?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setInt(1, book.getBookId());
			pstmt.setInt(2, libraryBranch.getBranchId());
			pstmt.setInt(3, 1);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** add the book id and the author id to book authors record. */
	private void addToBookAuthors() {
		try {
			String selectQuery = "insert into tbl_book_authors values (?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setInt(1, book.getBookId());
			pstmt.setInt(2, author.getAuthorId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** find the id of the book that has just been added. */
	private void findLastBookId() {
		try {
			String selectQuery = "select * from tbl_book";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

			}
			rs.last();

			int bookId = rs.getInt("bookId");

			book.setBookId(bookId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** add book to the book table. */
	private void addToBook() {
		try {
			String selectQuery = "insert into tbl_book (title) values (?)";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setString(1, book.getTitle());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Finds the author id of the last author on the table. Called after adding
	 * an author to retrieve their id.
	 */
	private void findAuthorId() {

		try {
			String selectQuery = "select * from tbl_author where authorName=?";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setString(1, author.getAuthorName());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

			}
			rs.last();

			int authorId = rs.getInt("authorId");
			author.setAuthorId(authorId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Add a new record to the author table. */
	private void addToAuthor() {
		try {
			String selectQuery = "insert into tbl_author (authorName) values (?)";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setString(1, author.getAuthorName());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * find the name and address of the branch that the admin identified by the
	 * branch id.
	 */
	public void findLibraryBranchInfo() {
		try {
			String selectQuery = "select * from tbl_library_branch where branchId=?";

			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setInt(1, libraryBranch.getBranchId());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String branchName = rs.getString("branchName");
				String branchAddress = rs.getString("branchAddress");

				libraryBranch.setBranchName(branchName);
				libraryBranch.setBranchAddress(branchAddress);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
