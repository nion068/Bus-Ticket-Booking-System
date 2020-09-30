package sample;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by nion on 12/7/2017.
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

    LocalDate journeyDate;

    /*@FXML
    TableColumn operator = new TableColumn("Operator Name");
    @FXML
    TableColumn model = new TableColumn("Model");
    @FXML
    TableColumn time = new TableColumn("Departure Time");
    @FXML
    TableColumn fare = new TableColumn("Fare");*/

    public void setFromNToDate(String from, String to, LocalDate journeyDate) throws SQLException{
        this.from.setText(from);
        this.to.setText(to);
        this.journeyDate = journeyDate;
        this.date.setText(journeyDate.getDayOfMonth() + " " + journeyDate.getMonth() + " " + journeyDate.getYear());
        searchResult(from, to);
    }

    private void searchResult(String from, String to) throws SQLException{

        TableColumn operator = new TableColumn("Operator Name");
        TableColumn model = new TableColumn("Model");
        TableColumn time = new TableColumn("Departure Time");
        TableColumn fare = new TableColumn("Fare");
        TableColumn bookNow = new TableColumn("Action");


        searchTable.getColumns().addAll(operator, model, time, fare, bookNow);

        ObservableList<Bus> busList = FXCollections.observableArrayList();

        /*String sql = "SELECT o.OPERATOR_NAME OPERATOR, b.MODEL MODEL, b.type TYPE, f.fare_rate FARE,  s.departure_time TIME " +
                    "FROM ROUTE r, Bus b, SCHEDULE s, FARE f, OPERATOR o " +
                    "WHERE o.OPERATOR_ID = b.OPERATOR_ID AND b.SCHEDULE_ID = s.SCHEDULE_ID AND  r.ROUTE_ID = f.ROUTE_ID AND f.BUS_ID = b.BUS_ID and r.STARTING_POINT = ? AND r.DESTINATION = ?";*/
        String sql = "SELECT o.OPERATOR_NAME, b.MODEL, s.DEPARTURE_TIME, bs.FARE, bs.bus_id, bs.ROUTE_ID,bs.SCHEDULE_ID FROM ROUTE r, Bus b, SCHEDULE s, BUS_SCHEDULE bs, OPERATOR o WHERE BS.BUS_ID = b.BUS_ID and bs.ROUTE_ID = r.ROUTE_ID and BS.SCHEDULE_ID = s.SCHEDULE_ID and b.OPERATOR_ID = o.OPERATOR_ID and r.STARTING_POINT = ? and r.DESTINATION = ?";
        Connection con = new DBMSConnection().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, from);
        pst.setString(2, to);
        ResultSet rs = pst.executeQuery();
        while (rs.next()){
            System.out.println(rs.getString(1)+"   " + rs.getString(2)+ "     " + rs.getString(3)+ "    " + rs.getInt(4) );
            busList.add(new Bus(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), journeyDate));
        }
        operator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        fare.setCellValueFactory(new PropertyValueFactory<>("fare"));
        bookNow.setCellValueFactory(new PropertyValueFactory<>("bookNow"));


        searchTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Bus bus = new Bus();
                bus = (Bus) searchTable.getSelectionModel().getSelectedItem();
                //System.out.println(bus.getOperator());
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("bookingFinalization.fxml"));
                    Parent root = (Parent) loader.load();
                    /*Parent root = FXMLLoader.load(getClass().getResource("search.fxml"));*/
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    BookingFinalizationController bookingFinalizationController = loader.<BookingFinalizationController>getController();
                    bookingFinalizationController.setInfo(bus);
                    stage.show();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

        searchTable.setItems(busList);


    }

    public void searchAgainClicked(ActionEvent event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
