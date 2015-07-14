package com.gcit.lms.service;

import java.sql.Connection;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.domain.Book;

public class BaseService {

	public List<Book> readBooks(int pageNo, int pageSize) throws Exception {
		// ConnectionUtil c = new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		BookDAO adao = new BookDAO(conn);
		return adao.readAll(pageNo, pageSize);
	}

	public Book readBook(int bookId) throws Exception {
		// ConnectionUtil c = new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bdao = new BookDAO(conn);
		return bdao.readOne(bookId);
	}

	public int countAuthors() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.count("select count(1) from tbl_author");
	}

}