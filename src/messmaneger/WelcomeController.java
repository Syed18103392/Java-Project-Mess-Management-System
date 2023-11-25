/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messmaneger;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sajib
 */
public class WelcomeController implements Initializable {

    @FXML
    private Label Greeding, admin_pass_error, admin_user_error;
    @FXML
    private TextField aName, uName;

    @FXML
    private PasswordField aPassword, uPassword;

    @FXML
    private Button aLogin, uLogin;
    @FXML
    private AnchorPane adminLogin, welcome,welcomePane;

    
        @FXML
    private AnchorPane AdminSignUpPane;
    
    @FXML
    private TextField registrationName;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField rePassword;
    
    @FXML
    private void handleAdminLogin(ActionEvent event) {
        adminLogin.setVisible(true);
        welcome.setVisible(false);

    }

    
    
    
    
    
    @FXML
    private void handleALogin(ActionEvent event) throws IOException {
        DbC ob = new DbC();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        ob.getData("tb_login");
        if (aName.getText().equals(ob.getname()) && aPassword.getText().equals(ob.getpass())) {
            admin_user_error.setVisible(false);
            admin_pass_error.setVisible(false);
            alert.setTitle("Successfull");
            alert.setContentText("Successfull Login");
            alert.showAndWait();
           
            
            AnchorPane obs=FXMLLoader.load(getClass().getResource("adminPane.fxml"));
            welcomePane.getChildren().setAll(obs);

        } else {
            if ((aName.getText() == null ? ob.getname() != null : !aName.getText().equals(ob.getname())) && aPassword.getText().equals(ob.getpass())) {
                admin_user_error.setVisible(true);
                admin_pass_error.setVisible(false);
                aName.setText(null);

            } else if ((aName.getText() == null ? ob.getname() == null : aName.getText().equals(ob.getname())) && (aPassword.getText() == null ? ob.getpass() != null : !aPassword.getText().equals(ob.getpass()))) {
                admin_pass_error.setVisible(true);
                admin_user_error.setVisible(false);
                aPassword.setText(null);

            } else {
                admin_pass_error.setVisible(true);
                admin_user_error.setVisible(true);
                aPassword.setText(null);
                aName.setText(null);
            }

        }
    }

    @FXML
    private void handleULogin(ActionEvent event) throws IOException {
        AnchorPane obs=FXMLLoader.load(getClass().getResource("UserPane.fxml"));
            welcomePane.getChildren().setAll(obs);

    }

    
        @FXML
    private void handelAdminSignUp (ActionEvent event) throws IOException {
        AdminSignUpPane.setVisible(true);
                adminLogin.setVisible(false);
        welcome.setVisible(false);
        

    }
    
    
    
    
    
           @FXML
    private void handleNewAdmin(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
         String rName;
        String rPass;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

        newPassword.getText();
        if (rePassword.getText().equals(newPassword.getText())) {

                rPass=newPassword.getText();
                
                CreateDb ob=new CreateDb();
                
                //Creating Database.
                
                ob.Create("db_mess");
                TableInput.creatingTable("tb_login","uname","password");
                Connection conn=TableInput.connection();
                String query="insert into tb_login(uname, password)values(?,?)";
                PreparedStatement create=conn.prepareStatement(query);
                create.setString(1,registrationName.getText());
                create.setString(2,rePassword.getText());
                create.executeUpdate();
                
                 alert.setTitle("Successful");
                alert.setContentText("Registration");
                alert.setHeaderText("Successfully Registered");
                alert.showAndWait();
                
                AdminSignUpPane.setVisible(false);
                adminLogin.setVisible(true);
                
        }
        else{
            
                alert.setTitle("Successful");
                alert.setContentText("Add Member");
                alert.setHeaderText("New password & Re-enter Password Dose not Match!");
                alert.showAndWait();
        
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Date todaysDate = new Date();
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa ");
        DateFormat df1 = new SimpleDateFormat("dd");
        DateFormat df2 = new SimpleDateFormat("HH");
        String str5 = df.format(todaysDate);
        String currentDate = df1.format(todaysDate);
        String currentTime = df2.format(todaysDate);
        int ct = Integer.valueOf(currentTime);
        if (ct > 6 & ct < 12) {
            Greeding.setText("Good Morning");
        } 
        else if (ct > 12 & ct < 15) {
            Greeding.setText("Good AfterNoon");
        } 
        else if (ct >= 15 & ct < 20) {
            Greeding.setText("Good Evening");
        }
        else if (ct > 0 & ct < 6) {
            Greeding.setText("Good Night");
        }

    }

}
