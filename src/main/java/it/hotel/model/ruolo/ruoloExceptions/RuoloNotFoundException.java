package it.hotel.model.ruolo.ruoloExceptions;

import it.hotel.model.ruolo.Ruolo;

/**
 * Segnala che l'oggetto {@link Ruolo} cercato non è presente nel database.
 */
public class RuoloNotFoundException extends Exception{

    /**
     * Costruisce una RuoloNotFoundException senza un messaggio di dettagli.
     */
    public RuoloNotFoundException() {}

    /**
     * Costruisce una RuoloNotFoundException con il messaggio di dettagli specificato.
     * @param msg Il messaggio di dettagli
     */
    public RuoloNotFoundException(String msg) { super(msg); }

}
