package it.hotel.model.personaPrenotazione;

/**
 * Rappresenta una PersonaPrenotazione e fornisce metodi per il recupero dello stato.
 */
public class PersonaPrenotazione {

    private int idPersonaPrenotazione;
    private int idPersonaAggiuntiva;
    private int idPrenotazioneStanza;

    /**
     * Costruisce una PersonaPrenotazione con i valori specificati.
     * @param idPersonaPrenotazione Identificativo della PersonaPrenotazione
     * @param idPersonaAggiuntiva Identificativo della PersonaAggiuntiva
     * @param idPrenotazioneStanza Identificativo della PrenotazioneStanza
     */
    public PersonaPrenotazione(int idPersonaPrenotazione, int idPersonaAggiuntiva, int idPrenotazioneStanza) {
        this.idPersonaPrenotazione = idPersonaPrenotazione;
        this.idPersonaAggiuntiva = idPersonaAggiuntiva;
        this.idPrenotazioneStanza = idPrenotazioneStanza;
    }

    /**
     * @return Identificativo della PersonaPrenotazione
     */
    public int getIdPersonaPrenotazione() {
        return idPersonaPrenotazione;
    }

    /**
     * @return Identificativo della PersonaAggiuntiva
     */
    public int getIdPersonaAggiuntiva() {
        return idPersonaAggiuntiva;
    }

    /**
     * @return Identificativo della PrenotazioneStanza
     */
    public int getIdPrenotazioneStanza() {
        return idPrenotazioneStanza;
    }
}
