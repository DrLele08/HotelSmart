package it.hotel.controller.services;

import it.hotel.model.stato.Stato;
import it.hotel.model.stato.StatoDAO;

import java.util.List;

public class StatoService {

    private final StatoDAO dao;

    public StatoService() { this.dao = new StatoDAO(); }

    public List<Stato> getAll() {
        return dao.doGetAll();
    }

}
