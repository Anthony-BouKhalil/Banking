package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletResponse;

import com.bank.beans.Customer;
import com.bank.service.BankingService;

/**
 * Servlet implementation class SearchController
 */
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BankingService bs = new BankingService();
        String role = bs.checkRole(request);

        if (role != null) {
            if (role.equals("executive") || role.equals("teller")) {
                String searchValue = request.getParameter("searchValue");
                String searchType = request.getParameter("Type");

                ArrayList<Customer> customers = bs.searchCustomer(searchType, searchValue);

                request.setAttribute("allcustomer", customers);
                RequestDispatcher rd = request.getRequestDispatcher("activeCustomers.jsp");
                rd.forward(request, (ServletResponse) response);
            } else {
                ((HttpServletResponse) response).sendRedirect("./executivehome");
            }
        } else {
            ((HttpServletResponse) response).sendRedirect("./executivehome");
        }
	}

}
