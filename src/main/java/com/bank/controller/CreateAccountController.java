package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.service.BankingService;


/**
 * Servlet implementation class CreateAccountController
 */
public class CreateAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountController() {
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
                int customerId = Integer.parseInt(request.getParameter("id"));
                String accountType = request.getParameter("type");
                int balance = Integer.parseInt(request.getParameter("deposit"));
                
                boolean result = bs.createAccount(customerId, accountType, balance);

                if(result) {
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
