package it.hotel.controller.services;

import it.hotel.model.prenotazioneServizio.PrenotazioneServizio;
import it.hotel.model.prenotazioneServizio.PrenotazioneServizioDAO;

/**
 * Fornisce metodi di utilizzo del database per {@link PrenotazioneServizio}.
 */
public class PrenotazioneServizioService {

    private final PrenotazioneServizioDAO dao;

    /**
     * Costruisce un oggetto PrenotazioneServizioService.
     */
    public PrenotazioneServizioService() {
        this.dao = new PrenotazioneServizioDAO();
    }

    /**
     * Elimina la prenotazione servizio specificata.
     * @param idPrenotazione Identificativo della prenotazione
     */
    public void deletePrenotazioneById(int idPrenotazione) {
        dao.doDelete(idPrenotazione);
    }
}
