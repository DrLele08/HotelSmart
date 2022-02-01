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

@WebServlet(name = "Home", value = "/home/*")

public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {

            case "/aboutus": {

                String active_link = "aboutus";
                request.setAttribute("active",active_link);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/AboutUs.jsp");
                dispatcher.forward(request,response);
                break;

            }

            case "/contattaci": {

                String active_link = "contattaci";
                request.setAttribute("active",active_link);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/Contattaci.jsp");
                dispatcher.forward(request,response);
                break;
            }

            default: {

                response.sendRedirect(request.getContextPath());
                break;
            }
        }
    }
}