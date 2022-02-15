package controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.ParseException;
import java.util.Optional;

public class CheckServletTest extends Mockito
{
    private CheckServlet controller;
    private HttpServletRequest request;
    private HttpSession session;
    private UtenteService utenteService;
    private Utente us;

    @Before
    public void setUp()
    {
        controller=mock(CheckServlet.class, Mockito.CALLS_REAL_METHODS);
        request=mock(HttpServletRequest.class);
        session=mock(HttpSession.class);
        utenteService=mock(UtenteService.class);
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

    @Test
    public void testCookie() {
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(Utility.SESSION_USER)).thenReturn(null);
        Cookie c[] = new Cookie[2];
        c[0] = new Cookie("name", "value");
        c[1] = new Cookie("name", "value");
        when(request.getCookies()).thenReturn(c);
        Assert.assertNotNull(controller.getUtente(request));
    }

    @Test
    public void testCookie1() throws Exception {
        when(request.getSession(true)).thenReturn(session);
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(1,"value")).thenReturn(new Utente(
                1,1,"asdfghjklasdfghj","nome","cognome","email", new Date(0), "value")
        );
        when(session.getAttribute(Utility.SESSION_USER)).thenReturn(null);
        Cookie c[] = new Cookie[2];
        c[0] = new Cookie(Utility.COOKIE_ID, "1");
        c[1] = new Cookie(Utility.COOKIE_TOKEN, "value");
        when(request.getCookies()).thenReturn(c);
        Assert.assertNotNull(controller.getUtente(request));
    }

    @Test
    public void testCookieUtenteNotFoundException() throws Exception {
        when(request.getSession(true)).thenReturn(session);
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenThrow(new UtenteNotFoundException());
        when(session.getAttribute(Utility.SESSION_USER)).thenReturn(null);
        Cookie c[] = new Cookie[2];
        c[0] = new Cookie(Utility.COOKIE_ID, "1");
        c[1] = new Cookie(Utility.COOKIE_TOKEN, "value");
        when(request.getCookies()).thenReturn(c);
        Assert.assertNotNull(controller.getUtente(request));
    }

    @Test
    public void testCookieNumberFormatException() {
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(Utility.SESSION_USER)).thenReturn(null);
        Cookie c[] = new Cookie[2];
        c[0] = new Cookie(Utility.COOKIE_ID, "value");
        c[1] = new Cookie(Utility.COOKIE_TOKEN, "value");
        when(request.getCookies()).thenReturn(c);
        Assert.assertThrows(NumberFormatException.class, ()->controller.getUtente(request));
    }

}
