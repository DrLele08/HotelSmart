package it.hotel.controller;

import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.Utility.payment.PaymentAdapter;
import it.hotel.Utility.payment.PaymentStripe;

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
                    PaymentAdapter pay=new PaymentStripe();
                    String urlPay=pay.makePayment(preno,link);
                    response.sendRedirect(urlPay);
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
