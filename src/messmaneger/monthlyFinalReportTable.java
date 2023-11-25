/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messmaneger;

/**
 *
 * @author sajib
 */
public class monthlyFinalReportTable {
    String name,tmeel,mrate,utility,tbazar,tcredit,tcost,gtake;

    public monthlyFinalReportTable(String name, String tmeel, String mrate, String utility, String tbazar, String tcredit, String tcost, String gtake) {
        this.name = name;
        this.tmeel = tmeel;
        this.mrate = mrate;
        this.utility = utility;
        this.tbazar = tbazar;
        this.tcredit = tcredit;
        this.tcost = tcost;
        this.gtake = gtake;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTmeel() {
        return tmeel;
    }

    public void setTmeel(String tmeel) {
        this.tmeel = tmeel;
    }

    public String getMrate() {
        return mrate;
    }

    public void setMrate(String mrate) {
        this.mrate = mrate;
    }

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public String getTbazar() {
        return tbazar;
    }

    public void setTbazar(String tbazar) {
        this.tbazar = tbazar;
    }

    public String getTcredit() {
        return tcredit;
    }

    public void setTcredit(String tcredit) {
        this.tcredit = tcredit;
    }

    public String getTcost() {
        return tcost;
    }

    public void setTcost(String tcost) {
        this.tcost = tcost;
    }

    public String getGtake() {
        return gtake;
    }

    public void setGtake(String gtake) {
        this.gtake = gtake;
    }


     
}
