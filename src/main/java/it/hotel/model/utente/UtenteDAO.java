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
        email = "\'" + email + "\'";
        Statement st;
        ResultSet rs;
        try (Connection con = Connect.getConnection()) {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Utente WHERE Email=" + email + ";");
            if (rs.next()) {
                utente = new Utente();
                utente.setIdUtente(rs.getInt(1));
                utente.setRuolo(rs.getInt(2));
                utente.setCf(rs.getString(3));
                utente.setNome(rs.getString(4));
                utente.setCognome(rs.getString(5));
                utente.setEmail(rs.getString(6));
                utente.setDataNascita(rs.getDate(8));
                utente.setTokenAuth(rs.getString(9));
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
