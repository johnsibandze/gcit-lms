<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	String publisherId = request.getParameter("publisherId");
	Publisher publisher = adminService.readPublisher(Integer
			.parseInt(publisherId));
%>
<div class="modal-body">
	<form action="editPublisher" method="post">
		Enter Publisher Name: <input type="text" name="publisherName"
			value=<%=publisher.getPublisherName()%>> <input type="hidden"
			name="publisherId" value=<%=publisher.getPublisherId()%>>
		Enter Publisher Address: <input type="text" name="publisherAddress"
			value=<%=publisher.getPublisherAddress()%>> <input
			type="hidden" name="publisherId"
			value=<%=publisher.getPublisherId()%>> Enter Publisher Phone:
		<input type="text" name="publisherPhone"
			value=<%=publisher.getPublisherPhone()%>> <input
			type="hidden" name="publisherId"
			value=<%=publisher.getPublisherId()%>> <input type="submit" />
	</form>
</div>