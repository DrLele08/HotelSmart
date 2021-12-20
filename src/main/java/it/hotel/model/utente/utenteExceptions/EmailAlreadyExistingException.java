package it.hotel.model.utente.utenteExceptions;

public class EmailAlreadyExistingException extends Exception {

    public EmailAlreadyExistingException() {}

    public EmailAlreadyExistingException(String msg) { super(msg); }

}
