import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.lang.*;
/**
 * This class is used to execute a query and get result
 */
class Database
{
    //  Database credentials
    final String USER = "root";
    final String PASS = "password/*-+123";
    Statement stmt;
    Connection conn;
    final int numberOfQuery=5;
    /**
     * To increase the number of queries first increase variable numberOfQuery by 1
     * Then write query underneath the last query
     */
    Database(String database)
    {
        // JDBC driver name and database URL
        final String DB_URL="jdbc:mysql://localhost/"+database;
        try
        {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        }
        catch(java.lang.ClassNotFoundException e)
        {
            System.err.print("Class not found exception2: ");
            System.err.println(e.getMessage());
        }
        catch(SQLException se)
        {
            System.out.println("Some error while opening the Database"+database);
            se.printStackTrace();
        }
    }
    void closeDatabase()
    {
        try
        {
            stmt.close();
            conn.close();
        }
        catch(SQLException se)
        {
            System.out.println("Error : SQL - occured when trying to close connection");
            se.printStackTrace();
        }
    }
    ResultSet executeQuery(String query)
    {
        ResultSet rs=null;
        try
        {
            rs = stmt.executeQuery(query);
        }
        catch(SQLException ex)
        {
            System.err.println("SQLException: " + ex.getMessage());
        }
        return rs;
    }

}
