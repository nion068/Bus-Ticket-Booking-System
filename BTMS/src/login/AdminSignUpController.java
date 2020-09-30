package login;

import Connection.Oracle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdminSignUpController {

    @FXML
    private JFXTextField manager_name;

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXTextField totBus;

    @FXML
    private JFXTextField operator_name;

    @FXML
    private JFXTextField password;

    @FXML
    private JFXTextField confirmPass;

    @FXML
    private JFXButton account;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton exitbtn;
    
    @FXML
    private Label label;

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/login/AdminLogin.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void newAccount(ActionEvent event) throws IOException {
        if(password.getText().equals(confirmPass.getText())){
            int num = Integer.parseInt(contact.getText());
            int bus = Integer.parseInt(totBus.getText());
            createUser(operator_name.getText(), num, bus, password.getText(),manager_name.getText());
//            Parent root = FXMLLoader.load(getClass().getResource("/login/AdminLoginController.fxml"));
//            //stage.initStyle(StageStyle.UNDECORATED);
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//            stage.setScene(scene);
//            stage.show();
            System.out.println("UPdated...");
            label.setText("UPdated...");
           
        }
        else{
            System.out.println("Why ???");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("Password does not match..");
            alert.showAndWait();
        }
            
        }
    
    
    public void createUser(String name,  int mobile,int bus, String password , String man_name){
         try {
             //int user_id = 103;
             Connection connection = new Oracle().getConnection();
             
             String insert = "insert into operator values(operator_id_seq4.nextval, ?, ?, ?, ?)";
             PreparedStatement pst = connection.prepareStatement(insert);
             //pst.setInt(1, user_id);
             pst.setString(1, name);
             pst.setInt(2, mobile);
             pst.setInt(3, bus);
             pst.setString(4, password);
             
             //pst.execute();
             
             String admin = "insert into admins values(admin_id_seq2.nextval, ?, ?, ?, operator_id_seq5.nextval)";
             PreparedStatement pst2 = connection.prepareStatement(admin);
             
             pst2.setString(1, password);
             pst2.setString(2, man_name);
             pst2.setInt(3, mobile);
             
             pst2.executeQuery();
             pst.executeQuery();
             
             //pst.close();
             //pst2.close();
             connection.close();
         } catch (SQLException ex) {
            // Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("Cause: " + ex.getMessage());
            alert.showAndWait();
         }
    }
    }

