package it.hotel.model.utente;

import it.hotel.Utility.Connect;
import it.hotel.model.utente.UtenteExceptions.*;

import java.sql.*;

public class UtenteDAO {

    public void registrazione(Utente utente, String password) throws EmailAlreadyExistingException {
        try (Connection con = Connect.getConnection()) {

            //controllo che l'email non sia già presente;
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Utente WHERE Email=?",
                    Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                throw new EmailAlreadyExistingException();
            }

            //inserisco l'utente;
            ps = con.prepareStatement
                    ("INSERT INTO Utente (ksRuolo, CF, Nome, Cognome, Email, Password," +
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

            rs = ps.executeQuery();
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Utente login(String email, String password)
            throws EmailNotFoundException, PasswordNotValidException {
        Utente utente;
        try (Connection con = Connect.getConnection()) {

            //verifico che esista l'email;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Utente WHERE Email=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new EmailNotFoundException();
            }

            //verifico la password;
            ps = con.prepareStatement("SELECT * FROM Utente WHERE Email=? AND Password=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                int idUtente = rs.getInt(1);
                int ruolo = rs.getInt(2);
                String cf = rs.getString(3);
                String nome = rs.getString(4);
                String cognome = rs.getString(5);
                Date dataNascita = rs.getDate(8);
                String tokenAuth = rs.getString(9);
                utente = new Utente(idUtente, ruolo, cf, nome, cognome, email, dataNascita, tokenAuth);
            } else {
                throw new PasswordNotValidException();
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    public void changePassword(int idUtente, String oldPassword, String newPassword)
            throws PasswordNotValidException {
        try (Connection con = Connect.getConnection()) {

            //verifico la oldPassword;
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Utente WHERE idUtente=? AND Password=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ps.setString(2, oldPassword);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new PasswordNotValidException();
            }

            //aggiorno con la newPassword;
            ps = con.prepareStatement
                    ("Update Utente SET Password=? WHERE idUtente=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newPassword);
            ps.setInt(2, idUtente);
            rs = ps.executeQuery();
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(int idUtente)  {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("DELETE FROM Utente WHERE idUtente=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getRuolo(int idUtente) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Utente WHERE idUtente=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();
            return rs.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
