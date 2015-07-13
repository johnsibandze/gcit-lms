<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Genre"%>
<%@page import="com.gcit.lms.web.AdminServlet"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	List<Genre> genres = null;
	if (request.getAttribute("genres") != null) {
		genres = (List<Genre>) request.getAttribute("genres");
	} else {
		genres = adminService.readGenres(0, AdminServlet.PAGE_SIZE);
	}
%>
<%@include file="include.html"%>
<script>
	function searchGenres() {
		$.ajax({
			url : "searchGenres",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#genresTable').html(data);
		});
	}
</script>
${result }
<form action="searchGenres" method="post">
	<input type="text" class="col-md-8" id="searchString"
		name="searchString" placeholder="Enter title name to search"><input
		type="button" value="Search!" onclick="javascript:searchGenres();">
</form>

<nav>
	<ul class="pagination">
		<li><a href="pageGenres?pageNo=1">1</a></li>
		<li><a href="pageGenres?pageNo=2">2</a></li>
		<li><a href="pageGenres?pageNo=3">3</a></li>
		<li><a href="pageGenres?pageNo=4">4</a></li>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>

<table class="table table-hover table-bordered" id="genresTable">
	<tr>
		<th>Genre ID</th>
		<th>Genre Name</th>
		<th>Edit Genre</th>
		<th>Delete Genre</th>
	</tr>
	<%
		for (Genre g : genres) {
	%>
	<tr>
		<td>
			<%
				out.println(g.getGenreId());
			%>
		</td>
		<td>
			<%
				out.println(g.getGenreName());
			%>
		</td>
		<td><button type="button" class="btn btn-md btn-success"
				data-toggle="modal" data-target="#myModal1"
				href="editGenre.jsp?genre_id=<%=g.getGenreId()%>">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger"
				onclick="javascript:location.href='deleteGenre?genre_id=<%=g.getGenreId()%>';">Delete</button></td>
	</tr>
	<%
		}
	%>
</table>

<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>