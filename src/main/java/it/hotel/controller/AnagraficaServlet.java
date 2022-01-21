package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "Anagrafica", value = "/Anagrafica")
public class AnagraficaServlet extends CheckServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Optional<Utente> us=getUtente(request,response);
        if(!us.isPresent())
        {
            response.sendRedirect("./");
        }
        else
        {
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("/WEB-INF/views/Anagrafica.jsp");
            request.setAttribute("Tipo",1);
            requestDispatcher.forward(request,response);
        }
    }
}
