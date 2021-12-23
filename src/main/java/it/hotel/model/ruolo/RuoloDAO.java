package it.hotel.model.ruolo;

import it.hotel.Utility.Connect;
import it.hotel.model.ruolo.ruoloExceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RuoloDAO {

    public void doInsert(Ruolo ruolo) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Ruolo (ruolo) VALUES(?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, ruolo.getRuolo());

            ResultSet rs = ps.executeQuery();
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ruolo doSelectByRuolo(String ruoloStr) throws RuoloNotFoundException {
        Ruolo ruolo;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Ruolo WHERE ruolo=?",
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

    public List<Ruolo> doGetAll() {
        ArrayList<Ruolo> ruoli = new ArrayList<>();
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Ruolo",
                            Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ruoli.add(new Ruolo(rs.getInt(1), rs.getString(2)));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ruoli;
    }

}
