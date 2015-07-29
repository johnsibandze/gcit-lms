package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> implements
		ResultSetExtractor<List<Publisher>> {

	private final String PUBLISHER_COLLECTION = "Publishers";

	public void create(Publisher publisher)  {
		mongoOps.insert(publisher, PUBLISHER_COLLECTION);
	}

	public void update(Publisher publisher)  {
		// Query query = new Query(Criteria.where("_id").is(
		// publisher.getPublisherId()));
		// Publisher oldPublisher = mongoOps.findOne(query, Publisher.class,
		// PUBLISHER_COLLECTION);
		//
		// oldPublisher.setPublisherName(publisher.getPublisherName());
		// oldPublisher.setPublisherAddress(publisher.getPublisherAddress());
		// oldPublisher.setPublisherPhone(publisher.getPublisherPhone());

		mongoOps.save(publisher, PUBLISHER_COLLECTION);
	}

	public void delete(Publisher publisher) throws Exception {
		Query query = new Query(Criteria.where("_id").is(
				publisher.getPublisherId()));
		mongoOps.remove(query, PUBLISHER_COLLECTION);
	}

	public List<Publisher> readAll() throws Exception {
		return mongoOps.findAll(Publisher.class, PUBLISHER_COLLECTION);
	}

	public Publisher readOne(UUID publisherId) throws SQLException {
		Query query = new Query(Criteria.where("_id").is(publisherId));
		return this.mongoOps.findOne(query, Publisher.class,
				PUBLISHER_COLLECTION);
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> pubs = new ArrayList<Publisher>();

		while (rs.next()) {
			Publisher pub = new Publisher();
			// pub.setPublisherId(rs.getInt("publisherId"));
			pub.setPublisherName(rs.getString("publisherName"));
			pub.setPublisherAddress(rs.getString("publisherAddress"));
			pub.setPublisherPhone(rs.getString("publisherPhone"));
			pubs.add(pub);
		}
		return pubs;
	}
}
