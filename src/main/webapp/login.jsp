<%@ page language="java" contentType="text/html; chaset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <%@ include file="header.jsp"%>
    
    <div class="login-section">
        <p class="heading">Please login to access</p>
        <form action="login" method="post" class="login-form">
            <div class="container">
                <label for="uname">Username</label>
                <input type="text" name="uname" placeholder="Enter username" required><br>
            </div>
            <div class="container">
                <label for="password">Password</label>
                <input type="password" name="password" placeholder="Enter password" required><br>
            </div>
            <input type="submit" value="Login">
        </form>
        <%
        String er=null;

        Cookie[] ecookies = request.getCookies();
        if(ecookies != null) {
            for (Cookie cookie: ecookies) {
                if (cookie.getName().equals("er")) er = cookie.getValue();
            }
        }
        if (er != null) {
        %>
        <p>Please enter valid username and password!</p>
        <%
            }
        %>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>