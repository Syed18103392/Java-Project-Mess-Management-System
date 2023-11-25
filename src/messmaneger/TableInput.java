/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messmaneger;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bonna
 */
public class TableInput {
    static Connection connection() throws SQLException, ClassNotFoundException{
        //database connection
    String driver="com.mysql.jdbc.Driver";
    String url="jdbc:mysql://localhost/db_mess";
    String username="root";
    String password="";
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return con; 
    }
    
    static void creatingTable(String c,String col1,String col2){
        try {
            Connection ob=connection();
            PreparedStatement create=ob.prepareStatement("CREATE TABLE IF NOT EXISTS "+c+"(id int NOT NULL AUTO_INCREMENT,"+col1+" varchar(20),"+col2+" varchar(20),PRIMARY KEY(id))");
            create.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(TableInput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TableInput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
