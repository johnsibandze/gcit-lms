<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Library"%>
<%@page import="com.gcit.lms.web.LibrarianServlet"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	String branchId = request.getParameter("branchId");

	/* Library library = adminService.readLibrary(Integer
			.parseInt(branchId)); */

	Library library = LibrarianServlet.library;
%>
<div class="modal-body">
	<form action="editLibraryAdmin" method="post">
		Enter Library Name: <input type="text" name="branchName"
			value=<%=library.getBranchName()%>> <input type="hidden"
			name="branchId" value=<%=library.getBranchId()%>> Enter
		Library Address: <input type="text" name="branchAddress"
			value=<%=library.getBranchAddress()%>> <input type="hidden"
			name="branchId" value=<%=library.getBranchId()%>> <input
			type="submit" />
	</form>
</div>