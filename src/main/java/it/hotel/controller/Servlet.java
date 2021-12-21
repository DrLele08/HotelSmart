package it.hotel.controller;

import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Servlet", value = "/Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        StanzaDAO stanzaDAO = new StanzaDAO();
        List<Stanza> stanze = (new StanzaDAO()).doSearch(null, null, null,
                null, null, null, null, null);
        String test = "";
        for (Stanza stanza : stanze) {
            test += stanza.toString();
        }
        response.getOutputStream().println(test);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
