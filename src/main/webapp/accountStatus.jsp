<%@ include file="authCheck.jsp"%>
<%
    if (r != null && !r.equals("teller")) {
        response.sendRedirect("./executivehome");
    }
%>
<%@ page import="com.bank.beans.CustomerAccount" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; chaset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account Status</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <%@ include file="header.jsp"%>
    <%
        ArrayList<CustomerAccount> customerAccounts = (ArrayList<CustomerAccount>)request.getAttribute("customerAccounts");
    	
        if (customerAccounts == null || customerAccounts.size() == 0) {
    %>
        <b>No Accounts in Database</b>        
    <%
    } else {
    %>
        <h1>Account Status</h1>
        <table>
            <tr>
                <th>Customer ID</th>
                <th>Account ID</th>
                <th>Account Type</th>
                <th>Account Status</th>
                <th>Balance</th>
                <th>Last Transaction</th>
            </tr>
            <%
                for (CustomerAccount ca:customerAccounts) {
            %>
            <tr>
                <td><%=ca.getCustomerId()%></td>
                <td><%=ca.getAccountId()%></td>
                <td><%=ca.getAccountType()%></td>
                <td>
                    <%
                    if (ca.getAccountStatus().equalsIgnoreCase("Open")) {%>
                        <%=ca.getAccountStatus()%>
                    <%} else if (ca.getAccountStatus().equalsIgnoreCase("Closed")) {%>
                        <b style="color:red"><%=ca.getAccountStatus()%></b>
                    <%}%>
                </td>
                <td>$<%=ca.getBalance()%></td>
                <td>$<%=ca.getLastTransaction()%></td>
            </tr>
            <%
                }
            %>
        </table><br>
        <div class="text-center">
            <a href="./AccountStatus"><input type="submit" value="Refresh"></a>
        </div>
    <%}%>
    <%@ include file="footer.jsp"%>
</body>
</html>