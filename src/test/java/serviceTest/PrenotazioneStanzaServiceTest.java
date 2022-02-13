package serviceTest;

import it.hotel.controller.exception.PagamentoInAttesaException;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.model.personaAggiuntiva.PersonaAggiuntiva;
import it.hotel.model.personaAggiuntiva.PersonaAggiuntivaDAO;
import it.hotel.model.personaPrenotazione.PersonaPrenotazioneDAO;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaInsertException;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stato.Stato;
import it.hotel.model.stato.StatoDAO;
import it.hotel.model.stato.statoExceptions.StatoNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

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
        Assert.assertNotNull(service.selectBy(1,1));
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
        Assert.assertNotNull(service.getAll());
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
        Mockito.verify(service,times(1)).editStato(1,1);
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
        Assert.assertNotNull(service.getPrenotazioneById(1));
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
        Mockito.verify(service,times(1)).addTokenStripe(1,"");
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
        Mockito.verify(service,times(1)).generateQrCode(1);
    }

    @Test
    public void testIsRimborsabileRuntimeException() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stato(1, "CONFERMATA")).when(statoDAO).doSelectByStato(conn, "CONFERMATA");
        doThrow(new SQLException()).when(conn).commit();
        Assert.assertThrows(RuntimeException.class, ()->service.isRimborsabile(1));
    }

    @Test
    public void testIsRimborsabileRuntimeException2() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doThrow(new StatoNotFoundException()).when(statoDAO).doSelectByStato(conn, "CONFERMATA");
        Assert.assertThrows(RuntimeException.class, ()->service.isRimborsabile(1));
    }

    @Test
    public void testIsRimborsabileFine() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stato(1, "")).when(statoDAO).doSelectByStato(conn, "CONFERMATA");
        doReturn(true).when(prenotazioneStanzaDAO).isRimborsabile(conn, 1,1);
        Assert.assertNotNull(service.isRimborsabile(1));
    }

    @Test
    public void testInserisciPrenotazioneFine() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(personaAggiuntivaDAO).when(service).createPersonaAggiuntivaDAO();
        doReturn(personaPrenotazioneDAO).when(service).createPersonaPrenotazioneDAO();
        doReturn(stanzaDAO).when(service).createStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stanza(
                1, true, true, 2,0,10.0,1.0)
        ).when(stanzaDAO).doSelectById(conn, 1);
        GregorianCalendar in = new GregorianCalendar(2000,0,1);
        GregorianCalendar fi = new GregorianCalendar(2000,0,2);
        Date inizio = new Date(in.getTimeInMillis());
        Date fine = new Date(fi.getTimeInMillis());
        doReturn(true).when(stanzaDAO).isDisponibile(conn, 1,inizio, fine);
        doReturn(new PrenotazioneStanza(1,1,1,1,inizio,fine,10.0,"token","token","commenti", -1)).when(prenotazioneStanzaDAO).doSelectById(conn, 1);
        doReturn(1).when(prenotazioneStanzaDAO).doInsert(conn, 1, 1, 1,inizio, fine, 10.0, 1.0);
        List<PersonaAggiuntiva> lista = new ArrayList<>();
        PersonaAggiuntiva persona = new PersonaAggiuntiva("asdfghjklasdfghj", "nome", "cognome", "2000-01-01");
        doReturn(persona).when(personaAggiuntivaDAO).doInsert(conn, 1, "asdfghjklasdfghj", "nome", "cognome", inizio);
        doNothing().when(personaPrenotazioneDAO).doInsert(conn, 1,1);
        lista.add(persona);
        Assert.assertNotNull(service.inserisciPrenotazione(1,1,"2000-01-01", "2000-01-02", lista));
    }

    @Test
    public void testInserisciPrenotazionePrenotazioneStanzaInsertException() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(personaAggiuntivaDAO).when(service).createPersonaAggiuntivaDAO();
        doReturn(personaPrenotazioneDAO).when(service).createPersonaPrenotazioneDAO();
        doReturn(stanzaDAO).when(service).createStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stanza(
                1, true, true, 1,0,10.0,1.0)
        ).when(stanzaDAO).doSelectById(conn, 1);
        GregorianCalendar in = new GregorianCalendar(2000,0,1);
        GregorianCalendar fi = new GregorianCalendar(2000,0,2);
        Date inizio = new Date(in.getTimeInMillis());
        Date fine = new Date(fi.getTimeInMillis());
        doReturn(false).when(stanzaDAO).isDisponibile(conn, 1,inizio, fine);
        Assert.assertThrows(PrenotazioneStanzaInsertException.class, ()->
            service.inserisciPrenotazione(1,1,"2000-01-01", "2000-01-02", new ArrayList<>()));
    }

    @Test
    public void testInserisciPrenotazionePrenotazioneStanzaInsertException2() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(personaAggiuntivaDAO).when(service).createPersonaAggiuntivaDAO();
        doReturn(personaPrenotazioneDAO).when(service).createPersonaPrenotazioneDAO();
        doReturn(stanzaDAO).when(service).createStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stanza(
                1, true, true, 1,1,10.0,1.0)
        ).when(stanzaDAO).doSelectById(conn, 1);
        GregorianCalendar in = new GregorianCalendar(2000,0,1);
        GregorianCalendar fi = new GregorianCalendar(2000,0,2);
        Date inizio = new Date(in.getTimeInMillis());
        Date fine = new Date(fi.getTimeInMillis());
        doReturn(true).when(stanzaDAO).isDisponibile(conn, 1,inizio, fine);
        doReturn(1).when(prenotazioneStanzaDAO).doInsert(conn, 1, 1, 1,inizio, fine, 10.0, 1.0);
        Assert.assertThrows(PrenotazioneStanzaInsertException.class, ()->
                service.inserisciPrenotazione(1,1,"2000-01-01", "2000-01-02", new ArrayList<>()));
    }

    @Test
    public void testInserisciPrenotazionePrenotazioneStanzaInsertException3() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(personaAggiuntivaDAO).when(service).createPersonaAggiuntivaDAO();
        doReturn(personaPrenotazioneDAO).when(service).createPersonaPrenotazioneDAO();
        doReturn(stanzaDAO).when(service).createStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stanza(
                1, true, true, 2,0,10.0,1.0)
        ).when(stanzaDAO).doSelectById(conn, 1);
        GregorianCalendar in = new GregorianCalendar(2000,0,1);
        GregorianCalendar fi = new GregorianCalendar(2000,0,2);
        Date inizio = new Date(in.getTimeInMillis());
        Date fine = new Date(fi.getTimeInMillis());
        doReturn(true).when(stanzaDAO).isDisponibile(conn, 1,inizio, fine);
        doReturn(new PrenotazioneStanza(1,1,1,1,inizio,fine,10.0,"token","token","commenti", -1)).when(prenotazioneStanzaDAO).doSelectById(conn, 1);
        doReturn(1).when(prenotazioneStanzaDAO).doInsert(conn, 1, 1, 1,inizio, fine, 10.0, 1.0);
        List<PersonaAggiuntiva> lista = new ArrayList<>();
        PersonaAggiuntiva persona = new PersonaAggiuntiva("asdfghjklasdfgfhj", "nome", "cognome", "2000-01-01");
        doReturn(persona).when(personaAggiuntivaDAO).doInsert(conn, 1, "asdfghjklasdfghj", "nome", "cognome", inizio);
        doNothing().when(personaPrenotazioneDAO).doInsert(conn, 1,1);
        lista.add(persona);
        Assert.assertThrows(PrenotazioneStanzaInsertException.class,()->
                service.inserisciPrenotazione(1,1,"2000-01-01", "2000-01-02", lista));
    }

    @Test
    public void testInserisciPrenotazionePrenotazioneStanzaInsertException4() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(personaAggiuntivaDAO).when(service).createPersonaAggiuntivaDAO();
        doReturn(personaPrenotazioneDAO).when(service).createPersonaPrenotazioneDAO();
        doReturn(stanzaDAO).when(service).createStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stanza(
                1, true, true, 2,0,10.0,1.0)
        ).when(stanzaDAO).doSelectById(conn, 1);
        GregorianCalendar in = new GregorianCalendar(2000,0,1);
        GregorianCalendar fi = new GregorianCalendar(2000,0,2);
        Date inizio = new Date(in.getTimeInMillis());
        Date fine = new Date(fi.getTimeInMillis());
        doReturn(true).when(stanzaDAO).isDisponibile(conn, 1,inizio, fine);
        doReturn(new PrenotazioneStanza(1,1,1,1,inizio,fine,10.0,"token","token","commenti", -1)).when(prenotazioneStanzaDAO).doSelectById(conn, 1);
        doReturn(1).when(prenotazioneStanzaDAO).doInsert(conn, 1, 1, 1,inizio, fine, 10.0, 1.0);
        List<PersonaAggiuntiva> lista = new ArrayList<>();
        PersonaAggiuntiva persona = new PersonaAggiuntiva("asdfghjklasdfghj", "", "cognome", "2000-01-01");
        doReturn(persona).when(personaAggiuntivaDAO).doInsert(conn, 1, "asdfghjklasdfghj", "nome", "cognome", inizio);
        doNothing().when(personaPrenotazioneDAO).doInsert(conn, 1,1);
        lista.add(persona);
        Assert.assertThrows(PrenotazioneStanzaInsertException.class,()->
                service.inserisciPrenotazione(1,1,"2000-01-01", "2000-01-02", lista));
    }

    @Test
    public void testInserisciPrenotazionePrenotazioneStanzaInsertException5() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(personaAggiuntivaDAO).when(service).createPersonaAggiuntivaDAO();
        doReturn(personaPrenotazioneDAO).when(service).createPersonaPrenotazioneDAO();
        doReturn(stanzaDAO).when(service).createStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stanza(
                1, true, true, 2,0,10.0,1.0)
        ).when(stanzaDAO).doSelectById(conn, 1);
        GregorianCalendar in = new GregorianCalendar(2000,0,1);
        GregorianCalendar fi = new GregorianCalendar(2000,0,2);
        Date inizio = new Date(in.getTimeInMillis());
        Date fine = new Date(fi.getTimeInMillis());
        doReturn(true).when(stanzaDAO).isDisponibile(conn, 1,inizio, fine);
        doReturn(new PrenotazioneStanza(1,1,1,1,inizio,fine,10.0,"token","token","commenti", -1)).when(prenotazioneStanzaDAO).doSelectById(conn, 1);
        doReturn(1).when(prenotazioneStanzaDAO).doInsert(conn, 1, 1, 1,inizio, fine, 10.0, 1.0);
        List<PersonaAggiuntiva> lista = new ArrayList<>();
        PersonaAggiuntiva persona = new PersonaAggiuntiva("asdfghjklasdfghj", "nome", "", "2000-01-01");
        doReturn(persona).when(personaAggiuntivaDAO).doInsert(conn, 1, "asdfghjklasdfghj", "nome", "cognome", inizio);
        doNothing().when(personaPrenotazioneDAO).doInsert(conn, 1,1);
        lista.add(persona);
        Assert.assertThrows(PrenotazioneStanzaInsertException.class,()->
                service.inserisciPrenotazione(1,1,"2000-01-01", "2000-01-02", lista));
    }

    @Test
    public void testInserisciPrenotazionePagamentoInAttesaException() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(personaAggiuntivaDAO).when(service).createPersonaAggiuntivaDAO();
        doReturn(personaPrenotazioneDAO).when(service).createPersonaPrenotazioneDAO();
        doReturn(stanzaDAO).when(service).createStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stanza(
                1, true, true, 1,0,10.0,1.0)
        ).when(stanzaDAO).doSelectById(conn, 1);
        GregorianCalendar in = new GregorianCalendar(2000,0,1);
        GregorianCalendar fi = new GregorianCalendar(2000,0,2);
        Date inizio = new Date(in.getTimeInMillis());
        Date fine = new Date(fi.getTimeInMillis());
        doReturn(true).when(stanzaDAO).isDisponibile(conn, 1,inizio, fine);
        doReturn(1).when(prenotazioneStanzaDAO).doInsert(conn, 1, 1, 1,inizio, fine, 10.0, 1.0);
        doThrow(new PrenotazioneStanzaInsertException()).when(prenotazioneStanzaDAO).doInsert(conn, 1, 1, 1,inizio, fine, 10.0, 1.0);
        Assert.assertThrows(PagamentoInAttesaException.class, ()->
                service.inserisciPrenotazione(1,1,"2000-01-01", "2000-01-02", new ArrayList<>()));
    }

    @Test
    public void testInserisciPrenotazioneRuntimeException() throws Exception {
        doReturn(statoDAO).when(service).createStatoDAO();
        doReturn(prenotazioneStanzaDAO).when(service).createPrenotazioneStanzaDAO();
        doReturn(personaAggiuntivaDAO).when(service).createPersonaAggiuntivaDAO();
        doReturn(personaPrenotazioneDAO).when(service).createPersonaPrenotazioneDAO();
        doReturn(stanzaDAO).when(service).createStanzaDAO();
        doReturn(conn).when(service).getConnection();
        doReturn(new Stanza(
                1, true, true, 1,0,10.0,1.0)
        ).when(stanzaDAO).doSelectById(conn, 1);
        doThrow(new SQLException()).when(stanzaDAO).doSelectById(conn, 1);
        GregorianCalendar in = new GregorianCalendar(2000,0,1);
        GregorianCalendar fi = new GregorianCalendar(2000,0,2);
        Date inizio = new Date(in.getTimeInMillis());
        Date fine = new Date(fi.getTimeInMillis());
        doReturn(true).when(stanzaDAO).isDisponibile(conn, 1,inizio, fine);
        doReturn(1).when(prenotazioneStanzaDAO).doInsert(conn, 1, 1, 1,inizio, fine, 10.0, 1.0);
        Assert.assertThrows(RuntimeException.class, ()->
                service.inserisciPrenotazione(1,1,"2000-01-01", "2000-01-02", new ArrayList<>()));
    }

}
