package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.BookCopies;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class BookCopiesDAO extends BaseDAO<BookCopies> implements
		ResultSetExtractor<List<BookCopies>> {

	@Autowired
	BookDAO bDao;

	@Autowired
	MongoDbFactory mongoDbFactory;

	@Autowired
	MongoTemplate mongoDbTemplate;

	private final String BOOK_COPIES_COLLECTION = "BookCopies";

	public void create(BookCopies bookCopy) throws Exception {
		mongoOps.insert(bookCopy, BOOK_COPIES_COLLECTION);
	}

	public void update(BookCopies bookCopy) throws Exception {
		// Query query = new
		// Query(Criteria.where("_id").is(bookCopy.getAuthorId()));
		// BookCopies oldAuthor = mongoOps.findOne(query, BookCopies.class,
		// BOOK_COPIES_COLLECTION);
		//
		// oldAuthor.setAuthorName(bookCopy.getAuthorName());
		mongoOps.save(bookCopy, BOOK_COPIES_COLLECTION);
	}

	// TODO not working yet
	public void delete(BookCopies bookCopy) throws Exception {
		// DBCollection collection = mongoOps
		// .getCollection(BOOK_COPIES_COLLECTION);
		// BasicDBObject whereQuery = new BasicDBObject();
		// whereQuery.put("authorName", "John");
		// DBCursor cursor = collection.find(whereQuery);

		Query query = new Query(Criteria.where("_id").is(bookCopy.getBookId()));
		mongoOps.remove(query, BOOK_COPIES_COLLECTION);
	}

	public List<BookCopies> readAll() throws Exception {
		// DBCollection collection =
		// mongoOps.getCollection(BOOK_COPIES_COLLECTION);
		// BasicDBObject whereQuery = new BasicDBObject();
		// whereQuery.put("authorName", "John");
		// DBCursor cursor = collection.find(whereQuery);
		//
		// List<BookCopies> authors = new ArrayList<BookCopies>();
		// while (cursor.hasNext()) {
		// DBObject next = cursor.next();
		// System.out.println(next);
		// System.out.println((UUID) next.get("_id"));
		//
		// BookCopies bookCopy = readOne((UUID) next.get("_id"));
		// authors.add(bookCopy);
		//
		// }

		// return authors;

		return mongoOps.findAll(BookCopies.class, BOOK_COPIES_COLLECTION);

		// ////////////////////////////////////////
		// String searchString = "/Joh/";
		// Query query = new Query(Criteria.where("authorName")
		// .regex(searchString));
		// return mongoOps.find(query, BookCopies.class,
		// BOOK_COPIES_COLLECTION);
		// ////////////////////////////////////////

	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count() from tbl_author",
				Integer.class);
	}

	public BookCopies readOne(UUID authorId) throws Exception {
		Query query = new Query(Criteria.where("_id").is(authorId));
		return this.mongoOps.findOne(query, BookCopies.class,
				BOOK_COPIES_COLLECTION);
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> authors = new ArrayList<BookCopies>();

		while (rs.next()) {
			BookCopies a = new BookCopies();
			// a.setAuthorId(rs.getInt("authorId"));
			// a.setAuthorName(rs.getString("authorName"));
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

	public List<BookCopies> readByAuthorName(String searchString)
			throws Exception {
		searchString = "%" + searchString + "%";
		return template.query(
				"select * from tbl_author where authorName like ?",
				new Object[] { searchString }, this);
	}

	// public List<Book> readBookByAuthorName(String searchString) throws
	// Exception{
	// searchString = "%"+searchString+"%";
	// return (List<BookCopies>)
	// template.query("select * from tbl_book_authors  where authorName like ?",
	// new Object[] {searchString}, this);
	// }
}
