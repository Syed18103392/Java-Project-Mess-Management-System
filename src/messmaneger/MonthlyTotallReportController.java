/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messmaneger;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author sajib
 */
public class MonthlyTotallReportController implements Initializable {

    @FXML
    private AnchorPane monthlyTotallReportPane;
    @FXML
    private AnchorPane monthlyReportTablePane;
    @FXML
    private Label MonthAndYear;
    @FXML
    private TableView<monthlyFinalReportTable> table_totallReport;
    @FXML
    private TableColumn<monthlyFinalReportTable, String> col_name;
    @FXML
    private TableColumn<monthlyFinalReportTable, String> col_TMeel;
    @FXML
    private TableColumn<monthlyFinalReportTable, String> col_MRate;
    @FXML
    private TableColumn<monthlyFinalReportTable, String> col_AdditionalCharge;
    @FXML
    private TableColumn<monthlyFinalReportTable, String> col_TBazar;
    @FXML
    private TableColumn<monthlyFinalReportTable, String> col_TCost;
    @FXML
    private TableColumn<monthlyFinalReportTable, String> col_TCredit;
    @FXML
    private TableColumn<monthlyFinalReportTable, String> col_GTake;
    @FXML
    private TableColumn<monthlyFinalReportTable, String> col_status;

    /**
     * Initializes the controller class.
     */
    ObservableList<monthlyFinalReportTable> oblist = FXCollections.observableArrayList();
//    String name,tMeel,mRate,utility,tBazar,tCredit,tCost,gTake;

    public String utotallcredit(String name) throws SQLException, ClassNotFoundException {
        Connection con = TableInput.connection();
        String tcredit = null;
        String x = "SELECT * from tb_credit where cName='"+name+"'";
        ResultSet x1 = con.createStatement().executeQuery(x);
        while (x1.next()) {
            tcredit = x1.getString("mainCredit");

        }
        return tcredit;

    }

    public String umeelcalculation(String name) throws SQLException, ClassNotFoundException {
        Connection con = TableInput.connection();
        String tMeel = null;
        String x = "SELECT SUM(meel) AS Totalmeel FROM " + name;
        ResultSet x1 = con.createStatement().executeQuery(x);
        while (x1.next()) {
            tMeel = x1.getString("Totalmeel");

        }
        return tMeel;

    }

    public double CalculateMealRate() {

        double[] num = new double[3];
        double sum = 0;
        try {
            double totallcost;
            Connection con = TableInput.connection();
            String x = "SELECT SUM(totalmeal) AS Totalmeel FROM tb_meel";
            ResultSet r = con.createStatement().executeQuery(x);

            while (r.next()) {
                num[0] = Double.valueOf(r.getString("Totalmeel"));
            }
            String y = "SELECT SUM(totalCost) AS Totalc FROM tb_bazar";
            ResultSet a = con.createStatement().executeQuery(y);

            while (a.next()) {
                num[1] = Float.valueOf(a.getString("Totalc"));

            }
            sum = num[1] / num[0];
            String sum1 = new DecimalFormat("##.##").format(sum);
            System.out.println(sum);

        } catch (Exception e) {
        };
        return sum;
    }

    public double utilitycalculation() throws SQLException, ClassNotFoundException {
        double utilitycalculate = 0;
        Connection con = TableInput.connection();
        String sql = "SELECT SUM(cost) AS Totalutility FROM tb_utility";
        String sql1 = "SELECT * FROM tb_user";
        ResultSet r = con.createStatement().executeQuery(sql1);
        int count = 0;
        while (r.next()) {
            count = count + 1;
        }
        System.out.println(count);

        ResultSet rs = con.createStatement().executeQuery(sql);
        double totallUtility;
        while (rs.next()) {

            totallUtility = Double.valueOf(rs.getString("Totalutility"));
            utilitycalculate = totallUtility / count;

        }
        return utilitycalculate;
    }
public String bazar() throws SQLException, ClassNotFoundException{
Connection con = TableInput.connection();
String tbazar=null;
        String sql = "Select Sum(totalCost) As tbazar from tb_bazar";
        ResultSet rs = con.createStatement().executeQuery(sql);
        while(rs.next())
        {
        tbazar=rs.getString("tbazar");
        }
return tbazar;
}
    public void loadData() throws SQLException, ClassNotFoundException {
        Connection con = TableInput.connection();
        String sql = "Select * from tb_user";
        ResultSet rs = con.createStatement().executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("userName");
            String tMeel = umeelcalculation(name);
            double meel_rate = CalculateMealRate();
            double utility = utilitycalculation();
            double total_cost = (meel_rate * Double.valueOf(tMeel)) + utility;
            double total_credit=Double.valueOf(utotallcredit(name));
            double total_remaining=total_credit-total_cost;
            String totalBazar=bazar();
            //monthlyFinalReportTable(String name, String tMeel, String tBazar, double mRate, double utility, double tCredit, double tCost, double gTake)
            //oblist.add(new monthlyFinalReportTable(name,tMeel,totalBazar,meel_rate,utility,total_credit,total_cost,total_remaining));
            //string name, String tMeel, String mRate, String utility, String tBazar, String tCredit, String tCost, String gTake
            oblist.add(new monthlyFinalReportTable(name,tMeel,String.valueOf(meel_rate),String.valueOf(utility),totalBazar,String.valueOf(total_credit),String.valueOf(total_cost),String.valueOf(total_remaining)));
            //oblist.add(new monthlyFinalReportTable("a","b","c","d","e","f","d","a"));
        }
            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_TMeel.setCellValueFactory(new PropertyValueFactory<>("tmeel"));
            col_MRate.setCellValueFactory(new PropertyValueFactory<>("mrate"));
            col_AdditionalCharge.setCellValueFactory(new PropertyValueFactory<>("utility"));
            col_TBazar.setCellValueFactory(new PropertyValueFactory<>("tbazar"));
            col_TCredit.setCellValueFactory(new PropertyValueFactory<>("tcredit"));
            col_TCost.setCellValueFactory(new PropertyValueFactory<>("tcost"));
            col_GTake.setCellValueFactory(new PropertyValueFactory<>("gtake"));
            table_totallReport.setItems(oblist);
           
            
            
    }

    @FXML
    private void handelPrint(ActionEvent event) throws IOException {

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPageSetupDialog(monthlyReportTablePane.getScene().getWindow())) {
            boolean success = job.printPage(monthlyReportTablePane);
            if (success) {
                job.endJob();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(MonthlyTotallReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MonthlyTotallReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
