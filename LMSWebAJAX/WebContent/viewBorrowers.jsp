<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Borrower"%>
<%@page import="com.gcit.lms.web.AdminServlet"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	List<Borrower> borrowers = null;
	if (request.getAttribute("borrowers") != null) {
		borrowers = (List<Borrower>) request.getAttribute("borrowers");
	} else {
		borrowers = adminService.readBorrowers(0, AdminServlet.PAGE_SIZE);
	}
%>
<%@include file="include.html"%>
<script>
	function searchBorrowers() {
		$.ajax({
			url : "searchBorrowers",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#borrowersTable').html(data);
		});
	}
</script>
${result }
<form action="searchBorrowers" method="post">
	<input type="text" class="col-md-8" id="searchString"
		name="searchString" placeholder="Enter title name to search"><input
		type="button" value="Search!" onclick="javascript:searchBorrowers();">
</form>

<nav>
	<ul class="pagination">
		<li><a href="pageBorrowers?pageNo=1">1</a></li>
		<li><a href="pageBorrowers?pageNo=2">2</a></li>
		<li><a href="pageBorrowers?pageNo=3">3</a></li>
		<li><a href="pageBorrowers?pageNo=4">4</a></li>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>

<table class="table table-hover table-bordered" id="borrowersTable">
	<tr>
		<th>Card Number</th>
		<th>Borrower Name</th>
		<th>Edit Borrower</th>
		<th>Delete Borrower</th>
	</tr>
	<%
		for (Borrower b : borrowers) {
	%>
	<tr>
		<td>
			<%
				out.println(b.getCardNo());
			%>
		</td>
		<td>
			<%
				out.println(b.getName());
			%>
		</td>
		<td><button type="button" class="btn btn-md btn-success"
				data-toggle="modal" data-target="#myModal1"
				href="editBorrower.jsp?cardNo=<%=b.getCardNo()%>">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger"
				onclick="javascript:location.href='deleteBorrower?cardNo=<%=b.getCardNo()%>';">Delete</button></td>
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