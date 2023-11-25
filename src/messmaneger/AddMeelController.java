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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sajib
 */
public class AddMeelController implements Initializable {

    @FXML
    private AnchorPane AddMeel;

    @FXML
    private TableView<AddMeelTable> tb_Addmeel;

    @FXML
    private TableColumn<AddMeelTable, String> col_memberName;

    @FXML
    private TableColumn<AddMeelTable, String> col_meelQuantity;

    /**
     * Initializes the controller class.
     */
    private void inttable() throws SQLException, ClassNotFoundException {
        intcol();
    }

    private void intcol() {
        col_memberName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        col_meelQuantity.setCellValueFactory(new PropertyValueFactory<>("todayMeel"));

        editableCol();
    }

    private void editableCol() {
        col_memberName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_memberName.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setMemberName(e.getNewValue());
        });
        col_meelQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
        col_meelQuantity.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTodayMeel(e.getNewValue());
        });
        tb_Addmeel.setEditable(true);
    }

    private void loadData() throws SQLException, ClassNotFoundException {
        ObservableList<AddMeelTable> oblist = FXCollections.observableArrayList();
        Connection con = TableInput.connection();
        String sql = "Select * from tb_user";
        ResultSet rs = con.createStatement().executeQuery(sql);

        while (rs.next()) {

            oblist.add(new AddMeelTable(rs.getString("userName"), "0", new Button("Update")));

        }
        // TODO
        tb_Addmeel.setItems(oblist);

    }

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private void handleAddMeel(ActionEvent event) throws SQLException, ClassNotFoundException  {
        
        Date todaysDate = new Date();
        DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = df1.format(todaysDate);
//------------------------date part---------------//
        AddMeelTable ob = new AddMeelTable();
        List<List<String>> arrList = new ArrayList<>();
        for (int i = 0; i < tb_Addmeel.getItems().size(); i++) {
            ob = tb_Addmeel.getItems().get(i);
            arrList.add(new ArrayList<>());
            arrList.get(i).add(ob.memberName);
            arrList.get(i).add(ob.todayMeel);

        }
        float sum = 0;

//        for (int i = 0; i < arrList.size(); i++) {
//            String name = arrList.get(i).get(0);
//            String meal = arrList.get(i).get(1);
//            Connection con = TableInput.connection();
//            String sql="CREATE TABLE IF NOT EXISTS tb_today_meel(userName varchar(10),meel int(10),PRIMARY KEY(userName))";
//            PreparedStatement tmeel = con.prepareStatement(sql);
//            tmeel.executeUpdate();
//            String y="insert into tb_today_meel(userName,meel)values(?,?)";
//            PreparedStatement tMeel = con.prepareStatement(y);
//            tMeel.setString(1, name);
//            tMeel.setString(2, meal);
//            tMeel.executeUpdate();
//            String x = "insert into " + name + "(date,meel)values(?,?)";
//            PreparedStatement create = con.prepareStatement(x);
//            create.setString(1, currentDate);
//            create.setString(2, meal);
//            create.executeUpdate();
//        }

        for (int i = 0; i < arrList.size(); i++) {
            sum = sum + Float.valueOf(arrList.get(i).get(1));

        }
        //----------------------insert data into table------------------------//
        CreateDb ob1 = new CreateDb();

        //Creating Database.
        ob1.Create("db_mess");
        Connection con = TableInput.connection();
        try {
            String q = "insert into tb_meel(date,totalmeal)values(?,?)";
            PreparedStatement create1 = con.prepareStatement(q);
            create1.setString(1, currentDate);
            create1.setString(2, String.valueOf(sum));
            create1.executeUpdate();
            get();
            for (int i = 0; i < arrList.size(); i++) {
            String name = arrList.get(i).get(0);
            String meal = arrList.get(i).get(1);
            String sql="CREATE TABLE IF NOT EXISTS tb_today_meel(userName varchar(10),meel int(10),PRIMARY KEY(userName))";
            PreparedStatement tmeel = con.prepareStatement(sql);
            tmeel.executeUpdate();
            String y="insert into tb_today_meel(userName,meel)values(?,?)";
            PreparedStatement tMeel = con.prepareStatement(y);
                System.out.println(name);
                System.out.println(meal);
            tMeel.setString(1, name);
            tMeel.setString(2, meal);
            tMeel.executeUpdate();
            String x = "insert into " + name + "(date,meel)values(?,?)";
            PreparedStatement create = con.prepareStatement(x);
            create.setString(1, currentDate);
            create.setString(2, meal);
            create.executeUpdate();
        }
            
     
            alert.setTitle("Successful");
            alert.setContentText("Add Meel");
            alert.setHeaderText("Successfully");
            alert.showAndWait();
            go();
        } catch (Exception e) {
            alert.setTitle("Error");
            alert.setContentText("Data Already Updated Please Go view Page If u want to Update");
            alert.setHeaderText("Error AddMeal");
            alert.showAndWait();

        }
    }

    public void go() throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("adminPane.fxml"));
        AddMeel.getChildren().setAll(obs);

    }
    public void get() throws SQLException, ClassNotFoundException{
    Connection con=TableInput.connection();
    String sql="Drop Table tb_today_meel";
            PreparedStatement create = con.prepareStatement(sql);
            create.executeUpdate();
    
    
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            inttable();
        } catch (SQLException ex) {
            Logger.getLogger(AddMeelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddMeelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(AddMeelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddMeelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
