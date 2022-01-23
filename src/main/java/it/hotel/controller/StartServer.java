package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.RuoloService;
import it.hotel.controller.services.StatoService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Servlet", value = "/Servlet",loadOnStartup = 1)
public class StartServer extends HttpServlet
{
    @Override
    public void init() throws ServletException
    {
        super.init();
        Utility.listRuoli=new RuoloService().getAll();
        Utility.listStato=new StatoService().getAll();
    }
}
