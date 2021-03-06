<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%@page import="com.gcit.lms.web.AdminServlet"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	List<Book> books = null;
	if (request.getAttribute("books") != null) {
		books = (List<Book>) request.getAttribute("books");
		System.out.println("i am here in if");
	} else {
		books = adminService.readBooks(0, AdminServlet.PAGE_SIZE);
		System.out.println("i am here in else");
	}
%>
<%@include file="include.html"%>
<script>
	function searchBooksAdmin() {
		$.ajax({
			url : "searchBooksAdmin",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#booksTable').html(data);
		});
	}
</script>
${result }
<form action="searchBooksAdmin" method="post">
	<input type="text" class="col-md-8" id="searchString"
		name="searchString" placeholder="Enter book title to search"><input
		type="button" value="Search!" onclick="javascript:searchBooksAdmin();">
</form>

<nav>
	<ul class="pagination">
		<li><a href="pageBooks?pageNo=1">1</a></li>
		<li><a href="pageBooks?pageNo=2">2</a></li>
		<li><a href="pageBooks?pageNo=3">3</a></li>
		<li><a href="pageBooks?pageNo=4">4</a></li>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>

<table class="table table-hover table-bordered" id="authorsTable">
	<tr>
		<th>Book ID</th>
		<th>Book Title</th>
		<th>Edit Book</th>
		<th>Delete Book</th>
	</tr>
	<%
		for (Book b : books) {
	%>
	<tr>
		<td>
			<%
				out.println(b.getBookId());
			%>
		</td>
		<td>
			<%
				out.println(b.getTitle());
			%>
		</td>
		<td><button type="button" class="btn btn-md btn-success"
				data-toggle="modal" data-target="#myModal1"
				href="editBook.jsp?bookId=<%=b.getBookId()%>">Edit</button></td>
		<td><button type="button" class="btn btn-md btn-danger"
				onclick="javascript:location.href='deleteBook?bookId=<%=b.getBookId()%>';">Delete</button></td>
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