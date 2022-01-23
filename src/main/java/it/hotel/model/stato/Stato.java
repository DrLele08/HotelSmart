package it.hotel.model.stato;

import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;

/**
 * Rappresenta lo Stato di una {@link PrenotazioneStanza}.
 */
public class Stato {

    private int idStato;
    private String stato;

    /**
     * Costruisce uno Stato con identificativo e stato specificati.
     * @param idStato Identificativo stato
     * @param stato Stringa rappresentativa dello stato
     */
    public Stato(int idStato, String stato) {
        this.idStato = idStato;
        this.stato = stato;
    }

    /**
     * Restituisce l'identificativo dell'oggetto Stato
     * @return Identificativo
     */
    public int getIdStato() {
        return idStato;
    }

    /**
     * Restituisce lo stato che l'oggetto Stato rappresenta
     * @return Stato
     */
    public String getStato() {
        return stato;
    }

}