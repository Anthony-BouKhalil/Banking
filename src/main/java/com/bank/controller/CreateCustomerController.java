package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bank.service.BankingService;

/**
 * Servlet implementation class CreateCustomerController
 */
public class CreateCustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCustomerController() {
        super();
        // TODO Auto-generated constructor stub
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
	            String firstName = request.getParameter("fname");
	            String lastName = request.getParameter("lname");
	            int ssn = Integer.parseInt(request.getParameter("ssn"));
	            String dateOfBirth = request.getParameter("dob");
	            String address1 = request.getParameter("address1");
	            String address2 = request.getParameter("address2");
	            String city = request.getParameter("city");
	            String state = request.getParameter("state");
	            String zipCode = request.getParameter("zipcode");
	
	            int cid = bs.createCustomer(firstName, lastName, ssn, dateOfBirth, address1, address2, city, state, zipCode);
	
	            if (cid > 0) {
	                ((HttpServletResponse) response).sendRedirect("./EditCustomer?id="+cid);
	            } else {
	                ((HttpServletResponse) response).sendRedirect("error.jsp");
	            }
	        }
	    } else {
	        ((HttpServletResponse) response).sendRedirect("./executivehome");
	    }
	}
}
