package it.hotel.model.utente;

import it.hotel.Utility.Connect;
import it.hotel.model.utente.utenteExceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fornisce l'accesso al database per {@link Utente}.
 */
public class UtenteDAO {

    /**
     * Inserisce nel database e ritorna un oggetto Utente secondo i valori specificati.
     * @param con Connessione al database
     * @param ruolo Ruolo
     * @param cf Codice fiscale
     * @param nome Nome
     * @param cognome Cognome
     * @param email Email
     * @param dataNascita Data di nascita
     * @param tokenAuth Token di autenticazione
     * @param password Password
     * @return L'utente inserito nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public Utente doInsert(Connection con, int ruolo, String cf, String nome, String cognome,
                           String email, Date dataNascita, String tokenAuth, String password) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("INSERT INTO Utente (ksRuolo, cf, nome, cognome, email, password," +
                        " dataNascita, tokenAuth) VALUES(?,?,?,?,?,MD5(?),?,?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ruolo);
        ps.setString(2, cf);
        ps.setString(3, nome);
        ps.setString(4, cognome);
        ps.setString(5, email);
        ps.setString(6, password);
        ps.setDate(7, dataNascita);
        ps.setString(8, tokenAuth);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        int id;
        if (rs.next()) {
            id = rs.getInt(1);
        } else {
            return null;
        }
        return new Utente(id, ruolo, cf, nome, cognome, email, dataNascita, tokenAuth);
    }

    /**
     * Recupera un oggetto Utente dal database secondo email e password specificati.
     * @param con Connessione al database
     * @param email L'email dell'utente da recuperare
     * @param password La password dell'utente da recuperare
     * @return L'utente recuperato dal database
     * @throws PasswordNotValidException La password specificata non è esatta
     * @throws SQLException Errore nella comunicazione con il database
     */
    public Utente doAuthenticate(Connection con, String email, String password) throws PasswordNotValidException, SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Utente WHERE email=? AND password=MD5(?)",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return createUtente(rs);
        } else {
            throw new PasswordNotValidException();
        }
    }

    /**
     * Recupera un oggetto Utente dal database secondo idUtente e tokenAuth specificati.
     * @param con Connessione al database
     * @param idUtente L'identificativo dell'utente da recuperare
     * @param tokenAuth Il token di autenticazione dell'utente da recuperare
     * @return L'utente recuperato dal database
     * @throws UtenteNotFoundException Nel database non è presente un utente con i valori specificati
     * @throws SQLException Errore nella comunicazione con il database
     */
    public Utente doAuthenticate(Connection con, int idUtente, String tokenAuth) throws UtenteNotFoundException, SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Utente WHERE idUtente=? AND tokenAuth=?",
                Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        ps.setString(2, tokenAuth);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return createUtente(rs);
        } else {
            throw new UtenteNotFoundException();
        }
    }

    /**
     * Modifica la password di un oggetto Utente nel database.
     * @param con Connessione al database
     * @param idUtente L'identificativo dell'utente da modificare
     * @param newPassword La nuova password dell'utente da modificare
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doChangePassword(Connection con, int idUtente, String newPassword) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement
                ("UPDATE Utente SET password=MD5(?) WHERE idUtente=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, newPassword);
        ps.setInt(2, idUtente);
        ps.executeUpdate();
    }

    /**
     * Informa se la password e l'oggetto Utente specificati hanno una corrispondenza nel database.
     * @param con Connessione al database
     * @param idUtente L'identificativo dell'utente da cercare
     * @param password La password dell'utente
     * @throws SQLException Errore nella comunicazione con il database
     */
    public boolean isPasswordValid(Connection con, int idUtente, String password) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Utente WHERE idUtente=? AND password=MD5(?)",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    /**
     * Recupera il ruolo di un oggetto Utente nel database.
     * @param con Connessione al database
     * @param idUtente L'identificativo dell'utente da recuperare
     * @param tokenAuth Il token di autenticazione dell'utente da recuperare
     * @return Il ruolo dell'utente recuperato
     * @throws SQLException Errore nella comunicazione con il database
     */
    public int doGetRuolo(Connection con, int idUtente, String tokenAuth) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Utente WHERE idUtente=? AND tokenAuth=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        ps.setString(2, tokenAuth);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt("ksRuolo");
        else
            return -1;
    }

    /**
     * Modifica il ruolo di un oggetto Utente nel database.
     * @param con Connessione al database
     * @param idUtente L'identificativo dell'utente da modificare
     * @param ksRuolo Il nuovo ruolo dell'utente da modificare
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doChangeRuolo(Connection con, int idUtente, int ksRuolo) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("UPDATE Utente SET ksRuolo=? WHERE idUtente=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ksRuolo);
        ps.setInt(2, idUtente);
        ps.executeUpdate();
    }

    /**
     * Modifica l'anagrafica di un oggetto Utente nel database
     * @param con Connessione al database
     * @param idUtente L'identificativo dell'utente da modificare
     * @param tokenAuth Il token di autenticazione dell'utente da modificare
     * @param nome Il nome da inserire nell'oggetto Utente
     * @param cognome Il cognome da inserire nell'oggetto Utente
     * @param cf Il codice fiscale da inserire nell'oggetto Utente
     * @param dataNascita La data di nascita da inserire nell'oggetto Utente
     * @param email L'indirizzo email da inserire nell'oggetto Utente
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doChangeAnagrafica(Connection con, int idUtente, String tokenAuth, String nome, String cognome, String cf, Date dataNascita, String email) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("UPDATE Utente SET nome=?, tokenAuth=?, cognome=?, cf=?, dataNascita=?, email=? WHERE idUtente=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, nome);
        ps.setString(2, tokenAuth);
        ps.setString(3, cognome);
        ps.setString(4, cf);
        ps.setDate(5, dataNascita);
        ps.setString(6, email);
        ps.setInt(7, idUtente);
        ps.executeUpdate();
    }

    /**
     * Recupera dal database l'oggetto utente a cui corrisponde la prenotazione stanza specificata.
     * @param con Connessione al database
     * @param idPrenotazione L'identificativo della prenotazione stanza
     * @return L'utente trovato nel database
     * @throws UtenteNotFoundException L'utente cercato non è stato trovato
     * @throws SQLException Errore nella comunicazione con il database
     */
    public Utente doSelectByPrenotazioneStanza(Connection con, int idPrenotazione) throws UtenteNotFoundException, SQLException {
        PreparedStatement ps = con.prepareStatement
                ("SELECT Utente.* FROM Utente, PrenotazioneStanza WHERE idPrenotazioneStanza=? AND utente.idUtente=prenotazionestanza.ksUtente",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idPrenotazione);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return createUtente(rs);
        } else {
            throw new UtenteNotFoundException();
        }
    }

    /**
     * Recupera tutti gli oggetti Utente presenti nel database.
     * @param con Connessione al database
     * @return Gli utenti presenti nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public List<Utente> getUtenti(Connection con) throws SQLException {
        ArrayList<Utente> utenti = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Utente",
                        Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            utenti.add(createUtente(rs));
        }
        return utenti;
    }

    /**
     * Informa se l'email specificata è già presente in un oggetto Utente nel database.
     * @param con Connessione al database
     * @param email Email da cercare
     * @return Presenza dell'email nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public boolean isEmailInDatabase(Connection con, String email) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Utente WHERE email=?",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    /**
     * Informa se l'email specificata è già presente nell'oggetto Utente specificato.
     * @param con Connessione al database
     * @param idUtente L'identificativo dell'utente
     * @param email L'email da cercare
     * @return Presenza dell'email nell'oggetto utente
     * @throws SQLException Errore nella comunicazione con il database
     */
    public boolean isEmailOld(Connection con, int idUtente, String email) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Utente WHERE idUtente=? AND email=?",
                Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        ps.setString(2, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private Utente createUtente(ResultSet rs) throws SQLException {
        int idUtente = rs.getInt("idUtente");
        int ksRuolo = rs.getInt("ksRuolo");
        String cf = rs.getString("cf");
        String nome = rs.getString("nome");
        String cognome = rs.getString("cognome");
        String email = rs.getString("email");
        Date dataNascita = rs.getDate("dataNascita");
        String tokenAuth = rs.getString("tokenAuth");
        return new Utente(idUtente, ksRuolo, cf, nome, cognome, email, dataNascita, tokenAuth);
    }

}
