package it.hotel.model.utente.utenteExceptions;

/**
 * Segnala che la password non è valida.
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
