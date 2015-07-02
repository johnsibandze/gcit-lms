package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch> {

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
		List<LibraryBranch> libraryBranches = (List<LibraryBranch>) read(
				"select * from tbl_library_branch where branchId = ?",
				new Object[] { branchId });
		if (libraryBranches != null && libraryBranches.size() > 0) {
			return libraryBranches.get(0);
		}
		return null;
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws Exception {
		List<LibraryBranch> libraryBranches = new ArrayList<LibraryBranch>();

		while (rs.next()) {

			int branchId = rs.getInt("branchId");
			String branchName = rs.getString("branchNAme");
			String branchAddress = rs.getString("branchAddress");

			LibraryBranch lb = new LibraryBranch(branchId, branchName,
					branchAddress);

			libraryBranches.add(lb);
		}
		return libraryBranches;
	}

}
