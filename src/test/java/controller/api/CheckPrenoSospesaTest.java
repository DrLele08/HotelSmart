package controller.api;

import it.hotel.controller.api.CheckPrenoSospesa;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;

public class CheckPrenoSospesaTest extends Mockito
{
    private CheckPrenoSospesa controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UtenteService utenteService;
    private PrenotazioneStanzaService prenotazioneStanzaService;
    private JSONObject object;
    @Before
    public void setUp()
    {
        controller=Mockito.spy(new CheckPrenoSospesa());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        utenteService=mock(UtenteService.class);
        prenotazioneStanzaService=mock(PrenotazioneStanzaService.class);
        object=new JSONObject();
    }

    @Test
    public void testNonContieneParametro() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri");
        verify(mockOutput).print(object.toString());
    }

    @Test
    public void testNonContieneParametro1() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(false).when(controller).contieneParametro(request,"token");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri");
        verify(mockOutput).print(object.toString());
    }

    @Test
    public void testNonContieneParametro2() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(false).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"token");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri");
        verify(mockOutput).print(object.toString());
    }

    @Test
    public void testParametroNonNumerico() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"token");
        when(request.getParameter("idUtente")).thenReturn("asd");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci i dati correttamente");
        verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteNonTrovato() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"token");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenThrow(new UtenteNotFoundException());
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Accesso negato");
        verify(mockOutput).print(object.toString());
    }

    @Test
    public void testPrenoNonInCorn() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"token");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(controller.getPrenoService()).thenReturn(prenotazioneStanzaService);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",1);
        object.put("InCorso",false);
        verify(mockOutput).print(object.toString());
    }

    @Test
    public void testPrenoNonInCorn1() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"token");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(controller.getPrenoService()).thenReturn(prenotazioneStanzaService);
        ArrayList<PrenotazioneStanza> lista = new ArrayList<>();
        lista.add(new PrenotazioneStanza(1,1,1,2, new Date(0), new Date(0),
                10.0, "tokenStripe", "tokenQr", "commenti", -1));
        when(prenotazioneStanzaService.selectBy(1, 0)).thenReturn(lista);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",1);
        object.put("InCorso",false);
        verify(mockOutput).print(object.toString());
    }

    @Test
    public void testPrenoInCorn() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"token");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(controller.getPrenoService()).thenReturn(prenotazioneStanzaService);
        ArrayList<PrenotazioneStanza> lista = new ArrayList<>();
        lista.add(new PrenotazioneStanza(1,1,1,1, new Date(0), new Date(0),
                10.0, "tokenStripe", "tokenQr", "commenti", -1));
        when(prenotazioneStanzaService.selectBy(1, 0)).thenReturn(lista);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",1);
        object.put("InCorso",true);
        verify(mockOutput).print(object.toString());
    }
}
