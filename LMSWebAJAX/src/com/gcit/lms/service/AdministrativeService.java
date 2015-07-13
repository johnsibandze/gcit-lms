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

public class AdministrativeService extends BaseService {

	public void createAuthor(Author author) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
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
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
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

	public void createBook(Book book) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
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

	public List<Author> readAuthors(int pageNo, int pageSize) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.readAll(pageNo, pageSize);
	}

	public Author readAuthor(int authorId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.readOne(authorId);
	}

	public void deleteAuthor(Author author) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
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
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
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

	public List<Genre> readGenres() throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		GenreDAO gdao = new GenreDAO(conn);
		List<Genre> genres = gdao.readAll();
		return genres;
	}

	public Genre readGenre(int genreId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		GenreDAO gdao = new GenreDAO(conn);
		return gdao.readOne(genreId);
	}

	public List<Author> searchAuthors(String searchString, int pageNo,
			int pageSize) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.readByAuthorName(searchString, pageNo, pageSize);
	}

	public void deleteBook(Book book) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			bdao.delete(book);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updateBook(Book b) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		try {
			BookDAO bdao = new BookDAO(conn);
			bdao.update(b);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Book> searchBooks(String searchString, int pageNo, int pageSize)
			throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		BookDAO bdao = new BookDAO(conn);
		return bdao.readByBookTitle(searchString, pageNo, pageSize);
	}

	public Publisher readPublisher(int publisherId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		PublisherDAO adao = new PublisherDAO(conn);
		return adao.readOne(publisherId);
	}

	public List<Library> readLibraries(int pageNo, int pageSize)
			throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		return ldao.readAll(pageNo, pageSize);
	}

	public List<Publisher> readPublishers(int pageNo, int pageSize)
			throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		List<Publisher> pubs = pdao.readAll(pageNo, pageSize);
		return pubs;
	}

	public List<Publisher> searchPublishers(String searchString, int pageNo,
			int pageSize) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		return pdao.readByPublisherName(searchString, pageNo, pageSize);
	}

	public List<Library> searchLibraries(String searchString, int pageNo,
			int pageSize) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryDAO adao = new LibraryDAO(conn);
		return adao.readByLibraryName(searchString, pageNo, pageSize);
	}

	public void updatePublisher(Publisher p) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		try {
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.update(p);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

	}

	public void deletePublisher(Publisher publisher) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try {
			pdao.delete(publisher);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public Library readLibrary(int branchId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		return ldao.readOne(branchId);
	}

	public void updateLibrary(Library a) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		try {
			LibraryDAO ldao = new LibraryDAO(conn);
			ldao.update(a);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

	}

	public void createLibrary(Library library) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		try {
			// if (library == null || library.getAuthorName() == null
			// || library.getAuthorName().length() == 0
			// || library.getAuthorName().length() > 45) {
			// throw new Exception(
			// "Author Name cannot be empty or more than 45 Chars");
			// } else {
			LibraryDAO adao = new LibraryDAO(conn);
			adao.create(library);
			conn.commit();
			// }
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

}