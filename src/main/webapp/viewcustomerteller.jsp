<%@ include file="authCheck.jsp"%>
<%
    if (r!= null && !r.equals("teller")) {
        response.sendRedirect("./executivehome");
    }
%>
<%@ page import="com.bank.beans.Customer" %>
<%@ page import="com.bank.beans.Account" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; chaset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Executive - Delete Customer</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <%@ include file="header.jsp"%>
    <div>
    <%     
    
    Customer customer = (Customer)request.getAttribute("customer");
    if (customer == null) {%>
        <b>No Active Customer Selected</b>
    <%
    } else {
    %>
        <h1>Customer</h1>
        <div class="customer-data">
            <div class="left-half">
                <b>Customer ID: </b><%=customer.getCustomerId()%><br></br>
                <b>First Name: </b><%=customer.getFirstName()%><br></br>
                <b>Last Name: </b><%=customer.getLastName()%><br><br>
                <b>SSN: </b><%=customer.getSSNId()%><br></br>
                <b>DOB: </b><%=customer.getDateOfBirth()%><br></br>
                <b>Address Line 1: </b><%=customer.getAddress1()%><br></br>
                <b>Address Line 2: </b><%=customer.getAddress2()%><br></br>
                <b>City: </b><%=customer.getCity()%><br></br>
                <b>State: </b><%=customer.getState()%><br></br>
                <b>Zip Code: </b><%=customer.getZipCode()%><br>
            </div>

            <% 
                ArrayList<Account> accounts = (ArrayList<Account>)request.getAttribute("accountList");
                boolean everyAccountClosed = false;
                
                for (Account a:accounts) {
                    if (a.getStatus().equalsIgnoreCase("Open")) {
                    	everyAccountClosed = true;
                    }
                }
                everyAccountClosed = false;
           
            %>
            <div class="right-half">
                <h1>Accounts</h1>
                <table align="center">
                    <tr>
                        <th>Account ID</th>
                        <th>Account Type</th>
                        <th>Account Status</th>
                        <th>Balance</th>
                        <th>Last Transaction</th>
                        <th>Action</th>
                    </tr>

                    <%
                        for (Account a:accounts) {
                    %>
                    <tr>
                      <td><%=a.getAccountId()%></td>
                      <td><%=a.getAccountType()%></td>
                      <td>
                        <%
                        if (a.getStatus().equalsIgnoreCase("Open")) {%>
                            <%=a.getStatus() %>
                        <%} else if (a.getStatus().equalsIgnoreCase("Closed")) {%>
                            <b style="color:red"><%=a.getStatus() %></b>
                        <%}%>
                      </td>
                      <td>$<%=a.getBalance() %></td>
                      <td><%=a.getLastUpdate() %></td>
                      <td>
                        <%
                            if (a.getStatus().equalsIgnoreCase("Open")) {%>
                                <a href="/Banking/transaction?aid=<%=a.getAccountId()%>&cid=<%=customer.getCustomerId()%>">$</a>&nbsp;&nbsp;
                                <a href="/Banking/accountStatement.jsp?aid=<%=a.getAccountId()%>">&#x2630;</a>
                            <% } %>
                      </td>
                    </tr>
                    <% 
                        }
                    %>
                </table>
                <div align="center">
                	<a href="./executivehome"><button type="button">Cancel</button></a>
                </div>
            </div>
        </div>
        <%}%>
        </div>
        <%@ include file="footer.jsp"%>

        <script src="script.js"></script>
</body>
</html>
                