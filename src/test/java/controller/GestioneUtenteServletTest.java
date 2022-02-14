package controller;

import it.hotel.controller.AnagraficaServlet;
import it.hotel.controller.GestioneUtentiServlet;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class GestioneUtenteServletTest extends Mockito
{
    private GestioneUtentiServlet controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private UtenteService utenteService;
    private Utente utente;
    @Before
    public void setUp()
    {
        controller=spy(new GestioneUtentiServlet());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        requestDispatcher=mock(RequestDispatcher.class);
        utenteService=mock(UtenteService.class);
        utente=mock(Utente.class);
    }

    @Test
    public void testUtenteVuoto() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.empty()).when(controller).getUtente(request);
        controller.doGet(request,response);
    }

    @Test
    public void testUtenteNotOk() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.of(utente)).when(controller).getUtente(request);
        when(utente.getRuolo()).thenReturn(3);
        controller.doGet(request,response);
    }

    @Test
    public void testUtenteOk() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.of(utente)).when(controller).getUtente(request);
        when(utente.getRuolo()).thenReturn(1);
        when(controller.getUtenteService()).thenReturn(utenteService);
        controller.doGet(request,response);
    }
    @Test
    public void testUtenteOk2() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.of(utente)).when(controller).getUtente(request);
        when(utente.getRuolo()).thenReturn(2);
        when(controller.getUtenteService()).thenReturn(utenteService);
        controller.doGet(request,response);
    }
}
