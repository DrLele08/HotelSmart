package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import jdk.nashorn.internal.runtime.options.Option;

import javax.servlet.http.*;
import java.util.Optional;

public class CheckServlet extends HttpServlet
{
    public Optional<Utente> getUtente(HttpServletRequest request, HttpServletResponse response)
    {
        int idUtente=-1;
        String tokenAuth="";
        HttpSession session=request.getSession();
        Utente us=(Utente)session.getAttribute(Utility.SESSION_USER);
        if(us!=null)
        {
            return Optional.of(us);
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
                return Optional.of(us);
            }
            catch (UtenteNotFoundException e)
            {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
