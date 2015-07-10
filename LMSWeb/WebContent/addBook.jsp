<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Author"%>
<%@page import="com.gcit.lms.domain.Genre"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	List<Author> authors = adminService.readAuthors();
	List<Genre> genres = adminService.readGenres();
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
				<td><select name="genre_id" multiple="multiple">
						<%
							for (Genre g : genres) {
						%>
						<option value=<%=g.getGenreId()%>><%=g.getGenreName()%></option>
						<%
							}
						%>
				</select></td>
			</tr>


		</table>
		<input type="submit">
	</body>
</form>
