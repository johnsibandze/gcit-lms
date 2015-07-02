package com.gcit.lms.domain;

public class BookAuthors {

	private int bookId;
	private int authorId;

	public BookAuthors() {

	}

	public BookAuthors(int bookId, int authorId) {
		super();
		this.bookId = bookId;
		this.authorId = authorId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

}
