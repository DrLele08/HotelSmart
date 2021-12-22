package it.hotel.model.stanza.stanzaExceptions;

import it.hotel.model.stanza.Stanza;

/**
 * Segnala che l'oggetto {@link Stanza} cercato non è presente nel database.
 */
public class StanzaNotFoundException extends Exception{

    /**
     * Costruisce una StanzaNotFoundException senza un messaggio di dettagli.
     */
    public StanzaNotFoundException() {}

    /**
     * Costruisce una StanzaNotFoundException con il messaggio di dettagli specificato.
     * @param msg Il messaggio di dettagli
     */
    public StanzaNotFoundException(String msg) { super(msg); }

}

