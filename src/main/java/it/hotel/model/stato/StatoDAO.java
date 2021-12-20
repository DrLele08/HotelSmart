package it.hotel.model.stato;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatoDAO {

    public static Stato doSelectByStato(String statoStr) {
        Stato stato = null;
        Statement st;
        ResultSet rs;
        statoStr = "\'" + statoStr + "\';";
        try (Connection con = Connect.getConnection()) {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Stato WHERE Stato=" + statoStr);
            if (rs.next()) {
                stato = new Stato();
                stato.setIdStato(rs.getInt(1));
                stato.setStato(rs.getString(2));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stato;
    }

    public static Stato doSelectById(int idStato) {
        Stato stato = null;
        Statement st;
        ResultSet rs;
        try (Connection con = Connect.getConnection()) {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Stato WHERE idStato=" + idStato + ";");
            if (rs.next()) {
                stato = new Stato();
                stato.setIdStato(rs.getInt(1));
                stato.setStato(rs.getString(2));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stato;
    }

}
