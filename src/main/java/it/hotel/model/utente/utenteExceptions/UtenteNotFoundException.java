package it.hotel.model.utente.utenteExceptions;

import it.hotel.model.utente.Utente;

/**
 * Segnala che l'oggetto {@link Utente} cercato non è presente nel database.
 */
public class UtenteNotFoundException extends Exception {

    /**
     * Costruisce una UtenteNotFoundException senza un messaggio di dettagli.
     */
    public UtenteNotFoundException() {}

    /**
     * Costruisce una UtenteNotFoundException con il messaggio di dettagli specificato.
     * @param msg Il messaggio di dettagli
     */
    public UtenteNotFoundException(String msg) { super(msg); }

}
