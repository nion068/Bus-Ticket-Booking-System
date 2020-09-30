package login;

import Connection.Oracle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminController {
    
    public String operator;

    @FXML
    private JFXButton bookingViewMore;


    @FXML
    private JFXTextField bus_model;

    @FXML
    private JFXTextField schedule;

    @FXML
    private JFXTextField tot_seat;

    @FXML
    private JFXTextField bus_type;

    @FXML
    private JFXButton add_bus;

    @FXML
    private JFXButton backbtn;

    @FXML
    private JFXTextField from;

    @FXML
    private JFXTextField to;

    @FXML
    private JFXTextField time;

    @FXML
    private JFXButton add_route;

    @FXML
    private JFXTextField new_fare;

    @FXML
    private JFXTextField change_fare_busID;
    
    @FXML
    private JFXTextField change_fare_routeID;
    
    @FXML
    private JFXTextField change_fare_schedule;

    @FXML
    private JFXButton update_fare;

    @FXML
    private JFXTextField staff_contact;

    @FXML
    private JFXTextField bus_id2;

    @FXML
    private JFXTextField staff_name;

    @FXML
    private JFXTextField staff_designation;

    @FXML
    private JFXTextField staff_address;

    @FXML
    private JFXButton update_staff;

    @FXML
    private JFXTextField booking_bus;

    @FXML
    private JFXTextField Booking_schedule;

    @FXML
    private JFXTextField booking_route;

    @FXML
    private JFXDatePicker journey_date;

    @FXML
    private Label totPassenger;

    @FXML
    private Label occuSeat;

    @FXML
    private Label availSeat;
    
    @FXML
    private JFXTextField staff_info_bus;

    @FXML
    private Label staff_info_driver;

    @FXML
    private Label staff_info_superviser;

    @FXML
    private Label staff_info_helper;
    
    @FXML
    private JFXButton staff_info_btn;
    
    @FXML
    private JFXButton remove_bus;
    
    @FXML
    private JFXTextField remove_busID;

    
    @FXML
    private JFXComboBox comboBusID;
    
    @FXML
    private JFXComboBox comboBusIDRemove;
    
    @FXML
    private JFXComboBox comboBusIDBooking;
    
    
    @FXML
    private JFXComboBox comboBusIDUpdateStaff;
    
    @FXML
    private JFXComboBox comboBusIDUpdateFare;
    
    
    
    ObservableList<String>busID = FXCollections.observableArrayList();
    
    public void setOperatorName(String name){
        this.operator = name;
        System.out.println(operator);
        setComboBox();
    }
    
    public void setComboBox() {
        try {
            Connection con = new Oracle().getConnection();
            String bus = "SELECT distinct BUS_ID FROM BUS WHERE OPERATOR_ID = (select operator_id from operator where operator_name ='"+operator+"')";
            //String bus = "SELECT DISTINCT STARTING_POINT FROM ROUTE";
            Statement pst = con.createStatement();
            //pst.setString(1, operator);
            ResultSet rs = pst.executeQuery(bus);
            System.out.println("hello");
            while(rs.next()){
                //System.out.println(rs.getInt(1));
                String asif = Integer.toString(rs.getInt(1));
                busID.add(asif);
                //System.out.println(asif);
            }
            comboBusID.setItems(busID);
            comboBusIDRemove.setItems(busID);
            comboBusIDBooking.setItems(busID);
            comboBusIDUpdateFare.setItems(busID);
            comboBusIDUpdateStaff.setItems(busID);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("  BUS_ID: " + busID);
        //comboBusID.setItems(busID);
        
    }
    
    //@FXML
//    public void initialize(URL url, ResourceBundle rb) {
//        try {
//            Connection con = new Oracle().getConnection();
//            String bus = "SELECT distinct BUS_ID FROM BUS WHERE OPERATOR_ID = (select operator_id from operator where operator_name ='"+operator+"')";
//            //String bus = "SELECT DISTINCT STARTING_POINT FROM ROUTE";
//            Statement pst = con.createStatement();
//            //pst.setString(1, operator);
//            ResultSet rs = pst.executeQuery(bus);
//            System.out.println("hello");
//            while(rs.next()){
//                System.out.println(rs.getInt(1));
//                String asif = Integer.toString(rs.getInt(1));
//                busID.add(asif);
//                System.out.println(asif);
//            }
//            comboBusID.setItems(busID);
//            con.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //System.out.println("  BUS_ID: " + busID);
//        //comboBusID.setItems(busID);
//        
//    }
    
    
    @FXML
    public void showStaff(ActionEvent event){
        try {
            Connection con = new Oracle().getConnection();
            int i = 0;
            //int bus_id = (int) comboBusID.getValue();
            int bus_id = Integer.parseInt((String) comboBusID.getValue());
            String sql = "SELECT s.STAFF_NAME FROM STAFF s, BUS b where s.BUS_ID = b.BUS_ID and b.BUS_ID = ? ORDER BY s.JOB_TYPE ASC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bus_id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                switch (i) {
                    case 0:
                        staff_info_driver.setText(rs.getString(1));
                        i++;
                        break;
                    case 1:
                        staff_info_helper.setText(rs.getString(1));
                        i++;
                        break;
                    default:
                        staff_info_superviser.setText(rs.getString(1));                
                        break;
                }
            }
            i =0 ;
//            staff_info_driver.setText(rs.getString(1));
//            staff_info_superviser.setText(rs.getString(3));
//            staff_info_helper.setText(rs.getString(2));

            pst.close();
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("Cause: " + ex.getMessage());
            alert.showAndWait();
        }
    }

//    public void setOperatorName(String name){
//        this.operator = name;
//        System.out.println(operator);
//    }
    
    @FXML
    void addBus()  {
        try {
            //        int scdl = Integer.parseInt(schedule.getText());
            int seat = Integer.parseInt(tot_seat.getText());
            createUser(bus_model.getText(), bus_type.getText(), seat);
            System.out.println("Insertion done..");
            System.out.println(operator);
        } catch (SQLException ex) {
            //Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("Cause: " + ex.getLocalizedMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    void removeBus(ActionEvent event){
        try {
            int busID = Integer.parseInt((String) comboBusIDRemove.getValue());
            Connection con = new Oracle().getConnection();
            String del =  "Delete FROM bus where bus_id = ?";
            PreparedStatement pst = con.prepareStatement(del);
            pst.setInt(1, busID);
            ResultSet rs2 = pst.executeQuery();
//        while(rs2.next()){
//            busID = rs2.getInt(1);
//            System.out.println(busID);
//        }
            pst.close();
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("Cause: " + ex.getLocalizedMessage());
            alert.showAndWait();
        }
        
    }

    @FXML
    void addRoute() {
        try {
            String start = from.getText();
            String dest = to.getText();
            int apxTime = Integer.parseInt(time.getText());
            Connection con = new Oracle().getConnection();
            String sql = "insert into route values(route_id_seq.nextval,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, start);
            pst.setString(2, dest);
            pst.setInt(3, apxTime);
            ResultSet rs2 = pst.executeQuery();
            
//        while(rs2.next()){
//            
//        }

            pst.close();
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("Cause: " + ex.getLocalizedMessage());
            alert.showAndWait();
        }
        
    }

    @FXML
    void updateFare() {
        try {
            //            String bus = change_fare_busID.getText();
//            String route = change_fare_routeID.getText();
//            String schdl = change_fare_schedule.getText();
//            String fare = new_fare.getText();

        int bsID = Integer.parseInt((String) comboBusIDUpdateFare.getValue());
        int rtID = Integer.parseInt(change_fare_routeID.getText());
        int scID = Integer.parseInt(change_fare_schedule.getText());
        int fareID = Integer.parseInt(new_fare.getText());
        Connection con = new Oracle().getConnection();
        String sql = "insert into bus_schedule values(?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, bsID);
        pst.setInt(2, rtID);
        pst.setInt(3, scID);
        pst.setInt(4, fareID);

        ResultSet rs2 = pst.executeQuery();

        //        while(rs2.next()){
        //            
        //        }

        pst.close();
        con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    void updateStaff()  {
        String stfName = staff_name.getText();
        String stfDesignation = staff_designation.getText();
        String stfNumber = staff_contact.getText();
        String stfAddress = staff_address.getText();
        int stfBus = Integer.parseInt((String) comboBusIDUpdateStaff.getValue());
        try{
        Connection connec = new Oracle().getConnection();
        //String qry = "insert into STAFF values(staff_id_seq2.nextval,?,?,?,?,?)";
        String qry = "BEGIN Staff_handle(?,?,?,?,?); END;";
        PreparedStatement pst;
        //try {
        pst = connec.prepareStatement(qry);
            
        pst.setString(1, stfName);
        pst.setString(2, stfDesignation);
        pst.setString(3, stfNumber);
        pst.setString(4, stfAddress);
        pst.setInt(5, stfBus); 

        pst.execute();
        pst.close();
        connec.close();
        } catch (SQLException ex) {
            //Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("Cause: " + ex.getMessage());
            alert.showAndWait();
        }
        
    }
    
    @FXML
    void back(ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("/login/AdminLogin.fxml"));
            Scene scene = new Scene(root);
        
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    
    public void createUser(String model, String type, int seat) throws SQLException{
        try ( //int user_id = 103;
            Connection connection = new Oracle().getConnection()) {
            String qry = "select operator_id from operator where operator_name = ?";
            PreparedStatement pst1 = connection.prepareStatement(qry);
            //pst.setInt(1, user_id);
            pst1.setString(1, operator);
            
            int operator_id = 0;
            
            ResultSet rs2 = pst1.executeQuery();
            while(rs2.next()){
                operator_id = rs2.getInt(1);
                System.out.println(operator_id);
            }
            
            
            String insert = "insert into bus values(bus_id_seq3.nextval, ?, ?, ?, ?)";
            //pst.setInt(1, user_id);
            try (PreparedStatement pst = connection.prepareStatement(insert)) {
                //pst.setInt(1, user_id);
                pst.setString(1, model);
                pst.setString(2, type);
                pst.setInt(3, seat);
                pst.setInt(4, operator_id);
                //pst.setInt(5, schedule);
                
                pst.execute();
            }
        }
    }

    @FXML

    public void bookingViewMoreClicked(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        //Parent root = FXMLLoader.load(getClass().getResource("ViewDetails.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewDetails.fxml"));
        Parent root = (Parent) loader.load();

        if(comboBusIDBooking.getItems() != null) stage.setTitle("Booking Details for BusId " + comboBusIDBooking.getValue().toString());
        stage.setScene(new Scene(root, 350, 354));
        String date = journey_date.getValue().getDayOfMonth() + "-" + journey_date.getValue().getMonthValue() + "-" + (journey_date.getValue().getYear()-2000);
        System.out.println(comboBusIDBooking.getValue().toString());
        System.out.println(Booking_schedule.getText().toString());
        System.out.println(booking_route.getText().toString());
        System.out.println(date);
        ViewDetailsController viewDetailsController = loader.<ViewDetailsController>getController();
        try{
            viewDetailsController.setData(Integer.parseInt(comboBusIDBooking.getValue().toString()), Integer.parseInt(booking_route.getText().toString()), Integer.parseInt(Booking_schedule.getText().toString()), date);
        } catch(SQLException e){
            e.printStackTrace();
        }

        stage.show();
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        //throw new UnsupportedOperationException("Not supported yet.");
//        //To change body of generated methods, choose Tools | Templates.
//        System.out.println(operator_name);
//    }

}
