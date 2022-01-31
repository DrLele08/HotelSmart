package it.hotel.model.personaPrenotazione;

public class PersonaPrenotazione {

    private int idPersonaPrenotazione;
    private int idPersonaAggiuntiva;
    private int idPrenotazioneStanza;

    public PersonaPrenotazione(int idPersonaPrenotazione, int idPersonaAggiuntiva, int idPrenotazioneStanza) {
        this.idPersonaPrenotazione = idPersonaPrenotazione;
        this.idPersonaAggiuntiva = idPersonaAggiuntiva;
        this.idPrenotazioneStanza = idPrenotazioneStanza;
    }

    public int getIdPersonaPrenotazione() {
        return idPersonaPrenotazione;
    }

    public int getIdPersonaAggiuntiva() {
        return idPersonaAggiuntiva;
    }

    public int getIdPrenotazioneStanza() {
        return idPrenotazioneStanza;
    }
}
