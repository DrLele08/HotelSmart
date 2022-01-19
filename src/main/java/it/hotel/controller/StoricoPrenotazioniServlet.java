package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO;
import it.hotel.model.utente.Utente;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StoricoPrenotazioni", value = "/StoricoPrenotazioni")
public class StoricoPrenotazioniServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        Utente us=(Utente)session.getAttribute(Utility.SESSION_USER);
        if(us==null)
        {
            response.sendRedirect("./");
        }
        else
        {
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("WEB-INF/StoricoPrenotazioni.jsp");
            //TODO Da modificare
            PrenotazioneStanzaDAO dao=new PrenotazioneStanzaDAO();
            List<PrenotazioneStanza> list=dao.doSelectBy(us.getIdUtente(),PrenotazioneStanzaDAO.UTENTE);
            request.setAttribute("ListaPreno",list);
            requestDispatcher.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
