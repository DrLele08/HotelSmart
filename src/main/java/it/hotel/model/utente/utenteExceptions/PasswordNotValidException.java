package it.hotel.model.utente.utenteExceptions;

import it.hotel.model.utente.UtenteDAO;

/**
 * Segnala che la password che un oggetto {@link UtenteDAO} sta utilizzando non ha un riscontro nel database.
 */
public class PasswordNotValidException extends Exception {

    /**
     * Costruisce una PasswordNotValidException senza un messaggio di dettagli.
     */
    public PasswordNotValidException() {}

    /**
     * Costruisce una PasswordNotValidException con il messaggio di dettagli specificato.
     * @param msg Il messaggio di dettagli
     */
    public PasswordNotValidException(String msg) { super(msg); }

}
