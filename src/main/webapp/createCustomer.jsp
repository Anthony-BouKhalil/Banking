<%@ include file="authCheck.jsp"%>
<%
    if (!r.equals("executive")) {
        response.sendRedirect("./executivehome");
    }
%>
<%@ page language="java" contentType="text/html; chaset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Customers</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <%@ include file="header.jsp"%>

    <h1>Create Customer</h1>
    <form action="CreateCustomer" method="post" class="login-form">
        <label for="fname"><b>First Name</b></label>
        <input type="text" id="fname" name="fname" maxlength="20" required/><br><br>
        <label for="lname"><b>Last Name</b></label>
        <input type="text" id="lname" name="lname" maxlength="20" required/><br><br>
        <label for="ssn"><b>SSN</b></label>
        <input type="number" id="ssn" name="ssn" min="100000000" max="999999999" minlength="9" maxlength="9" required/><br><br>
        <label for="dob"><b>DOB</b></label>
        <input type="date" id="dob" name="dob" class="input" required/><br><br>
        <label for="address"><b>Address Line 1</b></label>
        <input type="text" id="address1" name="address1" maxlength="50" required/><br><br>
        <label for="address1"><b>Address Line 2</b></label>
        <input type="text" id="address2" name="address2" maxlength="50" required/><br><br>
        <label for="city"><b>City</b></label>
        <input type="text" id="city" name="city" maxlength="20" required/><br><br>
        <label for="state"><b>State</b></label>
        <select id="state" name="state" required>
            <option value="Alaska">Alaska</option>
            <option value="California">California</option>
            <option value="Florida">Florida</option>
            <option value="New Jersey">New Jersey</option>
            <option value="New York">New York</option>
        </select><br><br>
        <label for="zipcode"><b>Zip Code</b></label>
        <input type="text" id="zipcode" name="zipcode" maxlength="6" required/><br><br>
        <input type="submit" value="Submit">      
        <input type="reset" value="Reset">     
    </form>
    <%@ include file="footer.jsp"%>
</body>
</html>