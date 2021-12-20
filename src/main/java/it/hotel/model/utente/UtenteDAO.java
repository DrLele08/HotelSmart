package it.hotel.model.utente;

import it.hotel.Utility.Connect;
import java.sql.*;

public class UtenteDAO {

    public void doInsert(Utente utente, String password) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Utente (ksRuolo, CF, Nome, Cognome, Email, Password," +
                            " DataNascita, TokenAuth) VALUES(?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, utente.getRuolo());
            ps.setString(2, utente.getCf());
            ps.setString(3, utente.getNome());
            ps.setString(4, utente.getCognome());
            ps.setString(5, utente.getEmail());
            ps.setString(6, password);
            ps.setDate(7, utente.getDataNascita());
            ps.setString(8, utente.getTokenAuth());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(e.getMessage() + '\n' + e.getErrorCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Utente doSelectByEmail(String email) {
        Utente utente = null;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM Utente WHERE Email=?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idUtente = rs.getInt(1);
                int ruolo = rs.getInt(2);
                String cf = rs.getString(3);
                String nome = rs.getString(4);
                String cognome = rs.getString(5);
                Date dataNascita = rs.getDate(8);
                String tokenAuth = rs.getString(9);
                utente = new Utente(idUtente, ruolo, cf, nome, cognome, email, dataNascita, tokenAuth);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    /*
        -1: Utente non ha permessi
        Altrimenti
        ris: Ruolo
     */
    public int getRuoloUser(int idUtente,String tokenAuth)
    {
        try(Connection con= Connect.getConnection())
        {
            PreparedStatement pS=con.prepareStatement("SELECT ksRuolo FROM Utente WHERE idUtente=? AND TokenAuth=?");
            pS.setInt(1,idUtente);
            pS.setString(2,tokenAuth);
            ResultSet rS=pS.executeQuery();
            if(rS.next())
            {
                return rS.getInt("ksRuolo");
            }
            else
                return -1;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
//selectbyid
    //selectbyruolo


    public void doUpdate(Utente utente, String password) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement("Update Utente SET ksRuolo=?, CF=?, Nome=?," +
                    " Cognome=?, Email=?, Password=?, DataNascita=?, TokenAuth=? WHERE idUtente=?");
            ps.setInt(1, utente.getRuolo());
            ps.setString(2, utente.getCf());
            ps.setString(3, utente.getNome());
            ps.setString(4, utente.getCognome());
            ps.setString(5, utente.getEmail());
            ps.setString(6, password);
            ps.setDate(7, utente.getDataNascita());
            ps.setString(8, utente.getTokenAuth());
            ps.setInt(9, utente.getIdUtente());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doDelete(String where) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Utente " + where);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
