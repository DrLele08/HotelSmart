package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.RuoloService;
import it.hotel.controller.services.StatoService;
import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.stato.Stato;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.List;

@WebServlet(name = "Servlet", value = "/Servlet",loadOnStartup = 1)
public class StartServer extends HttpServlet
{
    public List<Ruolo> getAllRuoli()
    {
        return new RuoloService().getAll();
    }

    public List<Stato> getAllStato()
    {
        return new StatoService().getAll();
    }
    @Override
    public void init() throws ServletException
    {
        super.init();
        Utility.listRuoli=getAllRuoli();
        Utility.listStato=getAllStato();
    }
}
