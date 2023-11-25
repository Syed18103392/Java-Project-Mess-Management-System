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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sajib
 */
public class AdminPaneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField sMember, uName, uNumber, uCredit;
    @FXML
    private TextArea uAddress;
    @FXML
    private TitledPane sResult;
    @FXML
    private Label sName, sNumber, sMeal, sCredit, memberId,cMeelRate,cTotallMeel,cMeelBazar,cTodayMeel;

    @FXML
    private AnchorPane updatePane;
    Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    private void handelSearch(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            Connection con = TableInput.connection();
            String searchMember = sMember.getText();
            String sql = "Select * from tb_user,tb_credit where tb_user.userName=tb_credit.cName and tb_user.userName='" + searchMember + "'";
            String sql1 = "SELECT SUM(meel) AS Totalmeel FROM " + searchMember;

            ResultSet rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {
                String searchNumber = rs.getString("contactNumber");

                String searchCredit = rs.getString("mainCredit");
                sResult.setVisible(true);
                sName.setText(searchMember);
                sNumber.setText(searchNumber);

                sCredit.setText(searchCredit);
            }
            ResultSet rs1 = con.createStatement().executeQuery(sql1);
            while (rs1.next()) {
                sMeal.setText(rs1.getString("Totalmeel"));
            };
        } catch (Exception e) {

            alert.setTitle("Search Error");
            alert.setContentText("No Member Found");
            alert.setHeaderText("Error");
            alert.showAndWait();
        }
        
    }
    
    
    public void CalculateMealRate() 
    {
        try{
            double[] num=new double[3];
            double sum=0;
    Connection con = TableInput.connection();
    String x = "SELECT SUM(totalmeal) AS Totalmeel FROM tb_meel";
                ResultSet r = con.createStatement().executeQuery(x);
            
                while (r.next()) {
                    num[0]= Double.valueOf(r.getString("Totalmeel"));
            }
                String y = "SELECT SUM(totalCost) AS Totalc FROM tb_bazar";
                ResultSet a= con.createStatement().executeQuery(y);
            
                while (a.next()) {
                    num[1]=Float.valueOf(a.getString("Totalc"));
                    
            }
            sum=num[1]/num[0];
            String sum1=new DecimalFormat("##.##").format(sum);
            System.out.println(sum);
    cMeelRate.setText(sum1);
        }
        catch(Exception e){};
    }


    public void CalculateMeal() 
    {
        try{
            double[] num=new double[3];
            double sum=0;
    Connection con = TableInput.connection();
    String x = "SELECT SUM(totalmeal) AS Totalmeel FROM tb_meel";
                ResultSet r = con.createStatement().executeQuery(x);
            
                while (r.next()) {
                    num[0]= Double.valueOf(r.getString("Totalmeel"));
            }
                String y = "SELECT SUM(totalCost) AS Totalc FROM tb_bazar";
                ResultSet a= con.createStatement().executeQuery(y);
            
                while (a.next()) {
                    num[1]=Float.valueOf(a.getString("Totalc"));
                    
            }
            cTotallMeel.setText(String.valueOf(num[0]));
            cMeelBazar.setText(String.valueOf(num[1]));
        }
        catch(Exception e){};
    }
    
    
        public void CalculateBazar() 
    {
        try{
            double[] num=new double[3];
            double sum=0;
    Connection con = TableInput.connection();
            
                String y = "SELECT SUM(totalCost) AS Totalc FROM tb_bazar";
                ResultSet a= con.createStatement().executeQuery(y);
            
                while (a.next()) {
                    num[1]=Float.valueOf(a.getString("Totalc"));
                    
            }
            cMeelBazar.setText(String.valueOf(num[1]));
        }
        catch(Exception e){};
    }
    
        public void tmeel() 
    {
        try{
            Date todaysDate = new Date();
            String[] numa=new String[1];
        DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = df1.format(todaysDate);
        Connection con = TableInput.connection();
        String x = "SELECT *  FROM tb_meel where date='"+currentDate+"'";
                ResultSet r = con.createStatement().executeQuery(x);
            
                while (r.next()) {
                    numa[0]=r.getString("totalmeal");
                    
            }
                cTodayMeel.setText(numa[0]);
        }
        catch(Exception e){};
    }
    
    
        

    @FXML
    private void handelUpdateMember(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        Connection con = TableInput.connection();
        String searchMember = sMember.getText();
        String sql = "Select * from tb_user,tb_credit where tb_user.userName=tb_credit.cName and tb_user.userName='" + searchMember + "'";
        ResultSet rs = con.createStatement().executeQuery(sql);

        while (rs.next()) {
            String id = rs.getString("id");
            String searchNumber = rs.getString("contactNumber");
            String searchAddress = rs.getString("address");

            String searchCredit = rs.getString("mainCredit");
            updatePane.setVisible(true);
            sResult.setVisible(false);
            memberId.setText(id);
            uName.setText(searchMember);
            uNumber.setText(searchNumber);
            uAddress.setText(searchAddress);
            uCredit.setText(searchCredit);
        }

    }

    @FXML
    private void UpdateInfo(ActionEvent event) throws SQLException, ClassNotFoundException {
        Connection con = TableInput.connection();
        String id = memberId.getText();
        String name = uName.getText();
        String credit = uCredit.getText();
        String number = uNumber.getText();
        String address = uAddress.getText();
        String sql = "Update tb_user,tb_credit set tb_user.userName='" + name + "',tb_user.contactNumber='" + number + "',tb_user.address='" + address + "',tb_credit.mainCredit=" + credit + " Where tb_user.userName=tb_credit.cName and tb_user.id='" + id + "'";
        PreparedStatement create = con.prepareStatement(sql);
        create.executeUpdate();

        updatePane.setVisible(false);
        sMember.setText(name);

    }

    @FXML
    private void handelMemberDelete(ActionEvent event) throws SQLException, ClassNotFoundException {
        Connection con = TableInput.connection();
        String name = sMember.getText();
        String sql = "Select * from tb_user,tb_credit where tb_user.userName=tb_credit.cName and tb_user.userName='" + name + "'";
        ResultSet rs = con.createStatement().executeQuery(sql);
        String id;

        while (rs.next()) {
            id = rs.getString("userName");
            String searchNumber = rs.getString("contactNumber");
            String searchAddress = rs.getString("address");

            String searchCredit = rs.getString("mainCredit");

            String sql1 = "DROP TABLE " + name;
            String sql2 = "Delete from tb_user Where tb_user.userName='" + name + "'";
            String sql12 = "Delete from tb_credit Where cName='" + name + "' ";
            PreparedStatement create = con.prepareStatement(sql2);
            create.executeUpdate();
            PreparedStatement create1 = con.prepareStatement(sql1);
            create1.executeUpdate();
            PreparedStatement create2 = con.prepareStatement(sql12);
            create2.executeUpdate();
        }
        sResult.setVisible(false);
        sMember.setText(name);

    }

    //-----------------------------------------------------Menu methods------------------------------------------------------------------------//
    @FXML
    private AnchorPane adminHome;

    @FXML
    private void handelClose(ActionEvent event) {
        System.exit(0);

    }
    @FXML
    private void handelAddAdditionalCharge(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("AddAdditionalCharge.fxml"));
        adminHome.getChildren().setAll(obs);

    }
    @FXML
    private void handelNewUser(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("AddMember.fxml"));
        adminHome.getChildren().setAll(obs);

    }

    @FXML
    private void handelAddMeel(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("AddMeel.fxml"));
        adminHome.getChildren().setAll(obs);

    }

    @FXML
    private void handelAddBazar(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("AddBazar.fxml"));
        adminHome.getChildren().setAll(obs);

    }

    @FXML
    private void handelAddCredit(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("AddCredit.fxml"));
        adminHome.getChildren().setAll(obs);
    }
    @FXML
    private void handelMonthlyTotallReport(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("monthlyTotallReport.fxml"));
        adminHome.getChildren().setAll(obs);
    }
    @FXML
    private void handelMenuHome(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("adminPane.fxml"));
        adminHome.getChildren().setAll(obs);
    }
        @FXML
    private void handelMonthlyReport(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("monthlyReport.fxml"));
        adminHome.getChildren().setAll(obs);
    }
            @FXML
    private void handelAbout(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("About page.fxml"));
        adminHome.getChildren().setAll(obs);
    }
            @FXML
    private void handelViewBazar(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("viewbazar.fxml"));
        adminHome.getChildren().setAll(obs);
    }
    @FXML
    private void handelEditMeel(ActionEvent event) throws IOException {
        AnchorPane obs = FXMLLoader.load(getClass().getResource("UpdateMeel.fxml"));
        adminHome.getChildren().setAll(obs);
    }     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    CalculateMealRate();
    CalculateBazar();
    CalculateMeal();
    tmeel();
    }

}
