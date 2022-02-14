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
/**
 * <h1>Gestione Stanze Servlet</h1>
 * Servlet che visualizza la pagine di Gestione Stanze
 * @author Sais Raffaele
 * @version 1.0
 * @since 2022-01-16
 */
@WebServlet(name = "GestioneStanze", value = "/GestioneStanze")
public class GestioneStanzeServlet extends CheckServlet
{

    public StanzaService getStanzaService()
    {
        return new StanzaService();
    }

    /**
     * Richiede di visualizzare la pagina di gestione stanze
     * @param request Richiesta del cliente
     * @param response Visualizza la pagina con i dati delle stanze
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Optional<Utente> us=getUtente(request);
        if(us.isPresent() && (us.get().getRuolo()==1 || us.get().getRuolo()==2))
        {
            StanzaService stanze=getStanzaService();
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
}
