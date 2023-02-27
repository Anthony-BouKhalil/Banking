<%@ include file="authCheck.jsp"%>
<%
    if (!r.equals("executive")) {
        response.sendRedirect("./executivehome");
    }
%>
<%@ page import="com.bank.beans.Customer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; chaset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Status</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <%@ include file="header.jsp"%>
    <%
        ArrayList<Customer> customers = (ArrayList<Customer>)request.getAttribute("customerList");
        
        if (customers.isEmpty()) { %>
            <b>No Active Customer Selected</b>        
    <%} else {
    %>
    <h1>Customer Status</h1>
    <table>
        <tr>
            <th>Customer ID</th>
            <th>Name</th>
            <th>SSN</th>
            <th>Status</th>
            <th>Last Updated</th>
            <th>Actions</th>
        </tr>
        <%
            for (Customer c:customers) {
        %>
        <tr>
            <td><%=c.getCustomerId()%></td>
            <td><%=c.getFirstName() + " " + c.getLastName()%> </td>
            <td><%=c.getSSNId()%> </td>
            <td>
                <%
                if (c.getStatus().equalsIgnoreCase("Active")) {%>
                    <%=c.getStatus()%>
                <%} else if (c.getStatus().equalsIgnoreCase("Inactive")) {%>
                    <b style="color:red"><%=c.getStatus()%></b>
                <%}%>
            </td>
            <td><%=c.getLastUpdate()%></td>
            <%  if (c.getStatus().equalsIgnoreCase("Active")) {%> 
            <td><a href="/Banking/EditCustomer?id=<%=c.getCustomerId()%>">&#9998</a>&nbsp;&nbsp;
            <a href="/Banking/DeleteCustomer?id=<%=c.getCustomerId()%>">&#10060</a>
            </td>
            <%} %>
            </tr>
            <%
                }
            %>
        </table><br>
        <div class="text-center">
            <a href="CustomerStatus"><input type="submit" value="Refresh"></a>
        </div>
    <%}%>
    <%@ include file="footer.jsp"%>
</body>
</html>