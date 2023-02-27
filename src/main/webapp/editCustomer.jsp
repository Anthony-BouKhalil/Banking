<%@ include file="authCheck.jsp"%>
<%
    if (r != null && !r.equals("executive")) {
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
<title>View/Edit Customer</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <%@ include file="header.jsp"%>
    <%
    Customer customer = (Customer)request.getAttribute("customer");
    if (customer == null) {
    %>
        <b>No Active Customer Selected</b>
    <%
    } else {
        ArrayList<Account> accounts = (ArrayList<Account>)request.getAttribute("accountList");
    %>
        <h1>View/Edit Customer</h1>
        <div class="customer-data">
        <div class="left-half">
            <form action="EditCustomer" name="editcustomer" method="post">
                <b>Customer ID: </b><%=customer.getCustomerId()%><br><br>
                <input type="hidden" name="id" value="<%=customer.getCustomerId()%>"/>
                <b>First Name: </b><input type="text" name="fName" maxlength="20" value="<%=customer.getFirstName()%>" required><br><br>
                <b>Last Name: </b><input type="text" name="lName" maxlength="20" value="<%=customer.getLastName()%>" required><br><br>
                <b>SSN: </b> <%=customer.getSSNId()%> <br><br>
                <b>Date of Birth: </b> <%=customer.getDateOfBirth()%> <br><br>
                <b>Address Line 1: </b><input type="text" name="address1" maxlength="50" value="<%=customer.getAddress1()%>" required><br><br>
                <b>Address Line 2: </b><input type="text" name="address2" maxlength="50" value="<%=customer.getAddress2()%>" required><br><br>
                <b>City: </b><input type="text" name="city" id="city" maxlength="20" value="<%=customer.getCity()%>" required><br><br>
                <b>State: </b>
                <select name="state" required>
                    <option value="Alaska" <% if(customer.getState().equalsIgnoreCase("Alaska")){%><%="selected" %> <%} %>>Alaska</option>
                    <option value="California" <% if(customer.getState().equalsIgnoreCase("California")){%><%="selected" %> <%} %>>California</option>
                    <option value="Florida" <% if(customer.getState().equalsIgnoreCase("Florida")){%><%="selected" %> <%} %>>Florida</option>
                    <option value="New Jersey" <% if(customer.getState().equalsIgnoreCase("New Jersey")){%><%="selected" %> <%} %>>New Jersey</option>
                    <option value="New York" <% if(customer.getState().equalsIgnoreCase("New York")){%><%="selected" %> <%} %>>New York</option>
                </select><br><br>
                <b>Zip Code: </b><input type="text" name="zipCode" maxlength="6" value="<%=customer.getZipCode()%>" required><br><br><br>
                <span><a href="./executivehome"><button type="button">Cancel</button></a></span>&emsp13;
                <span><input type="submit" value="Save"/></span>
            </form>
        </div>

        <div class="right-half">
            <h1>Accounts</h1>
            <% if (accounts.size() == 0) { %>
                <h1>No account available</h1>
            <% } else { %>
            <table>
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
                    <td><%=a.getAccountId() %></td>
                    <td><%=a.getAccountType() %></td>
                    <td>
                        <%
                        if (a.getStatus().equalsIgnoreCase("Open")) {%>
                            <%=a.getStatus() %>
                        <%} else if (a.getStatus().equalsIgnoreCase("Closed")) {%>
                            <b style="color:red"><%=a.getStatus() %></b>
                        <%}%>
                    </td>
                    <td>$<%=a.getBalance()%></td>
                    <td><%=a.getLastUpdate()%></td>
                    <td>
                        <%
                        if (a.getStatus().equalsIgnoreCase("Open")) {%>
                            <a href="./accountoperation?aid=<%=a.getAccountId()%>&cid=<%=customer.getCustomerId()%>&operation=close" onsubmit="return giveOpsWarning(close,<%=a.getBalance()%>)">close</a>
                        <%} else if (a.getStatus().equalsIgnoreCase("Closed")) {%>
                            <a href="./accountoperation?aid=<%=a.getAccountId()%>&cid=<%=customer.getCustomerId()%>&operation=reopen" onsubmit="return giveOpsWarning()">re-open</a>
                        <%}%>
                    </td>
                </tr>
                <% 
                    }
                %>
                </table> 
                <% } %>
                <div id="createAccount">
                &emsp;<button onclick="newAccount()">Create New Account</button>
            </div>
            <script>
                function newAccount() {
                    document.getElementById("createAccount").innerHTML =
                        '<h1>Create Account</h1>' +
                        '<form action="CreateAccount" method="post">' +
                        '&emsp;&emsp;<b>Customer ID: </b><%=customer.getCustomerId()%><br><br>' +
                        '<input type="hidden" name="id" value="<%=customer.getCustomerId()%>"/>' +
                        '&emsp;&emsp;<b>Select Account Type: </b>' +
                            '<select name="type" required>' +
                                '<option value="Checking">Checking</option>' +
                                '<option value="Savings">Savings</option>' +
                            '</select><br><br>' +
                            '&emsp;&emsp;<b>Deposit Amount: </b><input type="number" name="deposit" min="1" required><br><br>' +
                                '&emsp;&emsp;<span><input type="reset" value="Reset"/></span>' +
                                '&emsp;<span><input type="submit" value="Save"/></span>' +
                            '</form>';
                }
            </script>
        </div>
        </div>
        <%}%>
        <%@ include file="footer.jsp"%>
</body>
</html>