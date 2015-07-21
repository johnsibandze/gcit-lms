package com.gcit.lms.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Publisher {

	private UUID publisherId = UUID.randomUUID();
	private String publisherName;
	private String publisherAddress;
	private String publisherPhone;

	private List<Book> books;

	/**
	 * @return the publisherId
	 */
	@Id
	public UUID getPublisherId() {
		return publisherId;
	}

	/**
	 * @param publisherId
	 *            the publisherId to set
	 */
	public void setPublisherId(UUID publisherId) {
		this.publisherId = publisherId;
	}

	/**
	 * @return the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}

	/**
	 * @param publisherName
	 *            the publisherName to set
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	/**
	 * @return the publisherAddress
	 */
	public String getPublisherAddress() {
		return publisherAddress;
	}

	/**
	 * @param publisherAddress
	 *            the publisherAddress to set
	 */
	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}

	/**
	 * @return the publisherPhone
	 */
	public String getPublisherPhone() {
		return publisherPhone;
	}

	/**
	 * @param publisherPhone
	 *            the publisherPhone to set
	 */
	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
