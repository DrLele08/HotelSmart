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
        RequestDispatcher rd;
        HttpSession session=request.getSession();
        Utente us=(Utente)session.getAttribute(Utility.SESSION_USER);
        if(us==null)
        {
            rd = request.getRequestDispatcher("./");
        }
        else
        {
            rd=request.getRequestDispatcher("/WEB-INF/views/StoricoPrenotazioni.jsp");
            PrenotazioneStanzaDAO dao=new PrenotazioneStanzaDAO();
            List<PrenotazioneStanza> list = null;
            if(us.getRuolo()==3)
            {
                list=dao.doSelectBy(us.getIdUtente(),PrenotazioneStanzaDAO.UTENTE);
            }
            else if(us.getRuolo()== 1 || us.getRuolo()==2)
            {
                list=dao.doGetAll();
            }
            request.setAttribute("ListaPreno",list);

        }rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
