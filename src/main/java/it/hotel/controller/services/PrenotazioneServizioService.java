package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.model.prenotazioneServizio.PrenotazioneServizio;
import it.hotel.model.prenotazioneServizio.PrenotazioneServizioDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Fornisce metodi di utilizzo del database per {@link PrenotazioneServizio}.
 */
public class PrenotazioneServizioService {

    private final PrenotazioneServizioDAO dao;

    /**
     * Costruisce un oggetto PrenotazioneServizioService.
     */
    public PrenotazioneServizioService() {
        this.dao = new PrenotazioneServizioDAO();
    }

    /**
     * Elimina la prenotazione servizio specificata.
     * @param idPrenotazione Identificativo della prenotazione
     */
    public void deletePrenotazioneById(int idPrenotazione) {
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            dao.doDelete(con, idPrenotazione);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
