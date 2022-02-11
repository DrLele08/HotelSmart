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

    /**
     * Costruisce un oggetto StatoService.
     */
    public StatoService() {}

    /**
     * Costruisce un oggetto StatoDAO.
     * @return L'oggetto StatoDAO costruito.
     */
    public StatoDAO createDAO()
    {
        return new StatoDAO();
    }

    /**
     * Ottiene la connessione al database.
     * @return Connessione al database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public Connection getConnection() throws SQLException {
        return Connect.getConnection();
    }

    /**
     * Recupera tutti gli stati presenti nel database.
     * @return Lista contenente gli stati trovati
     */
    public List<Stato> getAll()
    {
        StatoDAO dao=createDAO();
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
        StatoDAO dao = createDAO();
        String statoStr;
        try (Connection con = getConnection()) {
            Stato stato = dao.doSelectById(con, idStato);
            statoStr = stato.getStato();
        } catch (SQLException | StatoNotFoundException e) {
            throw new RuntimeException();
        }
        return statoStr;
    }

}
