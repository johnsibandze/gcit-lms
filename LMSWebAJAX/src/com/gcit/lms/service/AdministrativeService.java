package com.gcit.lms.service;

import java.sql.Connection;

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

public class AdministrativeService extends BaseService {

	public void createAuthor(Author author) throws Exception {
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

	public void deleteBook(Book book) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
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
		Connection conn = ConnectionUtil.createConnection();
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

	public void updatePublisher(Publisher p) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
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
		Connection conn = ConnectionUtil.createConnection();
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

	public void updateLibrary(Library a) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
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
		Connection conn = ConnectionUtil.createConnection();
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

	public void deleteLibrary(Library library) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		try {
			ldao.delete(library);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void createGenre(Genre genre) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			// if (author == null || author.getAuthorName() == null
			// || author.getAuthorName().length() == 0
			// || author.getAuthorName().length() > 45) {
			// throw new Exception(
			// "Author Name cannot be empty or more than 45 Chars");
			// } else {
			GenreDAO adao = new GenreDAO(conn);
			adao.create(genre);
			conn.commit();
			// }
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updateGenre(Genre g) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			GenreDAO gdao = new GenreDAO(conn);
			gdao.update(g);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

	}

	public void deleteGenre(Genre genre) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gdao = new GenreDAO(conn);
		try {
			gdao.delete(genre);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updateBorrower(Borrower b) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			bdao.update(b);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

	}

	public void createBorrower(Borrower borrower) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			bdao.create(borrower);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteBorrower(Borrower borrower) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BorrowerDAO adao = new BorrowerDAO(conn);
		try {
			adao.delete(borrower);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

}