/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;

/**
 *
 * @author Nayeem
 */
public class Example extends javafx.application.Application
{
    //private static Users instance; 
    
//    private Users()
//    {
//    }
    
//    public static Users getInstance()
//    {
//        if (instance == null)
//        {
//            instance = new Users();
//        }
//        return instance;
//    }
    public boolean validateLogin(String userName, String password)
    {
        boolean success = false;
        String sql = "SELECT * FROM customer WHERE EMAIL = ? AND PASSWORD=?";
        
        try{
            Connection con = new Oracle().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                success = true;
            }
            pst.close();
            con.close();
        }
        catch(Exception e)
        {
            
        }
        return success;
    }
    public boolean validateLogin2(String userName, String password)
    {
        boolean success = false;
        String sql = "SELECT * FROM OPERATOR WHERE OPERATOR_NAME = ? AND ADMIN_PASSWORD=?";
        
        try{
            Connection con = new Oracle().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                success = true;
            }
            pst.close();
            con.close();
        }
        catch(Exception e)
        {
            
        }
        return success;
    }
    public List<List<String>> getAllUsers()
    {
        String sql = "SELECT * FROM USERS";
        List<List<String>> resultList = new ArrayList<>();
        try{
            Connection con = new Oracle().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while (rs.next())
            {
                List<String> row = new ArrayList<>();
                row.add(rs.getString("USERNAME"));
                row.add(rs.getString("PASSWORD"));
                row.add(rs.getString("FULLNAME"));
                resultList.add(row);
            }
            pst.close();
            con.close();
        }
        catch(Exception e)
        {
            
        }
        return resultList;
    }
    
    public static void main(String[] args) throws SQLException{
        Connection con = new Oracle().getConnection();
        String insert = "insert into customer values( ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(insert);
        pst.setInt(1, 101);
        pst.setString(2, "Asif");
        pst.setString(3, "Male");
        pst.setString(4, "Rajshahi");
        pst.setString(5, "01521300476");
        pst.setString(6, "asif@gmail.com");
        pst.setString(7, "hello");
        
        pst.execute();
        pst.close();
        con.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}