package it.hotel.controller;

import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Servlet", value = "/Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        StanzaDAO stanzaDAO = new StanzaDAO();
        try {
            stanzaDAO.doSelectById(1);
        } catch (StanzaNotFoundException e) {
            e.printStackTrace();
        }
        response.getOutputStream().print("OOK");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
