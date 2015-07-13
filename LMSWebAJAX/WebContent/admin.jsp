<%@include file="include.html"%>
<h2>Hello Admin - Welcome to GCIT Library Management System</h2>
${result}

<!-- <h3>Authors</h3>
<a href="addAuthor.jsp">Add Author</a>
<br />
<a href="viewAuthors.jsp">View Authors</a>
<br />

<h3>Publishers</h3>
<a href="addPublisher.jsp">Add Publisher</a>
<br />
<a href="viewPublishers.jsp">View Publisher</a>
<br />

<h3>Books</h3>
<a href="addBook.jsp">Add Book</a>
<br />
<a href="viewBooksAdmin.jsp">View Books</a>
<br />

<h3>Libraries</h3>
<a href="addLibrary.jsp">Add Library</a>
<br />
<a href="viewLibrariesAdmin.jsp">View Libraries</a>
<br />

<h3>Genres</h3>
<a href="addGenre.jsp">Add Genre</a>
<br />
<a href="viewGenres.jsp">View Genres</a>
<br /> -->



<!-- <table class="table" id="librariesTable"> -->
<table class="table table-hover table-bordered">
	<tr>
		<th>Add</th>
		<th>View</th>
		<!-- <th>Choose Library</th> -->
	</tr>
	<tr>
		<td><a href="addAuthor.jsp">Add Author</a></td>
		<td><a href="viewAuthors.jsp">View Authors</a></td>

		<%-- <td><button type="button" class="btn btn-md btn-success"
				data-toggle="modal" data-target="#myModal1"
				onclick="javascript:location.href='chooseLibrary?branchId=<%=l.getBranchId()%>';">Choose
				Library</button></td> --%>
	</tr>

	<tr>
		<td><a href="addPublisher.jsp">Add Publisher</a></td>
		<td><a href="viewPublishers.jsp">View Publisher</a></td>
	</tr>

	<tr>
		<td><a href="addBook.jsp">Add Book</a></td>
		<td><a href="viewBooksAdmin.jsp">View Books</a></td>
	</tr>

	<tr>
		<td><a href="addLibrary.jsp">Add Library</a></td>
		<td><a href="viewLibrariesAdmin.jsp">View Libraries</a></td>
	</tr>

	<tr>
		<td><a href="addGenre.jsp">Add Genre</a></td>
		<td><a href="viewGenres.jsp">View Genres</a></td>
	</tr>

	<tr>
		<td><a href="addBorrower.jsp">Add Borrower</a></td>
		<td><a href="viewBorrowers.jsp">View Borrowers</a></td>
	</tr>
















</table>