/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messmaneger;

import java.awt.Color;
import static java.awt.Color.red;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author sajib
 */
public class MonthlyReportController implements Initializable {

    @FXML
    private TextField sMember;
    @FXML
    private GridPane reportPane;
    @FXML
    private Label rName;
    @FXML
    private Label rCCost;
    @FXML
    private Label rTCost;
    @FXML
    private Label rMRate;
    @FXML
    private Label rtMeel;
    @FXML
    private Label rTCredit;
    @FXML
    private Label rRBalance;
    @FXML
    private Button printButton;
    @FXML
    private AnchorPane xpane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CalculateMealRate();
        try {
            double y = utilitycalculation();
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(MonthlyReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MonthlyReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    Alert alert = new Alert(Alert.AlertType.ERROR);

    
    
    public double tcostcalculation(){
            double[] num=new double[3];
            double sum=0;
            double totallcost=0;
            try{
            
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
                String searchMember = sMember.getText();
    String sql1 = "SELECT SUM(meel) AS Totalmeel FROM " + searchMember;
    int totallmeal;
    
    ResultSet rs1 = con.createStatement().executeQuery(sql1);
                while (rs1.next()) {
                 totallmeal=Integer.valueOf(rs1.getString("Totalmeel"));
                 totallcost=totallmeal*sum;
                 rTCost.setText(String.valueOf(totallcost));
                 
            };
            
        }
        catch(Exception e){};
    
    return totallcost;
    
    }
    @FXML
    private void handelSearch(ActionEvent event) throws SQLException, ClassNotFoundException {
        try{
            Connection con = TableInput.connection();
            String searchMember = sMember.getText();
            String sql = "Select * from tb_user,tb_credit where tb_user.userName=tb_credit.cName and tb_user.userName='" + searchMember + "'";
            String sql1 = "SELECT SUM(meel) AS Totalmeel FROM " + searchMember;
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {

                String searchCredit = rs.getString("mainCredit");
                calculateremaining(searchCredit);
                reportPane.setVisible(true);
                printButton.setVisible(true);
                rName.setText(searchMember);

                rTCredit.setText(searchCredit);
            }
            ResultSet rs1 = con.createStatement().executeQuery(sql1);
            while (rs1.next()) {
                rtMeel.setText(rs1.getString("Totalmeel"));
            };
            double x=tcostcalculation();
            System.out.println(x);
            
        } catch (Exception e) {

            alert.setTitle("Search Error");
            alert.setContentText("No Member Found");
            alert.setHeaderText("Error");
            alert.showAndWait();
    }
        
    
    }
    
    
    
    public double utilitycalculation() throws SQLException, ClassNotFoundException{
        double utilitycalculate=0;
        Connection con=TableInput.connection();
        String sql="SELECT SUM(cost) AS Totalutility FROM tb_utility";
        String sql1="SELECT * FROM tb_user";
        ResultSet r = con.createStatement().executeQuery(sql1);
        int count=0;
        while(r.next())
        {count=count+1;}
        System.out.println(count);
        
        
        ResultSet rs = con.createStatement().executeQuery(sql);
        double totallUtility;
        while(rs.next()){
        
        totallUtility= Double.valueOf(rs.getString("Totalutility"));
        utilitycalculate=totallUtility/count;
        rCCost.setText(String.valueOf(utilitycalculate));
        
        }
       return utilitycalculate; 
    }
    
    
    public void calculateremaining(String x) throws SQLException, ClassNotFoundException{
    
        double utilitycalculation = utilitycalculation();
        double tcostcalculation = tcostcalculation();
    double mainCredit=Double.valueOf(x);
    double remaining=mainCredit-(utilitycalculation+tcostcalculation);
    rRBalance.setText(String.valueOf(remaining));
    if(remaining>=0){rRBalance.setTextFill(Paint.valueOf("green"));}
    else{rRBalance.setTextFill(Paint.valueOf("red"));}
    }
    
    
        @FXML
    private void handelPrint(ActionEvent event) throws IOException { 
        
        
        
 PrinterJob job = PrinterJob.createPrinterJob();
if (job != null && job.showPageSetupDialog(reportPane.getScene().getWindow())){
    boolean success = job.printPage(reportPane);
    if (success) {
        job.endJob();
    }
}

    }
    
    
        public void CalculateMealRate() 
    {
        try{
            double[] num=new double[3];
            double sum=0;
            double totallcost;
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
    rMRate.setText(sum1);
                String searchMember = sMember.getText();
    String sql1 = "SELECT SUM(meel) AS Totalmeel FROM " + searchMember;
    int totallmeal;
    
    ResultSet rs1 = con.createStatement().executeQuery(sql1);
                while (rs1.next()) {
                 totallmeal=Integer.valueOf(rs1.getString("Totalmeel"));
                 totallcost=totallmeal*sum;
                 rTCost.setText(String.valueOf(totallcost));
            };
            
        }
        catch(Exception e){};
    }
    
}
