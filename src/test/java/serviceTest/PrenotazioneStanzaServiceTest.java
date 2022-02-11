package serviceTest;

import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.model.personaAggiuntiva.PersonaAggiuntivaDAO;
import it.hotel.model.personaPrenotazione.PersonaPrenotazioneDAO;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stato.Stato;
import it.hotel.model.stato.StatoDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrenotazioneStanzaServiceTest extends Mockito {

    private PrenotazioneStanzaService service;

    private PrenotazioneStanzaDAO prenotazioneStanzaDAO;
    private StanzaDAO stanzaDAO;
    private PersonaAggiuntivaDAO personaAggiuntivaDAO;
    private PersonaPrenotazioneDAO personaPrenotazioneDAO;
    private StatoDAO statoDAO;

    private Connection conn;

    @Before
    public void setUp()
    {
        service= Mockito.spy(new PrenotazioneStanzaService());

        prenotazioneStanzaDAO=Mockito.mock(PrenotazioneStanzaDAO.class);
        stanzaDAO=Mockito.mock(StanzaDAO.class);
        personaAggiuntivaDAO=Mockito.mock(PersonaAggiuntivaDAO.class);
        personaPrenotazioneDAO=Mockito.mock(PersonaPrenotazioneDAO.class);
        statoDAO=Mockito.mock(StatoDAO.class);

        conn=Mockito.mock(Connection.class);
    }

    @Test
    public void testCreatePrenotazioneStanzaDAO() {
        PrenotazioneStanzaDAO dao = service.createPrenotazioneStanzaDAO();
        Assert.assertNotEquals(dao, null);
    }

    @Test
    public void testCreateStanzaDAO() {
        StanzaDAO dao = service.createStanzaDAO();
        Assert.assertNotEquals(dao, null);
    }

    @Test
    public void testCreatePersonaAggiuntivaDAO() {
        PersonaAggiuntivaDAO dao = service.createPersonaAggiuntivaDAO();
        Assert.assertNotEquals(dao, null);
    }

    @Test
    public void testCreatePersonaPrenotazioneDAO() {
        PersonaPrenotazioneDAO dao = service.createPersonaPrenotazioneDAO();
        Assert.assertNotEquals(dao, null);
    }

    @Test
    public void testCreateStatoDAO() {
        StatoDAO dao = service.createStatoDAO();
        Assert.assertNotEquals(dao, null);
    }

    @Test
    public void testSelectByRuntimeException() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->
                service.selectBy(1, 1));
    }

    @Test
    public void testSelectByFine() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(conn).when(service).getConnection();
        when(prenotazioneStanzaDAO.doSelectBy(conn,1, 1)).thenReturn(new ArrayList<>());
        service.selectBy(1,1);
    }

    @Test
    public void testGetAllRuntimeException() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.getAll());
    }

    @Test
    public void testGetAllFine() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(conn).when(service).getConnection();
        when(prenotazioneStanzaDAO.doGetAll(conn)).thenReturn(new ArrayList<>());
        service.getAll();
    }

    @Test
    public void testEditStatoRuntimeException() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.editStato(1,1));
    }

    @Test
    public void testEditStatoFine() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doNothing().when(prenotazioneStanzaDAO).doChangeStato(conn, 1, 1);
        service.editStato(1,1);
    }

    @Test
    public void testGetPrenotazioneByIdRuntimeException() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.getPrenotazioneById(1));
    }

    @Test
    public void testGetPrenotazioneByIdFine() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new PrenotazioneStanza(
                1, 1,1, 1, new Date(0), new Date(0), 20.0, "", "", "", -1)
        ).when(prenotazioneStanzaDAO).doSelectById(conn, 1);
        service.getPrenotazioneById(1);
    }

    @Test
    public void testAddTokenStripeRuntimeException() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.addTokenStripe(1,""));
    }

    @Test
    public void testAddTokenStripeFine() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doNothing().when(prenotazioneStanzaDAO).insertTokenStripe(conn, 1, "");
        service.addTokenStripe(1,"");
    }

    @Test
    public void testGenerateQrCodeRuntimeException() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.generateQrCode(1));
    }

    @Test
    public void testGenerateQrCodeFine() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doNothing().when(prenotazioneStanzaDAO).doInsertTokenQrCode(conn, 1, "");
        service.generateQrCode(1);
    }

    @Test
    public void testIsRimborsabileRuntimeException() throws Exception {
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doThrow(new SQLException()).when(service).getConnection();
        Assert.assertThrows(RuntimeException.class, ()->service.isRimborsabile(1));
    }

    @Test
    public void testIsRimborsabileFine() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stato(1, "")).when(statoDAO).doSelectByStato(conn, "CONFERMATA");
        doReturn(true).when(prenotazioneStanzaDAO).isRimborsabile(conn, 1,1);
        service.isRimborsabile(1);
    }

}
