package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Controller{

    @FXML
    TextField name;

    @FXML
    TextField gender;

    @FXML
    TextField address;

    @FXML
    TextField mobno;

    @FXML
    TextField email;

    @FXML
    PasswordField pass;

    @FXML
    PasswordField confirm;

    @FXML
    Button submit;

    @FXML
    Button back;

    @FXML

    public void signUp(ActionEvent event) throws IOException {

        if(pass.getText().toString().equals(confirm.getText().toString())){
            try {
                createUser(name.getText().toString(), gender.getText().toString(), address.getText().toString(), mobno.getText().toString(), email.getText().toString(), pass.getText().toString());
            } catch (SQLException e){
                e.printStackTrace();
            }

            Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

            /*Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
            //stage.setTitle("Hello World");
            stage.setScene(new Scene(root, 800, 500));
            stage.show();*/
        }
        else {
            System.out.println("Password doesn't match.");
        }
    }

    public  void back(ActionEvent event) throws  IOException{
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();
    }

    public void createUser(String userName, String gender, String address, String mobile,String email, String password) throws SQLException{
        //int user_id = 103;
        Connection connection = new DBMSConnection().getConnection();
        String insert = "insert into customer values(customer_id_seq.nextval, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(insert);
        //pst.setInt(1, user_id);
        pst.setString(1, userName);
        pst.setString(2, gender);
        pst.setString(3, address);
        pst.setString(4, mobile);
        pst.setString(5, email);
        pst.setString(6, password);

        pst.execute();

        pst.close();
        connection.close();
    }




}
