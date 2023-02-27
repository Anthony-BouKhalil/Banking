package com.bank.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.bank.beans.Account;
import com.bank.beans.Customer;
import com.bank.beans.CustomerAccount;
import com.bank.beans.Login;
import com.bank.beans.Transaction;
import com.bank.dal.BankingDAOImpl;

public class BankingService {

		BankingDAOImpl dao;
		public Login checkAuth(String uname, String password) {
			dao = new BankingDAOImpl();
			return dao.checkAuth(uname, password);
		}
		public ArrayList<Customer> fetchAllCustomer() {
			dao = new BankingDAOImpl();
			return dao.fetchAllCustomer();
		}
		public Customer fetchCustomer(int customerId) {
			dao = new BankingDAOImpl();
			return dao.fetchCustomer(customerId);
		}
		public ArrayList<Account> fetchAccounts(int customerId) {
			dao = new BankingDAOImpl();
			return dao.fetchAccounts(customerId);
		}
		public boolean deleteCustomer(int customerId) {
			dao = new BankingDAOImpl();
			return dao.deleteCustomer(customerId);
		}
		public ArrayList<CustomerAccount> fetchCustomerAccounts() {
			dao = new BankingDAOImpl();
			return dao.fetchCustomerAccounts();			
		}
		public String checkRole(HttpServletRequest request) {
			dao = new BankingDAOImpl();
			return dao.checkRole(request);
		}
		public boolean withdraw(int accountId, String type, double balance, int toAc) {
			dao = new BankingDAOImpl();
			return dao.withdraw(accountId, type, balance, toAc);
		}
		public boolean deposit(int accountId, String type, double balance, int toAc) {
			dao = new BankingDAOImpl();
			return dao.deposit(accountId, type, balance, toAc);
		}
		public ArrayList<Customer> fetchCustomers() {
			dao = new BankingDAOImpl();
			return dao.fetchCustomers();
		}
		public boolean editCustomer(int customerId, String firstName, String lastName, String address1, String address2, String city, String state, String zipCode) {
			dao = new BankingDAOImpl();
			return dao.editCustomer(customerId, firstName, lastName, address1, address2, city, state, zipCode);
		}
		public boolean createAccount(int customerId, String accountType, int balance) {
			dao = new BankingDAOImpl();
			return dao.createAccount(customerId, accountType, balance);
		}
		public boolean accountOperation(int cid, int aid, String operation) {
			dao = new BankingDAOImpl();
			return dao.accountOperation(cid, aid, operation);
		}
		public int createCustomer(String firstName, String lastName, int ssn, String dateOfBirth, String address1, String address2, String city, String state, String zipCode) {
			dao = new BankingDAOImpl();
			return dao.createCustomer(firstName, lastName, ssn, dateOfBirth, address1, address2, city, state, zipCode);			
		}
		public ArrayList<Customer> searchCustomer(String searchType, String searchValue) {
			dao = new BankingDAOImpl();	
			return dao.searchCustomer(searchType, searchValue);
		}
		public ArrayList<Transaction> fetchTransactionsByDate(int accountId, String startDate, String endDate) {			
			dao = new BankingDAOImpl();
			return dao.fetchTransactionsByDate(accountId, startDate, endDate);
		}
		public ArrayList<Transaction> fetchTransactionsByNumber(int accountId, int numOfTransactions) {
			dao = new BankingDAOImpl();
			return dao.fetchTransactionsByNumber(accountId, numOfTransactions);
		}
}