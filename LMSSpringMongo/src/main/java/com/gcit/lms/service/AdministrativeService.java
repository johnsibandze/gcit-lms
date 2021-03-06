package com.gcit.lms.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Publisher;

public class AdministrativeService {

	@Autowired
	BasicDataSource ds;

	@Autowired
	AuthorDAO adao;

	@Autowired
	BookDAO bdao;

	@Autowired
	PublisherDAO pdao;

	@Autowired
	GenreDAO gdao;

	@Transactional
	public void createAuthor(Author author) throws Exception {

		if (author == null || author.getAuthorName() == null
				|| author.getAuthorName().length() == 0
				|| author.getAuthorName().length() > 45) {
			throw new Exception(
					"Author Name cannot be empty or more than 45 Chars");
		} else {
			adao.create(author);
		}
	}

	@Transactional
	public void createPublisher(Publisher publisher) throws Exception {
		pdao.create(publisher);
	}

	@Transactional
	public void createBook(Book book) throws Exception {
		bdao.create(book);
	}

	public List<Author> readAuthors(int pageNo, int pageSize) throws Exception {
		return adao.readAll(pageNo, pageSize);
	}

	public Author readAuthor(UUID uuid) throws Exception {
		return adao.readOne(uuid);
	}

	@Transactional
	public void deleteAuthor(Author author) throws Exception {
		adao.delete(author);
	}

	@Transactional
	public void updateAuthor(Author a) throws Exception {
		adao.update(a);
	}

	public List<Genre> readGenres() throws Exception {
		List<Genre> genres = gdao.readAll();
		return genres;
	}

	public List<Publisher> readPublishers() throws Exception {
		List<Publisher> pubs = pdao.readAll();
		return pubs;
	}

	public Genre readGenre(UUID genreId) throws Exception {
		return gdao.readOne(genreId);
	}

	public List<Author> searchAuthors(String searchString) throws Exception {
		return adao.readByAuthorName(searchString);
	}
}
