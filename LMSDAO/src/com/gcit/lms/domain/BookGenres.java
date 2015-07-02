package com.gcit.lms.domain;

public class BookGenres {

	private int genreId;
	private int bookId;

	public BookGenres() {

	}

	public BookGenres(int genreId, int bookId) {
		super();
		this.genreId = genreId;
		this.bookId = bookId;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

}
