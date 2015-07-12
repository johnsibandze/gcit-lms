<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%@page import="com.gcit.lms.domain.Library"%>
<%@page import="com.gcit.lms.domain.BookCopies"%>
<%@page import="com.gcit.lms.web.LibrarianServlet"%>
<%
	LibrarianService librarianService = new LibrarianService();
	String bookId = request.getParameter("bookId");
	Book book = librarianService.readBook(Integer.parseInt(bookId));
	Library library = LibrarianServlet.library;

	BookCopies bc = librarianService.readBookCopies(book.getBookId(),
			library.getBranchId());
%>
<div class="modal-body">
	<form action="addBookCopies" method="post">
		Enter Number of Copies You Want to Add: <input type="text"
			name="noOfCopies" value=<%=book.getTitle()%>> <input
			type="hidden" name="bookId" value=<%=book.getBookId()%>> <input
			type="hidden" name="branchId" value=<%=library.getBranchId()%>>
		<input type="hidden" name="oldNoOfCopies"
			value=<%=bc.getNoOfCopies()%>> <input type="submit" />
	</form>
</div>