package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.BookLoan;

public class BookLoanDAO extends BaseDAO<BookLoan> {

	public BookLoanDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(BookLoan bookLoan) throws Exception {
		// save the book and get it's id
		int bookId = saveWithID(
				"insert into tbl_book_loans values(?, ?, ?, ?, ?, ?)",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(),
						bookLoan.getCardNo(), bookLoan.getDateOut(),
						bookLoan.getDueDate(), bookLoan.getDateIn() });

		// if the book wasn't saved
		if (bookId == -1) {
			throw new Exception("creating book failed");
		}

	}

	public void update(BookLoan bookLoan) throws Exception {
		save("update tbl_book_loans set dueDate = ?, dateIn = ? where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?",
				new Object[] { bookLoan.getDueDate(), bookLoan.getDateIn(),
						bookLoan.getBookId(), bookLoan.getBranchId(),
						bookLoan.getCardNo(), bookLoan.getDateOut() });
	}

	public void delete(BookLoan bookLoan) throws Exception {
		save("delete from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(),
						bookLoan.getCardNo(), bookLoan.getDateOut() });
	}

	@SuppressWarnings("unchecked")
	public List<BookLoan> readAll() throws Exception {
		return (List<BookLoan>) read("select * from tbl_book_loans", null);
	}

	@Override
	public List<BookLoan> extractData(ResultSet rs) throws Exception {
		List<BookLoan> bookLoans = new ArrayList<BookLoan>();

		while (rs.next()) {
			BookLoan bl = new BookLoan();
			bl.setBookId(rs.getInt("bookId"));
			bl.setBranchId(rs.getInt("branchId"));
			bl.setCardNo(rs.getInt("cardNo"));
			bl.setDateOut(rs.getDate("dateOut"));
			bl.setDueDate(rs.getDate("dueDate"));
			bl.setDateOut(rs.getDate("dateIn"));

			bookLoans.add(bl);
		}

		return bookLoans;
	}

	@Override
	public List<BookLoan> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<BookLoan> bookLoans = new ArrayList<BookLoan>();

		while (rs.next()) {
			BookLoan bl = new BookLoan();
			bl.setBookId(rs.getInt("bookId"));
			bl.setBranchId(rs.getInt("branchId"));
			bl.setCardNo(rs.getInt("cardNo"));
			bl.setDateOut(rs.getDate("dateOut"));
			bl.setDueDate(rs.getDate("dueDate"));
			bl.setDateOut(rs.getDate("dateIn"));

			bookLoans.add(bl);
		}

		return bookLoans;
	}

}
