package it.hotel.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PagamentoServlet", value = "/PagamentoServlet")
public class PagamentoServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
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
                                        .setAmount(124L)
                                        .setName("Pagamento prenotazione #123")
                                        .build())
                        .build();
        Session session;
        try
        {
            session=Session.create(params);
            response.sendRedirect(session.getUrl());
        }
        catch (StripeException e)
        {
            e.printStackTrace();
        }
    }
}
