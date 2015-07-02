package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.BookAuthors;

public class BookAuthorsDAO extends BaseDAO {

	public void create(BookAuthors bookAuthors) throws Exception {
		save("insert into tbl_book_authors values(?, ?)", new Object[] {
				bookAuthors.getBookId(), bookAuthors.getAuthorId() });
	}

	public void update(BookAuthors bookAuthors) throws Exception {
		// no need to update records because primary keys do not change?

		// save("update tbl_book_authors set authorName = ? where authorId = ?",
		// new Object[] { bookAuthors.getAuthorName(),
		// bookAuthors.getAuthorId() });
	}

	public void delete(BookAuthors bookAuthors) throws Exception {
		// also probably no need to delete since we are cascading
		save("delete from tbl_book_authors where bookId = ? and branchId = ?",
				new Object[] { bookAuthors.getBookId(),
						bookAuthors.getAuthorId() });
	}

	public List<BookAuthors> readAll() throws Exception {
		return (List<BookAuthors>) read("select * from tbl_book_authors", null);
	}

	public BookAuthors readOne(int bookId, int authorId) throws Exception {
		List<BookAuthors> bookAuthors = (List<BookAuthors>) read(
				"select * from tbl_book_authors where bookId = ? and authorId = ?",
				new Object[] { bookId, authorId });
		if (bookAuthors != null && bookAuthors.size() > 0) {
			return bookAuthors.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<BookAuthors> bookAuthors = new ArrayList<BookAuthors>();

		while (rs.next()) {

			int bookId = rs.getInt("bookId");
			int authorId = rs.getInt("authorId");

			BookAuthors ba = new BookAuthors(bookId, authorId);

			bookAuthors.add(ba);
		}
		return bookAuthors;
	}

}
