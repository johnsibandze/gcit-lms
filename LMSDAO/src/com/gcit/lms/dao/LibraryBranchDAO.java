package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO {

	public void create(LibraryBranch libraryBranch) throws Exception {
		save("insert into tbl_library_branch (branchName, branchAddress) values(?, ?)",
				new Object[] { libraryBranch.getBranchName(),
						libraryBranch.getBranchAddress() });
	}

	public void update(LibraryBranch libraryBranch) throws Exception {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { libraryBranch.getBranchName(),
						libraryBranch.getBranchAddress(),
						libraryBranch.getBranchId() });
	}

	public void delete(LibraryBranch libraryBranch) throws Exception {
		save("delete from tbl_library_branch where branchId = ?",
				new Object[] { libraryBranch.getBranchId() });
	}

	public List<LibraryBranch> readAll() throws Exception {
		return (List<LibraryBranch>) read("select * from tbl_library_branch",
				null);
	}

	public LibraryBranch readOne(int branchId) throws Exception {
		List<LibraryBranch> libraries = (List<LibraryBranch>) read(
				"select * from tbl_library_branch where branchId = ?",
				new Object[] { branchId });
		if (libraries != null && libraries.size() > 0) {
			return libraries.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<LibraryBranch> libraries = new ArrayList<LibraryBranch>();

		while (rs.next()) {
			LibraryBranch lb = new LibraryBranch();
			lb.setBranchId(rs.getInt("branchId"));
			lb.setBranchName(rs.getString("branchName"));
			lb.setBranchAddress(rs.getString("branchAddress"));

			libraries.add(lb);
		}
		return libraries;
	}

}
