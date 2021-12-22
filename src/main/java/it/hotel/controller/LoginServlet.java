package it.hotel.controller;

import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.EmailNotFoundException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "Login", value = "/Login")
public class LoginServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        Utente u = (Utente) session.getAttribute("utente");
        if(u == null)
            response.sendRedirect("Login.jsp");
        else
            response.sendRedirect("index.jsp");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String email=request.getParameter("textEmail");
        String pwd=request.getParameter("textPwd");
        UtenteService service=new UtenteService();
        Utente user= null;
        try
        {
            user = service.doLogin(email,pwd);
            if(user!= null)
            {
                HttpSession session = request.getSession(true);
                session.setAttribute("utente",user);
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print("true");
                out.flush();
            }
        } catch (EmailNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (PasswordNotValidException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }
}
