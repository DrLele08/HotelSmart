package controller.api;

import it.hotel.controller.api.ModificaPermessi;
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

public class ModificaPermessiTest extends Mockito
{
    private ModificaPermessi controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UtenteService utenteService;
    private Utente utente;
    private JSONObject object;

    @Before
    public void setUp()
    {
        controller=Mockito.spy(new ModificaPermessi());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        utenteService=mock(UtenteService.class);
        utente=mock(Utente.class);
        object=new JSONObject();
    }

    @Test
    public void testParametroMancante() throws Exception
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
        doReturn(true).when(controller).contieneParametro(request,"idUtenteCambio");
        doReturn(true).when(controller).contieneParametro(request,"idRuolo");
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
        doReturn(true).when(controller).contieneParametro(request,"idUtenteCambio");
        doReturn(true).when(controller).contieneParametro(request,"idRuolo");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(request.getParameter("idUtenteCambio")).thenReturn("1");
        when(request.getParameter("idRuolo")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenThrow(new UtenteNotFoundException());
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Non hai i permessi");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteSenzaPermessi() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idUtenteCambio");
        doReturn(true).when(controller).contieneParametro(request,"idRuolo");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(request.getParameter("idUtenteCambio")).thenReturn("1");
        when(request.getParameter("idRuolo")).thenReturn("1");
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
    public void testUtenteOk() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idUtenteCambio");
        doReturn(true).when(controller).contieneParametro(request,"idRuolo");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(request.getParameter("idUtenteCambio")).thenReturn("1");
        when(request.getParameter("idRuolo")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenReturn(utente);
        when(utente.getRuolo()).thenReturn(1);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",1);
        object.put("Mess","Fatto");
        Mockito.verify(mockOutput).print(object.toString());
    }
}
