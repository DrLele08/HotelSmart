package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.UtenteDAO;
import it.hotel.model.utente.utenteExceptions.*;
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
        if(!email.trim().isEmpty() && !pwd.trim().isEmpty())
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
    public Utente doLogin(int idUtente,String tokenAuth) throws UtenteNotFoundException {
        if(!tokenAuth.trim().isEmpty())
        {
            return dao.doAuthenticate(idUtente,tokenAuth);
        }
        else
            throw new IllegalArgumentException();
    }

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
    public Utente doRegistrazione(String cf, String nome, String cognome, String email, Date data,String pwd) throws EmailAlreadyExistingException
    {
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(Utility.lenghtAuth, useLetters, useNumbers);
        //TODO check valori e regex
        return dao.doInsert(3,cf,nome,cognome,email,data,generatedString,pwd);
    }

    /**
     * Effettua la modifica della password di un utente
     *
     * @param idUtente Identificativo dell'utente
     * @param token Token di autenticazione utente
     * @param oldPwd Vecchia password dell'utente
     * @param newPwd Nuova password utente
     */
    public void editPassword(int idUtente,String token,String oldPwd,String newPwd)
            throws PasswordNotValidException, UtenteNotFoundException
    {
        //TODO Controllare i campi
        //TODO Controllare Regex newPwd
        dao.doGetRuolo(idUtente, token);
        dao.doChangePassword(idUtente,oldPwd,newPwd);

    }
}
