package it.hotel.model.prenotazioneStanza;

import it.hotel.Utility.Connect;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.*;
import it.hotel.model.stato.Stato;

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
     * Inserisce nel database e ritorna un oggetto {@link PrenotazioneStanza} secondo i valori specificati
     * e con stato "IN ATTESA DI PAGAMENTO".
     * @param ksUtente Utente che effettua la prenotazione
     * @param ksStanza Stanza prenotata
     * @param dataInizio Data di entrata
     * @param dataFine Data di uscita
     * @param prezzoFinale Prezzo finale
     * @param tokenStripe Token di Stripe
     * @param tokenQr Token del codice qr
     * @param commenti Commenti
     * @param valutazione Valutazione
     * @return Ritorna l'oggetto inserito nel database
     * @exception PrenotazioneStanzaInsertException Non è possibile effettuare l'inserimento nel database
     */
    public PrenotazioneStanza doInsert(int ksUtente, int ksStanza, Date dataInizio, Date dataFine,
                                       double prezzoFinale, String tokenStripe, String tokenQr, String commenti, int valutazione)
            throws PrenotazioneStanzaInsertException {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT idStato FROM Stato st " +
                    "WHERE (st.stato = \'IN ATTESA DI PAGAMENTO\') LIMIT 1",
                    Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            int ksStato;
            if (rs.next()) {
                ksStato = rs.getInt(1);
            } else {
                return null;
            }

            ps = con.prepareStatement
                    ("INSERT INTO PrenotazioneStanza (ksUtente, ksStanza," +
                                    " ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr," +
                                    " commenti, valutazione) SELECT ?,?,?,?,?,?,?,?,?,? FROM dual " +
                                    "WHERE NOT EXISTS (SELECT * FROM PrenotazioneStanza WHERE ksUtente = ? AND " +
                                    "ksStato = ?)",
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
            ps.setInt(11, ksUtente);
            ps.setInt(12, ksStato);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            int id;
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new PrenotazioneStanzaInsertException();
            }

            return new PrenotazioneStanza(id, ksUtente, ksStanza, ksStato, dataInizio, dataFine,
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

    /**
     * Recupera tutti gli oggetti {@link PrenotazioneStanza} trovati nel database.
     * @return Ritorna le prenotazioni stanza trovate nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public List<PrenotazioneStanza> doGetAll() {
        ArrayList<PrenotazioneStanza> prenotazioni = new ArrayList<>();
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Prenotazionestanza",
                            Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prenotazioni.add(new PrenotazioneStanza(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
                        rs.getDate(5), rs.getDate(6), rs.getDouble(7), rs.getString(8), rs.getString(9),
                        rs.getString(10), rs.getInt(11)));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prenotazioni;
    }

    private PrenotazioneStanza createPrenotazioneStanza(ResultSet rs) throws SQLException {
        return new PrenotazioneStanza(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                rs.getInt(4), rs.getDate(5), rs.getDate(6), rs.getDouble(7), rs.getString(8),
                rs.getString(9), rs.getString(10), rs.getInt(11));
    }

}
