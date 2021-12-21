package it.hotel.model.prenotazioneStanza.prenotazioneStanzaException;

import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;

/**
 * Segnala che non è stato trovato un oggetto {@link PrenotazioneStanza} nel database.
 */
public class PrenotazioneStanzaNotFoundException extends Exception{

    /**
     * Costruisce una PrenotazioneStanzaNotFoundException senza un messaggio di dettagli.
     */
    public PrenotazioneStanzaNotFoundException() {}

    /**
     * Costruisce una PrenotazioneStanzaNotFoundException con il messaggio di dettagli specificato.
     * @param msg Il messaggio di dettagli
     */
    public PrenotazioneStanzaNotFoundException(String msg) { super(msg); }

}

