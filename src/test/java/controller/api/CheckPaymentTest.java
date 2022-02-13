package controller.api;

import com.stripe.model.PaymentIntent;
import it.hotel.controller.api.CheckPayment;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.prenotazioneStanza.prenotazioneStanzaException.PrenotazioneStanzaNotFoundException;
import it.hotel.model.utente.Utente;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CheckPaymentTest extends Mockito
{
    private CheckPayment controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrenotazioneStanzaService prenotazioneStanzaService;
    private PrenotazioneStanza prenotazioneStanza;
    private PaymentIntent paymentIntent;
    private UtenteService utenteService;
    private Utente utente;
    private JSONObject object;

    @Before
    public void setUp()
    {
        controller=Mockito.spy(new CheckPayment());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        prenotazioneStanzaService=mock(PrenotazioneStanzaService.class);
        prenotazioneStanza=mock(PrenotazioneStanza.class);
        paymentIntent=mock(PaymentIntent.class);
        utenteService=mock(UtenteService.class);
        utente=mock(Utente.class);
        object=new JSONObject();
    }


    @Test
    public void testParametroMancante() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testParametroNonNumerico() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idPreno");
        when(request.getParameter("idPreno")).thenReturn("asd");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri correttamente");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testPrenotazioneNonTrovata() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idPreno");
        when(request.getParameter("idPreno")).thenReturn("999");
        when(response.getOutputStream()).thenReturn(mockOutput);
        when(controller.getPrenoStanzaService()).thenReturn(prenotazioneStanzaService);
        when(prenotazioneStanzaService.getPrenotazioneById(anyInt()))
                .thenThrow(new PrenotazioneStanzaNotFoundException());
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Prenotazione non trovata");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testPrenotazioneNonPagabile() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idPreno");
        when(request.getParameter("idPreno")).thenReturn("999");
        when(response.getOutputStream()).thenReturn(mockOutput);
        when(controller.getPrenoStanzaService()).thenReturn(prenotazioneStanzaService);
        when(prenotazioneStanzaService.getPrenotazioneById(anyInt()))
                .thenReturn(prenotazioneStanza);
        when(prenotazioneStanza.getKsStato()).thenReturn(-1);
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Operazione non possibile");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testPagamentoNonEffettuato() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idPreno");
        when(request.getParameter("idPreno")).thenReturn("999");
        when(response.getOutputStream()).thenReturn(mockOutput);
        when(controller.getPrenoStanzaService()).thenReturn(prenotazioneStanzaService);
        when(prenotazioneStanzaService.getPrenotazioneById(anyInt()))
                .thenReturn(prenotazioneStanza);
        when(prenotazioneStanza.getKsStato()).thenReturn(1);
        doReturn(paymentIntent).when(controller).getPaymentIntent(anyString());
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.getUtenteByPrenotazioneStanza(anyInt())).thenReturn(utente);
        when(paymentIntent.getStatus()).thenReturn("error");
        when(utente.getNome()).thenReturn("Testing");
        when(utente.getEmail()).thenReturn("saisraffaele08@gmail.con");
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Errore durante il pagamento");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testPagamentoEffettuato() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idPreno");
        when(request.getParameter("idPreno")).thenReturn("999");
        when(response.getOutputStream()).thenReturn(mockOutput);
        when(controller.getPrenoStanzaService()).thenReturn(prenotazioneStanzaService);
        when(prenotazioneStanzaService.getPrenotazioneById(anyInt()))
                .thenReturn(prenotazioneStanza);
        when(prenotazioneStanza.getKsStato()).thenReturn(1);
        doReturn(paymentIntent).when(controller).getPaymentIntent(anyString());
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.getUtenteByPrenotazioneStanza(anyInt())).thenReturn(mock(Utente.class));
        when(utenteService.getUtenteByPrenotazioneStanza(anyInt())).thenReturn(utente);
        when(paymentIntent.getStatus()).thenReturn("succeeded");
        when(utente.getNome()).thenReturn("Testing");
        when(utente.getEmail()).thenReturn("saisraffaele08@gmail.con");
        controller.doGet(request,response);
        object.put("Ris",1);
        object.put("Mess","Fatto");
        Mockito.verify(mockOutput).print(object.toString());
    }
}
