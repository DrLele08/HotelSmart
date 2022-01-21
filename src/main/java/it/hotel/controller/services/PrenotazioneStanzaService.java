package it.hotel.controller.services;

import it.hotel.controller.exception.PagamentoInAttesaException;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaInsertException;

import java.sql.Date;
import java.util.List;

public class PrenotazioneStanzaService {

    private final PrenotazioneStanzaDAO dao;

    public PrenotazioneStanzaService() {
        this.dao = new PrenotazioneStanzaDAO();
    }

    public void inserisciPrenotazione(int ksUtente, int ksStanza, Date dataInizio, Date dataFine, double prezzoFinale,
            String tokenStripe, String tokenQr, String commenti, int valutazione) throws PagamentoInAttesaException {
        try {
            dao.doInsert(ksUtente, ksStanza, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr, commenti, valutazione);
        } catch (PrenotazioneStanzaInsertException e) {
            throw new PagamentoInAttesaException();
        }
    }

    public List<PrenotazioneStanza> selectBy(int value, int type) {
        return dao.doSelectBy(value, type);
    }

    public List<PrenotazioneStanza> getAll() {
        return dao.doGetAll();
    }
}
