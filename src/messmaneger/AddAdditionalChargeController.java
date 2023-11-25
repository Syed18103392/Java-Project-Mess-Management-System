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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sajib
 */
public class AddAdditionalChargeController implements Initializable {

    @FXML
    private AnchorPane additionalCharge;
    @FXML
    private MenuItem addMember;
    @FXML
    private MenuItem addMember1;
    @FXML
    private TextField addCurrentBill;
    @FXML
    private TextField addOthers;
    @FXML
    private TextField addWifiBill;
    @FXML
    private TextField addGasBill, addServentBill;

    /**
     * Initializes the controller class.
     */
    
        @FXML
    private void handelAddCharge(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        Connection con=TableInput.connection();
        String current="Update tb_utility set cost='"+addCurrentBill.getText()+"' where billType='current'";
        String wifi="Update tb_utility set cost='"+addWifiBill.getText()+"' where billType='wifi'";
        String gas="Update tb_utility set cost='"+addGasBill.getText()+"' where billType='gas'";
        String others="Update tb_utility set cost='"+addOthers.getText()+"' where billType='others'";
        String servent="Update tb_utility set cost='"+addServentBill.getText()+"' where billType='servent'";
        PreparedStatement ucurrent=con.prepareStatement(current);
        ucurrent.executeUpdate();
        PreparedStatement uwifi=con.prepareStatement(wifi);
        uwifi.executeUpdate();
        PreparedStatement uothers=con.prepareStatement(others);
        uothers.executeUpdate();
        PreparedStatement uservent=con.prepareStatement(servent);
        uservent.executeUpdate();
        PreparedStatement ugas=con.prepareStatement(gas);
        ugas.executeUpdate();
            System.out.println("Success");
            go();
        
        
    }

    private void go() throws IOException{   
    AnchorPane obs = FXMLLoader.load(getClass().getResource("adminPane.fxml"));
        additionalCharge.getChildren().setAll(obs);    
    }
    
    @FXML
    private void handelMenuHome(ActionEvent event) {
    }

    @FXML
    private void handelClose(ActionEvent event) {
    }

    @FXML
    private void handelNewUser(ActionEvent event) {
    }

    @FXML
    private void handelAddAdditionalCharge(ActionEvent event) {
    }

    @FXML
    private void handelAddMeel(ActionEvent event) {
    }

    @FXML
    private void handelAddBazar(ActionEvent event) {
    }

    @FXML
    private void handelAddCredit(ActionEvent event) {
    }

    @FXML
    private void handelMonthlyReport(ActionEvent event) {
    }
    
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
}
