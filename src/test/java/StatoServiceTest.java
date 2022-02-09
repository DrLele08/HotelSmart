import it.hotel.Utility.Connect;
import it.hotel.controller.services.StatoService;
import it.hotel.model.stato.Stato;
import it.hotel.model.stato.StatoDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
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
        List<Stato> aspet=service.getAll();
        Assert.assertEquals(aspet.size(),emptyList.size());
    }
}
