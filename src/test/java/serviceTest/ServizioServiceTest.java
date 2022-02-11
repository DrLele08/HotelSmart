package serviceTest;

import it.hotel.controller.services.ServizioService;
import it.hotel.model.servizio.Servizio;
import it.hotel.model.servizio.ServizioDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServizioServiceTest extends Mockito {

    private ServizioService service;
    private ServizioDAO dao;
    private Connection conn;

    @Before
    public void setUp()
    {
        service=Mockito.spy(new ServizioService());
        dao=Mockito.mock(ServizioDAO.class);
        conn=Mockito.mock(Connection.class);
    }

    @Test
    public void testCreateServizioRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.createServizio(
                "nome", "descrizione", "foto", 10.0, 2));
    }

    @Test
    public void testCreateServizioFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doNothing().when(dao).doInsert(conn, "nome", "descrizione", "foto", 10.0, 2);
        service.createServizio(
                "nome", "descrizione", "foto", 10.0, 2);
    }

    @Test
    public void testUpdateServizioRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.updateServizio(
                1, "nome", "descrizione", "foto", 10.0, 2)
        );
    }

    @Test
    public void testUpdateServizioFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doNothing().when(dao).doUpdate(conn,1, "nome", "descrizione", "foto", 10.0, 2);
        service.updateServizio(1, "nome", "descrizione", "foto", 10.0, 2);
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
        when(dao.doSelectById(conn, 1)).thenReturn(new Servizio(
                1, "nome", "descrizione", "foto", 10.0, 2)
        );
        service.getById(1);
    }

    @Test
    public void testGetAllReturnZero() throws Exception
    {
        List<Servizio> emptyList=new ArrayList<>();
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        when(dao.getServizi(conn)).thenReturn(emptyList);
        List<Servizio> result=service.getAll();
        Assert.assertEquals(result.size(),0);
    }

    @Test
    public void testGetAllRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.getAll());
    }

    @Test
    public void testCreateDAO() {
        ServizioDAO dao = service.createDAO();
        Assert.assertNotEquals(dao, null);
    }

}
