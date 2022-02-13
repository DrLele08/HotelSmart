package daoTest;

import it.hotel.model.prenotazioneServizio.PrenotazioneServizioDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;

public class PrenotazioneServizioDAOTest extends Mockito {

    private PrenotazioneServizioDAO dao;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Before
    public void setUp() {
        dao = Mockito.spy(new PrenotazioneServizioDAO());
        conn=Mockito.mock(Connection.class);
        ps=Mockito.mock(PreparedStatement.class);
        rs=Mockito.mock(ResultSet.class);
    }

    @Test
    public void testDoInsertFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("INSERT INTO PrenotazioneServizio (ksPrenotazioneStanza, ksServizio, numPersone, " +
                        "dataPrenotazioneServizio) VALUES(?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doNothing().when(ps).setInt(3,1);
        doNothing().when(ps).setDate(4, new Date(0));
        doReturn(rs).when(ps).getGeneratedKeys();
        doReturn(true).when(rs).next();
        doReturn(1).when(rs).getInt(1);
        dao.doInsert(conn, 1,1,1,new Date(0));
    }

    @Test
    public void testDoInsertNull() throws Exception {
        doReturn(ps).when(conn).prepareStatement("INSERT INTO PrenotazioneServizio (ksPrenotazioneStanza, ksServizio, numPersone, " +
                        "dataPrenotazioneServizio) VALUES(?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doNothing().when(ps).setInt(3,1);
        doNothing().when(ps).setDate(4, new Date(0));
        doReturn(rs).when(ps).getGeneratedKeys();
        doReturn(false).when(rs).next();
        doReturn(1).when(rs).getInt(1);
        dao.doInsert(conn, 1,1,1,new Date(0));
    }

    @Test
    public void testDoSelectByUserFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT pser.* FROM PrenotazioneServizio as pser, PrenotazioneStanza as psta WHERE " +
                        "pser.ksPrenotazioneStanza = psta.idPrenotazioneStanza " +
                        "AND psta.ksUtente = ?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        doReturn(1).when(rs).getInt(1);
        doReturn(1).when(rs).getInt(2);
        doReturn(1).when(rs).getInt(3);
        doReturn(1).when(rs).getInt(4);
        doReturn(new Date(0)).when(rs).getDate(5);
        dao.doSelectByUser(conn, 1);
    }

    @Test
    public void testDoDeleteFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("DELETE FROM PrenotazioneServizio " +
                        "WHERE idPrenotazioneServizio=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(0).when(ps).executeUpdate();
        dao.doDelete(conn, 1);
    }

}
