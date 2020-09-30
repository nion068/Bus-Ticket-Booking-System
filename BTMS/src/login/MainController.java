/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import Connection.Oracle;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Asif
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    ObservableList<String>citiesFrom = FXCollections.observableArrayList();
    
    ObservableList<String>citiesTo = FXCollections.observableArrayList();
    
    @FXML
    private JFXButton btnSrc;
    
    @FXML
    private ChoiceBox comboFrom;
    
    @FXML
    private ChoiceBox comboTo;

    @FXML
    private DatePicker journeyDate;
    
    @FXML
    private JFXButton back;
    
    @FXML
    private JFXButton exit;
    
    @FXML
    private Label lbluserName;
    
    @FXML
    private JFXButton updateCustomer;
    
    @FXML
    private JFXButton feedback;

//    @FXML
//    public void initialize1(){
//        comboFrom.setValue("Dhaka");
//        comboFrom.setItems(cities);
//    }
//    @FXML
//    public void initialize2(){
//        comboTo.setValue("Dhaka");
//        comboTo.setItems(cities);
//    }
    
    public void initialize(URL url, ResourceBundle rb) {
    // TODO
    Connection con = new Oracle().getConnection();
    String from = "SELECT DISTINCT STARTING_POINT FROM ROUTE";
    String to = "SELECT DISTINCT DESTINATION FROM ROUTE";

    try{
        PreparedStatement pst = con.prepareStatement(from);
        ResultSet rs = pst.executeQuery();
        while (rs.next()){
            citiesFrom.add(rs.getString("STARTING_POINT"));
            System.out.println("inside init");
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
    
    int userId;
    String userName;
    
    public void setIdandName(String email) throws SQLException{
        System.out.println(email);
        Connection connection = new Oracle().getConnection();
        String sql = "select user_id, customer_name from customer where email = ?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            userId = rs.getInt(1);
            userName = rs.getString(2);
            lbluserName.setText(userName);
            System.out.println(userName);
        }
        pst.close();
        connection.close();
    }
    
    public void setUserIdName(String name, int id){
        this.userId = id;
        this.userName = name;
    }
    
    @FXML
    /*public void search(ActionEvent event) throws IOException
    {
        //Stage stage = new Stage();
        
        Parent root = FXMLLoader.load(getClass().getResource("/login/Bus.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }*/
    //    @FXML
    
    
    public void search(ActionEvent event) throws IOException
    {
        System.out.println("Search Button Clicked");
//        System.out.println(comboFrom.getValue().toString());
//        System.out.println(comboTo.getValue().toString());
//        System.out.println(journeyDate.getValue().toString());
        if(journeyDate.getValue().isBefore(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Date");
            alert.setHeaderText("Invalid Date");
            alert.setContentText("Please Select Valid Date");
            alert.showAndWait();
        }
        else if((comboFrom.getValue() != null && comboTo.getValue() != null) && journeyDate.getValue() != null ){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("search.fxml"));
            Parent root = (Parent) loader.load();
            /*Parent root = FXMLLoader.load(getClass().getResource("search.fxml"));*/
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            SearchController searchController = loader.<SearchController>getController();
            try {
                searchController.setFromNToDate(comboFrom.getValue().toString(), comboTo.getValue().toString(), journeyDate.getValue(), userName, userId);
                //searchController.setUserNameId(userName, userId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stage.show();
        }
        else {
            System.out.println("Why ???");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("Please fill all the required fields..");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void updateInfo(ActionEvent event) throws IOException, SQLException{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("myAccount.fxml"));
        Parent root = (Parent) loader.load();
        /*Parent root = FXMLLoader.load(getClass().getResource("search.fxml"));*/
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        MyAccountController myAccount = loader.<MyAccountController>getController();
        
        Connection con = new Oracle().getConnection();
        String qry = "select customer_name,gender,address,mobile_no,email,password from customer where user_id = ?";
        PreparedStatement pst = con.prepareStatement(qry);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            myAccount.setInfo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),userId);
            
        }
        
        stage.show();
    }
    
//    @FXML
//    public void feedbackIN(ActionEvent event) throws SQLException{
//        Connection con = new Oracle().getConnection();
//        String qry = "insert into feedback values(feedback_id_seq.nextval,?,?)";
//        PreparedStatement pst = con.prepareStatement(qry);
//        pst.setInt(1, userId);
//        pst.setString(2, qry);
//        
//    }
    
    @FXML
    public void back(ActionEvent event) throws IOException
    {
        //Stage stage = new Stage();
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
    
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//    }    
    
}
