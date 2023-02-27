package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

import com.bank.service.BankingService;
import com.bank.beans.Customer;
import com.bank.beans.Account;

/**
 * Servlet implementation class EditCustomerController
 */
public class EditCustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCustomerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("id"));
        
        BankingService bs = new BankingService();
        String role=bs.checkRole(request);

        if (role != null) {
            if (!role.equals("executive")) {
                ((HttpServletResponse) response).sendRedirect("./executivehome");
            } else {
                Customer customer = bs.fetchCustomer(customerId);
                ArrayList<Account> accounts = bs.fetchAccounts(customerId);

                if (customer != null && accounts != null) {
                    request.setAttribute("customer", customer);
                    request.setAttribute("accountList", accounts);
                    RequestDispatcher rd = request.getRequestDispatcher("editCustomer.jsp");
                    rd.forward(request, (ServletResponse) response);
                } else {
                    ((HttpServletResponse) response).sendRedirect("error.jsp");
                }
            }
        } else {
            ((HttpServletResponse) response).sendRedirect("./executivehome");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BankingService bs = new BankingService();
        String role=bs.checkRole(request);

        if (role != null) {
            if (!role.equals("executive")) {
                ((HttpServletResponse) response).sendRedirect("./executivehome");
            } else {
                int customerId = Integer.parseInt(request.getParameter("id"));
                String firstName = request.getParameter("fName");
                String lastName = request.getParameter("lName");
                String address1 = request.getParameter("address1");
                String address2 = request.getParameter("address2");
                String city = request.getParameter("city");
                String state = request.getParameter("state");
                String zipCode = request.getParameter("zipCode");

                boolean result = bs.editCustomer(customerId, firstName, lastName, address1, address2, city, state, zipCode);

                if (result) {
                    ((HttpServletResponse) response).sendRedirect("./EditCustomer?id="+customerId);
                } else {
                    ((HttpServletResponse) response).sendRedirect("error.jsp");
                }
            }
        } else {
            ((HttpServletResponse) response).sendRedirect("./executivehome");
        }
    }

}
