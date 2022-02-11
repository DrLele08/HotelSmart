package serviceTest;

import it.hotel.controller.services.PrenotazioneServizioService;
import it.hotel.model.prenotazioneServizio.PrenotazioneServizioDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class PrenotazioneServizioServiceTest extends Mockito {

    private PrenotazioneServizioService service;
    private PrenotazioneServizioDAO dao;
    private Connection conn;

    @Before
    public void setUp()
    {
        service= Mockito.spy(new PrenotazioneServizioService());
        dao=Mockito.mock(PrenotazioneServizioDAO.class);
        conn=Mockito.mock(Connection.class);
    }

    @Test
    public void testCreatePrenotazioneRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class,()->
                service.createPrenotazione(1,1,1,new Date(0)));
    }

    @Test
    public void testCreatePrenotazioneFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(null).when(dao).doInsert(conn, 1,1,1,null);
        service.createPrenotazione(1,1,1,null);
    }

    @Test
    public void testDeletePrenotazioneByIdRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class,()->
                service.deletePrenotazioneById(1));
    }

    @Test
    public void testDeletePrenotazioneByIdFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doNothing().when(dao).doDelete(conn, 1);
        service.deletePrenotazioneById(1);
    }

    @Test
    public void testGetAllByUserRuntimeException() throws Exception {
        doReturn(dao).when(service).createDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class,()->
                service.getAllByUser(1));
    }

    @Test
    public void testGetAllByUserFine() throws Exception {
        doReturn(dao).when(service).createDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(null).when(dao).doSelectByUser(conn, 1);
        service.getAllByUser(1);
    }

    @Test
    public void testCreateDAO() {
        PrenotazioneServizioDAO dao = service.createDAO();
        Assert.assertNotEquals(dao, null);
    }

}
