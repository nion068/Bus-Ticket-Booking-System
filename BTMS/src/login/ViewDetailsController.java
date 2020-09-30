package login;

import Connection.Oracle;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nion on 1/16/2018.
 */
public class ViewDetailsController {

    @FXML
    private TableView detailsTable;

    @FXML
    private JFXButton bookinginfoPdf;


    int bus_id, route_id, schedule_id;
    String date;

    ObservableList<ViewDetails> list = FXCollections.observableArrayList();

    public void setData(int bus_id, int route_id, int schedule_id, String date) throws SQLException{
        this.bus_id = bus_id;
        this.route_id = route_id;
        this.schedule_id = schedule_id;
        this.date = date;

        TableColumn seatNo = new TableColumn("Seat No");
        TableColumn userName = new TableColumn("Ticket Holder Name");
        TableColumn contactNo = new TableColumn("Contact Number");

        detailsTable.getColumns().addAll(seatNo, userName, contactNo);

        String sql = "SELECT b.SEAT_NO, c.CUSTOMER_NAME, c.MOBILE_NO FROM BOOKING b, CUSTOMER c  WHERE b.BUS_ID = ? and b.ROUTE_ID = ? and b.SCHEDULE_ID = ? and b.JOURNEY_DATE = to_date(?, 'YYYY-MM-DD') and c.USER_ID = b.USER_ID";
        Connection con = new Oracle().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, bus_id);
        pst.setInt(2, route_id);
        pst.setInt(3, schedule_id);
        pst.setString(4, date);

        ResultSet rs = pst.executeQuery();
        while (rs.next()){
            list.add(new ViewDetails(rs.getString(1), rs.getString(2), rs.getString(3)));
        }

        seatNo.setCellValueFactory(new PropertyValueFactory<>("seatNo"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        contactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));

        detailsTable.setItems(list);

    }

    @FXML
    void bookinginfoPdfClicked(ActionEvent event) {
        String FILE = "F:/Class Materials/L2T2/CSE 216 - Database (Sessional)/Bus Ticket Booking Management/ticketPdf/" + Integer.toString(bus_id) + "_" +  Integer.toString(route_id) + "_" +  Integer.toString(schedule_id) + "_" +  date + ".pdf";

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


    private void addContent(Document document) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA);
        font.setSize(30);
        Paragraph head = new Paragraph("Trip Report", font);
        head.setAlignment(Element.ALIGN_CENTER);

        document.add(head);

        Paragraph busInfo = new Paragraph("Bus Id : " + Integer.toString(bus_id) + "     Route Id : " +  Integer.toString(route_id) + "   Schedule Id : " +  Integer.toString(schedule_id) + "   Journey Date : " +  date + "   Total Passenger : " + list.size());
        busInfo.setSpacingBefore(30f);
        busInfo.setSpacingAfter(30f);
        busInfo.setAlignment(Element.ALIGN_CENTER);


        document.add(busInfo);

        PdfPTable detailsTable = new PdfPTable(3);

        PdfPCell head1 = new PdfPCell(new Phrase("Seat No"));
        head1.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell head2 = new PdfPCell(new Phrase("Ticket Holder Name"));
        head2.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell head3 = new PdfPCell(new Phrase("Contact Number"));
        head3.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailsTable.addCell(head1);
        detailsTable.addCell(head2);
        detailsTable.addCell(head3);

        for(ViewDetails data : list){
            PdfPCell cell1 = new PdfPCell(new Phrase(data.getSeatNo()));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(new Phrase(data.getUserName()));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell3 = new PdfPCell(new Phrase(data.getContactNo()));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            detailsTable.addCell(cell1);
            detailsTable.addCell(cell2);
            detailsTable.addCell(cell3);
        }

        document.add(detailsTable);


    }

}
