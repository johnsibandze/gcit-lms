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
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Library;
import com.gcit.lms.domain.Publisher;
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

	@Autowired
	GenreDAO genreDAO;

	@Autowired
	PublisherDAO publisherDAO;

	@Autowired
	LibraryDAO libraryDAO;

	@Autowired
	BorrowerDAO borrowerDAO;

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
		System.out.println("here");
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

	@Transactional
	@RequestMapping(value = "/genre/add", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String addGenre(@RequestBody Genre genre) {
		try {
			genreDAO.create(genre);
			return "Genre added sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Genre add failed";
		}
	}

	@RequestMapping(value = "/genres/get", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json")
	public List<Genre> getGenres() {
		try {
			return genreDAO.readAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/genre/get", method = RequestMethod.POST, consumes = "application/json")
	public Genre getGenre(@RequestBody Genre genre) throws Exception {
		return genreDAO.readOne(genre.getGenreId());
	}

	@Transactional
	@RequestMapping(value = "/genre/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String deleteGenre(@RequestBody Genre genre) {
		try {
			genreDAO.delete(genre);
			return "Genre deleted sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Genre delete failed";
		}
	}

	@Transactional
	@RequestMapping(value = "/genre/update", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String updateGenre(@RequestBody Genre genre) {
		try {
			genreDAO.update(genre);
			return "Genre updated sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Genre update failed";
		}
	}

	@Transactional
	@RequestMapping(value = "/publisher/add", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String addPublisher(@RequestBody Publisher publisher) {
		try {
			publisherDAO.create(publisher);
			return "Publisher added sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Publisher add failed";
		}
	}

	@RequestMapping(value = "/publishers/get", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json")
	public List<Publisher> getPublishers() {
		try {
			return publisherDAO.readAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/publisher/get", method = RequestMethod.POST, consumes = "application/json")
	public Publisher getPublisher(@RequestBody Publisher publisher)
			throws Exception {
		return publisherDAO.readOne(publisher.getPublisherId());
	}

	@Transactional
	@RequestMapping(value = "/publisher/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String deletePublisher(@RequestBody Publisher publisher) {
		try {
			publisherDAO.delete(publisher);
			return "Publisher deleted sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Publisher delete failed";
		}
	}

	@Transactional
	@RequestMapping(value = "/publisher/update", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String updatePublisher(@RequestBody Publisher publisher) {
		try {
			publisherDAO.update(publisher);
			return "Publisher updated sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Publisher update failed";
		}
	}

	@Transactional
	@RequestMapping(value = "/library/add", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String addLibrary(@RequestBody Library library) {
		try {
			libraryDAO.create(library);
			return "Library added sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Library add failed";
		}
	}

	@RequestMapping(value = "/libraries/get", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json")
	public List<Library> getLibraries() {
		try {
			return libraryDAO.readAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/library/get", method = RequestMethod.POST, consumes = "application/json")
	public Library getLibrary(@RequestBody Library library) throws Exception {
		return libraryDAO.readOne(library.getBranchId());
	}

	@Transactional
	@RequestMapping(value = "/library/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String deleteLibrary(@RequestBody Library library) {
		try {
			libraryDAO.delete(library);
			return "Library deleted sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Library delete failed";
		}
	}

	@Transactional
	@RequestMapping(value = "/library/update", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String updateLibrary(@RequestBody Library library) {
		try {
			libraryDAO.update(library);
			return "Library updated sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Library update failed";
		}
	}

	@Transactional
	@RequestMapping(value = "/borrower/add", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String addBorrower(@RequestBody Borrower borrower) {
		try {
			borrowerDAO.create(borrower);
			return "Borrower added sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Borrower add failed";
		}
	}

	@RequestMapping(value = "/borrowers/get", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json")
	public List<Borrower> getBorrowers() {
		try {
			return borrowerDAO.readAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/borrower/get", method = RequestMethod.POST, consumes = "application/json")
	public Borrower getBorrower(@RequestBody Borrower borrower)
			throws Exception {
		return borrowerDAO.readOne(borrower.getCardNo());
	}

	@Transactional
	@RequestMapping(value = "/borrower/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String deleteBorrower(@RequestBody Borrower borrower) {
		try {
			borrowerDAO.delete(borrower);
			return "Borrower deleted sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Borrower delete failed";
		}
	}

	@Transactional
	@RequestMapping(value = "/borrower/update", method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = "application/json")
	public String updateBorrower(@RequestBody Borrower borrower) {
		try {
			borrowerDAO.update(borrower);
			return "Borrower updated sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Borrower update failed";
		}
	}

}
