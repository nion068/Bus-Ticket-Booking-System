package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by nion on 11/30/2017.
 */
public class LoginController {

    private Main main;

    @FXML

    private AnchorPane rootPane;

    @FXML
    private JFXButton adminButton;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    void loginAction(ActionEvent event) throws IOException
    {
        //String validUserName = "admin";
        //String validPassword = "123";
        String userName = userText.getText();
        String password = passwordText.getText();
        boolean success = new Users().validateLogin(userName, password);
        if (success)
        {
            // successful login

            Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

            System.out.println("Log in Successful");

        } else
        {
            // failed login
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("The username and password you provided is not correct.");
            alert.showAndWait();
        }

    }


    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    @FXML
    void signUp(ActionEvent event) throws IOException{
        //Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("SignUP.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();

        //stage.setTitle("Hello World");
        //stage.setScene(new Scene(root, 800, 500));
        //stage.show();
    }
    @FXML
    void adminClicked(ActionEvent event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();

        /*AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        rootPane.getChildren().setAll(pane);*/

    }

}
