package serviceTest;

import it.hotel.controller.services.RuoloService;
import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.ruolo.RuoloDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RuoloServiceTest extends Mockito {

    private RuoloService service;
    private RuoloDAO dao;
    private Connection conn;

    @Before
    public void setUp()
    {
        service=Mockito.spy(new RuoloService());
        dao=Mockito.mock(RuoloDAO.class);
        conn=Mockito.mock(Connection.class);
    }

    @Test
    public void testGetAllReturnZero() throws Exception
    {
        List<Ruolo> emptyList=new ArrayList<>();
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doGetAll(conn)).thenReturn(emptyList);
        List<Ruolo> result=service.getAll();
        Assert.assertEquals(result.size(),0);
    }

    @Test
    public void testGetAllRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.getAll());
    }

    @Test
    public void testGetByIdRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.getById(0));
    }

    @Test
    public void testGetByIdFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doSelectById(conn, 1)).thenReturn(new Ruolo(1, ""));
        String ruolo = service.getById(1);
        Assert.assertEquals(ruolo, "");
    }

    @Test
    public void testGetByRuoloRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.getByRuolo(""));
    }

    @Test
    public void testGetByRuoloFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doSelectByRuolo(conn, "")).thenReturn(new Ruolo(1, ""));
        int ruolo = service.getByRuolo("");
        Assert.assertEquals(ruolo, 1);
    }

    @Test
    public void testCreateDAO() {
        RuoloDAO dao = service.createDAO();
        Assert.assertNotEquals(dao, null);
    }

}
