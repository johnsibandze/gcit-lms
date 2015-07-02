package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gcit.lms.domain.BookLoans;

public class BookLoansDAO extends BaseDAO<BookLoans> {

	public void create(BookLoans bookLoans) throws Exception {
		save("insert into tbl_book_loans values(?, ?, ?, ?, ?, ?)",
				new Object[] { bookLoans.getBookId(), bookLoans.getBranchId(),
						bookLoans.getCardNo(), bookLoans.getDateOut(),
						bookLoans.getDueDate(), bookLoans.getDateIn() });
	}

	public void update(BookLoans bookLoans) throws Exception {
		// TODO ensure that there is a
		save("update tbl_book_loans set dueDate = ?, dateIn = ? where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?",
				new Object[] { bookLoans.getDueDate(), bookLoans.getDateIn(),
						bookLoans.getBookId(), bookLoans.getBranchId(),
						bookLoans.getCardNo(), bookLoans.getDateOut() });
	}

	public void delete(BookLoans bookLoans) throws Exception {
		save("delete from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?",
				new Object[] { bookLoans.getBookId(), bookLoans.getBranchId(),
						bookLoans.getCardNo(), bookLoans.getDateOut() });
	}

	public List<BookLoans> readAll() throws Exception {
		return (List<BookLoans>) read("select * from tbl_book_loans", null);
	}

	public BookLoans readOne(int bookId, int branchId, int cardNo,
			String dateOutString) throws Exception {
		List<BookLoans> bookLoans = (List<BookLoans>) read(
				"select * from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?",
				new Object[] { bookId, branchId, cardNo, dateOutString });
		if (bookLoans != null && bookLoans.size() > 0) {
			return bookLoans.get(0);
		}
		return null;
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws Exception {
		List<BookLoans> bookLoans = new ArrayList<BookLoans>();

		while (rs.next()) {

			int bookId = rs.getInt("bookId");
			int branchId = rs.getInt("branchId");
			int cardNo = rs.getInt("cardNo");
			Date dateOut = rs.getDate("dateOut");
			Date dueDate = rs.getDate("dueDate");
			Date dateIn = rs.getDate("dateIn");

			BookLoans bl = new BookLoans(bookId, branchId, cardNo, dateOut,
					dueDate, dateIn);

			bookLoans.add(bl);
		}
		return bookLoans;
	}

}
