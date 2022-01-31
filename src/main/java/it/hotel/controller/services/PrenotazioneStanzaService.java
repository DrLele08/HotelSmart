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

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.List;

/**
 * Fornisce metodi di utilizzo del database per {@link PrenotazioneStanza}.
 */
public class PrenotazioneStanzaService {

    private final PrenotazioneStanzaDAO prenotazioneStanzaDAO;
    private final StanzaDAO daoStanza;
    private final PersonaAggiuntivaDAO personaAggiuntivaDAO;
    private final PersonaPrenotazioneDAO personaPrenotazioneDAO;

    /**
     * Costruisce un oggetto PrenotazioneStanzaService.
     */
    public PrenotazioneStanzaService() {
        this.prenotazioneStanzaDAO = new PrenotazioneStanzaDAO();
        this.daoStanza = new StanzaDAO();
        this.personaAggiuntivaDAO = new PersonaAggiuntivaDAO();
        this.personaPrenotazioneDAO = new PersonaPrenotazioneDAO();
    }

    /**
     * Inserisce una prenotazione stanza secondo i valori specificati.
     * @param ksUtente Identificativo utente
     * @param ksStanza Identificativo stanza
     * @param dataInizio Data d'inizio
     * @param dataFine Data di fine
     * @param listExtra Lista di persone aggiuntive
     * @return Prenotazione stanza inserita
     *
     */
    public PrenotazioneStanza inserisciPrenotazione(int ksUtente, int ksStanza, String dataInizio, String dataFine, List<PersonaAggiuntiva> listExtra)
            throws StanzaNotFoundException, ParseException, PrenotazioneStanzaInsertException, PrenotazioneStanzaNotFoundException {

        PrenotazioneStanza prenotazione;
        try (Connection con = Connect.getConnection()) {
            con.setAutoCommit(false);

            Stanza s = daoStanza.doSelectById(ksStanza);
            double costoNotte = s.getCostoNotte();
            Date inizio = Utility.dataConverter(dataInizio);
            Date fine = Utility.dataConverter(dataFine);
            prenotazione = prenotazioneStanzaDAO.doInsert(con, ksUtente, ksStanza, inizio, fine, costoNotte);
            for (PersonaAggiuntiva persona : listExtra) {
                int idPersona = personaAggiuntivaDAO.doInsert(con, ksUtente, persona.getCf(), persona.getNome(), persona.getCognome(), persona.getDataNascita()).getIdPersona();
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
        return prenotazioneStanzaDAO.doSelectBy(value, type);
    }

    /**
     * Recupera tutte le prenotazioni stanza presenti nel database.
     * @return Lista contenente le prenotazioni stanza trovate
     */
    public List<PrenotazioneStanza> getAll() {
        return prenotazioneStanzaDAO.doGetAll();
    }

    /**
     * Modifica lo stato della prenotazione stanza specificata.
     * @param idPrenotazioneStanza Identificativo della prenotazione stanza
     * @param stato Stato da inserire
     * @throws PrenotazioneStanzaNotFoundException La prenotazione stanza non è stata trovata
     */
    public void editStato(int idPrenotazioneStanza, int stato) throws PrenotazioneStanzaNotFoundException {
        prenotazioneStanzaDAO.doChangeStato(idPrenotazioneStanza, stato);
    }

    /**
     * Recupera una prenotazione stanza secondo il valore specificato.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @return La prenotazione stanza trovata
     * @throws PrenotazioneStanzaNotFoundException La prenotazione stanza cercata non è stata trovata
     */
    public PrenotazioneStanza getPrenotazioneById(int idPrenotazione) throws PrenotazioneStanzaNotFoundException {
        return prenotazioneStanzaDAO.doSelectById(idPrenotazione);
    }

    /**
     * Informa se la prenotazione stanza specificata è rimborsabile.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @return Rimborsabilità della prenotazione
     */
    public boolean isRimborsabile(int idPrenotazione) {
        return prenotazioneStanzaDAO.isRimborsabile(idPrenotazione);
    }

    /**
     * Inserisce nella prenotazione stanza specificata il Token Stripe relativo al pagamento effettuato.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @param tokenStripe Token da inserire
     */
    public void addTokenStripe(int idPrenotazione, String tokenStripe) throws PrenotazioneStanzaNotFoundException {
        prenotazioneStanzaDAO.insertTokenStripe(idPrenotazione, tokenStripe);
    }

    /**
     * Inserisce nella prenotazione stanza specificata un Token Qr Code alfanumerico generato casualmente.
     * @param idPrenotazione Identificativo della prenotazione stanza
     */
    public void generateQrCode(int idPrenotazione) throws PrenotazioneStanzaNotFoundException
    {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        int len=48;
        StringBuilder sb = new StringBuilder(len);
        boolean duplicate;
        do {
            try {
                for (int i = 0; i < len; i++)
                    sb.append(AB.charAt(rnd.nextInt(AB.length())));
                prenotazioneStanzaDAO.doInsertTokenQrCode(idPrenotazione, sb.toString());
                duplicate = false;
            } catch (SQLIntegrityConstraintViolationException e) {
                duplicate = true;
            }
        } while (duplicate);
    }

}
