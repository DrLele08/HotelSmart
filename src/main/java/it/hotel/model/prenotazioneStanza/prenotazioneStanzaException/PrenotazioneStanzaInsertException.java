package it.hotel.model.prenotazioneStanza.prenotazioneStanzaException;

import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;

/**
 * Segnala che non è stato possibile inserire un oggetto {@link PrenotazioneStanza} nel database.
 */
public class PrenotazioneStanzaInsertException extends Exception{

    /**
     * Costruisce una PrenotazioneStanzaNotFoundException senza un messaggio di dettagli.
     */
    public PrenotazioneStanzaInsertException() {}

    /**
     * Costruisce una PrenotazioneStanzaNotFoundException con il messaggio di dettagli specificato.
     * @param msg Il messaggio di dettagli
     */
    public PrenotazioneStanzaInsertException(String msg) { super(msg); }

}

