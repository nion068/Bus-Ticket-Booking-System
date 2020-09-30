package login;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by nion on 1/16/2018.
 */
public class ViewDetails {
    SimpleStringProperty seatNo;
    SimpleStringProperty userName;
    SimpleStringProperty contactNo;

    public ViewDetails(String seatNo, String userName, String contactNo){
        this.seatNo = new SimpleStringProperty(seatNo);
        this.userName = new SimpleStringProperty(userName);
        this.contactNo = new SimpleStringProperty(contactNo);
    }

    public String getSeatNo(){
        return seatNo.get();
    }

    public String getUserName(){
        return userName.get();
    }

    public String getContactNo(){
        return contactNo.get();
    }
}
