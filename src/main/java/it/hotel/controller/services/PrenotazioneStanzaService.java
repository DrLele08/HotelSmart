package it.hotel.controller.services;

import it.hotel.Utility.Connect;
import it.hotel.Utility.Utility;
import it.hotel.controller.exception.PagamentoInAttesaException;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaInsertException;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaNotFoundException;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.StanzaDAO;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Random;

/**
 * Fornisce metodi di utilizzo del database per {@link PrenotazioneStanza}.
 */
public class PrenotazioneStanzaService {

    private final PrenotazioneStanzaDAO dao;
    private final StanzaDAO daoStanza;
    /**
     * Costruisce un oggetto PrenotazioneStanzaService.
     */
    public PrenotazioneStanzaService() {
        this.dao = new PrenotazioneStanzaDAO();
        this.daoStanza=new StanzaDAO();
    }

    /**
     * Inserisce una prenotazione stanza secondo i valori specificati.
     * @param ksUtente Identificativo utente
     * @param ksStanza Identificativo stanza
     * @param dataInizio Data d'inizio
     * @param dataFine Data di fine
     * @param commenti Commenti
     * @param valutazione Valutazione
     * @return Prenotazione stanza inserita
     * @throws PagamentoInAttesaException Un altro pagamento è in attesa di essere completato
     */
    public PrenotazioneStanza inserisciPrenotazione(int ksUtente, int ksStanza, String  dataInizio, String dataFine,
            String commenti, int valutazione) throws PagamentoInAttesaException {
        try
        {
            Stanza s=daoStanza.doSelectById(ksStanza);
            double costoNotte=s.getCostoNotte();
            Date inizio=Utility.dataConverter(dataInizio);
            Date fine=Utility.dataConverter(dataFine);
            return dao.doInsert(ksUtente, ksStanza, dataInizio, dataFine, prezzoFinale, commenti, valutazione);
        }
        catch (PrenotazioneStanzaInsertException e)
        {
            throw new PagamentoInAttesaException();
        }
    }

    /**
     * Recupera le prenotazioni stanza presenti nel database secondo i valori specificati.
     * @param value Valore secondo il quale effettuare la ricerca
     * @param type Tipo del valore
     * @return Prenotazioni stanza trovate
     */
    public List<PrenotazioneStanza> selectBy(int value, int type) {
        return dao.doSelectBy(value, type);
    }

    /**
     * Recupera tutte le prenotazioni stanza presenti nel database.
     * @return Lista contenente le prenotazioni stanza trovate
     */
    public List<PrenotazioneStanza> getAll() {
        return dao.doGetAll();
    }

    /**
     * Modifica lo stato della prenotazione stanza specificata.
     * @param idPrenotazioneStanza Identificativo della prenotazione stanza
     * @param stato Stato da inserire
     * @throws PrenotazioneStanzaNotFoundException La prenotazione stanza non è stata trovata
     */
    public void editStato(int idPrenotazioneStanza, int stato) throws PrenotazioneStanzaNotFoundException {
        dao.doChangeStato(idPrenotazioneStanza, stato);
    }

    /**
     * Recupera una prenotazione stanza secondo il valore specificato.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @return La prenotazione stanza trovata
     * @throws PrenotazioneStanzaNotFoundException La prenotazione stanza cercata non è stata trovata
     */
    public PrenotazioneStanza getPrenotazioneById(int idPrenotazione) throws PrenotazioneStanzaNotFoundException {
        return dao.doSelectById(idPrenotazione);
    }

    /**
     * Informa se la prenotazione stanza specificata è rimborsabile.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @return Rimborsabilità della prenotazione
     */
    public boolean isRimborsabile(int idPrenotazione) {
        return dao.isRimborsabile(idPrenotazione);
    }

    /**
     * Inserisce nella prenotazione stanza specificata il Token Stripe relativo al pagamento effettuato.
     * @param idPrenotazione Identificativo della prenotazione stanza
     * @param tokenStripe Token da inserire
     */
    public void addTokenStripe(int idPrenotazione, String tokenStripe) throws PrenotazioneStanzaNotFoundException {
        dao.insertTokenStripe(idPrenotazione, tokenStripe);
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
                dao.doInsertTokenQrCode(idPrenotazione, sb.toString());
                duplicate = false;
            } catch (SQLIntegrityConstraintViolationException e) {
                duplicate = true;
            }
        } while (duplicate);
    }

}
