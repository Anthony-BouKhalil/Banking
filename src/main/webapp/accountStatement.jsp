<%@ include file ="authCheck.jsp"%>
<%
    if (r != null && !r.equals("teller")) {
        response.sendRedirect("./executivehome");
    }
%>
<%@ page import="com.bank.beans.Account" %>
<%@ page import="com.bank.beans.Transaction" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; chaset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account Statement</title>
<link rel="stylesheet" href="style.css">
</head>

<body>
    <%@ include file="header.jsp"%>
    <%
        int aid = Integer.parseInt(request.getParameter("aid"));

        if (aid>0) {
    %>
    <h1>Account Statement </h1><br><br>

    <p><b>Account ID: </b> <%=aid%></p>

    <input type="radio" id="numOfTransactions" name="statementType" onclick="statementType(this)" value="numberOfTransactions" required>
    <label for="numberOfTransactions">Last Number of Transactions</label><br><br>
    <input type="radio" id="date" name="statementType" onclick="statementType(this)" value="date">
    <label for="date">Start-End Dates</label><br><br>

    <div id="numDiv">
    <form action="ShowTransactions" method="post">
        <input type="hidden" name="aid" value="<%=aid%>">
        <label for="number">Number of Transactions </label>
        <input type="number" id="number" name="number" min="1" required><br><br>
        <br><br>
        <input type="submit" value="Submit">
    </form>    
    </div>

    <div id="dateDiv">
    <form action="ShowTransactions" method="post" onsubmit="return checkDate()">
        <input type="hidden" name="aid" value="<%=aid%>">
        <label for="startDate">Start Date </label>
        <input type="date" class="input" id="startDate" oninput="abc(this)" name="startDate" placeholder="mm/dd/yyyy" required><br><br>
        <br><br>
        <label for="endDate">End Date </label>
        <input type="date" class="input" id="endDate"name="endDate" placeholder="mm/dd/yyyy" required><br><br>
        <br><br>
        <input type="submit" value="Submit">
    </form>
    </div>
    <%
        ArrayList<Transaction> transactions = (ArrayList<Transaction>)request.getAttribute("transactions");
        if (transactions!=null) {
            if (transactions.size()==0) {
    %>
    <b>No transactions available</b>
    <%} else { %>
        <table>
            <tr>
                <th>Transaction ID</th>
                <th>Description</th>
                <th>Date</th>
                <th>Amount</th>
            </tr>
            <%
                for (Transaction t:transactions) {
            %>
            <tr>
                <td><%=t.getTransactionId()%></td>
                <td><%=t.getType()%></td>
                <td><%=t.getTimestamp()%></td>
                <td><%=t.getAmount()%></td>
            </tr>
                <%}%>
        </table>
    <%}}
    } else { response.sendRedirect("./executivehome"); } %>
    <%@ include file="footer.jsp"%>
    <script src="script.js"></script>
</body>
</html>
