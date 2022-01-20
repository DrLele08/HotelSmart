package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.exception.PermissionDeniedException;
import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.UtenteDAO;
import it.hotel.model.utente.utenteExceptions.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Date;
import java.util.regex.Pattern;

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
        if(!(email.trim().isEmpty() || pwd.trim().isEmpty()))
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
     * Effettua la modifica della password di un utente
     *
     * @param idUtente Identificativo dell'utente
     * @param token Token di autenticazione utente
     * @param oldPwd Vecchia password dell'utente
     * @param newPwd Nuova password utente
     */
    public void editPassword(int idUtente,String token,String oldPwd,String newPwd)
            throws PasswordNotValidException, UtenteNotFoundException, PermissionDeniedException
    {
        if(!token.trim().isEmpty() && !oldPwd.trim().isEmpty() && !newPwd.trim().isEmpty())
        {
            int tipoRuolo=dao.doGetRuolo(idUtente,token);
            if(tipoRuolo==2 || tipoRuolo==1 || tipoRuolo==3)
                dao.doChangePassword(idUtente,oldPwd,newPwd);
            else
                throw new PermissionDeniedException();
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Effettua la modifica dell'anagrafica di un utente
     * @param nome Nome dell'utente
     * @param cognome Cognome dell'utente
     * @param cf Codice Fiscale dell'utente
     * @param dataNascita Data di nascita dell'utente
     * @param email Email dell'utente
     */
    public void editAnagrafica(int idUtente, String nome, String cognome, String cf, Date dataNascita, String email)
            throws EmailAlreadyExistingException
    {
        if(cf.length()==16 && !nome.trim().isEmpty() && !cognome.trim().isEmpty() && !email.trim().isEmpty() && dataNascita!=null)
        {
            dao.doChangeAnagrafica(idUtente, nome, cognome, cf, dataNascita, email);
        }
        else
            throw new IllegalArgumentException();
    }

}
