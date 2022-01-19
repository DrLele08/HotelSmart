package it.hotel.controller;

import it.hotel.Utility.Utility;
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
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/ricerca.jsp");
                dispatcher.forward(request, response);
                break;
            }

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
