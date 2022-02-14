package daoTest;

import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.ruolo.RuoloDAO;
import it.hotel.model.ruolo.ruoloExceptions.RuoloNotFoundException;
import it.hotel.model.stato.Stato;
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

public class RuoloDAOTest extends Mockito
{
    private RuoloDAO dao;
    private Connection conn;
    private PreparedStatement pS;
    private ResultSet rS;

    @Before
    public void setUp()
    {
        dao=spy(new RuoloDAO());
        conn=mock(Connection.class);
        pS=mock(PreparedStatement.class);
        rS=mock(ResultSet.class);
    }

    @Test
    public void testSelectStatoFail() throws Exception
    {
        Ruolo test=new Ruolo(1,"Test");
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Ruolo WHERE ruolo=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(pS).setString(1, null);
        when(pS.executeQuery()).thenReturn(rS);
        when(rS.next()).thenReturn(false);
        Assert.assertThrows(RuoloNotFoundException.class,()->dao.doSelectByRuolo(conn,test.getRuolo()));
    }

    @Test
    public void testSelectStatoOk() throws Exception
    {
        Ruolo test=new Ruolo(1,"Test");
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Ruolo WHERE ruolo=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(pS).setString(1, null);
        when(pS.executeQuery()).thenReturn(rS);
        when(rS.next()).thenReturn(true);
        doReturn(test).when(dao).createRuolo(rS);
        Assert.assertEquals(test.getRuolo(),dao.doSelectByRuolo(conn,test.getRuolo()).getRuolo());
    }

    @Test
    public void testSelectIdFail() throws Exception
    {
        Ruolo test=new Ruolo(1,"Test");
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Ruolo WHERE idRuolo=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(pS).setInt(1,1);
        when(pS.executeQuery()).thenReturn(rS);
        when(rS.next()).thenReturn(false);
        Assert.assertThrows(RuoloNotFoundException.class,()->dao.doSelectById(conn,test.getIdRuolo()));
    }

    @Test
    public void testSelectIdOk() throws Exception
    {
        Ruolo test=new Ruolo(1,"Test");
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Ruolo WHERE idRuolo=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(pS).setInt(1,1);
        when(pS.executeQuery()).thenReturn(rS);
        when(rS.next()).thenReturn(true);
        doReturn(test).when(dao).createRuolo(rS);
        Assert.assertEquals(test.getRuolo(),dao.doSelectById(conn,test.getIdRuolo()).getRuolo());
    }

    @Test
    public void testAllVuoto() throws Exception
    {
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Ruolo",
                Statement.RETURN_GENERATED_KEYS);
        when(pS.executeQuery()).thenReturn(rS);
        Assert.assertEquals(0,dao.doGetAll(conn).size());
    }

    @Test
    public void testAllOk() throws Exception
    {
        List<Ruolo> list=new ArrayList<>();
        list.add(mock(Ruolo.class));
        list.add(mock(Ruolo.class));
        list.add(mock(Ruolo.class));
        list.add(mock(Ruolo.class));
        doReturn(pS).when(conn).prepareStatement("SELECT * FROM Ruolo",
                Statement.RETURN_GENERATED_KEYS);
        when(pS.executeQuery()).thenReturn(rS);
        doReturn(true).doReturn(true).doReturn(true).doReturn(true).doReturn(false).when(rS).next();
        Assert.assertEquals(list.size(),dao.doGetAll(conn).size());
    }
}
