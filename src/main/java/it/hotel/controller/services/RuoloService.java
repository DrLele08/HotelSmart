package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.ruolo.RuoloDAO;
import it.hotel.model.ruolo.ruoloExceptions.RuoloNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
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
        List<Ruolo> ruoli;
        try (Connection con = Connect.getConnection()) {
            ruoli = dao.doGetAll(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return ruoli;
    }

    /**
     * Recupera il ruolo secondo l'identificativo specificato.
     * @param idRuolo Identificativo del ruolo
     * @return Ruolo
     */
    public String getById(int idRuolo) {
        String ruolo;
        try (Connection con = Connect.getConnection()) {
            ruolo = dao.doSelectById(con, idRuolo).getRuolo();
        } catch (SQLException | RuoloNotFoundException e) {
            throw new RuntimeException();
        }
        return ruolo;
    }

    /**
     * Recupera l'identificativo del ruolo specificato.
     * @param ruolo Ruolo
     * @return Identificativo del ruolo
     */
    public int getByRuolo(String ruolo) {
        int idRuolo;
        try (Connection con = Connect.getConnection()) {
            idRuolo = dao.doSelectByRuolo(con, ruolo).getIdRuolo();
        } catch (SQLException | RuoloNotFoundException e) {
            throw new RuntimeException();
        }
        return idRuolo;
    }

}
