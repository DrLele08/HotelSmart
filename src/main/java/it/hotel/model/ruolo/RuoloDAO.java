package it.hotel.model.ruolo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RuoloDAO {

    public static Ruolo doSelectByTipo(String tipo) {
        Ruolo ruolo = null;
        Statement st;
        ResultSet rs;
        tipo = "\'" + tipo + "\';";
        try (Connection con = Connect.getConnection()) {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Ruolo WHERE Tipo=" + tipo);
            if (rs.next()) {
                ruolo = new Ruolo();
                ruolo.setIdRuolo(rs.getInt(1));
                ruolo.setTipo(rs.getString(2));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ruolo;
    }

    public static Ruolo doSelectById(int idRuolo) {
        Ruolo ruolo = null;
        Statement st;
        ResultSet rs;
        try (Connection con = Connect.getConnection()) {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Ruolo WHERE idRuolo=" + idRuolo + ";");
            if (rs.next()) {
                ruolo = new Ruolo();
                ruolo.setIdRuolo(rs.getInt(1));
                ruolo.setTipo(rs.getString(2));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ruolo;
    }

}
