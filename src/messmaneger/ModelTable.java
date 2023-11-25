/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messmaneger;

/**
 *
 * @author syed
 */
public class ModelTable {

    private String date;
    private String meel;

    public ModelTable(String date, String meel) {
        this.date = date;
        this.meel = meel;

    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the meel
     */
    public String getMeel() {
        return meel;
    }

    /**
     * @param meel the meel to set
     */
    public void setMeel(String meel) {
        this.meel = meel;
    }



    
}
