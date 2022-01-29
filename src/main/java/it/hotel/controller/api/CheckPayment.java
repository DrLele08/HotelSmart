package it.hotel.controller.api;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import it.hotel.Utility.Email;
import it.hotel.Utility.Utility;
import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaNotFoundException;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;

import javax.mail.MessagingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CheckPayment", value = "/api/CheckPayment")
public class CheckPayment extends CheckServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        JSONObject object=new JSONObject();
        if(contieneParametro(request,"idPreno"))
        {
            try
            {
                int idPreno=Integer.parseInt(request.getParameter("idPreno"));
                PrenotazioneStanzaService stanzaService=new PrenotazioneStanzaService();
                PrenotazioneStanza preno=stanzaService.getPrenotazioneById(idPreno);
                if(preno.getKsStato()==1)
                {
                    String tokenStripe=preno.getTokenStripe();
                    Stripe.apiKey = Utility.stripeKey;
                    PaymentIntent paymentIntent=PaymentIntent.retrieve(tokenStripe);
                    UtenteService utenteService=new UtenteService();
                    Utente user=utenteService.getUtenteByPrenotazioneStanza(idPreno);
                    if(paymentIntent.getStatus().equals("succeeded"))
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
            catch(MessagingException e)
            {
                object.put("Ris",1);
                object.put("Mess","Fatto, errore durante l'invio della email");
                response.getOutputStream().print(object.toString());
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
            catch (StripeException e)
            {
                object.put("Ris",0);
                object.put("Mess","Pagamento non trovato");
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
