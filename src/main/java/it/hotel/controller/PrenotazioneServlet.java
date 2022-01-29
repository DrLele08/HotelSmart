package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.exception.PagamentoInAttesaException;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.StanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.EmailAlreadyExistingException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

@WebServlet(name = "Prenotazione", value = "/Prenotazione")
public class PrenotazioneServlet extends CheckServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Check Parametri
        try
        {
            Optional<Utente> userOpt=getUtente(request);
            Utente user=null;
            if(userOpt.isPresent())
                user=userOpt.get();
            else
            {
                if(canDoSignUp(request))
                {
                    String cf=request.getParameter("textCF");
                    String nome=request.getParameter("textNome");
                    String cognome=request.getParameter("textCognome");
                    String email=request.getParameter("textEmail");
                    String dataStr=request.getParameter("textNascita");
                    String pwd=request.getParameter("textPwd");
                    UtenteService utenteService=new UtenteService();
                    user=utenteService.doRegistrazione(cf,nome,cognome,email, Utility.dataConverter(dataStr),pwd);
                }
                else
                {
                    response.sendRedirect(request.getContextPath());
                }
            }
            int idStanza=Integer.parseInt(request.getParameter("idStanza"));
            StanzaService stanzaService=new StanzaService();
            //ToDo Giovanni
            if(stanzaService.isPrenotabile(idStanza,dataInizio,dataFine))
            {
                //Dati ospiti

                PrenotazioneStanzaService prenotazioneStanzaService=new PrenotazioneStanzaService();
                //ToDo Giovanni Come gestire i dati degli ospiti???
                PrenotazioneStanza preno=prenotazioneStanzaService.inserisciPrenotazione(user.getIdUtente(),idStanza,dataInizio,dataFine,totale,"aaa","aaa",-1);
                response.sendRedirect(request.getContextPath()+"/PagamentoServlet?idPreno="+preno.getIdPrenotazioneStanza());
            }
            else
            {
                response.sendRedirect(request.getContextPath());
            }
        }
        catch(ParseException | PagamentoInAttesaException | PasswordNotValidException | EmailAlreadyExistingException e)
        {
            response.sendRedirect(request.getContextPath());
        }
    }

    private boolean canDoSignUp(HttpServletRequest request)
    {
        return contieneParametro(request, "textCF") && contieneParametro(request, "textNome") && contieneParametro(request, "textCognome") && contieneParametro(request, "textEmail") && contieneParametro(request, "textNascita") && contieneParametro(request, "textPwd");
    }
}
