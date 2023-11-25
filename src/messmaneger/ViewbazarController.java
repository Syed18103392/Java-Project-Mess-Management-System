/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messmaneger;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sajib
 */
public class ViewbazarController implements Initializable {

    @FXML
    private TableView<viewbazartableinput> table_bazar;
    @FXML
    private TableColumn<viewbazartableinput, String> col_date;
    @FXML
    private TableColumn<viewbazartableinput, String> col_time;
    @FXML
    private TableColumn<viewbazartableinput, String> col_name;
    @FXML
    private TableColumn<viewbazartableinput, String> col_list;
    @FXML
    private TableColumn<viewbazartableinput, String> col_cost;
    @FXML
    private MenuItem addMember;
    @FXML
    private MenuItem addMember1;

    /**
     * Initializes the controller class.
     */
    
    ObservableList<viewbazartableinput> oblist=FXCollections.observableArrayList(); 
    @FXML
    private AnchorPane bazarView;
    
    
        public void tableview() throws SQLException, ClassNotFoundException{
    Connection con=TableInput.connection();
            String sql="Select * from tb_bazar";
            ResultSet rs=con.createStatement().executeQuery(sql);

            while(rs.next()){

            oblist.add(new viewbazartableinput(rs.getString("bazarDate"),rs.getString("bazarTime"),rs.getString("bazarNameId"),rs.getString("bazarList"),rs.getString("totalCost")));
            }
            // TODO
            // date,time,name,list,cost;
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_list.setCellValueFactory(new PropertyValueFactory<>("list"));
            col_cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table_bazar.setItems(oblist);
    
    
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            tableview();
        } catch (SQLException ex) {
            Logger.getLogger(ViewbazarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewbazarController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    @FXML
    private void handelAbout(ActionEvent event) {
    }
    
}
