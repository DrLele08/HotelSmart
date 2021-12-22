package it.hotel.model.utente.utenteExceptions;

import it.hotel.model.utente.UtenteDAO;

/**
 * Segnala che l'email che un oggetto {@link UtenteDAO} vuole inserire nel database è già presente.
 */
public class EmailAlreadyExistingException extends Exception {

    /**
     * Costruisce una EmailAlreadyExistingException senza un messaggio di dettagli.
     */
    public EmailAlreadyExistingException() {}

    /**
     * Costruisce una EmailAlreadyExistingException con il messaggio di dettagli specificato.
     * @param msg Il messaggio di dettagli
     */
    public EmailAlreadyExistingException(String msg) { super(msg); }

}
