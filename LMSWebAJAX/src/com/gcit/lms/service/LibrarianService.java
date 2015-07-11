package com.gcit.lms.service;

import java.sql.Connection;
import java.util.List;

import com.gcit.lms.dao.LibraryDAO;
import com.gcit.lms.domain.Library;

public class LibrarianService extends BaseService {

	public List<Library> readLibraries(int pageNo, int pageSize)
			throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		return ldao.readAll(pageNo, pageSize);
	}

	public List<Library> searchLibraries(String searchString, int pageNo,
			int pageSize) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		return ldao.readByLibraryName(searchString, pageNo, pageSize);
	}

	public Library readLibrary(int branchId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryDAO ldao = new LibraryDAO(conn);
		return ldao.readOne(branchId);
	}

}
