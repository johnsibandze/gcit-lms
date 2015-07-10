package com.gcit.lms.service;

import java.sql.Connection;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Library;
import com.gcit.lms.domain.Publisher;

public class LibrarianService {

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

}
