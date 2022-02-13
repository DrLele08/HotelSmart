package controller;

import it.hotel.controller.AnagraficaServlet;
import it.hotel.controller.StartServlet;
import it.hotel.model.utente.Utente;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class StartServletTest extends Mockito
{
    private StartServlet controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private HttpSession session;
    private Utente utente;
    @Before
    public void setUp()
    {
        controller=spy(new StartServlet());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        requestDispatcher=mock(RequestDispatcher.class);
        session=mock(HttpSession.class);
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
    public void testUtenteTrovato() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.of(utente)).when(controller).getUtente(request);
        when(request.getSession(true)).thenReturn(session);
        controller.doGet(request,response);
    }
}
