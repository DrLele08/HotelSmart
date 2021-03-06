package it.hotel.controller;

import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;

import javax.servlet.http.*;
import java.util.Optional;
/**
 * <h1>Gestione Controlli Servlet</h1>
 * Classe astratta per effettuare controlli
 * @author Sais Raffaele
 * @version 1.3
 * @since 2022-01-2
 */
public abstract class CheckServlet extends HttpServlet
{
    public boolean canOpen(String name)
    {
        return it.hotel.Utility.Utilita.isActive(name);
    }

    /**
     * Controlla se il parametro indicato è presente nella richiesta
     * @param request Richiesta del cliente
     * @param nome Identificatore del valore della richiesta
     * @see HttpServletRequest
     * @see String
     * @return Booleano per indicare se il valore è presente
     */
    public boolean contieneParametro(HttpServletRequest request,String nome)
    {
        return request.getParameterMap().containsKey(nome);
    }

    public UtenteService getUtenteService()
    {
        return new UtenteService();
    }
    /**
     * Controlla se l'utente è presente in sessione o in cookie e lo restituisce
     * @param request Richiesta del cliente
     * @see HttpServletRequest
     * @see Utente
     * @see Cookie
     * @see HttpSession
     * @see Optional
     * @return Utente loggato
     */
    public Optional<Utente> getUtente(HttpServletRequest request)
    {
        int idUtente=-1;
        String tokenAuth="";
        HttpSession session=request.getSession(true);
        Utente us=(Utente)session.getAttribute(it.hotel.Utility.Utilita.SESSION_USER);
        if(us!=null)
        {
            return Optional.of(us);
        }
        else
        {
            if(request.getCookies()!=null)
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
        }
        if(idUtente!=-1)
        {
            try
            {
                us=getUtenteService().doLogin(idUtente,tokenAuth);
                session.setAttribute(it.hotel.Utility.Utilita.SESSION_USER,us);
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
