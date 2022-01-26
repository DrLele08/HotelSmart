package it.hotel.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaNotFoundException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PagamentoServlet", value = "/PagamentoServlet")
public class PagamentoServlet extends CheckServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(contieneParametro(request,"idPreno"))
        {
            try
            {
                int idPreno=Integer.parseInt(request.getParameter("idPreno"));
                PrenotazioneStanzaService prenoService=new PrenotazioneStanzaService();
                PrenotazioneStanza preno=prenoService.getPrenotazioneById(idPreno);
                String domain=request.getContextPath();
                String link="http://localhost:8080"+domain+"/confirmationPage.jsp?success=";
                if(preno.getKsStato()==1)
                {
                    Stripe.apiKey = "sk_test_51KLDXkBGMwZsdNHVNexZB0QYRKoufGyY1XkvZqIvRUncWZIrTwuxFmWA2v9mfWkRHkrdzHmeQfFHsQGKHWu7SYvO00PAVrndqP";
                    Long price=((Double)preno.getPrezzoFinale()).longValue()*100;
                    SessionCreateParams params =
                            SessionCreateParams.builder()
                                    .setMode(SessionCreateParams.Mode.PAYMENT)
                                    .setSuccessUrl(link+"true&id="+preno.getIdPrenotazioneStanza())
                                    .setCancelUrl(link+"false")
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
                    prenoService.addTokenStripe(idPreno,idPagamento);
                    response.sendRedirect(session.getUrl());
                }
                else
                {
                    response.sendRedirect(link+"false");
                }
            }
            catch (Exception e)
            {
                response.getOutputStream().print(e.toString());
            }
        }
        else
        {
            response.sendRedirect("./");
        }
    }
}
