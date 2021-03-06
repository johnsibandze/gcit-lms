package com.gcit.training;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Administrator extends User {

	private Author author;
	private Book book;
	private LibraryBranch libraryBranch;
	private ArrayList<Author> authors;
	private Publisher publisher;
	private ResultSet rs;
	private Borrower borrower;
	private BookLoan bookLoan;

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
			handlePublisher();
		} else if (decision == 3) {
			handleLibraryBranch();
		} else if (decision == 4) {
			handleBorrower();
		} else if (decision == 5) {
			overrideDueDate();
		} else {
			this.startMainMenu = true;
		}

	}

	private void overrideDueDate() {
		int bookLoanCounter = displayBookLoans();

		System.out.println("there are " + bookLoanCounter + " books");

		int bookLoanChoice = Integer.parseInt(sc.nextLine());
		if (bookLoanChoice == bookLoanCounter) {
			mainMenu();
			return;
		}

		updateBookLoanInfo(bookLoanChoice);

		updateBookLoanOnDatabase();
	}

	private void updateBookLoanOnDatabase() {

		System.out.println("Please enter new due date");
		String dueDateString = sc.nextLine();

		Date dueDate = Date.valueOf(dueDateString);

		try {
			String updateQuery = "update tbl_book_loans set dueDate=? where bookId=? and branchId=? and cardNo=?";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(updateQuery);
			pstmt.setDate(1, dueDate);
			pstmt.setInt(2, bookLoan.getBookId());
			pstmt.setInt(3, bookLoan.getBranchId());
			pstmt.setInt(4, bookLoan.getCardNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int displayBookLoans() {
		int borrowerCounter = 0;
		try {
			String selectQuery = "select * from ((((tbl_book_loans natural join tbl_book) natural join tbl_borrower) natural join tbl_book_authors) natural join tbl_author)";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);

			rs = pstmt.executeQuery();

			borrowerCounter = 1;
			while (rs.next()) {
				String title = rs.getString("title");
				String authorName = rs.getString("authorName");
				String name = rs.getString("name");

				System.out.println(title + " " + authorName + " borrowed by "
						+ name);

				borrowerCounter++;
			}

			System.out.println(borrowerCounter + ") Quit to previous");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return borrowerCounter;
	}

	private void handleBorrower() {
		System.out.println("1) Add Borrower");
		System.out.println("2) Update Borrower");
		System.out.println("3) Delete Borrower");
		System.out.println("4) Quit to previous");

		int choice = Integer.parseInt(sc.nextLine());

		if (choice == 1) {
			addBorrower();
		} else if (choice == 2) {
			updateBorrower();
		} else if (choice == 3) {
			deleteBorrower();
		} else {
			mainMenu();
			return;
		}

		mainMenu();

	}

	private void deleteBorrower() {
		int libraryCounter = displayBorrowers();

		int libraryChoice = Integer.parseInt(sc.nextLine());

		if (libraryCounter == libraryChoice) {
			handleBorrower();
			return;
		}

		updateBorrowerInfo(libraryChoice);

		deleteBorrowerFromDatabase();

		System.out.println("Successfully deleted borrower");
	}

	private void updateBorrower() {
		int borrowerCounter = displayBorrowers();

		int borrowerChoice = Integer.parseInt(sc.nextLine());
		if (borrowerChoice == borrowerCounter) {
			handleBorrower();
			return;
		}

		updateBorrowerInfo(borrowerChoice);

		updateBorrowerOnDatabase();
	}

	private void addBorrower() {
		System.out.println("Please enter name of borrower");
		String name = sc.nextLine();

		System.out.println("Please enter address of borrower");
		String address = sc.nextLine();

		System.out.println("Please enter phone number of borrower");
		String phone = sc.nextLine();

		borrower = new Borrower();
		borrower.setName(name);
		borrower.setAddress(address);
		borrower.setPhone(phone);

		insertBorrowerToDatabase();

		System.out.println("sucessfully added borrower");
	}

	private void deleteBorrowerFromDatabase() {
		String deleteQuery = "delete from tbl_borrower where cardNo=?";

		try {
			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(deleteQuery);
			pstmt.setInt(1, borrower.getCardNo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateBookLoanInfo(int bookLoanChoice) {
		try {
			int bookLoanCounter = 1;
			rs.beforeFirst();
			while (rs.next()) {
				if (bookLoanCounter == bookLoanChoice) {

					int bookId = rs.getInt("bookId");
					int branchId = rs.getInt("branchId");
					int cardNo = rs.getInt("cardNo");
					Date dateOut = rs.getDate("dateOut");
					Date dueDate = rs.getDate("dueDate");
					Date dateIn = rs.getDate("dateIn");

					bookLoan = new BookLoan();

					bookLoan.setBookId(bookId);
					bookLoan.setBranchId(branchId);
					bookLoan.setCardNo(cardNo);
					bookLoan.setDateOut(dateOut);
					bookLoan.setDueDate(dueDate);
					bookLoan.setDateIn(dateIn);

					return;
				}

				bookLoanCounter++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insertBorrowerToDatabase() {
		try {
			String insertQuery = "insert into tbl_borrower (name, address, phone) values (?, ?, ?)";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(insertQuery);
			pstmt.setString(1, borrower.getName());
			pstmt.setString(2, borrower.getAddress());
			pstmt.setString(3, borrower.getPhone());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateBorrowerInfo(int borrowerChoice) {
		try {
			int borrowerCounter = 1;
			rs.beforeFirst();
			while (rs.next()) {
				if (borrowerCounter == borrowerChoice) {
					int cardNo = rs.getInt("cardNo");
					String name = rs.getString("name");
					String address = rs.getString("address");
					String phone = rs.getString("phone");

					borrower = new Borrower();
					borrower.setCardNo(cardNo);
					borrower.setName(name);
					borrower.setAddress(address);
					borrower.setPhone(phone);

					return;
				}

				borrowerCounter++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateBorrowerOnDatabase() {
		System.out.println("Please enter new name");
		String name = sc.nextLine();

		System.out.println("Please enter new address");
		String address = sc.nextLine();

		System.out.println("Please enter new phone number");
		String phone = sc.nextLine();

		try {
			String updateQuery = "update tbl_borrower set name=?, address=?, phone=? where cardNo=?";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(updateQuery);

			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, phone);
			pstmt.setInt(4, borrower.getCardNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handleLibraryBranch() {
		System.out.println("1) Add Library Branch");
		System.out.println("2) Update Library Branch");
		System.out.println("3) Delete Library Branch");
		System.out.println("4) Quit to previous");

		int choice = Integer.parseInt(sc.nextLine());

		if (choice == 1) {
			addBranch();
		} else if (choice == 2) {
			updateBranch();
		} else if (choice == 3) {
			// TODO delete branch
			deleteBranch();
		} else {
			mainMenu();
			return;
		}

		mainMenu();

	}

	private void deleteBranch() {
		int libraryCounter = displayLibraries();

		int libraryChoice = Integer.parseInt(sc.nextLine());

		if (libraryCounter == libraryChoice) {
			handleLibraryBranch();
			return;
		}

		updateLibraryInfo(libraryChoice);

		deleteLibraryFromDatabase();

		System.out.println("Successfully deleted branch");
	}

	private void updateBranch() {
		int libraryCounter = displayLibraries();

		int libraryChoice = Integer.parseInt(sc.nextLine());
		if (libraryChoice == libraryCounter) {
			handleLibraryBranch();
			return;
		}

		updateLibraryInfo(libraryChoice);

		updateLibraryOnDatabase();
	}

	private void addBranch() {

		System.out.println("Please enter branch name");
		String branchName = sc.nextLine();

		System.out.println("Please enter branch address");
		String branchAddress = sc.nextLine();

		libraryBranch = new LibraryBranch();
		libraryBranch.setBranchName(branchName);
		libraryBranch.setBranchAddress(branchAddress);

		insertLibraryBranchToDatabase();

		System.out.println("sucessfully added library branch");

	}

	private void deleteLibraryFromDatabase() {
		String deleteQuery = "delete from tbl_library_branch where branchId=?";

		try {
			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(deleteQuery);
			pstmt.setInt(1, libraryBranch.getBranchId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateLibraryOnDatabase() {
		System.out.println("Please enter new library branch name");
		String branchName = sc.nextLine();

		System.out.println("Please enter new library branch address");
		String branchAddress = sc.nextLine();

		try {
			String updateQuery = "update tbl_library_branch set branchName=?, branchAddress=? where branchId=?";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(updateQuery);

			pstmt.setString(1, branchName);
			pstmt.setString(2, branchAddress);
			pstmt.setInt(3, libraryBranch.getBranchId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updatePublisherOnDatabase() {
		System.out.println("Please enter new publisher name");
		String publisherName = sc.nextLine();

		System.out.println("Please enter new publisher address");
		String publisherAddress = sc.nextLine();

		// TODO check if this is a valid phone number
		System.out.println("Please enter new publisher phone number");
		String publisherPhone = sc.nextLine();

		try {
			String updateQuery = "update tbl_publisher set publisherName=?, publisherAddress=?, publisherPhone=? where publisherId=?";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(updateQuery);

			pstmt.setString(1, publisherName);
			pstmt.setString(2, publisherAddress);
			pstmt.setString(3, publisherPhone);
			pstmt.setInt(4, publisher.getPublisherId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insertLibraryBranchToDatabase() {
		try {
			String insertQuery = "insert into tbl_library_branch (branchName, branchAddress) values (?, ?)";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(insertQuery);
			pstmt.setString(1, libraryBranch.getBranchName());
			pstmt.setString(2, libraryBranch.getBranchAddress());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deletePublisher() {
		int publisherCounter = displayPublishers();

		int publisherChoice = Integer.parseInt(sc.nextLine());
		if (publisherChoice == publisherCounter) {
			handlePublisher();
			return;
		}
		updatePublisherInfo(publisherChoice);

		deletePublisherFromDatabase();

		System.out.println("successfully deleted publisher!");

	}

	private void updatePublisher() {
		int publisherCounter = displayPublishers();

		int publisherChoice = Integer.parseInt(sc.nextLine());
		if (publisherChoice == publisherCounter) {
			handlePublisher();
			return;
		}
		updatePublisherInfo(publisherChoice);
		updatePublisherOnDatabase();
	}

	private void addPublisher() {

		System.out.println("Please Enter Publisher Name");
		String publisherName = sc.nextLine();

		System.out.println("Please Enter Publisher Address");
		String publisherAddress = sc.nextLine();

		// TODO validate phone number
		System.out.println("Please Enter Publisher Phone number");
		String publisherPhone = sc.nextLine();

		publisher = new Publisher();
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhone(publisherPhone);

		insertPublisherToDatabase();

		System.out.println("Successfully Added Publisher");
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
			updatePublisher();
		} else if (choice == 3) {
			// TODO check if this publisher currently has books on our records
			// before deleting them
			deletePublisher();
		} else {
			mainMenu();
			return;
		}

		mainMenu();

	}

	private void deletePublisherFromDatabase() {

		try {
			String deleteQuery = "delete from tbl_publisher where publisherId=?";
			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(deleteQuery);
			pstmt.setInt(1, publisher.getPublisherId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updatePublisherInfo(int publisherChoice) {

		try {
			int publisherCounter = 1;
			rs.beforeFirst();
			while (rs.next()) {
				if (publisherCounter == publisherChoice) {
					int publisherId = rs.getInt("publisherId");
					String publisherName = rs.getString("publisherName");
					String publisherAddress = rs.getString("publisherAddress");
					String publisherPhone = rs.getString("publisherPhone");

					publisher = new Publisher();
					publisher.setPublisherId(publisherId);
					publisher.setPublisherName(publisherName);
					publisher.setPublisherAddress(publisherAddress);
					publisher.setPublisherPhone(publisherPhone);

					return;
				}

				publisherCounter++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insertPublisherToDatabase() {
		try {
			String insertQuery = "insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?, ?, ?)";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(insertQuery);
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

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);

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
			// TODO check if this book is loaned out before deleting it
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

		updateBookOnDatabase();
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

		// pick the branch you want to add the book to
		System.out.println("Please select a library branch to add the book");
		int libraryCounter = displayLibraries();
		int libraryChoice = Integer.parseInt(sc.nextLine());
		if (libraryChoice == libraryCounter) {
			handleBorrower();
			return;
		}
		updateLibraryInfo(libraryChoice);

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
			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(deleteQuery);
			pstmt.setInt(1, book.getBookId());
			pstmt.executeUpdate();

			// delete book from book table
			deleteQuery = "delete from tbl_book where bookId=?";
			pstmt = LibMangApp.conn.prepareStatement(deleteQuery);
			pstmt.setInt(1, book.getBookId());
			pstmt.executeUpdate();

			// delete book from book copies table
			deleteQuery = "delete from tbl_book_copies where bookId=?";
			pstmt = LibMangApp.conn.prepareStatement(deleteQuery);
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
			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(deleteQuery);
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

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);
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

	private void updateBookOnDatabase() {
		System.out.println("enter new book title");
		String title = sc.nextLine();

		System.out.println("enter author name");
		String authorName = sc.nextLine();

		try {
			String updateQuery = "update ((tbl_book natural join tbl_book_authors) natural join tbl_author) set title=?, authorName=? where bookId=?";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(updateQuery);

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

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);
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

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);

			ResultSet rs = pstmt.executeQuery();

			int libraryCounter = 1;
			while (rs.next()) {
				if (libraryCounter == libraryChoice) {
					int branchId = rs.getInt("branchId");
					String branchName = rs.getString("branchName");
					String branchAddress = rs.getString("branchAddress");

					libraryBranch = new LibraryBranch(branchId, branchName,
							branchAddress);

					// System.out.println(branchName + ", " + branchAddress);

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
			String insertQuery = "insert into tbl_book_copies values (?, ?, ?)";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(insertQuery);
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
			String insertQuery = "insert into tbl_book_authors values (?, ?)";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(insertQuery);
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

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);

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
			String insertQuery = "insert into tbl_book (title) values (?)";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(insertQuery);
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

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);
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
			String insertQuery = "insert into tbl_author (authorName) values (?)";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(insertQuery);
			pstmt.setString(1, author.getAuthorName());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int displayBorrowers() {
		int borrowerCounter = 0;
		try {
			String selectQuery = "select * from tbl_borrower";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);

			rs = pstmt.executeQuery();

			borrowerCounter = 1;
			while (rs.next()) {
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");

				System.out.println(borrowerCounter + ") " + name + ", "
						+ address + ", " + phone);
				borrowerCounter++;
			}

			System.out.println(borrowerCounter + ") Quit to previous");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return borrowerCounter;
	}

}
