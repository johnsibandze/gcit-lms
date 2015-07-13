package com.gcit.lms.service;

import java.sql.Connection;
import java.util.List;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.LibraryDAO;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Library;

public class LibrarianService extends BaseService {

	public List<Library> readLibraries(int pageNo, int pageSize)
			throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		return ldao.readAll(pageNo, pageSize);
	}

	public List<Library> searchLibraries(String searchString, int pageNo,
			int pageSize) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		return ldao.readByLibraryName(searchString, pageNo, pageSize);
	}

	public Library readLibrary(int branchId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		return ldao.readOne(branchId);
	}

	public BookCopies readBookCopies(int bookId, int branchId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		return bcdao.readOne(bookId, branchId);
	}

	public void updateBookCopies(BookCopies bc) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		try {
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			bcdao.update(bc);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void createBookCopies(BookCopies bookCopies) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		try {
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			bcdao.create(bookCopies);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

}