package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Publisher;

public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>>{

	

	public void create(Publisher publisher) throws Exception {
		template.update("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values(?, ?, ?)",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone() });
	}

	public void update(Publisher publisher) throws Exception {
		template.update("update tbl_publisher set publisherName = ?, publisherAddress=?, publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),  publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId() });

	}

	public void delete(Author author) throws Exception {
		template.update("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorName() });
	}

	public List<Publisher> readAll() throws Exception{
		return (List<Publisher>) template.query("select * from tbl_publisher", this);
		
	}

	public Publisher readOne(int publisherId) throws SQLException {
		List<Publisher> publishers = (List<Publisher>) template.query("select * from tbl_publisher", new Object[] {publisherId}, this);
		if(publishers!=null && publishers.size()>0){
			return publishers.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws SQLException {
		List<Publisher> pubs=  new ArrayList<Publisher>();
		
		while(rs.next()){
			Publisher pub = new Publisher();
			pub.setPublisherId(rs.getInt("publisherId"));
			pub.setPublisherName(rs.getString("publisherName"));
			pub.setPublisherAddress(rs.getString("publisherAddress"));
			pub.setPublisherPhone(rs.getString("publisherPhone"));
			pubs.add(pub);
		}
		return pubs;
	}
}
