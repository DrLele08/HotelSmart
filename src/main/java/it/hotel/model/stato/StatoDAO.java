package it.hotel.model.stato;

import it.hotel.model.stato.statoExceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fornisce l'accesso al database per {@link Stato}.
 */
public class StatoDAO {

    /**
     * Inserisce nel database l'oggetto Stato specificato.
     * @param con Connessione al database
     * @param stato Stato da inserire nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doInsert(Connection con, Stato stato) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("INSERT INTO Stato (stato) VALUES(?)",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, stato.getStato());
        ps.executeUpdate();
    }

    /**
     * Recupera l'oggetto Stato trovato nel database secondo la stringa specificata.
     * @param con Connessione al database
     * @param statoStr Stringa che identifica lo stato cercato
     * @return Lo stato trovato nel database
     * @throws StatoNotFoundException Lo stato specificato non è presente nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public Stato doSelectByStato(Connection con, String statoStr) throws StatoNotFoundException, SQLException {
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Stato WHERE stato=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, statoStr);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return createStato(rs);
        } else {
            throw new StatoNotFoundException();
        }
    }


    /**
     * Recupera l'oggetto Stato trovato nel database secondo l'id specificato.
     * @param con Connessione al database
     * @param idStato Id che identifica lo stato cercato
     * @return Lo stato trovato nel database
     * @throws StatoNotFoundException Lo stato specificato non è presente nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public Stato doSelectById(Connection con, int idStato) throws StatoNotFoundException, SQLException {
        Stato stato;
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Stato WHERE idStato=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idStato);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            stato = createStato(rs);
        } else {
            throw new StatoNotFoundException();
        }
        return stato;
    }

    /**
     * Recupera tutti gli oggetti Stato trovati nel database.
     * @param con Connessione al database
     * @return Gli stati trovati nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public List<Stato> doGetAll(Connection con) throws SQLException
    {
        ArrayList<Stato> ruoli = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Stato");
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            ruoli.add(createStato(rs));
        }
        return ruoli;
    }

    private Stato createStato (ResultSet rs) throws SQLException {
        return new Stato(rs.getInt(1), rs.getString(2));
    }

}
