package it.hotel.model.servizio;

import it.hotel.Utility.Connect;
import it.hotel.model.utente.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fornisce l'accesso al database per {@link Servizio}.
 */
public class ServizioDAO {

    /**
     * Recupera tutti gli oggetti Servizio presenti nel database.
     * @return I servizi presenti nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public List<Servizio> getServizi() {
        ArrayList<Servizio> servizi = new ArrayList<>();
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Servizio",
                            Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                servizi.add(new Servizio(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getDouble(5), rs.getInt(6)));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return servizi;
    }

    /**
     * Recupera gli oggetti Servizio trovati nel database secondo il valore specificato.
     * @param idUtente Identificativo dell'{@link Utente}
     * @return I servizi trovati nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public List<Servizio> doSelectByUserId(int idUtente) {
        ArrayList<Servizio> servizi = new ArrayList<>();
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT servizio.* FROM Servizio, Utente WHERE idUtente=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                servizi.add(new Servizio(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getDouble(5), rs.getInt(6)));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return servizi;
    }

}
