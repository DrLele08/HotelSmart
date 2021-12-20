package it.hotel.controller;

import it.hotel.model.utente.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String email=request.getParameter("textEmail");
        String pwd=request.getParameter("textPwd");
        UtenteService service=new UtenteService();
        Utente user=service.doLogin(email,pwd);
        if(user != null)
        {
            //Non trovato
        }
        else
        {
            //Trovato
        }
    }
}
