<%@ include file="authCheck.jsp"%>
<%@ page language="java" contentType="text/html; chaset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Success</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <%@ include file="header.jsp"%>
    <%
        String msg=(String)request.getAttribute("msg");
        String link=(String)request.getAttribute("link");
    %>
    <h1><%=msg%></h1>
    <a href="<%=link%>"><button type="button">Go Back</button></a>
    <%@ include file="footer.jsp"%>    
</body>
</html>