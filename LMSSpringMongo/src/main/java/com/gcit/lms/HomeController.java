package com.gcit.lms;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.service.AdministrativeService;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {

	@Autowired
	AdministrativeService adminService;

	@Autowired
	AuthorDAO authorDAO;

	@Autowired
	BookDAO bookDAO;

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/authors/get/{pageNo}/{pageSize}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public List<Author> getAuthors(@PathVariable int pageNo,
			@PathVariable int pageSize) {
		try {
			return authorDAO.readAll(pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/authors/get", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json")
	public List<Author> getAuthors() {
		try {
			return authorDAO.readAll(0, 100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/books/get", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json")
	public List<Book> getBooks() {
		try {
			return bookDAO.readAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@RequestMapping(value = "/author/add", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String addAuthor(@RequestBody Author author) {
		try {
			authorDAO.create(author);
			return "Author added sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Author add failed";
		}
	}

	@Transactional
	@RequestMapping(value = "/book/add", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String addBook(@RequestBody Book book) {
		try {
			bookDAO.create(book);
			return "Book added sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Book add failed";
		}
	}

	@RequestMapping(value = "/author/get", method = RequestMethod.POST, consumes = "application/json")
	public Author getAuthor(@RequestBody Author auth) throws Exception {
		return authorDAO.readOne(auth.getAuthorId());
	}

	@Transactional
	@RequestMapping(value = "/author/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String deleteAuthor(@RequestBody Author author) {
		try {
			authorDAO.delete(author);
			return "Author deleted sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Author delete failed";
		}
	}

	@Transactional
	@RequestMapping(value = "/author/update", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String updateAuthor(@RequestBody Author author) {
		try {
			authorDAO.update(author);
			return "Author updated sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Author update failed";
		}
	}

	@Transactional
	@RequestMapping(value = "/book/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String deleteBook(@RequestBody Book book) {
		try {
			bookDAO.delete(book);
			return "Book deleted sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Book delete failed";
		}
	}
	
	@Transactional
	@RequestMapping(value = "/book/update", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String updateBook(@RequestBody Book book) {
		try {
			bookDAO.update(book);
			return "Book updated sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Book update failed";
		}
	}
	
	@RequestMapping(value = "/book/get", method = RequestMethod.POST, consumes = "application/json")
	public Book getBook(@RequestBody Book book) throws Exception {
		return bookDAO.readOne(book.getBookId());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}