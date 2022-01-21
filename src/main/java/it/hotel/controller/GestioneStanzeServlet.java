package it.hotel.controller;

import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "GestioneStanze", value = "/GestioneStanze")
public class GestioneStanzeServlet extends CheckServlet
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
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("/WEB-INF/views/GestioneStanze.jsp");
            request.setAttribute("Tipo",5);
            requestDispatcher.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
