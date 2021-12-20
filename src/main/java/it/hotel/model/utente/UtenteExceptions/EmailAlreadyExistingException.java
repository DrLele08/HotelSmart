package it.hotel.model.utente.UtenteExceptions;

public class EmailAlreadyExistingException extends Exception {

    public EmailAlreadyExistingException() {}

    public EmailAlreadyExistingException(String msg) { super(msg); }

}
