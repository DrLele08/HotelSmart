package it.hotel.controller;

import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * <h1>Gestione Utenti Servlet</h1>
 * Servlet che visualizza la pagine di Gestione Utenti
 * @author Sais Raffaele
 * @version 1.1
 * @since 2022-01-18
 */
@WebServlet(name = "GestioneUtenti", value = "/GestioneUtenti")
public class GestioneUtentiServlet extends CheckServlet
{
    public UtenteService getUtenteService()
    {
        return new UtenteService();
    }
    /**
     * Richiede di visualizzare la pagina di gestione utenti
     * @param request Richiesta del cliente
     * @param response Visualizza la pagina con i dati degli utenti
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Optional<Utente> us=getUtente(request);
        if(us.isPresent() && (us.get().getRuolo()==1 || us.get().getRuolo()==2))
        {
            UtenteService service=getUtenteService();
            List<Utente> listUser=service.getAll();
            request.setAttribute("Utenti",listUser);
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("/WEB-INF/views/GestioneUtenti.jsp");
            request.setAttribute("Tipo",2);
            requestDispatcher.forward(request,response);
        }
        else
        {
            response.sendRedirect("./");
        }
    }
}
