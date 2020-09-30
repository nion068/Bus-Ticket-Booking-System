package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by nion on 1/8/2018.
 */
public class BookingFinalizationController {

    @FXML
    Label operator;
    @FXML
    Label model;
    @FXML
    Label time;
    @FXML
    Label fare;
    @FXML
    TextField seatNo;
    @FXML
    Button confirm;

    Bus bus;

    public void setInfo(Bus bus){
        this.bus = bus;
        operator.setText(bus.getOperator());
        model.setText(bus.getModel());
        time.setText(bus.getTime());
        Integer integer = bus.getFare();
        fare.setText(integer.toString());
        System.out.println("Bus Id - " + bus.getBus_id() + " Route Id - " + bus.getRoute_id() + " Schedule Id - "+ bus.getSchedule_id());
    }

    public void searchAgain(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();
    }

    public void confirmClicked(ActionEvent event) throws SQLException{
        Connection connection = new DBMSConnection().getConnection();
        String sql = "INSERT INTO BOOKING VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), 5, 1061, 5001)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, bus.getBus_id());
        pst.setInt(2, bus.getSchedule_id());
        pst.setInt(3, bus.getRoute_id());
        pst.setString(4, seatNo.getText().toString());
        pst.setString(5, bus.getJourneyDate().toString());
        pst.execute();
        pst.close();
        connection.close();
        System.out.println(bus.getBus_id());
        System.out.println(bus.getSchedule_id());
        System.out.println(bus.getRoute_id());
        System.out.println(seatNo.getText().toString());
        System.out.println(bus.getJourneyDate().toString());

    }

}
