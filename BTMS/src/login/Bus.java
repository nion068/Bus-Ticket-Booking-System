

package login;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * Created by  on 1/7/2018.
 */
public class Bus {
    SimpleStringProperty operator;
    SimpleStringProperty model;
    SimpleStringProperty type;
    SimpleStringProperty time;
    SimpleIntegerProperty fare;
    SimpleStringProperty bookNow;
    SimpleStringProperty path;
    SimpleIntegerProperty availableSeats;
    int bus_id;
    int route_id;
    int schedule_id;
    LocalDate journeyDate;

    public Bus(){

    }

    public Bus(String operator, String model, String time, int fare, int bus_id, int route_id, int schedule_id, LocalDate journeyDate, String path, String type, int availableSeats) {
        this.operator = new SimpleStringProperty(operator);
        this.model = new SimpleStringProperty(model);
        this.type = new SimpleStringProperty(type);
        this.time = new SimpleStringProperty(time);
        this.fare = new SimpleIntegerProperty(fare);
        this.bookNow = new SimpleStringProperty("Book Now");
        this.bus_id = bus_id;
        this.route_id = route_id;
        this.schedule_id = schedule_id;
        this.journeyDate = journeyDate;
        this.path = new SimpleStringProperty(path);
        this.availableSeats = new SimpleIntegerProperty(availableSeats);
    }

    public String getOperator() {
        return operator.get();
    }

    public String getModel() {
        return model.get();
    }
    
    public int getAvailableSeat(){
        return availableSeats.get();
    }

    public String getType() {
        return type.get();
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

    public String getPath(){
        return  path.get();
    }
}