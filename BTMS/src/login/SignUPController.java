package login;

import Connection.Oracle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUPController{

    @FXML
    private JFXButton exit;

    @FXML
    private JFXButton submit;

    @FXML
    private JFXButton back;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField gender;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField mobno;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField pass;

    @FXML
    private JFXTextField confirm;

    @FXML

    public void signUp(ActionEvent event) throws IOException {

        if(!validateEmail(email.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Email");
            alert.setHeaderText("Invalid Email");
            alert.setContentText("Please Enter a valid email.");
            alert.showAndWait();
        }
        else if(!pass.getText().toString().equals(confirm.getText().toString())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Don't Match");
            alert.setHeaderText("Password Don't Match");
            alert.setContentText("Password Don't Match");
            alert.showAndWait();
        }
        else {
            try {
                createUser(name.getText().toString(), gender.getText().toString(), address.getText().toString(), mobno.getText().toString(), email.getText().toString(), pass.getText().toString());
            } catch (SQLException e){
                //e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText("Cause: " + e.getMessage());
                alert.showAndWait();
            }
            Parent root = FXMLLoader.load(getClass().getResource("/login/FXMLDocument.fxml"));
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

//            Stage stage = new Stage();
//            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//            stage.initStyle(StageStyle.UNDECORATED);
//            stage.setScene(new Scene(root, 800, 500));
//            stage.show();
        }
    }

    private boolean validateEmail(String email){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email);
        if(m.find() && m.group().equals(email)){
            return  true;
        }
        else {
            return false;
        }
    }

    public void createUser(String userName, String gender, String address, String mobile,String email, String password) throws SQLException{
        //int user_id = 103;
        Connection connection = new Oracle().getConnection();
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
    
    public void back(ActionEvent event) throws IOException{
        
//            Stage stage = new Stage();
//            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//            //stage.setTitle("Hello World");
//            stage.initStyle(StageStyle.UNDECORATED);
//            stage.setScene(new Scene(root, 800, 500));
//            stage.show();
            
            //Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/login/FXMLDocument.fxml"));
        //stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    public void exit(){
        System.exit(0);
    }


}
