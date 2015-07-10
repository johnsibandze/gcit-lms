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
import com.gcit.lms.domain.Publisher;
import com.gcit.lms.service.AdministrativeService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/addPublisher", "/viewAuthors", "/deleteAuthor",
		"/editAuthor", "/addBook", "/searchAuthors", "/pageAuthors",
		"/viewBooks", "/deleteBook" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			addBook(request, response);
			break;
		case "/searchAuthors":
			searchAuthors(request, response);
			break;
		case "/viewBooks":
			viewBooks(request, response);
			break;
		default:
			break;
		}
	}

	// private void addBook(HttpServletRequest request,
	// HttpServletResponse response) {
	// String bookTitle = request.getParameter("bookTitle");
	// int authorId = Integer.parseInt(request.getParameter("authorId"));
	// int pubId = Integer.parseInt(request.getParameter("pubId"));
	// int genreId = Integer.parseInt(request.getParameter("genreId"));
	//
	// AdministrativeService adminService = new AdministrativeService();
	// List<Author> authors = new ArrayList<Author>();
	// List<Genre> genres = new ArrayList<Genre>();
	// Book book = new Book();
	// book.setTitle(bookTitle);
	// try {
	// authors.add(adminService.readAuthor(authorId));
	//
	// book.setAuthors(authors);
	// genres.add(adminService.readGenre(genreId));
	// book.setGenres(genres);
	// adminService.createBook(book);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	private void addBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		// int authorId = Integer.parseInt(request.getParameter("authorId"));
		int pubId = Integer.parseInt(request.getParameter("pubId"));
		// int genreId = Integer.parseInt(request.getParameter("genreId"));

		AdministrativeService adminService = new AdministrativeService();
		List<Author> authors = new ArrayList<Author>();
		List<Genre> genres = new ArrayList<Genre>();
		Book book = new Book();
		book.setTitle(title);
		try {
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

			// authors.add(adminService.readAuthor(authorId));

			book.setAuthors(authors);
			// genres.add(adminService.readGenre(genreId));
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
				"/viewBooks.jsp");
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
			return new AdministrativeService().readAuthors(0, 10);
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
			List<Author> authors = new AdministrativeService().readAuthors(
					pageNo, 10);
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
		try {
			List<Author> authors = new AdministrativeService()
					.searchAuthors(searchString);
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
			return new AdministrativeService().readBooks(0, 10);
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
				"/viewBooks.jsp");
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
}
