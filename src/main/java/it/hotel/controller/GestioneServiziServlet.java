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

@WebServlet(name = "GestioneServizi", value = "/GestioneServizi")
public class GestioneServiziServlet extends CheckServlet
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
            Utente user=us.get();
            ServizioService service=new ServizioService();
            List<Servizio> listServizi;
            if(user.getRuolo()==3)
                listServizi=service.getByUser(user.getIdUtente());
            else
                listServizi=service.getAll();
            request.setAttribute("Servizi",listServizi);
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("/WEB-INF/views/GestioneServizi.jsp");
            request.setAttribute("Tipo",6);
            requestDispatcher.forward(request,response);
        }
    }
}
