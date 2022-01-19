package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class CheckServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        super.doGet(req,resp);
    }
    public Utente getUtente(HttpServletRequest request,HttpServletResponse response)
    {
        int idUtente=-1;
        String tokenAuth="";
        HttpSession session=request.getSession();
        Utente us=(Utente)session.getAttribute(Utility.SESSION_USER);
        if(us!=null)
        {
            return us;
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
                return us;
            }
            catch (UtenteNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
