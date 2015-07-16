package com.gcit.lms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public abstract class BaseDAO<T> {

	@Autowired
	protected JdbcTemplate template;
	
	private int pageNo = -1;
	private int pageSize = 10;

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}


	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}


	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
