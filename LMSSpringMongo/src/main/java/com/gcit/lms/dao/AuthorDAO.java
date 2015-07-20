package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;

public class AuthorDAO extends BaseDAO<Author> implements
		ResultSetExtractor<List<Author>> {

	@Autowired
	BookDAO bDao;

	private final String AUTH_COLLECTION = "Authors";

	public void create(Author author) throws Exception {
		// template.update("insert into tbl_author (authorName) values(?)",
		// new Object[] { author.getAuthorName() });
		mongoOps.insert(author, AUTH_COLLECTION);
	}

	public void update(Author author) throws Exception {
		// template.update(
		// "update tbl_author set authorName = ? where authorId = ?",
		// new Object[] { author.getAuthorName(), author.getAuthorId() });

		Query query = new Query(Criteria.where("_id").is(author.getAuthorId()));
		Author oldAuthor = mongoOps.findOne(query, Author.class,
				AUTH_COLLECTION);

		oldAuthor.setAuthorName(author.getAuthorName());
		mongoOps.save(oldAuthor, AUTH_COLLECTION);

	}

	public void delete(Author author) throws Exception {
		// template.update("delete from tbl_author where authorId = ?",
		// new Object[] { author.getAuthorId() });
		Query query = new Query(Criteria.where("_id").is(author.getAuthorId()));
		mongoOps.remove(query, AUTH_COLLECTION);
	}

	public List<Author> readAll(int pageNo, int pageSize) throws Exception {
		// setPageNo(pageNo);
		// setPageSize(pageSize);
		// (List<Author>)
		// template.query("select * from tbl_author LIMIT "+pageNo+", "+pageSize
		// , this);
		return mongoOps.findAll(Author.class, AUTH_COLLECTION);

	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count() from tbl_author",
				Integer.class);

	}

	public Author readOne(UUID authorId) throws Exception {
		// List<Author> authors = (List<Author>)
		// template.query("select * from tbl_author where authorId = ?", new
		// Object[] {authorId}, this);
		// if(authors!=null && authors.size()>0){
		// return authors.get(0);
		// }
		// return null;
		Query query = new Query(Criteria.where("_id").is(authorId));
		return this.mongoOps.findOne(query, Author.class, AUTH_COLLECTION);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<Author>();

		while (rs.next()) {
			Author a = new Author();
			// a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			// @SuppressWarnings("unchecked")
			// List<Book> books = (List<Book>)
			// bDao.readFirstLevel("select * from tbl_book where bookId In"
			// + "(select bookId from tbl_book_authors where authorId=?)", new
			// Object[] {rs.getInt("authorId")});
			// a.setBooks(books);
			authors.add(a);
		}
		return authors;
	}

	public List<Author> readByAuthorName(String searchString) throws Exception {
		searchString = "%" + searchString + "%";
		return (List<Author>) template.query(
				"select * from tbl_author where authorName like ?",
				new Object[] { searchString }, this);
	}

	// public List<Book> readBookByAuthorName(String searchString) throws
	// Exception{
	// searchString = "%"+searchString+"%";
	// return (List<Author>)
	// template.query("select * from tbl_book_authors  where authorName like ?",
	// new Object[] {searchString}, this);
	// }
}
