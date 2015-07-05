package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

public class BookDAO extends BaseDAO<Book> {

	public BookDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(Book book) throws Exception {
		// save the book and get it's id
		int bookId = saveWithID("insert into tbl_book (title) values(?)",
				new Object[] { book.getTitle() });

		// if the book wasn't saved
		if (bookId == -1) {
			throw new Exception("creating book failed");
		}

		// insert into book authors table
		for (Author a : book.getAuthors()) {
			save("insert into tbl_book_authors (bookId, authorId) values (?,?)",
					new Object[] { bookId, a.getAuthorId() });
		}

		// insert into book genres table
		for (Genre g : book.getGenres()) {
			save("insert into tbl_book_genres (bookId, genre_id) values (?,?)",
					new Object[] { bookId, g.getGenreId() });
		}

	}

	public void update(Book book) throws Exception {
		save("update tbl_book set title = ? where bookId = ?", new Object[] {
				book.getTitle(), book.getBookId() });
	}

	public void delete(Book book) throws Exception {
		save("delete from tbl_book where bookId = ?",
				new Object[] { book.getBookId() });
	}

	public List<Book> readAll() throws Exception {
		return (List<Book>) read("select * from tbl_book", null);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws Exception {
		List<Book> books = new ArrayList<Book>();
		PublisherDAO pdao = new PublisherDAO(getConnection());
		AuthorDAO aDao = new AuthorDAO(getConnection());
		GenreDAO gDao = new GenreDAO(getConnection());
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setPublisher(pdao.readOne(rs.getInt("pubId")));

			// get the authors of this book
			@SuppressWarnings("unchecked")
			List<Author> authors = (List<Author>) aDao
					.readFirstLevel(
							"select * from tbl_author where authorId In"
									+ "(select authorId from tbl_book_authors where bookId=?)",
							new Object[] { rs.getInt("bookId") });

			// get the genres of this book
			@SuppressWarnings("unchecked")
			List<Genre> genres = (List<Genre>) gDao
					.readFirstLevel(
							"select * from tbl_genre where genreId In"
									+ "(select genre_id from tbl_book_genres where bookId=?)",
							new Object[] { rs.getInt("genre_id") });

			b.setAuthors(authors);
			b.setGenres(genres);
			books.add(b);
		}

		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Book> books = new ArrayList<Book>();
		// PublisherDAO pdao = new PublisherDAO(getConnection());
		// AuthorDAO aDao = new AuthorDAO(getConnection());
		// GenreDAO gD
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}

		return books;
	}

}
