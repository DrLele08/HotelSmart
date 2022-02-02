package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.model.stato.Stato;
import it.hotel.model.stato.StatoDAO;
import it.hotel.model.stato.statoExceptions.StatoNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
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
        List<Stato> stati;
        try (Connection con = Connect.getConnection()) {
            stati = dao.doGetAll(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return stati;
    }

    /**
     * Recupera l'identificativo dello stato specificato.
     * @return Identificativo dello stato
     */
    public int getByStato(String ruolo) {
        int idStato;
        try (Connection con = Connect.getConnection()) {
            idStato = dao.doSelectByStato(con, ruolo).getIdStato();
        } catch (SQLException e) {
            throw new RuntimeException();
        } catch (StatoNotFoundException e) {
            throw new RuntimeException();
        }
        return idStato;
    }

}
