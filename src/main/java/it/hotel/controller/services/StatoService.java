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
public class StatoService
{

    private StatoDAO dao;

    /**
     * Costruisce un oggetto StatoService.
     */
    public StatoService()
    {
        //this.dao=createDAO();
    }

    public StatoDAO createDAO()
    {
        return new StatoDAO();
    }

    public Connection getConnection() throws SQLException {
        return Connect.getConnection();
    }
    /**
     * Recupera tutti gli stati presenti nel database.
     * @return Lista contenente gli stati trovati
     */
    public List<Stato> getAll()
    {
        dao=createDAO();
        List<Stato> stati;
        try (Connection con = getConnection()) {
            stati = dao.doGetAll(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return stati;
    }

    /**
     * Recupera l'identificativo dello stato specificato.
     * @param idStato Identificativo dello stato
     * @return Stato
     */
    public String getById(int idStato)
    {
        String stato;
        try (Connection con = Connect.getConnection()) {
            stato = dao.doSelectById(con, idStato).getStato();
        } catch (SQLException | StatoNotFoundException e) {
            throw new RuntimeException();
        }
        return stato;
    }

}
