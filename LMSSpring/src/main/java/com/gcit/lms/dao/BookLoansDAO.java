package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookLoans;

public class BookLoansDAO extends BaseDAO<BookLoans> implements
		ResultSetExtractor<List<BookLoans>> {

	@Autowired
	BookDAO bDao;

	public void create(BookLoans bookLoan) throws Exception {
		template.update(
				"insert into tbl_book_loans values(?, ?, ?, ?, ?, ?)",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(),
						bookLoan.getCardNo(), bookLoan.getDateOut(),
						bookLoan.getDueDate(), bookLoan.getDateIn() });
	}

	public void update(BookLoans bookLoan) throws Exception {
		template.update(
				"update tbl_book_loans set dueDate = ?, dateIn = ? where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?",
				new Object[] { bookLoan.getDueDate(), bookLoan.getDateIn(),
						bookLoan.getBookId(), bookLoan.getBranchId(),
						bookLoan.getCardNo(), bookLoan.getDateOut() });
	}

	// should probably not be used since we should not delete from the book
	// loans?
	public void delete(BookLoans bookLoan) throws Exception {
		template.update(
				"delete from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(),
						bookLoan.getCardNo(), bookLoan.getDateOut() });
	}

	public List<BookLoans> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return template.query("select * from tbl_book_loans", this);
	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count(*) from tbl_book_loans",
				Integer.class);
	}

	public BookLoans readOne(int bookId, int branchId, int cardNo, Date dateOut)
			throws Exception {
		List<BookLoans> bookLoans = template.query(
				"select * from tbl_book_loans where authorId = ?",
				new Object[] { bookId, branchId, cardNo, dateOut }, this);
		if (bookLoans != null && bookLoans.size() > 0) {
			return bookLoans.get(0);
		}
		return null;
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> bookLoans = new ArrayList<BookLoans>();

		while (rs.next()) {
			BookLoans bl = new BookLoans();
			bl.setBookId(rs.getInt("bookId"));
			bl.setBranchId(rs.getInt("branchId"));
			bl.setCardNo(rs.getInt("cardNo"));
			bl.setDateOut(rs.getDate("dateOut"));
			bl.setDueDate(rs.getDate("dueDate"));
			bl.setDateIn(rs.getDate("dateIn"));

			// List<Book> books = template
			// .query("select * from tbl_book where bookId In"
			// + "(select bookId from tbl_book_authors where authorId=?)",
			// new Object[] { rs.getInt("authorId") }, bDao);
			// bl.setBooks(books);

			bookLoans.add(bl);
		}
		return bookLoans;
	}

	// public List<BookLoans> readByAuthorName(String searchString)
	// throws Exception {
	// searchString = "%" + searchString + "%";
	// return template.query(
	// "select * from tbl_book_loans where authorName like ?",
	// new Object[] { searchString }, this);
	// }

	public List<Book> readBookByBorrowerName(String searchString)
			throws Exception {
		searchString = "%" + searchString + "%";
		return template
				.query("select * from tbl_book_borrower join tbl_book_loans  where name like ?",
						new Object[] { searchString }, bDao);
	}

}
