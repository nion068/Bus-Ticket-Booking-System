/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    public void admin(ActionEvent event) throws IOException{
        if(txtAdm.getText().toString().equals("asif") && passAdm.getText().toString().equals("hanif")){
        System.out.println("Login successful.... :-* ");
        /*Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();*/
            Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

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
    public void back(ActionEvent event) throws IOException{
        /*Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/login/FXMLDocument.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show(); */
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
