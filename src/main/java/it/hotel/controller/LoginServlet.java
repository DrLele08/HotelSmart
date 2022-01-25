package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.EmailNotFoundException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class LoginServlet extends HttpServlet
{
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
                catch (UtenteNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                rd=request.getRequestDispatcher("/WEB-INF/views/Login.jsp");
                rd.forward(request, response);
            }
        }
        else
        {
            response.sendRedirect("./ServiceNA.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String email=request.getParameter("textEmail");
        String pwd=request.getParameter("textPwd");
        boolean ricordami = Boolean.parseBoolean(request.getParameter("textRicordami"));
        UtenteService service=new UtenteService();
        Utente user;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject obj=new JSONObject();
        try
        {
            user = service.doLogin(email,pwd);
            HttpSession session=request.getSession();
            session.setAttribute(Utility.SESSION_USER,user);
            obj.put("status",true);
            response.getOutputStream().print(obj.toString());
            if(ricordami)
            {
                Cookie c1 = new Cookie(Utility.COOKIE_ID,user.getIdUtente()+"");
                Cookie c2 = new Cookie(Utility.COOKIE_TOKEN,user.getTokenAuth());
                response.addCookie(c1);
                response.addCookie(c2);
            }
        }
        catch (EmailNotFoundException e)
        {
            obj.put("status",false);
            obj.put("data","EMAIL PROBLEMA");
            response.getOutputStream().print(obj.toString());
        }
        catch (PasswordNotValidException e)
        {
            obj.put("status",false);
            obj.put("data","PWDPROBLEMA");
            response.getOutputStream().print(obj.toString());
        }
        catch (IllegalArgumentException e)
        {
            obj.put("status",false);
            obj.put("data","BO PROBLEMA");
            response.getOutputStream().print(obj.toString());
        }
    }
}
