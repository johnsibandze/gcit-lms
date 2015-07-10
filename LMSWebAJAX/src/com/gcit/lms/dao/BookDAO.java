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
		int bookId = saveWithID(
				"insert into tbl_book (title, pubId) values(?, ?)",
				new Object[] { book.getTitle(),
						book.getPublisher().getPublisherId() });

		for (Author a : book.getAuthors()) {
			save("insert into tbl_book_authors (bookId, authorId) values (?,?)",
					new Object[] { bookId, a.getAuthorId() });
		}

		for (Genre g : book.getGenres()) {
			save("insert into tbl_book_genres (bookId, genre_id) values (?,?)",
					new Object[] { bookId, g.getGenre_id() });
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

	public Book readOne(int bookId) throws Exception {
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>) read(
				"select * from tbl_book where bookId = ?",
				new Object[] { bookId });
		if (books != null && books.size() > 0) {
			return books.get(0);
		}
		return null;
	}

	// public List<Book> readAll() throws Exception {
	// return (List<Book>) read("select * from tbl_book", null);
	//
	// }

	@SuppressWarnings("unchecked")
	public List<Book> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
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
			@SuppressWarnings("unchecked")
			List<Author> authors = (List<Author>) aDao
					.readFirstLevel(
							"select * from tbl_author where authorId In"
									+ "(select authorId from tbl_book_authors where bookId=?)",
							new Object[] { rs.getInt("bookId") });

			@SuppressWarnings("unchecked")
			List<Genre> genres = (List<Genre>) gDao
					.readFirstLevel(
							"select * from tbl_genre where genre_id In"
									+ "(select genre_id from tbl_book_genres where bookId=?)",
							new Object[] { rs.getInt("bookId") });

			b.setAuthors(authors);
			b.setGenres(genres);
			books.add(b);
		}

		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Book> books = new ArrayList<Book>();

		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}

		return books;
	}

	@SuppressWarnings("unchecked")
	public List<Book> readByBookTitle(String searchString, int pageNo,
			int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		searchString = "%" + searchString + "%";
		return (List<Book>) read("select * from tbl_book where title like ?",
				new Object[] { searchString });
	}

}
