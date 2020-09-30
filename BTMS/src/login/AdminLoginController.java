/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import Connection.Example;
import Connection.Oracle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Asif
 */
public class AdminLoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private JFXTextField txtAdm;

    @FXML
    private JFXPasswordField passAdm;

    @FXML
    private JFXButton btnSign;

    @FXML
    private JFXTextField txtOperator;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton exitbtn;
    
    @FXML
    private JFXButton btnCreate;
    
    @FXML
    public void admin(ActionEvent event) throws IOException{
        String userName = txtAdm.getText();
        String password = passAdm.getText();
        boolean success = new Example().validateLogin2(userName, password);
        if(success)
        {
            System.out.println("Login successful.... :-* ");
            //Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/Admin.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/login/Admin.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
        
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            AdminController adminController = loader.<AdminController>getController();
            adminController.setOperatorName(userName);
            stage.show();
        }
        else{
            System.out.println("Why ???");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("why did you did this?? you shouldnt have done that..");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void newAccount(ActionEvent event) throws IOException{ 
        Parent root = FXMLLoader.load(getClass().getResource("/login/AdminSignUp.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    
    }
    @FXML
    public void back(ActionEvent event) throws IOException{
//        Stage stage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/login/FXMLDocument.fxml"));
//        stage.initStyle(StageStyle.UNDECORATED);
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.show();  
        
        Parent root = FXMLLoader.load(getClass().getResource("/login/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void exit(){
        System.exit(0);
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
