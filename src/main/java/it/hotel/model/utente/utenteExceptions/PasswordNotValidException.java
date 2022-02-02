package it.hotel.model.utente.utenteExceptions;

import it.hotel.model.utente.Utente;

/**
 * Segnala che la password di un oggetto {@link Utente} non è valida.
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
