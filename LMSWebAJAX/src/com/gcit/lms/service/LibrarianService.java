package com.gcit.lms.service;

import java.sql.Connection;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.domain.BookCopies;

public class LibrarianService extends BaseService {

	public BookCopies readBookCopies(int bookId, int branchId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		return bcdao.readOne(bookId, branchId);
	}

	public void updateBookCopies(BookCopies bc) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
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
		Connection conn = ConnectionUtil.createConnection();
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