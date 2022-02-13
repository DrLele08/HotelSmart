package controller.api;

import it.hotel.controller.api.ModificaPermessi;
import it.hotel.controller.api.ModificaPwd;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModificaPwdTest extends Mockito
{
    private ModificaPwd controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UtenteService utenteService;
    private Utente utente;
    private JSONObject object;

    @Before
    public void setUp()
    {
        controller=Mockito.spy(new ModificaPwd());
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
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textOldPwd");
        doReturn(true).when(controller).contieneParametro(request,"textNewPwd");
        doReturn(true).when(controller).contieneParametro(request,"textRepeatPwd");
        when(request.getParameter("textIdUtente")).thenReturn("asd");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri correttamente");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void textPasswordNotEquals() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textOldPwd");
        doReturn(true).when(controller).contieneParametro(request,"textNewPwd");
        doReturn(true).when(controller).contieneParametro(request,"textRepeatPwd");
        when(request.getParameter("textIdUtente")).thenReturn("1");
        when(request.getParameter("textNewPwd")).thenReturn("aaaa");
        when(request.getParameter("textRepeatPwd")).thenReturn("bbbb");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Le password non coincidono");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void textPasswordOk() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textOldPwd");
        doReturn(true).when(controller).contieneParametro(request,"textNewPwd");
        doReturn(true).when(controller).contieneParametro(request,"textRepeatPwd");
        when(request.getParameter("textIdUtente")).thenReturn("1");
        when(request.getParameter("textNewPwd")).thenReturn("aaaa");
        when(request.getParameter("textRepeatPwd")).thenReturn("aaaa");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",1);
        object.put("Mess","Fatto");
        Mockito.verify(mockOutput).print(object.toString());
    }
}
