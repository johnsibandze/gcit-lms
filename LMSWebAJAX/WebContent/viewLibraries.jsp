<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Library"%>
<%@page import="com.gcit.lms.web.AdminServlet"%>
<%
LibrarianService librarianService = new LibrarianService();
	List<Library> libraries = null;
	if (request.getAttribute("libraries") != null) {
		libraries = (List<Library>) request.getAttribute("libraries");
	} else {
		libraries = librarianService.readLibraries(0, AdminServlet.PAGE_SIZE);
	}
%>
<%@include file="include.html"%>
<script>
	function searchLibraries() {
		$.ajax({
			url : "searchLibraries",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#librariesTable').html(data);
		});
	}
</script>
${result }
<form action="searchLibraries" method="post">
	<input type="text" class="col-md-8" id="searchString"
		name="searchString" placeholder="Enter branch name to search"><input
		type="button" value="Search!" onclick="javascript:searchLibraries();">
</form>

<nav>
	<ul class="pagination">
		<li><a href="pageLibraries?pageNo=1">1</a></li>
		<li><a href="pageLibraries?pageNo=2">2</a></li>
		<li><a href="pageLibraries?pageNo=3">3</a></li>
		<li><a href="pageLibraries?pageNo=4">4</a></li>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>

<table class="table" id="librariesTable">
	<tr>
		<th>Library ID</th>
		<th>Library Name</th>
		<th>Edit Library</th>
		<th>Delete Library</th>
	</tr>
	<%
		for (Library l : libraries) {
	%>
	<tr>
		<td>
			<%
				out.println(l.getBranchId());
			%>
		</td>
		<td>
			<%
				out.println(l.getBranchName());
			%>
		</td>
		<td><button type="button" class="btn btn-md btn-success"
				data-toggle="modal" data-target="#myModal1"
				href="editLibrary.jsp?branchId=<%=l.getBranchId()%>">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger"
				onclick="javascript:location.href='deleteLibrary?branchId=<%=l.getBranchId()%>';">Delete</button></td>
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