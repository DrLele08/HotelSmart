package it.hotel.model.stanza;

import it.hotel.Utility.Connect;
import it.hotel.model.stanza.stanzaExceptions.*;

import java.sql.*;

public class StanzaDAO {

    public void doInsert(Stanza stanza) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Stanza (animaleDomestico, fumatore, lettiSingoli," +
                                    " lettiMatrimoniali, costoNotte, sconto) VALUES(?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, stanza.getAnimaleDomestico());
            ps.setBoolean(2, stanza.getFumatore());
            ps.setInt(3, stanza.getLettiSingoli());
            ps.setInt(4, stanza.getLettiMatrimoniali());
            ps.setDouble(5, stanza.getCostoNotte());
            ps.setDouble(6, stanza.getSconto());

            ResultSet rs = ps.executeQuery();
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Stanza doSelectById(int idStanza) throws StanzaNotFoundException {
        Stanza stanza;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Stanza WHERE idStanza=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idStanza);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stanza = new Stanza(rs.getInt(1), rs.getBoolean(2), rs.getBoolean(3),
                        rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7));
            } else {
                throw new StanzaNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stanza;
    }

}
