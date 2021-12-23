package it.hotel.model.prenotazioneStanza;

import it.hotel.Utility.Connect;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fornisce l'accesso al database per {@link PrenotazioneStanza}.
 */
public class PrenotazioneStanzaDAO {

    public final static int UTENTE = 0;
    public final static int STANZA = 1;
    public final static int STATO = 2;

    /**
     * Inserisce nel database e ritorna un oggetto {@link PrenotazioneStanza} secondo i valori specificati.
     * @param ksUtente
     * @param ksStanza
     * @param ksStato
     * @param dataInizio
     * @param dataFine
     * @param prezzoFinale
     * @param tokenStripe
     * @param tokenQr
     * @param commenti
     * @param valutazione
     */
    public PrenotazioneStanza doInsert(int ksUtente, int ksStanza, int ksStato, Date dataInizio, Date dataFine,
            double prezzoFinale, String tokenStripe, String tokenQr, String commenti, int valutazione) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO PrenotazioneStanza (ksUtente, ksStanza," +
                            " ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr," +
                                    " commenti, valutazione) VALUES(?,?,?,?,?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ksUtente);
            ps.setInt(2, ksStanza);
            ps.setInt(3, ksStato);
            ps.setDate(4, dataInizio);
            ps.setDate(5, dataFine);
            ps.setDouble(6, prezzoFinale);
            ps.setString(7, tokenStripe);
            ps.setString(8, tokenQr);
            ps.setString(9, commenti);
            ps.setInt(10, valutazione);
            ResultSet rs = ps.executeQuery();

            return new PrenotazioneStanza(rs.getInt(1), ksUtente, ksStanza, ksStato, dataInizio, dataFine,
                    prezzoFinale, tokenStripe, tokenQr, commenti, valutazione);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recupera un oggetto {@link PrenotazioneStanza} dal database.
     * @param idPrenotazioneStanza È l'identificativo dell'oggetto da recuperare dal database
     * @return Ritorna l'oggetto recuperato dal database
     * @exception PrenotazioneStanzaNotFoundException L'oggetto non è presente nel database
     * @see PrenotazioneStanzaNotFoundException
     */
    public PrenotazioneStanza doSelectById(int idPrenotazioneStanza)
            throws PrenotazioneStanzaNotFoundException {
        PrenotazioneStanza prenotazioneStanza;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM PrenotazioneStanza WHERE idPrenotazioneStanza=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idPrenotazioneStanza);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                prenotazioneStanza = createPrenotazioneStanza(rs);
            } else {
                throw new PrenotazioneStanzaNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prenotazioneStanza;
    }

    /**
     * Recupera oggetti {@link PrenotazioneStanza} dal database.
     * @param value È il valore identificativo degli oggetti da recuperare dal database
     * @param type È il tipo del valore identificativo
     * @return Ritorna gli oggetti recuperati dal database
     */
    public List<PrenotazioneStanza> doSelectBy(int value, int type) {
        ArrayList<PrenotazioneStanza> prenotazioniStanza = new ArrayList<>();
        String str = "";
        switch (type) {
            case UTENTE:
                str = "SELECT * FROM PrenotazioneStanza WHERE ksUtente=?";
                break;
            case STANZA:
                str = "SELECT * FROM PrenotazioneStanza WHERE ksStanza=?";
                break;
            case STATO:
                str = "SELECT * FROM PrenotazioneStanza WHERE ksStato=?";
                break;
        }
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    (str, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, value);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prenotazioniStanza.add(createPrenotazioneStanza(rs));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prenotazioniStanza;
    }

    private PrenotazioneStanza createPrenotazioneStanza(ResultSet rs) throws SQLException {
        return new PrenotazioneStanza(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                rs.getInt(4), rs.getDate(5), rs.getDate(6), rs.getDouble(7), rs.getString(8),
                rs.getString(9), rs.getString(10), rs.getInt(11));
    }

}
