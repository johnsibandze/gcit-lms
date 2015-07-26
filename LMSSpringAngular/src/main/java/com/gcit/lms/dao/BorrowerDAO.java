package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> implements
		ResultSetExtractor<List<Borrower>> {

	@Autowired
	BookDAO bDao;

	private final String BORROWER_COLLECTION = "Borrowers";

	public void create(Borrower borrower) throws Exception {
		mongoOps.insert(borrower, BORROWER_COLLECTION);
	}

	public void update(Borrower borrower) throws Exception {
		// Query query = new
		// Query(Criteria.where("_id").is(borrower.getCardNo()));
		// Borrower oldBorrower = mongoOps.findOne(query, Borrower.class,
		// BORROWER_COLLECTION);
		//
		// oldBorrower.setName(borrower.getName());
		// oldBorrower.setAddress(borrower.getAddress());
		// oldBorrower.setPhone(borrower.getPhone());
		mongoOps.save(borrower, BORROWER_COLLECTION);
	}

	public void delete(Borrower borrower) throws Exception {
		Query query = new Query(Criteria.where("_id").is(borrower.getCardNo()));
		mongoOps.remove(query, BORROWER_COLLECTION);
	}

	public List<Borrower> readAll() throws Exception {
		return mongoOps.findAll(Borrower.class, BORROWER_COLLECTION);
	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count() from tbl_author",
				Integer.class);
	}

	public Borrower readOne(UUID cardNo) throws Exception {
		Query query = new Query(Criteria.where("_id").is(cardNo));
		return this.mongoOps
				.findOne(query, Borrower.class, BORROWER_COLLECTION);
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<Borrower>();

		while (rs.next()) {
			Borrower a = new Borrower();
			// a.setAuthorId(rs.getInt("authorId"));
			// a.setAuthorName(rs.getString("authorName"));
			// @SuppressWarnings("unchecked")
			// List<Book> books = (List<Book>)
			// bDao.readFirstLevel("select * from tbl_book where bookId In"
			// + "(select bookId from tbl_book_authors where authorId=?)", new
			// Object[] {rs.getInt("authorId")});
			// a.setBooks(books);
			borrowers.add(a);
		}
		return borrowers;
	}

	public List<Borrower> readByAuthorName(String searchString)
			throws Exception {
		searchString = "%" + searchString + "%";
		return (List<Borrower>) template.query(
				"select * from tbl_author where authorName like ?",
				new Object[] { searchString }, this);
	}

}
