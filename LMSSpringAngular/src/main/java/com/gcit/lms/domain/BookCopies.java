package com.gcit.lms.domain;

import java.util.UUID;

public class BookCopies {

	private UUID bookId;
	private UUID branchId;
	private int noOfCopies;

	public UUID getBookId() {
		return bookId;
	}

	public void setBookId(UUID bookId) {
		this.bookId = bookId;
	}

	public UUID getBranchId() {
		return branchId;
	}

	public void setBranchId(UUID branchId) {
		this.branchId = branchId;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

}
