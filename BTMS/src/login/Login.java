/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Asif
 */
public class Login extends Application {
    
    SplashController splash = new SplashController();
    
    @Override
    public void start(Stage stage) throws Exception {
        //splashScreen();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        splash.SplashController();
        splash.showWindow();
        PauseTransition splashScreenDelay = new PauseTransition(Duration.seconds(3));
        splashScreenDelay.setOnFinished(f->{
            stage.show();
            splash.hideWindow();
        });
        splashScreenDelay.playFromStart();
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        //stage.initStyle(StageStyle.UNDECORATED);
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
