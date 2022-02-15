package controller;

import it.hotel.controller.LogoutServlet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServletTest extends Mockito {

    private LogoutServlet controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private HttpSession session;

    @Before
    public void setUp()
    {
        controller=spy(new LogoutServlet());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        requestDispatcher=mock(RequestDispatcher.class);
        session=mock(HttpSession.class);
    }

    @Test
    public void testFine() throws Exception {
        doReturn(session).when(controller).getSession(request);
        controller.doGet(request, response);
    }

    @Test
    public void testNullPointerException() {
        doReturn(null).when(controller).getSession(request);
        Assert.assertThrows(NullPointerException.class,
                ()->controller.doGet(request, response));
    }

    @Test
    public void testGetSession() {
        Assert.assertNull(controller.getSession(request));
    }

}
