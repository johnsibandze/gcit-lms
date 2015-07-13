<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Library"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%@page import="com.gcit.lms.dao.BookDAO"%>
<%@page import="com.gcit.lms.web.BorrowerServlet"%>
<%
	List<Book> books = BorrowerServlet.library.getBooks();
%>
<%@include file="include.html"%>
<script>
	function searchLibraryBooksBorrower() {
		$.ajax({
			url : "searchLibraryBooksBorrower",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#librariesTable').html(data);
		});
	}
</script>
${result }
<form action="searchLibraryBooks" method="post">
	<input type="text" class="col-md-8" id="searchString"
		name="searchString" placeholder="Enter branch name to search"><input
		type="button" value="Search!"
		onclick="javascript:searchLibraryBooks();">
</form>

<nav>
	<ul class="pagination">
		<li><a href="pageLibraryBooksBorrower?pageNo=1">1</a></li>
		<li><a href="pageLibraryBooksBorrower?pageNo=2">2</a></li>
		<li><a href="pageLibraryBooksBorrower?pageNo=3">3</a></li>
		<li><a href="pageLibraryBooksBorrower?pageNo=4">4</a></li>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>

<table class="table" id="libraryBooksTable">
	<tr>
		<th>Book ID</th>
		<th>Book Title</th>
		<!-- <th>Choose Library</th> -->
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
				onclick="javascript:location.href='checkOutBook?bookId=<%=b.getBookId()%>';">Check
				Out Book</button></td>
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