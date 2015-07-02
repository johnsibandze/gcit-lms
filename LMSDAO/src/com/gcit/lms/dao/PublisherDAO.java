//package com.gcit.lms.dao;
//
//import com.gcit.lms.domain.Publisher;
//
//public class PublisherDAO extends BaseDAO {
//
//	public void create(Publisher pub) throws Exception {
//		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values(?, ?, ?)",
//				new Object[] { pub.getPublisherName(), pub.getPublisherAddress(), pub.getPublisherPhone() });
//	}
//
////	public void update(Author author) throws Exception {
////		save("update tbl_author set authorName = ? where authorId = ?",
////				new Object[] { author.getAuthorName() });
////
////	}
////
////	public void delete(Author author) throws Exception {
////		save("delete from tbl_author where authorId = ?",
////				new Object[] { author.getAuthorName() });
////	}
////
////	public List<Author> readAll() {
////		return null;
////
////	}
////
////	public Author readOne() {
////		return null;
////	}
//
// }
