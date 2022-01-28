package it.hotel.model.personaAggiuntiva;

import it.hotel.Utility.Connect;

import java.sql.*;

/**
 * Fornisce l'accesso al database per {@link PersonaAggiuntiva}.
 */
public class PersonaAggiuntivaDAO {

    /**
     * Inserisce nel database e ritorna un oggetto {@link PersonaAggiuntiva} secondo i valori specificati.
     * @param ksUtente
     * @param cf
     * @param nome
     * @param cognome
     * @param dataNascita
     * @return
     */
    public PersonaAggiuntiva doInsert(int ksUtente, String cf, String nome, String cognome, Date dataNascita) {
        try (Connection con = Connect.getConnection()) {
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
