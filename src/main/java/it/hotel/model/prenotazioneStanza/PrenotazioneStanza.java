package it.hotel.model.prenotazioneStanza;

import java.sql.Date;

/**
 * Rappresenta una PrenotazioneStanza e fornisce metodi per il recupero dello stato.
 */
public class PrenotazioneStanza {

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
     * @param idPrenotazioneStanza
     * @param ksUtente
     * @param ksStanza
     * @param ksStato
     * @param dataInizio
     * @param dataFine
     * @param prezzoFinale
     * @param tokenStripe
     * @param tokenQr
     * @param commenti
     * @param valutazione
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
     * @return
     */
    public int getIdPrenotazioneStanza() {
        return idPrenotazioneStanza;
    }

    /**
     * @return
     */
    public int getKsUtente() {
        return ksUtente;
    }

    /**
     * @return
     */
    public int getKsStanza() {
        return ksStanza;
    }

    /**
     * @return
     */
    public int getKsStato() {
        return ksStato;
    }

    /**
     * @return
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * @return
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * @return
     */
    public double getPrezzoFinale() {
        return prezzoFinale;
    }

    /**
     * @return
     */
    public String getTokenStripe() {
        return tokenStripe;
    }

    /**
     * @return
     */
    public String getTokenQr() {
        return tokenQr;
    }

    /**
     * @return
     */
    public String getCommenti() {
        return commenti;
    }

    /**
     * @return
     */
    public int getValutazione() {
        return valutazione;
    }

}
