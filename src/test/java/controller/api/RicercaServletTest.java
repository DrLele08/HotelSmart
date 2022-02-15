package controller.api;

import it.hotel.controller.RicercaServlet;
import it.hotel.controller.services.StanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RicercaServletTest extends Mockito {

    private RicercaServlet controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UtenteService utenteService;
    private StanzaService stanzaService;
    private RequestDispatcher dispatcher;

    @Before
    public void setUp()
    {
        controller= Mockito.spy(new RicercaServlet());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        utenteService=mock(UtenteService.class);
        stanzaService=mock(StanzaService.class);
        dispatcher=mock(RequestDispatcher.class);
    }

    @Test
    public void testDoGetSearch() throws Exception {
        doReturn(true).when(controller).isSearchActive();
        doReturn(stanzaService).when(controller).getStanzaService();
        List<Double> prezzi = new ArrayList<>();
        prezzi.add(5.0);
        prezzi.add(10.0);
        List<Stanza> stanze = new ArrayList<>();
        stanze.add(new Stanza(1, true,true,1,2,10.0,1.0));
        doReturn(prezzi).when(stanzaService).get_Min_And_Max_Prices();
        doReturn(stanze).when(stanzaService).getOfferte();
        doNothing().when(request).setAttribute(anyString(), anyObject());
        doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
        doNothing().when(dispatcher).forward(anyObject(),anyObject());
        doReturn("/gosearch").when(request).getPathInfo();
        controller.doGet(request, response);
    }

    @Test
    public void testDoGetDefault() throws Exception {
        doReturn(true).when(controller).isSearchActive();
        doReturn(stanzaService).when(controller).getStanzaService();
        List<Double> prezzi = new ArrayList<>();
        prezzi.add(5.0);
        prezzi.add(10.0);
        List<Stanza> stanze = new ArrayList<>();
        stanze.add(new Stanza(1, true,true,1,2,10.0,1.0));
        doReturn(prezzi).when(stanzaService).get_Min_And_Max_Prices();
        doReturn(stanze).when(stanzaService).getOfferte();
        doNothing().when(request).setAttribute(anyString(), anyObject());
        doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
        doNothing().when(dispatcher).forward(anyObject(),anyObject());
        doReturn("/default").when(request).getPathInfo();
        controller.doGet(request, response);
    }

    @Test
    public void testDoGetSearchDisabled() throws Exception {
        doReturn(false).when(controller).isSearchActive();
        controller.doGet(request, response);
    }

    @Test
    public void testDoPostSearch() throws Exception {
        doReturn(true).when(controller).isSearchActive();
        doReturn(stanzaService).when(controller).getStanzaService();
        List<Double> prezzi = new ArrayList<>();
        prezzi.add(5.0);
        prezzi.add(10.0);
        List<Stanza> stanze = new ArrayList<>();
        stanze.add(new Stanza(1, true,true,1,2,10.0,1.0));
        doReturn(prezzi).when(stanzaService).get_Min_And_Max_Prices();
        doReturn(stanze).when(stanzaService).getOfferte();
        doNothing().when(request).setAttribute(anyString(), anyObject());
        doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
        doReturn("5.0").when(request).getParameter("prezzoMinimo");
        doReturn("10.0").when(request).getParameter("prezzoMassimo");
        doReturn("3").when(request).getParameter("numero_ospiti");
        doReturn("2000-01-01").when(request).getParameter("dataPartenza");
        doReturn("2000-01-02").when(request).getParameter("dataArrivo");
        doReturn(stanze).when(stanzaService).search(anyBoolean(),anyBoolean(),anyInt(),anyDouble(),anyDouble(),
                anyDouble(),anyDouble(),anyObject(),anyObject());
        doNothing().when(dispatcher).forward(anyObject(),anyObject());
        doReturn("/dosearch").when(request).getPathInfo();
        controller.doPost(request, response);
    }

    @Test
    public void testDoPostSearchNumberFormatException() throws Exception {
        doReturn(true).when(controller).isSearchActive();
        doReturn(stanzaService).when(controller).getStanzaService();
        List<Double> prezzi = new ArrayList<>();
        prezzi.add(5.0);
        prezzi.add(10.0);
        List<Stanza> stanze = new ArrayList<>();
        stanze.add(new Stanza(1, true,true,1,2,10.0,1.0));
        doReturn(prezzi).when(stanzaService).get_Min_And_Max_Prices();
        doReturn(stanze).when(stanzaService).getOfferte();
        doNothing().when(request).setAttribute(anyString(), anyObject());
        doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
        doReturn("5.0").when(request).getParameter("prezzoMinimo");
        doReturn("10.0").when(request).getParameter("prezzoMassimo");
        doReturn("").when(request).getParameter("numero_ospiti");
        doReturn("2000-01-01").when(request).getParameter("dataPartenza");
        doReturn("2000-01-02").when(request).getParameter("dataArrivo");
        doReturn(stanze).when(stanzaService).search(anyBoolean(),anyBoolean(),anyInt(),anyDouble(),anyDouble(),
                anyDouble(),anyDouble(),anyObject(),anyObject());
        doNothing().when(dispatcher).forward(anyObject(),anyObject());
        doReturn("/dosearch").when(request).getPathInfo();
        controller.doPost(request, response);
    }

    @Test
    public void testDoPostSearchParseException() throws Exception {
        doReturn(true).when(controller).isSearchActive();
        doReturn(stanzaService).when(controller).getStanzaService();
        List<Double> prezzi = new ArrayList<>();
        prezzi.add(5.0);
        prezzi.add(10.0);
        List<Stanza> stanze = new ArrayList<>();
        stanze.add(new Stanza(1, true,true,1,2,10.0,1.0));
        doReturn(prezzi).when(stanzaService).get_Min_And_Max_Prices();
        doReturn(stanze).when(stanzaService).getOfferte();
        doNothing().when(request).setAttribute(anyString(), anyObject());
        doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
        doReturn("5.0").when(request).getParameter("prezzoMinimo");
        doReturn("10.0").when(request).getParameter("prezzoMassimo");
        doReturn("3").when(request).getParameter("numero_ospiti");
        doReturn("").when(request).getParameter("dataPartenza");
        doReturn("2000-01-02").when(request).getParameter("dataArrivo");
        doReturn(stanze).when(stanzaService).search(anyBoolean(),anyBoolean(),anyInt(),anyDouble(),anyDouble(),
                anyDouble(),anyDouble(),anyObject(),anyObject());
        doNothing().when(dispatcher).forward(anyObject(),anyObject());
        doReturn("/dosearch").when(request).getPathInfo();
        controller.doPost(request, response);
    }

    @Test
    public void testDoPostDefault() throws Exception {
        doReturn(true).when(controller).isSearchActive();
        doReturn(stanzaService).when(controller).getStanzaService();
        List<Double> prezzi = new ArrayList<>();
        prezzi.add(5.0);
        prezzi.add(10.0);
        List<Stanza> stanze = new ArrayList<>();
        stanze.add(new Stanza(1, true,true,1,2,10.0,1.0));
        doReturn(prezzi).when(stanzaService).get_Min_And_Max_Prices();
        doReturn(stanze).when(stanzaService).getOfferte();
        doNothing().when(request).setAttribute(anyString(), anyObject());
        doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
        doNothing().when(dispatcher).forward(anyObject(),anyObject());
        doReturn("/default").when(request).getPathInfo();
        controller.doPost(request, response);
    }

    @Test
    public void testDoPostGoDetailForm() throws Exception {
        doReturn(true).when(controller).isSearchActive();
        doReturn(stanzaService).when(controller).getStanzaService();
        List<Double> prezzi = new ArrayList<>();
        prezzi.add(5.0);
        prezzi.add(10.0);
        List<Stanza> stanze = new ArrayList<>();
        stanze.add(new Stanza(1, true,true,1,2,10.0,1.0));
        doReturn(prezzi).when(stanzaService).get_Min_And_Max_Prices();
        doReturn(stanze).when(stanzaService).getOfferte();
        doNothing().when(request).setAttribute(anyString(), anyObject());
        doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
        doReturn("1").when(request).getParameter("stanzaId");
        doReturn("3").when(request).getParameter("numero_ospiti");
        doReturn("2000-01-01").when(request).getParameter("dataPartenza");
        doReturn("2000-01-02").when(request).getParameter("dataArrivo");
        doReturn(stanze).when(stanzaService).search(anyBoolean(),anyBoolean(),anyInt(),anyDouble(),anyDouble(),
                anyDouble(),anyDouble(),anyObject(),anyObject());
        doNothing().when(dispatcher).forward(anyObject(),anyObject());
        doReturn("/goDetailForm").when(request).getPathInfo();
        controller.doPost(request, response);
    }

    @Test
    public void testDoPostGoDetailFormStanzaNotFoundException() throws Exception {
        doReturn(true).when(controller).isSearchActive();
        doReturn(stanzaService).when(controller).getStanzaService();
        List<Double> prezzi = new ArrayList<>();
        prezzi.add(5.0);
        prezzi.add(10.0);
        List<Stanza> stanze = new ArrayList<>();
        stanze.add(new Stanza(1, true,true,1,2,10.0,1.0));
        doReturn(prezzi).when(stanzaService).get_Min_And_Max_Prices();
        doReturn(stanze).when(stanzaService).getOfferte();
        doNothing().when(request).setAttribute(anyString(), anyObject());
        doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
        doReturn("1").when(request).getParameter("stanzaId");
        doReturn("3").when(request).getParameter("numero_ospiti");
        doReturn("2000-01-01").when(request).getParameter("dataPartenza");
        doReturn("2000-01-02").when(request).getParameter("dataArrivo");
        doReturn(stanze).when(stanzaService).search(anyBoolean(),anyBoolean(),anyInt(),anyDouble(),anyDouble(),
                anyDouble(),anyDouble(),anyObject(),anyObject());
        doThrow(new StanzaNotFoundException()).when(stanzaService).selectById(anyInt());
        doNothing().when(dispatcher).forward(anyObject(),anyObject());
        doReturn("/goDetailForm").when(request).getPathInfo();
        controller.doPost(request, response);
    }

}
