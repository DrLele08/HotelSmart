package it.hotel.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servizi", value = "/servizi/*")

public class ServiziServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path) {
            case "/":
                break;

            case "/goservizi": {

                String active_link = "servizi";
                request.setAttribute("active", active_link);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/servizi.jsp");
                dispatcher.forward(request, response);
                break;
            }
        }
    }
}