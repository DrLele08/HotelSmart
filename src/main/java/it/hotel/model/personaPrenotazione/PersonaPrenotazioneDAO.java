package it.hotel.model.personaPrenotazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Fornisce l'accesso al database per {@link PersonaPrenotazione}.
 */
public class PersonaPrenotazioneDAO {

    /**
     * Inserisce nel database un oggetto {@link PersonaPrenotazione} secondo i valori specificati.
     * @param con Connessione al database
     * @param idPersonaAggiuntiva Identificativo della persona aggiuntiva
     * @param idPrenotazioneStanza Identificativo della prenotazione stanza
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doInsert(Connection con, int idPersonaAggiuntiva, int idPrenotazioneStanza) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO PersonePrenotazione " +
                "(ksPersona, ksPrenotazioneStanza) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idPersonaAggiuntiva);
        ps.setInt(2, idPrenotazioneStanza);
        ps.executeUpdate();
    }

}
