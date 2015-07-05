package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch> {

	public LibraryBranchDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(LibraryBranch libraryBranch) throws Exception {
		// save the book and get it's id
		int branchId = saveWithID(
				"insert into tbl_library_branch (branchName, branchAddress) values(?, ?)",
				new Object[] { libraryBranch.getBranchName(),
						libraryBranch.getBranchAddress() });

		// if the book wasn't saved
		if (branchId == -1) {
			throw new Exception("creating branch failed");
		}

		// // insert into book authors table
		// for (Book b : libraryBranch.getBooks()) {
		// save("insert into tbl_book_authors (bookId, authorId) values (?,?)",
		// new Object[] { branchId, b.getAuthorId() });
		// }
		//
		// // insert into book genres table
		// for (Genre g : libraryBranch.getGenres()) {
		// save("insert into tbl_book_genres (bookId, genre_id) values (?,?)",
		// new Object[] { branchId, g.getGenreId() });
		// }
	}

	public void update(LibraryBranch libraryBranch) throws Exception {
		save("update tbl_library_branch set branchName = ?, branchAddress=? where branchId = ?",
				new Object[] { libraryBranch.getBranchName(),
						libraryBranch.getBranchAddress(),
						libraryBranch.getBranchId() });
	}

	public void delete(LibraryBranch libraryBranch) throws Exception {
		save("delete from tbl_library_branch where branchId = ?",
				new Object[] { libraryBranch.getBranchId() });
	}

	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAll() throws Exception {
		return (List<LibraryBranch>) read("select * from tbl_library_branch",
				null);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws Exception {
		List<LibraryBranch> branches = new ArrayList<LibraryBranch>();
		// PublisherDAO pdao = new PublisherDAO(getConnection());
		BookDAO bDao = new BookDAO(getConnection());
		// GenreDAO gDao = new GenreDAO(getConnection());
		while (rs.next()) {
			LibraryBranch lb = new LibraryBranch();
			lb.setBranchId(rs.getInt("branchId"));
			lb.setBranchName(rs.getString("branchName"));
			lb.setBranchAddress(rs.getString("branchAddress"));

			// the books found in this library
			@SuppressWarnings("unchecked")
			List<Book> books = (List<Book>) bDao
					.readFirstLevel(
							"select * from tbl_book where bookId In"
									+ "(select bookId from tbl_book_copies where branchId=?)",
							new Object[] { rs.getInt("branchId") });

			lb.setBooks(books);
			// lb.setGenres(genres);
			branches.add(lb);
		}

		return branches;
	}

	@Override
	public List<LibraryBranch> extractDataFirstLevel(ResultSet rs)
			throws Exception {
		List<LibraryBranch> librarybranches = new ArrayList<LibraryBranch>();
		// PublisherDAO pdao = new PublisherDAO(getConnection());
		// AuthorDAO aDao = new AuthorDAO(getConnection());
		// GenreDAO gD
		while (rs.next()) {
			LibraryBranch lb = new LibraryBranch();
			lb.setBranchId(rs.getInt("branchId"));
			lb.setBranchName(rs.getString("branchName"));
			lb.setBranchAddress(rs.getString("branchAddress"));
			librarybranches.add(lb);
		}

		return librarybranches;
	}

}
