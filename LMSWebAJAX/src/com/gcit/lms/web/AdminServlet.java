package com.gcit.lms.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Library;
import com.gcit.lms.domain.Publisher;
import com.gcit.lms.service.AdministrativeService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/addPublisher", "/viewAuthors", "/deleteAuthor",
		"/editAuthor", "/addBook", "/searchAuthors", "/pageAuthors",
		"/viewBooks", "/deleteBook", "/editBook", "/pageBooks",
		"/searchBooksAdmin", "/pagePublishers", "/searchPublishers",
		"/pageLibrariesAdmin", "/editPublisher", "/deletePublisher",
		"/editLibraryAdmin", "/addLibrary", "/deleteLibrary", "/addGenre",
		"/pageGenres" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final int PAGE_SIZE = 3;

	private String searchString;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		case "/deleteAuthor":
			deleteAuthor(request, response);
			break;
		case "/deleteBook":
			deleteBook(request, response);
		case "/pageAuthors":
			pageAuthors(request, response);
			break;
		case "/searchAuthors":
			searchAuthors(request, response);
			break;
		case "/pageBooks":
			pageBooks(request, response);
			break;
		case "/searchBooksAdmin":
			searchBooks(request, response);
			break;
		case "/pagePublishers":
			pagePublishers(request, response);
			break;
		case "/searchPublishers":
			searchPublishers(request, response);
			break;
		case "/pageLibrariesAdmin":
			pageLibraries(request, response);
			break;
		case "/deletePublisher":
			deletePublisher(request, response);
			break;
		case "/deleteLibrary":
			deleteLibrary(request, response);
			break;
		case "/pageGenres":
			pageGenres(request, response);
			break;
		default:
			break;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		case "/addAuthor":
			createAuthor(request, response);
			break;

		case "/addPublisher":
			createPublisher(request, response);
			break;

		case "/viewAuthors":
			viewAuthors(request, response);
			break;
		case "/editAuthor": {
			editAuthor(request, response);
			break;
		}
		case "/addBook":
			createBook(request, response);
			break;
		case "/searchAuthors":
			searchAuthors(request, response);
			break;
		case "/viewBooks":
			viewBooks(request, response);
			break;
		case "/editBook": {
			editBook(request, response);
			break;
		}
		case "/searchPublishers":
			searchPublishers(request, response);
			break;
		case "/searchBooksAdmin":
			searchBooks(request, response);
			break;
		case "/editPublisher":
			editPublisher(request, response);
			break;
		case "/editLibraryAdmin":
			editLibrary(request, response);
			break;
		case "/addLibrary":
			createLibrary(request, response);
			break;
		case "/addGenre":
			createGenre(request, response);
			break;
		default:
			break;
		}
	}

	private void createBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		int pubId = Integer.parseInt(request.getParameter("pubId"));

		AdministrativeService adminService = new AdministrativeService();
		List<Author> authors = new ArrayList<Author>();
		List<Genre> genres = new ArrayList<Genre>();

		try {
			Book book = new Book();
			book.setTitle(title);
			book.setPublisher(adminService.readPublisher(pubId));

			String[] authorIds = request.getParameterValues("authorId");
			for (String s : authorIds) {
				int authorId = Integer.parseInt(s);
				authors.add(adminService.readAuthor(authorId));
			}

			String[] genreIds = request.getParameterValues("genreId");
			for (String s : genreIds) {
				int genreId = Integer.parseInt(s);
				genres.add(adminService.readGenre(genreId));
			}

			book.setAuthors(authors);
			book.setGenres(genres);
			adminService.createBook(book);
			request.setAttribute("result", "Book Added Succesfully!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("result",
					"Book Addition Failed because: " + e.getMessage());
			e.printStackTrace();
		}

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBooksAdmin.jsp");
		rd.forward(request, response);

	}

	private void editAuthor(HttpServletRequest request,
			HttpServletResponse response) {
		String authorName = request.getParameter("authorName");
		int authorId = Integer.parseInt(request.getParameter("authorId"));
		Author a = new Author();
		a.setAuthorName(authorName);
		a.setAuthorId(authorId);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.updateAuthor(a);
			request.setAttribute("result", "Author updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewAuthors.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createAuthor(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authorName = request.getParameter("authorName");
		Author a = new Author();
		a.setAuthorName(authorName);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createAuthor(a);
			request.setAttribute("result", "Author Added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");
		rd.forward(request, response);
	}

	private void createPublisher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String publisherName = request.getParameter("publisherName");
		String publisherAddress = request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");
		Publisher p = new Publisher();
		p.setPublisherName(publisherName);
		p.setPublisherAddress(publisherAddress);
		p.setPublisherPhone(publisherPhone);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createPublisher(p);
			request.setAttribute("result", "Publisher added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Publisher add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");
		rd.forward(request, response);
	}

	private List<Author> viewAuthors(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			return new AdministrativeService().readAuthors(0, PAGE_SIZE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void pageAuthors(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Author> authors;

			if (searchString == null) {
				authors = new AdministrativeService().readAuthors(pageNo,
						PAGE_SIZE);
			} else {
				authors = new AdministrativeService().searchAuthors(
						searchString, pageNo, PAGE_SIZE);
			}
			request.setAttribute("authors", authors);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewAuthors.jsp");
		rd.forward(request, response);
	}

	private void searchAuthors(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");

		this.searchString = searchString;

		try {
			// by default, show the first page of the search results
			List<Author> authors = new AdministrativeService().searchAuthors(
					searchString, 0, PAGE_SIZE);
			request.setAttribute("authors", authors);
			StringBuilder str = new StringBuilder();
			str.append("<tr><th>Author ID</th><th>Author Name</th><th>Edit Author</th><th>Delete Author</th></tr>");
			for (Author a : authors) {
				str.append("<tr><td>"
						+ a.getAuthorId()
						+ "</td><td>"
						+ a.getAuthorName()
						+ "</td><td><button type='button' "
						+ "class='btn btn-md btn-success' data-toggle='modal' data-target='#myModal1' href='editAuthor.jsp?authorId="
						+ a.getAuthorId()
						+ "'>"
						+ "Edit</button></td><td><button type='button' class='btn btn-md btn-danger' onclick='javascript:location.href="
						+ "'deleteAuthor?authorId=" + a.getAuthorId()
						+ "'>Delete</button></td></tr>");
			}
			response.getWriter().write(str.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteAuthor(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authorId = request.getParameter("authorId");
		Author author = new Author();
		author.setAuthorId(Integer.parseInt(authorId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewAuthors.jsp");
		try {
			new AdministrativeService().deleteAuthor(author);

			request.setAttribute("result", "Author Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Author Delete Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}

	private List<Book> viewBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			return new AdministrativeService().readBooks(0, PAGE_SIZE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void deleteBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = new Book();
		book.setBookId(Integer.parseInt(bookId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBooksAdmin.jsp");
		try {
			new AdministrativeService().deleteBook(book);

			request.setAttribute("result", "Book Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Book Delete Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}

	private void editBook(HttpServletRequest request,
			HttpServletResponse response) {
		String title = request.getParameter("title");
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Book b = new Book();
		b.setTitle(title);
		b.setBookId(bookId);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.updateBook(b);
			request.setAttribute("result", "Author updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBooksAdmin.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void pageBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Book> books;

			if (searchString == null) {
				books = new AdministrativeService()
						.readBooks(pageNo, PAGE_SIZE);
			} else {
				books = new AdministrativeService().searchBooks(searchString,
						pageNo, PAGE_SIZE);
			}
			request.setAttribute("books", books);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBooksAdmin.jsp");
		rd.forward(request, response);
	}

	private void searchBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");

		this.searchString = searchString;

		System.out.println("search string: " + searchString);

		try {
			// by default, show the first page of the search results
			List<Book> books = new AdministrativeService().searchBooks(
					searchString, 0, PAGE_SIZE);
			request.setAttribute("books", books);
			StringBuilder str = new StringBuilder();
			str.append("<tr><th>Book ID</th><th>Book Title</th><th>Edit Book</th><th>Delete Book</th></tr>");
			for (Book b : books) {
				System.out.println("title: " + b.getTitle());
				str.append("<tr><td>"
						+ b.getBookId()
						+ "</td><td>"
						+ b.getTitle()
						+ "</td><td><button type='button' "
						+ "class='btn btn-md btn-success' data-toggle='modal' data-target='#myModal1' href='editBook.jsp?bookId="
						+ b.getBookId()
						+ "'>"
						+ "Edit</button></td><td><button type='button' class='btn btn-md btn-danger' onclick='javascript:location.href="
						+ "'deleteBook?bookId=" + b.getBookId()
						+ "'>Delete</button></td></tr>");
			}
			response.getWriter().write(str.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void pagePublishers(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Publisher> publishers;

			if (searchString == null) {
				publishers = new AdministrativeService().readPublishers(pageNo,
						PAGE_SIZE);
			} else {
				publishers = new AdministrativeService().searchPublishers(
						searchString, pageNo, PAGE_SIZE);
			}
			request.setAttribute("publishers", publishers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewPublishers.jsp");
		rd.forward(request, response);
	}

	private void searchPublishers(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");

		this.searchString = searchString;

		System.out.println("search string: " + searchString);

		try {
			// by default, show the first page of the search results
			List<Publisher> publishers = new AdministrativeService()
					.searchPublishers(searchString, 0, PAGE_SIZE);
			request.setAttribute("publishers", publishers);
			StringBuilder str = new StringBuilder();
			str.append("<tr><th>Publisher ID</th><th>Publisher Name</th><th>Edit Publisher</th><th>Delete Publisher</th></tr>");
			for (Publisher p : publishers) {
				str.append("<tr><td>"
						+ p.getPublisherId()
						+ "</td><td>"
						+ p.getPublisherName()
						+ "</td><td><button type='button' "
						+ "class='btn btn-md btn-success' data-toggle='modal' data-target='#myModal1' href='editPublisher.jsp?publisherId="
						+ p.getPublisherId()
						+ "'>"
						+ "Edit</button></td><td><button type='button' class='btn btn-md btn-danger' onclick='javascript:location.href="
						+ "'deletePublisher?publisherId=" + p.getPublisherId()
						+ "'>Delete</button></td></tr>");
			}
			response.getWriter().write(str.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void pageLibraries(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Library> libraries;

			if (searchString == null) {
				libraries = new AdministrativeService().readLibraries(pageNo,
						PAGE_SIZE);
			} else {
				libraries = new AdministrativeService().searchLibraries(
						searchString, pageNo, PAGE_SIZE);
			}
			request.setAttribute("libraries", libraries);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewLibrariesAdmin.jsp");
		rd.forward(request, response);
	}

	private void editPublisher(HttpServletRequest request,
			HttpServletResponse response) {

		int publisherId = Integer.parseInt(request.getParameter("publisherId"));
		String publisherName = request.getParameter("publisherName");
		String publisherAddress = request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherAddress");

		Publisher p = new Publisher();
		p.setPublisherId(publisherId);
		p.setPublisherName(publisherName);
		p.setPublisherAddress(publisherAddress);
		p.setPublisherPhone(publisherPhone);

		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.updatePublisher(p);
			request.setAttribute("result", "Author updated Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewPublishers.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deletePublisher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String publisherId = request.getParameter("publisherId");
		Publisher publisher = new Publisher();
		publisher.setPublisherId(Integer.parseInt(publisherId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewPublishers.jsp");
		try {
			new AdministrativeService().deletePublisher(publisher);

			request.setAttribute("result", "Publisher Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Publisher Delete Failed because: "
					+ e.getMessage());
		}

		rd.forward(request, response);
	}

	private void editLibrary(HttpServletRequest request,
			HttpServletResponse response) {

		int branchId = Integer.parseInt(request.getParameter("branchId"));
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("branchAddress");

		Library l = new Library();
		l.setBranchId(branchId);
		l.setBranchName(branchName);
		l.setBranchAddress(branchAddress);

		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.updateLibrary(l);
			request.setAttribute("result", "Library updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Library update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewLibrariesAdmin.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createLibrary(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("branchAddress");

		Library l = new Library();
		l.setBranchName(branchName);
		l.setBranchAddress(branchAddress);

		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createLibrary(l);
			request.setAttribute("result", "Library Added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Library add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");
		rd.forward(request, response);
	}

	private void deleteLibrary(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String branchId = request.getParameter("branchId");
		Library library = new Library();

		library.setBranchId(Integer.parseInt(branchId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewLibrariesAdmin.jsp");
		try {
			new AdministrativeService().deleteLibrary(library);

			request.setAttribute("result", "Library Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Library Delete Failed because: "
					+ e.getMessage());
		}

		rd.forward(request, response);
	}

	private void createGenre(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String genreName = request.getParameter("genre_name");

		Genre genre = new Genre();
		genre.setGenreName(genreName);

		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createGenre(genre);
			request.setAttribute("result", "Genre Added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result", "Genre add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin.jsp");
		rd.forward(request, response);
	}

	private void pageGenres(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Genre> genres;

			if (searchString == null) {
				genres = new AdministrativeService().readGenres(pageNo,
						PAGE_SIZE);
			} else {
				genres = new AdministrativeService().searchGenres(searchString,
						pageNo, PAGE_SIZE);
			}
			request.setAttribute("genres", genres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewGenres.jsp");
		rd.forward(request, response);
	}

}