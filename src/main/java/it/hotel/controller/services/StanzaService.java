package it.hotel.controller.services;


import it.hotel.Utility.Connect;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;
import it.hotel.model.stato.StatoDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fornisce metodi di utilizzo del database per {@link Stanza}.
 */
public class StanzaService
{

    /**
     * Costruisce un oggetto StanzaService.
     */
    public StanzaService() {}

    /**
     * Costruisce un oggetto StanzaDAO.
     * @return L'oggetto StanzaDAO costruito.
     */
    public StanzaDAO createDAO()
    {
        return new StanzaDAO();
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
     * Recupera tutte le stanze presenti nel database.
     * @return Lista contenente le stanze trovate
     */
    public List<Stanza> getStanze() {
        StanzaDAO dao = createDAO();
        List<Stanza> stanze;
        try (Connection con = getConnection()) {
            stanze = dao.getStanze(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return stanze;
    }

    /**
     * Recupera tutte le stanze con sconto maggiore di ZERO.
     * @return Lista contenente le stanze trovate
     */
    public List<Stanza> getOfferte() {
        StanzaDAO dao = createDAO();
        List<Stanza> stanze;
        try (Connection con = getConnection()) {
            stanze = dao.getOfferte(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return stanze;
    }

    /**
     * Restituisce il più basso e il più alto tra i prezzi tra tutte le stanze.
     * @return Lista contenente il prezzo più basso e il prezzo più alto
     */
    public List<Double> get_Min_And_Max_Prices() {
        StanzaDAO dao = createDAO();
        List<Double> prezzi;
        try (Connection con = getConnection()) {
            prezzi = dao.doSelect_Min_And_Max_Prices(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return prezzi;
    }

    /**
     * Restituisce le stanze trovate secondo i valori specificati.
     * @param animaleDomestico Idoneità per animali domestici
     * @param fumatore Idoneità per fumatori
     * @param numeroOspiti Numero di ospiti
     * @param costoNotteMinimo Costo per notte minimo
     * @param costoNotteMassimo Costo per notte massimo
     * @param scontoMinimo Sconto applicabile minimo
     * @param scontoMassimo Sconto applicabile massimo
     * @param dataIn Data di ingresso
     * @param dataOut Data di uscita
     * @return Le stanze trovate
     */
    public List<Stanza> search(Boolean animaleDomestico, Boolean fumatore, Integer numeroOspiti, Double costoNotteMinimo, Double costoNotteMassimo,
                               Double scontoMinimo, Double scontoMassimo, java.sql.Date dataIn, Date dataOut) {
        StanzaDAO dao = createDAO();
        List<Stanza> stanze;
        try (Connection con = getConnection()) {
            stanze = dao.doSearch(con, animaleDomestico, fumatore, numeroOspiti,
                    costoNotteMinimo, costoNotteMassimo, scontoMinimo, scontoMassimo, dataIn, dataOut);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return stanze;
    }

    /**
     * Restituisce la stanza trovata secondo l'identificativo specificato.
     * @param stanzaId Identificativo della stanza
     * @return Stanza trovata
     * @throws StanzaNotFoundException Non è stata trovata la stanza cercata
     */
    public Stanza selectById(Integer stanzaId) throws StanzaNotFoundException
    {
        StanzaDAO dao = createDAO();
        Stanza stanza;
        try (Connection con= getConnection()) {
            stanza = dao.doSelectById(con, stanzaId);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return stanza;
    }

    /**
     * Inserisce una stanza secondo i valori specificati.
     * @param animale Idoneità per animali domestici
     * @param fumatore Idoneità per fumatori
     * @param lettiSingoli Quantità letti singoli
     * @param lettiMatrimoniali Quantità letti matrimoniali
     * @param costoNotte Costo per notte
     * @param sconto Sconto applicabile
     */
    public void insertStanza(boolean animale, boolean fumatore, int lettiSingoli, int lettiMatrimoniali,
                             double costoNotte, double sconto) {
        StanzaDAO dao = createDAO();
        try (Connection con = getConnection()) {
            dao.doInsert(con, animale, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Aggiorna una stanza secondo i valori specificati.
     * @param animale Idoneità per animali domestici
     * @param fumatore Idoneità per fumatori
     * @param lettiSingoli Quantità letti singoli
     * @param lettiMatrimoniali Quantità letti matrimoniali
     * @param costoNotte Costo per notte
     * @param sconto Sconto applicabile
     */
    public void updateStanza(int idStanza, boolean animale, boolean fumatore, int lettiSingoli,
                             int lettiMatrimoniali, double costoNotte, double sconto) {
        StanzaDAO dao = createDAO();
        try (Connection con = getConnection()) {
            dao.doUpdate(con, idStanza, animale, fumatore, lettiSingoli, lettiMatrimoniali, costoNotte, sconto);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
