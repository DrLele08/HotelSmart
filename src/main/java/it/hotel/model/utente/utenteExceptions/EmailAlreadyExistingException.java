package it.hotel.model.utente.utenteExceptions;

import it.hotel.model.utente.Utente;

/**
 * Segnala che l'email di un oggetto {@link Utente} è già presente nel database.
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
