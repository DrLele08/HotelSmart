package it.hotel.controller;

import it.hotel.Utility.Utility;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
/**
 * <h1>Logout Servlet</h1>
 * Servlet che effettua il logout
 * @author Alessandro d'Esposito
 * @version 1.0
 * @since 2022-01-4
 */
@WebServlet(name = "Logout", value = "/Logout")
public class LogoutServlet extends HttpServlet
{
    /**
     * Richiesta che elimina i cookie dell'utente
     * e lo rimuove dalla sessione
     * @param request Richiesta del cliente
     * @param response Visualizza la home page
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = getSession(request);
        session.removeAttribute(Utility.SESSION_USER);
        Cookie c1 = new Cookie(Utility.COOKIE_ID,"");
        c1.setMaxAge(0);
        Cookie c2 = new Cookie(Utility.COOKIE_TOKEN,"");
        c2.setMaxAge(0);
        response.addCookie(c1);
        response.addCookie(c2);
        response.sendRedirect("./");
    }

    public HttpSession getSession(HttpServletRequest request) {
        return request.getSession(true);
    }
}
