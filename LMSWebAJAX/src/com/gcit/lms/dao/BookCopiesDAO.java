package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.BookCopies;

public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public BookCopiesDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(BookCopies bookCopy) throws Exception {
		save("insert into tbl_book_copies values(?, ?, ?)",
				new Object[] { bookCopy.getBookId(), bookCopy.getBranchId(),
						bookCopy.getNoOfCopies() });
	}

	public void update(BookCopies bookCopy) throws Exception {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
				new Object[] { bookCopy.getNoOfCopies(), bookCopy.getBookId(),
						bookCopy.getBranchId() });

	}

	public void delete(BookCopies bookCopy) throws Exception {
		save("delete from tbl_book_copies where bookId = ? and branchId = ?",
				new Object[] { bookCopy.getBookId(), bookCopy.getBranchId() });
	}

	@SuppressWarnings("unchecked")
	public List<BookCopies> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<BookCopies>) read("select * from tbl_book_copies", null);

	}

	public BookCopies readOne(int bookId, int branchId) throws Exception {
		@SuppressWarnings("unchecked")
		List<BookCopies> bookCopies = (List<BookCopies>) read(
				"select * from tbl_book_copies where bookId = ? and branchId = ?",
				new Object[] { bookId, branchId });
		if (bookCopies != null && bookCopies.size() > 0) {
			return bookCopies.get(0);
		}
		return null;
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws Exception {
		List<BookCopies> bookCopies = new ArrayList<BookCopies>();

		while (rs.next()) {

			int bookId = rs.getInt("bookId");
			int branchId = rs.getInt("branchId");
			int noOfCopies = rs.getInt("noOfCopies");

			BookCopies bc = new BookCopies(bookId, branchId, noOfCopies);

			bookCopies.add(bc);
		}
		return bookCopies;
	}

	@Override
	public List<BookCopies> extractDataFirstLevel(ResultSet rs)
			throws Exception {
		List<BookCopies> bookCopies = new ArrayList<BookCopies>();

		while (rs.next()) {

			int bookId = rs.getInt("bookId");
			int branchId = rs.getInt("branchId");
			int noOfCopies = rs.getInt("noOfCopies");

			BookCopies bc = new BookCopies(bookId, branchId, noOfCopies);

			bookCopies.add(bc);
		}
		return bookCopies;
	}

	// @SuppressWarnings("unchecked")
	// public List<BookCopies> readByAuthorName(String searchString, int pageNo,
	// int pageSize) throws Exception {
	// setPageNo(pageNo);
	// setPageSize(pageSize);
	//
	// searchString = "%" + searchString + "%";
	// return (List<BookCopies>) read(
	// "select * from tbl_book_copies where authorName like ?",
	// new Object[] { searchString });
	// }

}
