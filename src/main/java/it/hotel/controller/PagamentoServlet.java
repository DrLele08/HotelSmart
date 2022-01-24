package it.hotel.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;

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
                //ToDo Giovanni
                PrenotazioneStanza preno=prenoService.getPrenoById(idPreno);
                Stripe.apiKey = "sk_test_51KLDXkBGMwZsdNHVNexZB0QYRKoufGyY1XkvZqIvRUncWZIrTwuxFmWA2v9mfWkRHkrdzHmeQfFHsQGKHWu7SYvO00PAVrndqP";
                String domain=request.getContextPath();
                SessionCreateParams params =
                        SessionCreateParams.builder()
                                .setMode(SessionCreateParams.Mode.PAYMENT)
                                .setSuccessUrl("https://www.google.it")
                                .setCancelUrl("https://www.google.it")
                                .addLineItem(
                                        SessionCreateParams.LineItem.builder()
                                                .setQuantity(1L)
                                                .setCurrency("EUR")
                                                .setAmount(Double.doubleToLongBits(preno.getPrezzoFinale())*100)
                                                .setName("Pagamento prenotazione #"+preno.getIdPrenotazioneStanza())
                                                .build())
                                .build();
                Session session;
                session=Session.create(params);
                response.sendRedirect(session.getUrl());
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
