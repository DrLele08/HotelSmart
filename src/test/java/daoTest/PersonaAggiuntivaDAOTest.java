package daoTest;

import it.hotel.model.personaAggiuntiva.PersonaAggiuntiva;
import it.hotel.model.personaAggiuntiva.PersonaAggiuntivaDAO;
import it.hotel.model.ruolo.RuoloDAO;
import it.hotel.model.stato.StatoDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;

public class PersonaAggiuntivaDAOTest extends Mockito
{
    private PersonaAggiuntivaDAO dao;
    private Connection conn;
    private PreparedStatement pS;
    private ResultSet rS;

    @Before
    public void setUp()
    {
        dao=spy(new PersonaAggiuntivaDAO());
        conn=mock(Connection.class);
        pS=mock(PreparedStatement.class);
        rS=mock(ResultSet.class);
    }

    @Test
    public void testErrore() throws Exception
    {
        doReturn(pS).when(conn).prepareStatement("INSERT INTO PersonaAggiuntiva (ksUtente, cf, nome, cognome, dataNascita) " +
                        "VALUES(?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        when(pS.getGeneratedKeys()).thenReturn(rS);
        when(rS.next()).thenReturn(false);
        Assert.assertNull(dao.doInsert(conn,1,"CF","Nome","Cognome",new Date(0)));
    }

    @Test
    public void testOk() throws Exception
    {
        PersonaAggiuntiva persone=new PersonaAggiuntiva(0,1,"CF","Nome","Cognome",new Date(0));
        doReturn(pS).when(conn).prepareStatement("INSERT INTO PersonaAggiuntiva (ksUtente, cf, nome, cognome, dataNascita) " +
                        "VALUES(?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        when(pS.getGeneratedKeys()).thenReturn(rS);
        when(rS.next()).thenReturn(true);
        Assert.assertEquals(persone.getIdPersona(),dao.doInsert(conn,1,"CF","Nome","Cognome",new Date(0)).getIdPersona());
    }
}
