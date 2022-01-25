package it.hotel.controller.api;

import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaNotFoundException;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.EmailNotFoundException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ModificaStato", value = "/api/ModificaStato")
public class ModificaStato extends CheckServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        JSONObject obj=new JSONObject();
        if(contieneParametro(request,"idUtente") && contieneParametro(request,"Token")
                && contieneParametro(request,"idPreno") && contieneParametro(request,"Stato"))
        {
            try
            {
                String idUtente=request.getParameter("idUtente");
                String tokenAuth=request.getParameter("Token");
                int idPreno=Integer.parseInt(request.getParameter("idPreno"));
                int newStato=Integer.parseInt(request.getParameter("Stato"));
                UtenteService service=new UtenteService();
                Utente user=service.doLogin(idUtente,tokenAuth);
                PrenotazioneStanzaService serviceStanza=new PrenotazioneStanzaService();
                PrenotazioneStanza prenotazione=serviceStanza.getPrenotazioneById(idPreno);
                if(user.getRuolo()==1 || user.getRuolo()==2)
                {
                    serviceStanza.editStato(idPreno,newStato);
                    obj.put("Ris",1);
                    obj.put("Mess","Fatto");
                    response.getOutputStream().print(obj.toString());
                }
                else
                {
                    if(newStato==5 && prenotazione.getKsStato()==2)
                    {
                        //TODO GIOVANNI
                        if(serviceStanza.canRimborsare(idPreno))
                        {
                            serviceStanza.editStato(idPreno,5);
                            obj.put("Ris",1);
                            obj.put("Mess","Fatto");
                            response.getOutputStream().print(obj.toString());
                        }
                        else
                        {
                            obj.put("Ris",0);
                            obj.put("Mess","E' possibile richiedere il rimborso solo prima dei 14 giorni");
                            response.getOutputStream().print(obj.toString());
                        }
                    }
                    else if(newStato==6 && prenotazione.getKsStato()==1)
                    {
                        serviceStanza.editStato(idPreno,6);
                        obj.put("Ris",1);
                        obj.put("Mess","Fatto");
                        response.getOutputStream().print(obj.toString());
                    }
                    else
                    {
                        obj.put("Ris",0);
                        obj.put("Mess","Non hai i permessi");
                        response.getOutputStream().print(obj.toString());
                    }
                }
            }
            catch(NumberFormatException | PrenotazioneStanzaNotFoundException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Inserisci i parametri correttamente");
                response.getOutputStream().print(obj.toString());
            }
            catch (EmailNotFoundException | PasswordNotValidException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Utente non valido");
                response.getOutputStream().print(obj.toString());
            }
        }
        else
        {
            obj.put("Ris",0);
            obj.put("Mess","Non hai inserito tutti i parametri");
            response.getOutputStream().print(obj.toString());
        }
    }
}
