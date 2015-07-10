package com.gcit.lms.service;

import java.sql.Connection;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Publisher;

public class AdministrativeService {

	public void createAuthor(Author author) throws Exception {
		// ConnectionUtil c = new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (author == null || author.getAuthorName() == null
					|| author.getAuthorName().length() == 0
					|| author.getAuthorName().length() > 45) {
				throw new Exception(
						"Author Name cannot be empty or more than 45 Chars");
			} else {
				AuthorDAO adao = new AuthorDAO(conn);
				adao.create(author);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void createPublisher(Publisher publisher) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.create(publisher);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Author> readAuthors() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.readAll();
	}

	public Author readAuthor(int authorId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.readOne(authorId);
	}

	public void deleteAuthor(Author author) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			adao.delete(author);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updateAuthor(Author a) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			AuthorDAO adao = new AuthorDAO(conn);
			adao.update(a);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

	}

	public List<Publisher> readPublishers() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		return pdao.readAll();
	}

	public List<Book> readBooks() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO pdao = new BookDAO(conn);
		return pdao.readAll();
	}

	public Book readBook(int bookId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO adao = new BookDAO(conn);
		return adao.readOne(bookId);
	}

	public void createBook(Book book) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			BookDAO bdao = new BookDAO(conn);
			bdao.create(book);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Genre> readGenres() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gdao = new GenreDAO(conn);
		return gdao.readAll();
	}

}
