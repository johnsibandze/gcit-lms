package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.BookCopies;

public class BookCopiesDAO extends BaseDAO {

	public void create(BookCopies bookCopies) throws Exception {
		save("insert into tbl_book_copies values(?, ?, ?)",
				new Object[] { bookCopies.getBookId(),
						bookCopies.getBranchId(), bookCopies.getNoOfCopies() });
	}

	public void update(BookCopies bookCopies) throws Exception {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
				new Object[] { bookCopies.getNoOfCopies(),
						bookCopies.getBookId(), bookCopies.getBranchId() });
	}

	public void delete(BookCopies bookCopies) throws Exception {
		save("delete from tbl_book_copies where bookId = ? and branchId = ?",
				new Object[] { bookCopies.getBookId(), bookCopies.getBranchId() });
	}

	public List<BookCopies> readAll() throws Exception {
		return (List<BookCopies>) read("select * from tbl_book_copies", null);
	}

	public BookCopies readOne(int bookId, int branchId) throws Exception {
		List<BookCopies> bookCopiesRecords = (List<BookCopies>) read(
				"select * from tbl_book_copies where bookId = ? and branchId = ?",
				new Object[] { bookId, branchId });
		if (bookCopiesRecords != null && bookCopiesRecords.size() > 0) {
			return bookCopiesRecords.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<BookCopies> bookCopiesRecords = new ArrayList<BookCopies>();

		while (rs.next()) {

			int bookId = rs.getInt("bookId");
			int branchId = rs.getInt("branchId");
			int noOfCopies = rs.getInt("noOfCopies");

			BookCopies bc = new BookCopies(bookId, branchId, noOfCopies);

			bookCopiesRecords.add(bc);
		}
		return bookCopiesRecords;
	}

}
