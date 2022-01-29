package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.PrenotazioneServizioService;
import it.hotel.controller.services.ServizioService;
import it.hotel.model.prenotazioneServizio.PrenotazioneServizio;
import it.hotel.model.servizio.Servizio;
import it.hotel.model.utente.Utente;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "StoricoServizi", value = "/StoricoServizi")
public class StoricoServiziServlet extends CheckServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher rd;
        Optional<Utente> us=getUtente(request,response);
        if(!us.isPresent())
        {
            rd=request.getRequestDispatcher("./");
        }
        else
        {
            PrenotazioneServizioService service = new PrenotazioneServizioService();
            //List<PrenotazioneServizio> prenotazioneServizio = service.getAll();
            //request.setAttribute("PrenotazioneServizi", prenotazioneServizio);
            request.setAttribute("Tipo",4);
            rd=request.getRequestDispatcher("/WEB-INF/views/StoricoServizi.jsp");
        }
        rd.forward(request, response);
    }

}
