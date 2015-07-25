package com.gcit.lms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gcit.lms.domain.SequenceId;

public abstract class BaseDAO<T> {
	
	private int pageNo = -1;
	private int pageSize = 10;
	
	@Autowired
	public JdbcTemplate template;
	
	@Autowired
	public MongoOperations mongoOps;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getNextSequenceId(String key){
		Query query = new Query(Criteria.where("_id").is(key));
		Update update = new Update();
		update.inc("seq", 1);
		
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		
		SequenceId seqId = mongoOps.findAndModify(query,  update,  options, SequenceId.class);
		
		return seqId.getSeq();
	}
}
