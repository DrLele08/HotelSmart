package it.hotel.controller.services;

import it.hotel.model.servizio.Servizio;
import it.hotel.model.servizio.ServizioDAO;

import java.util.List;

/**
 * Fornisce metodi di utilizzo del database per {@link Servizio}.
 */
public class ServizioService {

    private final ServizioDAO dao;

    /**
     * Costruisce un oggetto ServizioService.
     */
    public ServizioService() { this.dao = new ServizioDAO(); }

    /**
     * Recupera tutti i servizi presenti nel database.
     * @return Lista contenente i servizi trovati
     */
    public List<Servizio> getAll() {
        return dao.getServizi();
    }

    /**
     * Recupera i servizi utilizzati dall'utente specificato.
     * @param idUtente Identificativo dell'utente
     * @return Lista contenente i servizi trovati per l'utente
     */
    public List<Servizio> getByUser(int idUtente) {
        return dao.doSelectByUserId(idUtente);
    }
}
