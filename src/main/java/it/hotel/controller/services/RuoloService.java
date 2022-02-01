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
            con.setAutoCommit(false);

            ruoli = dao.doGetAll(con);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return ruoli;
    }

    /**
     * Recupera l'identificativo del ruolo specificato.
     * @return Identificativo del ruolo
     * @throws RuoloNotFoundException Il ruolo specificato non è stato trovato
     */
    public int getByRuolo(String ruolo) {
        int idRuolo;
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            idRuolo = dao.doSelectByRuolo(con, ruolo).getIdRuolo();

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        } catch (RuoloNotFoundException e) {
            throw new RuntimeException();
        }
        return idRuolo;
    }

}
