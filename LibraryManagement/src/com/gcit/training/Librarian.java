package com.gcit.training;

public class Librarian extends User {

	/** the name of the branch that this librarian manages. */
	private String branchName;

	/** the address of the branch that this librarian manages. */
	private String branchAddress;

	/** the id of the branch that this librarian manages. */
	private int branchId;

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}
