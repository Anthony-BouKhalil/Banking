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
 * Servlet implementation class TransactionControlller
 */
public class TransactionControlller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionControlller() {
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
                int aid = Integer.parseInt(request.getParameter("aid"));
                int cid = Integer.parseInt(request.getParameter("cid"));

                Customer customer = bs.fetchCustomer(cid);
                ArrayList<Account> accounts = bs.fetchAccounts(cid);

                if (customer != null && accounts != null) {
                    request.setAttribute("customer", customer);
                    request.setAttribute("accountList", accounts);
                    request.setAttribute("aid", aid);
                    RequestDispatcher rd = request.getRequestDispatcher("transaction.jsp");
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
        String role = bs.checkRole(request);
        if (role != null) {
            if (!role.equals("teller")) {
                ((HttpServletResponse) response).sendRedirect("./executivehome");
            } else {
                int accountId = Integer.parseInt(request.getParameter("aid"));
                int cId = Integer.parseInt(request.getParameter("cid"));
                String type = request.getParameter("transaction");
                double amount = Double.parseDouble(request.getParameter("amount"));
                boolean result = false;
                int toAc = 0;
                
                if (type.equalsIgnoreCase("Deposit")) {
                    result = bs.deposit(accountId, type, amount, toAc);
                } else if (type.equalsIgnoreCase("Withdraw")) {
                    result = bs.withdraw(accountId, type, amount, toAc);
                } else if (type.equalsIgnoreCase("Transfer")) {
                    toAc = Integer.parseInt(request.getParameter("toId"));
                    result = bs.deposit(accountId, type, amount, toAc) & bs.withdraw(accountId, type, amount, toAc);
                }

                if (result) {
                    request.setAttribute("msg", type+" transaction in account number "+accountId+" of $"+amount+" successfully done.");
                    request.setAttribute("link", "./transaction?aid="+accountId+"&cid="+cId);
                    RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
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
