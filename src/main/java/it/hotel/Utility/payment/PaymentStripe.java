package it.hotel.Utility.payment;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import it.hotel.Utility.Utilita;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.utente.Utente;

public class PaymentStripe implements PaymentAdapter
{

    @Override
    public String makePayment(PrenotazioneStanza preno,String link)
    {
        try
        {
            PrenotazioneStanzaService prenoService=new PrenotazioneStanzaService();
            Stripe.apiKey = Utilita.stripeKey;
            Long price=((Double)preno.getPrezzoFinale()).longValue()*100;
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(link+"true&id="+preno.getIdPrenotazioneStanza())
                            .setCancelUrl(link+"false&id="+-1)
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setCurrency("EUR")
                                            .setAmount(price)
                                            .setName("Pagamento prenotazione #"+preno.getIdPrenotazioneStanza())
                                            .build())
                            .build();
            Session session;
            session=Session.create(params);
            String idPagamento=session.getPaymentIntent();
            prenoService.addTokenStripe(preno.getIdPrenotazioneStanza(),idPagamento);
            return session.getUrl();
        }
        catch(Exception e)
        {
            return "";
        }
    }

    @Override
    public boolean isPagato(String idPagamento)
    {
        try
        {
            Stripe.apiKey = Utilita.stripeKey;
            PaymentIntent paymentIntent=PaymentIntent.retrieve(idPagamento);
            return paymentIntent.getStatus().equals("succeeded");
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
