package it.hotel.model.prenotazioneServizio;

import it.hotel.Utility.Connect;

import java.sql.*;

/**
 * Fornisce l'accesso al database per {@link PrenotazioneServizio}.
 */
public class PrenotazioneServizioDAO {

    /**
     * Inserisce nel database e ritorno un oggetto PrenotazioneServizio secondo i valori specificati.
     * @param ksPrenotazioneStanza Identificativo della prenotazione stanza associata
     * @param ksServizio Identificativo del servizio
     * @param numPersone Numero delle persone
     * @param dataPrenotazioneServizio Data della prenotazione del servizio
     * @return L'oggetto inserito nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public PrenotazioneServizio doInsert(int ksPrenotazioneStanza, int ksServizio, int numPersone, Date dataPrenotazioneServizio) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO PrenotazioneServizio (ksPrenotazioneStanza, ksServizio, numPersone, " +
                                    "dataPrenotazioneServizio) VALUES(?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ksPrenotazioneStanza);
            ps.setInt(2, ksServizio);
            ps.setInt(3, numPersone);
            ps.setDate(4, dataPrenotazioneServizio);
            ps.executeUpdate();
            int id;
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                return null;
            }
            return new PrenotazioneServizio(id, ksPrenotazioneStanza, ksServizio, numPersone, dataPrenotazioneServizio);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina un oggetto PrenotazioneServizio dal database.
     * @param con Connessione al database
     * @param idPrenotazione L'identificativo della prenotazione da eliminare
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doDelete(Connection con, int idPrenotazione) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("DELETE FROM PrenotazioneServizio WHERE idPrenotazioneServizio=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idPrenotazione);
        ps.executeUpdate();
    }

    private PrenotazioneServizio createPrenotazioneServizio(ResultSet rs) throws SQLException {
        return new PrenotazioneServizio(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5));
    }

}
