package it.hotel.model.utente.utenteExceptions;

/**
 * Segnala che l'email è già presente nel database.
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
