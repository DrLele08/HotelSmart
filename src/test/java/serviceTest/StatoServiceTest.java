package serviceTest;

import it.hotel.controller.services.StatoService;
import it.hotel.model.stato.Stato;
import it.hotel.model.stato.StatoDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatoServiceTest extends Mockito
{

    private StatoService service;
    private StatoDAO dao;
    private Connection conn;

    @Before
    public void setUp()
    {
        service=Mockito.spy(new StatoService());
        dao=Mockito.mock(StatoDAO.class);
        conn=Mockito.mock(Connection.class);
    }

    @Test
    public void testGetAllReturnZero() throws Exception
    {
        List<Stato> emptyList=new ArrayList<>();
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.doGetAll(conn)).thenReturn(emptyList);
        List<Stato> result=service.getAll();
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
        when(dao.doSelectById(conn, 1)).thenReturn(new Stato(1, ""));
        String stato = service.getById(1);
        Assert.assertEquals(stato, "");
    }

    @Test
    public void testCreateDAO() {
        StatoDAO dao = service.createDAO();
        Assert.assertNotEquals(dao, null);
    }

}
