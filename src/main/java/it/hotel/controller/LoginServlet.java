package it.hotel.controller;

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
import java.sql.SQLException;
/**
 * <h1>Login Servlet</h1>
 * Servlet che gestisce l'accesso di un utente
 * @author Sais Raffaele
 * @version 2.0
 * @since 2022-02-1
 */
@WebServlet(name = "Login", value = "/Login")
public class LoginServlet extends HttpServlet
{
    /**
     * Richiesta che controlla se l'utente è gia loggato altrimenti
     * visualizza la pagina di login
     * @param request Richiesta del cliente
     * @param response Visualizza la pagina richiesta
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(it.hotel.Utility.Utilita.isActive(it.hotel.Utility.Utilita.CHECK_LOGIN))
        {
            RequestDispatcher rd;
            int idUtente=-1;
            String tokenAuth="";
            HttpSession session=request.getSession();
            Utente us=(Utente)session.getAttribute(it.hotel.Utility.Utilita.SESSION_USER);
            if(us!=null)
            {
                rd=request.getRequestDispatcher("index.jsp");
                request.setAttribute(it.hotel.Utility.Utilita.SESSION_USER,us);
                rd.forward(request, response);
            }
            else
            {
                Cookie[] cookies=request.getCookies();
                for(Cookie c:cookies)
                {
                    if(c.getName().equals(it.hotel.Utility.Utilita.COOKIE_ID))
                    {
                        idUtente=Integer.parseInt(c.getValue());
                    }
                    if(c.getName().equals(it.hotel.Utility.Utilita.COOKIE_TOKEN))
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
                    request.setAttribute(it.hotel.Utility.Utilita.SESSION_USER,us);
                    session.setAttribute(it.hotel.Utility.Utilita.SESSION_USER,us);
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
    /**
     * Richiesta che effettua il login
     * @param request Richiesta del cliente
     * @param response JSON di risposta
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
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
            session.setAttribute(it.hotel.Utility.Utilita.SESSION_USER,user);
            obj.put("Ris",1);
            obj.put("Mess","Login effettuato con successo");
            response.getOutputStream().print(obj.toString());
            if(ricordami)
            {
                Cookie c1 = new Cookie(it.hotel.Utility.Utilita.COOKIE_ID,user.getIdUtente()+"");
                Cookie c2 = new Cookie(it.hotel.Utility.Utilita.COOKIE_TOKEN,user.getTokenAuth());
                response.addCookie(c1);
                response.addCookie(c2);
            }
        }
        catch (EmailNotFoundException | PasswordNotValidException e)
        {
            obj.put("Ris",0);
            obj.put("Mess","Hai immesso un nome utente o una password errata!");
            response.getOutputStream().print(obj.toString());
        }
        catch (IllegalArgumentException | SQLException e)
        {
            obj.put("Ris",0);
            obj.put("Mess","Si e' verificato un errore, riprovare più tardi o contattare l'assistenza");
            response.getOutputStream().print(obj.toString());
        }
    }
}
