/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messmaneger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Bonna
 */
public class CreateDb {
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
        static final String DB_URL = "jdbc:mysql://localhost/";
        //Data base Credentials
        static final String USER="root";
        static final String PASS="";
        public void Create(String c){
            Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 4: Execute a query
      System.out.println("Creating database...");
      stmt = conn.createStatement();
      
      String sql = "CREATE DATABASE "+c;
      stmt.executeUpdate(sql);
      System.out.println("Database created successfully...");
   }catch(SQLException se){
                //Handle errors for JDBC

   }catch(ClassNotFoundException e){
                //Handle errors for Class.forName

   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
        }
    
    }

