package it.hotel.controller.api;

import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
/**
 * <h1>Controllo prenotazione in sospeso</h1>
 * API per verificare se il cliente ha gia una prenotazione in sospeso
 * @author Sais Raffaele
 * @version 1.0
 * @since 2022-02-02
 */
@WebServlet(name = "CheckPrenoSospesa", value = "/api/CheckPrenoSospesa")
public class CheckPrenoSospesa extends CheckServlet
{
    /**
     * Richiesta che riceve i dati del cliente
     * e controlla se ha una prenotazione in sospeso
     * @param request Richiesta del cliente
     * @param response Risposta per inviare il JSON
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        JSONObject obj=new JSONObject();
        if(contieneParametro(request,"idUtente") && contieneParametro(request,"token"))
            try
            {
                int idUtente=Integer.parseInt(request.getParameter("idUtente"));
                String token=request.getParameter("token");
                UtenteService utenteService=new UtenteService();
                utenteService.doLogin(idUtente,token);
                PrenotazioneStanzaService stanzaService=new PrenotazioneStanzaService();
                boolean hasPrenoInCorso=stanzaService.selectBy(idUtente, PrenotazioneStanzaDAO.UTENTE).stream().anyMatch(p->p.getKsStato()==1);
                obj.put("Ris",1);
                obj.put("InCorso",hasPrenoInCorso);
                response.getOutputStream().print(obj.toString());
            }
            catch(NumberFormatException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Inserisci i dati correttamente");
                response.getOutputStream().print(obj.toString());
            }
            catch (UtenteNotFoundException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Accesso negato");
                response.getOutputStream().print(obj.toString());
            }
        else
        {
            obj.put("Ris",0);
            obj.put("Mess","Inserisci tutti i parametri");
            response.getOutputStream().print(obj.toString());
        }
    }
}
