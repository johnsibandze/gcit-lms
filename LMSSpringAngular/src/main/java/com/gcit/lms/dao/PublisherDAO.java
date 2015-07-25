package com.gcit.lms.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> implements Serializable,
		ResultSetExtractor<List<Publisher>> {

	private static final long serialVersionUID = 1619700647002508164L;

	public void addPublisher(Publisher publisher) throws SQLException {
		template.update("insert into tbl_publisher (publisherName) values (?)",
				new Object[] { publisher.getName() });

	}

	public void updatePublisher(Publisher publisher) throws SQLException {
		template.update(
				"update tbl_publisher set publisherName = ? where publisherId = ?",
				new Object[] { publisher.getName(), publisher.getId() });
	}

	public void removePublisher(Publisher publisher) throws SQLException {
		template.update("delete from tbl_publisher where publisherId=?",
				new Object[] { publisher.getId() });
	}

	public List<Publisher> readAll() throws SQLException {
		return template.query("select * from tbl_publisher", this);
	}

	public Publisher readOne(int publisherId) throws SQLException {
		List<Publisher> publishers = template.query(
				"select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId }, this);
		if (publishers != null && publishers.size() > 0) {
			return publishers.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		while (rs.next()) {
			Publisher a = new Publisher();
			a.setId(rs.getInt("publisherId"));
			a.setName(rs.getString("publisherName"));

			publishers.add(a);
		}
		return publishers;
	}
}
