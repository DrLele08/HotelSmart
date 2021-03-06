package it.hotel.model.personaAggiuntiva;

import java.sql.*;

/**
 * Fornisce l'accesso al database per {@link PersonaAggiuntiva}.
 */
public class PersonaAggiuntivaDAO {

    /**
     * Inserisce nel database e ritorna un oggetto {@link PersonaAggiuntiva} secondo i valori specificati.
     * @param con Connessione al database
     * @param ksUtente Identificativo dell'utente
     * @param cf Codice fiscale
     * @param nome Nome
     * @param cognome Cognome
     * @param dataNascita Data di nascita
     * @return L'oggetto inserito nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public PersonaAggiuntiva doInsert(Connection con, int ksUtente, String cf, String nome, String cognome, Date dataNascita) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("INSERT INTO PersonaAggiuntiva (ksUtente, cf, nome, cognome, dataNascita) " +
                                "VALUES(?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ksUtente);
        ps.setString(2, cf);
        ps.setString(3, nome);
        ps.setString(4, cognome);
        ps.setDate(5, dataNascita);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        int id;
        if (rs.next()) {
            id = rs.getInt(1);
        } else {
            return null;
        }
        return new PersonaAggiuntiva(id, ksUtente, cf, nome, cognome, dataNascita);
    }

}
