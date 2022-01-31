package it.hotel.model.personaPrenotazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonaPrenotazioneDAO {

    public void doInsert(Connection con, int idPersonaAggiuntiva, int idPrenotazioneStanza) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO PersonePrenotazione " +
                "(ksPersona, ksPrenotazioneStanza) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idPersonaAggiuntiva);
        ps.setInt(2, idPrenotazioneStanza);
        ps.executeUpdate();
    }

}
