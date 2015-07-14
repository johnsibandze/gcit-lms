package com.gcit.lms.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Library;
import com.gcit.lms.service.AdministrativeService;
import com.gcit.lms.service.LibrarianService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/viewLibraries", "/editLibrary", "/searchLibrary",
		"/pageLibraries", "/chooseLibrary", "/pageBooksLibrarian",
		"/addBookCopies" })
public class LibrarianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LibrarianServlet() {
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

		case "/pageLibraries":
			pageLibraries(request, response);
			break;
		case "/searchLibraries":
			searchAuthors(request, response);
			break;
		case "/chooseLibrary":
			chooseLibrary(request, response);
			break;
		case "/pageBooksLibrarian":
			pageBooks(request, response);
			break;
		// case "/pageBooks":
		// pageBooks(request, response);
		// break;
		// case "/searchBooks":
		// searchBooks(request, response);
		// break;
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
		case "/viewLibraries":
			viewLibraries(request, response);
			break;
		case "/editLibrary":
			editAuthor(request, response);
			break;
		case "/searchLibraries":
			searchAuthors(request, response);
			break;
		case "/addBookCopies":
			addBookCopies(request, response);
			break;
		// case "/viewBooks":
		// viewBooks(request, response);
		// break;
		// case "/editBook": {
		// editBook(request, response);
		// break;
		// }
		default:
			break;
		}
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

	private List<Library> viewLibraries(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			return new LibrarianService().readLibraries(0,
					AdminServlet.PAGE_SIZE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void pageLibraries(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Library> libraries;

			if (searchString == null) {
				libraries = new LibrarianService().readLibraries(pageNo,
						AdminServlet.PAGE_SIZE);
			} else {
				libraries = new LibrarianService().searchLibraries(
						searchString, pageNo, AdminServlet.PAGE_SIZE);
			}
			request.setAttribute("libraries", libraries);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewLibraries.jsp");
		rd.forward(request, response);
	}

	private void searchAuthors(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");

		this.searchString = searchString;

		try {
			// by default, show the first page of the search results
			List<Author> authors = new AdministrativeService().searchAuthors(
					searchString, 0, AdminServlet.PAGE_SIZE);
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
				"/viewBooks.jsp");
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

	private void chooseLibrary(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String branchId = request.getParameter("branchId");

		// System.out.println("the branch id: " + branchId);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/librarianChoice.jsp");

		try {
			library = new LibrarianService().readLibrary(Integer
					.parseInt(branchId));

			request.setAttribute("result", "Successfully Chosen Library");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Library Choosing Failed Because: "
					+ e.getMessage());
		}

		rd.forward(request, response);
	}

	private void pageBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Book> books;

			if (searchString == null) {
				books = new AdministrativeService().readBooks(pageNo,
						AdminServlet.PAGE_SIZE);
			} else {
				books = new AdministrativeService().searchBooks(searchString,
						pageNo, AdminServlet.PAGE_SIZE);
			}
			request.setAttribute("books", books);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBooksLibrarian.jsp");
		rd.forward(request, response);
	}

	private String searchString;
	public static Library library;

	private void addBookCopies(HttpServletRequest request,
			HttpServletResponse response) {
		int noOfCopies = Integer.parseInt(request.getParameter("noOfCopies"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int branchId = Integer.parseInt(request.getParameter("branchId"));

		int oldNoOfCopies = Integer.parseInt(request
				.getParameter("oldNoOfCopies"));

		// System.out.println("old noOfCopies: " + oldNoOfCopies);
		// System.out.println("new noOfCopies: " + noOfCopies);

		LibrarianService librarianService = new LibrarianService();

		try {
			BookCopies bc = librarianService.readBookCopies(bookId, branchId);

			if (bc == null) {// the current branch has no copy of this book yet
				bc = new BookCopies(bookId, branchId, noOfCopies);
				librarianService.createBookCopies(bc);
			} else {// the current branch already has a copy of the book
				bc.setNoOfCopies(oldNoOfCopies + noOfCopies);
				librarianService.updateBookCopies(bc);
			}
			request.setAttribute("result", "Book Copies updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Book Copies update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBooksLibrarian.jsp");
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

}