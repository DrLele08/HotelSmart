package controller;

import it.hotel.controller.RegistrazioneServlet;
import it.hotel.model.utente.Utente;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrazioneServletTest extends Mockito
{
    private RegistrazioneServlet controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher requestDispatcher;
    private Utente utente;
    @Before
    public void setUp()
    {
        controller=spy(new RegistrazioneServlet());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        requestDispatcher=mock(RequestDispatcher.class);
        session=mock(HttpSession.class);
        utente=mock(Utente.class);
    }

    @Test
    public void testViewDisabled() throws Exception
    {
        doReturn(false).when(controller).canOpen(it.hotel.Utility.Utilita.CHECK_SIGNUP);
        controller.doGet(request,response);
    }

    @Test
    public void testViewAlreadyLogged() throws Exception
    {
        doReturn(true).when(controller).canOpen(it.hotel.Utility.Utilita.CHECK_SIGNUP);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(it.hotel.Utility.Utilita.SESSION_USER)).thenReturn(utente);
        controller.doGet(request,response);
    }

    @Test
    public void testViewOk() throws Exception
    {
        doReturn(true).when(controller).canOpen(it.hotel.Utility.Utilita.CHECK_SIGNUP);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(it.hotel.Utility.Utilita.SESSION_USER)).thenReturn(null);
        controller.doGet(request,response);
    }


}
