<%@ include file="authCheck.jsp"%>
<%
    if (!r.equals("teller")) {
        response.sendRedirect("./executivehome");
    }
%>
<%@ page import="com.bank.beans.Customer" %>
<%@ page import="com.bank.beans.Account" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bank.service.BankingService" %>
<%@ page language="java" contentType="text/html; chaset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account Transactions</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <%@ include file="header.jsp"%>

    <h1 style="text-align:center">Account Transactions</h1>
    <%
        Customer customer = (Customer)request.getAttribute("customer");
        int aid = Integer.parseInt(request.getParameter("aid"));
        ArrayList<Account> accounts = (ArrayList<Account>)request.getAttribute("accountList");
        Account account = null;
        for (Account a:accounts) {
            if (a.getAccountId() == aid) {
                account=a;
            }
        }
    %>
    <form action="transaction" method="post" onsubmit="return validateInput()" class="login-form">
        <p><b>Customer Name: </b><%=customer.getFirstName()+" "+customer.getLastName()%></p>
        <p><b>Customer ID: </b><%=customer.getCustomerId()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        <b>Account: </b><%=account.getAccountId()%></Account>
        <input type="hidden" name="aid" value="<%=account.getAccountId()%>"/>
        <input type="hidden" name="cid" value="<%=customer.getCustomerId()%>"/>
        <input type="hidden" name="accountList" value="<%=accounts%>"/>
        <p><b>Account Type: </b><%=account.getAccountType()%></p>
        <div>
            <label for="transaction"><b>Transaction type: </b></label>
            <select id="transaction" name="transaction" onchange="changeLabel(this)">
                <option value="Deposit">Deposit</option>
                <option value="Withdraw">Withdraw</option>
                <option value="Transfer">Transfer</option>
            </select>
        </div>
        <p><b>Balance: </b>$<span id="balance"><%=account.getBalance()%></span></p>
        <div id="transferdiv">
            <label for="toId"><b>Transfer account: </b></label>
            <select id="toId" name="toId">
                <%
                for (Account a : accounts) {
                    if (aid != a.getAccountId() && a.getStatus().equalsIgnoreCase("open")) {
                %>
                <option value="<%= a.getAccountId() %>"><%= a.getAccountId() + " " + a.getAccountType() %></option>
                <% }
                }
                %> 
            </select>
        </div>
        <div id="input">
            <b><label id="amountLabel">Deposit Money</label></b>
            <input type="number" name="amount" id="amount" step="any" min="1" required>
        </div>
        <br><br>
        <a href="./viewcustomerteller?id=<%=customer.getCustomerId()%>"><button type="button">Cancel</button></a>
        <input type="submit" value="Do Transaction" style="color:yellow;background-color:black;">
    </form>

    <%@ include file="footer.jsp"%>

    <script src="script.js"></script>
</body>
</html>