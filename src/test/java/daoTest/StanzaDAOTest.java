package daoTest;

import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;

public class StanzaDAOTest extends Mockito {

    private StanzaDAO dao;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Before
    public void setUp() {
        dao = Mockito.spy(new StanzaDAO());
        conn=Mockito.mock(Connection.class);
        ps=Mockito.mock(PreparedStatement.class);
        rs=Mockito.mock(ResultSet.class);
    }

    @Test
    public void testDoInsertFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("INSERT INTO Stanza (animaleDomestico, fumatore, lettiSingoli," +
                        " lettiMatrimoniali, costoNotte, sconto) VALUES(?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setBoolean(1,true);
        doNothing().when(ps).setBoolean(2,true);
        doNothing().when(ps).setInt(3,1);
        doNothing().when(ps).setInt(4,1);
        doNothing().when(ps).setDouble(5,1.0);
        doNothing().when(ps).setDouble(6,1.0);
        doReturn(0).when(ps).executeUpdate();
        dao.doInsert(conn,true,true,1,1,1.0,1.0);
        Mockito.verify(dao, times(1)).doInsert(conn,true,true,1,1,1.0,1.0);
    }

    @Test
    public void testDoUpdateFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("UPDATE Stanza SET animaleDomestico=?, fumatore=?, lettiSingoli=?, lettiMatrimoniali=?, " +
                        "costoNotte=?, sconto=? WHERE idStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setBoolean(1,true);
        doNothing().when(ps).setBoolean(2,true);
        doNothing().when(ps).setInt(3,1);
        doNothing().when(ps).setInt(4,1);
        doNothing().when(ps).setDouble(5,1.0);
        doNothing().when(ps).setDouble(6,1.0);
        doNothing().when(ps).setInt(7,1);
        doReturn(0).when(ps).executeUpdate();
        dao.doUpdate(conn,1,true,true,1,1,1.0,1.0);
        Mockito.verify(dao, times(1)).doUpdate(conn,1,true,true,1,1,1.0,1.0);
    }

    @Test
    public void testDoSelectByIdFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Stanza WHERE idStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        doReturn(true).when(rs).next();
        Assert.assertNotNull(dao.doSelectById(conn ,1));
    }

    @Test
    public void testDoSelectByIdStanzaNotFoundException() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Stanza WHERE idStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        doReturn(false).when(rs).next();
        Assert.assertThrows(StanzaNotFoundException.class,()->
                dao.doSelectById(conn ,1));
    }

    @Test
    public void testGetStanzeFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Stanza",
                Statement.RETURN_GENERATED_KEYS);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        Assert.assertNotNull(dao.getStanze(conn));
    }

    @Test
    public void testGetOfferteFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Stanza WHERE Stanza.sconto > 0",
                Statement.RETURN_GENERATED_KEYS);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        Assert.assertNotNull(dao.getOfferte(conn));
    }

    @Test
    public void testDoSelect_Min_And_Max_Prices1() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT MIN(costoNotte), MAX(costoNotte) FROM Stanza",
                Statement.RETURN_GENERATED_KEYS);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true);
        Assert.assertNotNull(dao.doSelect_Min_And_Max_Prices(conn));
    }

    @Test
    public void testDoSelect_Min_And_Max_Prices2() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT MIN(costoNotte), MAX(costoNotte) FROM Stanza",
                Statement.RETURN_GENERATED_KEYS);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(false);
        Assert.assertNotNull(dao.doSelect_Min_And_Max_Prices(conn));
    }

    @Test
    public void testIsDisponibile1() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT idPrenotazioneStanza FROM PrenotazioneStanza WHERE " +
                        "ksStanza=? AND dataFine >= ? AND dataInizio <= ? AND ksStato NOT IN (SELECT idStato FROM Stato st WHERE " +
                        "(st.stato = 'ANNULLATA') OR (st.stato = 'ARCHIVIATA') OR (st.stato = 'RIMBORSATA'))",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setDate(2,new Date(0));
        doNothing().when(ps).setDate(3,new Date(0));
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true);
        Assert.assertNotNull(dao.isDisponibile(conn, 1,new Date(0), new Date(0)));
    }

    @Test
    public void testIsDisponibile2() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT idPrenotazioneStanza FROM PrenotazioneStanza WHERE " +
                        "ksStanza=? AND dataFine >= ? AND dataInizio <= ? AND ksStato NOT IN (SELECT idStato FROM Stato st WHERE " +
                        "(st.stato = 'ANNULLATA') OR (st.stato = 'ARCHIVIATA') OR (st.stato = 'RIMBORSATA'))",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doNothing().when(ps).setDate(2,new Date(0));
        doNothing().when(ps).setDate(3,new Date(0));
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(false);
        Assert.assertNotNull(dao.isDisponibile(conn, 1,new Date(0), new Date(0)));
    }

    @Test
    public void testAnimaleDomesticoStrTrue() {
        ArrayList<String> lista = new ArrayList<>();
        dao.animaleDomesticoStr(lista, true);
        Mockito.verify(dao, times(1)).animaleDomesticoStr(lista, true);
    }

    @Test
    public void testAnimaleDomesticoStrFalse() {
        ArrayList<String> lista = new ArrayList<>();
        dao.animaleDomesticoStr(lista, false);
        Mockito.verify(dao, times(1)).animaleDomesticoStr(lista, false);
    }

    @Test
    public void testAnimaleDomesticoStrNull() {
        ArrayList<String> lista = new ArrayList<>();
        dao.animaleDomesticoStr(lista, null);
        Mockito.verify(dao, times(1)).animaleDomesticoStr(lista, null);
    }

    @Test
    public void testFumatoreStrTrue() {
        ArrayList<String> lista = new ArrayList<>();
        dao.fumatoreStr(lista, true);
        Mockito.verify(dao, times(1)).fumatoreStr(lista, true);
    }

    @Test
    public void testFumatoreStrFalse() {
        ArrayList<String> lista = new ArrayList<>();
        dao.fumatoreStr(lista, false);
        Mockito.verify(dao, times(1)).fumatoreStr(lista, false);
    }

    @Test
    public void testFumatoreStrNull() {
        dao.fumatoreStr(new ArrayList<>(), null);
        Mockito.verify(dao, times(1)).fumatoreStr(new ArrayList<>(), null);
    }

    @Test
    public void testNumeroOspitiStrFine() {
        ArrayList<String> lista = new ArrayList<>();
        dao.numeroOspitiStr(lista, 3);
        Mockito.verify(dao, times(1)).numeroOspitiStr(lista, 3);
    }

    @Test
    public void testNumeroOspitiStrNull() {
        ArrayList<String> lista = new ArrayList<>();
        dao.numeroOspitiStr(lista, null);
        Mockito.verify(dao, times(1)).numeroOspitiStr(lista, null);
    }

    @Test
    public void testCostoNotteStr1() {
        ArrayList<String> lista = new ArrayList<>();
        dao.costoNotteStr(lista, 1.0, null);
        Mockito.verify(dao, times(1)).costoNotteStr(lista, 1.0, null);
    }

    @Test
    public void testCostoNotteStr2() {
        ArrayList<String> lista = new ArrayList<>();
        dao.costoNotteStr(lista, null, 1.0);
        Mockito.verify(dao, times(1)).costoNotteStr(lista, null, 1.0);
    }

    @Test
    public void testCostoNotteStr3() {
        ArrayList<String> lista = new ArrayList<>();
        dao.costoNotteStr(lista, 1.0, 2.0);
        Mockito.verify(dao, times(1)).costoNotteStr(lista, 1.0, 2.0);
    }

    @Test
    public void testCostoNotteStr4() {
        ArrayList<String> lista = new ArrayList<>();
        dao.costoNotteStr(lista, null, null);
        Mockito.verify(dao, times(1)).costoNotteStr(lista, null, null);
    }

    @Test
    public void testScontoStr1() {
        ArrayList<String> lista = new ArrayList<>();
        dao.scontoStr(lista, 1.0, null);
        Mockito.verify(dao, times(1)).scontoStr(lista, 1.0, null);
    }

    @Test
    public void testScontoStr2() {
        ArrayList<String> lista = new ArrayList<>();
        dao.scontoStr(lista, null, 1.0);
        Mockito.verify(dao, times(1)).scontoStr(lista, null, 1.0);
    }

    @Test
    public void testScontoStr3() {
        ArrayList<String> lista = new ArrayList<>();
        dao.scontoStr(lista, 1.0, 2.0);
        Mockito.verify(dao, times(1)).scontoStr(lista, 1.0, 2.0);
    }

    @Test
    public void testScontoStr4() {
        ArrayList<String> lista = new ArrayList<>();
        dao.scontoStr(lista, null, null);
        Mockito.verify(dao, times(1)).scontoStr(lista, null, null);
    }

    @Test
    public void testData1() {
        Assert.assertNotNull(dao.data(null, null, null));
    }

    @Test
    public void testData2() {
        Assert.assertNotNull(dao.data(null, new Date(0), new Date(0)));
    }

    @Test
    public void testData3() {
        Assert.assertNotNull(dao.data(null, new Date(0), null));
    }

    @Test
    public void testData4() {
        Assert.assertNotNull(dao.data(null, null, new Date(0)));
    }

    @Test
    public void testData5() {
        Assert.assertNotNull(dao.data("", null, null));
    }

    @Test
    public void testData6() {
        Assert.assertNotNull(dao.data("", new Date(0), new Date(0)));
    }

    @Test
    public void testData7() {
        Assert.assertNotNull(dao.data("", new Date(0), null));
    }

    @Test
    public void testData8() {
        Assert.assertNotNull(dao.data("", null, new Date(0)));
    }

    @Test
    public void testDoSearchFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Stanza s",
                Statement.RETURN_GENERATED_KEYS);
        doReturn("").when(dao).getQuery(null,null,null,null,null,null,null,null,null);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        Assert.assertNotNull(dao.doSearch(conn, null, null,null,null,null,null,null,null,null));
    }

    @Test
    public void testGetQuery1() {
        Assert.assertNotNull(dao.getQuery(null,null,null,null,null,null,null,null,null));
    }

    @Test
    public void testGetQuery2() {
        Assert.assertNotNull(dao.getQuery(true,true,null,null,null,null,null,new Date(0),new Date(0)));
    }

    @Test
    public void testGetQuery3() {
        Assert.assertNotNull(dao.getQuery(true,true,null,null,null,null,null,null,new Date(0)));
    }

    @Test
    public void testGetQuery4() {
        Assert.assertNotNull(dao.getQuery(true,true,null,null,null,null,null,new Date(0),null));
    }

}
