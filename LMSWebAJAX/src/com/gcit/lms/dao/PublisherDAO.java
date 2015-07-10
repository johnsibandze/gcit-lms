package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(Publisher publisher) throws Exception {
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values(?, ?, ?)",
				new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(),
						publisher.getPublisherPhone() });
	}

	public void update(Publisher publisher) throws Exception {
		save("update tbl_publisher set publisherName = ?, publisherAddress=?, publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(),
						publisher.getPublisherPhone(),
						publisher.getPublisherId() });

	}

	public void delete(Publisher publisher) throws Exception {
		save("delete from tbl_publisher where publisherId = ?",
				new Object[] { publisher.getPublisherId() });
	}

	@SuppressWarnings("unchecked")
	public List<Publisher> readAll() throws Exception {
		return (List<Publisher>) read("select * from tbl_publisher", null);

	}

	public Publisher readOne(int publisherId) throws Exception {
		@SuppressWarnings("unchecked")
		List<Publisher> publishers = (List<Publisher>) read(
				"select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId });
		if (publishers != null && publishers.size() > 0) {
			return publishers.get(0);
		}
		return null;
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws Exception {
		List<Publisher> pubs = new ArrayList<Publisher>();

		while (rs.next()) {
			Publisher pub = new Publisher();
			pub.setPublisherId(rs.getInt("publisherId"));
			pub.setPublisherName(rs.getString("publisherName"));
			pub.setPublisherAddress(rs.getString("publisherAddress"));
			pub.setPublisherPhone(rs.getString("publisherPhone"));
			pubs.add(pub);
		}
		return pubs;
	}

	@Override
	public List<Publisher> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Publisher> pubs = new ArrayList<Publisher>();
		while (rs.next()) {
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
