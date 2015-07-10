package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Library;

public class LibraryDAO extends BaseDAO<Library> {

	public LibraryDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(Library library) throws Exception {
		save("insert into tbl_library_branch (branchName, branchAddress) values(?, ?)",
				new Object[] { library.getBranchName(),
						library.getBranchAddress() });
	}

	public void update(Library library) throws Exception {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { library.getBranchName(),
						library.getBranchAddress(), library.getBranchId() });

	}

	public void delete(Library library) throws Exception {
		save("delete from tbl_library_branch where branchId = ?",
				new Object[] { library.getBranchId() });
	}

	@SuppressWarnings("unchecked")
	public List<Library> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Library>) read("select * from tbl_library_branch", null);

	}

	public Library readOne(int branchId) throws Exception {
		@SuppressWarnings("unchecked")
		List<Library> libraries = (List<Library>) read(
				"select * from tbl_library_branch where branchId = ?",
				new Object[] { branchId });
		if (libraries != null && libraries.size() > 0) {
			return libraries.get(0);
		}
		return null;
	}

	@Override
	public List<Library> extractData(ResultSet rs) throws Exception {
		List<Library> libraries = new ArrayList<Library>();
		BookDAO bDao = new BookDAO(getConnection());

		while (rs.next()) {
			Library l = new Library();
			l.setBranchId(rs.getInt("branchId"));
			l.setBranchName(rs.getString("branchName"));
			l.setBranchAddress(rs.getString("branchAddress"));
			@SuppressWarnings("unchecked")
			List<Book> books = (List<Book>) bDao
					.readFirstLevel(
							"select * from tbl_book where bookId In"
									+ "(select bookId from tbl_book_copies where branchId=?)",
							new Object[] { rs.getInt("branchId") });
			l.setBooks(books);
			libraries.add(l);
		}
		return libraries;
	}

	@Override
	public List<Library> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Library> libraries = new ArrayList<Library>();

		while (rs.next()) {
			Library l = new Library();
			l.setBranchId(rs.getInt("branchId"));
			l.setBranchName(rs.getString("branchName"));
			l.setBranchAddress(rs.getString("branchAddress"));

			libraries.add(l);
		}
		return libraries;
	}

	@SuppressWarnings("unchecked")
	public List<Library> readByLibraryName(String searchString, int pageNo,
			int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		searchString = "%" + searchString + "%";
		return (List<Library>) read(
				"select * from tbl_library_branch where branchName like ?",
				new Object[] { searchString });
	}

}
