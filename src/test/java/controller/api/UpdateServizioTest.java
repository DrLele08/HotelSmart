package controller.api;

import it.hotel.controller.api.UpdateServizio;
import it.hotel.controller.services.ServizioService;
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

public class UpdateServizioTest extends Mockito
{
    private UpdateServizio controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private JSONObject object;
    private UtenteService utenteService;
    private Utente user;
    private ServizioService servizioService;

    @Before
    public void setUp()
    {
        controller= Mockito.spy(new UpdateServizio());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        utenteService=mock(UtenteService.class);
        user=mock(Utente.class);
        servizioService=mock(ServizioService.class);
        object=new JSONObject();
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
    public void testParametroNonIntero() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idServizio");
        doReturn(true).when(controller).contieneParametro(request,"Nome");
        doReturn(true).when(controller).contieneParametro(request,"Descrizione");
        doReturn(true).when(controller).contieneParametro(request,"Prezzo");
        doReturn(true).when(controller).contieneParametro(request,"Posti");
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
        doReturn(true).when(controller).contieneParametro(request,"idServizio");
        doReturn(true).when(controller).contieneParametro(request,"Nome");
        doReturn(true).when(controller).contieneParametro(request,"Descrizione");
        doReturn(true).when(controller).contieneParametro(request,"Prezzo");
        doReturn(true).when(controller).contieneParametro(request,"Posti");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenThrow(new UtenteNotFoundException());
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Utente inesistente");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteSenzaPermessi() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idServizio");
        doReturn(true).when(controller).contieneParametro(request,"Nome");
        doReturn(true).when(controller).contieneParametro(request,"Descrizione");
        doReturn(true).when(controller).contieneParametro(request,"Prezzo");
        doReturn(true).when(controller).contieneParametro(request,"Posti");
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
    public void testUtenteConPermessi() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"idUtente");
        doReturn(true).when(controller).contieneParametro(request,"Token");
        doReturn(true).when(controller).contieneParametro(request,"idServizio");
        doReturn(true).when(controller).contieneParametro(request,"Nome");
        doReturn(true).when(controller).contieneParametro(request,"Descrizione");
        doReturn(true).when(controller).contieneParametro(request,"Prezzo");
        doReturn(true).when(controller).contieneParametro(request,"Posti");
        when(request.getParameter("idUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(utenteService.doLogin(anyInt(),anyString())).thenReturn(user);
        when(user.getRuolo()).thenReturn(1);
        when(request.getParameter("idServizio")).thenReturn("1");
        when(request.getParameter("Prezzo")).thenReturn("1");
        when(request.getParameter("Posti")).thenReturn("1");
        when(controller.getServizioService()).thenReturn(servizioService);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",1);
        object.put("Mess","Fatto");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testNonContieneUnParametro() throws Exception
    {
        boolean b[] = new boolean[7];
        for (int i = 0; i<7;i++) {
            b[i]=false;
            ServletOutputStream mockOutput = mock(ServletOutputStream.class);
            doReturn(b[0]).when(controller).contieneParametro(request, "idUtente");
            doReturn(b[1]).when(controller).contieneParametro(request, "Token");
            doReturn(b[2]).when(controller).contieneParametro(request, "idServizio");
            doReturn(b[3]).when(controller).contieneParametro(request, "Nome");
            doReturn(b[4]).when(controller).contieneParametro(request, "Descrizione");
            doReturn(b[5]).when(controller).contieneParametro(request, "Prezzo");
            doReturn(b[6]).when(controller).contieneParametro(request, "Posti");
            when(response.getOutputStream()).thenReturn(mockOutput);
            controller.doPost(request, response);
            object.put("Ris", 0);
            object.put("Mess", "Inserisci tutti i parametri");
            Mockito.verify(mockOutput).print(object.toString());
            b[i]=true;
        }
    }

}
