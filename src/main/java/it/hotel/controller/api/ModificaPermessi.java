package it.hotel.controller.api;

import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ModificaPermessi", value = "/api/ModificaPermessi")
public class ModificaPermessi extends CheckServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        JSONObject obj=new JSONObject();
        if(contieneParametro(request,"idUtente") && contieneParametro(request,"Token") && contieneParametro(request,"idUtenteCambio")
                        && contieneParametro(request,"idRuolo"))
        {
            try
            {
                int idUtente=Integer.parseInt(request.getParameter("idUtente"));
                String tokenAuth=request.getParameter("Token");
                int idUtenteCambio=Integer.parseInt(request.getParameter("idUtenteCambio"));
                int newRuolo=Integer.parseInt(request.getParameter("idRuolo"));
                UtenteService userService=new UtenteService();
                Utente user=userService.doLogin(idUtente,tokenAuth);
                if(user.getRuolo()==1)
                {
                    //TODO Giovanni
                    userService.editRuoloByUser(idUtenteCambio,newRuolo);
                    obj.put("Ris",1);
                    obj.put("Mess","Fatto");
                    response.getOutputStream().print(obj.toString());
                }
                else
                {
                    obj.put("Ris",0);
                    obj.put("Mess","Non hai i permessi");
                    response.getOutputStream().print(obj.toString());
                }
            }
            catch(NumberFormatException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Inserisci tutti i parametri correttamente");
                response.getOutputStream().print(obj.toString());
            }
            catch (UtenteNotFoundException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Non hai i permessi");
                response.getOutputStream().print(obj.toString());
            }
        }
        else
        {
            obj.put("Ris",0);
            obj.put("Mess","Inserisci tutti i parametri");
            response.getOutputStream().print(obj.toString());
        }
    }
}
