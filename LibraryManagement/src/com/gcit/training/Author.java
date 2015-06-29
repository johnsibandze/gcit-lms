package com.gcit.training;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Author {

	private int authorId;
	private String authorName;

	public Author() {

	}

	public Author(int authorId, String authorName) {
		super();
		this.authorId = authorId;
		this.authorName = authorName;

	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @param author
	 * @return true if author has books
	 */
	public boolean hasBooks() {
		try {
			String selectQuery = "SELECT * FROM tbl_book_authors where authorId=?";

			PreparedStatement pstmt = LibMangApp.conn
					.prepareStatement(selectQuery);
			pstmt.setInt(1, authorId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
