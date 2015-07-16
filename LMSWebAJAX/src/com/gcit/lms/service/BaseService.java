package com.gcit.lms.service;

import java.sql.Connection;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Library;
import com.gcit.lms.domain.Publisher;

public class BaseService {

	public List<Book> readBooks(int pageNo, int pageSize) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO adao = new BookDAO(conn);
		return adao.readAll(pageNo, pageSize);
	}

	public Book readBook(int bookId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bdao = new BookDAO(conn);
		return bdao.readOne(bookId);
	}

	public int countAuthors() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.count("select count(1) from tbl_author");
	}

	public List<Author> searchAuthors(String searchString, int pageNo,
			int pageSize) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.readByAuthorName(searchString, pageNo, pageSize);
	}

	public List<Book> searchBooks(String searchString, int pageNo, int pageSize)
			throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bdao = new BookDAO(conn);
		return bdao.readByBookTitle(searchString, pageNo, pageSize);
	}

	public Publisher readPublisher(int publisherId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO adao = new PublisherDAO(conn);
		return adao.readOne(publisherId);
	}

	public List<Publisher> readPublishers(int pageNo, int pageSize)
			throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		List<Publisher> pubs = pdao.readAll(pageNo, pageSize);
		return pubs;
	}

	public List<Publisher> searchPublishers(String searchString, int pageNo,
			int pageSize) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		return pdao.readByPublisherName(searchString, pageNo, pageSize);
	}

	public List<Library> searchLibraries(String searchString, int pageNo,
			int pageSize) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		LibraryDAO adao = new LibraryDAO(conn);
		return adao.readByLibraryName(searchString, pageNo, pageSize);
	}

	public List<Genre> searchGenres(String searchString, int pageNo,
			int pageSize) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gdao = new GenreDAO(conn);
		return gdao.readByGenreName(searchString, pageNo, pageSize);
	}

	public Library readLibrary(int branchId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		return ldao.readOne(branchId);
	}

	public List<Borrower> readBorrowers(int pageNo, int pageSize)
			throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BorrowerDAO bdao = new BorrowerDAO(conn);
		return bdao.readAll(pageNo, pageSize);
	}

	public List<Borrower> searchBorrowers(String searchString, int pageNo,
			int pageSize) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BorrowerDAO bdao = new BorrowerDAO(conn);
		return bdao.readByBorrowerName(searchString, pageNo, pageSize);
	}

	public Borrower readBorrower(int cardNo) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BorrowerDAO adao = new BorrowerDAO(conn);
		return adao.readOne(cardNo);
	}

	public List<Library> readLibraries(int pageNo, int pageSize)
			throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		return ldao.readAll(pageNo, pageSize);
	}

	public List<Genre> readGenres(int pageNo, int pageSize) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gdao = new GenreDAO(conn);
		List<Genre> genres = gdao.readAll(pageNo, pageSize);
		return genres;
	}

	public Genre readGenre(int genreId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gdao = new GenreDAO(conn);
		return gdao.readOne(genreId);
	}

	public List<Author> readAuthors(int pageNo, int pageSize) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.readAll(pageNo, pageSize);
	}

	public Author readAuthor(int authorId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.readOne(authorId);
	}

}