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

import com.gcit.lms.domain.Library;

public class LibraryDAO extends BaseDAO<Library> implements
		ResultSetExtractor<List<Library>> {

	@Autowired
	BookDAO bDao;

	private final String LIBRARY_COLLECTION = "Libraries";

	public void create(Library library) throws Exception {
		mongoOps.insert(library, LIBRARY_COLLECTION);
	}

	public void update(Library library) throws Exception {
		Query query = new Query(Criteria.where("_id").is(library.getBranchId()));
		Library oldLibrary = mongoOps.findOne(query, Library.class,
				LIBRARY_COLLECTION);

		oldLibrary.setBranchName(library.getBranchName());
		oldLibrary.setBranchAddress(library.getBranchAddress());
		oldLibrary.setBooks(library.getBooks());

		mongoOps.save(oldLibrary, LIBRARY_COLLECTION);
	}

	public void delete(Library library) throws Exception {
		Query query = new Query(Criteria.where("_id").is(library.getBranchId()));
		mongoOps.remove(query, LIBRARY_COLLECTION);
	}

	public List<Library> readAll() throws Exception {
		return mongoOps.findAll(Library.class, LIBRARY_COLLECTION);
	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count() from tbl_author",
				Integer.class);
	}

	public Library readOne(UUID branchId) throws Exception {
		Query query = new Query(Criteria.where("_id").is(branchId));
		return this.mongoOps.findOne(query, Library.class, LIBRARY_COLLECTION);
	}

	@Override
	public List<Library> extractData(ResultSet rs) throws SQLException {
		List<Library> libraries = new ArrayList<Library>();

		while (rs.next()) {
			Library a = new Library();
			// a.setAuthorId(rs.getInt("authorId"));
			// a.setAuthorName(rs.getString("authorName"));
			// @SuppressWarnings("unchecked")
			// List<Book> books = (List<Book>)
			// bDao.readFirstLevel("select * from tbl_book where bookId In"
			// + "(select bookId from tbl_book_authors where authorId=?)", new
			// Object[] {rs.getInt("authorId")});
			// a.setBooks(books);
			libraries.add(a);
		}
		return libraries;
	}

}
