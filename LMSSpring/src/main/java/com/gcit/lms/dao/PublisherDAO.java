package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Publisher;
import com.gcit.lms.domain.Book;

public class PublisherDAO extends BaseDAO<Publisher> implements
		ResultSetExtractor<List<Publisher>> {

	@Autowired
	BookDAO bDao;

	public void create(Publisher publisher) throws Exception {
		template.update("insert into tbl_publisher (publisherName) values(?)",
				new Object[] { publisher.getPublisherName() });
	}

	public void update(Publisher publisher) throws Exception {
		template.update(
				"update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(),
						publisher.getPublisherPhone(),
						publisher.getPublisherId() });
	}

	public void delete(Publisher publisher) throws Exception {
		template.update("delete from tbl_publisher where publisherId = ?",
				new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> readAll(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return  template.query("select * from tbl_publisher",
				this);
	}

	public int readCount(int pageNo, int pageSize) throws Exception {
		setPageNo(pageNo);
		setPageSize(pageSize);

		return template.queryForObject("select count(*) from tbl_publisher",
				Integer.class);
	}

	public Publisher readOne(int publisherId) throws Exception {
		List<Publisher> publishers =  template.query(
				"select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId }, this);
		if (publishers != null && publishers.size() > 0) {
			return publishers.get(0);
		}
		return null;
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();

		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));

			List<Book> books =  template
					.query("select * from tbl_book join tbl_publisher where publisherId = pubId and publisherId = ?",
							new Object[] { rs.getInt("publisherId") }, bDao);
			p.setBooks(books);

			publishers.add(p);
		}
		return publishers;
	}

	public List<Publisher> readByPublisherName(String searchString)
			throws Exception {
		searchString = "%" + searchString + "%";
		return  template.query(
				"select * from tbl_publisher where publisherName like ?",
				new Object[] { searchString }, this);
	}

	public List<Book> readBookByPublisherName(String searchString)
			throws Exception {
		searchString = "%" + searchString + "%";
		return  template
				.query("select * from tbl_book join tbl_publisher  where pubId = publisherId and publisherName like ?",
						new Object[] { searchString }, bDao);
	}

}
