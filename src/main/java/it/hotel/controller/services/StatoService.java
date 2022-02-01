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
        return dao.doGetAll();
    }

    /**
     * Recupera l'identificativo dello stato specificato.
     * @return Identificativo dello stato
     * @throws StatoNotFoundException Lo stato specificato non è stato trovato
     */
    public int getByStato(String ruolo) {
        int idStato;
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            idStato = dao.doSelectByStato(con, ruolo).getIdStato();

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        } catch (StatoNotFoundException e) {
            throw new RuntimeException();
        }
        return idStato;
    }

}
