package login;

import Connection.Oracle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by  on 12/7/2017.
 */
public class SearchController implements Initializable {

    @FXML
    Label from;

    @FXML
    Label to;

    @FXML
    Label date;

    @FXML
    Button searchAgain;

    @FXML
    TableView searchTable;
    
    @FXML
    private JFXComboBox comboOperator;
    
    @FXML
    private JFXComboBox comboType;
    
    @FXML
    private JFXButton filter;
    
    @FXML
    private JFXButton btnFilter;
    

    LocalDate journeyDate;

    /*@FXML
    TableColumn operator = new TableColumn("Operator Name");
    @FXML
    TableColumn model = new TableColumn("Model");
    @FXML
    TableColumn time = new TableColumn("Departure Time");
    @FXML
    TableColumn fare = new TableColumn("Fare");*/
    
    ObservableList<Bus> busList = FXCollections.observableArrayList();

    String userName;
    int userId;

    public void setFromNToDate(String from, String to, LocalDate journeyDate, String userName, int userId) throws SQLException{
        this.from.setText(from);
        this.to.setText(to);
        this.journeyDate = journeyDate;
        this.userName = userName;
        this.userId = userId;
        System.out.println("In Search controller = " + userId);
        //this.date.setText(journeyDate.getDayOfMonth() + " " + journeyDate.getMonth() + " " + journeyDate.getYear());
        this.date.setText(journeyDate.getDayOfMonth() + "-" + journeyDate.getMonthValue() + "-" + (journeyDate.getYear()-2000));
        searchResult(from, to);
    }

    
    /*public void setUserNameId(String userName, int userId){
        this.userName = userName;
        this.userId = userId;
        System.out.println("In Search controller = " + userId);
    }*/

    private void searchResult(String from, String to) throws SQLException{

        TableColumn operator = new TableColumn("Operator Name");
        TableColumn model = new TableColumn("Model");
        TableColumn type = new TableColumn("Type");
        TableColumn time = new TableColumn("Departure Time");
        TableColumn fare = new TableColumn("Fare");
        TableColumn path = new TableColumn("Route Path");
        TableColumn bookNow = new TableColumn("Action");
        TableColumn availableSeat = new TableColumn("Available Seats");


        searchTable.getColumns().addAll(operator, model, type, time, fare, availableSeat, path, bookNow);


        /*String sql = "SELECT o.OPERATOR_NAME OPERATOR, b.MODEL MODEL, b.type TYPE, f.fare_rate FARE,  s.departure_time TIME " +
                    "FROM ROUTE r, Bus b, SCHEDULE s, FARE f, OPERATOR o " +
                    "WHERE o.OPERATOR_ID = b.OPERATOR_ID AND b.SCHEDULE_ID = s.SCHEDULE_ID AND  r.ROUTE_ID = f.ROUTE_ID AND f.BUS_ID = b.BUS_ID and r.STARTING_POINT = ? AND r.DESTINATION = ?";*/
        String sql = "SELECT o.OPERATOR_NAME, b.MODEL, s.DEPARTURE_TIME, bs.FARE, bs.bus_id, bs.ROUTE_ID,bs.SCHEDULE_ID, r.ROUTE_PATH, b.type FROM ROUTE r, Bus b, SCHEDULE s, BUS_SCHEDULE bs, OPERATOR o WHERE BS.BUS_ID = b.BUS_ID and bs.ROUTE_ID = r.ROUTE_ID and BS.SCHEDULE_ID = s.SCHEDULE_ID and b.OPERATOR_ID = o.OPERATOR_ID and r.STARTING_POINT = ? and r.DESTINATION = ?";
        Connection con = new Oracle().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, from);
        pst.setString(2, to);
        ResultSet rs = pst.executeQuery();
        while (rs.next()){
            CallableStatement cst = con.prepareCall("{? = call REMAINING_SEAT(?, ?, ?, to_date(?, 'YYYY-MM-DD'))}");
            cst.setInt(2, rs.getInt(5));
            cst.setInt(3, rs.getInt(7));
            cst.setInt(4, rs.getInt(6));
            cst.setString(5, date.getText());
            cst.registerOutParameter(1, Types.INTEGER);
            cst.execute();
            System.out.println(rs.getString(1)+"   " + rs.getString(2)+ "     " + rs.getString(3)+ "    " + rs.getInt(4) );
            busList.add(new Bus(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), journeyDate, rs.getString(8), rs.getString(9), cst.getInt(1)));
        }
        operator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        fare.setCellValueFactory(new PropertyValueFactory<>("fare"));
        availableSeat.setCellValueFactory(new PropertyValueFactory<>("availableSeat"));
        path.setCellValueFactory(new PropertyValueFactory<>("path"));
        bookNow.setCellValueFactory(new PropertyValueFactory<>("bookNow"));



        searchTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Bus bus = new Bus();
                bus = (Bus) searchTable.getSelectionModel().getSelectedItem();
                //System.out.println(bus.getOperator());
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Bus.fxml"));
                    Parent root = (Parent) loader.load();
                    /*Parent root = FXMLLoader.load(getClass().getResource("search.fxml"));*/
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    //BookingFinalizationController bookingFinalizationController = loader.<BookingFinalizationController>getController();
                    BusController busController = loader.<BusController>getController();
                    busController.setInfo(bus);
                    busController.setUserNameId(userName, userId, from, to);
                    stage.show();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
        searchTable.setItems(busList);


    }
    
    public void filter(ActionEvent event){
        //searchTable.getItems().clear();
        /*TableColumn operator = new TableColumn("Operator Name");
        TableColumn model = new TableColumn("Model");
        TableColumn type = new TableColumn("Type");
        TableColumn time = new TableColumn("Departure Time");
        TableColumn fare = new TableColumn("Fare");
        TableColumn path = new TableColumn("Route Path");
        TableColumn bookNow = new TableColumn("Action");
        searchTable.getColumns().addAll(operator, model, type, time, fare,path, bookNow);*/

        ObservableList<Bus> filteredList = FXCollections.observableArrayList();

        if(comboOperator.getValue() != null && comboType.getValue() != null){
            for (Bus filterBus : busList ) {
                if(filterBus.getType().equals(comboType.getValue().toString()) && filterBus.getOperator().equals(comboOperator.getValue().toString())){
                    filteredList.add(filterBus);
                }
            }
        }
        else if(comboOperator.getValue() == null && comboType.getValue() != null){
            for (Bus filterBus : busList ) {
                if(filterBus.getType().equals(comboType.getValue().toString())){
                    filteredList.add(filterBus);
                }
            }
        }
        else if(comboOperator.getValue() != null && comboType.getValue() == null){
            for (Bus filterBus : busList ) {
                if(filterBus.getOperator().equals(comboOperator.getValue().toString())){
                    filteredList.add(filterBus);
                }
            }
        }
        else {
            return;
        }

        /*operator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        fare.setCellValueFactory(new PropertyValueFactory<>("fare"));
        path.setCellValueFactory(new PropertyValueFactory<>("path"));
        bookNow.setCellValueFactory(new PropertyValueFactory<>("bookNow"));*/

        searchTable.setItems(filteredList);

    }

    public void searchAgainClicked(ActionEvent event) throws IOException{

//        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
//
//        Scene scene = new Scene(root);
//
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//        stage.setScene(scene);
//
//        stage.show();

FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/main.fxml"));
        Parent root = (Parent) loader.load();
        
//        Parent root = FXMLLoader.load(getClass().getResource("/login/main.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        MainController mainController = loader.<MainController>getController();
        mainController.setUserIdName(userName, userId);
        stage.show();
    }
    
    public void exit(ActionEvent event){
        System.exit(0);
    }
    
    
    ObservableList<String>opName = FXCollections.observableArrayList();
    ObservableList<String>type = FXCollections.observableArrayList("AC", "Non-AC");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try {
            Connection con = new Oracle().getConnection();
            String operate = "select distinct operator_name from operator";
            PreparedStatement pst = con.prepareStatement(operate);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                opName.add(rs.getString(1));
            }
            
            } catch (SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboOperator.setItems(opName);
        comboType.setItems(type);
    }
}
