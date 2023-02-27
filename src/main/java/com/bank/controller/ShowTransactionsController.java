package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletResponse;

import com.bank.beans.Transaction;
import com.bank.service.BankingService;

/**
 * Servlet implementation class ShowTransactionsController
 */
public class ShowTransactionsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowTransactionsController() {
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
            if (!role.equals("teller")) {
                ((HttpServletResponse) response).sendRedirect("./executivehome");
            } else {
                int numOfTransactions = -1;
            
                if (request.getParameter("number") != null) {
                    numOfTransactions = Integer.parseInt(request.getParameter("number"));
                }

                int accountId = Integer.parseInt(request.getParameter("aid"));
                ArrayList<Transaction> transactions = new ArrayList<Transaction>();

                if (numOfTransactions == -1) {
                    String startDate = request.getParameter("startDate");
                    String endDate = request.getParameter("endDate");
                    transactions = bs.fetchTransactionsByDate(accountId, startDate, endDate);
                } else {
                    transactions = bs.fetchTransactionsByNumber(accountId, numOfTransactions);
                }
                request.setAttribute("aid", accountId);
                request.setAttribute("transactions", transactions);

                RequestDispatcher rd = request.getRequestDispatcher("accountStatement.jsp");
                rd.forward(request, (ServletResponse) response);
            }
        } else {
            ((HttpServletResponse) response).sendRedirect("./executivehome");
        }
	}

}
