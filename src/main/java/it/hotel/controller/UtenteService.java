package it.hotel.controller;

import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.UtenteDAO;
import it.hotel.model.utente.utenteExceptions.EmailNotFoundException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;

public class UtenteService
{
    private UtenteDAO dao;

    public UtenteService()
    {
        dao=new UtenteDAO();
    }
    /**
     * Effettua il login dell'utente data un indirizzo email e una password
     *
     * @param  email  an absolute URL giving the base location of the image
     * @param  pwd the location of the image, relative to the url argument
     * @return      Dati utente
     * @see         Utente
     */
    public Utente doLogin(String email, String pwd) throws EmailNotFoundException, PasswordNotValidException,IllegalArgumentException
    {
        if(email.trim().isEmpty() && pwd.trim().isEmpty())
        {
            return dao.doAuthenticate(email,pwd);
        }
        else
            throw new IllegalArgumentException();
    }
}
