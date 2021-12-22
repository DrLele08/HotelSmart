package it.hotel.model.utente.utenteExceptions;

import it.hotel.model.utente.UtenteDAO;

/**
 * Segnala che l'email che un oggetto {@link UtenteDAO} cerca nel database non è stata trovata.
 */
public class EmailNotFoundException extends Exception {

    /**
     * Costruisce una EmailNotFoundException senza un messaggio di dettagli.
     */
    public EmailNotFoundException() {}

    /**
     * Costruisce una EmailNotFoundException con il messaggio di dettagli specificato.
     * @param msg Il messaggio di dettagli
     */
    public EmailNotFoundException(String msg) { super(msg); }

}
