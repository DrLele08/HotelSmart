package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.EmailNotFoundException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Login", value = "/Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(Utility.isActive(Utility.CHECK_LOGIN))
        {
            RequestDispatcher rd;
            int idUtente=-1;
            String tokenAuth="";
            HttpSession session=request.getSession();
            Utente us=(Utente)session.getAttribute(Utility.SESSION_USER);
            if(us!=null)
            {
                rd=request.getRequestDispatcher("index.jsp");
                request.setAttribute(Utility.SESSION_USER,us);
                rd.forward(request, response);
            }
            else
            {
                Cookie[] cookies=request.getCookies();
                for(Cookie c:cookies)
                {
                    if(c.getName().equals(Utility.COOKIE_ID))
                    {
                        idUtente=Integer.parseInt(c.getValue());
                    }
                    if(c.getName().equals(Utility.COOKIE_TOKEN))
                    {
                        tokenAuth=c.getValue();
                    }
                }
            }
            if(idUtente!=-1)
            {
                try
                {
                    us=new UtenteService().doLogin(idUtente,tokenAuth);
                    rd=request.getRequestDispatcher("index.jsp");
                    request.setAttribute(Utility.SESSION_USER,us);
                    session.setAttribute(Utility.SESSION_USER,us);
                    rd.forward(request, response);
                }
                catch (EmailNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (PasswordNotValidException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                rd=request.getRequestDispatcher("Login.jsp");
                rd.forward(request, response);
            }
        }
        else
        {
            response.sendRedirect("ServiceNA.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String email=request.getParameter("textEmail");
        String pwd=request.getParameter("textPwd");
        UtenteService service=new UtenteService();
        Utente user= null;
        try
        {
            user = service.doLogin(email,pwd);
            HttpSession session=request.getSession();
            session.setAttribute(Utility.SESSION_USER,user);
            response.getOutputStream().print("OK");
            //TODO Ricordami
            boolean ricordami=true;
            if(ricordami)
            {
                Cookie c1 = new Cookie(Utility.COOKIE_ID,user.getIdUtente()+"");
                Cookie c2 = new Cookie(Utility.COOKIE_TOKEN,user.getTokenAuth());
                response.addCookie(c1);
                response.addCookie(c2);
            }
        } catch (EmailNotFoundException e)
        {
            response.getOutputStream().print("EMAIL PROBLEMA");

        }
        catch (PasswordNotValidException e)
        {
            response.getOutputStream().print("PWDPROBLEMA");

        }
        catch (IllegalArgumentException e)
        {
            response.getOutputStream().print("BO PROBLEMA"+pwd);
        }
    }
}
