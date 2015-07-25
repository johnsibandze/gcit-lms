package com.gcit.lms.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book {

	@Id
	private long bookId;

	private String title;

	private List<Author> authors;

	private Publisher publisher;

	private List<Genre> genres;

	/**
	 * @return the bookId
	 */
	public long getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 *            the bookId to set
	 */
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the authors
	 */
	public List<Author> getAuthors() {
		return authors;
	}

	/**
	 * @param authors
	 *            the authors to set
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((authors == null) ? 0 : authors.hashCode());
	// result = prime * result + (int) (bookId ^ (bookId >>> 32));
	// result = prime * result
	// + ((publisher == null) ? 0 : publisher.hashCode());
	// result = prime * result + ((title == null) ? 0 : title.hashCode());
	// return result;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	// Book other = (Book) obj;
	// if (authors == null) {
	// if (other.authors != null)
	// return false;
	// } else if (!authors.equals(other.authors))
	// return false;
	// if (bookId != other.bookId)
	// return false;
	// if (publisher == null) {
	// if (other.publisher != null)
	// return false;
	// } else if (!publisher.equals(other.publisher))
	// return false;
	// if (title == null) {
	// if (other.title != null)
	// return false;
	// } else if (!title.equals(other.title))
	// return false;
	// return true;
	// }
}
