package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection conn) throws Exception {
		super(conn);
	}

	public void create(Borrower borrower) throws Exception {
		save("insert into tbl_borrower (name, address, phone) values(?, ?, ?)",
				new Object[] { borrower.getName(), borrower.getAddress(),
						borrower.getPhone() });
	}

	public void update(Borrower borrower) throws Exception {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(),
						borrower.getPhone(), borrower.getCardNo() });

	}

	public void delete(Borrower borrower) throws Exception {
		save("delete from tbl_borrower where cardNo = ?",
				new Object[] { borrower.getCardNo() });
	}

	@SuppressWarnings("unchecked")
	public List<Borrower> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Borrower>) read("select * from tbl_borrower", null);

	}

	/** reads everything. */
	@SuppressWarnings("unchecked")
	public List<Borrower> readAll() throws Exception {

		return (List<Borrower>) readAll("select * from tbl_borrower", null);

	}

	public Borrower readOne(int cardNo) throws Exception {
		@SuppressWarnings("unchecked")
		List<Borrower> borrowers = (List<Borrower>) read(
				"select * from tbl_borrower where cardNo = ?",
				new Object[] { cardNo });
		if (borrowers != null && borrowers.size() > 0) {
			return borrowers.get(0);
		}
		return null;
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws Exception {
		List<Borrower> borrowers = new ArrayList<Borrower>();
		BookDAO bDao = new BookDAO(getConnection());

		while (rs.next()) {
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setName(rs.getString("name"));
			b.setAddress(rs.getString("address"));
			b.setPhone(rs.getString("phone"));

			@SuppressWarnings("unchecked")
			List<Book> books = (List<Book>) bDao
					.readFirstLevel(
							"select * from tbl_book where bookId In"
									+ "(select bookId from tbl_book_loans where cardNo=?)",
							new Object[] { rs.getInt("cardNo") });
			b.setBooks(books);

			borrowers.add(b);
		}
		return borrowers;
	}

	@Override
	public List<Borrower> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Borrower> borrowers = new ArrayList<Borrower>();

		while (rs.next()) {
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setName(rs.getString("name"));
			b.setAddress(rs.getString("address"));
			b.setPhone(rs.getString("phone"));

			borrowers.add(b);
		}
		return borrowers;
	}

	@SuppressWarnings("unchecked")
	public List<Borrower> readByBorrowerName(String searchString, int pageNo,
			int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		searchString = "%" + searchString + "%";
		return (List<Borrower>) read(
				"select * from tbl_borrower where authorName like ?",
				new Object[] { searchString });
	}

	/** to be used locally to read everything. */
	public List<?> readAll(String query, Object[] vals) throws Exception {

		Connection conn = getConnection();

		PreparedStatement stmt = conn.prepareStatement(query);

		if (vals != null) {
			int count = 1;
			for (Object o : vals) {
				stmt.setObject(count, o);
				count++;
			}
		}
		ResultSet rs = stmt.executeQuery();
		return extractData(rs);
	}

}