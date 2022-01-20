package it.hotel.controller.exception;

import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;

/**
 * Segnala che non è stato possibile inserire un oggetto {@link PrenotazioneStanza} nel database a causa di un pagamento in attesa.
 */
public class PagamentoInAttesaException extends Exception{

    /**
     * Costruisce una PagamentoInAttesaException senza un messaggio di dettagli.
     */
    public PagamentoInAttesaException() {}

    /**
     * Costruisce una PagamentoInAttesaException con il messaggio di dettagli specificato.
     * @param msg Il messaggio di dettagli
     */
    public PagamentoInAttesaException(String msg) { super(msg); }

}