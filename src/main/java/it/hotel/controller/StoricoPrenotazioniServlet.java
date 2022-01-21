package it.hotel.controller;

import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.utente.Utente;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO.UTENTE;

@WebServlet(name = "StoricoPrenotazioni", value = "/StoricoPrenotazioni")
public class StoricoPrenotazioniServlet extends CheckServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher rd;
        Optional<Utente> us=getUtente(request,response);
        if(!us.isPresent())
        {
            rd = request.getRequestDispatcher("./");
        }
        else
        {
            rd=request.getRequestDispatcher("/WEB-INF/views/StoricoPrenotazioni.jsp");
            PrenotazioneStanzaService service = new PrenotazioneStanzaService();
            request.setAttribute("Tipo",3);
            List<PrenotazioneStanza> list = null;
            if(us.get().getRuolo()==3)
            {
                list=service.selectBy(us.get().getIdUtente(),UTENTE);
            }
            else if(us.get().getRuolo()== 1 || us.get().getRuolo()==2)
            {
                list=service.getAll();
            }
            request.setAttribute("ListaPreno",list);

        }
        rd.forward(request,response);
    }
}
