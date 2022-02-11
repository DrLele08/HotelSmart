package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.model.ruolo.RuoloDAO;
import it.hotel.model.servizio.Servizio;
import it.hotel.model.servizio.ServizioDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Fornisce metodi di utilizzo del database per {@link Servizio}.
 */
public class ServizioService {

    /**
     * Costruisce un oggetto ServizioService.
     */
    public ServizioService() {}

    /**
     * Costruisce un oggetto ServizioDAO.
     * @return L'oggetto ServizioDAO costruito.
     */
    public ServizioDAO createDAO()
    {
        return new ServizioDAO();
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
     * Inserisce un servizio secondo i valori specificati.
     * @param nome Nome
     * @param descrizione Descrizione
     * @param foto Percorso foto
     * @param costo Costo
     * @param limitePosti Posti disponibili
     */
    public void createServizio(String nome, String descrizione, String foto, double costo, int limitePosti) {
        try (Connection con = getConnection()) {
            ServizioDAO dao = createDAO();
            dao.doInsert(con, nome, descrizione, foto, costo, limitePosti);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Aggiorna un servizio secondo i valori specificati.
     * @param idServizio Identificativo
     * @param nome Nome
     * @param descrizione Descrizione
     * @param foto Percorso foto
     * @param costo Costo
     * @param limitePosti Posti disponibili
     */
    public void updateServizio(int idServizio, String nome, String descrizione, String foto, double costo, int limitePosti) {
        try (Connection con = getConnection()) {
            ServizioDAO dao = createDAO();
            dao.doUpdate(con, idServizio, nome, descrizione, foto, costo, limitePosti);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Recupera tutti i servizi presenti nel database.
     * @return Lista contenente i servizi trovati
     */
    public List<Servizio> getAll() {
        try (Connection con = getConnection()) {
            ServizioDAO dao = createDAO();
            return dao.getServizi(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Recupera un servizio dato un id.
     * @param idServizio Identificativo del servizio.
     * @return Il servizio trovato.
     */
    public Servizio getById(int idServizio) {
        try (Connection con = getConnection()) {
            ServizioDAO dao = createDAO();
            return dao.doSelectById(con, idServizio);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}
