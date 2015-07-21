package com.gcit.lms.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Author {

	private UUID authorId = UUID.randomUUID();
	private String authorName;
	private List<Book> books;

	/**
	 * @return the authorId
	 */
	@Id
	public UUID getAuthorId() {
		return authorId;
	}

	/**
	 * @param authorId
	 *            the authorId to set
	 */
	public void setAuthorId(UUID authorId) {
		this.authorId = authorId;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName
	 *            the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * @param books
	 *            the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
