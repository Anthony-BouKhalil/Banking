package com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    private static Connection connection = null;

    //If this is a remote database, change localhost to the IP address of the remote server for mySQL use
    //URL="jdbc:mysql://localhost:3306"
    
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; //for Oracle
    private static final String USER = "system";
    private static final String PASSWORD = "requests";

	public static Connection getConnection() {
        if (connection==null) 
        {
            try
            {
				//For mySQL use
				//Class.forName("com.mysql.jdbc.Driver");
				//For Oracle
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			} 
			catch (Exception e)
			{ 
				e.printStackTrace();
			}
		}
		return connection;
	}

    public static void closeConnection() {
        if (connection==null) 
        {
            try
            {
                if(connection != null && !connection.isClosed())
                {
                    connection.close();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        Connection con = getConnection();

        if (con != null) {
            System.out.println("connection created...");
        }
        closeConnection();
        System.out.println("connection closed...");
    }

}