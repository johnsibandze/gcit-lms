package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

public class BookDAO extends BaseDAO<Book> implements
		ResultSetExtractor<List<Book>> {
	@Autowired
	PublisherDAO pdao;

	@Autowired
	AuthorDAO aDao;

	@Autowired
	GenreDAO gDao;

	public void create(Book book) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update("insert into tbl_book (title, pubId) values(?, ?)",
				new Object[] { book.getTitle(),
						book.getPublisher().getPublisherId() }, keyHolder);
		int bookId = keyHolder.getKey().intValue();

		for (Author a : book.getAuthors()) {
			template.update(
					"insert into tbl_book_authors (bookId, authorId) values (?,?)",
					new Object[] { bookId, a.getAuthorId() });
		}

		for (Genre g : book.getGenres()) {
			template.update(
					"insert into tbl_book_genres (bookId, genre_id) values (?,?)",
					new Object[] { bookId, g.getGenreId() });
		}
	}

	public List<Book> readAll() throws Exception {
		return  template.query("select * from tbl_book", this);
	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count(*) from tbl_book",
				Integer.class);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		// GenreDAO gD
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			try {
				b.setPublisher(pdao.readOne(rs.getInt("pubId")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<Author> authors =  template
					.query("select * from tbl_author where authorId In"
							+ "(select authorId from tbl_book_authors where bookId=?)",
							new Object[] { rs.getInt("bookId") }, aDao);

			List<Genre> genres =  template
					.query("select * from tbl_genre where genre_id In"
							+ "(select genre_id from tbl_book_genres where bookId=?)",
							new Object[] { rs.getInt("bookId") }, gDao);

			b.setAuthors(authors);
			b.setGenres(genres);
			books.add(b);
		}

		return books;
	}

	public List<Book> readByBookTitle(String searchString) throws Exception {
		searchString = "%" + searchString + "%";
		return  template.query(
				"select * from tbl_book where title like ?",
				new Object[] { searchString }, this);
	}

	public List<Author> readAuthorByBookTitle(String searchString)
			throws Exception {
		searchString = "%" + searchString + "%";
		return  template
				.query("select * from tbl_book_authors join tbl_book join tbl_author  where title like ?",
						new Object[] { searchString }, aDao);
	}

}
