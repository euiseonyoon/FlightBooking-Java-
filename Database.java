
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lee
 */
public class Database {
    
    private Connection con = null;
    
    public Database()
    {    
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance(); 
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            System.out.println("finding drvier FAIL");
        }
        
        String url;
        url = "jdbc:mysql://localhost:3306/project2";
        //"jdbc:mysql://localhost:3306/new_schema
        String user = "root";
        String pass = "1234";
        
        try
        {
            con = DriverManager.getConnection(url,user,pass);
        }catch(SQLException e)
        {
            System.out.println("MY-sql connection FAIL");
        }
    }
    
    public Connection getConnection()
    {
        return con;
    }
    
}
