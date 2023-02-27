package com.bank.dal;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.bank.beans.Account;
import com.bank.beans.Customer;
import com.bank.beans.CustomerAccount;
import com.bank.beans.Login;
import com.bank.beans.Transaction;

public interface BankingDAO {
	
	Login checkAuth(String uname, String password);
	
	String checkRole(HttpServletRequest request);
	
	ArrayList<Customer> fetchAllCustomer();
	
	Customer fetchCustomer(int customerId);
	
	ArrayList<Account> fetchAccounts(int customerId);
	
	boolean deleteCustomer(int customerId);
	
	ArrayList<CustomerAccount> fetchCustomerAccounts();
	
	boolean deposit(int accountId, String type, double balance, int toAc);
	
	boolean withdraw(int accountId, String type, double balance, int toAc);
	
	ArrayList<Customer> fetchCustomers();
	
	boolean editCustomer(int customerId, String firstName, String lastName, String address1, String address2, String city, String state, String zipCode);

	boolean createAccount(int customerId, String accountType, int balance);
	
	boolean accountOperation(int cid, int aid, String operation);
	
	Integer createCustomer(String firstName, String lastName, Integer ssn, String dateOfBirth, String address1, String address2, String city, String state, String zipCode);
	
	ArrayList<Customer> searchCustomer(String searchType, String input);
	
	ArrayList<Transaction> fetchTransactionsByDate(int accountId, String startDate, String endDate);
	
	ArrayList<Transaction> fetchTransactionsByNumber(int accountId, int numOfTransactions);

}