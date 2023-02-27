<%@ include file="authCheck.jsp"%>
<%@ page import="com.bank.beans.Customer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; chaset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Active Customers</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <%@ include file="header.jsp"%>
    <%
        ArrayList<Customer> customers = (ArrayList<Customer>)request.getAttribute("allcustomer");
        
        if (customers == null || customers.size() == 0) {
    %>
        <h1>No Active Customers in Database</h1>        
    <%
    } else {
    %>
        <h1>Active Customers</h1>
        <table>
            <tr>
                <th>Customer ID</th>
                <th>Name</th>
                <th>SSN</th>
                <% if ("executive".equalsIgnoreCase(r)) { %>
                <th>Last Updated</th>
                <%}%>
                <th>Actions</th>
            </tr>
            <%
                for (Customer c:customers) {
            %>
            <tr>
                <td><%=c.getCustomerId()%></td>
                <td><%=c.getFirstName() + " " + c.getLastName()%></td>
                <td><%=c.getSSNId()%></td>
                <% if ("executive".equalsIgnoreCase(r)) { %>
                <td><%=c.getLastUpdate()%></td>
                <%}%>
                <% if ("executive".equalsIgnoreCase(r)) { %>
                <td>
                    <a href="/Banking/EditCustomer?id=<%=c.getCustomerId()%>">&#9998;</a>
                    <a href="/Banking/DeleteCustomer?id=<%=c.getCustomerId()%>">&#10060;</a>
                </td>
                <%} else if ("teller".equalsIgnoreCase(r)) { %>
                <td>
                    <a href="./viewcustomerteller?id=<%=c.getCustomerId()%>">&#36;</a>&nbsp;&nbsp;
                </td>
                <%}%>
            </tr>
            <% 
                }
            %>
        </table><br>
    <%}%>
    <div class="text-center">
        <a href="./executivehome"><input type="submit" value="Refresh"></a>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>