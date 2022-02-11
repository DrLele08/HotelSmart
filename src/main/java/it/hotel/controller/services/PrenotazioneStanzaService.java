package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.Utility.Utility;
import it.hotel.controller.exception.PagamentoInAttesaException;
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
import it.hotel.model.stato.Stato;
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

    /**
     * Costruisce un oggetto PrenotazioneStanzaService.
     */
    public PrenotazioneStanzaService() {}

    /**
     * Costruisce un oggetto PrenotazioneStanzaDAO.
     * @return L'oggetto PrenotazioneStanzaDAO costruito.
     */
    public PrenotazioneStanzaDAO createPrenotazioneStanzaDAO()
    {
        return new PrenotazioneStanzaDAO();
    }

    /**
     * Costruisce un oggetto StanzaDAO.
     * @return L'oggetto StanzaDAO costruito.
     */
    public StanzaDAO createStanzaDAO()
    {
        return new StanzaDAO();
    }

    /**
     * Costruisce un oggetto PersonaAggiuntivaDAO.
     * @return L'oggetto PersonaAggiuntivaDAO costruito.
     */
    public PersonaAggiuntivaDAO createPersonaAggiuntivaDAO()
    {
        return new PersonaAggiuntivaDAO();
    }

    /**
     * Costruisce un oggetto PersonaPrenotazioneDAO.
     * @return L'oggetto PersonaPrenotazioneDAO costruito.
     */
    public PersonaPrenotazioneDAO createPersonaPrenotazioneDAO()
    {
        return new PersonaPrenotazioneDAO();
    }

    /**
     * Costruisce un oggetto StatoDAO.
     * @return L'oggetto StatoDAO costruito.
     */
    public StatoDAO createStatoDAO()
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
     * @throws PagamentoInAttesaException Esiste un'altra prenotazione in attesa di pagamento
     * @throws PrenotazioneStanzaNotFoundException La prenotazione non è stata trovata
     * @throws SQLException Errore nella comunicazione con il database
     */
    public PrenotazioneStanza inserisciPrenotazione(int ksUtente, int ksStanza, String dataInizio, String dataFine, List<PersonaAggiuntiva> listExtra)
            throws StanzaNotFoundException, ParseException, PagamentoInAttesaException, PrenotazioneStanzaNotFoundException, SQLException, PrenotazioneStanzaInsertException {

        PrenotazioneStanza prenotazione;
        Connection con = null;
        try {
            con=getConnection();
            con.setAutoCommit(false);

            StanzaDAO stanzaDAO = createStanzaDAO();
            Stanza s = stanzaDAO.doSelectById(con, ksStanza);
            double costoNotte = s.getCostoNotte();
            double sconto = s.getSconto();
            Date inizio = Utility.dataConverter(dataInizio);
            Date fine = Utility.dataConverter(dataFine);
            if (!stanzaDAO.isDisponibile(con, ksStanza, inizio, fine)) {
                throw new PrenotazioneStanzaInsertException();
            }
            int ksStato =  1;
            int idPrenotazione;
            try {
                PrenotazioneStanzaDAO prenotazioneStanzaDAO = createPrenotazioneStanzaDAO();
                idPrenotazione = prenotazioneStanzaDAO.doInsert(con, ksUtente, ksStato, ksStanza, inizio, fine, costoNotte, sconto);
            } catch (PrenotazioneStanzaInsertException e) {
                throw new PagamentoInAttesaException();
            }
            PrenotazioneStanzaDAO prenotazioneStanzaDAO = createPrenotazioneStanzaDAO();
            prenotazione = prenotazioneStanzaDAO.doSelectById(con, idPrenotazione);
            int posti = s.getLettiMatrimoniali()*2 + s.getLettiSingoli();
            if (listExtra.size() + 1 != posti) {
                throw new PrenotazioneStanzaInsertException();
            }
            for (PersonaAggiuntiva p : listExtra) {
                if (p.getCf().length()!=16 || p.getNome().trim().isEmpty() || p.getCognome().trim().isEmpty()) {
                    throw new PrenotazioneStanzaInsertException();
                }
                PersonaAggiuntivaDAO personaAggiuntivaDAO = createPersonaAggiuntivaDAO();
                int idPersona = personaAggiuntivaDAO.doInsert(con, ksUtente, p.getCf(), p.getNome(), p.getCognome(), p.getDataNascita()).getIdPersona();
                PersonaPrenotazioneDAO personaPrenotazioneDAO = createPersonaPrenotazioneDAO();
                personaPrenotazioneDAO.doInsert(con, idPersona, prenotazione.getIdPrenotazioneStanza());
            }

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            con.rollback();
            con.setAutoCommit(true);
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
        try (Connection con = getConnection()) {
            PrenotazioneStanzaDAO prenotazioneStanzaDAO = createPrenotazioneStanzaDAO();
            prenotazioni = prenotazioneStanzaDAO.doSelectBy(con, value, type);
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
        try (Connection con = getConnection()) {
            PrenotazioneStanzaDAO prenotazioneStanzaDAO = createPrenotazioneStanzaDAO();
            prenotazioni = prenotazioneStanzaDAO.doGetAll(con);
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
        try (Connection con = getConnection()) {
            PrenotazioneStanzaDAO prenotazioneStanzaDAO = createPrenotazioneStanzaDAO();
            prenotazioneStanzaDAO.doChangeStato(con, idPrenotazioneStanza, stato);
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
        try (Connection con = getConnection()) {
            PrenotazioneStanzaDAO prenotazioneStanzaDAO = createPrenotazioneStanzaDAO();
            prenotazione = prenotazioneStanzaDAO.doSelectById(con, idPrenotazione);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return prenotazione;
    }

    /**
     * Informa se la prenotazione stanza specificata è rimborsabile.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @return Rimborsabilità della prenotazione
     * @throws SQLException Errore nella comunicazione con il database
     */
    public boolean isRimborsabile(int idPrenotazione) throws SQLException {
        boolean rimborsabile;
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);

            StatoDAO statoDAO = createStatoDAO();
            Stato stato = statoDAO.doSelectByStato(con, "CONFERMATA");
            int confermata = stato.getIdStato();
            PrenotazioneStanzaDAO prenotazioneStanzaDAO = createPrenotazioneStanzaDAO();
            rimborsabile = prenotazioneStanzaDAO.isRimborsabile(con, idPrenotazione, confermata);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            con.rollback();
            con.setAutoCommit(true);
            throw new RuntimeException();
        } catch (StatoNotFoundException e) {
            con.rollback();
            con.setAutoCommit(true);
            throw new RuntimeException();
        }
        return rimborsabile;
    }

    /**
     * Inserisce nella prenotazione stanza specificata il Token Stripe relativo al pagamento effettuato.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @param tokenStripe Token da inserire nella prenotazione stanza
     * @throws PrenotazioneStanzaNotFoundException La prenotazione stanza specificata non è stata trovata
     */
    public void addTokenStripe(int idPrenotazione, String tokenStripe) throws PrenotazioneStanzaNotFoundException {
        try (Connection con = getConnection()) {
            PrenotazioneStanzaDAO prenotazioneStanzaDAO = createPrenotazioneStanzaDAO();
            prenotazioneStanzaDAO.insertTokenStripe(con, idPrenotazione, tokenStripe);
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
            try (Connection con = getConnection()) {
                for (int i = 0; i < len; i++)
                    sb.append(AB.charAt(rnd.nextInt(AB.length())));
                PrenotazioneStanzaDAO prenotazioneStanzaDAO = createPrenotazioneStanzaDAO();
                prenotazioneStanzaDAO.doInsertTokenQrCode(con, idPrenotazione, sb.toString());
                duplicate = false;
            } catch (SQLIntegrityConstraintViolationException e) {
                duplicate = true;
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        } while (duplicate);
    }

}
