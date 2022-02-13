package it.hotel.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilityDAO
{
    public static Connection getConnessione() throws SQLException
    {
        return Connect.getConnection();
    }
    public static boolean isActive(String tipo)
    {
        try(Connection conn=UtilityDAO.getConnessione())
        {
            PreparedStatement pS=conn.prepareStatement("SELECT valore FROM utility WHERE tipo=?");
            pS.setString(1,tipo);
            ResultSet rS=pS.executeQuery();
            if(rS.next())
            {
                conn.close();
                return rS.getInt("valore")>0;
            }
            else
            {
                conn.close();
                return false;
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
