package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Publisher;

public class BookDAO extends BaseDAO<Book> implements
		ResultSetExtractor<List<Book>> {
	@Autowired
	PublisherDAO pDao;

	@Autowired
	AuthorDAO aDao;

	@Autowired
	GenreDAO gDao;

	private final String BOOK_COLLECTION = "Books";

	public void create(final Book book) {
		// only need id and title
		Book b = new Book();
		b.setBookId(book.getBookId());
		b.setTitle(book.getTitle());

		// add the book to books list of each author
		for (Author a : book.getAuthors()) {
			if (a.getBooks() == null) {
				a.setBooks(new ArrayList<Book>());
			}
			a.getBooks().add(b);
			aDao.update(a);
		}

		// add the book to books list of each genre
		for (Genre g : book.getGenres()) {
			if (g.getBooks() == null) {
				g.setBooks(new ArrayList<Book>());
			}
			g.getBooks().add(b);
			gDao.update(g);
		}

		// add the book to the books list for the publisher
		if (book.getPublisher() == null) {
			book.setPublisher(new Publisher());
			book.getPublisher().setBooks(new ArrayList<Book>());
		}
		book.getPublisher().getBooks().add(b);
		pDao.update(book.getPublisher());

		mongoOps.insert(book, BOOK_COLLECTION);
	}

	public void update(Book book) throws Exception {
		mongoOps.save(book, BOOK_COLLECTION);
	}

	public void delete(Book book) throws Exception {
		Query query = new Query(Criteria.where("_id").is(book.getBookId()));
		mongoOps.remove(query, BOOK_COLLECTION);
	}

	public List<Book> readAll() {
		return mongoOps.findAll(Book.class, BOOK_COLLECTION);
	}

	public Book readOne(UUID bookId) throws Exception {
		Query query = new Query(Criteria.where("_id").is(bookId));
		return this.mongoOps.findOne(query, Book.class, BOOK_COLLECTION);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		// GenreDAO gD
		while (rs.next()) {
			Book b = new Book();
			// b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			try {
				// b.setPublisher(pdao.readOne(rs.getInt("pubId")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// @SuppressWarnings("unchecked")
			List<Author> authors = (List<Author>) aDao.template
					.query("select * from tbl_author where authorId In"
							+ "(select authorId from tbl_book_authors where bookId=?)",
							new Object[] { rs.getInt("bookId") }, aDao);
			b.setAuthors(authors);
			// @SuppressWarnings("unchecked")
			List<Genre> genres = (List<Genre>) gDao.template
					.query("select * from tbl_genre where genre_id In"
							+ "(select genre_id from tbl_book_genres where bookId=?)",
							new Object[] { rs.getInt("bookId") }, gDao);
			b.setGenres(genres);
			books.add(b);
		}

		return books;
	}
}
