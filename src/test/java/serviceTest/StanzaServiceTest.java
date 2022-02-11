package serviceTest;

import it.hotel.controller.services.StanzaService;
import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stato.Stato;
import it.hotel.model.stato.StatoDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StanzaServiceTest extends Mockito {

    private StanzaService service;
    private StanzaDAO dao;
    private Connection conn;

    @Before
    public void setUp()
    {
        service=Mockito.spy(new StanzaService());
        dao=Mockito.mock(StanzaDAO.class);
        conn=Mockito.mock(Connection.class);
    }

    @Test
    public void testGetAllStanzeReturnZero() throws Exception
    {
        List<Stanza> emptyList=new ArrayList<>();
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.getStanze(conn)).thenReturn(emptyList);
        List<Stanza> result=service.getStanze();
        Assert.assertEquals(result.size(),0);
    }

    @Test
    public void testGetAllStanzaRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()-> service.getStanze());
    }

    @Test
    public void testGetAllOfferteReturnZero() throws Exception
    {
        List<Stanza> emptyList=new ArrayList<>();
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.getOfferte(conn)).thenReturn(emptyList);
        List<Stanza> result=service.getOfferte();
        Assert.assertEquals(result.size(),0);
    }

    @Test
    public void testGetOfferteRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()-> service.getOfferte());
    }

    @Test
    public void testGetPricesFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doSelect_Min_And_Max_Prices(conn)).thenReturn(new ArrayList<>());
        List<Double> prezzi = service.get_Min_And_Max_Prices();
        Assert.assertNotEquals(prezzi, null);
    }

    @Test
    public void testGetPricesRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()-> service.get_Min_And_Max_Prices());
    }

    @Test
    public void testSearchReturnZero() throws Exception
    {
        List<Stanza> emptyList=new ArrayList<>();
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doSearch(conn,false, false, 1, 10.0, 20.0,
                1.0, 2.0, new Date(0), new Date(0))).thenReturn(emptyList);
        List<Stanza> result=service.search(false, false, 1, 10.0, 20.0,
                1.0, 2.0, new Date(0), new Date(0));
        Assert.assertEquals(result.size(),0);
    }

    @Test
    public void testSearchRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()-> service.search(
                false, false, 1, 10.0, 20.0,
                1.0, 2.0, new Date(0), new Date(0)));
    }

    @Test
    public void testSelectByIdFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doSelectById(conn, 1)).thenReturn(new Stanza(1, true,
                true, 1, 1, 20.0, 1.0));
        Stanza stanza = service.selectById(1);
        Assert.assertNotEquals(stanza, null);
    }

    @Test
    public void testInsertStanzaRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()-> service.insertStanza(
                true, true, 1, 1, 10.0, 2.0));
    }

    @Test
    public void testInsertStanzaFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doNothing().when(dao).doInsert(conn, true, true, 1, 1, 10.0, 2.0);
        service.insertStanza(true, true, 1, 1, 10.0, 2.0);
    }

    @Test
    public void testUpdateStanzaRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()-> service.updateStanza(
                1, true, true, 1, 1, 10.0, 2.0));
    }

    @Test
    public void testUpdateStanzaFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doNothing().when(dao).doUpdate(conn, 1, true, true, 1, 1, 10.0, 2.0);
        service.updateStanza(1,true, true, 1, 1, 10.0, 2.0);
    }

    @Test
    public void testCreateDAO() {
        StanzaDAO dao = service.createDAO();
        Assert.assertNotEquals(dao, null);
    }

}
