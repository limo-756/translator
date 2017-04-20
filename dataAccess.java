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
/**
 * This class makes file handeling easy
 */
class FileRead
{
    private String filePath;
    private File file;
    private Scanner inputFile;
    /**
     * This function returns a file pointer to desired file
     * @param filePath path of the desired file
     */
    FileRead(String filePath)
    {
        this.filePath=filePath;
        // Open the file.
        try
        {
            file = new File(filePath);
            inputFile = new Scanner(file);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found"+filePath);
        }
    }
    void fileClose()
    {
        inputFile.close();
        // file.close();
    }
    String readNextLine()
    {
            String line = inputFile.nextLine();
            line=line.trim();
            line=line.toLowerCase();
            return line;
    }
    boolean hasNextLine()
    {
        return inputFile.hasNext();
    }
}
class FileWrite
{
    BufferedWriter bw = null;
    FileWriter fw = null;
    FileWrite(String filename)
    {
        try
        {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    void writeContent(String content)
    {
        try
        {
            bw.write(content);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    void fileClose()
    {
        try
        {
            if (bw != null)
                bw.close();
            if (fw != null)
                fw.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
