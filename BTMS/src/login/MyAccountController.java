package login;

import Connection.Oracle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by  on 1/15/2018.
 */
public class MyAccountController {
    @FXML
    private Label infoFullName;

    @FXML
    private Label infoGender;

    @FXML
    private Label infoAddress;

    @FXML
    private Label infoMobile;

    @FXML
    private Label infoEmail;

    @FXML
    private JFXTextField updateFullName;

    @FXML
    private JFXTextField updateGender;

    @FXML
    private JFXTextField updateAddress;

    @FXML
    private JFXTextField updateMobile;

    @FXML
    private JFXTextField updateEmail;

    @FXML
    private JFXButton updateInfoButton;

    @FXML
    private JFXPasswordField updateCurrentPass;

    @FXML
    private JFXPasswordField updatePassCurrentPass;

    @FXML
    private JFXTextField updatePassNewPass;

    @FXML
    private JFXTextField updatePassConfirmPass;

    @FXML
    private JFXButton updatePassButton;
    
    @FXML
    private Label updateInfoLabel;
    
    String pass;
    int userID;
    
    public void setInfo(String name, String gender, String address, String email, String mobile, String password,int userID){
        this.infoFullName.setText(name);
        this.infoGender.setText(gender);
        this.infoAddress.setText(address);
        this.infoEmail.setText(email);
        this.infoMobile.setText(mobile);
        this.pass = password;
        this.userID = userID;
    }

    @FXML
    void updateInfoButtonClicked(ActionEvent event) {
        if(updateCurrentPass.getText().equals(pass)){
            try {
                String upInfo = "update customer set customer_name = ? , gender = ? , address = ? ,mobile_no = ? , email = ? where user_id = ?";
                Connection con = new Oracle().getConnection();
                PreparedStatement pst = con.prepareStatement(upInfo);
                pst.setString(1,updateFullName.getText());
                pst.setString(2,updateGender.getText());
                pst.setString(3,updateAddress.getText());
                pst.setString(4,updateMobile.getText());
                pst.setString(5,updateEmail.getText());
                pst.setInt(6, userID);
                ResultSet rs = pst.executeQuery();
                while(rs.next()){
                    System.out.println("Updated");
                    updateInfoLabel.setText("Your Info Has Been Updated");
                }
            } catch (SQLException ex) {
                //Logger.getLogger(MyAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @FXML
    void updatePassButtonClicked(ActionEvent event) {
        if(updatePassCurrentPass.getText().equals(pass) && (updatePassNewPass.getText().equals(updatePassConfirmPass.getText()))){
            try {
                Connection con = new Oracle().getConnection();
                String qry = "update customer set password = ? where user_id = ?";
                PreparedStatement pst = con.prepareStatement(qry);
                pst.setString(1, updatePassConfirmPass.getText());
                pst.setInt(2, userID);
                pst.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(MyAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
    @FXML
    void back(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = (Parent) loader.load();
        /*Parent root = FXMLLoader.load(getClass().getResource("search.fxml"));*/
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
