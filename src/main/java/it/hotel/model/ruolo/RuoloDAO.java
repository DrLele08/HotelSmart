package it.hotel.model.ruolo;

import it.hotel.Utility.Connect;
import it.hotel.model.ruolo.ruoloExceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fornisce l'accesso al database per {@link Ruolo}.
 */
public class RuoloDAO {

    /**
     * Inserisce nel database l'oggetto Ruolo specificato.
     * @param ruolo Ruolo da inserire nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public void doInsert(Ruolo ruolo) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Ruolo (ruolo) VALUES(?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, ruolo.getRuolo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recupera l'oggetto Ruolo trovato nel database secondo il valore specificato.
     * @param con Connessione al database
     * @param ruoloStr Stringa che identifica il ruolo cercato
     * @return Il ruolo trovato nel database
     * @throws RuoloNotFoundException Il ruolo cercato non è presente nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public Ruolo doSelectByRuolo(Connection con, String ruoloStr) throws RuoloNotFoundException, SQLException {
        Ruolo ruolo;
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Ruolo WHERE ruolo=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ruoloStr);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return createRuolo(rs);
        } else {
            throw new RuoloNotFoundException();
        }
    }

    /**
     * Recupera l'oggetto Ruolo trovato nel database secondo il valore specificato.
     * @param idRuolo Id che identifica il ruolo cercato
     * @return Il ruolo trovato nel database
     * @throws RuoloNotFoundException Il ruolo cercato non è presente nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public Ruolo doSelectById(int idRuolo) throws RuoloNotFoundException {
        Ruolo ruolo;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Ruolo WHERE idRuolo=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idRuolo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ruolo = createRuolo(rs);
            } else {
                throw new RuoloNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ruolo;
    }

    /**
     * Recupera tutti gli oggetti Ruolo presenti nel database.
     * @param con Connessione al database
     * @return I ruoli presenti nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public List<Ruolo> doGetAll(Connection con) throws SQLException {
        ArrayList<Ruolo> ruoli = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Ruolo",
                        Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ruoli.add(createRuolo(rs));
        }
        return ruoli;
    }

    private Ruolo createRuolo(ResultSet rs) throws SQLException {
        return new Ruolo(rs.getInt(1), rs.getString(2));
    }

}
