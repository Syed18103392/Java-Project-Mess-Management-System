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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sajib
 */
public class AddCreditController implements Initializable {

    @FXML
    private AnchorPane credit_panel;
    @FXML
    private TextField credit_id;
    @FXML
    private TextField credit_amount;

    /**
     * Initializes the controller class.
     */
         Alert alert = new Alert(Alert.AlertType.INFORMATION);

        @FXML
    private void handelAddCredit(ActionEvent event) throws SQLException, ClassNotFoundException {
        
            Connection con=TableInput.connection();
            int creditAmount=Integer.valueOf(credit_amount.getText());
            String creditId=credit_id.getText();
            System.out.println(creditId);
            String spl="UPDATE tb_credit set mainCredit="+creditAmount+" where cName='"+creditId+"'";
            PreparedStatement create=con.prepareStatement(spl);
            create.executeUpdate();
            alert.setTitle("Error");
                alert.setContentText("Adding Credit Successfully");
                alert.setHeaderText("Successfull!");
                alert.showAndWait();
  
        }
    
        //-----------------------------------------------------Menu methods------------------------------------------------------------------------//
        @FXML
    private AnchorPane addCreditPane;   
    
    @FXML
    private void handelClose(ActionEvent event) {
                    System.exit(0);

    }
        @FXML
    private void handelNewUser(ActionEvent event) throws IOException {
            AnchorPane obs=FXMLLoader.load(getClass().getResource("AddMember.fxml"));
            addCreditPane.getChildren().setAll(obs);

    }
    @FXML
    private void handelAddBazar(ActionEvent event) throws IOException {
            AnchorPane obs=FXMLLoader.load(getClass().getResource("AddBazar.fxml"));
            addCreditPane.getChildren().setAll(obs);

    }
    private void handelMenuAddCredit(ActionEvent event) throws IOException {
            AnchorPane obs=FXMLLoader.load(getClass().getResource("AddCredit.fxml"));
            addCreditPane.getChildren().setAll(obs);
    }
    
         @FXML
    private void handelMenuHome(ActionEvent event) throws IOException {
            AnchorPane obs=FXMLLoader.load(getClass().getResource("adminPane.fxml"));
            addCreditPane.getChildren().setAll(obs);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


}
