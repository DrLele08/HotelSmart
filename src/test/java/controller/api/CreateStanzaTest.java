package controller.api;

import it.hotel.controller.api.CreateStanza;
import it.hotel.controller.services.StanzaService;
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

public class CreateStanzaTest extends Mockito
{
    private CreateStanza controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private JSONObject object;
    private UtenteService utenteService;
    private StanzaService stanzaService;
    private Utente user;
    @Before
    public void setUp()
    {
        controller=Mockito.spy(new CreateStanza());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        object=new JSONObject();
        utenteService=mock(UtenteService.class);
        stanzaService=mock(StanzaService.class);
        user=mock(Utente.class);
    }

    @Test
    public void testNonContieneParametri() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testTuttiIParametri() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"Animale");
        doReturn(true).when(controller).contieneParametro(request,"Fumatore");
        doReturn(true).when(controller).contieneParametro(request,"LettiS");
        doReturn(true).when(controller).contieneParametro(request,"LettiM");
        doReturn(true).when(controller).contieneParametro(request,"Costo");
        doReturn(true).when(controller).contieneParametro(request,"Sconto");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri correttamente");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testNessunParametro() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(false).when(controller).contieneParametro(request,"idUtente");
        doReturn(false).when(controller).contieneParametro(request,"Token");
        doReturn(false).when(controller).contieneParametro(request,"Animale");
        doReturn(false).when(controller).contieneParametro(request,"Fumatore");
        doReturn(false).when(controller).contieneParametro(request,"LettiS");
        doReturn(false).when(controller).contieneParametro(request,"LettiM");
        doReturn(false).when(controller).contieneParametro(request,"Costo");
        doReturn(false).when(controller).contieneParametro(request,"Sconto");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testParametroNonIntero() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"Animale");
        doReturn(true).when(controller).contieneParametro(request,"Fumatore");
        doReturn(true).when(controller).contieneParametro(request,"LettiS");
        doReturn(true).when(controller).contieneParametro(request,"LettiM");
        doReturn(true).when(controller).contieneParametro(request,"Costo");
        doReturn(true).when(controller).contieneParametro(request,"Sconto");
        when(request.getParameter("idUtente")).thenReturn("asd");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri correttamente");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteNotFound() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"Animale");
        doReturn(true).when(controller).contieneParametro(request,"Fumatore");
        doReturn(true).when(controller).contieneParametro(request,"LettiS");
        doReturn(true).when(controller).contieneParametro(request,"LettiM");
        doReturn(true).when(controller).contieneParametro(request,"Costo");
        doReturn(true).when(controller).contieneParametro(request,"Sconto");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenThrow(new UtenteNotFoundException());
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Utente non valido");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteSenzaPermessi() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"Animale");
        doReturn(true).when(controller).contieneParametro(request,"Fumatore");
        doReturn(true).when(controller).contieneParametro(request,"LettiS");
        doReturn(true).when(controller).contieneParametro(request,"LettiM");
        doReturn(true).when(controller).contieneParametro(request,"Costo");
        doReturn(true).when(controller).contieneParametro(request,"Sconto");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenReturn(user);
        when(user.getRuolo()).thenReturn(0);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Non hai i permessi");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteConPermessi() throws Exception {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request, "idUtente");
        doReturn(true).when(controller).contieneParametro(request, "Token");
        doReturn(true).when(controller).contieneParametro(request, "Animale");
        doReturn(true).when(controller).contieneParametro(request, "Fumatore");
        doReturn(true).when(controller).contieneParametro(request, "LettiS");
        doReturn(true).when(controller).contieneParametro(request, "LettiM");
        doReturn(true).when(controller).contieneParametro(request, "Costo");
        doReturn(true).when(controller).contieneParametro(request, "Sconto");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(), anyString())).thenReturn(user);
        when(user.getRuolo()).thenReturn(1);
        when(request.getParameter("LettiS")).thenReturn("1");
        when(request.getParameter("LettiM")).thenReturn("1");
        when(request.getParameter("Costo")).thenReturn("1");
        when(request.getParameter("Sconto")).thenReturn("1");
        when(controller.getStanzaService()).thenReturn(stanzaService);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request, response);
        object.put("Ris", 1);
        object.put("Mess", "Fatto");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testNonContieneUnParametro() throws Exception
    {
        boolean b[] = new boolean[8];
        for (int i = 0; i < 8; i++) {
            b[i]=false;
            ServletOutputStream mockOutput = mock(ServletOutputStream.class);
            doReturn(b[0]).when(controller).contieneParametro(request, "idUtente");
            doReturn(b[1]).when(controller).contieneParametro(request, "Token");
            doReturn(b[2]).when(controller).contieneParametro(request, "Animale");
            doReturn(b[3]).when(controller).contieneParametro(request, "Fumatore");
            doReturn(b[4]).when(controller).contieneParametro(request, "LettiS");
            doReturn(b[5]).when(controller).contieneParametro(request, "LettiM");
            doReturn(b[6]).when(controller).contieneParametro(request, "Costo");
            doReturn(b[7]).when(controller).contieneParametro(request, "Sconto");
            when(response.getOutputStream()).thenReturn(mockOutput);
            controller.doPost(request, response);
            object.put("Ris", 0);
            object.put("Mess", "Inserisci tutti i parametri");
            Mockito.verify(mockOutput).print(object.toString());
            b[i] = true;
        }
    }

}
