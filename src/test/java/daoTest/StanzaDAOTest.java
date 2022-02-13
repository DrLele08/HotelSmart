package daoTest;

import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
    }

    @Test
    public void testDoSelectByIdFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Stanza WHERE idStanza=?",
                Statement.RETURN_GENERATED_KEYS);
        doNothing().when(ps).setInt(1,1);
        doReturn(rs).when(ps).executeQuery();
        doReturn(true).when(rs).next();
        dao.doSelectById(conn ,1);
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
        dao.getStanze(conn);
    }

    @Test
    public void testGetOfferteFine() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT * FROM Stanza WHERE Stanza.sconto > 0",
                Statement.RETURN_GENERATED_KEYS);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true).thenReturn(false);
        dao.getOfferte(conn);
    }

    @Test
    public void testDoSelect_Min_And_Max_Prices1() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT MIN(costoNotte), MAX(costoNotte) FROM Stanza",
                Statement.RETURN_GENERATED_KEYS);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(true);
        dao.doSelect_Min_And_Max_Prices(conn);
    }

    @Test
    public void testDoSelect_Min_And_Max_Prices2() throws Exception {
        doReturn(ps).when(conn).prepareStatement("SELECT MIN(costoNotte), MAX(costoNotte) FROM Stanza",
                Statement.RETURN_GENERATED_KEYS);
        doReturn(rs).when(ps).executeQuery();
        when(rs.next()).thenReturn(false);
        dao.doSelect_Min_And_Max_Prices(conn);
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
        dao.isDisponibile(conn, 1,new Date(0), new Date(0));
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
        dao.isDisponibile(conn, 1,new Date(0), new Date(0));
    }

    @Test
    public void testAnimaleDomesticoStrTrue() {
        dao.animaleDomesticoStr(new ArrayList<>(), true);
    }

    @Test
    public void testAnimaleDomesticoStrFalse() {
        dao.animaleDomesticoStr(new ArrayList<>(), false);
    }

    @Test
    public void testAnimaleDomesticoStrNull() {
        dao.animaleDomesticoStr(new ArrayList<>(), null);
    }

    @Test
    public void testFumatoreStrTrue() {
        dao.fumatoreStr(new ArrayList<>(), true);
    }

    @Test
    public void testFumatoreStrFalse() {
        dao.fumatoreStr(new ArrayList<>(), false);
    }

    @Test
    public void testFumatoreStrNull() {
        dao.fumatoreStr(new ArrayList<>(), null);
    }

    @Test
    public void testNumeroOspitiStrFine() {
        dao.numeroOspitiStr(new ArrayList<>(), 3);
    }

    @Test
    public void testNumeroOspitiStrNull() {
        dao.numeroOspitiStr(new ArrayList<>(), null);
    }

    @Test
    public void testCostoNotteStr1() {
        dao.costoNotteStr(new ArrayList<>(), 1.0, null);
    }

    @Test
    public void testCostoNotteStr2() {
        dao.costoNotteStr(new ArrayList<>(), null, 1.0);
    }

    @Test
    public void testCostoNotteStr3() {
        dao.costoNotteStr(new ArrayList<>(), 1.0, 2.0);
    }

    @Test
    public void testCostoNotteStr4() {
        dao.costoNotteStr(new ArrayList<>(), null, null);
    }

    @Test
    public void testScontoStr1() {
        dao.scontoStr(new ArrayList<>(), 1.0, null);
    }

    @Test
    public void testScontoStr2() {
        dao.scontoStr(new ArrayList<>(), null, 1.0);
    }

    @Test
    public void testScontoStr3() {
        dao.scontoStr(new ArrayList<>(), 1.0, 2.0);
    }

    @Test
    public void testScontoStr4() {
        dao.scontoStr(new ArrayList<>(), null, null);
    }

    @Test
    public void testData1() {
        dao.data(null, null, null);
    }

    @Test
    public void testData2() {
        dao.data(null, new Date(0), new Date(0));
    }

    @Test
    public void testData3() {
        dao.data(null, new Date(0), null);
    }

    @Test
    public void testData4() {
        dao.data(null, null, new Date(0));
    }

    @Test
    public void testData5() {
        dao.data("", null, null);
    }

    @Test
    public void testData6() {
        dao.data("", new Date(0), new Date(0));
    }

    @Test
    public void testData7() {
        dao.data("", new Date(0), null);
    }

    @Test
    public void testData8() {
        dao.data("", null, new Date(0));
    }



}
