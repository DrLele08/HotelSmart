package it.hotel.controller.services;

import it.hotel.model.stato.Stato;
import it.hotel.model.stato.StatoDAO;

import java.util.List;

/**
 * Fornisce metodi di utilizzo del database per {@link Stato}.
 */
public class StatoService {

    private final StatoDAO dao;

    /**
     * Costruisce un oggetto StatoService.
     */
    public StatoService() { this.dao = new StatoDAO(); }

    /**
     * Recupera tutti gli stati presenti nel database.
     * @return Lista contenente gli stati trovati
     */
    public List<Stato> getAll() {
        return dao.doGetAll();
    }

}
