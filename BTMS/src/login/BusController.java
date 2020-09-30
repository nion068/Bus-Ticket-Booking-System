/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import Connection.Oracle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Asif
 */
public class BusController {

    /**
     * Initializes the controller class.
     */
    

    @FXML
    private JFXComboBox<String> comboSeat;

    @FXML
    private JFXComboBox<String> comboBoarding;

    @FXML
    private JFXButton buyTicket;

    @FXML
    private Label lblSeat1;

    @FXML
    private Label lblSeat2;

    @FXML
    private Label lblSeat3;

    @FXML
    private Label lblSeat4;

    @FXML
    private Label lblFare;

    @FXML
    private JFXButton backbtn;

    @FXML
    private JFXButton exitbtn;
    
    @FXML
    private JFXButton bookTicket;
    
    @FXML
    private JFXButton resetBtn;
    
    @FXML
    private Label lblWarning;
    
    @FXML
    private ImageView seatPlanImageView;
    
    int count = 0;
    int fare;
    ObservableList<String>seat = FXCollections.observableArrayList();
    
    ObservableList<String>boarding = FXCollections.observableArrayList();
    @FXML
    public void buy(ActionEvent event) throws IOException {
        try {
            Connection con = new Oracle().getConnection();
            String seq = "SELECT operator_id_seq5.nextval FROM dual";
            //int sequence = Integer.parseInt(seq);
            int sequence = 0;
            String seats = lblSeat1.getText() + "   " + lblSeat2.getText() + "   " + lblSeat3.getText() + "   " + lblSeat4.getText() + "   ";
            PreparedStatement pst = con.prepareStatement(seq);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                sequence = rs.getInt(1);
            }
            if(lblSeat1.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Seat is Selected");
                alert.setContentText("Please select atleast one  seat.");
                alert.showAndWait();
                return;
            }
            else if(comboBoarding.getValue() == null ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Boarding Point is Selected");
                alert.setContentText("Please select a boarding point.");
                alert.showAndWait();
                return;
            }
            else {
                bookInsert(lblSeat1.getText(), sequence);
            }
            if(!lblSeat2.getText().isEmpty()){
                bookInsert(lblSeat2.getText(),sequence);
            }
            if(!lblSeat3.getText().isEmpty()){
                bookInsert(lblSeat3.getText(),sequence);
            }
            if(!lblSeat4.getText().isEmpty()){
                System.out.println("hello");
                bookInsert(lblSeat4.getText(),sequence);
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/ShowTicket.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
                
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                //stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            String date = bus.getJourneyDate().getDayOfMonth()+"-"+bus.getJourneyDate().getMonthValue()+"-"+(bus.getJourneyDate().getYear()-2000);
            ShowTicketController showTicketController = loader.<ShowTicketController>getController();
            showTicketController.ticketInfo(sequence, userId, userName, from, to, date, bus.getOperator(), bus.getModel(), seats, Integer.parseInt(lblFare.getText().toString()), comboBoarding.getValue());
            
            stage.show();
          
//        try {
//            Connection connection = new Oracle().getConnection();
//            String sql = "INSERT INTO BOOKING VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), 5, 1022, 5001)";
//            PreparedStatement pst = connection.prepareStatement(sql);
//            pst.setInt(1, bus.getBus_id());
//            pst.setInt(2, bus.getSchedule_id());
//            pst.setInt(3, bus.getRoute_id());
//            pst.setString(4, lblSeat1.getText().toString());
//            String date = bus.getJourneyDate().getDayOfMonth()+"-"+bus.getJourneyDate().getMonthValue()+"-"+(bus.getJourneyDate().getYear()-2000);
//            pst.setString(5, date);
//            pst.execute();
//            pst.close();
//            connection.close();
//            System.out.println(bus.getBus_id());
//            System.out.println(bus.getSchedule_id());
//            System.out.println(bus.getRoute_id());
//            System.out.println(lblSeat1.getText().toString());
//            System.out.println(bus.getJourneyDate().toString());
//        } catch (SQLException ex) {
//            Logger.getLogger(BusController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (SQLException ex) {
            //Logger.getLogger(BusController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Something Went Wrong!");
            alert.setContentText("Cause: " + ex.getMessage());
            alert.showAndWait();
        }
    }
    
    Bus bus;
    String userName;
    String from;
    String to;
    int userId;
    public void bookInsert(String seat , int ticket){
        try {
            Connection connection = new Oracle().getConnection();
            String date = bus.getJourneyDate().getDayOfMonth()+"-"+bus.getJourneyDate().getMonthValue()+"-"+(bus.getJourneyDate().getYear()-2000);
            CallableStatement cst = connection.prepareCall("{? = call get_boarding(?,?)}");
            cst.setString(2, comboBoarding.getValue());
            cst.setInt(3, bus.getRoute_id());
            cst.registerOutParameter(1, Types.INTEGER);
            cst.execute();
            String sql = "INSERT INTO BOOKING VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, bus.getBus_id());
            pst.setInt(2, bus.getSchedule_id());
            pst.setInt(3, bus.getRoute_id());
            pst.setString(4, seat);
         
            pst.setString(5, date);
            pst.setInt(6, cst.getInt(1));
            pst.setInt(7, userId);
            pst.setInt(8, ticket);
            
            pst.execute();
            cst.close();
            pst.close();
            connection.close();
            System.out.println(bus.getBus_id());
            System.out.println(bus.getSchedule_id());
            System.out.println(bus.getRoute_id());
            System.out.println(lblSeat1.getText().toString());
            System.out.println(bus.getJourneyDate().toString());
        } catch (SQLException ex) {
            Logger.getLogger(BusController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Something Went Wrong!");
            alert.setContentText("Cause: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    public void setInfo(Bus bus){
        this.bus = bus;
        System.out.println("Bus Id - " + bus.getBus_id() + " Route Id - " + bus.getRoute_id() + " Schedule Id - "+ bus.getSchedule_id());
        String date = bus.getJourneyDate().getDayOfMonth()+"-"+bus.getJourneyDate().getMonthValue()+"-"+(bus.getJourneyDate().getYear()-2000);
        System.out.println(date);
        setValues(bus.getBus_id(), bus.getSchedule_id(), bus.getRoute_id(), date);
        if(bus.getType().equals("AC")){
            File file = new File("src/login/image/seatPlanAC.png");
            Image image = new Image(file.toURI().toString());
            seatPlanImageView.setImage(image);
        }
        else{
            File file = new File("src/login/image/SeatPlanNonAC.png");
            Image image = new Image(file.toURI().toString());
            seatPlanImageView.setImage(image);
        }
        
        
    }
    
    public void setUserNameId(String userName, int userId, String from, String to){
        this.userName = userName;
        this.userId = userId;
        System.out.println("In bus controller = " + userId);
        this.from = from;
        this.to = to;
    }
    
    @FXML
    public void reset(ActionEvent event){
        /*lblSeat1.setText(null);
        lblSeat2.setText(null);
        lblSeat3.setText(null);
        lblSeat4.setText(null);
        lblFare.setText(null);
        lblWarning.setText(null);*/
        lblSeat1.setText("");
        lblSeat2.setText("");
        lblSeat3.setText("");
        lblSeat4.setText("");
        lblFare.setText("");
        lblWarning.setText("");
        comboBoarding.setValue(null);
        count = 0;
        fare = bus.getFare();
    }

    @FXML
    public void book(ActionEvent event){
        String seatSelect = comboSeat.getValue();
        String lebel1 = lblSeat1.getText();
        String lebel2 = lblSeat2.getText();
        String lebel3 = lblSeat3.getText();
        String lebel4 = lblSeat4.getText();
        fare = bus.getFare();
        //String fareShow = Integer.toString(fare);
        System.out.println("combo inside.");
        if(comboSeat.getValue() == null){
            lblWarning.setText("Please Select a Seat First");
            return;
        }
        lblWarning.setText("You Can Buy Atmost 4 Tickets.");
        if(count == 0){
            lblSeat1.setText(seatSelect);
            lblFare.setText(Integer.toString(fare));
            count++;
        }
        else if(count == 1 && !seatSelect.equals(lebel1)){
            lblSeat2.setText(seatSelect);
            lblFare.setText(Integer.toString(fare*2));
            count++;
        }
        else if(count == 2 && !seatSelect.equals(lebel1) && !seatSelect.equals(lebel2)){
            lblSeat3.setText(seatSelect);
            lblFare.setText(Integer.toString(fare*3));
            count++;
        }
        else if(count == 3 && !seatSelect.equals(lebel1) && !seatSelect.equals(lebel2) && !seatSelect.equals(lebel3)){
            lblSeat4.setText(seatSelect);
            lblFare.setText(Integer.toString(fare*4));
            count++;
        }
        
        
        
    }
    
    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }
    
    

    @FXML
    void back(ActionEvent event) throws IOException {
          Parent root = FXMLLoader.load(getClass().getResource("/login/main.fxml"));
            Scene scene = new Scene(root);
        
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
    }

    public void setValues(int bus_id, int schedule_id, int route_id, String date) {
        try {
            // TODO
            Connection con = new Oracle().getConnection();
            //String availSeat = "SELECT SEAT_NO from SEAT WHERE BUS_ID = ? MINUS (SELECT s.SEAT_NO from SEAT s, BOOKING b	WHERE s.BUS_ID = b.BUS_ID AND b.BUS_ID = ? AND b.SCHEDULE_ID = ? AND b.ROUTE_ID = ? AND b.JOURNEY_DATE = TO_DATE(?, 'YYYY-MM-DD') AND s.SEAT_NO = b.SEAT_NO )";
            String availSeat = "SELECT * FROM TABLE(get_Remaining_seats(?,?,?,TO_DATE(?, 'YYYY-MM-DD')))";
            PreparedStatement pst = con.prepareStatement(availSeat);
            //pst.setInt(1, 1001);
            pst.setInt(1, bus_id);
            pst.setInt(2, schedule_id);
            pst.setInt(3, route_id);
            pst.setString(4, date);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                seat.add(rs.getString(1));
                //System.out.println("Nice..");
            }
            
            comboSeat.setItems(seat);
            
            String boardingPoint = "SELECT BOARDING_POINT FROM BOARDING WHERE ROUTE_ID = ?";
            PreparedStatement pst2 = con.prepareStatement(boardingPoint);
            pst2.setInt(1, route_id);
            
            ResultSet rs2 = pst2.executeQuery();
            //System.out.println("nyc lagsa..");
            while(rs2.next()){
                boarding.add(rs2.getString("BOARDING_POINT"));
                //System.out.println("Also Nice..");
            }
            
            comboBoarding.setItems(boarding);
            } catch (SQLException ex) {
            //Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("What Happened");
            alert.setContentText("Cause: " + ex.getMessage());
            alert.showAndWait();
        }
    }    
    
}
