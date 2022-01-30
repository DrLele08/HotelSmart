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
     * Inserisce nel database e ritorna un oggetto PrenotazioneStanza secondo i valori specificati
     * e con stato "IN ATTESA DI PAGAMENTO".
     * @param ksUtente Utente che effettua la prenotazione
     * @param ksStanza Stanza prenotata
     * @param dataInizio Data di entrata
     * @param dataFine Data di uscita
     * @param prezzoFinale Prezzo finale
     * @param commenti Commenti
     * @param valutazione Valutazione
     * @return L'oggetto inserito nel database
     * @exception PrenotazioneStanzaInsertException Non è possibile effettuare l'inserimento nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public PrenotazioneStanza doInsert(int ksUtente, int ksStanza, Date dataInizio, Date dataFine,
                                       double prezzoFinale, String commenti, int valutazione)
            throws PrenotazioneStanzaInsertException {

        /*
        Se esistono prenotazioni per la stessa stanza, in un arco temporale anche solo parzialmente sovrapposto,
        e con stato diverso da ANNULLATA, ARCHIVIATA o RIMBORSATA, lancio PrenotazioneStanzaInsertException().
        */
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT idPrenotazioneStanza FROM PrenotazioneStanza WHERE " +
                            "ksStanza=? AND dataFine >= ? AND dataInizio <= ? AND ksStato NOT IN (SELECT idStato FROM Stato st WHERE " +
                            "(st.stato = 'ANNULLATA') OR (st.stato = 'ARCHIVIATA') OR (st.stato = 'RIMBORSATA'))",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ksStanza);
            ps.setDate(2, dataInizio);
            ps.setDate(3, dataFine);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                throw new PrenotazioneStanzaInsertException();
            }

            //recupero l'idStato dello stato 'IN ATTESA DI PAGAMENTO';
            ps = con.prepareStatement("SELECT idStato FROM Stato st " +
                    "WHERE (st.stato = \'IN ATTESA DI PAGAMENTO\') LIMIT 1",
                    Statement.RETURN_GENERATED_KEYS);
            rs = ps.executeQuery();
            int ksStato;
            if (rs.next()) {
                ksStato = rs.getInt(1);
            } else {
                throw new PrenotazioneStanzaInsertException();
            }

            //se l'utente non ha altre prenotazioni 'IN ATTESA DI PAGAMENTO', inserisco la nuova prenotazione con questo stato;
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
            ps.setString(7, null);
            ps.setString(8, null);
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
                    prezzoFinale, null, null, commenti, valutazione);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserisce nell'oggetto PrenotazioneStanza trovato nel database secondo il valore specificato il relativo Token Stripe.
     * @param idPrenotazioneStanza L'identificativo dell'oggetto da trovare nel database
     * @param tokenStripe Il Token Stripe da inserire nell'oggetto
     * @throws PrenotazioneStanzaNotFoundException L'oggetto non è presente nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public void insertTokenStripe(int idPrenotazioneStanza, String tokenStripe) throws PrenotazioneStanzaNotFoundException {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("UPDATE PrenotazioneStanza SET tokenStripe=? WHERE idPrenotazioneStanza=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tokenStripe);
            ps.setInt(2, idPrenotazioneStanza);

            int rs = ps.executeUpdate();
            if (rs==0) {
                throw new PrenotazioneStanzaNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserisce nell'oggetto PrenotazioneStanza trovato nel database secondo il valore specificato il relativo Token Qr Code.
     * @param idPrenotazione L'identificativo dell'oggetto da trovare nel database
     * @param tokenQr Il Token Qr Code da inserire nell'oggetto
     * @throws PrenotazioneStanzaNotFoundException L'oggetto non è presente nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public void doInsertTokenQrCode(int idPrenotazione, String tokenQr) throws PrenotazioneStanzaNotFoundException {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("UPDATE PrenotazioneStanza SET tokenQr=? WHERE idPrenotazioneStanza=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tokenQr);
            ps.setInt(2, idPrenotazione);

            int rs = ps.executeUpdate();
            if (rs==0) {
                throw new PrenotazioneStanzaNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recupera l'oggetto PrenotazioneStanza trovato nel database secondo il valore specificato.
     * @param idPrenotazioneStanza L'identificativo dell'oggetto da recuperare dal database
     * @return L'oggetto trovato nel database
     * @exception PrenotazioneStanzaNotFoundException L'oggetto non è presente nel database
     * @throws RuntimeException Errore nella comunicazione con il database
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
     * Recupera gli oggetti PrenotazioneStanza trovati nel database secondo i valori specificati.
     * @param value Il valore identificativo degli oggetti da recuperare dal database
     * @param type Il tipo del valore identificativo
     * @return Gli oggetti trovati nel database
     * @throws RuntimeException Errore nella comunicazione con il database
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
     * Recupera tutti gli oggetti PrenotazioneStanza presenti nel database.
     * @return Le prenotazioni stanza presenti nel database
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
                prenotazioni.add(createPrenotazioneStanza(rs));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prenotazioni;
    }

    /**
     * Modifica lo stato dell'oggetto PrenotazioneStanza trovato nel database secondo i valori specificati.
     * @param idPrenotazioneStanza Identificativo della prenotazione stanza da modificare
     * @param stato Stato da inserire nell'oggetto trovato
     * @exception PrenotazioneStanzaNotFoundException L'oggetto non è presente nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public void doChangeStato(int idPrenotazioneStanza, int stato) throws PrenotazioneStanzaNotFoundException {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("UPDATE PrenotazioneStanza SET ksStato=? WHERE idPrenotazioneStanza=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, stato);
            ps.setInt(2, idPrenotazioneStanza);
            int n=ps.executeUpdate();
            if (n==0) {
                throw new PrenotazioneStanzaNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Restituisce un informazione sulla rimborsabilità dell'oggetto PrenotazioneStanza specificato.
     * @param idPrenotazione Identificativo della prenotazione stanza cercata
     * @return Rimborsabilità della prenotazione
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public boolean isRimborsabile(int idPrenotazione) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT idStato FROM Stato s " +
                            "WHERE (s.stato = \'CONFERMATA\') LIMIT 1",
                    Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            int ksStato;
            if (rs.next()) {
                ksStato = rs.getInt(1);
            } else {
                return false;
            }

            ps = con.prepareStatement
                    ("SELECT count(*) FROM Prenotazionestanza WHERE idPrenotazioneStanza=? AND ksStato=? " +
                                    "AND dataInizio >= (CURDATE() + INTERVAL 14 DAY)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idPrenotazione);
            ps.setInt(2, ksStato);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1)==1) {
                return true;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private PrenotazioneStanza createPrenotazioneStanza(ResultSet rs) throws SQLException {
        return new PrenotazioneStanza(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                rs.getInt(4), rs.getDate(5), rs.getDate(6), rs.getDouble(7), rs.getString(8),
                rs.getString(9), rs.getString(10), rs.getInt(11));
    }

}
