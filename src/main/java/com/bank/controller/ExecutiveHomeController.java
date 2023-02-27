package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletResponse;

import com.bank.service.BankingService;
import com.bank.beans.Customer;

/**
 * Servlet implementation class ExecutiveHomeController
 */
public class ExecutiveHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecutiveHomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BankingService bs = new BankingService();
        String role=bs.checkRole(request);

        if (role != null) {
            ArrayList<Customer> allCustomer = bs.fetchAllCustomer();
            request.setAttribute("allcustomer", allCustomer);
            RequestDispatcher rd = request.getRequestDispatcher("activeCustomers.jsp");
            rd.forward(request, (ServletResponse) response);
    
        } else {
        	((HttpServletResponse) response).sendRedirect("./");
        }
	}
}
