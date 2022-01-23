package it.hotel.controller;

import it.hotel.controller.services.StanzaService;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "GestioneStanze", value = "/GestioneStanze")
public class GestioneStanzeServlet extends CheckServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Optional<Utente> us=getUtente(request,response);
        if(us.isPresent() && (us.get().getRuolo()==1 || us.get().getRuolo()==2))
        {
            StanzaService stanze=new StanzaService();
            List<Stanza> listStanze=stanze.getStanze();
            request.setAttribute("Stanze",listStanze);
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("/WEB-INF/views/GestioneStanze.jsp");
            request.setAttribute("Tipo",5);
            requestDispatcher.forward(request,response);
        }
        else
        {
            response.sendRedirect("./");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
