package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.BookCopies;

public class BookCopiesDAO extends BaseDAO<BookCopies> implements
		ResultSetExtractor<List<BookCopies>> {

	// @Autowired
	// BookDAO bDao;

	public void create(BookCopies bookCopy) throws Exception {
		template.update("insert into tbl_book_copies values(?, ?, ?)",
				new Object[] { bookCopy.getBookId(), bookCopy.getBranchId(),
						bookCopy.getNoOfCopies() });
	}

	public void update(BookCopies bookCopy) throws Exception {
		template.update(
				"update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
				new Object[] { bookCopy.getNoOfCopies(), bookCopy.getBookId(),
						bookCopy.getBranchId() });
	}

	public void delete(BookCopies bookCopy) throws Exception {
		template.update(
				"delete from tbl_book_copies where bookId = ? and branchId = ?",
				new Object[] { bookCopy.getBookId(), bookCopy.getBranchId() });
	}

	public List<BookCopies> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return  template.query(
				"select * from tbl_book_copies", this);
	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count(*) from tbl_book_copies",
				Integer.class);
	}

	public BookCopies readOne(int bookId, int branchId) throws Exception {
		List<BookCopies> bookCopies =  template
				.query("select * from tbl_book_copies where bookId = ? and branchId = ?",
						new Object[] { bookId, branchId }, this);
		if (bookCopies != null && bookCopies.size() > 0) {
			return bookCopies.get(0);
		}
		return null;
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bookCopies = new ArrayList<BookCopies>();

		while (rs.next()) {
			BookCopies bc = new BookCopies();
			bc.setBookId(rs.getInt("bookId"));
			bc.setBranchId(rs.getInt("branchId"));
			bc.setNoOfCopies(rs.getInt("noOfCopies"));

			// List<Book> books = (List<Book>) template
			// .query("select * from tbl_book where bookId In"
			// + "(select bookId from tbl_book_authors where authorId=?)",
			// new Object[] { rs.getInt("authorId") }, bDao);

			// bc.setBooks(books);

			bookCopies.add(bc);
		}
		return bookCopies;
	}

	// public List<BookCopies> readByAuthorName(String searchString)
	// throws Exception {
	// searchString = "%" + searchString + "%";
	// return  template.query(
	// "select * from tbl_book_copies where authorName like ?",
	// new Object[] { searchString }, this);
	// }
	//
	// public List<Book> readBookByAuthorName(String searchString)
	// throws Exception {
	// searchString = "%" + searchString + "%";
	// return (List<Book>) template
	// .query("select * from tbl_book_authors join tbl_book join tbl_book_copies  where authorName like ?",
	// new Object[] { searchString }, bDao);
	// }

}
