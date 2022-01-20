package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StartServlet", value = "")
public class StartServlet extends CheckServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher requestDispatcher=request.getRequestDispatcher("WEB-INF/views/index.jsp");
        Utente us=getUtente(request,response);
        if(us != null)
        {
            HttpSession session=request.getSession(true);
            session.setAttribute(Utility.SESSION_USER,us);
        }
        requestDispatcher.forward(request,response);
    }
}
