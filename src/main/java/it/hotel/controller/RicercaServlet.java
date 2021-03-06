package it.hotel.controller;

import it.hotel.controller.services.StanzaService;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "Ricerca", value = "/ricerca/*")

public class RicercaServlet extends CheckServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        if(isSearchActive())
        {
            String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
            if ("/gosearch".equals(path)) {
                StanzaService service = getStanzaService();
                ArrayList<Double> prezzi = (ArrayList<Double>) service.get_Min_And_Max_Prices();
                ArrayList<Stanza> stanze_offerta = (ArrayList<Stanza>) service.getOfferte();

                String active_link = "ricerca";

                request.setAttribute("min_price", prezzi.get(0));
                request.setAttribute("max_price", prezzi.get(1));
                request.setAttribute("active", active_link);
                request.setAttribute("stanze_offerta", stanze_offerta);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/ricerca.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath());
            }
        }
        else
        {
            response.sendRedirect("../ServiceNA.html");
        }
    }

    public boolean isSearchActive() {
        return it.hotel.Utility.Utilita.isActive(it.hotel.Utility.Utilita.CHECK_SEARCH);
    }

    public StanzaService getStanzaService() {
        return new StanzaService();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {

            case "/dosearch": {

                String temp = request.getParameter("prezzoMinimo");
                String temp1 = request.getParameter("prezzoMassimo");
                String temp2 = request.getParameter("numero_ospiti");

                double prezzoMinimo;
                double prezzoMassimo;
                int numero_ospiti;

                try {

                    prezzoMinimo = Double.parseDouble(temp);
                    prezzoMassimo = Double.parseDouble(temp1);
                    numero_ospiti = Integer.parseInt(temp2);

                }catch(NumberFormatException e){
                    return;
                }

                boolean animale = request.getParameter("animaleDom") != null;

                boolean fumatore = request.getParameter("fumatore") != null;

                String dataArrivoString = request.getParameter("dataArrivo");
                String dataPartenzaString = request.getParameter("dataPartenza");

                java.sql.Date dataArrivoSql;
                java.sql.Date dataPartenzaSql;

                try {

                    Date dataArrivo = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dataArrivo"));
                    Date dataPartenza = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dataPartenza"));

                    dataArrivoSql =  new java.sql.Date(dataArrivo.getTime());
                    dataPartenzaSql =  new java.sql.Date(dataPartenza.getTime());

                } catch (ParseException e) {
                    return;
                }

                StanzaService service = getStanzaService();
                ArrayList<Stanza> stanze = (ArrayList<Stanza>) service.search(animale,fumatore, numero_ospiti,
                        prezzoMinimo,prezzoMassimo,null,null,dataArrivoSql,dataPartenzaSql);

                request.setAttribute("stanze_result",stanze);
                request.setAttribute("numero_ospiti",numero_ospiti);
                request.setAttribute("dataArrivoString",dataArrivoString);
                request.setAttribute("dataPartenzaString",dataPartenzaString);
                String active_link = "ricerca";
                request.setAttribute("active", active_link);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/searchResult.jsp");
                dispatcher.forward(request,response);
                break;
            }

            case "/goDetailForm" : {

                String temp = request.getParameter("stanzaId");
                String temp1 = request.getParameter("numero_ospiti");
                String dataArrivoString = request.getParameter("dataArrivo");
                String dataPartenzaString = request.getParameter("dataPartenza");

                Integer stanzaId = Integer.parseInt(temp);
                Integer num_persone = Integer.parseInt(temp1);

                StanzaService service = getStanzaService();
                Stanza selected_stanza;

                try {
                    selected_stanza = service.selectById(stanzaId);
                    String active_link = "ricerca";
                    request.setAttribute("active", active_link);
                    request.setAttribute("selected_stanza",selected_stanza);
                    request.setAttribute("num_persone",num_persone);
                    request.setAttribute("dataArrivoString",dataArrivoString);
                    request.setAttribute("dataPartenzaString",dataPartenzaString);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/detailForm.jsp");
                    dispatcher.forward(request,response);
                } catch (StanzaNotFoundException e) {
                    return;
                }

                break;
            }

            default:
            {
                response.sendRedirect(request.getContextPath());
                break;
            }

        }

    }
}
