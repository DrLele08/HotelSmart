package it.hotel.controller.services;

import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.ruolo.RuoloDAO;

import java.util.List;

public class RuoloService {

    private final RuoloDAO dao;

    public RuoloService() { this.dao = new RuoloDAO(); }

    public List<Ruolo> getAll() {
        return dao.doGetAll();
    }

}
