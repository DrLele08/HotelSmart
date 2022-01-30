package it.hotel.model.prenotazioneServizio;

import java.sql.Date;

/**
 * Rappresenta una PrenotazioneServizio.
 */
public class PrenotazioneServizio {

    private int idPrenotazioneServizio;
    private int ksPrenotazioneStanza;
    private int ksServizio;
    private int numPersone;
    private Date dataPrenotazioneServizio;

    /**
     * Costruisce una PrenotazioneServizio con i valori specificati.
     * @param idPrenotazioneServizio Identificativo della prenotazione servizio
     * @param ksPrenotazioneStanza Identificativo della prenotazione stanza associata
     * @param ksServizio Identificativo del servizio
     * @param numPersone Numero delle persone
     * @param dataPrenotazioneServizio Data della prenotazione del servizio
     */
    public PrenotazioneServizio(int idPrenotazioneServizio, int ksPrenotazioneStanza,
                                int ksServizio, int numPersone, Date dataPrenotazioneServizio) {
        this.idPrenotazioneServizio = idPrenotazioneServizio;
        this.ksPrenotazioneStanza = ksPrenotazioneStanza;
        this.ksServizio = ksServizio;
        this.numPersone = numPersone;
        this.dataPrenotazioneServizio = dataPrenotazioneServizio;
    }

    /**
     * Restituisce l'identificativo della prenotazione servizio.
     * @return Identificativo della prenotazione servizio
     */
    public int getIdPrenotazioneServizio() {
        return idPrenotazioneServizio;
    }

    /**
     * Restituisce l'identificativo della prenotazione stanza associata.
     * @return Identificativo della prenotazione stanza
     */
    public int getKsPrenotazioneStanza() { return ksPrenotazioneStanza; }

    /**
     * Restituisce l'identificativo del servizio.
     * @return Identificativo del servizio
     */
    public int getKsServizio() { return ksServizio; }

    /**
     * Restituisce il numero delle persone.
     * @return Numero delle persone
     */
    public int getNumPersone() { return numPersone; }

    /**
     * Restituisce la data della prenotazione del servizio.
     * @return Data della prenotazione del servizio.
     */
    public Date getDataPrenotazioneServizio() { return dataPrenotazioneServizio; }

}
