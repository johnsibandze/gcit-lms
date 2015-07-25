package com.gcit.lms.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;

public class BookDAO extends BaseDAO<Book> implements Serializable,
		ResultSetExtractor<List<Book>> {

	private static final long serialVersionUID = 1619700647002508164L;

	@Autowired
	PublisherDAO pDAO;

	private static final String BOOK_COLLECTION = "Books";

	public void addBook(Book bk) throws SQLException {

		// Integer pubId = null;
		// if (bk.getPublisher() != null)
		// pubId = bk.getPublisher().getId();
		//
		// KeyHolder keyHolder = new GeneratedKeyHolder();
		//
		// template.update(
		// "insert into tbl_book (title, pubId) values (?,?)", new Object[] {
		// bk.getTitle(), pubId }, keyHolder);
		// int bookId = keyHolder.getKey().intValue();
		//
		// for (Author a : bk.getAuthors()) {
		// template.update("insert into tbl_book_authors (bookId, authorId) values (?,?)",
		// new Object[] { bookId, a.getAuthorId() });
		// }
		bk.setBookId(getNextSequenceId(BOOK_COLLECTION));
		mongoOps.insert(bk, BOOK_COLLECTION);
	}

	public void updateAuthor(Book book) throws SQLException {
	}

	public void removeAuthor(Book book) throws SQLException {
	}

	public List<Book> readAll() throws SQLException {
		return template.query("select * from tbl_book", this);
	}

	public Author readOne(int authorId) throws SQLException {
		return null;
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();

		while (rs.next()) {
			Book b = new Book();
			// b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setPublisher(pDAO.readOne(rs.getInt("pubId")));
			books.add(b);
		}
		return books;
	}

	public List<Book> searchBookByTitle(String searchString)
			throws SQLException {
		searchString = "%" + searchString + "%";
		return template.query("select * from tbl_book where title like ?",
				new Object[] { searchString }, this);
	}
}
