package com.gcit.lms.domain;

import java.util.List;

public class Book {
	private int bookId;
	private String title;
	private Publisher publisher;
	
	private List<Author> authors;
	private List<Genre> genres;
	/**
	 * @return the bookId
	 */
	public int getBookId() {
		return bookId;
	}
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the publisher
	 */
	public Publisher getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	/**
	 * @return the authors
	 */
	public List<Author> getAuthors() {
		return authors;
	}
	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	/**
	 * @return the genres
	 */
	public List<Genre> getGenres() {
		return genres;
	}
	/**
	 * @param genres the genres to set
	 */
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
}
