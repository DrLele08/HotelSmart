package daoTest;

import it.hotel.model.personaPrenotazione.PersonaPrenotazioneDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;

public class PersonaPrenotazioneDAOTest extends Mockito {

    private PersonaPrenotazioneDAO dao;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Before
    public void setUp() {
        dao = Mockito.spy(new PersonaPrenotazioneDAO());
        conn=Mockito.mock(Connection.class);
        ps=Mockito.mock(PreparedStatement.class);
        rs=Mockito.mock(ResultSet.class);
    }

    @Test
    public void testDoInsertFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("INSERT INTO PersonePrenotazione " +
                        "(ksPersona, ksPrenotazioneStanza) VALUES(?,?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setInt(2,1);
        doReturn(0).when(ps).executeUpdate();
        dao.doInsert(conn, 1,1);
        Mockito.verify(dao,times(1)).doInsert(conn, 1,1);
    }

}
