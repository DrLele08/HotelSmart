package it.hotel.model.servizio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fornisce l'accesso al database per {@link Servizio}.
 */
public class ServizioDAO {

    /**
     * Inserisce nel database un oggetto Servizio secondo i valori specificati.
     * @param con Connessione al database
     * @param nome Nome
     * @param descrizione Descrizione
     * @param foto Percorso foto
     * @param costo Costo
     * @param limitePosti Posti disponibili
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doInsert(Connection con, String nome, String descrizione, String foto, double costo, int limitePosti) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("INSERT INTO Servizio (nome, descrizione, foto, costo, limitePosti) VALUES(?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, nome);
        ps.setString(2, descrizione);
        ps.setString(3, foto);
        ps.setDouble(4, costo);
        ps.setInt(5, limitePosti);
        ps.executeUpdate();
    }

    /**
     * Aggiorna un oggetto Servizio nel database secondo i valori specificati.
     * @param con Connessione al database
     * @param idServizio Identificativo
     * @param nome Nome
     * @param descrizione Descrizione
     * @param foto Percorso foto
     * @param costo Costo
     * @param limitePosti Posti disponibili
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doUpdate(Connection con, int idServizio, String nome, String descrizione, String foto, double costo, int limitePosti) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("UPDATE Servizio SET nome=?, descrizione=?, foto=?, costo=?, " +
                                "limitePosti=? WHERE idServizio=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, nome);
        ps.setString(2, descrizione);
        ps.setString(3, foto);
        ps.setDouble(4, costo);
        ps.setInt(5, limitePosti);
        ps.setInt(6, idServizio);
        ps.executeUpdate();
    }

    /**
     * Recupera tutti gli oggetti Servizio presenti nel database.
     * @param con Connessione al database
     * @return I servizi presenti nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public List<Servizio> getServizi(Connection con) throws SQLException {
        ArrayList<Servizio> servizi = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Servizio",
                        Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            servizi.add(createServizio(rs));
        }
        return servizi;
    }

    /**
     * Recupera un servizio dato un id.
     * @param con Connessione al database
     * @param idServizio Identificativo del {@link Servizio}
     * @return Il servizio trovato
     * @throws SQLException Errore nella comunicazione con il database
     */
    public Servizio doSelectById(Connection con, int idServizio) throws SQLException {
        Servizio servizio = null;
        PreparedStatement ps = con.prepareStatement
                ("SELECT servizio.* FROM Servizio WHERE idServizio=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idServizio);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            servizio = createServizio(rs);
        }
        return servizio;
    }

    private Servizio createServizio(ResultSet rs) throws SQLException {
        return new Servizio(rs.getInt(1), rs.getString(2), rs.getString(3),
                rs.getString(4), rs.getDouble(5), rs.getInt(6));
    }

}
