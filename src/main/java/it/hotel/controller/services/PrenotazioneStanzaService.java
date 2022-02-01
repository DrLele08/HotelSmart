package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.Utility.Utility;
import it.hotel.model.personaAggiuntiva.PersonaAggiuntiva;
import it.hotel.model.personaAggiuntiva.PersonaAggiuntivaDAO;
import it.hotel.model.personaPrenotazione.PersonaPrenotazioneDAO;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaInsertException;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaNotFoundException;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;
import it.hotel.model.stato.StatoDAO;
import it.hotel.model.stato.statoExceptions.StatoNotFoundException;

import java.security.SecureRandom;
import java.sql.*;
import java.text.ParseException;
import java.util.List;

/**
 * Fornisce metodi di utilizzo del database per {@link PrenotazioneStanza}.
 */
public class PrenotazioneStanzaService {

    private final PrenotazioneStanzaDAO prenotazioneStanzaDAO;
    private final StanzaDAO stanzaDAO;
    private final PersonaAggiuntivaDAO personaAggiuntivaDAO;
    private final PersonaPrenotazioneDAO personaPrenotazioneDAO;
    private final StatoDAO statoDAO;

    /**
     * Costruisce un oggetto PrenotazioneStanzaService.
     */
    public PrenotazioneStanzaService() {
        this.prenotazioneStanzaDAO = new PrenotazioneStanzaDAO();
        this.stanzaDAO = new StanzaDAO();
        this.personaAggiuntivaDAO = new PersonaAggiuntivaDAO();
        this.personaPrenotazioneDAO = new PersonaPrenotazioneDAO();
        this.statoDAO = new StatoDAO();
    }

    /**
     * Inserisce una prenotazione stanza secondo i valori specificati.
     * @param ksUtente Identificativo utente
     * @param ksStanza Identificativo stanza
     * @param dataInizio Data d'inizio
     * @param dataFine Data di fine
     * @param listExtra Lista di persone aggiuntive
     * @return Prenotazione stanza inserita
     * @throws StanzaNotFoundException La stanza della prenotazione non è stata trovata
     * @throws ParseException Errore nella conversione delle date
     * @throws PrenotazioneStanzaInsertException Errore nell'inserimento della prenotazione
     * @throws PrenotazioneStanzaNotFoundException La prenotazione non è stata trovata
     */
    public PrenotazioneStanza inserisciPrenotazione(int ksUtente, int ksStanza, String dataInizio, String dataFine, List<PersonaAggiuntiva> listExtra)
            throws StanzaNotFoundException, ParseException, PrenotazioneStanzaInsertException, PrenotazioneStanzaNotFoundException {

        PrenotazioneStanza prenotazione;
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            Stanza s = stanzaDAO.doSelectById(ksStanza);
            double costoNotte = s.getCostoNotte();
            Date inizio = Utility.dataConverter(dataInizio);
            Date fine = Utility.dataConverter(dataFine);
            if (!stanzaDAO.isDisponibile(con, ksStanza, inizio, fine)) {
                throw new PrenotazioneStanzaInsertException();
            }
            int ksStato =  new StatoService().getByStato("IN ATTESA DI PAGAMENTO");
            int idPrenotazione = prenotazioneStanzaDAO.doInsert(con, ksUtente, ksStato, ksStanza, inizio, fine, costoNotte);
            prenotazione = prenotazioneStanzaDAO.doSelectById(con, idPrenotazione);
            for (PersonaAggiuntiva p : listExtra) {
                if (p.getCf().length()!=16 || p.getNome().trim().isEmpty() || p.getCognome().trim().isEmpty()) {
                    throw new PrenotazioneStanzaInsertException();
                }
                int idPersona = personaAggiuntivaDAO.doInsert(con, ksUtente, p.getCf(), p.getNome(), p.getCognome(), p.getDataNascita()).getIdPersona();
                personaPrenotazioneDAO.doInsert(con, idPersona, prenotazione.getIdPrenotazioneStanza());
            }

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return prenotazione;

    }

    /**
     * Recupera le prenotazioni stanza presenti nel database secondo i valori specificati.
     * @param value Valore secondo il quale effettuare la ricerca
     * @param type Tipo del valore
     * @return Prenotazioni stanza trovate
     */
    public List<PrenotazioneStanza> selectBy(int value, int type) {
        List<PrenotazioneStanza> prenotazioni;
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            prenotazioni = prenotazioneStanzaDAO.doSelectBy(con, value, type);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return prenotazioni;
    }

    /**
     * Recupera tutte le prenotazioni stanza presenti nel database.
     * @return Lista contenente le prenotazioni stanza trovate
     */
    public List<PrenotazioneStanza> getAll() {
        List<PrenotazioneStanza> prenotazioni;
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            prenotazioni = prenotazioneStanzaDAO.doGetAll(con);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return prenotazioni;
    }

    /**
     * Modifica lo stato della prenotazione stanza specificata.
     * @param idPrenotazioneStanza Identificativo della prenotazione stanza
     * @param stato Stato da inserire
     * @throws PrenotazioneStanzaNotFoundException La prenotazione stanza non è stata trovata
     */
    public void editStato(int idPrenotazioneStanza, int stato) throws PrenotazioneStanzaNotFoundException {
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            prenotazioneStanzaDAO.doChangeStato(con, idPrenotazioneStanza, stato);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Recupera una prenotazione stanza secondo il valore specificato.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @return La prenotazione stanza trovata
     * @throws PrenotazioneStanzaNotFoundException La prenotazione stanza cercata non è stata trovata
     */
    public PrenotazioneStanza getPrenotazioneById(int idPrenotazione) throws PrenotazioneStanzaNotFoundException {
        PrenotazioneStanza prenotazione;
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            prenotazione = prenotazioneStanzaDAO.doSelectById(con, idPrenotazione);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return prenotazione;
    }

    /**
     * Informa se la prenotazione stanza specificata è rimborsabile.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @return Rimborsabilità della prenotazione
     */
    public boolean isRimborsabile(int idPrenotazione) {
        boolean rimborsabile;
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            int confermata = statoDAO.doSelectByStato(con, "CONFERMATA").getIdStato();
            rimborsabile = prenotazioneStanzaDAO.isRimborsabile(con, idPrenotazione, confermata);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        } catch (StatoNotFoundException e) {
            throw new RuntimeException();
        }
        return rimborsabile;
    }

    /**
     * Inserisce nella prenotazione stanza specificata il Token Stripe relativo al pagamento effettuato.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @param tokenStripe Token da inserire
     * @throws PrenotazioneStanzaNotFoundException La prenotazione stanza specificata non è stata trovata
     */
    public void addTokenStripe(int idPrenotazione, String tokenStripe) throws PrenotazioneStanzaNotFoundException {
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            prenotazioneStanzaDAO.insertTokenStripe(con, idPrenotazione, tokenStripe);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Inserisce nella prenotazione stanza specificata un Token Qr Code alfanumerico generato casualmente.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @throws PrenotazioneStanzaNotFoundException La prenotazione stanza specificata non è stata trovata
     */
    public void generateQrCode(int idPrenotazione) throws PrenotazioneStanzaNotFoundException
    {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        int len=48;
        StringBuilder sb = new StringBuilder(len);
        boolean duplicate;
        do {
            try (Connection con = Connect.getConnection()) {
                con.setAutoCommit(false);

                for (int i = 0; i < len; i++)
                    sb.append(AB.charAt(rnd.nextInt(AB.length())));
                prenotazioneStanzaDAO.doInsertTokenQrCode(con, idPrenotazione, sb.toString());
                duplicate = false;

                con.commit();
                con.setAutoCommit(true);
            } catch (SQLIntegrityConstraintViolationException e) {
                duplicate = true;
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        } while (duplicate);
    }

}
