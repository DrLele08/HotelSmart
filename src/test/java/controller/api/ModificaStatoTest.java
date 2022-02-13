package controller.api;

import it.hotel.controller.api.ModificaStato;
import it.hotel.controller.services.PrenotazioneStanzaService;
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
import java.sql.SQLException;

public class ModificaStatoTest extends Mockito
{
    private ModificaStato controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletOutputStream mockOutput;
    private PrenotazioneStanzaService prenotazioneStanzaService;
    private UtenteService utenteService;
    private Utente utente;
    private JSONObject object;

    @Before
    public void setUp()
    {
        controller=spy(new ModificaStato());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        mockOutput=mock(ServletOutputStream.class);
        prenotazioneStanzaService=mock(PrenotazioneStanzaService.class);
        utenteService=mock(UtenteService.class);
        utente=mock(Utente.class);
        object=new JSONObject();
    }

    @Test
    public void testNonContieneParametri() throws Exception
    {
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Non hai inserito tutti i parametri");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testNonContieneUnParametro() throws Exception
    {
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idPreno");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Non hai inserito tutti i parametri");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testParametroNonNumerico() throws Exception
    {
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idPreno");
        doReturn(true).when(controller).contieneParametro(request,"Stato");
        when(request.getParameter("idUtente")).thenReturn("asd");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci i parametri correttamente");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteNonTrovato() throws Exception
    {
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idPreno");
        doReturn(true).when(controller).contieneParametro(request,"Stato");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(request.getParameter("idPreno")).thenReturn("1");
        when(request.getParameter("Stato")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(controller.getPrenotazioneStanzaService()).thenReturn(prenotazioneStanzaService);
        when(utenteService.doLogin(anyInt(),anyString())).thenThrow(new UtenteNotFoundException());
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Utente non valido");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testPermessoNegato() throws Exception
    {
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idPreno");
        doReturn(true).when(controller).contieneParametro(request,"Stato");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(request.getParameter("idPreno")).thenReturn("1");
        when(request.getParameter("Stato")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(controller.getPrenotazioneStanzaService()).thenReturn(prenotazioneStanzaService);
        when(utenteService.doLogin(anyInt(),anyString())).thenReturn(utente);
        when(utente.getRuolo()).thenReturn(3);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Non hai i permessi");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testEsitoPositivo() throws Exception
    {
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idPreno");
        doReturn(true).when(controller).contieneParametro(request,"Stato");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(request.getParameter("idPreno")).thenReturn("1");
        when(request.getParameter("Stato")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(controller.getPrenotazioneStanzaService()).thenReturn(prenotazioneStanzaService);
        when(utenteService.doLogin(anyInt(),anyString())).thenReturn(utente);
        when(utente.getRuolo()).thenReturn(1);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",1);
        object.put("Mess","Fatto");
        Mockito.verify(mockOutput).print(object.toString());
    }
}
