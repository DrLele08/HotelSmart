package it.hotel.controller.api;

import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.utenteExceptions.EmailAlreadyExistingException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.GregorianCalendar;

@WebServlet(name = "ModificaAnagrafica", value = "/api/ModificaAnagrafica")
public class ModificaAnagrafica extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String textNome=request.getParameter("textNome");
        String textCognome=request.getParameter("textCognome");
        String textCodiceFiscale=request.getParameter("textCodiceFiscale");
        String textDataNascita=request.getParameter("textDataNascita");
        String textEmail=request.getParameter("textEmail");

        //recuperare idUtente, e dati da dataNascita;
        int idUtente = 0, anno = 2000, mese = 1, giorno = 1;

        GregorianCalendar gc = new GregorianCalendar(anno, mese - 1, giorno);
        Date data = new Date(gc.getTimeInMillis());
        UtenteService service = new UtenteService();
        try {
            service.editAnagrafica(idUtente, textNome, textCognome, textCodiceFiscale, data, textEmail);
        } catch (EmailAlreadyExistingException e) {
            //l'email già esiste
        }

    }
}
