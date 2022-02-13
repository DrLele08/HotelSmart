package daoTest;

import it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaInsertException;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;

public class PrenotazioneStanzaDAOTest extends Mockito {

    private PrenotazioneStanzaDAO dao;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Before
    public void setUp() {
        dao = Mockito.spy(new PrenotazioneStanzaDAO());
        conn=Mockito.mock(Connection.class);
        ps=Mockito.mock(PreparedStatement.class);
        rs=Mockito.mock(ResultSet.class);
    }

    @Test
    public void testDoInsertFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("INSERT INTO PrenotazioneStanza (ksUtente, ksStanza," +
                        " ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr," +
                        " commenti, valutazione) SELECT ?,?,?,?,?,(SELECT DATEDIFF(?,?)*(?-?)),?,?,?,? FROM dual " +
                        "WHERE NOT EXISTS (SELECT * FROM PrenotazioneStanza WHERE ksUtente = ? AND " +
                        "ksStato = ?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doNothing().when(ps).setInt(3,1);
        doNothing().when(ps).setDate(4, new Date(0));
        doNothing().when(ps).setDate(5, new Date(0));
        doNothing().when(ps).setDate(6, new Date(0));
        doNothing().when(ps).setDate(7, new Date(0));
        doNothing().when(ps).setDouble(8, 10.0);
        doNothing().when(ps).setDouble(9, 1.0);
        doNothing().when(ps).setString(10, null);
        doNothing().when(ps).setString(11, null);
        doNothing().when(ps).setString(12, null);
        doNothing().when(ps).setInt(13,-1);
        doNothing().when(ps).setInt(14,1);
        doNothing().when(ps).setInt(15,1);
        doReturn(0).when(ps).executeUpdate();
        doReturn(rs).when(ps).getGeneratedKeys();
        when(rs.next()).thenReturn(true);
        doReturn(1).when(rs).getInt(1);
        dao.doInsert(conn, 1,1,1,new Date(0),new Date(0),10.0,1.0);
    }

    @Test
    public void testDoInsertPrenotazioneStanzaInsertException() throws Exception {
        doReturn(ps).when(conn).prepareStatement("INSERT INTO PrenotazioneStanza (ksUtente, ksStanza," +
                        " ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr," +
                        " commenti, valutazione) SELECT ?,?,?,?,?,(SELECT DATEDIFF(?,?)*(?-?)),?,?,?,? FROM dual " +
                        "WHERE NOT EXISTS (SELECT * FROM PrenotazioneStanza WHERE ksUtente = ? AND " +
                        "ksStato = ?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doNothing().when(ps).setInt(3,1);
        doNothing().when(ps).setDate(4, new Date(0));
        doNothing().when(ps).setDate(5, new Date(0));
        doNothing().when(ps).setDate(6, new Date(0));
        doNothing().when(ps).setDate(7, new Date(0));
        doNothing().when(ps).setDouble(8, 10.0);
        doNothing().when(ps).setDouble(9, 1.0);
        doNothing().when(ps).setString(10, null);
        doNothing().when(ps).setString(11, null);
        doNothing().when(ps).setString(12, null);
        doNothing().when(ps).setInt(13,-1);
        doNothing().when(ps).setInt(14,1);
        doNothing().when(ps).setInt(15,1);
        doReturn(0).when(ps).executeUpdate();
        doReturn(rs).when(ps).getGeneratedKeys();
        when(rs.next()).thenReturn(false);
        doReturn(1).when(rs).getInt(1);
        Assert.assertThrows(PrenotazioneStanzaInsertException.class,()->
                dao.doInsert(conn, 1,1,1,new Date(0),new Date(0),10.0,1.0));
    }

    @Test
    public void testDoInsertTokenQrCodeFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE PrenotazioneStanza " +
                        "SET tokenQr=? WHERE idPrenotazioneStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1, null);
        doNothing().when(ps).setInt(2,1);
        doReturn(1).when(ps).executeUpdate();
        dao.doInsertTokenQrCode(conn, 1, null);
    }

    @Test
    public void testDoInsertTokenQrCodePrenotazioneStanzaNotFoundException() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE PrenotazioneStanza " +
                        "SET tokenQr=? WHERE idPrenotazioneStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1, null);
        doNothing().when(ps).setInt(2,1);
        doReturn(0).when(ps).executeUpdate();
        Assert.assertThrows(PrenotazioneStanzaNotFoundException.class, ()->
                dao.doInsertTokenQrCode(conn, 1, null));
    }

    @Test
    public void testDoSelectByIdFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM PrenotazioneStanza " +
                        "WHERE idPrenotazioneStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true);
        doReturn(1).when(rs).getInt(1);
        doReturn(1).when(rs).getInt(2);
        doReturn(1).when(rs).getInt(3);
        doReturn(1).when(rs).getInt(4);
        doReturn(new Date(0)).when(rs).getDate(5);
        doReturn(new Date(0)).when(rs).getDate(6);
        doReturn(10.0).when(rs).getDouble(7);
        doReturn(null).when(rs).getString(8);
        doReturn(null).when(rs).getString(9);
        doReturn(null).when(rs).getString(10);
        doReturn(1).when(rs).getInt(11);
        dao.doSelectById(conn, 1);
    }

    @Test
    public void testDoSelectByIdPrenotazioneStanzaNotFoundException() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM PrenotazioneStanza " +
                        "WHERE idPrenotazioneStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(false);
        Assert.assertThrows(PrenotazioneStanzaNotFoundException.class, ()->
                dao.doSelectById(conn, 1));
    }

    @Test
    public void testDoInsertTokenStripeFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE PrenotazioneStanza " +
                        "SET tokenStripe=? WHERE idPrenotazioneStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1, null);
        doNothing().when(ps).setInt(2,1);
        doReturn(1).when(ps).executeUpdate();
        dao.insertTokenStripe(conn, 1, null);
    }

    @Test
    public void testDoInsertTokenStripePrenotazioneStanzaNotFoundException() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE PrenotazioneStanza " +
                        "SET tokenStripe=? WHERE idPrenotazioneStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1, null);
        doNothing().when(ps).setInt(2,1);
        doReturn(0).when(ps).executeUpdate();
        Assert.assertThrows(PrenotazioneStanzaNotFoundException.class, ()->
                dao.insertTokenStripe(conn, 1, null));
    }

    @Test
    public void testDoGetAllFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Prenotazionestanza",
                Statement.RETURN_GENERATED_KEYS);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        dao.doGetAll(conn);
    }

    @Test
    public void testDoChangeStatoFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE PrenotazioneStanza " +
                        "SET ksStato=? WHERE idPrenotazioneStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doReturn(1).when(ps).executeUpdate();
        dao.doChangeStato(conn,1,1);
    }

    @Test
    public void testDoChangeStatoPrenotazioneNotFoundException() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE PrenotazioneStanza " +
                        "SET ksStato=? WHERE idPrenotazioneStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doReturn(0).when(ps).executeUpdate();
        Assert.assertThrows(PrenotazioneStanzaNotFoundException.class,()->
                dao.doChangeStato(conn,1,1));
    }

    @Test
    public void testIsRimborsabileTrue() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT count(*) FROM Prenotazionestanza " +
                        "WHERE idPrenotazioneStanza=? AND ksStato=? " +
                        "AND dataInizio >= (CURDATE() + INTERVAL 14 DAY)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doReturn(rs).when(ps).executeQuery();
        doReturn(true).when(rs).next();
        doReturn(1).when(rs).getInt(1);
        Assert.assertEquals(dao.isRimborsabile(conn, 1,1), true);
    }

    @Test
    public void testIsRimborsabileFalse() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT count(*) FROM Prenotazionestanza " +
                        "WHERE idPrenotazioneStanza=? AND ksStato=? " +
                        "AND dataInizio >= (CURDATE() + INTERVAL 14 DAY)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doReturn(rs).when(ps).executeQuery();
        doReturn(false).when(rs).next();
        doReturn(1).when(rs).getInt(1);
        Assert.assertEquals(dao.isRimborsabile(conn, 1,1), false);
    }

    @Test
    public void testIsRimborsabileFalse2() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT count(*) FROM Prenotazionestanza " +
                        "WHERE idPrenotazioneStanza=? AND ksStato=? " +
                        "AND dataInizio >= (CURDATE() + INTERVAL 14 DAY)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doReturn(rs).when(ps).executeQuery();
        doReturn(false).when(rs).next();
        doReturn(0).when(rs).getInt(1);
        Assert.assertEquals(dao.isRimborsabile(conn, 1,1), false);
    }

    @Test
    public void testIsRimborsabileFalse3() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT count(*) FROM Prenotazionestanza " +
                        "WHERE idPrenotazioneStanza=? AND ksStato=? " +
                        "AND dataInizio >= (CURDATE() + INTERVAL 14 DAY)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doReturn(rs).when(ps).executeQuery();
        doReturn(true).when(rs).next();
        doReturn(0).when(rs).getInt(1);
        Assert.assertEquals(dao.isRimborsabile(conn, 1,1), false);
    }

    @Test
    public void testDoSelectByUtenteFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM PrenotazioneStanza WHERE ksUtente=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        dao.doSelectBy(conn,1,0);
    }

    @Test
    public void testDoSelectByStanzaFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM PrenotazioneStanza WHERE ksStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        dao.doSelectBy(conn,1,1);
    }

    @Test
    public void testDoSelectByStatoFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM PrenotazioneStanza WHERE ksStato=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        dao.doSelectBy(conn,1,2);
    }

    @Test
    public void testDoSelectByDefault() throws Exception {
        dao.doSelectBy(conn,1,3);
    }

}
