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
     * Inserisce nel database un oggetto Servizio secondo i valori specificati.
     * @param nome Nome
     * @param descrizione Descrizione
     * @param foto Percorso foto
     * @param costo Costo
     * @param limitePosti Posti disponibili
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public void doInsert(String nome, String descrizione, String foto, double costo, int limitePosti) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Servizio (nome, descrizione, foto, costo, limitePosti) VALUES(?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nome);
            ps.setString(2, descrizione);
            ps.setString(3, foto);
            ps.setDouble(4, costo);
            ps.setInt(5, limitePosti);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Aggiorna un oggetto Servizio nel database secondo i valori specificati.
     * @param idServizio Identificativo
     * @param nome Nome
     * @param descrizione Descrizione
     * @param foto Percorso foto
     * @param costo Costo
     * @param limitePosti Posti disponibili
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public void doUpdate(int idServizio, String nome, String descrizione, String foto, double costo, int limitePosti) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("UPDATE Servizio SET nome=?, descrizione=?, foto=?, costo=?, " +
                                    "limitePosti=? WHERE idServizio=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nome);
            ps.setString(2, descrizione);
            ps.setString(3, foto);
            ps.setDouble(4, costo);
            ps.setInt(5, limitePosti);
            ps.setInt(6, idServizio);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
                servizi.add(createServizio(rs));
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
                servizi.add(createServizio(rs));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return servizi;
    }

    private Servizio createServizio(ResultSet rs) throws SQLException {
        return new Servizio(rs.getInt(1), rs.getString(2), rs.getString(3),
                rs.getString(4), rs.getDouble(5), rs.getInt(6));
    }

}
