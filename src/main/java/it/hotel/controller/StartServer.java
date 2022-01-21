package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.RuoloService;
import it.hotel.controller.services.StatoService;
import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.ruolo.RuoloDAO;
import it.hotel.model.stato.StatoDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Servlet", value = "/Servlet",loadOnStartup = 1)
public class StartServer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       Utility.listRuoli=new RuoloService().getAll();
       Utility.listStato=new StatoService().getAll();
    }
}
