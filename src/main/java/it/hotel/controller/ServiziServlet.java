package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.PrenotazioneServizioService;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.ServizioService;
import it.hotel.controller.services.StanzaService;
import it.hotel.model.prenotazioneServizio.PrenotazioneServizioDAO;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "Servizi", value = "/servizi/*")

public class ServiziServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {

            case "/goservizi":
            {
                ServizioService service = new ServizioService();
                ArrayList<Servizio> servizi = (ArrayList<Servizio>) service.getAll();

                PrenotazioneStanza result = getActiveReservation(request);
                boolean has_active_reservation = false;
                if(result != null) has_active_reservation = true;

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

    public PrenotazioneStanza getActiveReservation(HttpServletRequest request){

        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute(Utility.SESSION_USER);
        PrenotazioneStanzaService service1 = new PrenotazioneStanzaService();
        PrenotazioneStanza result = null;

        if(user != null){

            ArrayList<PrenotazioneStanza> prenotazioni = (ArrayList<PrenotazioneStanza>) service1.selectBy(user.getIdUtente(), PrenotazioneStanzaDAO.UTENTE);
            for(PrenotazioneStanza p: prenotazioni){
                if(p.getStatoName().equals("IN CORSO")){
                    result = p;
                    break;
                }
            }
        }

        return result;
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

            case "/prenotaServizio": {

                int numero_ospiti = Integer.parseInt(request.getParameter("numero_ospiti"));
                int servizioId = Integer.parseInt(request.getParameter("servizioId"));
                java.sql.Date dataSql = null;
                String success = "FAIL";

                PrenotazioneStanza linked_reservation = getActiveReservation(request);

                try {

                    Date temp_data = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("data"));
                    dataSql =  new java.sql.Date(temp_data.getTime());

                    PrenotazioneServizioDAO service = new PrenotazioneServizioDAO();

                    service.doInsert(linked_reservation.getIdPrenotazioneStanza(),servizioId,numero_ospiti,dataSql);

                } catch (ParseException e) {
                    e.printStackTrace();
                    request.setAttribute("success",success);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/ServizioConfirmation.jsp");
                    dispatcher.forward(request,response);
                    break;
                }

                success = "OK";
                request.setAttribute("success",success);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/ServizioConfirmation.jsp");
                dispatcher.forward(request,response);
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