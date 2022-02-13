package daoTest;

import it.hotel.model.servizio.ServizioDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;

public class ServizioDAOTest extends Mockito {

    private ServizioDAO dao;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Before
    public void setUp() {
        dao = Mockito.spy(new ServizioDAO());
        conn=Mockito.mock(Connection.class);
        ps=Mockito.mock(PreparedStatement.class);
        rs=Mockito.mock(ResultSet.class);
    }

    @Test
    public void testDoInsertFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("INSERT INTO " +
                        "Servizio (nome, descrizione, foto, costo, limitePosti) VALUES(?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1, "nome");
        doNothing().when(ps).setString(2, "descrizione");
        doNothing().when(ps).setString(3, "foto");
        doNothing().when(ps).setDouble(4, 1.0);
        doNothing().when(ps).setInt(5, 1);
        doReturn(1).when(ps).executeUpdate();
        dao.doInsert(conn, "nome", "descrizione", "foto", 1.0, 1);
        Mockito.verify(dao, times(1)).doInsert(conn, "nome", "descrizione", "foto", 1.0, 1);
    }

    @Test
    public void testDoUpdateFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE Servizio SET nome=?, descrizione=?, foto=?, costo=?, " +
                        "limitePosti=? WHERE idServizio=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setString(1, "nome");
        doNothing().when(ps).setString(2, "descrizione");
        doNothing().when(ps).setString(3, "foto");
        doNothing().when(ps).setDouble(4, 1.0);
        doNothing().when(ps).setInt(5, 1);
        doNothing().when(ps).setInt(6,1);
        doReturn(1).when(ps).executeUpdate();
        dao.doUpdate(conn, 1,"nome", "descrizione", "foto", 1.0, 1);
        Mockito.verify(dao, times(1)).doUpdate(conn, 1,"nome", "descrizione", "foto", 1.0, 1);
    }

    @Test
    public void testGetServiziFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Servizio",
                Statement.RETURN_GENERATED_KEYS);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        Assert.assertNotNull(dao.getServizi(conn));
    }

    @Test
    public void testDoSelectByIdFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT servizio.* FROM Servizio WHERE idServizio=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        Assert.assertNotNull(dao.doSelectById(conn, 1));
    }

}
