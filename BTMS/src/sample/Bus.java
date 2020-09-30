package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * Created by nion and asif on 1/7/2018.
 */
public class Bus {
    SimpleStringProperty operator;
    SimpleStringProperty model;
    SimpleStringProperty time;
    SimpleIntegerProperty fare;
    SimpleStringProperty bookNow;
    int bus_id;
    int route_id;
    int schedule_id;
    LocalDate journeyDate;

    public Bus(){

    }

    public Bus(String operator, String model, String time, int fare, int bus_id, int route_id, int schedule_id, LocalDate journeyDate) {
        this.operator = new SimpleStringProperty(operator);
        this.model = new SimpleStringProperty(model);
        this.time = new SimpleStringProperty(time);
        this.fare = new SimpleIntegerProperty(fare);
        this.bookNow = new SimpleStringProperty("Book Now");
        this.bus_id = bus_id;
        this.route_id = route_id;
        this.schedule_id = schedule_id;
        this.journeyDate = journeyDate;
    }

    public String getOperator() {
        return operator.get();
    }

    public String getModel() {
        return model.get();
    }

    public String getTime() {
        return time.get();
    }

    public int getFare() {
        return fare.get();
    }
    public String getBookNow(){
        return bookNow.get();
    }

    public int getBus_id() {
        return bus_id;
    }

    public int getRoute_id() {
        return route_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }
}
