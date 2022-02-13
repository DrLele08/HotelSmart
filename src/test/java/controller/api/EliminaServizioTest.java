package controller.api;

import it.hotel.controller.api.EliminaServizioPrenotato;
import it.hotel.controller.services.PrenotazioneServizioService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EliminaServizioTest extends Mockito
{
    private EliminaServizioPrenotato controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UtenteService utenteService;
    private PrenotazioneServizioService prenotazioneServizioService;
    private Utente utente;
    private JSONObject object;

    @Before
    public void setUp()
    {
        controller=spy(new EliminaServizioPrenotato());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        utenteService=mock(UtenteService.class);
        prenotazioneServizioService=mock(PrenotazioneServizioService.class);
        utente=mock(Utente.class);
        object=new JSONObject();
    }

    @Test
    public void testParametriNonInseriti() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testParametroNonNumerico() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idPrenoServizio");
        when(request.getParameter("idUtente")).thenReturn("asd");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri correttamente");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteNonTrovato() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idPrenoServizio");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(request.getParameter("idPrenoServizio")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenThrow(new UtenteNotFoundException());
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Utente non trovato");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteSenzaPermesso() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idPrenoServizio");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(request.getParameter("idPrenoServizio")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenReturn(utente);
        when(utente.getRuolo()).thenReturn(3);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Non hai i permessi");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteConPermessi() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idPrenoServizio");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(request.getParameter("idPrenoServizio")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenReturn(utente);
        when(utente.getRuolo()).thenReturn(1);
        when(controller.getPrenoService()).thenReturn(prenotazioneServizioService);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",1);
        object.put("Mess","Fatto");
        Mockito.verify(mockOutput).print(object.toString());
    }
}
