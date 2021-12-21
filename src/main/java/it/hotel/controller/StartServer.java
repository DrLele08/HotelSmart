package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.ruolo.RuoloDAO;
import it.hotel.model.stato.Stato;
import it.hotel.model.stato.StatoDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Servlet", value = "/Servlet",loadOnStartup = 1)
public class StartServer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Utility.listRuoli= (ArrayList<Ruolo>) new RuoloDAO().doGetAll();
        Utility.listStato= (ArrayList<Stato>) new StatoDAO().doGetAll();
    }
}
