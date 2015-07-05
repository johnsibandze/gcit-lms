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

	public void create(BookCopies bookCopies) throws Exception {
		// save the book copy record and get the id
		int bookId = saveWithID("insert into tbl_book_copies values(?, ?, ?)",
				new Object[] { bookCopies.getBookId(), bookCopies.getBookId(),
						bookCopies.getNoOfCopies() });

		// if the book copy wasn't saved
		if (bookId == -1) {
			throw new Exception("inserting book copies failed");
		}

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

	@SuppressWarnings("unchecked")
	public List<BookCopies> readAll() throws Exception {
		return (List<BookCopies>) read("select * from tbl_book_copies", null);
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws Exception {
		List<BookCopies> bookCopies = new ArrayList<BookCopies>();

		while (rs.next()) {
			BookCopies bc = new BookCopies();
			bc.setBookId(rs.getInt("bookId"));
			bc.setBranchId(rs.getInt("branchId"));
			bc.setNoOfCopies(rs.getInt("noOfCopies"));

			bookCopies.add(bc);
		}

		return bookCopies;
	}

	@Override
	public List<BookCopies> extractDataFirstLevel(ResultSet rs)
			throws Exception {
		List<BookCopies> books = new ArrayList<BookCopies>();

		while (rs.next()) {
			BookCopies bc = new BookCopies();
			bc.setBookId(rs.getInt("bookId"));
			bc.setBranchId(rs.getInt("branchId"));
			bc.setNoOfCopies(rs.getInt("noOfCopies"));

			books.add(bc);
		}

		return books;
	}
}
