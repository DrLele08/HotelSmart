package controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.CheckServlet;
import it.hotel.model.utente.Utente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CheckServletTest extends Mockito
{
    private CheckServlet controller;
    private HttpServletRequest request;
    private HttpSession session;
    private Utente us;

    @Before
    public void setUp()
    {
        controller=mock(CheckServlet.class, Mockito.CALLS_REAL_METHODS);
        request=mock(HttpServletRequest.class);
        session=mock(HttpSession.class);
        us=mock(Utente.class);
    }

    @Test
    public void testUtenteInSessione()
    {
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(Utility.SESSION_USER)).thenReturn(us);
        Assert.assertEquals(controller.getUtente(request), Optional.of(us));
    }

    @Test
    public void testUtenteNonTrovato()
    {
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(Utility.SESSION_USER)).thenReturn(null);
        Assert.assertEquals(controller.getUtente(request), Optional.empty());
    }
}
