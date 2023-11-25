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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sajib
 */
public class UserPaneController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private AnchorPane userPaneView, LoginPane, userPane;
    @FXML
    private TextField uVName;
    @FXML
    private TextField uVContact;

    @FXML
    private PasswordField loginPass;

    @FXML
    private TableView<ModelTable> uVTable;
    @FXML
    private TableColumn<ModelTable, String> col_uDate;
    @FXML
    private TableColumn<ModelTable, String> col_meel;

    /**
     * Initializes the controller class.
     */
    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    public void tableview(String name) throws SQLException, ClassNotFoundException {
        Connection con = TableInput.connection();
        String sql = "Select * from " + name;
        ResultSet rs = con.createStatement().executeQuery(sql);

        while (rs.next()) {

            oblist.add(new ModelTable(rs.getString("date"), rs.getString("meel")));
        }
        // TODO
        col_uDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_meel.setCellValueFactory(new PropertyValueFactory<>("meel"));

        uVTable.setItems(oblist);

    }

    @FXML
    private void handelUpdateButton(ActionEvent event) throws SQLException, ClassNotFoundException {

        LoginPane.setVisible(true);

    }

    @FXML
    private void handelLoginButton(ActionEvent event) throws SQLException, ClassNotFoundException {

        Connection con = TableInput.connection();
        String searchMember = userName.getText();
        String sql = "Select * from tb_user where tb_user.userName='" + searchMember + "'";
        ResultSet rs = con.createStatement().executeQuery(sql);

        String pass = loginPass.getText();
        while (rs.next()) {
            if (pass.equals(rs.getString("password"))) {

                String id = rs.getString("id");
                System.out.println(id);
                String name = uVName.getText();
                String number = uVContact.getText();
                String sql1 = "Update tb_user,tb_credit set tb_user.userName='" + name + "',tb_user.contactNumber='" + number + "' Where  tb_user.id='" + id + "'";
                PreparedStatement create = con.prepareStatement(sql1);
                create.executeUpdate();

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Password Error");
                alert.setContentText("Check Again Your Password");
                alert.setHeaderText("Error");
                alert.showAndWait();
                loginPass.setText(null);

            }
            LoginPane.setVisible(false);

        }
    }

    @FXML
    private void handelSearch(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        Connection con = TableInput.connection();
        String searchMember = userName.getText();
        String sql = "Select * from tb_user where tb_user.userName='" + searchMember + "'";
        ResultSet rs = con.createStatement().executeQuery(sql);

        while (rs.next()) {
            //-----------------------------deleting table content-----------------------------//     
            for (int i = 0; i < uVTable.getItems().size(); i++) {
                uVTable.getItems().clear();
            }

            String id = rs.getString("id");
            String searchNumber = rs.getString("contactNumber");
            userPaneView.setVisible(true);
            uVName.setText(searchMember);
            uVContact.setText(searchNumber);
            tableview(searchMember);
        }

    }

    @FXML
    private void handelLogout(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
        userPane.getChildren().setAll(obs);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userPaneView.setVisible(false);

    }

}
