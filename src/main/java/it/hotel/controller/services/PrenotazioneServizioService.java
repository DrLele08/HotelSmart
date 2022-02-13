package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.model.prenotazioneServizio.PrenotazioneServizio;
import it.hotel.model.prenotazioneServizio.PrenotazioneServizioDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Fornisce metodi di utilizzo del database per {@link PrenotazioneServizio}.
 */
public class PrenotazioneServizioService {

    /**
     * Costruisce un oggetto PrenotazioneServizioService.
     */
    public PrenotazioneServizioService() { }

    /**
     * Costruisce un oggetto PrenotazioneServizioDAO.
     * @return L'oggetto PrenotazioneServizioDAO costruito.
     */
    public PrenotazioneServizioDAO createDAO()
    {
        return new PrenotazioneServizioDAO();
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
     * Inserisce una prenotazione servizio secondo i valori specificati.
     * @param ksPrenotazioneStanza Identificativo della prenotazione stanza
     * @param ksServizio Identificativo del servizio
     * @param numPersone Numero delle persone
     * @param dataPrenotazioneServizio Data della prenotazione del servizio
     */
    public void createPrenotazione(int ksPrenotazioneStanza, int ksServizio, int numPersone, Date dataPrenotazioneServizio) {
        Connection con;
        try {
            con = getConnection();
            PrenotazioneServizioDAO dao = createDAO();
            dao.doInsert(con,ksPrenotazioneStanza,ksServizio,numPersone,dataPrenotazioneServizio);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Recupera tutte le prenotazioni servizio dell'utente specificato.
     * @param idUtente Identificativo dell'utente
     * @return Lista contenente le prenotazioni servizio trovate
     */
    public List<PrenotazioneServizio> getAllByUser(int idUtente) {
        List<PrenotazioneServizio> prenotazioni;
        Connection con;
        try {
            con = getConnection();
            PrenotazioneServizioDAO dao = createDAO();
            prenotazioni = dao.doSelectByUser(con, idUtente);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return prenotazioni;
    }

    /**
     * Elimina la prenotazione servizio specificata.
     * @param idPrenotazione Identificativo della prenotazione
     */
    public void deletePrenotazioneById(int idPrenotazione) {
        Connection con;
        try {
            con = getConnection();
            PrenotazioneServizioDAO dao = createDAO();
            dao.doDelete(con, idPrenotazione);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
