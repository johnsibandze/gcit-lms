package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

public class GenreDAO extends BaseDAO<Genre> implements
		ResultSetExtractor<List<Genre>> {

	@Autowired
	BookDAO bDao;

	public void create(Genre genre) throws Exception {
		template.update("insert into tbl_genre (genre_name) values(?)",
				new Object[] { genre.getGenreName() });
	}

	public void update(Genre genre) throws Exception {
		template.update(
				"update tbl_genre set genre_name = ? where genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void delete(Genre genre) throws Exception {
		template.update("delete from tbl_genre where genre_id = ?",
				new Object[] { genre.getGenreId() });
	}

	public List<Genre> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return template.query("select * from tbl_genre", this);
	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count(*) from tbl_genre",
				Integer.class);
	}

	public Genre readOne(int genre_id) throws Exception {
		List<Genre> genres = template.query(
				"select * from tbl_genre where genre_id = ?",
				new Object[] { genre_id }, this);
		if (genres != null && genres.size() > 0) {
			return genres.get(0);
		}
		return null;
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<Genre>();

		while (rs.next()) {
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));

			List<Book> books =  template
					.query("select * from tbl_book where bookId In"
							+ "(select bookId from tbl_book_genres where genre_id = ?)",
							new Object[] { rs.getInt("genre_id") }, bDao);
			g.setBooks(books);

			genres.add(g);
		}
		return genres;
	}

	public List<Genre> readByGenreName(String searchString) throws Exception {
		searchString = "%" + searchString + "%";
		return template.query(
				"select * from tbl_genre where genre_name like ?",
				new Object[] { searchString }, this);
	}

	public List<Book> readBookByGenreName(String searchString) throws Exception {
		searchString = "%" + searchString + "%";
		return  template
				.query("select * from tbl_book_genres join tbl_book join tbl_genre  where genre_name like ?",
						new Object[] { searchString }, bDao);
	}

}
