/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//import com.itextpdf.text.Document;

/**
 * FXML Controller class
 *
 * @author Asif
 */
public class ShowTicketController implements Initializable {

    @FXML
    private Label ticket_id;

    @FXML
    private Label userName;

    @FXML
    private Label from;

    @FXML
    private Label to;

    @FXML
    private Label date;

    @FXML
    private Label operator;

    @FXML
    private Label model;

    @FXML
    private Label seat;

    @FXML
    private Label boarding;

    @FXML
    private JFXButton bookAgain;

    @FXML
    private JFXButton getPdf;

    @FXML
    private Label fare;
    int userId;

    private static String FILE = "C:/Users/nion/Desktop/ticket.pdf";

    public void ticketInfo(int ticket_id, int userId,  String userName, String from, String to, String date, String operator, String model, String seats, int fare, String boarding){
        this.userId = userId;
        this.userName.setText(userName);
        this.ticket_id.setText(Integer.toString(ticket_id));
        this.from.setText(from);
        this.to.setText(to);
        this.date.setText(date);
        this.operator.setText(operator);
        this.model.setText(model);
        this.seat.setText(seats);
        this.boarding.setText(boarding);
        this.fare.setText(Integer.toString(fare));
    }
    
    @FXML
    void bookAgainClicked(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/main.fxml"));
        Parent root = (Parent) loader.load();

        
//        Parent root = FXMLLoader.load(getClass().getResource("/login/main.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        MainController mainController = loader.<MainController>getController();
        mainController.setUserIdName(userName.getText(), userId);
        stage.show();
    }

    @FXML
    public void getPdfClicked(ActionEvent event) {

        String FILE = "F:/Class Materials/L2T2/CSE 216 - Database (Sessional)/Bus Ticket Booking Management/ticketPdf/" + userName.getText().toString()+"_"+ticket_id.getText().toString() + ".pdf";

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File folder = new File(":/Class Materials/L2T2/CSE 216 - Database (Sessional)/Bus Ticket Booking Management/ticketPdf/");
        File pdf = new File(FILE);

        Desktop d = null;

        if(Desktop.isDesktopSupported()){
            d = Desktop.getDesktop();

            try {
                //d.open(folder);
                d.open(pdf);
            } catch (IOException io){
                io.printStackTrace();
            }
        }


    }

    Font font = new Font(Font.FontFamily.COURIER, 13);

    private void addContent(Document document) throws DocumentException {

        String banner = "src/login/image/banner.jpg";
        Image image;
        try{
            image = Image.getInstance(banner);
            image.setAlignment(Image.ALIGN_CENTER);
            document.add(image);
        } catch(IOException io){
            io.printStackTrace();
        }

        //Font font = new Font(Font.FontFamily.COURIER, 13);


        //Anchor anchor = new Anchor("Ticket Details");
        //anchor.setFont(font);
        document.add(new Paragraph("\n\n"));

        PdfPTable table = new PdfPTable(2);

        setTable(table, "Ticket_Id : ",ticket_id.getText());
        setTable(table, "User_Id : " , Integer.toString(userId));
        setTable(table, "User Name : " , userName.getText());
        setTable(table, "From : " , from.getText());
        setTable(table, "To : " , to.getText());
        setTable(table, "Journey Date : " , date.getText());
        setTable(table, "Operator : " , operator.getText());
        setTable(table, "Model : " , model.getText());
        setTable(table, "Seat : " , seat.getText());
        setTable(table, "Boarding Point : " , boarding.getText());
        setTable(table, "Total Fare : " , fare.getText());

        document.add(table);

        Paragraph note = new Paragraph("\n\n\n\n\nPlease show the ticket in the counter and verify your ticket and pay the fare.", font);
        note.setAlignment(note.ALIGN_CENTER);
        document.add(note);



        /*Paragraph info = new Paragraph("Ticket_Id : " + ticket_id.getText(), font);
        //info.setFont(font);
        info.setAlignment(Paragraph.ALIGN_CENTER);
        //anchor.add(info);

        document.add(info);*/


        /*Anchor anchor = new Anchor("Ticket Details");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("");

        subPara.setAlignment(Paragraph.ALIGN_CENTER);
        Section subCatPart;

        subCatPart = catPart.addSection(subPara);

        Font font = new Font(Font.FontFamily.COURIER, 20);

        subCatPart.add(new Paragraph(Paragraph.ALIGN_CENTER,"Ticket_Id : " + ticket_id.getText(), font));
        subCatPart.add(new Paragraph("User_Id : " + userId));
        subCatPart.add(new Paragraph("User Name : " + userName.getText()));
        subCatPart.add(new Paragraph("From : " + from.getText()));
        subCatPart.add(new Paragraph("To : " + to.getText()));
        subCatPart.add(new Paragraph("Journey Date : " + date.getText()));
        subCatPart.add(new Paragraph("Operator : " + operator.getText()));
        subCatPart.add(new Paragraph("Model : " + model.getText()));
        subCatPart.add(new Paragraph("Seat : " + seat.getText()));
        subCatPart.add(new Paragraph("Boarding Point : " + boarding.getText()));
        subCatPart.add(new Paragraph("Total Fare : " + fare.getText()));


        // add a list
        Paragraph paragraph = new Paragraph();
        subCatPart.add(paragraph);

        // add a table

        // now add all this to the document
        document.add(catPart);

        // Next section

        document.add(catPart);
*/
    }

    public  void setTable (PdfPTable pdfPTable ,String lebel, String info) throws DocumentException{
        PdfPCell cell1 = new PdfPCell(new Phrase(lebel));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(cell1);
        PdfPCell cell2 = new PdfPCell(new Phrase(info));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(cell2);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
