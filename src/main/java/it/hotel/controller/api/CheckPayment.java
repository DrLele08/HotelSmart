package it.hotel.controller.api;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import it.hotel.Utility.Email;
import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaNotFoundException;
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
                String tokenStripe=preno.getTokenStripe();
                PaymentIntent paymentIntent=PaymentIntent.retrieve(tokenStripe);
                String textHtml="Il pagamento per la prenotazione #<NUMERO> è stato ricevuto con successo!<br>La aspettiamo, HotelSmart!";
                Email.sendAsHtml("saisraffaele08@gmail.com","[HotelSmart] Pagamento confermato ordine #1",textHtml);
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
            } catch (StripeException e)
            {
                object.put("Ris",0);
                object.put("Mess","Pagamento non trovato");
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
