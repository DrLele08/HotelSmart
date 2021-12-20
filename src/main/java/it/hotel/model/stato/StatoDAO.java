package it.hotel.model.stato;

import it.hotel.Utility.Connect;
import it.hotel.model.stato.statoExceptions.*;

import java.sql.*;

public class StatoDAO {

    public static Stato doSelectByStato(String statoStr) throws StatoNotFoundException {
        Stato stato;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Stato WHERE Stato=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, statoStr);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stato = new Stato(rs.getInt(1), rs.getString(2));
            } else {
                throw new StatoNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stato;
    }

    public static Stato doSelectById(int idStato) throws StatoNotFoundException {
        Stato stato;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Stato WHERE idStato=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idStato);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stato = new Stato(rs.getInt(1), rs.getString(2));
            } else {
                throw new StatoNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stato;
    }

}
