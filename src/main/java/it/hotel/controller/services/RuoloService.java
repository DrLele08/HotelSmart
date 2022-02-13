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

    /**
     * Costruisce un oggetto RuoloService.
     */
    public RuoloService() { }

    /**
     * Costruisce un oggetto RuoloDAO.
     * @return L'oggetto RuoloDAO costruito.
     */
    public RuoloDAO createDAO()
    {
        return new RuoloDAO();
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
     * Recupera tutti i ruoli presenti nel database.
     * @return Lista contenente i ruoli trovati
     */
    public List<Ruolo> getAll() {
        RuoloDAO dao=createDAO();
        List<Ruolo> ruoli;
        Connection con;
        try {
            con = getConnection();
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
        RuoloDAO dao=createDAO();
        Ruolo ruolo;
        String ruoloStr;
        Connection con;
        try {
            con = getConnection();
            ruolo = dao.doSelectById(con, idRuolo);
            ruoloStr = ruolo.getRuolo();
        } catch (SQLException | RuoloNotFoundException e) {
            throw new RuntimeException();
        }
        return ruoloStr;
    }

    /**
     * Recupera l'identificativo del ruolo specificato.
     * @param ruolo Ruolo
     * @return Identificativo del ruolo
     */
    public int getByRuolo(String ruolo) {
        RuoloDAO dao=createDAO();
        int idRuolo;
        Connection con;
        try {
            con = getConnection();
            idRuolo = dao.doSelectByRuolo(con, ruolo).getIdRuolo();
        } catch (SQLException | RuoloNotFoundException e) {
            throw new RuntimeException();
        }
        return idRuolo;
    }

}
