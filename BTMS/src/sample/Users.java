    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nayeem
 */
public class Users
{
    private static Users instance;
    public Users()
    {
    }
    
/*//    public static Users getInstance()
    {
        if (instance == null)
        {
            instance = new Users();
        }
        return instance;
    }*/

    public boolean validateLogin(String userName, String password)
    {
        boolean success = false;
        String sql = "SELECT * FROM CUSTOMER WHERE EMAIL = ? AND PASSWORD=?";
        
        try{
            Connection con = new DBMSConnection().getConnection();
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
            Connection con = new DBMSConnection().getConnection();
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
    
}
