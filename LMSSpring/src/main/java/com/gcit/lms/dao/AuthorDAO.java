package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;

public class AuthorDAO extends BaseDAO<Author> implements
		ResultSetExtractor<List<Author>> {

	@Autowired
	BookDAO bDao;

	public void create(Author author) throws Exception {
		template.update("insert into tbl_author (authorName) values(?)",
				new Object[] { author.getAuthorName() });
	}

	public void update(Author author) throws Exception {
		template.update(
				"update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void delete(Author author) throws Exception {
		template.update("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorId() });
	}

	public List<Author> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return template.query("select * from tbl_author", this);
	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count(*) from tbl_author",
				Integer.class);
	}

	public Author readOne(int authorId) throws Exception {
		List<Author> authors = template.query(
				"select * from tbl_author where authorId = ?",
				new Object[] { authorId }, this);
		if (authors != null && authors.size() > 0) {
			return authors.get(0);
		}
		return null;
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<Author>();

		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));

			List<Book> books =  template
					.query("select * from tbl_book where bookId In"
							+ "(select bookId from tbl_book_authors where authorId=?)",
							new Object[] { rs.getInt("authorId") }, bDao);

			a.setBooks(books);
			authors.add(a);
		}
		return authors;
	}

	public List<Author> readByAuthorName(String searchString) throws Exception {
		searchString = "%" + searchString + "%";
		return template.query(
				"select * from tbl_author where authorName like ?",
				new Object[] { searchString }, this);
	}

	public List<Book> readBookByAuthorName(String searchString)
			throws Exception {
		searchString = "%" + searchString + "%";
		return  template
				.query("select * from tbl_book_authors join tbl_book join tbl_author  where authorName like ?",
						new Object[] { searchString }, bDao);
	}

}
