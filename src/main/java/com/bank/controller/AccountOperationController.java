package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.service.BankingService;

/**
 * Servlet implementation class AccountOperationController
 */
public class AccountOperationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountOperationController() {
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
            if (!role.equals("executive")) {
                ((HttpServletResponse) response).sendRedirect("./executivehome");
            } else {
                int cid = Integer.parseInt(request.getParameter("cid"));
                int aid = Integer.parseInt(request.getParameter("aid"));
                String operation = request.getParameter("operation");

                boolean status = bs.accountOperation(cid,aid,operation);
                if (status) {
                    ((HttpServletResponse) response).sendRedirect("./EditCustomer?id="+cid);
                } else {
                    ((HttpServletResponse) response).sendRedirect("error.jsp");
                }
            }
        } 
	}

}
