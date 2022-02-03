package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.ServizioService;
import it.hotel.controller.services.StanzaService;
import it.hotel.controller.services.StatoService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO;
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
import java.util.ArrayList;

@WebServlet(name = "Servizi", value = "/servizi/*")

public class ServiziServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {

            case "/goservizi":
            {

                HttpSession session = request.getSession();

                ServizioService service = new ServizioService();
                ArrayList<Servizio> servizi = (ArrayList<Servizio>) service.getAll();
                PrenotazioneStanzaService service1 = new PrenotazioneStanzaService();
                Utente user = (Utente) session.getAttribute(Utility.SESSION_USER);

                boolean has_active_reservation = false;

                if(user != null){

                    ArrayList<PrenotazioneStanza> prenotazioni = (ArrayList<PrenotazioneStanza>) service1.selectBy(user.getIdUtente(), PrenotazioneStanzaDAO.UTENTE);
                    for(PrenotazioneStanza p: prenotazioni){
                        /* String stato = new StatoService().getById(p.getKsStato());
                            if stato.equals("IN CORSO")
                         */
                        if(p.getStatoName().equals("IN CORSO")){
                            has_active_reservation = true;
                        }
                    }
                }

                String active_link = "servizi";
                request.setAttribute("has_active_reservation",has_active_reservation);
                request.setAttribute("servizi",servizi);
                request.setAttribute("active", active_link);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/servizi.jsp");
                dispatcher.forward(request, response);
                break;

            }

            default:
            {
                response.sendRedirect(request.getContextPath());
                break;
            }

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {

            case "/servizioDetailForm": {

                String temp = request.getParameter("servizioId");
                int servizioId = Integer.parseInt(temp);
                ServizioService service = new ServizioService();
                Servizio s = service.getById(servizioId);

                String active_link = "servizi";
                request.setAttribute("servizio",s);
                request.setAttribute("active", active_link);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/ServizioDetailForm.jsp");
                requestDispatcher.forward(request,response);
                break;
            }

            default:
            {
                response.sendRedirect(request.getContextPath());
                break;
            }
        }
    }
}