package it.hotel.model.stato.statoExceptions;

import it.hotel.model.stato.Stato;

/**
 * Segnala che non è stato trovato un oggetto {@link Stato} nel database.
 */
public class StatoNotFoundException extends Exception{

    /**
     * Costruisce una StatoNotFoundException senza un messaggio di dettagli.
     */
    public StatoNotFoundException() {}

    /**
     * Costruisce una StatoNotFoundException con il messaggio di dettagli specificato.
     * @param msg Il messaggio di dettagli
     */
    public StatoNotFoundException(String msg) { super(msg); }

}
