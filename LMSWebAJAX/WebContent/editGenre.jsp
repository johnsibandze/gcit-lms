<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Genre"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	String genreId = request.getParameter("genre_id");
	Genre genre = adminService.readGenre(Integer.parseInt(genreId));
%>
<div class="modal-body">
	<form action="editGenre" method="post">
		Enter Genre Name: <input type="text" name="genre_name"
			value=<%=genre.getGenreName()%>> <input type="hidden"
			name="genre_id" value=<%=genre.getGenreId()%>> <input
			type="submit" />
	</form>
</div>