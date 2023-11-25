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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sajib
 */
public class AddBazarController implements Initializable {

    @FXML
    private AnchorPane enter_bazar;
    @FXML
    private TextField bazar_name_id;
    @FXML
    private Label current_date;
    @FXML
    private TextArea bazar_list;
    @FXML
    private TextField bazar_cost;
    @FXML
    private TextField bazar_paid;
    @FXML
    private MenuItem addMember;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handelbazar(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        String bazarId = bazar_name_id.getText();
        String bazarList = bazar_list.getText();
        int totalCost = Integer.valueOf(bazar_cost.getText());
        int totalPaid = Integer.valueOf(bazar_paid.getText());
        int extraAdd = totalCost - totalPaid;

        Connection con = TableInput.connection();
        //Creating Table
        String spl = "CREATE TABLE IF NOT EXISTS tb_bazar(id int NOT NULL AUTO_INCREMENT,bazarDate varchar(5),bazarTime varchar(30),bazarNameId varchar(20),bazarList varchar(400),totalCost varchar(5),totalPaid varchar(5),extraAdd varchar(20),PRIMARY KEY(id))";
        PreparedStatement create = con.prepareStatement(spl);
        create.executeUpdate();
        String q = "insert into tb_bazar(bazarDate,bazarTime,bazarNameId,bazarList,totalCost,totalPaid,extraAdd)values(?,?,?,?,?,?,?)";
        PreparedStatement create1 = con.prepareStatement(q);
        create1.setString(1, currentDate);
        create1.setString(2, currentTime);
        create1.setString(3, bazarId);
        create1.setString(4, bazarList);
        create1.setString(5, bazar_cost.getText());
        create1.setString(6, bazar_paid.getText());
        create1.setString(7, String.valueOf(extraAdd));
        create1.executeUpdate();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Successfull");
        alert.setContentText("Add Bazar");
        alert.setHeaderText(" ");
        alert.showAndWait();
        bazar_name_id.setText(null);
        bazar_list.setText(null);
        bazar_cost.setText(null);
        bazar_paid.setText(null);
        go();

    }
    
        public void go() throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("adminPane.fxml"));
        addBazarPane.getChildren().setAll(obs);

    }

    //newUser----------------------------------
    Date todaysDate = new Date();
    DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
    DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
    DateFormat df2 = new SimpleDateFormat("hh:mm aa");
    String str5 = df.format(todaysDate);
    String currentDate = df1.format(todaysDate);
    String currentTime = df2.format(todaysDate);

    //-----------------------------------------------------Menu methods------------------------------------------------------------------------//
    @FXML
    private AnchorPane adminHome, addBazarPane;

    @FXML
    private void handelClose(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    private void handelNewUser(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("AddMember.fxml"));
        addBazarPane.getChildren().setAll(obs);

    }

    @FXML
    private void handelAddBazar(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("AddBazar.fxml"));
        addBazarPane.getChildren().setAll(obs);

    }

    @FXML
    private void handelAddCredit(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("AddCredit.fxml"));
        addBazarPane.getChildren().setAll(obs);
    }

    @FXML
    private void handelMenuHome(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("adminPane.fxml"));
        addBazarPane.getChildren().setAll(obs);
    }

}
