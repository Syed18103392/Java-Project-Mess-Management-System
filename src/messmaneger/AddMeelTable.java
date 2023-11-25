/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messmaneger;

import javafx.scene.control.Button;

/**
 *
 * @author sajib
 */
public class AddMeelTable {
    
    String memberName;
    String todayMeel;
    private Button update;
    
    public AddMeelTable(){}
    
    public AddMeelTable(String memberName,String todayMeel,Button update){
    
    this.memberName=memberName;
    this.todayMeel=todayMeel;
    this.update=update;
    
    }

    /**
     * @return the memberName
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * @param memberName the memberName to set
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * @return the todayMeel
     */
    public String getTodayMeel() {
        return todayMeel;
    }

    /**
     * @param todayMeel the todayMeel to set
     */
    public void setTodayMeel(String todayMeel) {
        this.todayMeel = todayMeel;
    }

    /**
     * @return the update
     */
    public Button getUpdate() {
        return update;
    }

    /**
     * @param update the update to set
     */
    public void setUpdate(Button update) {
        this.update = update;
    }
}
 