package it.hotel.controller.services;

import it.hotel.model.servizio.Servizio;
import it.hotel.model.servizio.ServizioDAO;

import java.util.List;

public class ServizioService {

    private final ServizioDAO dao;

    public ServizioService() { this.dao = new ServizioDAO(); }

    public List<Servizio> getAll() {
        return dao.getServizi();
    }

    public List<Servizio> getByUser(int idUtente) {
        return dao.doSelectByUserId(idUtente);
    }
}
