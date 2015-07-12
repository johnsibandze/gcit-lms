package com.gcit.lms.service;

import java.sql.Connection;
import java.util.List;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.domain.Book;

public class BaseService {

	public List<Book> readBooks(int pageNo, int pageSize) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		BookDAO adao = new BookDAO(conn);
		return adao.readAll(pageNo, pageSize);
	}

	public Book readBook(int bookId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		BookDAO bdao = new BookDAO(conn);
		return bdao.readOne(bookId);
	}

}
