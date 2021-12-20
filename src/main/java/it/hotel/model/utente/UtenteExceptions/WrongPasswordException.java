package it.hotel.model.utente.UtenteExceptions;

public class WrongPasswordException extends Exception {

    public WrongPasswordException() {}

    public WrongPasswordException(String msg) { super(msg); }

}
