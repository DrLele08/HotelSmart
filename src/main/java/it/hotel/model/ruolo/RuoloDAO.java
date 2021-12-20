package it.hotel.model.ruolo;

import it.hotel.Utility.Connect;
import it.hotel.model.ruolo.ruoloExceptions.*;

import java.sql.*;

public class RuoloDAO {

    public Ruolo doSelectByRuolo(String ruoloStr) throws RuoloNotFoundException {
        Ruolo ruolo;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Ruolo WHERE tipo=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, ruoloStr);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ruolo = new Ruolo(rs.getInt(1), rs.getString(2));
            } else {
                throw new RuoloNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ruolo;
    }

    public Ruolo doSelectById(int idRuolo) throws RuoloNotFoundException {
        Ruolo ruolo;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Ruolo WHERE idRuolo=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idRuolo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ruolo = new Ruolo(rs.getInt(1), rs.getString(2));
            } else {
                throw new RuoloNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ruolo;
    }

}
