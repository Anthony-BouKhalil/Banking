package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import com.bank.service.BankingService;
import com.bank.beans.Login;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uname = request.getParameter("uname");
        String password = request.getParameter("password");
        
        BankingService bs = new BankingService();
        Login auth=bs.checkAuth(uname, password);

        if (auth.getUsername() != null) {
            Cookie un = new Cookie("username", auth.getUsername());
            un.setMaxAge(60*60);
            ((HttpServletResponse) response).addCookie(un);
            
            Cookie role = new Cookie("role", auth.getRole());
            role.setMaxAge(60*60);
            ((HttpServletResponse) response).addCookie(role);

            ((HttpServletResponse) response).sendRedirect("./executivehome");
        } else {
            Cookie er = new Cookie("er", "er");
            er.setMaxAge(60*60);
            ((HttpServletResponse) response).addCookie(er);
            ((HttpServletResponse) response).sendRedirect("login.jsp");
        }
	}

}
