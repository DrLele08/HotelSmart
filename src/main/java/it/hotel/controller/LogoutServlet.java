package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Logout", value = "/Logout")
public class LogoutServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        session.removeAttribute(Utility.SESSION_USER);
        Cookie c1 = new Cookie(Utility.COOKIE_ID,"");
        c1.setMaxAge(0);
        Cookie c2 = new Cookie(Utility.COOKIE_TOKEN,"");
        c2.setMaxAge(0);
        response.addCookie(c1);
        response.addCookie(c2);
        response.sendRedirect("./");
    }
}
