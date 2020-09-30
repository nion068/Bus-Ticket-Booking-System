/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Asif
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    /*ObservableList<String>cities = FXCollections.observableArrayList("Dhaka","Rajshahi","Rangpur","Chittagong");*/

    ObservableList<String> citiesFrom = FXCollections.observableArrayList();
    ObservableList<String> citiesTo = FXCollections.observableArrayList();

    @FXML
    private Button btnSrc;
    
    @FXML
    private ChoiceBox comboFrom;
    
    @FXML
    private ChoiceBox comboTo;

    @FXML
    private DatePicker journeyDate;

    /*@FXML
    public void initialize1(){
        //comboFrom.setValue("Dhaka");
        comboFrom.setItems(cities);
    }
    @FXML
    public void initialize2(){
        comboTo.setValue("Dhaka");
        comboTo.setItems(cities);
    }*/
    
    @FXML
    public void search(ActionEvent event) throws IOException
    {
        System.out.println("Search Button Clicked");
        System.out.println(comboFrom.getValue().toString());
        System.out.println(comboTo.getValue().toString());
        System.out.println(journeyDate.getValue().toString());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("search.fxml"));
        Parent root = (Parent) loader.load();
        /*Parent root = FXMLLoader.load(getClass().getResource("search.fxml"));*/
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        SearchController searchController = loader.<SearchController>getController();
        try{
            searchController.setFromNToDate(comboFrom.getValue().toString(), comboTo.getValue().toString(), journeyDate.getValue());
        } catch(SQLException e){
            e.printStackTrace();
        }
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Connection con = new DBMSConnection().getConnection();
        String from = "SELECT DISTINCT STARTING_POINT FROM ROUTE";
        String to = "SELECT DISTINCT DESTINATION FROM ROUTE";

        try{
            PreparedStatement pst = con.prepareStatement(from);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                citiesFrom.add(rs.getString("STARTING_POINT"));
            }
            pst = con.prepareStatement(to);
            rs = pst.executeQuery();
            while (rs.next()){
                citiesTo.add(rs.getString("DESTINATION"));
            }
            con.close();
        } catch(SQLException exception){
            exception.printStackTrace();
        }

        comboFrom.setItems(citiesFrom);
        comboTo.setItems(citiesTo);
    }    
    
}
