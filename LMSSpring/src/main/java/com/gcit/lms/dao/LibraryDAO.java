package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Library;

public class LibraryDAO extends BaseDAO<Library> implements
		ResultSetExtractor<List<Library>> {

	@Autowired
	BookDAO bDao;

	public void create(Library library) throws Exception {
		template.update(
				"insert into tbl_library_branch (branchName, branchAddress) values(?, ?)",
				new Object[] { library.getBranchName(),
						library.getBranchAddress() });
	}

	public void update(Library library) throws Exception {
		template.update(
				"update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { library.getBranchName(),
						library.getBranchAddress(), library.getBranchId() });
	}

	public void delete(Library library) throws Exception {
		template.update("delete from tbl_library_branch where branchId = ?",
				new Object[] { library.getBranchId() });
	}

	public List<Library> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return template.query("select * from tbl_library_branch", this);
	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject(
				"select count(*) from tbl_library_branch", Integer.class);
	}

	public Library readOne(int branchId) throws Exception {
		List<Library> libraries = template.query(
				"select * from tbl_library_branch where branchId = ?",
				new Object[] { branchId }, this);
		if (libraries != null && libraries.size() > 0) {
			return libraries.get(0);
		}
		return null;
	}

	@Override
	public List<Library> extractData(ResultSet rs) throws SQLException {
		List<Library> libraries = new ArrayList<Library>();

		while (rs.next()) {
			Library l = new Library();
			l.setBranchId(rs.getInt("branchId"));
			l.setBranchName(rs.getString("branchName"));
			l.setBranchAddress(rs.getString("branchAddress"));

			List<Book> books =  template
					.query("select * from tbl_book where bookId In"
							+ "(select bookId from tbl_book_copies where branchId = ?)",
							new Object[] { rs.getInt("branchId") }, bDao);
			l.setBooks(books);

			libraries.add(l);
		}
		return libraries;
	}

	public List<Library> readByBranchName(String searchString) throws Exception {
		searchString = "%" + searchString + "%";
		return template.query(
				"select * from tbl_library_branch where branchName like ?",
				new Object[] { searchString }, this);
	}

	public List<Book> readBookByBranchName(String searchString)
			throws Exception {
		searchString = "%" + searchString + "%";
		return  template
				.query("select * from tbl_book_book_copies join tbl_book join tbl_library_branch  where branchName like ?",
						new Object[] { searchString }, bDao);
	}

}
