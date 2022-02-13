package it.hotel.controller;

import it.hotel.controller.services.ServizioService;
import it.hotel.model.servizio.Servizio;
import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
/**
 * <h1>Gestione Stanze Servlet</h1>
 * Servlet che visualizza la pagine di Gestione Servizi
 * @author Sais Raffaele
 * @version 1.1
 * @since 2022-01-17
 */
@WebServlet(name = "GestioneServizi", value = "/GestioneServizi")
public class GestioneServiziServlet extends CheckServlet
{
    public ServizioService getServizioService()
    {
        return new ServizioService();
    }
    /**
     * Richiede di visualizzare la pagina di gestione servizi
     * @param request Richiesta del cliente
     * @param response Visualizza la pagina con i dati dei servizi
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Optional<Utente> us=getUtente(request);
        if(!us.isPresent())
        {
            response.sendRedirect("./");
        }
        else
        {
            Utente user=us.get();
            ServizioService service=getServizioService();
            List<Servizio> listServizi;
            if(user.getRuolo()==3)
                response.sendRedirect(request.getContextPath());
            else
            {
                listServizi=service.getAll();
                request.setAttribute("Servizi",listServizi);
                RequestDispatcher requestDispatcher=request.getRequestDispatcher("/WEB-INF/views/GestioneServizi.jsp");
                request.setAttribute("Tipo",6);
                requestDispatcher.forward(request,response);
            }
        }
    }
}
