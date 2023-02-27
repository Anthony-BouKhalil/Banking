<header class="cover">
    <h3><span style="color:#fff"></span>Bank</h3>
    <div class="navbar">
    
        <%
        String role=null;
        String roleParam = request.getParameter("role");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("role")) role = cookie.getValue();
            }
        }
        if (role == null) {
        %>
            <a href="./">Home</a>
        <%
        }
        else if (role != null) {
        %>
        <a href="./executivehome">Home</a>
        <%
        }
        if (role != null && role.equals("executive")) {
        %>
            <a href="createCustomer.jsp">Create Customer</a>

            <form method="post" action="Search">
                <select name="Type" class="Type">
                    <option value="SSNID">SSN</option>
                    <option value="CUSTOMER_ID">Customer ID</option>
                    <option value="LNAME">Last Name</option>
                </select>
                <input type="text" name="searchValue" placeholder="Search...">
                <input type="submit" value="Search">
            </form>

			<div class="dropdown">
                <span>Status Details</span>
                <div class="dropdown-content">
                    <a href="./AccountStatus">All Account Details</a>
                    <a href="./CustomerStatus">All Customers Details</a>
                </div>
			</div>
            <a href="logout">Logout</a>
        <%
        } else if(role != null && role.equals("teller")) {
        %>
            <form method="post" action="Search">
                <select name="Type" class="Type">
                    <option value="SSNID">SSN</option>
                    <option value="CUSTOMER_ID">Customer ID</option>
                    <option value="ACCOUNT_ID">Account ID</option>
                </select>
                <input type="text" name="searchValue" placeholder="Search...">
                <input type="submit" value="Search">
            </form>
            <a href="logout">Logout</a>
        <%
        }
        %>
    </div>
</header>