package it.hotel.model.stato;

import it.hotel.Utility.Connect;
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
     * @param stato Stato da inserire nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public void doInsert(Stato stato) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Stato (stato) VALUES(?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, stato.getStato());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recupera l'oggetto Stato trovato nel database secondo la stringa specificata.
     * @param statoStr Stringa che identifica lo stato cercato
     * @return Lo stato trovato nel database
     * @throws StatoNotFoundException Lo stato specificato non è presente nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public Stato doSelectByStato(String statoStr) throws StatoNotFoundException {
        Stato stato;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Stato WHERE stato=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, statoStr);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stato = createStato(rs);
            } else {
                throw new StatoNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stato;
    }

    /**
     * Recupera l'oggetto Stato trovato nel database secondo l'id specificato.
     * @param idStato Id che identifica lo stato cercato
     * @return Lo stato trovato nel database
     * @throws StatoNotFoundException Lo stato specificato non è presente nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public Stato doSelectById(int idStato) throws StatoNotFoundException {
        Stato stato;
        try (Connection con = Connect.getConnection()) {
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
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stato;
    }

    /**
     * Recupera tutti gli oggetti Stato trovati nel database.
     * @return Gli stati trovati nel database
     * @throws RuntimeException Errore nella comunicazione con il database
     */
    public List<Stato> doGetAll()
    {
        ArrayList<Stato> ruoli = new ArrayList<>();
        try (Connection con = Connect.getConnection())
        {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Stato");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                ruoli.add(createStato(rs));
            }
            return ruoli;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Stato createStato (ResultSet rs) throws SQLException {
        return new Stato(rs.getInt(1), rs.getString(2));
    }

}
