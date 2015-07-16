<%@include file="include.html"%>
<h2>Hello Valuable Borrower - Welcome to GCIT Library Management
	System</h2>
${result}

<!-- <h3>Choose a branch to manage</h3> -->

<!-- <a href="editLibrary.jsp">Edit the branch Details</a>
<a href="viewLibraryBooks.jsp">Add Book Copies to This Branch</a> -->

<%
	String cardNo = "" + request.getAttribute("cardNo");
	System.out.println("cardNo: " + Integer.parseInt(cardNo));
%>

<%-- onclick="javascript:location.href='chooseLibraryBorrower?branchId=<%=l.getBranchId()%>';">Choose --%>

<h3></h3>
<a href="viewLibrariesBorrower.jsp?cardNo="<%=request.getAttribute("cardNo") %>>Check out a Book</a>
<br />
<a href="viewLibraryBooks.jsp">Return a Book</a>
<br />