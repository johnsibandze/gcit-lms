<%@include file="include.html"%>
<form action="addLibrary" method="post">
	<body>
		<h2>Hello Admin - Enter Library Details</h2>
		<table class="table">
			<tr>
				<td>Enter Library Name:</td>
				<td><input type="text" name="branchName" /></td>
			</tr>
			<tr>
				<td>Enter Library Address:</td>
				<td><input type="text" name="branchAddress" /></td>
			</tr>
		</table>
		<input type="submit">
	</body>
</form>
