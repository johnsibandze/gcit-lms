package com.gcit.training;

public class Book {

	private int bookId;
	private String title;
	private String authorName;
	
	public Book() {
		
	}

	public Book(int bookId, String title, String authorName) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.authorName = authorName;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

}
