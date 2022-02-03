package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.Utility.Utility;
import it.hotel.controller.exception.PermissionDeniedException;
import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.UtenteDAO;
import it.hotel.model.utente.utenteExceptions.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Fornisce metodi di utilizzo del database per {@link Utente}.
 */
public class UtenteService
{
    private final UtenteDAO dao;

    /**
     * Costruisce un oggetto UtenteService.
     */
    public UtenteService()
    {
        dao=new UtenteDAO();
    }

    /**
     * Effettua il login dell'utente secondo indirizzo email e password specificati.
     * @param email  Email inserita dall'utente
     * @param pwd Password inserita dall'utente
     * @return Utente trovato
     * @throws  EmailNotFoundException L'email specificata non è stata trovata nel database
     * @throws PasswordNotValidException La password specificata non è esatta
     * @throws SQLException Errore nella comunicazione con il database
     * @throws IllegalArgumentException Dati non validi
     * @see Utente
     */
    public Utente doLogin(String email, String pwd) throws EmailNotFoundException, PasswordNotValidException, SQLException {
        if(!(email.trim().isEmpty() || pwd.trim().isEmpty()))
        {
            Utente utente;
            Connection con = null;
            try {
                con = Connect.getConnection();
                con.setAutoCommit(false);

                if (!dao.isEmailInDatabase(con, email)) {
                    throw new EmailNotFoundException();
                }
                utente = dao.doAuthenticate(con, email, pwd);

                con.commit();
                con.setAutoCommit(true);
                return utente;
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                throw new RuntimeException();
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Effettua il login dell'utente secondo identificativo e token specificati.
     * @param idUtente Identificativo dell'utente
     * @param tokenAuth Token dell'utente
     * @return Utente trovato
     * @throws UtenteNotFoundException L'utente cercato non è stato trovato
     * @throws IllegalArgumentException Dati non validi
     * @see Utente
     */
    public Utente doLogin(int idUtente,String tokenAuth) throws UtenteNotFoundException {
        if(!tokenAuth.trim().isEmpty())
        {
            try (Connection con = Connect.getConnection()) {
                return dao.doAuthenticate(con, idUtente, tokenAuth);
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Effettua la registrazione di un nuovo utente secondo i valori specificati.
     * @param cf Codice Fiscale utente
     * @param nome Nome utente
     * @param cognome Cognome utente
     * @param email  Email utente
     * @param data Data nascita utente
     * @param pwd Password utente
     * @return Utente registrato
     * @throws EmailAlreadyExistingException L'email specificata è già presente nel database
     * @throws PasswordNotValidException La password specificata non è valida
     * @throws SQLException Errore nella comunicazione con il database
     * @throws IllegalArgumentException Dati non validi
     * @see Utente
     */
    public Utente doRegistrazione(String cf, String nome, String cognome, String email, Date data,String pwd) throws EmailAlreadyExistingException, PasswordNotValidException, SQLException {
        if(cf.length()==16 && !nome.trim().isEmpty() && !cognome.trim().isEmpty() && !email.trim().isEmpty() && !pwd.trim().isEmpty())
        {
            Utente utente;
            Connection con = null;
            try
            {
                con = Connect.getConnection();
                con.setAutoCommit(false);

                Pattern pattern = Pattern.compile(Utility.PASSWORD_PATTERN);
                if(pattern.matcher(pwd).matches())
                {
                    boolean useLetters = true;
                    boolean useNumbers = false;
                    String generatedString = RandomStringUtils.random(Utility.lenghtAuth, useLetters, useNumbers);
                    if (dao.isEmailInDatabase(con, email)) {
                        throw new EmailAlreadyExistingException();
                    }
                    utente = dao.doInsert(con, new RuoloService().getByRuolo("UTENTE"),cf,nome,cognome,email,data,generatedString,pwd);
                }
                else
                {
                    throw new PasswordNotValidException();
                }

                con.commit();
                con.setAutoCommit(true);
                return utente;
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                throw new RuntimeException(e);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Effettua la modifica della password di un utente con la nuova password specificata.
     * @param idUtente Identificativo dell'utente
     * @param token Token di autenticazione utente
     * @param oldPwd Vecchia password dell'utente
     * @param newPwd Nuova password utente
     * @throws PasswordNotValidException La password specificata non è valida
     * @throws UtenteNotFoundException L'utente cercato non è stato trovato
     * @throws PermissionDeniedException Non si dispone dei privilegi necessari
     * @throws SQLException Errore nella comunicazione con il database
     * @throws IllegalArgumentException Dati non validi
     */
    public void editPassword(int idUtente,String token,String oldPwd,String newPwd)
            throws PasswordNotValidException, UtenteNotFoundException, PermissionDeniedException, SQLException {
        Pattern pattern = Pattern.compile(Utility.PASSWORD_PATTERN);
        if(!token.trim().isEmpty() && !oldPwd.trim().isEmpty() && !newPwd.trim().isEmpty() && pattern.matcher(newPwd).matches())
        {
            Connection con = null;
            try {
                con = Connect.getConnection();
                con.setAutoCommit(false);

                int tipoRuolo = dao.doGetRuolo(con, idUtente, token);
                if (tipoRuolo==-1)
                {
                    throw new PermissionDeniedException();
                }

                if (dao.isPasswordValid(con, idUtente, oldPwd)) {
                    dao.doChangePassword(con, idUtente, newPwd);
                } else {
                    throw new PasswordNotValidException();
                }

                con.commit();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                throw new RuntimeException();
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Effettua la modifica dell'anagrafica di un utente secondo i valori specificati.
     * @param nome Nome dell'utente
     * @param tokenAuth Token di autenticazione dell'utente
     * @param cognome Cognome dell'utente
     * @param cf Codice Fiscale dell'utente
     * @param dataNascitaStr Data di nascita dell'utente
     * @param email Email dell'utente
     * @throws EmailAlreadyExistingException L'email specificata è già presente nel database
     * @throws ParseException La data di nascita non è in un formato comprensibile
     * @throws SQLException Errore nella comunicazione con il database
     * @throws IllegalArgumentException Dati non validi
     */
    public void editAnagrafica(int idUtente, String tokenAuth, String nome, String cognome, String cf, String dataNascitaStr, String email)
            throws EmailAlreadyExistingException, ParseException, SQLException {
        if(cf.length()==16 && !nome.trim().isEmpty() && !cognome.trim().isEmpty() && !dataNascitaStr.trim().isEmpty() && !email.trim().isEmpty())
        {
            Connection con = null;
            try {
                con = Connect.getConnection();
                con.setAutoCommit(false);

                if (dao.isEmailInDatabase(con, email) && !dao.isEmailOld(con, idUtente, email)) {
                    throw new EmailAlreadyExistingException();
                }
                dao.doChangeAnagrafica(con, idUtente, tokenAuth, nome, cognome, cf, Utility.dataConverter(dataNascitaStr), email);

                con.commit();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                throw new RuntimeException();
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Recupera l'utente che ha effettuato la prenotazione stanza specificata.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @return Utente trovato
     * @throws UtenteNotFoundException L'utente cercato non è stato trovato
     */
    public Utente getUtenteByPrenotazioneStanza(int idPrenotazione) throws UtenteNotFoundException {
        try (Connection con = Connect.getConnection()) {
            return dao.doSelectByPrenotazioneStanza(con, idPrenotazione);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Recupera tutti gli utenti presenti nel database.
     * @return Lista contenente gli utenti trovati
     */
    public List<Utente> getAll() {
        try (Connection con = Connect.getConnection()) {
            return dao.getUtenti(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Modifica il ruolo di un utente secondo i valori specificati.
     * @param idUtente Identificativo dell'utente
     * @param ruolo Ruolo da inserire
     */
    public void editRuoloById(int idUtente, int ruolo) {
        try (Connection con = Connect.getConnection()) {
            dao.doChangeRuolo(con, idUtente, ruolo);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}
