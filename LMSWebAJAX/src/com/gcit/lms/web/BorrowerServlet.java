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
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Library;
import com.gcit.lms.domain.Publisher;
import com.gcit.lms.service.AdministrativeService;
import com.gcit.lms.service.BorrowerService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/validateCardNo" })
public class BorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final int PAGE_SIZE = 3;

	private String searchString;

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

		BorrowerService borrowerService = new BorrowerService();

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/validateCardNo.jsp");
		try {
			Borrower b = borrowerService.readBorrower(cardNo);

			if (b != null) {

				request.setAttribute("result", "Card Validated Successfully");
				rd = getServletContext().getRequestDispatcher("/admin.jsp");
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

}