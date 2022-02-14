package controller.api;

import it.hotel.controller.api.ModificaPermessi;
import it.hotel.controller.api.ModificaPwd;
import it.hotel.controller.exception.PermissionDeniedException;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

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

    @Test
    public void testPasswordNonValida() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textOldPwd");
        doReturn(true).when(controller).contieneParametro(request,"textNewPwd");
        doReturn(true).when(controller).contieneParametro(request,"textRepeatPwd");
        when(request.getParameter("textIdUtente")).thenReturn("1");
        when(request.getParameter("textToken")).thenReturn("token");
        when(request.getParameter("textOldPwd")).thenReturn("oldPassword?9");
        when(request.getParameter("textNewPwd")).thenReturn("newPassword?9");
        when(request.getParameter("textRepeatPwd")).thenReturn("newPassword?9");
        when(controller.getUtenteService()).thenReturn(utenteService);
        doThrow(new PasswordNotValidException()).when(utenteService).editPassword(1,"token","oldPassword?9", "newPassword?9");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Password non valida");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testUtenteNonTrovato() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textOldPwd");
        doReturn(true).when(controller).contieneParametro(request,"textNewPwd");
        doReturn(true).when(controller).contieneParametro(request,"textRepeatPwd");
        when(request.getParameter("textIdUtente")).thenReturn("1");
        when(request.getParameter("textToken")).thenReturn("token");
        when(request.getParameter("textOldPwd")).thenReturn("oldPassword?9");
        when(request.getParameter("textNewPwd")).thenReturn("newPassword?9");
        when(request.getParameter("textRepeatPwd")).thenReturn("newPassword?9");
        when(controller.getUtenteService()).thenReturn(utenteService);
        doThrow(new UtenteNotFoundException()).when(utenteService).editPassword(1,"token","oldPassword?9", "newPassword?9");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Utente non trovato");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testPermessiNonValidi() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textOldPwd");
        doReturn(true).when(controller).contieneParametro(request,"textNewPwd");
        doReturn(true).when(controller).contieneParametro(request,"textRepeatPwd");
        when(request.getParameter("textIdUtente")).thenReturn("1");
        when(request.getParameter("textToken")).thenReturn("token");
        when(request.getParameter("textOldPwd")).thenReturn("oldPassword?9");
        when(request.getParameter("textNewPwd")).thenReturn("newPassword?9");
        when(request.getParameter("textRepeatPwd")).thenReturn("newPassword?9");
        when(controller.getUtenteService()).thenReturn(utenteService);
        doThrow(new PermissionDeniedException()).when(utenteService).editPassword(1,"token","oldPassword?9", "newPassword?9");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Permessi non validi");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testSqlException() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textOldPwd");
        doReturn(true).when(controller).contieneParametro(request,"textNewPwd");
        doReturn(true).when(controller).contieneParametro(request,"textRepeatPwd");
        when(request.getParameter("textIdUtente")).thenReturn("1");
        when(request.getParameter("textToken")).thenReturn("token");
        when(request.getParameter("textOldPwd")).thenReturn("oldPassword?9");
        when(request.getParameter("textNewPwd")).thenReturn("newPassword?9");
        when(request.getParameter("textRepeatPwd")).thenReturn("newPassword?9");
        when(controller.getUtenteService()).thenReturn(utenteService);
        doThrow(new SQLException()).when(utenteService).editPassword(1,"token","oldPassword?9", "newPassword?9");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Errore DB");
        Mockito.verify(mockOutput).print(object.toString());
    }

}
