/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Asif
 */
public class SplashController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Stage stage = new Stage();
    public void SplashController() throws IOException{
       //Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Splash.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("Spalsh.fxml"));
//        loader.setController(this);
//        loader.setRoot(this);
//        
//        loader.load();
//        
//        stage.setTitle("Spalsh..");
//        stage.initStyle(StageStyle.TRANSPARENT);
//        Scene scene = new Scene(loader);
//        stage.setScene(scene);
    }
    
    public void showWindow(){
        stage.show();
    }
    
    public void hideWindow(){
        stage.hide();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
