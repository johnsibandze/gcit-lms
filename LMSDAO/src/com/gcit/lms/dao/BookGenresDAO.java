package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.BookGenres;

public class BookGenresDAO extends BaseDAO<BookGenres> {

	public void create(BookGenres bookGenres) throws Exception {
		save("insert into tbl_book_genres values(?, ?)", new Object[] {
				bookGenres.getGenreId(), bookGenres.getBookId() });
	}

	public void update(BookGenres bookGenres) throws Exception {
		// no need to update since we only have primary keys?

		// save("update tbl_book_genres set authorName = ? where authorId = ?",
		// new Object[] { bookGenres.getAuthorName(),
		// bookGenres.getAuthorId() });
	}

	public void delete(BookGenres bookGenres) throws Exception {
		// might not really need to delete because of cascading
		save("delete from tbl_book_genres where genre_id = ? and bookId = ?",
				new Object[] { bookGenres.getGenreId(), bookGenres.getBookId() });
	}

	public List<BookGenres> readAll() throws Exception {
		return (List<BookGenres>) read("select * from tbl_book_genres", null);
	}

	public BookGenres readOne(int genreId, int bookId) throws Exception {
		List<BookGenres> bookGenres = (List<BookGenres>) read(
				"select * from tbl_book_genres where genre_id = ? and bookId = ?",
				new Object[] { genreId, bookId });
		if (bookGenres != null && bookGenres.size() > 0) {
			return bookGenres.get(0);
		}
		return null;
	}

	@Override
	public List<BookGenres> extractData(ResultSet rs) throws Exception {
		List<BookGenres> bookGenres = new ArrayList<BookGenres>();

		while (rs.next()) {
			int genreId = rs.getInt("genre_id");
			int bookId = rs.getInt("bookId");

			BookGenres bg = new BookGenres(genreId, bookId);

			bookGenres.add(bg);
		}
		return bookGenres;
	}

}
