package it.hotel.controller.api;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import it.hotel.Utility.Email;
import it.hotel.Utility.payment.PaymentAdapter;
import it.hotel.Utility.payment.PaymentStripe;
import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaNotFoundException;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
/**
 * <h1>Controllo del pagamento</h1>
 * API per verificare il pagamento di una prenotazione
 * @author Sais Raffaele
 * @version 1.3
 * @since 2022-01-30
 */
@WebServlet(name = "CheckPayment", value = "/api/CheckPayment")
public class CheckPayment extends CheckServlet
{
    /**
     * Metodo che ritorna un PaymentStripe
     */
    public PaymentStripe getPaymentStripe()
    {
        return new PaymentStripe();
    }
    /**
     * Ritorna il service della prenotazione stanza
     * @see PrenotazioneStanzaService
     */
    public PrenotazioneStanzaService getPrenoStanzaService()
    {
        return new PrenotazioneStanzaService();
    }
    /**
     * Richiesta che riceve la prenotazione
     * e controlla se il pagamento è stato effettuato
     * @param request Richiesta del cliente
     * @param response Risposta per inviare il JSON
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        JSONObject object=new JSONObject();
        if(contieneParametro(request,"idPreno"))
        {
            try
            {
                int idPreno=Integer.parseInt(request.getParameter("idPreno"));
                PrenotazioneStanzaService stanzaService=getPrenoStanzaService();
                PrenotazioneStanza preno=stanzaService.getPrenotazioneById(idPreno);
                if(preno.getKsStato()==1)
                {
                    UtenteService utenteService=getUtenteService();
                    Utente user=utenteService.getUtenteByPrenotazioneStanza(idPreno);
                    String tokenStripe=preno.getTokenStripe();
                    PaymentAdapter adapter=getPaymentStripe();
                    boolean isPagato=adapter.isPagato(tokenStripe);
                    if(isPagato)
                    {
                        stanzaService.editStato(idPreno,2);
                        stanzaService.generateQrCode(idPreno);
                        String textHtml="Ciao "+user.getNome()+"<br>Il pagamento per la prenotazione #"+preno.getIdPrenotazioneStanza()+" è stato ricevuto con successo!<br>La aspettiamo, HotelSmart!";
                        Email.sendAsHtml(user.getEmail(),"[HotelSmart] Pagamento confermato ordine #"+preno.getIdPrenotazioneStanza(),textHtml);
                        object.put("Ris",1);
                        object.put("Mess","Fatto");
                        response.getOutputStream().print(object.toString());
                    }
                    else
                    {
                        String textHtml="Ciao "+user.getNome()+"<br>Il pagamento per la prenotazione #<NUMERO> non è stato ricevuto!<br>Puo contattarci 24/24 alla mail: info@hotelsmart.it, HotelSmart!";
                        Email.sendAsHtml(user.getEmail(),"[HotelSmart] Errore pagamento ordine #"+preno.getIdPrenotazioneStanza(),textHtml);
                        object.put("Ris",0);
                        object.put("Mess","Errore durante il pagamento");
                        response.getOutputStream().print(object.toString());
                    }
                }
                else
                {
                    object.put("Ris",0);
                    object.put("Mess","Operazione non possibile");
                    response.getOutputStream().print(object.toString());
                }
            }
            catch(NumberFormatException e)
            {
                object.put("Ris",0);
                object.put("Mess","Inserisci tutti i parametri correttamente");
                response.getOutputStream().print(object.toString());
            }
            catch (PrenotazioneStanzaNotFoundException e)
            {
                object.put("Ris",0);
                object.put("Mess","Prenotazione non trovata");
                response.getOutputStream().print(object.toString());
            }
            catch (UtenteNotFoundException e)
            {
                object.put("Ris",0);
                object.put("Mess","Utente non trovato");
                response.getOutputStream().print(object.toString());
            }
        }
        else
        {
            object.put("Ris",0);
            object.put("Mess","Inserisci tutti i parametri");
            response.getOutputStream().print(object.toString());
        }
    }
}
