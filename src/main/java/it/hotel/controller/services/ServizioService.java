package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.model.servizio.Servizio;
import it.hotel.model.servizio.ServizioDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Fornisce metodi di utilizzo del database per {@link Servizio}.
 */
public class ServizioService {

    private final ServizioDAO dao;

    /**
     * Costruisce un oggetto ServizioService.
     */
    public ServizioService() { this.dao = new ServizioDAO(); }

    /**
     * Inserisce un servizio secondo i valori specificati.
     * @param nome Nome
     * @param descrizione Descrizione
     * @param foto Percorso foto
     * @param costo Costo
     * @param limitePosti Posti disponibili
     */
    public void createServizio(String nome, String descrizione, String foto, double costo, int limitePosti) {
        try (Connection con = Connect.getConnection()) {
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
        try (Connection con = Connect.getConnection()) {
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
        try (Connection con = Connect.getConnection()) {
            return dao.getServizi(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Recupera i servizi utilizzati dall'utente specificato.
     * @param idUtente Identificativo dell'utente
     * @return Lista contenente i servizi trovati per l'utente
     */
    public List<Servizio> getByUser(int idUtente) {
        try (Connection con = Connect.getConnection()) {
            return dao.doSelectByUserId(con, idUtente);
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
        try (Connection con = Connect.getConnection()) {
            return dao.doSelectById(con, idServizio);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}
