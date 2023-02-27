package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletResponse;

import com.bank.service.BankingService;
import com.bank.beans.CustomerAccount;
import java.util.ArrayList;

/**
 * Servlet implementation class AccountStatusController
 */
public class AccountStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountStatusController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BankingService bs = new BankingService();
        String role=bs.checkRole(request);

        if (role != null) {
            if (!role.equals("executive")) {
            	((HttpServletResponse) response).sendRedirect("./executivehome");
            } else {
                ArrayList<CustomerAccount> customerAccounts = bs.fetchCustomerAccounts();

                if (customerAccounts!=null) {
                    request.setAttribute("customerAccounts", customerAccounts);
                    RequestDispatcher rd = request.getRequestDispatcher("accountStatus.jsp");
                    rd.forward(request, (ServletResponse) response);
                } else {
                	((HttpServletResponse) response).sendRedirect("error.jsp");
                }
            }
        } else {
            ((HttpServletResponse) response).sendRedirect("./executivehome");
        }
	}

}
