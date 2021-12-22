package it.hotel.model.utente;

import it.hotel.Utility.Connect;
import it.hotel.model.utente.utenteExceptions.*;

import java.sql.*;

public class UtenteDAO {

    public Utente doInsert(int ruolo, String cf, String nome, String cognome,
                           String email, Date dataNascita, String tokenAuth, String password)
            throws EmailAlreadyExistingException {
        try (Connection con = Connect.getConnection()) {

            //controllo che l'email non sia già presente;
            if (isEmailInDatabase(con, email)) {
                throw new EmailAlreadyExistingException();
            }

            //inserisco l'utente;
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Utente (ksRuolo, cf, nome, cognome, email, password," +
                            " dataNascita, tokenAuth) VALUES(?,?,?,?,?,MD5(?),?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ruolo);
            ps.setString(2, cf);
            ps.setString(3, nome);
            ps.setString(4, cognome);
            ps.setString(5, email);
            ps.setString(6, password);
            ps.setDate(7, dataNascita);
            ps.setString(8, tokenAuth);
            ResultSet rs = ps.executeQuery();

            return new Utente(rs.getInt(1), ruolo, cf, nome, cognome, email, dataNascita, tokenAuth);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Utente doAuthenticate(String email, String password)
            throws EmailNotFoundException, PasswordNotValidException {
        try (Connection con = Connect.getConnection()) {

            //verifico che esista l'email;
            if (!isEmailInDatabase(con, email)) {
                throw new EmailNotFoundException();
            }

            //verifico la password;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Utente WHERE email=? AND password=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getUtente(rs);
            } else {
                throw new PasswordNotValidException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Utente doAuthenticate(int idUtente, String tokenAuth)
            throws UtenteNotFoundException {
        Utente utente;
        try (Connection con = Connect.getConnection())
        {

            //verifico la password;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Utente WHERE idUtente=? AND tokenAuth=?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ps.setString(2, tokenAuth);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getUtente(rs);
            } else {
                throw new UtenteNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doChangePassword(int idUtente, String oldPassword, String newPassword)
            throws PasswordNotValidException
    {
        try (Connection con = Connect.getConnection())
        {

            //verifico la oldPassword;
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Utente WHERE idUtente=? AND password=MD5(?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ps.setString(2, oldPassword);
            ResultSet rs = ps.executeQuery();
            if (!rs.next())
            {
                throw new PasswordNotValidException();
            }

            //aggiorno con la newPassword;
            ps = con.prepareStatement
                    ("UPDATE Utente SET password=MD5(?) WHERE idUtente=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newPassword);
            ps.setInt(2, idUtente);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doDelete(int idUtente)  {
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

    public int doGetRuolo(int idUtente, String tokenAuth)
    {
        try (Connection con = Connect.getConnection())
        {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Utente WHERE idUtente=? AND tokenAuth=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ps.setString(2, tokenAuth);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("ksRuolo");
            else
                return -1;
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void doChangeRuolo(int idUtente, int ksRuolo) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("UPDATE Utente SET ksRuolo=? WHERE idUtente=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ksRuolo);
            ps.setInt(2, idUtente);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isEmailInDatabase(Connection con, String email) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Utente WHERE email=?",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private Utente getUtente(ResultSet rs) throws SQLException {
        int idUtente = rs.getInt("idUtente");
        int ksRuolo = rs.getInt("ksRuolo");
        String cf = rs.getString("cf");
        String nome = rs.getString("nome");
        String cognome = rs.getString("cognome");
        String email = rs.getString("email");
        Date dataNascita = rs.getDate("dataNascita");
        String tokenAuth = rs.getString("tokenAuth");
        return new Utente(idUtente, ksRuolo, cf, nome, cognome, email, dataNascita, tokenAuth);
    }

}
