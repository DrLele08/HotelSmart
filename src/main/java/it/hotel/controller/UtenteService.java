package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.UtenteDAO;
import it.hotel.model.utente.utenteExceptions.EmailAlreadyExistingException;
import it.hotel.model.utente.utenteExceptions.EmailNotFoundException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;
import it.hotel.model.utente.utenteExceptions.TokenNotValidException;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Date;

public class UtenteService
{
    private final UtenteDAO dao;

    public UtenteService()
    {
        dao=new UtenteDAO();
    }
    /**
     * Effettua il login dell'utente data un indirizzo email e una password
     *
     * @param email  Email inserita dall'utente
     * @param pwd Password inserita dall'utente
     * @return Dati utente
     * @see Utente
     */
    public Utente doLogin(String email, String pwd) throws EmailNotFoundException, PasswordNotValidException
    {
        if(email.trim().isEmpty() && pwd.trim().isEmpty())
        {
            return dao.doAuthenticate(email,pwd);
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Effettua il login dell'utente data un indirizzo email e una password
     *
     * @param idUtente Identificatico dell'utente
     * @param tokenAuth Token dell'utente
     * @return Dati utente
     * @see Utente
     */
    /*public Utente doLogin(int idUtente,String tokenAuth) throws EmailNotFoundException, PasswordNotValidException
    {
        if(tokenAuth.trim().isEmpty())
        {
            //TODO DAO Login Per idUtente & TokenAuth
            //return dao.doAuthenticate(idUtente,tokenAuth);
        }
        else
            throw new IllegalArgumentException();
    }*/

    /**
     * Effettua la registrazione di un nuovo utente standard
     *
     * @param cf Codice Fiscale utente
     * @param nome Nome utente
     * @param cognome Cognome utente
     * @param email  Email utente
     * @param data Data nascita utente
     * @param pwd Password utente
     * @return      Dati utente
     * @see         Utente
     */
    /*public Utente doRegistrazione(String cf, String nome, String cognome, String email, Date data,String pwd) throws EmailAlreadyExistingException
    {
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(Utility.lenghtAuth, useLetters, useNumbers);
        Utente newUser=new Utente(-1,-1,cf,nome,cognome,email,data,generatedString);
        //Chiedere a Giovanni
        //return dao.doInsert(newUser,pwd);
    }*/

    /**
     * Effettua la modifica della password di un utente
     *
     * @param idUtente Identificativo dell'utente
     * @param token Token di autenticazione utente
     * @param oldPwd Vecchia password dell'utente
     * @param newPwd Nuova password utente
     * @return Booleano che indica se la password è stata modificata o meno
     */
    /*public boolean editPassword(int idUtente,String token,String oldPwd,String newPwd) throws PasswordNotValidException, TokenNotValidException
    {
        //TODO Controllare i campi
        //TODO Controllare Regex newPwd
        //Chiedere a Giovanni Exception Ruolo
        if(dao.doGetRuolo(idUtente,token)>0)
        {
            //Chiedere Giovanni Password Exception & Return Type
            //return dao.doChangePassword(idUtente,oldPwd,newPwd);
        }
        return false;
    }*/
}
