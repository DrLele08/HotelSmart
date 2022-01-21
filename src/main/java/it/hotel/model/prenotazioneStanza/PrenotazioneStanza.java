package it.hotel.model.prenotazioneStanza;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Rappresenta una PrenotazioneStanza e fornisce metodi per il recupero dello stato.
 */
public class PrenotazioneStanza implements Serializable
{

    private int idPrenotazioneStanza;
    private int ksUtente;
    private int ksStanza;
    private int ksStato;
    private Date dataInizio;
    private Date dataFine;
    private double prezzoFinale;
    private String tokenStripe;
    private String tokenQr;
    private String commenti;
    private int valutazione;

    /**
     * Costruisce un oggetto PrenotazioneStanza.
     * @param idPrenotazioneStanza Id della prenotazione
     * @param ksUtente Utente che effettua la prenotazione
     * @param ksStanza Stanza prenotata
     * @param ksStato Stato della prenotazione
     * @param dataInizio Data di entrata
     * @param dataFine Data di uscita
     * @param prezzoFinale Prezzo finale
     * @param tokenStripe Token di Stripe
     * @param tokenQr Token del codice qr
     * @param commenti Commenti
     * @param valutazione Valutazione
     */
    public PrenotazioneStanza(int idPrenotazioneStanza, int ksUtente, int ksStanza, int ksStato,
                Date dataInizio, Date dataFine, double prezzoFinale, String tokenStripe,
                        String tokenQr, String commenti, int valutazione) {
        this.idPrenotazioneStanza = idPrenotazioneStanza;
        this.ksUtente = ksUtente;
        this.ksStanza = ksStanza;
        this.ksStato = ksStato;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.prezzoFinale = prezzoFinale;
        this.tokenStripe = tokenStripe;
        this.tokenQr = tokenQr;
        this.commenti = commenti;
        this.valutazione = valutazione;
    }

    /**
     * @return Id della prenotazione
     */
    public int getIdPrenotazioneStanza() {
        return idPrenotazioneStanza;
    }

    /**
     * @return Utente che effettua la prenotazione
     */
    public int getKsUtente() {
        return ksUtente;
    }

    /**
     * @return Stanza prenotata
     */
    public int getKsStanza() {
        return ksStanza;
    }

    /**
     * @return Stato della prenotazione
     */
    public int getKsStato() {
        return ksStato;
    }

    /**
     * @return Data di entrata
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * @return Data di uscita
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * @return Prezzo finale
     */
    public double getPrezzoFinale() {
        return prezzoFinale;
    }

    /**
     * @return Token di Stripe
     */
    public String getTokenStripe() {
        return tokenStripe;
    }

    /**
     * @return Token del codice qr
     */
    public String getTokenQr() {
        return tokenQr;
    }

    /**
     * @return Commenti
     */
    public String getCommenti() {
        return commenti;
    }

    /**
     * @return Valutazione
     */
    public int getValutazione() {
        return valutazione;
    }

    /**
     * restituisce una data formattata in standard italiano
     * @param date data non formattata YYYY-mm-dd
     * @return date formattato
     */
    public String convertDateToView(Date date){
        final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);

    }

}
