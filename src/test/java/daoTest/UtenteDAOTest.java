package daoTest;

import it.hotel.model.utente.UtenteDAO;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;

public class UtenteDAOTest extends Mockito {

    private UtenteDAO dao;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Before
    public void setUp() {
        dao = Mockito.spy(new UtenteDAO());
        conn=Mockito.mock(Connection.class);
        ps=Mockito.mock(PreparedStatement.class);
        rs=Mockito.mock(ResultSet.class);
    }

    @Test
    public void testDoInsertFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("INSERT INTO Utente (ksRuolo, cf, nome, cognome, email, password," +
                        " dataNascita, tokenAuth) VALUES(?,?,?,?,?,MD5(?),?,?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setString(2,null);
        doNothing().when(ps).setString(3,null);
        doNothing().when(ps).setString(4, null);
        doNothing().when(ps).setString(5,null);
        doNothing().when(ps).setString(6, null);
        doNothing().when(ps).setDate(7, new Date(0));
        doNothing().when(ps).setString(8, null);
        doReturn(0).when(ps).executeUpdate();
        doReturn(rs).when(ps).getGeneratedKeys();
        when(rs.next()).thenReturn(true);
        doReturn(1).when(rs).getInt(1);
        dao.doInsert(conn, 1, null, null, null, null, new Date(0), null, null);
    }

    @Test
    public void testDoInsertNull() throws Exception {
        doReturn(ps).when(conn).prepareStatement("INSERT INTO Utente (ksRuolo, cf, nome, cognome, email, password," +
                        " dataNascita, tokenAuth) VALUES(?,?,?,?,?,MD5(?),?,?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setString(2,null);
        doNothing().when(ps).setString(3,null);
        doNothing().when(ps).setString(4, null);
        doNothing().when(ps).setString(5,null);
        doNothing().when(ps).setString(6, null);
        doNothing().when(ps).setDate(7, new Date(0));
        doNothing().when(ps).setString(8, null);
        doReturn(0).when(ps).executeUpdate();
        doReturn(rs).when(ps).getGeneratedKeys();
        when(rs.next()).thenReturn(false);
        doReturn(1).when(rs).getInt(1);
        dao.doInsert(conn, 1, null, null, null, null, new Date(0), null, null);
    }

    @Test
    public void testDoAuthenticateEmailFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Utente WHERE email=? AND password=MD5(?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1,null);
        doNothing().when(ps).setString(2,null);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true);
        doReturn(1).when(rs).getInt(1);
        dao.doAuthenticate(conn, null, null);
    }

    @Test
    public void testDoAuthenticateEmailPasswordNotValidException() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Utente WHERE email=? AND password=MD5(?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1,null);
        doNothing().when(ps).setString(2,null);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(false);
        doReturn(1).when(rs).getInt(1);
        Assert.assertThrows(PasswordNotValidException.class, ()->
                dao.doAuthenticate(conn, null, null));
    }

    @Test
    public void testDoAuthenticateIdFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Utente WHERE idUtente=? AND tokenAuth=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setString(2,null);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true);
        doReturn(1).when(rs).getInt(1);
        dao.doAuthenticate(conn, 1, null);
    }

    @Test
    public void testDoAuthenticateIdUtenteNotFoundException() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Utente WHERE idUtente=? AND tokenAuth=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setString(2,null);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(false);
        doReturn(1).when(rs).getInt(1);
        Assert.assertThrows(UtenteNotFoundException.class, ()->
                dao.doAuthenticate(conn, 1, null));
    }

    @Test
    public void testDoChangePasswordFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE Utente SET password=MD5(?) WHERE idUtente=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1,null);
        doNothing().when(ps).setInt(2,1);
        doReturn(1).when(ps).executeUpdate();
        dao.doChangePassword(conn, 1, null);
    }

    @Test
    public void testIsPasswordValidFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Utente WHERE idUtente=? AND password=MD5(?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setString(2,null);
        doReturn(rs).when(ps).executeQuery();
        doReturn(true).when(rs).next();
        dao.isPasswordValid(conn, 1, null);
    }

    @Test
    public void testDoGetRuoloFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Utente WHERE idUtente=? AND tokenAuth=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setString(2,null);
        doReturn(rs).when(ps).executeQuery();
        doReturn(true).when(rs).next();
        doReturn(1).when(rs).getInt("ksRuolo");
        dao.doGetRuolo(conn, 1, null);
    }

    @Test
    public void testDoGetRuoloNull() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Utente WHERE idUtente=? AND tokenAuth=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setString(2,null);
        doReturn(rs).when(ps).executeQuery();
        doReturn(false).when(rs).next();
        doReturn(1).when(rs).getInt("ksRuolo");
        dao.doGetRuolo(conn, 1, null);
    }

    @Test
    public void testDoChangeRuoloFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE Utente SET ksRuolo=? WHERE idUtente=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doReturn(1).when(ps).executeUpdate();
        dao.doChangeRuolo(conn,1,1);
    }

    @Test
    public void testDoChangeAnagraficaFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE Utente " +
                        "SET nome=?, tokenAuth=?, cognome=?, cf=?, dataNascita=?, email=? WHERE idUtente=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1, null);
        doNothing().when(ps).setString(2, null);
        doNothing().when(ps).setString(3, null);
        doNothing().when(ps).setString(4, null);
        doNothing().when(ps).setDate(5,new Date(0));
        doNothing().when(ps).setString(6, null);
        doNothing().when(ps).setInt(7,1);
        doReturn(1).when(ps).executeUpdate();
        dao.doChangeAnagrafica(conn,1,null,null,null,null,new Date(0),null);
    }

    @Test
    public void testDoSelectByPrenotazioneStanzaFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT Utente.* " +
                        "FROM Utente, PrenotazioneStanza WHERE idPrenotazioneStanza=? AND utente.idUtente=prenotazionestanza.ksUtente",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        doReturn(true).when(rs).next();
        dao.doSelectByPrenotazioneStanza(conn, 1);
    }

    @Test
    public void testDoSelectByPrenotazioneStanzaUtenteNotFoundException() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT Utente.* " +
                        "FROM Utente, PrenotazioneStanza WHERE idPrenotazioneStanza=? AND utente.idUtente=prenotazionestanza.ksUtente",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        doReturn(false).when(rs).next();
        Assert.assertThrows(UtenteNotFoundException.class,()->
                dao.doSelectByPrenotazioneStanza(conn, 1));
    }

    @Test
    public void testGetUtentiFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Utente",
                Statement.RETURN_GENERATED_KEYS);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        dao.getUtenti(conn);
    }

    @Test
    public void testIsEmailInDatabaseFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Utente WHERE email=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1,null);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true);
        dao.isEmailInDatabase(conn, null);
    }

    @Test
    public void testIsEmailOldFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Utente WHERE idUtente=? AND email=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setString(2,null);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true);
        dao.isEmailOld(conn,1,null);
    }

}
