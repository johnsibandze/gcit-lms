package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Publisher;

public class PublisherDAO extends BaseDAO {

	public PublisherDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(Publisher publisher) throws Exception {
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values(?, ?, ?)",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone() });
	}

	public void update(Publisher publisher) throws Exception {
		save("update tbl_publisher set publisherName = ?, publisherAddress=?, publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),  publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId() });

	}

	public void delete(Author author) throws Exception {
		save("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorName() });
	}

	public List<Author> readAll() throws Exception{
		return (List<Author>) read("select * from tbl_author", null);
		
	}

	public Publisher readOne(int publisherId) throws Exception {
		List<Publisher> publishers = (List<Publisher>) read("select * from tbl_publisher", new Object[] {publisherId});
		if(publishers!=null && publishers.size()>0){
			return publishers.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<Author> authors =  new ArrayList<Author>();
		
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			
			authors.add(a);
		}
		return authors;
	}

	@Override
	public List extractDataFirstLevel(ResultSet rs) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
