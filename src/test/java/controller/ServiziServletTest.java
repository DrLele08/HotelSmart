package controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.ServiziServlet;
import it.hotel.controller.services.PrenotazioneServizioService;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.ServizioService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.servizio.Servizio;
import it.hotel.model.utente.Utente;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;

public class ServiziServletTest extends Mockito
{

    private ServiziServlet controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher requestDispatcher;
    private ServizioService servizioService;
    private PrenotazioneServizioService prenotazioneServizioService;
    private PrenotazioneStanzaService prenotazioneStanzaService;
    private Utente utente;

    @Before
    public void setUp()
    {
        controller=spy(new ServiziServlet());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        session=mock(HttpSession.class);
        requestDispatcher=mock(RequestDispatcher.class);
        servizioService=mock(ServizioService.class);
        prenotazioneServizioService=mock(PrenotazioneServizioService.class);
        prenotazioneStanzaService=mock(PrenotazioneStanzaService.class);
        utente=mock(Utente.class);
    }

    @Test
    public void testPathNull() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn(null);
        controller.doGet(request,response);
    }

    @Test
    public void testPathGoServizi() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/goservizi");
        when(servizioService.getAll()).thenReturn(new ArrayList<>());
        doReturn(new PrenotazioneStanza(
                1,1,1,1,new Date(0), new Date(0), 10.0,
                "tokenStripe", "tokenQr", "commenti", -1)
        ).when(controller).getActiveReservation(request);
        controller.doGet(request,response);
    }

    @Test
    public void testPathGoServizi1() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/goservizi");
        when(servizioService.getAll()).thenReturn(new ArrayList<>());
        doReturn(null).when(controller).getActiveReservation(request);
        controller.doGet(request,response);
    }

    @Test
    public void testPathDefault() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/default");
        when(servizioService.getAll()).thenReturn(new ArrayList<>());
        controller.doGet(request,response);
    }

    @Test
    public void testPathDefault1() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/default");
        when(servizioService.getAll()).thenReturn(new ArrayList<>());
        controller.doPost(request,response);
    }

    @Test
    public void testPathServizioDetailForm() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/servizioDetailForm");
        when(request.getParameter("servizioId")).thenReturn("1");
        when(servizioService.getById(1)).thenReturn(new Servizio(
                1, "nome", "descrizione", "foto", 10.0,1));
        controller.doPost(request,response);
    }

    @Test
    public void testPathPrenotaServizio() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/prenotaServizio");
        when(request.getParameter("numero_ospiti")).thenReturn("3");
        when(request.getParameter("servizioId")).thenReturn("1");
        when(request.getParameter("data")).thenReturn("2000-01-01");
        when(controller.getPrenotazioneServizioService()).thenReturn(prenotazioneServizioService);
        doNothing().when(prenotazioneServizioService).createPrenotazione(anyInt(),anyInt(),anyInt(),anyObject());
        doReturn(new PrenotazioneStanza(
                1,1,1,1,new Date(0), new Date(0), 10.0,
                "tokenStripe", "tokenQr", "commenti", -1)
        ).when(controller).getActiveReservation(request);
        controller.doPost(request,response);
    }

    @Test
    public void testPathPrenotaServizioParseException() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/prenotaServizio");
        when(request.getParameter("numero_ospiti")).thenReturn("3");
        when(request.getParameter("servizioId")).thenReturn("1");
        when(request.getParameter("data")).thenReturn("0");
        when(controller.getPrenotazioneServizioService()).thenReturn(prenotazioneServizioService);
        doNothing().when(prenotazioneServizioService).createPrenotazione(anyInt(),anyInt(),anyInt(),anyObject());
        doReturn(new PrenotazioneStanza(
                1,1,1,1,new Date(0), new Date(0), 10.0,
                "tokenStripe", "tokenQr", "commenti", -1)
        ).when(controller).getActiveReservation(request);
        controller.doPost(request,response);
    }

    @Test
    public void testPathPrenotaServizio1() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/prenotaServizio");
        when(request.getParameter("numero_ospiti")).thenReturn("3");
        when(request.getParameter("servizioId")).thenReturn("1");
        when(request.getParameter("data")).thenReturn("0");
        when(controller.getPrenotazioneServizioService()).thenReturn(prenotazioneServizioService);
        doNothing().when(prenotazioneServizioService).createPrenotazione(anyInt(),anyInt(),anyInt(),anyObject());
        when(controller.getSession(request)).thenReturn(session);
        when(controller.getPrenotazioneStanzaService()).thenReturn(prenotazioneStanzaService);
        controller.doPost(request,response);
    }

    @Test
    public void testPathPrenotaServizio2() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/prenotaServizio");
        when(request.getParameter("numero_ospiti")).thenReturn("3");
        when(request.getParameter("servizioId")).thenReturn("1");
        when(request.getParameter("data")).thenReturn("0");
        when(controller.getPrenotazioneServizioService()).thenReturn(prenotazioneServizioService);
        doNothing().when(prenotazioneServizioService).createPrenotazione(anyInt(),anyInt(),anyInt(),anyObject());
        when(controller.getSession(request)).thenReturn(session);
        Utente utente = new Utente(1,1,"asdfghjklasdfghj","nome","cognome","email",new Date(0),"token");
        Object o = utente;
        when(session.getAttribute(Utility.SESSION_USER)).thenReturn(o);
        when(controller.getPrenotazioneStanzaService()).thenReturn(prenotazioneStanzaService);
        when(prenotazioneStanzaService.selectBy(1,0)).thenReturn(new ArrayList<>());
        controller.doPost(request,response);
    }

    @Test
    public void testPathPrenotaServizio3() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/prenotaServizio");
        when(request.getParameter("numero_ospiti")).thenReturn("3");
        when(request.getParameter("servizioId")).thenReturn("1");
        when(request.getParameter("data")).thenReturn("0");
        when(controller.getPrenotazioneServizioService()).thenReturn(prenotazioneServizioService);
        doNothing().when(prenotazioneServizioService).createPrenotazione(anyInt(),anyInt(),anyInt(),anyObject());
        when(controller.getSession(request)).thenReturn(session);
        Utente utente = new Utente(1,1,"asdfghjklasdfghj","nome","cognome","email",new Date(0),"token");
        Object o = utente;
        when(session.getAttribute(Utility.SESSION_USER)).thenReturn(o);
        when(controller.getPrenotazioneStanzaService()).thenReturn(prenotazioneStanzaService);
        ArrayList<PrenotazioneStanza> lista = new ArrayList<>();
        lista.add(new PrenotazioneStanza(1,1,1,1,new Date(0),
                new Date(0),10.0,"tokenStripe","tokenQr","commenti",-1));
        when(prenotazioneStanzaService.selectBy(1,0)).thenReturn(lista);
        controller.doPost(request,response);
    }

    @Test
    public void testPathPrenotaServizio4() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(request.getPathInfo()).thenReturn("/prenotaServizio");
        when(request.getParameter("numero_ospiti")).thenReturn("3");
        when(request.getParameter("servizioId")).thenReturn("1");
        when(request.getParameter("data")).thenReturn("0");
        when(controller.getPrenotazioneServizioService()).thenReturn(prenotazioneServizioService);
        doNothing().when(prenotazioneServizioService).createPrenotazione(anyInt(),anyInt(),anyInt(),anyObject());
        when(controller.getSession(request)).thenReturn(session);
        Utente utente = new Utente(1,1,"asdfghjklasdfghj","nome","cognome","email",new Date(0),"token");
        Object o = utente;
        when(session.getAttribute(Utility.SESSION_USER)).thenReturn(o);
        when(controller.getPrenotazioneStanzaService()).thenReturn(prenotazioneStanzaService);
        ArrayList<PrenotazioneStanza> lista = new ArrayList<>();
        lista.add(new PrenotazioneStanza(1,1,1,3,new Date(0),
                new Date(0),10.0,"tokenStripe","tokenQr","commenti",-1));
        when(prenotazioneStanzaService.selectBy(1,0)).thenReturn(lista);
        controller.doPost(request,response);
    }

}
