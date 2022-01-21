package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.StanzaService;
import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;

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

                ArrayList<Integer> numletti = getLetti();
                ArrayList<Double> prezzi = getPrices();
                ArrayList<Integer> Letti_s = new ArrayList<>();
                ArrayList<Integer> Letti_m = new ArrayList<>();

                Integer maxLetti_s = numletti.get(0);
                Integer maxLetti_m = numletti.get(1);

                for(int i = maxLetti_s; i >= 0; i--){
                    Letti_s.add(i);
                }

                for(int i = maxLetti_m; i >= 0; i--){
                    Letti_m.add(i);
                }

                request.setAttribute("min_price",prezzi.get(0));
                request.setAttribute("max_price",prezzi.get(1));

                request.setAttribute("Letti_s",Letti_s);
                request.setAttribute("Letti_m",Letti_m);


                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/ricerca.jsp");
                dispatcher.forward(request, response);
                break;
            }

        }

    }

    public ArrayList<Double> getPrices(){
        StanzaDAO service = new StanzaDAO();
        ArrayList<Stanza> stanze = (ArrayList<Stanza>) service.getStanze();

        Double max_prezzo = stanze.get(0).getCostoNotte();
        Double min_prezzo = stanze.get(0).getCostoNotte();
        for(Stanza s: stanze){
            if(s.getCostoNotte() > max_prezzo) max_prezzo = s.getCostoNotte();
            if(s.getCostoNotte() < min_prezzo) min_prezzo = s.getCostoNotte();
        }

        ArrayList<Double> prezzi = new ArrayList<>();
        prezzi.add(min_prezzo);
        prezzi.add(max_prezzo);

        return prezzi;
    }

    public ArrayList<Integer> getLetti(){

        StanzaDAO service = new StanzaDAO();
        ArrayList<Stanza> stanze = (ArrayList<Stanza>) service.getStanze();

        Integer maxLetti_s = stanze.get(0).getLettiSingoli();
        Integer maxLetti_m = stanze.get(0).getLettiMatrimoniali();

        for(Stanza s: stanze){

            if(s.getLettiSingoli() > maxLetti_s) maxLetti_s = s.getLettiSingoli();
            if(s.getLettiMatrimoniali() > maxLetti_m) maxLetti_m = s.getLettiMatrimoniali();

        }

        ArrayList<Integer> numLetti = new ArrayList<>();

        numLetti.add(maxLetti_s);
        numLetti.add(maxLetti_m);

        return numLetti;
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

                Double prezzoMinimo = Double.parseDouble(temp);
                Double prezzoMassimo = Double.parseDouble(temp1);
                Integer letti_matrimoniali = Integer.parseInt(temp2);
                Integer letti_singoli = Integer.parseInt(temp3);

                boolean animale = false;
                if(request.getParameter("animaleDom") != null) animale = true;

                boolean fumatore = false;
                if(request.getParameter("fumatore") != null) fumatore = true;

                java.sql.Date dataArrivoSql = null;
                java.sql.Date dataPartenzaSql = null;

                try {

                    Date dataArrivo = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dataArrivo"));
                    Date dataPartenza = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dataPartenza"));

                    dataArrivoSql =  new java.sql.Date(dataArrivo.getTime());
                    dataPartenzaSql =  new java.sql.Date(dataPartenza.getTime());

                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }

                StanzaDAO service = new StanzaDAO();

                ArrayList<Stanza> stanze = (ArrayList<Stanza>) service.doSearch(animale,fumatore, letti_singoli,letti_matrimoniali,
                        prezzoMinimo,prezzoMassimo,null,null,dataArrivoSql,dataPartenzaSql);

                request.setAttribute("stanze_result",stanze);
                String active_link = "ricerca";
                request.setAttribute("active", active_link);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/searchResult.jsp");
                dispatcher.forward(request,response);
                break;
            }

            case "/goDetailForm" : {

                String temp = request.getParameter("stanzaId");
                Integer stanzaId = Integer.parseInt(temp);

                StanzaDAO service = new StanzaDAO();
                Stanza selected_stanza = null;

                try {
                    selected_stanza = service.doSelectById(stanzaId);
                    String active_link = "ricerca";
                    request.setAttribute("active", active_link);
                    request.setAttribute("stanzaid",stanzaId);
                    Integer num_persone = selected_stanza.getLettiMatrimoniali() + selected_stanza.getLettiSingoli();
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