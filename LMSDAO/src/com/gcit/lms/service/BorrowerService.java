package com.gcit.lms.service;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoan;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.LibraryBranch;

public class BorrowerService {

	public void checkOutBook(Borrower borrower, Book book,
			LibraryBranch libraryBranch) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (libraryBranch == null || libraryBranch.getBranchName() == null
					|| libraryBranch.getBranchName().length() == 0
					|| libraryBranch.getBranchName().length() > 45) {
				throw new Exception(
						"Library Branch Name cannot be empty or more than 45 Chars");
			} else if (libraryBranch == null
					|| libraryBranch.getBranchAddress() == null
					|| libraryBranch.getBranchAddress().length() == 0
					|| libraryBranch.getBranchAddress().length() > 45) {
				throw new Exception(
						"Library Branch Address cannot be empty or more than 45 Chars");
			} else if (book == null || book.getTitle() == null
					|| book.getTitle().length() == 0
					|| book.getTitle().length() > 45) {
				throw new Exception(
						"The book title cannot be empty or more than 45 Chars");
			} else {

				// update the book copies table
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);
				@SuppressWarnings("unchecked")
				List<BookCopies> bookCopies = (List<BookCopies>) bcdao
						.read("select * from tbl_book_copies where bookId = ? and branchId = ?",
								new Object[] { book.getBookId(),
										libraryBranch.getBranchId() });

				BookCopies bookCopy = bookCopies.get(0);
				int noOfCopies = bookCopy.getNoOfCopies();
				if (noOfCopies == 1) {
					bcdao.delete(bookCopy);
				} else {
					bookCopy.setNoOfCopies(bookCopy.getNoOfCopies() - 1);
					bcdao.update(bookCopy);
				}

				// now update the book loans table
				BookLoanDAO bldao = new BookLoanDAO(conn);

				// ////////////////////////////////
				Date today = new Date();
				Date nextWeek = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(nextWeek);
				c.add(Calendar.DATE, 7);
				nextWeek = c.getTime();
				// ////////////////////////////////

				BookLoan bookLoan = new BookLoan(book.getBookId(),
						libraryBranch.getBranchId(), borrower.getCardNo(),
						today, nextWeek, null);

				bldao.create(bookLoan);

				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void returnBook(Borrower borrower, BookLoan bookLoan)
			throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (borrower == null || borrower.getName() == null
					|| borrower.getName().length() == 0
					|| borrower.getName().length() > 45) {
				throw new Exception(
						"Borrower name cannot be empty or more than 45 Chars");
			} else if (borrower == null || borrower.getAddress() == null
					|| borrower.getAddress().length() == 0
					|| borrower.getAddress().length() > 45) {
				throw new Exception(
						"Borrower address cannot be empty or more than 45 Chars");
			} else if (borrower == null || borrower.getPhone() == null
					|| borrower.getPhone().length() == 0
					|| borrower.getPhone().length() > 45) {
				throw new Exception(
						"Phone number cannot be empty or more than 45 Chars");
			} else if (bookLoan == null) {
				throw new Exception(
						"The book title cannot be empty or more than 45 Chars");
			} else {

				// update the book copies table
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);
				@SuppressWarnings("unchecked")
				List<BookCopies> bookCopies = (List<BookCopies>) bcdao
						.read("select * from tbl_book_copies where bookId = ? and branchId = ?",
								new Object[] { bookLoan.getBookId(),
										bookLoan.getBranchId() });

				BookCopies bookCopy;
				// there is no copy of book in library
				if (bookCopies.size() == 0) {
					// create a new copy
					bookCopy = new BookCopies();
					bookCopy.setBookId(bookLoan.getBookId());
					bookCopy.setBranchId(bookLoan.getBranchId());
					bookCopy.setNoOfCopies(1);
					bcdao.create(bookCopy);
				} else {
					// increase the number of copies available
					bookCopy = bookCopies.get(0);
					bookCopy.setNoOfCopies(bookCopy.getNoOfCopies() + 1);
					bcdao.update(bookCopy);
				}

				// now update the book loans table
				Date today = new Date();

				bookLoan.setDateIn(today);
				BookLoanDAO bldao = new BookLoanDAO(conn);

				bldao.update(bookLoan);

				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

}
