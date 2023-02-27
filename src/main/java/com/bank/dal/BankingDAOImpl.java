package com.bank.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.bank.beans.Account;
import com.bank.beans.Customer;
import com.bank.beans.CustomerAccount;
import com.bank.beans.Login;
import com.bank.beans.Transaction;
import com.bank.util.DbConnection;

public class BankingDAOImpl implements BankingDAO {


    private static final String TBL_USER = "tbl_user";
    private static final String TBL_CUSTOMER = "tbl_customer";
    private static final String TBL_ACCOUNT = "tbl_account";
    private static final String TBL_TRANSACTION = "tbl_transaction";

    private static Timestamp getCurrentTimeStamp() {
        Date today = new Date();
        return new Timestamp(today.getTime());
    }

    @Override
    public Login checkAuth(String uname, String password) {

        Connection con = DbConnection.getConnection();
        Login l = new Login();

        try {
            String qry = "select username, role from " + TBL_USER + " where username=? and password=?";
            
            PreparedStatement st = con.prepareStatement(qry);
            st.setString(1, uname);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                l.setUsername(rs.getString(1));
                l.setRole(rs.getString(2));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (l.getUsername() != null) {

            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                DbConnection.closeConnection();
            }
        }
        return l;
    }

    @Override
    public String checkRole(HttpServletRequest request) {
        String role=null;

        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("role")) role = cookie.getValue();
            } 
        }
        return role;
    } 

    @Override
    public ArrayList<Customer> fetchAllCustomer() {
        ArrayList<Customer> allCustomers = new ArrayList<Customer>();
        Connection con = DbConnection.getConnection();

        try {
            String qry = "select CUSTOMER_ID,FNAME,LNAME,SSNID,LAST_UPDATE from " + TBL_CUSTOMER + " where STATUS ='active'";
            PreparedStatement st = con.prepareStatement(qry);

            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt(1));
                c.setFirstName(rs.getString(2));
                c.setLastName(rs.getString(3));
                c.setSSNId(rs.getInt(4));
                c.setLastUpdate(rs.getString(5));
                allCustomers.add(c);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        return allCustomers;
    }

    @Override
    public Customer fetchCustomer(int customerId) {
        Customer customer = new Customer();
        Connection con = DbConnection.getConnection();

        try {
            String qry = "SELECT * FROM " + TBL_CUSTOMER + " WHERE CUSTOMER_ID = ?";
            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, customerId);

            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                customer.setCustomerId(rs.getInt(1));
                customer.setSSNId(rs.getInt(2));
                customer.setFirstName(rs.getString(3));
                customer.setLastName(rs.getString(4));
                customer.setDateOfBirth(rs.getString(5));
                customer.setAddress1(rs.getString(6));
                customer.setAddress2(rs.getString(7));
                customer.setCity(rs.getString(8));
                customer.setState(rs.getString(9));
                customer.setZipCode(rs.getString(10));
                customer.setStatus(rs.getString(11));
                customer.setLastUpdate(rs.getString(12));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        	return customer;
    }

    @Override
    public ArrayList<Account> fetchAccounts(int customerId) {
        ArrayList<Account> accounts = new ArrayList<Account>();
        Connection con = DbConnection.getConnection();

        try {
            String qry = "SELECT * FROM " + TBL_ACCOUNT + " WHERE CUSTOMER_ID = ?";
            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, customerId);

            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                Account a = new Account();
                a.setAccountId(rs.getInt(1));
                a.setCustomerId(rs.getInt(2));
                a.setAccountType(rs.getString(3));
                a.setBalance(rs.getDouble(4));
                a.setStatus(rs.getString(5));
                a.setLastUpdate(rs.getString(6));
                accounts.add(a);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        	return accounts;        
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        Connection con = DbConnection.getConnection();

        boolean result = false;
        try {
            String qry1 = "UPDATE " + TBL_ACCOUNT + " SET STATUS='closed',LAST_UPDATE=? WHERE CUSTOMER_ID = ?";

            PreparedStatement stl1 = con.prepareStatement(qry1);
            stl1.setObject(1, getCurrentTimeStamp());
            stl1.setInt(2, customerId);

            int n1 = stl1.executeUpdate();

            String qry2 = "UPDATE " + TBL_CUSTOMER + " SET STATUS='inactive',LAST_UPDATE=? WHERE CUSTOMER_ID = ?";

            PreparedStatement stl2 = con.prepareStatement(qry2);
            stl2.setObject(1, getCurrentTimeStamp());
            stl2.setInt(2, customerId);

            int n2 = stl2.executeUpdate(); 

            if (n1 > 0 || n2 > 0) {
                result = true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        	return result;  
    }

    @Override
    public ArrayList<CustomerAccount> fetchCustomerAccounts() {

        ArrayList<CustomerAccount> customerAccounts = new ArrayList<CustomerAccount>();
        Connection con = DbConnection.getConnection();

        try {
            String qry = "SELECT C.CUSTOMER_ID, A.ACCOUNT_ID, A.AC_TYPE, A.STATUS, A.BALANCE, A.LAST_UPDATE"
            + " FROM " + TBL_ACCOUNT 
            + " A INNER JOIN " + TBL_CUSTOMER + " C ON " +
            "A.CUSTOMER_ID=C.CUSTOMER_ID";

            PreparedStatement st = con.prepareStatement(qry);
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                CustomerAccount ca = new CustomerAccount();
                ca.setCustomerId(rs.getInt(1));
                ca.setAccountId(rs.getInt(2));
                ca.setAccountType(rs.getString(3));
                ca.setBalance(rs.getDouble(4));
                ca.setAccountStatus(rs.getString(5));
                ca.setLastTransaction(rs.getString(6));
                customerAccounts.add(ca);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        return customerAccounts;
    }

    @Override
    public boolean withdraw(int accountId, String type, double balance, int toAc){
        Connection con = DbConnection.getConnection();

        boolean result = false;
        try {
            String qry1 = "UPDATE " + TBL_ACCOUNT + " SET BALANCE=BALANCE-?, LAST_UPDATE=? WHERE ACCOUNT_ID = ?";
            PreparedStatement st1 = con.prepareStatement(qry1);
            st1.setDouble(1, balance);
            st1.setObject(2, getCurrentTimeStamp());
            st1.setInt(3, accountId);

            int n1 = st1.executeUpdate();

            String qry2 = "INSERT INTO " + TBL_TRANSACTION + 
            " (TRANSACTION_TIMESTAMP, T_TYPE, AMOUNT, FROM_AC_ID, TO_AC_ID) " + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement st2 = con.prepareStatement(qry2);
            st2.setObject(1, getCurrentTimeStamp());
            st2.setString(2, type);
            st2.setDouble(3, balance);
            st2.setInt(4, accountId);
            if (type.equals("Transfer")) {
                st2.setInt(5, toAc);
            } else {
                st2.setInt(5, accountId);
            }

            int n2 = st2.executeUpdate();

            if (n1 > 0 && n2 > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        return result;
    }

    public boolean deposit(int accountId, String type, double balance, int toAc) {
        Connection con = DbConnection.getConnection();
        
        boolean result = false;
        try {
            String qry1 = "UPDATE " + TBL_ACCOUNT + " SET BALANCE=BALANCE+?, LAST_UPDATE=? WHERE ACCOUNT_ID = ?";
            PreparedStatement st1 = con.prepareStatement(qry1);
            st1.setDouble(1, balance);
            st1.setObject(2, getCurrentTimeStamp());
            st1.setInt(3, accountId);

            int n1 = st1.executeUpdate();

            String qry2 = "INSERT INTO " + TBL_TRANSACTION + 
            " (TRANSACTION_TIMESTAMP, T_TYPE, AMOUNT, FROM_AC_ID, TO_AC_ID) " + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement st2 = con.prepareStatement(qry2);
            st2.setObject(1, getCurrentTimeStamp());
            st2.setString(2, type);
            st2.setDouble(3, balance);
            st2.setInt(4, accountId);
            if (type.equals("Transfer")) {
                st2.setInt(5, toAc);
            } else {
                st2.setInt(5, accountId);
            }

            int n2 = st2.executeUpdate();

            if (n1 > 0 && n2 > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        return result;
    }

    @Override
    public ArrayList<Customer> fetchCustomers() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        Connection con = DbConnection.getConnection();

        try {
            String qry = "SELECT * FROM " + TBL_CUSTOMER;
            PreparedStatement st = con.prepareStatement(qry);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt(1));
                customer.setSSNId(rs.getInt(2));
                customer.setFirstName(rs.getString(3));
                customer.setLastName(rs.getString(4));
                customer.setDateOfBirth(rs.getString(5));
                customer.setAddress1(rs.getString(6));
                customer.setAddress2(rs.getString(7));
                customer.setCity(rs.getString(8));
                customer.setState(rs.getString(9));
                customer.setZipCode(rs.getString(10));
                customer.setStatus(rs.getString(11));
                customer.setLastUpdate(rs.getString(12));
                customers.add(customer);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        return customers;
    }

    @Override
    public boolean editCustomer(int customerId, String firstName, String lastName, String address1, String address2, String city, String state, String zipCode){
        Connection con = DbConnection.getConnection();

        boolean result = false;
        try {
            String qry = "UPDATE " + TBL_CUSTOMER +
                            " SET FNAME=?, LNAME=?, ADDRESS1=?, ADDRESS2=?, CITY=?, STATE=?, ZIP_CODE=?, LAST_UPDATE=?" +
                            " WHERE CUSTOMER_ID = ?";

            PreparedStatement st = con.prepareStatement(qry);
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setString(3, address1);
            st.setString(4, address2);
            st.setString(5, city);
            st.setString(6, state);
            st.setString(7, zipCode);
            st.setObject(8, getCurrentTimeStamp());
            st.setInt(9, customerId);
            
            int n = st.executeUpdate();

            if (n > 0) {
                result = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        return result;
    }

    @Override
    public boolean createAccount(int customerId, String accountType, int balance) {
        Connection con = DbConnection.getConnection();

        boolean result = false;
        try {
            String qry = "INSERT INTO " + TBL_ACCOUNT +
                            " (CUSTOMER_ID, AC_TYPE, BALANCE, STATUS, LAST_UPDATE)" +
                            " VALUES(?, ?, ?, ?, ?)";

            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, customerId);
            st.setString(2, accountType);
            st.setInt(3, balance);
            st.setString(4, "Open");
            st.setTimestamp(5, getCurrentTimeStamp());
            
            int n = st.executeUpdate();

            if (n > 0) {
                result = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        return result;
    }

    @Override
    public boolean accountOperation(int cid, int aid, String operation) {
        Connection con = DbConnection.getConnection();

        String qry = null;
        boolean result = false;
        try {
            PreparedStatement st = null;
            if (operation.equals("close")) {
                qry = "UPDATE " + TBL_ACCOUNT + " SET STATUS='closed',LAST_UPDATE=? WHERE ACCOUNT_ID=?";

                st = con.prepareStatement(qry);
                st.setObject(1, getCurrentTimeStamp());
                st.setInt(2, aid);
            } else if (operation.equals("reopen")) {
                qry = "UPDATE " + TBL_ACCOUNT + " SET STATUS='open',LAST_UPDATE=? WHERE ACCOUNT_ID=?";

                st = con.prepareStatement(qry);
                st.setObject(1, getCurrentTimeStamp());
                st.setInt(2, aid);
            }

            int n1 = st.executeUpdate();

            if (n1 > 0) {
                result = true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnection.closeConnection();
        }
        return result;
    }

    @Override
    public Integer createCustomer(String firstName, String lastName, Integer ssn, String dateOfBirth, String address1, String address2, String city, String state, String zipCode) {
        Connection con = DbConnection.getConnection();
        int cid = 0;

        try {
            String qry1 = "INSERT INTO " + TBL_CUSTOMER + " (SSNID,FNAME,LNAME,DOB,ADDRESS1,ADDRESS2,CITY,STATE,ZIP_CODE,STATUS,LAST_UPDATE) VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?, ?, ?, ?)";

        	String qry2 = "SELECT CUSTOMER_ID FROM " +
            "( SELECT CUSTOMER_ID, ROWNUM rn FROM " + TBL_CUSTOMER + " " +
            "ORDER BY CUSTOMER_ID DESC ) " +
            "WHERE rn = 1";

            PreparedStatement st1 = con.prepareStatement(qry1);
            st1.setInt(1, ssn);
            st1.setString(2, firstName);
            st1.setString(3, lastName);
            st1.setString(4, dateOfBirth);
            st1.setString(5, address1);
            st1.setString(6, address2);
            st1.setString(7, city);
            st1.setString(8, state);
            st1.setString(9, zipCode);
            st1.setString(10, "active");
            st1.setTimestamp(11, getCurrentTimeStamp());

            int n = st1.executeUpdate();
            
            if (n < 1) {
            	return null;
            }

            PreparedStatement st2 = con.prepareStatement(qry2);
            ResultSet rs = st2.executeQuery();

            while (rs.next()) {
                cid=rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();  
        } finally {
            DbConnection.closeConnection();
        }
        return cid;
    }

    @Override
    public ArrayList<Customer> searchCustomer(String searchType, String input) {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        Connection con = DbConnection.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            if (searchType.equalsIgnoreCase("Account_ID")) {
                String qry = "select * from " + TBL_CUSTOMER + " where CUSTOMER_ID=(select TBL_ACCOUNT.CUSTOMER_ID from TBL_ACCOUNT where TBL_ACCOUNT.ACCOUNT_ID = ?)";

                st = con.prepareStatement(qry);
                st.setInt(1, Integer.parseInt(input));
            } else {
                String qry = "select * from " + TBL_CUSTOMER + " where " + searchType + "=? and STATUS='active'";
                st = con.prepareStatement(qry);
                if (searchType.equalsIgnoreCase("lname")) {
                    st.setString(1, input);
                } else {
                    st.setInt(1, Integer.parseInt(input));
                }
            }

            rs = st.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt(1));
                customer.setSSNId(rs.getInt(2));
                customer.setFirstName(rs.getString(3));
                customer.setLastName(rs.getString(4));
                customer.setDateOfBirth(rs.getString(5));
                customer.setAddress1(rs.getString(6));
                customer.setAddress2(rs.getString(7));
                customer.setCity(rs.getString(8));
                customer.setState(rs.getString(9));
                customer.setZipCode(rs.getString(10));
                customer.setStatus(rs.getString(11));
                customer.setLastUpdate(rs.getString(12));
                customers.add(customer);               
            }
        } catch (Exception ex) {
            ex.printStackTrace();  
        } finally {
            DbConnection.closeConnection();
        }
        return customers;
    }

    @Override
	public ArrayList<Transaction> fetchTransactionsByDate(int accountId, String startDate, String endDate)  {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        Connection con = DbConnection.getConnection();

        try {
            String qry = "SELECT TRANSACTION_ID, TRANSACTION_TIMESTAMP, T_TYPE, AMOUNT FROM " + TBL_TRANSACTION + " " +
                        "WHERE (FROM_AC_ID=? OR TO_AC_ID=?) AND (TRANSACTION_TIMESTAMP BETWEEN ? AND ?) " +
                        "ORDER BY TRANSACTION_ID DESC";

            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, accountId);
            st.setInt(2, accountId);
            st.setTimestamp(3, Timestamp.valueOf(startDate + " 00:00:00"));
            st.setTimestamp(4, Timestamp.valueOf(endDate + " 23:59:59"));
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt(1));
                transaction.setTimestamp(rs.getString(2));
                transaction.setType(rs.getString(3));
                transaction.setAmount(rs.getDouble(4));
                transactions.add(transaction);
            }
        } catch (Exception ex) {
            ex.printStackTrace();  
        } finally {
            DbConnection.closeConnection();
        }
        return transactions;
    }

    @Override
	public ArrayList<Transaction> fetchTransactionsByNumber(int accountId, int numOfTransactions) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        Connection con = DbConnection.getConnection();

        try {
            String qry = "select * from (select * from " + TBL_TRANSACTION + " where FROM_AC_ID=? order by TRANSACTION_ID desc) where ROWNUM <= ?";

            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, accountId);
            st.setInt(2, numOfTransactions);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt(1));
                transaction.setTimestamp(rs.getString(2));
                transaction.setType(rs.getString(3));
                transaction.setAmount(rs.getDouble(4));
                transactions.add(transaction);
            }
        } catch (Exception ex) {
            ex.printStackTrace();  
        } finally {
            DbConnection.closeConnection();
        }
        return transactions;
    }
}
