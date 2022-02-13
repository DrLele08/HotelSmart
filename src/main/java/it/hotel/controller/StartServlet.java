package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "StartServlet", value = "")
public class StartServlet extends CheckServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher requestDispatcher=request.getRequestDispatcher("WEB-INF/views/index.jsp");
        Optional<Utente> us=getUtente(request);
        if(us.isPresent())
        {
            HttpSession session=request.getSession(true);
            session.setAttribute(Utility.SESSION_USER,us.get());
        }
        requestDispatcher.forward(request,response);
    }
}
