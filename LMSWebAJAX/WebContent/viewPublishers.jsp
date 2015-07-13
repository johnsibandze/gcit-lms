<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%@page import="com.gcit.lms.web.AdminServlet"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	List<Publisher> publishers = null;
	if (request.getAttribute("publishers") != null) {
		publishers = (List<Publisher>) request.getAttribute("publishers");
	} else {
		publishers = adminService.readPublishers(0, AdminServlet.PAGE_SIZE);
	}
%>
<%@include file="include.html"%>
<script>
	function searchPublishers() {
		$.ajax({
			url : "searchPublishers",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#publishersTable').html(data);
		});
	}
</script>
${result }
<form action="searchPublishers" method="post">
	<input type="text" class="col-md-8" id="searchString"
		name="searchString" placeholder="Enter Branch Name to Search"><input
		type="button" value="Search!" onclick="javascript:searchPublishers();">
</form>

<nav>
	<ul class="pagination">
		<li><a href="pagePublishers?pageNo=1">1</a></li>
		<li><a href="pagePublishers?pageNo=2">2</a></li>
		<li><a href="pagePublishers?pageNo=3">3</a></li>
		<li><a href="pagePublishers?pageNo=4">4</a></li>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>

<table class="table table-hover table-bordered" id="publishersTable">
	<tr>
		<th>Publisher ID</th>
		<th>Publisher Name</th>
		<th>Edit Publisher</th>
		<th>Delete Publisher</th>
	</tr>
	<%
		for (Publisher p : publishers) {
	%>
	<tr>
		<td>
			<%
				out.println(p.getPublisherId());
			%>
		</td>
		<td>
			<%
				out.println(p.getPublisherName());
			%>
		</td>
		<td><button type="button" class="btn btn-md btn-success"
				data-toggle="modal" data-target="#myModal1"
				href="editPublisher.jsp?publisherId=<%=p.getPublisherId()%>">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger"
				onclick="javascript:location.href='deletePublisher?publisherId=<%=p.getPublisherId()%>';">Delete</button></td>
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