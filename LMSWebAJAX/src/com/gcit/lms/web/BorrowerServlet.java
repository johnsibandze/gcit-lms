package com.gcit.lms.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Library;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/validateCardNo", "/chooseLibraryBorrower",
		"/pageLibrariesBorrower", "/pageLibraryBooksBorrower" })
public class BorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final int PAGE_SIZE = 3;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BorrowerServlet() {
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
		case "/validateCardNo":
			validateCardNo(request, response);
			break;
		case "/chooseLibraryBorrower":
			chooseLibrary(request, response);
			break;
		case "/pageLibrariesBorrower":
			pageLibraries(request, response);
			break;
		case "/pageLibraryBooksBorrower":
			pageLibraryBooks(request, response);
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
		// String reqUrl = request.getRequestURI().substring(
		// request.getContextPath().length(),
		// request.getRequestURI().length());
		// switch (reqUrl) {
		// case "/validateCardNo":
		// validateCardNo(request, response);
		// break;
		// default:
		// break;
		// }
	}

	private void validateCardNo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int cardNo = Integer.parseInt(request.getParameter("cardNo"));

		request.setAttribute("cardNo", cardNo);

		BorrowerService borrowerService = new BorrowerService();

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/validateCardNo.jsp");
		try {
			Borrower b = borrowerService.readBorrower(cardNo);

			if (b != null) {

				// request.setAttribute("result",
				// "Card Validated Successfully");
				rd = getServletContext().getRequestDispatcher(
						"/borrowerChoice.jsp");
			} else {
				request.setAttribute("result",
						"Please enter a valid Card Number");
				rd = getServletContext().getRequestDispatcher(
						"/validateCardNo.jsp");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
		rd.forward(request, response);

	}

	private void chooseLibrary(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String branchId = request.getParameter("branchId");

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewLibraryBooksBorrower.jsp");

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

	public static Library library;
	private String searchString;

	private void pageLibraries(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Library> libraries;

			if (searchString == null) {
				libraries = new BorrowerService().readLibraries(pageNo,
						AdminServlet.PAGE_SIZE);
			} else {
				libraries = new BorrowerService().searchLibraries(searchString,
						pageNo, AdminServlet.PAGE_SIZE);
			}
			request.setAttribute("libraries", libraries);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewLibrariesBorrower.jsp");
		rd.forward(request, response);
	}

	private void pageLibraryBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Book> books;

			if (searchString == null) {
				books = library.getBooks();
			} else {
				books = new BorrowerService().searchBooks(searchString, pageNo,
						AdminServlet.PAGE_SIZE);
			}
			request.setAttribute("books", books);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewLibraryBooksBorrower.jsp");
		rd.forward(request, response);
	}

}