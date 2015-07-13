<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Borrower"%>
<%
	AdministrativeService adminService = new AdministrativeService();
	String cardNo = request.getParameter("cardNo");
	Borrower borrower = adminService.readBorrower(Integer
			.parseInt(cardNo));
%>
<div class="modal-body">
	<form action="editBorrower" method="post">
		Enter Borrower Name: <input type="text" name="name"
			value=<%=borrower.getName()%>> <input type="hidden"
			name="cardNo" value=<%=borrower.getCardNo()%>> Enter Borrower
		Address: <input type="text" name="address"
			value=<%=borrower.getAddress()%>> <input type="hidden"
			name="cardNo" value=<%=borrower.getCardNo()%>> Enter Borrower
		Phone: <input type="text" name="phone"
			value=<%=borrower.getPhone()%>> <input type="hidden"
			name="cardNo" value=<%=borrower.getCardNo()%>> <input
			type="submit" />
	</form>
</div>