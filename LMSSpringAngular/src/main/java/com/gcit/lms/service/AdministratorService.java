package com.gcit.lms.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Publisher;

//@Scope("prototype")
public class AdministratorService {

	@Autowired
	public BasicDataSource ds;

	@Autowired
	private AuthorDAO authorDAO;

	@Autowired
	private PublisherDAO pubDAO;

	@Autowired
	private BookDAO bookDAO;

	@Transactional
	public void addAuthor(Author author) throws Exception {
		authorDAO.create(author);
	}

	@Transactional
	public void deleteAuthor(Author author) throws Exception {
		authorDAO.delete(author);
	}

	@Transactional
	public void addPublisher(Publisher p) throws Exception {
		pubDAO.create(p);
	}

	public List<Author> getAuthors(int pageNo, int pageSize) throws Exception {
		authorDAO.setPageNo(pageNo);
		authorDAO.setPageSize(pageSize);
		return authorDAO.readAll();
	}

	public List<Book> getBooks() throws Exception {
		return bookDAO.readAll();
	}

	public List<Publisher> getPublishers() throws Exception {
		return pubDAO.readAll();
	}

	public Author getAuthor(UUID authorId) throws Exception {
		return authorDAO.readOne(authorId);
	}

	@Transactional
	public void editAuthor(Author author) throws Exception {
		authorDAO.update(author);
	}

	@Transactional
	public void addBook(Book b) throws Exception {
		bookDAO.create(b);
	}

	// public List<Book> searchBooks(String searchString) throws Exception {
	// return bookDAO.searchBookByTitle(searchString);
	// }

}
