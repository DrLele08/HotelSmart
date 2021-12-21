package it.hotel.model.ruolo.ruoloExceptions;

import it.hotel.model.ruolo.Ruolo;

/**
 * Segnala che non è stato trovato un oggetto {@link Ruolo} nel database.
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
