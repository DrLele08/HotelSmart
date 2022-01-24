package it.hotel.controller.services;

import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.ruolo.RuoloDAO;

import java.util.List;

/**
 * Fornisce metodi di utilizzo del database per {@link Ruolo}.
 */
public class RuoloService {

    private final RuoloDAO dao;

    /**
     * Costruisce un oggetto RuoloService.
     */
    public RuoloService() { this.dao = new RuoloDAO(); }

    /**
     * Recupera tutti i ruoli presenti nel database.
     * @return Lista contenente i ruoli trovati
     */
    public List<Ruolo> getAll() {
        return dao.doGetAll();
    }

}
