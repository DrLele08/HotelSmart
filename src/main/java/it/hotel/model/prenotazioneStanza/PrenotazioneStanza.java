package it.hotel.model.prenotazioneStanza;

import java.sql.Date;

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

    public int getIdPrenotazioneStanza() {
        return idPrenotazioneStanza;
    }

    public int getKsUtente() {
        return ksUtente;
    }

    public int getKsStanza() {
        return ksStanza;
    }

    public int getKsStato() {
        return ksStato;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public double getPrezzoFinale() {
        return prezzoFinale;
    }

    public String getTokenStripe() {
        return tokenStripe;
    }

    public String getTokenQr() {
        return tokenQr;
    }

    public String getCommenti() {
        return commenti;
    }

    public int getValutazione() {
        return valutazione;
    }

}
