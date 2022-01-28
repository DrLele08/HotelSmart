package it.hotel.controller;

import it.hotel.controller.services.StanzaService;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@WebServlet(name = "Ricerca", value = "/ricerca/*")

public class RicercaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {
            case "/":
                break;

            case "/gosearch": {

                String active_link = "ricerca";
                request.setAttribute("active", active_link);

                //Prendo 3 stanze a caso tra quelle in offerta
                ArrayList<Stanza> stanze_offerta = getStanzeOfferte();

                ArrayList<Integer> numletti = getLetti();
                ArrayList<Double> prezzi = getPrices();
                ArrayList<Integer> Letti_s = new ArrayList<>();
                ArrayList<Integer> Letti_m = new ArrayList<>();

                Integer maxLetti_s = numletti.get(0);
                Integer maxLetti_m = numletti.get(1);

                for(int i = 0; i <= maxLetti_s; i++){
                    Letti_s.add(i);
                }

                for(int i = 0; i <= maxLetti_m; i++){
                    Letti_m.add(i);
                }
                request.setAttribute("min_price",prezzi.get(0));
                request.setAttribute("max_price",prezzi.get(1));

                request.setAttribute("Letti_s",Letti_s);
                request.setAttribute("Letti_m",Letti_m);

                request.setAttribute("stanze_offerta",stanze_offerta);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/ricerca.jsp");
                dispatcher.forward(request, response);
                break;
            }


        }

    }

    public ArrayList<Double> getPrices()
    {
        StanzaService service = new StanzaService();
        return (ArrayList<Double>) service.get_Min_And_Max_Prices();
    }

    public ArrayList<Integer> getLetti()
    {
        StanzaService service = new StanzaService();
        return (ArrayList<Integer>) service.get_S_And_M_Letti();
    }

    public ArrayList<Stanza> getStanzeOfferte()
    {

        StanzaService service = new StanzaService();
        ArrayList<Stanza> stanze_offerte = (ArrayList<Stanza>) service.getOfferte();

        if(stanze_offerte.size() < 3) return stanze_offerte;

        else {

            Random rand = new Random();
            ArrayList<Stanza> stanze_temp = new ArrayList<>();

            for(int i = 0; i < 3; i++){

                stanze_temp.add(stanze_offerte.get(rand.nextInt(stanze_offerte.size())));
            }

            return stanze_temp;
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {
            case "/":
                break;

            case "/dosearch": {

                String temp = request.getParameter("prezzoMinimo");
                String temp1 = request.getParameter("prezzoMassimo");
                String temp2 = request.getParameter("letti_matrimoniali");
                String temp3 = request.getParameter("letti_singoli");
                String temp4 = request.getParameter("numero_ospiti");

                Double prezzoMinimo = Double.parseDouble(temp);
                Double prezzoMassimo = Double.parseDouble(temp1);
                Integer letti_matrimoniali = Integer.parseInt(temp2);
                Integer letti_singoli = Integer.parseInt(temp3);
                Integer numero_ospiti = Integer.parseInt(temp4);

                boolean animale = request.getParameter("animaleDom") != null;

                boolean fumatore = request.getParameter("fumatore") != null;

                java.sql.Date dataArrivoSql;
                java.sql.Date dataPartenzaSql;

                try {

                    Date dataArrivo = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dataArrivo"));
                    Date dataPartenza = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dataPartenza"));

                    dataArrivoSql =  new java.sql.Date(dataArrivo.getTime());
                    dataPartenzaSql =  new java.sql.Date(dataPartenza.getTime());

                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }

                StanzaService service = new StanzaService();
                ArrayList<Stanza> stanze = (ArrayList<Stanza>) service.search(animale,fumatore, letti_singoli,letti_matrimoniali,
                        prezzoMinimo,prezzoMassimo,null,null,dataArrivoSql,dataPartenzaSql);

                request.setAttribute("stanze_result",stanze);
                request.setAttribute("numero_ospiti",numero_ospiti);
                String active_link = "ricerca";
                request.setAttribute("active", active_link);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/searchResult.jsp");
                dispatcher.forward(request,response);
                break;
            }

            case "/goDetailForm" : {

                String temp = request.getParameter("stanzaId");
                String temp1 = request.getParameter("numero_ospiti");
                Integer stanzaId = Integer.parseInt(temp);
                Integer num_persone = Integer.parseInt(temp1);

                StanzaService service = new StanzaService();
                Stanza selected_stanza = null;

                try {
                    selected_stanza = service.selectById(stanzaId);
                    String active_link = "ricerca";
                    request.setAttribute("active", active_link);
                    request.setAttribute("selected_stanza",selected_stanza);
                    request.setAttribute("num_persone",num_persone);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/detailForm.jsp");
                    dispatcher.forward(request,response);
                } catch (StanzaNotFoundException e) {
                    e.printStackTrace();
                }

                break;
            }

        }

    }
}
