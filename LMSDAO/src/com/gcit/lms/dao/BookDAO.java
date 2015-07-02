package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Book;

public class BookDAO extends BaseDAO {

	public void create(Book book) throws Exception {
		save("insert into tbl_book (title) values(?)",
				new Object[] { book.getTitle() });
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

	public Book readOne(int bookId) throws Exception {
		List<Book> books = (List<Book>) read(
				"select * from tbl_book where bookId = ?",
				new Object[] { bookId });
		if (books != null && books.size() > 0) {
			return books.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<Book> books = new ArrayList<Book>();

		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));

			books.add(b);
		}
		return books;
	}

}
