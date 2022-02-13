package it.hotel.controller.api;

import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.PrenotazioneServizioService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
/**
 * <h1>Eliminazione di un servizio prenotato</h1>
 * API per eliminare una prenotazione di un servizio effettuata da un cliente
 * @author Sais Raffaele
 * @version 1.0
 * @since 2022-01-17
 */
@WebServlet(name = "EliminaServizioPrenotato", value = "/api/EliminaServizioPrenotato")
public class EliminaServizioPrenotato extends CheckServlet
{
    /**
     * Ritorna utente service
     * @see UtenteService
     */
    public UtenteService getUtenteService()
    {
        return new UtenteService();
    }

    /**
     * Ritorna prenotazione servizio service
     * @see PrenotazioneServizioService
     */
    public PrenotazioneServizioService getPrenoService()
    {
        return new PrenotazioneServizioService();
    }
    /**
     * Richiesta che riceve l'ID della prenotazione del servizio
     * e il token dell'utente per controllare se ha i permessi per l'operazione
     * @param request Richiesta del cliente
     * @param response Risposta per inviare il JSON
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        JSONObject obj=new JSONObject();
        if(contieneParametro(request,"idUtente") && contieneParametro(request,"Token") && contieneParametro(request,"idPrenoServizio"))
        {
            try
            {
                int idUtente=Integer.parseInt(request.getParameter("idUtente"));
                String token=request.getParameter("Token");
                int idPrenoServizio=Integer.parseInt(request.getParameter("idPrenoServizio"));
                UtenteService utenteService=getUtenteService();
                Utente user=utenteService.doLogin(idUtente,token);
                if(user.getRuolo()==1 || user.getRuolo()==2)
                {
                    PrenotazioneServizioService prenoServizioService=getPrenoService();
                    prenoServizioService.deletePrenotazioneById(idPrenoServizio);
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
            catch(NumberFormatException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Inserisci tutti i parametri correttamente");
                response.getOutputStream().print(obj.toString());
            }
            catch (UtenteNotFoundException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Utente non trovato");
                response.getOutputStream().print(obj.toString());
            }
        }
        else
        {
            obj.put("Ris",0);
            obj.put("Mess","Inserisci tutti i parametri");
            response.getOutputStream().print(obj.toString());
        }
    }
}
