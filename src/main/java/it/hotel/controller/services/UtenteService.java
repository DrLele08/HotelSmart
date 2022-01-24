package it.hotel.controller.services;

import it.hotel.Utility.Utility;
import it.hotel.controller.exception.PermissionDeniedException;
import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.UtenteDAO;
import it.hotel.model.utente.utenteExceptions.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Date;
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
     * @throws  EmailNotFoundException L'email specificata non è stata trovata
     * @throws PasswordNotValidException La password specificata non è valida
     * @see Utente
     */
    public Utente doLogin(String email, String pwd) throws EmailNotFoundException, PasswordNotValidException
    {
        if(!(email.trim().isEmpty() || pwd.trim().isEmpty()))
        {
            return dao.doAuthenticate(email,pwd);
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
     * @see Utente
     */
    public Utente doLogin(int idUtente,String tokenAuth) throws UtenteNotFoundException {
        if(!tokenAuth.trim().isEmpty())
        {
            return dao.doAuthenticate(idUtente,tokenAuth);
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
     * @throws EmailAlreadyExistingException L'email specificata è già presente
     * @throws PasswordNotValidException La password specificata non è valida
     * @see Utente
     */
    public Utente doRegistrazione(String cf, String nome, String cognome, String email, Date data,String pwd) throws EmailAlreadyExistingException,PasswordNotValidException
    {
        if(cf.length()==16 && !nome.trim().isEmpty() && !cognome.trim().isEmpty() && !email.trim().isEmpty() && !pwd.trim().isEmpty())
        {
            Pattern pattern = Pattern.compile(Utility.PASSWORD_PATTERN);
            if(pattern.matcher(pwd).matches())
            {
                boolean useLetters = true;
                boolean useNumbers = false;
                String generatedString = RandomStringUtils.random(Utility.lenghtAuth, useLetters, useNumbers);
                return dao.doInsert(Ruolo.getIdByNome(Utility.listRuoli,"UTENTE"),cf,nome,cognome,email,data,generatedString,pwd);
            }
            else
            {
                throw new PasswordNotValidException();
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
     */
    public void editPassword(int idUtente,String token,String oldPwd,String newPwd)
            throws PasswordNotValidException, UtenteNotFoundException, PermissionDeniedException
    {
        if(!token.trim().isEmpty() && !oldPwd.trim().isEmpty() && !newPwd.trim().isEmpty())
        {
            Pattern pattern = Pattern.compile(Utility.PASSWORD_PATTERN);
            if (pattern.matcher(newPwd).matches())
            {
                int tipoRuolo = dao.doGetRuolo(idUtente, token);
                if (tipoRuolo == 2 || tipoRuolo == 1 || tipoRuolo == 3)
                    dao.doChangePassword(idUtente, oldPwd, newPwd);
                else
                    throw new PermissionDeniedException();
            }
            else
                throw new PasswordNotValidException();
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
     * @throws EmailAlreadyExistingException L'email specificata è già presente
     * @throws ParseException La data di nascita non è in un formato comprensibile
     */
    public void editAnagrafica(int idUtente, String tokenAuth, String nome, String cognome, String cf, String dataNascitaStr, String email)
            throws EmailAlreadyExistingException, ParseException {
        if(cf.length()==16 && !nome.trim().isEmpty() && !cognome.trim().isEmpty() && !dataNascitaStr.trim().isEmpty() && !email.trim().isEmpty())
        {
            dao.doChangeAnagrafica(idUtente, tokenAuth, nome, cognome, cf, Utility.dataConverter(dataNascitaStr), email);
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Recupera tutti gli utenti presenti nel database.
     * @return Lista contenente gli utenti trovati
     */
    public List<Utente> getAll() {
        return dao.getUtenti();
    }
}
