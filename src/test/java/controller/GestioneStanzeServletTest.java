package controller;

import it.hotel.controller.AnagraficaServlet;
import it.hotel.controller.GestioneStanzeServlet;
import it.hotel.controller.services.StanzaService;
import it.hotel.model.utente.Utente;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class GestioneStanzeServletTest extends Mockito
{
    private GestioneStanzeServlet controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private Utente utente;
    private StanzaService service;
    @Before
    public void setUp()
    {
        controller=spy(new GestioneStanzeServlet());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        requestDispatcher=mock(RequestDispatcher.class);
        utente=mock(Utente.class);
        service=mock(StanzaService.class);
    }

    @Test
    public void testUtenteNonTrovato() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.empty()).when(controller).getUtente(request);
        controller.doGet(request,response);
    }

    @Test
    public void testUtenteOkRuolo1() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.of(utente)).when(controller).getUtente(request);
        when(controller.getStanzaService()).thenReturn(service);
        when(utente.getRuolo()).thenReturn(1);
        controller.doGet(request,response);
    }

    @Test
    public void testUtenteOkRuolo2() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.of(utente)).when(controller).getUtente(request);
        when(controller.getStanzaService()).thenReturn(service);
        when(utente.getRuolo()).thenReturn(2);
        controller.doGet(request,response);
    }

}
