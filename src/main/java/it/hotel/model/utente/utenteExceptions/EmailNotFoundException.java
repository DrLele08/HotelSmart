package it.hotel.model.utente.utenteExceptions;

import it.hotel.model.utente.Utente;

/**
 * Segnala che l'email di un oggetto {@link Utente} non è presente nel database.
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
