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
public class UpdateMeelController implements Initializable {

    @FXML
    private AnchorPane AddMeel;

    @FXML
    private TableView<UpdateMeelTable> tb_Addmeel;

    @FXML
    private TableColumn<UpdateMeelTable, String> col_memberName;

    @FXML
    private TableColumn<UpdateMeelTable, String> col_meelQuantity;

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
        ObservableList<UpdateMeelTable> oblist = FXCollections.observableArrayList();
        Connection con = TableInput.connection();
        String sql = "Select * from tb_today_meel";
        ResultSet rs = con.createStatement().executeQuery(sql);

        while (rs.next()) {

            oblist.add(new UpdateMeelTable(rs.getString("userName"),rs.getString("meel") , new Button("Update")));

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
        UpdateMeelTable ob = new UpdateMeelTable();
        List<List<String>> arrList = new ArrayList<>();
        for (int i = 0; i < tb_Addmeel.getItems().size(); i++) {
            ob = tb_Addmeel.getItems().get(i);
            arrList.add(new ArrayList<>());
            arrList.get(i).add(ob.memberName);
            arrList.get(i).add(ob.todayMeel);

        }
        float sum = 0;

        for (int i = 0; i < arrList.size(); i++) {
            String name = arrList.get(i).get(0);
            String meal = arrList.get(i).get(1);
            Connection con = TableInput.connection();
            System.out.println(name);
            System.out.println(meal);
            System.out.println(currentDate);
            String y="Update tb_today_meel set meel='"+meal+"' where userName='"+name+"'";
            PreparedStatement y1=con.prepareStatement(y);
            y1.executeUpdate();
            String x="Update "+name+" set meel='"+meal+"' where date='"+currentDate+"'";
            PreparedStatement create = con.prepareStatement(x);
            create.executeUpdate();
        }

        for (int i = 0; i < arrList.size(); i++) {
            sum = sum + Float.valueOf(arrList.get(i).get(1));

        }
        //----------------------insert data into table------------------------//
        CreateDb ob1 = new CreateDb();

        //Creating Database.
        ob1.Create("db_mess");
        Connection con = TableInput.connection();
        try {
            System.out.println(currentDate);
            String q="update tb_meel set totalmeal='"+String.valueOf(sum)+"' where date='"+currentDate+"'";
            PreparedStatement create1 = con.prepareStatement(q);
            create1.executeUpdate();
            alert.setTitle("Successful");
            alert.setContentText("Update Meel");
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
