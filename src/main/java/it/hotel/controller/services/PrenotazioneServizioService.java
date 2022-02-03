package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.model.prenotazioneServizio.PrenotazioneServizio;
import it.hotel.model.prenotazioneServizio.PrenotazioneServizioDAO;

import java.sql.Connection;
import java.sql.Date;
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
     * Crea la prenotazione di un servizio
     * @param ksPrenotazioneStanza Identificativo della prenotazione stanza associata
     * @param ksServizio Identificativo del servizio
     * @param numPersone Numero delle persone
     * @param dataPrenotazioneServizio Data della prenotazione del servizio
     */

    public void createPrenotazione(int ksPrenotazioneStanza, int ksServizio, int numPersone, Date dataPrenotazioneServizio) throws SQLException {

        try (Connection con = Connect.getConnection()){
            dao.doInsert(con,ksPrenotazioneStanza,ksServizio,numPersone,dataPrenotazioneServizio);
        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

    /**
     * Elimina la prenotazione servizio specificata.
     * @param idPrenotazione Identificativo della prenotazione
     */

    public void deletePrenotazioneById(int idPrenotazione) {
        try (Connection con = Connect.getConnection()) {
            dao.doDelete(con, idPrenotazione);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
