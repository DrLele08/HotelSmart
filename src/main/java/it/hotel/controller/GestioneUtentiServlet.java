package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "GestioneUtenti", value = "/GestioneUtenti")
public class GestioneUtentiServlet extends CheckServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Optional<Utente> us=getUtente(request,response);
        if(us.isPresent() && (us.get().getRuolo()==1 || us.get().getRuolo()==2))
        {
            UtenteService service=new UtenteService();
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
