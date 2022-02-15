package it.hotel.controller;

import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.personaAggiuntiva.PersonaAggiuntiva;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "Prenotazione", value = "/Prenotazione")
public class PrenotazioneServlet extends CheckServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            Utente user=null;
            if(canDoSignUp(request))
            {
                String cf=request.getParameter("codicef1");
                String nome=request.getParameter("nome1");
                String cognome=request.getParameter("cognome1");
                String email=request.getParameter("emailcliente");
                String dataStr=request.getParameter("dataNascita1");
                String pwd=request.getParameter("pwdcliente");
                UtenteService utenteService=new UtenteService();
                user=utenteService.doRegistrazione(cf,nome,cognome,email, it.hotel.Utility.Utilita.dataConverter(dataStr),pwd);
            }
            else
            {
                Optional<Utente> userOpt=getUtente(request);
                if(userOpt.isPresent())
                    user=userOpt.get();
            }
            if(user!=null)
            {
                int idStanza=Integer.parseInt(request.getParameter("id_stanza"));
                String dataInizio=request.getParameter("dataArrivo");
                String dataFine=request.getParameter("dataPartenza");
                List<PersonaAggiuntiva> listExtra=new ArrayList<>();
                int numPersone=Integer.parseInt(request.getParameter("num_persone"));
                if(numPersone>1)
                {
                    for(int index=2;index<=numPersone;index++)
                    {
                        if(contieneParametro(request,"nome"+index) && contieneParametro(request,"cognome"+index) &&
                                contieneParametro(request,"codicef"+index) && contieneParametro(request,"dataNascita"+index))
                        {
                            String nome=request.getParameter("nome"+index);
                            String cognome=request.getParameter("cognome"+index);
                            String cf=request.getParameter("codicef"+index);
                            String dataStr=request.getParameter("dataNascita"+index);
                            listExtra.add(new PersonaAggiuntiva(cf, nome, cognome, dataStr));
                        }
                    }
                }
                PrenotazioneStanzaService prenotazioneStanzaService=new PrenotazioneStanzaService();
                PrenotazioneStanza preno=prenotazioneStanzaService.inserisciPrenotazione(user.getIdUtente(),idStanza,dataInizio,dataFine, listExtra);
                response.sendRedirect(request.getContextPath()+"/PagamentoServlet?idPreno="+preno.getIdPrenotazioneStanza());
            }
            else
            {
                response.sendRedirect(request.getContextPath());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath());
        }
    }
    private boolean canDoSignUp(HttpServletRequest request)
    {
        return contieneParametro(request, "codicef1") && contieneParametro(request, "nome1") && contieneParametro(request, "cognome1") && contieneParametro(request, "emailcliente") && contieneParametro(request, "dataNascita1") && contieneParametro(request, "pwdcliente");
    }
}
