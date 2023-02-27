<%
    String un = null; 
	String r = null;

    Cookie[] authCookies = request.getCookies();
if (authCookies != null) {
    for (Cookie cookie : authCookies) {
        if (cookie.getName().equals("username")) un = cookie.getValue() ;
        if (cookie.getName().equals("role")) r = cookie.getValue()  ;
    }
}
if (un != null || r != null) {
    if (!(r.equals("executive") || r.equals("teller"))) {
    	response.sendRedirect("./");
    }
} else {
    response.sendRedirect("./executivehome");
}
%>