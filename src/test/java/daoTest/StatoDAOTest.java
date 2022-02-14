package daoTest;

import it.hotel.model.stato.Stato;
import it.hotel.model.stato.StatoDAO;
import it.hotel.model.stato.statoExceptions.StatoNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sais Raffaele
 */
public class StatoDAOTest extends Mockito
{
    private StatoDAO dao;
    private Connection conn;
    private PreparedStatement pS;
    private ResultSet rS;
    private Stato stato;

    @Before
    public void setUp()
    {
        dao=spy(new StatoDAO());
        conn=mock(Connection.class);
        pS=mock(PreparedStatement.class);
        rS=mock(ResultSet.class);
        stato=mock(Stato.class);
    }

    @Test
    public void testSelectStatoFail() throws Exception
    {
        Stato test=new Stato(1,"Test");
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Stato WHERE stato=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(pS).setString(1, null);
        when(pS.executeQuery()).thenReturn(rS);
        when(rS.next()).thenReturn(false);
        Assert.assertThrows(StatoNotFoundException.class,()->dao.doSelectByStato(conn,test.getStato()));
    }

    @Test
    public void testSelectStatoOk() throws Exception
    {
        Stato test=new Stato(1,"Test");
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Stato WHERE stato=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(pS).setString(1, null);
        when(pS.executeQuery()).thenReturn(rS);
        when(rS.next()).thenReturn(true);
        Assert.assertEquals(stato.getStato(),dao.doSelectByStato(conn,test.getStato()).getStato());
    }

    @Test
    public void testSelectIdFail() throws Exception
    {
        Stato test=new Stato(1,"Test");
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Stato WHERE idStato=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(pS).setInt(1,1);
        when(pS.executeQuery()).thenReturn(rS);
        when(rS.next()).thenReturn(false);
        Assert.assertThrows(StatoNotFoundException.class,()->dao.doSelectById(conn,test.getIdStato()));
    }

    @Test
    public void testSelectIdOk() throws Exception
    {
        Stato test=new Stato(1,"Test");
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Stato WHERE idStato=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(pS).setInt(1,1);
        when(pS.executeQuery()).thenReturn(rS);
        when(rS.next()).thenReturn(true);
        doReturn(test).when(dao).createStato(rS);
        Assert.assertEquals(test.getStato(),dao.doSelectById(conn,test.getIdStato()).getStato());
    }

    @Test
    public void testAllVuoto() throws Exception
    {
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Stato");
        when(pS.executeQuery()).thenReturn(rS);
        Assert.assertEquals(0,dao.doGetAll(conn).size());
    }

    @Test
    public void testAllOk() throws Exception
    {
        List<Stato> list=new ArrayList<>();
        list.add(mock(Stato.class));
        list.add(mock(Stato.class));
        list.add(mock(Stato.class));
        list.add(mock(Stato.class));
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Stato");
        when(pS.executeQuery()).thenReturn(rS);
        doReturn(true).doReturn(true).doReturn(true).doReturn(true).doReturn(false).when(rS).next();
        Assert.assertEquals(list.size(),dao.doGetAll(conn).size());
    }
}
