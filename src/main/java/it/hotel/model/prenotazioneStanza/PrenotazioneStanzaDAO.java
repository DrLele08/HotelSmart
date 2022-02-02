package it.hotel.model.prenotazioneStanza;

import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.*;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;

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
     * @param con Connessione al database
     * @param ksUtente Utente che effettua la prenotazione
     * @param ksStato Stato della prenotazione
     * @param ksStanza Stanza prenotata
     * @param dataInizio Data di entrata
     * @param dataFine Data di uscita
     * @param costoNotte Costo notte
     * @param sconto Sconto costo notte
     * @return L'oggetto inserito nel database
     * @exception PrenotazioneStanzaInsertException Non è possibile effettuare l'inserimento nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public int doInsert(Connection con, int ksUtente, int ksStato, int ksStanza, Date dataInizio, Date dataFine, double costoNotte, double sconto)
            throws PrenotazioneStanzaInsertException, SQLException, PrenotazioneStanzaNotFoundException {

        //se l'utente non ha altre prenotazioni 'IN ATTESA DI PAGAMENTO', inserisco la nuova prenotazione con questo stato;
        PreparedStatement ps = con.prepareStatement
                ("INSERT INTO PrenotazioneStanza (ksUtente, ksStanza," +
                                " ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr," +
                                " commenti, valutazione) SELECT ?,?,?,?,?,(SELECT DATEDIFF(?,?)*(?-?)),?,?,?,? FROM dual " +
                                "WHERE NOT EXISTS (SELECT * FROM PrenotazioneStanza WHERE ksUtente = ? AND " +
                                "ksStato = ?)",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ksUtente);
        ps.setInt(2, ksStanza);
        ps.setInt(3, ksStato);
        ps.setDate(4, dataInizio);
        ps.setDate(5, dataFine);
        ps.setDate(6, dataFine);
        ps.setDate(7, dataInizio);
        ps.setDouble(8, costoNotte);
        ps.setDouble(9, sconto);
        ps.setString(10, null);
        ps.setString(11, null);
        ps.setString(12, null);
        ps.setInt(13, -1);
        ps.setInt(14, ksUtente);
        ps.setInt(15, ksStato);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        int id;
        if (rs.next()) {
            id = rs.getInt(1);
        } else {
            throw new PrenotazioneStanzaInsertException();
        }
        return id;

    }

    /**
     * Inserisce nell'oggetto PrenotazioneStanza trovato nel database secondo il valore specificato il relativo Token Stripe.
     * @param con Connessione al database
     * @param idPrenotazioneStanza L'identificativo dell'oggetto da trovare nel database
     * @param tokenStripe Il Token Stripe da inserire nell'oggetto
     * @throws PrenotazioneStanzaNotFoundException L'oggetto non è presente nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void insertTokenStripe(Connection con, int idPrenotazioneStanza, String tokenStripe) throws PrenotazioneStanzaNotFoundException, SQLException {
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

    /**
     * Inserisce nell'oggetto PrenotazioneStanza trovato nel database secondo il valore specificato il relativo Token Qr Code.
     * @param con Connessione al database
     * @param idPrenotazione L'identificativo dell'oggetto da trovare nel database
     * @param tokenQr Il Token Qr Code da inserire nell'oggetto
     * @throws PrenotazioneStanzaNotFoundException L'oggetto non è presente nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doInsertTokenQrCode(Connection con, int idPrenotazione, String tokenQr) throws PrenotazioneStanzaNotFoundException, SQLException {
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

    /**
     * Recupera l'oggetto PrenotazioneStanza trovato nel database secondo il valore specificato.
     * @param con Connessione al database
     * @param idPrenotazioneStanza L'identificativo dell'oggetto da recuperare dal database
     * @return L'oggetto trovato nel database
     * @exception PrenotazioneStanzaNotFoundException L'oggetto non è presente nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public PrenotazioneStanza doSelectById(Connection con, int idPrenotazioneStanza)
            throws PrenotazioneStanzaNotFoundException, SQLException, UtenteNotFoundException {
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM PrenotazioneStanza WHERE idPrenotazioneStanza=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idPrenotazioneStanza);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return createPrenotazioneStanza(rs);
        } else {
            throw new PrenotazioneStanzaNotFoundException();
        }
    }

    /**
     * Recupera gli oggetti PrenotazioneStanza trovati nel database secondo i valori specificati.
     * @param con Connessione al database
     * @param value Il valore identificativo degli oggetti da recuperare dal database
     * @param type Il tipo del valore identificativo
     * @return Gli oggetti trovati nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public List<PrenotazioneStanza> doSelectBy(Connection con, int value, int type) throws SQLException, UtenteNotFoundException {
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
        PreparedStatement ps = con.prepareStatement
                (str, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, value);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            prenotazioniStanza.add(createPrenotazioneStanza(rs));
        }
        return prenotazioniStanza;
    }

    /**
     * Recupera tutti gli oggetti PrenotazioneStanza presenti nel database.
     * @param con Connessione al database
     * @return Le prenotazioni stanza presenti nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public List<PrenotazioneStanza> doGetAll(Connection con) throws SQLException, UtenteNotFoundException {
        ArrayList<PrenotazioneStanza> prenotazioni = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Prenotazionestanza",
                        Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            prenotazioni.add(createPrenotazioneStanza(rs));
        }
        return prenotazioni;
    }

    /**
     * Modifica lo stato dell'oggetto PrenotazioneStanza trovato nel database secondo i valori specificati.
     * @param con Connessione al database
     * @param idPrenotazioneStanza Identificativo della prenotazione stanza da modificare
     * @param stato Stato da inserire nell'oggetto trovato
     * @exception PrenotazioneStanzaNotFoundException L'oggetto non è presente nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public void doChangeStato(Connection con, int idPrenotazioneStanza, int stato) throws PrenotazioneStanzaNotFoundException, SQLException {
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

    /**
     * Restituisce un informazione sulla rimborsabilità dell'oggetto PrenotazioneStanza specificato.
     * @param con Connessione al database
     * @param idPrenotazione Identificativo della prenotazione stanza cercata
     * @param confermata Identificativo dello stato "CONFERMATA"
     * @return Rimborsabilità della prenotazione
     * @throws SQLException Errore nella comunicazione con il database
     */
    public boolean isRimborsabile(Connection con, int idPrenotazione, int confermata) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("SELECT count(*) FROM Prenotazionestanza WHERE idPrenotazioneStanza=? AND ksStato=? " +
                                "AND dataInizio >= (CURDATE() + INTERVAL 14 DAY)",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idPrenotazione);
        ps.setInt(2, confermata);
        ResultSet rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1)==1) {
            return true;
        }
        return false;
    }

    private PrenotazioneStanza createPrenotazioneStanza(ResultSet rs) throws SQLException, UtenteNotFoundException {
        return new PrenotazioneStanza(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                rs.getInt(4), rs.getDate(5), rs.getDate(6), rs.getDouble(7), rs.getString(8),
                rs.getString(9), rs.getString(10), rs.getInt(11));
    }

}
