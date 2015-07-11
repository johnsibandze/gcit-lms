<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	String bookId = request.getParameter("bookId");
	Book book = adminService.readBook(Integer.parseInt(bookId));
%>
<div class="modal-body">
	<form action="editBook" method="post">
		Enter Book Title: <input type="text" name="title"
			value=<%=book.getTitle()%>> <input type="hidden"
			name="bookId" value=<%=book.getBookId()%>> <input
			type="submit" />
	</form>
</div>