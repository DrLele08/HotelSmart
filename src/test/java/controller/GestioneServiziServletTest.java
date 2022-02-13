package controller;

import it.hotel.controller.GestioneServiziServlet;
import it.hotel.controller.StartServlet;
import it.hotel.controller.services.ServizioService;
import it.hotel.model.utente.Utente;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class GestioneServiziServletTest extends Mockito
{
    private GestioneServiziServlet controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private ServizioService servizioService;
    private Utente utente;
    @Before
    public void setUp()
    {
        controller=spy(new GestioneServiziServlet());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        requestDispatcher=mock(RequestDispatcher.class);
        servizioService=mock(ServizioService.class);
        utente=mock(Utente.class);
    }
    @Test
    public void testUtenteNonTrovato() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.empty()).when(controller).getUtente(request);
        controller.doGet(request,response);
    }

    @Test
    public void testUtenteTrovatoNotAdmin() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.of(utente)).when(controller).getUtente(request);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(utente.getRuolo()).thenReturn(3);
        controller.doGet(request,response);
    }

    @Test
    public void testUtenteTrovatoAdmin() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.of(utente)).when(controller).getUtente(request);
        when(controller.getServizioService()).thenReturn(servizioService);
        when(utente.getRuolo()).thenReturn(1);

        controller.doGet(request,response);
    }
}
