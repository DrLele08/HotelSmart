package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GestioneUtenti", value = "/GestioneUtenti")
public class GestioneUtentiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        Utente us=(Utente)session.getAttribute(Utility.SESSION_USER);
        if(us==null)
        {
            response.sendRedirect("./");
        }
        else
        {
            RequestDispatcher requestDispatcher=request.getRequestDispatcher("/WEB-INF/views/GestioneUtenti.jsp");
            requestDispatcher.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}