package it.hotel.controller;

import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

/**
 * <h1>Anagrafica Servlet</h1>
 * Servlet che visualizza le informazioni dell'utente
 * @author Sais Raffaele
 * @version 1.2
 * @since 2022-01-11
 */
@WebServlet(name = "Anagrafica", value = "/Anagrafica")
public class AnagraficaServlet extends CheckServlet
{
    /**
     * Richiede di visualizzare la pagina di Anagrafica
     * @param request Richiesta del cliente
     * @param response Visualizza la pagina con i dati dell'utente
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
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("/WEB-INF/views/Anagrafica.jsp");
            request.setAttribute("Tipo",1);
            requestDispatcher.forward(request,response);
        }
    }
}
