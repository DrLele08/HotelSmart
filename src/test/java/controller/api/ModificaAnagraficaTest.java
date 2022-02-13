package controller.api;

import it.hotel.controller.api.ModificaAnagrafica;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.EmailAlreadyExistingException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class ModificaAnagraficaTest extends Mockito
{
    private ModificaAnagrafica controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UtenteService utenteService;
    private HttpSession session;
    private Utente utente;
    private JSONObject object;
    @Before
    public void setUp()
    {
        controller=spy(new ModificaAnagrafica());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        utenteService=mock(UtenteService.class);
        session=mock(HttpSession.class);
        utente=mock(Utente.class);
        object=new JSONObject();
    }

    @Test
    public void testParametriMancanti() throws Exception
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
        doReturn(true).when(controller).contieneParametro(request,"textNome");
        doReturn(true).when(controller).contieneParametro(request,"textCognome");
        doReturn(true).when(controller).contieneParametro(request,"textCodiceFiscale");
        doReturn(true).when(controller).contieneParametro(request,"textDataNascita");
        doReturn(true).when(controller).contieneParametro(request,"textEmail");
        when(request.getParameter("textIdUtente")).thenReturn("asd");
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Id utente non valido");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testInvalidData() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textNome");
        doReturn(true).when(controller).contieneParametro(request,"textCognome");
        doReturn(true).when(controller).contieneParametro(request,"textCodiceFiscale");
        doReturn(true).when(controller).contieneParametro(request,"textDataNascita");
        doReturn(true).when(controller).contieneParametro(request,"textEmail");
        when(request.getParameter("textIdUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(request.getParameter("textDataNascita")).thenReturn("aaa");
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(utente);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Data non valida");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testEmailGiaEsistente() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textNome");
        doReturn(true).when(controller).contieneParametro(request,"textCognome");
        doReturn(true).when(controller).contieneParametro(request,"textCodiceFiscale");
        doReturn(true).when(controller).contieneParametro(request,"textDataNascita");
        doReturn(true).when(controller).contieneParametro(request,"textEmail");
        when(request.getParameter("textIdUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(request.getParameter("textDataNascita")).thenReturn("2022-01-01");
        doThrow(new EmailAlreadyExistingException()).when(utenteService).editAnagrafica(anyInt(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString());
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(utente);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","L'email e' gia inserita");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testErroreDb() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textNome");
        doReturn(true).when(controller).contieneParametro(request,"textCognome");
        doReturn(true).when(controller).contieneParametro(request,"textCodiceFiscale");
        doReturn(true).when(controller).contieneParametro(request,"textDataNascita");
        doReturn(true).when(controller).contieneParametro(request,"textEmail");
        when(request.getParameter("textIdUtente")).thenReturn("1");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(request.getParameter("textDataNascita")).thenReturn("2022-01-01");
        doThrow(new SQLException()).when(utenteService).editAnagrafica(anyInt(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString());
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(utente);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",0);
        object.put("Mess","Errore db");
        Mockito.verify(mockOutput).print(object.toString());
    }

    @Test
    public void testValido() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        doReturn(true).when(controller).contieneParametro(request,"textIdUtente");
        doReturn(true).when(controller).contieneParametro(request,"textToken");
        doReturn(true).when(controller).contieneParametro(request,"textNome");
        doReturn(true).when(controller).contieneParametro(request,"textCognome");
        doReturn(true).when(controller).contieneParametro(request,"textCodiceFiscale");
        doReturn(true).when(controller).contieneParametro(request,"textDataNascita");
        doReturn(true).when(controller).contieneParametro(request,"textEmail");
        when(request.getParameter("textIdUtente")).thenReturn("1");
        when(request.getParameter("textDataNascita")).thenReturn("2022-01-01");
        when(controller.getUtenteService()).thenReturn(utenteService);
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(utente);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doPost(request,response);
        object.put("Ris",1);
        object.put("Mess","Fatto");
        Mockito.verify(mockOutput).print(object.toString());
    }

}
