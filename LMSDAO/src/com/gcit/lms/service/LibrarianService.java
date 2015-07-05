package com.gcit.lms.service;

import java.sql.Connection;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.LibraryBranch;

public class LibrarianService {

	public void updateLibraryBranch(LibraryBranch libraryBranch)
			throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (libraryBranch == null || libraryBranch.getBranchName() == null
					|| libraryBranch.getBranchName().length() == 0
					|| libraryBranch.getBranchName().length() > 45) {
				throw new Exception(
						"Library Branch Name cannot be empty or more than 45 Chars");
			} else if (libraryBranch == null
					|| libraryBranch.getBranchAddress() == null
					|| libraryBranch.getBranchAddress().length() == 0
					|| libraryBranch.getBranchAddress().length() > 45) {
				throw new Exception(
						"Library Branch Address cannot be empty or more than 45 Chars");
			} else {
				LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
				lbdao.update(libraryBranch);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void addBookCopies(BookCopies bookCopies) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (bookCopies == null || bookCopies.getNoOfCopies() == 0) {
				throw new Exception("You must add at least 1 copy");
			} else {
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);
				bcdao.create(bookCopies);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

}
