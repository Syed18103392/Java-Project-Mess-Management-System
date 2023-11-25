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
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sajib
 */
public class AddMemberController implements Initializable {

    @FXML
    private AnchorPane newMember;
    @FXML
    private AnchorPane new_user;
    @FXML
    private TextField userName;
    @FXML
    private TextField ContactNumber;
    @FXML
    private TextField GurdianPhone;
    @FXML
    private TextField Profession;
    @FXML
    private PasswordField UserPassword;
    @FXML
    private PasswordField ConfirmPassword;
    @FXML
    private RadioButton male;
    @FXML
    private ToggleGroup a;
    @FXML
    private RadioButton female;
    @FXML
    private Button UserSignUp;
    @FXML
    private TextArea UserAddress;

    /**
     * Initializes the controller class.
     */
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
@FXML
  private void handelUserRegistration(ActionEvent event) throws ClassNotFoundException{
  
        try {
            if(UserPassword.getText().equals(ConfirmPassword.getText())){
            
            CreateDb ob=new CreateDb();
                
                //Creating Database.
                
            ob.Create("db_mess");
            Connection con=TableInput.connection();
            //Creating Table
            String spl="CREATE TABLE IF NOT EXISTS tb_bazar(id int NOT NULL AUTO_INCREMENT,bazarDate varchar(20),bazarTime varchar(30),bazarNameId varchar(20),bazarList varchar(400),totalCost varchar(5),totalPaid varchar(5),extraAdd varchar(20),PRIMARY KEY(id))";  
            PreparedStatement createbazar=con.prepareStatement(spl);
            createbazar.executeUpdate();
            PreparedStatement create=con.prepareStatement("CREATE TABLE IF NOT EXISTS tb_user(id int NOT NULL AUTO_INCREMENT,userName varchar(20),gender varchar(20),contactNumber varchar(20),gurdianNumber varchar(20),profession varchar(20),password varchar(20),address varchar(500),PRIMARY KEY(id))");
            create.executeUpdate();
            PreparedStatement createmeal=con.prepareStatement("CREATE TABLE IF NOT EXISTS "+userName.getText()+"( date varchar(20),meel varchar(20),PRIMARY KEY(date))");
            createmeal.executeUpdate();
            PreparedStatement createcredit=con.prepareStatement("CREATE TABLE IF NOT EXISTS tb_credit(cName varchar(30),mainCredit int(10),extraAdd int(10),PRIMARY KEY(cName))");
            createcredit.executeUpdate();
            PreparedStatement createMeal=con.prepareStatement("CREATE TABLE IF NOT EXISTS tb_meel(date varchar(10),totalmeal int(10),PRIMARY KEY(date))");
            createMeal.executeUpdate();
            String sql12="CREATE TABLE IF NOT EXISTS tb_today_meel(userName varchar(10),meel int(10),PRIMARY KEY(userName))";
            PreparedStatement tmeel = con.prepareStatement(sql12);
            tmeel.executeUpdate();
            
            
            
            PreparedStatement createAdditionalTable=con.prepareStatement("CREATE TABLE IF NOT EXISTS tb_utility(billType int(10),cost int(10),PRIMARY KEY(billType))");
            createAdditionalTable.executeUpdate();
            try{
            String x1="insert into tb_utility(billType,cost)values('current','0')";
            PreparedStatement x=con.prepareStatement(x1);
            x.executeUpdate();
            String y1="insert into tb_utility(billType,cost)values('gas','0')";
            PreparedStatement y=con.prepareStatement(y1);
            y.executeUpdate();
            String z1="insert into tb_utility(billType,cost)values('others','0')";
            PreparedStatement z=con.prepareStatement(z1);
            z.executeUpdate();
            String a1="insert into tb_utility(billType,cost)values('servent','0')";
            PreparedStatement a=con.prepareStatement(a1);
            a.executeUpdate();
            String b1="insert into tb_utility(billType,cost)values('wifi','0')";
            PreparedStatement b=con.prepareStatement(b1);
            b.executeUpdate();
            }
            catch(Exception e){}
            
            
            //Creating Table
            Connection conn=TableInput.connection();
                String q="insert into tb_user(userName,gender,contactNumber,gurdianNumber,profession,password,address)values(?,?,?,?,?,?,?)";
                String incredit="insert into tb_credit(cName,mainCredit,extraAdd)values(?,'0','0')";
                PreparedStatement insertcredit=conn.prepareStatement(incredit);
                insertcredit.setString(1, userName.getText());
                insertcredit.executeUpdate();
                PreparedStatement create1=conn.prepareStatement(q);
                create1.setString(1,userName.getText());
                String gender;
                if(male.isSelected()){gender="male";}
                else{
                gender="female";
                }
                create1.setString(2, gender);
                create1.setString(3,ContactNumber.getText());
                create1.setString(4,GurdianPhone.getText());
                create1.setString(5,Profession.getText());
                create1.setString(6,UserPassword.getText());
                create1.setString(7,UserAddress.getText());
                create1.executeUpdate();
                alert.setTitle("Successful");
                alert.setContentText("Add Member");
                alert.setHeaderText("Successfully");
                alert.showAndWait();
                
                userName.setText(null);
                ContactNumber.setText(null);
                GurdianPhone.setText(null);
                Profession.setText(null);
                UserPassword.setText(null);
                ConfirmPassword.setText(null);
                UserAddress.setText(null);
            }
            else{
                alert.setTitle("Error");
                alert.setContentText("Password Doesn't Match");
                alert.setHeaderText("Error Found");
                alert.showAndWait();
            }
            // TODO add your handling code here:
        } catch (SQLException ex) {
        }
  
  }
    
    
    
        
    //-----------------------------------------------------Menu methods------------------------------------------------------------------------//
        @FXML
    private AnchorPane adminHome,newMemberPane;   
    
    @FXML
    private void handelClose(ActionEvent event) {
                    System.exit(0);

    }
        @FXML
    private void handelNewUser(ActionEvent event) throws IOException {
            AnchorPane obs=FXMLLoader.load(getClass().getResource("AddMember.fxml"));
            adminHome.getChildren().setAll(obs);

    }
    @FXML
        private void handelAddBazar(ActionEvent event) throws IOException {
            AnchorPane obs=FXMLLoader.load(getClass().getResource("AddBazar.fxml"));
            newMemberPane.getChildren().setAll(obs);

    }
        
        
             @FXML
    private void handelMenuHome(ActionEvent event) throws IOException {
            AnchorPane obs=FXMLLoader.load(getClass().getResource("adminPane.fxml"));
            newMemberPane.getChildren().setAll(obs);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    
}
