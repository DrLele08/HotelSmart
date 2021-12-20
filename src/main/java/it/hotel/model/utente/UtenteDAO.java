package it.hotel.model.utente;

import it.hotel.Utility.Connect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    public void doInsertUtente(Utente utente, String password) {
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

            ResultSet rs = ps.executeQuery();
            rs.next();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(e.getMessage() + '\n' + e.getErrorCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Utente doSelectUtenteByEmail(String email) {
        Utente utente = null;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM Utente WHERE Email=?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
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

    public Utente doSelectUtenteById(int id) {
        Utente utente = null;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM Utente WHERE idUtente=?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idUtente = rs.getInt(1);
                int ruolo = rs.getInt(2);
                String cf = rs.getString(3);
                String nome = rs.getString(4);
                String cognome = rs.getString(5);
                String email = rs.getString(6);
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

    public List<Utente> doSelectUtentiByRuolo(int ruolo) {
        ArrayList<Utente> utenti = new ArrayList<>();
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM Utente WHERE ksRuolo=?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ruolo);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idUtente = rs.getInt(1);
                String cf = rs.getString(3);
                String nome = rs.getString(4);
                String cognome = rs.getString(5);
                String email = rs.getString(6);
                Date dataNascita = rs.getDate(8);
                String tokenAuth = rs.getString(9);
                utenti.add(new Utente(idUtente, ruolo, cf, nome, cognome, email, dataNascita, tokenAuth));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utenti;
    }

    public void doDeleteUtenteByEmail(String email) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("DELETE FROM Utente WHERE Email=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void changePassword(String email, String oldPassword, String newPassword) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("Update Utente SET Password=? WHERE Email=? AND Password=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newPassword);
            ps.setString(2, email);
            ps.setString(3, oldPassword);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Utente authenticate(String email, String password) {
        Utente utente = null;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM Utente WHERE Email=?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
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
        try(Connection con = Connect.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT ksRuolo FROM Utente " +
                    "WHERE idUtente=? AND TokenAuth=?");
            ps.setInt(1,idUtente);
            ps.setString(2,tokenAuth);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getInt("ksRuolo");
            }
            else
                return -1;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }


}
