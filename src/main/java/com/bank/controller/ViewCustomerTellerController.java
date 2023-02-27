package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletResponse;

import com.bank.beans.Account;
import com.bank.beans.Customer;
import com.bank.service.BankingService;

/**
 * Servlet implementation class ViewCustomerTellerController
 */
public class ViewCustomerTellerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewCustomerTellerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BankingService bs = new BankingService();
        String role = bs.checkRole(request);

        if (role != null) {
            if (!role.equals("teller")) {
                ((HttpServletResponse) response).sendRedirect("./executivehome");
            } else {
                int customerId = Integer.parseInt(request.getParameter("id"));
                Customer customer = bs.fetchCustomer(customerId);
                ArrayList<Account> accounts = bs.fetchAccounts(customerId);

                if (customer != null && accounts != null) {
                    request.setAttribute("customer", customer);
                    request.setAttribute("accountList", accounts);
                    RequestDispatcher rd = request.getRequestDispatcher("viewcustomerteller.jsp");
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
