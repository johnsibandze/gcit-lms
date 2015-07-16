package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> implements
		ResultSetExtractor<List<Borrower>> {

	@Autowired
	BookDAO bDao;

	public void create(Borrower borrower) throws Exception {
		template.update(
				"insert into tbl_borrower (name, address, phone) values(?, ?, ?)",
				new Object[] { borrower.getName(), borrower.getAddress(),
						borrower.getPhone() });
	}

	public void update(Borrower borrower) throws Exception {
		template.update(
				"update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(),
						borrower.getPhone(), borrower.getCardNo() });
	}

	public void delete(Borrower borrower) throws Exception {
		template.update("delete from tbl_borrower where cardNo = ?",
				new Object[] { borrower.getCardNo() });
	}

	public List<Borrower> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Borrower>) template.query("select * from tbl_borrower",
				this);
	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count() from tbl_borrower",
				Integer.class);
	}

	public Borrower readOne(int cardNo) throws Exception {
		List<Borrower> borrowers = (List<Borrower>) template.query(
				"select * from tbl_borrower where cardNo = ?",
				new Object[] { cardNo }, this);
		if (borrowers != null && borrowers.size() > 0) {
			return borrowers.get(0);
		}
		return null;
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<Borrower>();

		while (rs.next()) {
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setName(rs.getString("name"));
			b.setAddress(rs.getString("address"));
			b.setPhone(rs.getString("phone"));

			List<Book> books = (List<Book>) template
					.query("select * from tbl_book where bookId In"
							+ "(select bookId from tbl_book_loans where cardNo = ?)",
							new Object[] { rs.getInt("cardNo") }, bDao);

			b.setBooks(books);
			borrowers.add(b);
		}
		return borrowers;
	}

	public List<Borrower> readByBorrowerName(String searchString)
			throws Exception {
		searchString = "%" + searchString + "%";
		return (List<Borrower>) template.query(
				"select * from tbl_borrower where name like ?",
				new Object[] { searchString }, this);
	}

	public List<Book> readBookByBorrowerName(String searchString)
			throws Exception {
		searchString = "%" + searchString + "%";
		return (List<Book>) template
				.query("select * from tbl_book_loans join tbl_book join tbl_borrower  where name like ?",
						new Object[] { searchString }, bDao);
	}

}
