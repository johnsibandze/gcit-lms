<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Author"%>
<%@page import="com.gcit.lms.domain.Genre"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	List<Author> authors = adminService.readAuthors(0, 10);
	List<Genre> genres = adminService.readGenres();
	List<Publisher> pubs = adminService.readPublishers();
%>
<%@include file="include.html"%>
<form action="addBook" method="post">
	<body>
		<h2>Hello Admin - Enter Book Details</h2>
		<table class="table">
			<tr>
				<td>Enter Book Title:</td>
				<td><input type="text" name="title" /></td>
			</tr>
			<tr>
				<td>Select Author</td>
				<td><select name="authorId" multiple="multiple">
						<%
							for (Author a : authors) {
						%>
						<option value=<%=a.getAuthorId()%>><%=a.getAuthorName()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Select Genre</td>
				<td><select name="genreId" multiple="multiple">
						<%
							for (Genre g : genres) {
						%>
						<option value=<%=g.getGenre_id()%>><%=g.getGenre_name()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Select Publisher</td>
				<td><select name="pubId">
						<%
							for (Publisher p : pubs) {
						%>
						<option value=<%=p.getPublisherId()%>><%=p.getPublisherName()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
		</table>
		<input type="submit">
	</body>
</form>
