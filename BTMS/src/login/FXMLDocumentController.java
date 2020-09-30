package login;

import Connection.Example;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable{

    @FXML
    private AnchorPane anchor;

    @FXML
    private JFXButton btnSign;

    @FXML
    private Label lblwar;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton admin;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private JFXButton btnSign2;
    
    
    @FXML
    private JFXButton backbtn;
   
    @FXML
    private JFXButton exitbtn;

    @FXML
    private Hyperlink hyperlink;
    
    @FXML
    private Label labelHype;
    
    @FXML
    void adminLog(ActionEvent event) throws IOException {
//Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/login/AdminLogin.fxml"));
        //stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void login(ActionEvent event) throws IOException {
                
        String userName = txtUser.getText();
        String password = txtPassword.getText();
        boolean success = new Example().validateLogin(userName, password);
        if(success)
        {
            System.out.println("Login successful.... :-* ");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/main.fxml"));
            Parent root = (Parent) loader.load();
            /*Parent root = FXMLLoader.load(getClass().getResource("search.fxml"));*/
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            MainController mainController = loader.<MainController>getController();
            try {
                mainController.setIdandName(userName);
                //searchController.setUserNameId(userName, userId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stage.show();

            //Stage stage = new Stage();
            /*Parent root = FXMLLoader.load(getClass().getResource("/login/main.fxml"));
            Scene scene = new Scene(root);
        
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();*/
        }
        else{
    //        lblwar.setText("INVALID LOGIN");
            System.out.println("Login unsuccessful.. : ");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("The username and password you provided is not correct.");
            alert.showAndWait();
        }
    }

    @FXML
    void reset(ActionEvent event) {
        txtUser.setText(null);
        txtPassword.setText(null);
    }

    @FXML
    void signUp(ActionEvent event) throws IOException {
        //System.out.println("Login successful.... :-* ");
        //Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/login/SignUP.fxml"));
        //stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();

    }
    
    @FXML
    public void hype(ActionEvent event) throws URISyntaxException{
        //getHostServices().showDocuments("mailto:asifbrur005@gmail.com");
        try {
            Desktop.getDesktop().browse(new URL("mailto:asifbrur005@gmail.com").toURI());
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        labelHype.setText("Give Your Email to this Link");
            
    }
    @FXML
    public void exit(){
        System.exit(0);
    }


//    public AnchorPane anchorP;
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
    
//    public void splashScreen() throws IOException
//    {
//        StackPane pane =  FXMLLoader.load(getClass().getResource("Splash.fxml"));
//        anchor.getChildren().setAll(pane);
////        Stage stage = new Stage();
////        Parent root = FXMLLoader.load(getClass().getResource("/login/Splash.fxml"));
////            //stage.initStyle(StageStyle.UNDECORATED);
////        Scene scene = new Scene(root);
////        
////        stage.setScene(scene);
//        FadeTransition fout = new FadeTransition(Duration.seconds(3), pane);
//        fout.setFromValue(1);
//        fout.setToValue(0);
//        fout.setCycleCount(1);
//        
//        fout.play();
//        
//        //stage.show();
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
}
