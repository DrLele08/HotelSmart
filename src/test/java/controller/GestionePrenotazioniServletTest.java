package controller;

import it.hotel.controller.GestionePrenotazioniServlet;
import it.hotel.controller.services.PrenotazioneStanzaService;
import it.hotel.model.utente.Utente;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class GestionePrenotazioniServletTest extends Mockito
{
    private GestionePrenotazioniServlet controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private Utente utente;
    private PrenotazioneStanzaService prenoService;

    @Before
    public void setUp()
    {
        controller=spy(new GestionePrenotazioniServlet());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        requestDispatcher=mock(RequestDispatcher.class);
        utente=mock(Utente.class);
        prenoService=mock(PrenotazioneStanzaService.class);
    }

    @Test
    public void testUtenteNonTrovato() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.empty()).when(controller).getUtente(request);
        controller.doGet(request,response);
    }

    @Test
    public void testUtenteNormale() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.of(utente)).when(controller).getUtente(request);
        when(utente.getRuolo()).thenReturn(1);
        when(controller.getPrenoService()).thenReturn(prenoService);
        controller.doGet(request,response);
    }

    @Test
    public void testUtenteAdmin() throws Exception
    {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(Optional.of(utente)).when(controller).getUtente(request);
        when(utente.getRuolo()).thenReturn(3);
        when(controller.getPrenoService()).thenReturn(prenoService);
        controller.doGet(request,response);
    }
}
