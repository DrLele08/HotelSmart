package it.hotel.controller.api;

import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.ServizioService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateServizio", value = "/api/UpdateServizio")
public class UpdateServizio extends CheckServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        JSONObject obj=new JSONObject();
        if(contieneParametro(request,"idUtente") && contieneParametro(request,"Token") && contieneParametro(request,"idServizio")
                    && contieneParametro(request,"Nome") && contieneParametro(request,"Descrizione")
                        && contieneParametro(request,"Prezzo") && contieneParametro(request,"Posti"))
        {
            try
            {
                int idUtente=Integer.parseInt(request.getParameter("idUtente"));
                String token=request.getParameter("Token");
                UtenteService userService=new UtenteService();
                Utente user=userService.doLogin(idUtente,token);
                if(user.getRuolo()==1)
                {
                    int idServizio=Integer.parseInt(request.getParameter("idServizio"));
                    String nome=request.getParameter("Nome");
                    String descrizione=request.getParameter("Descrizione");
                    String foto="Servizi/default.jpg";
                    double prezzo=Double.parseDouble(request.getParameter("Prezzo"));
                    int posti=Integer.parseInt(request.getParameter("Posti"));
                    ServizioService servizioService=new ServizioService();
                    //ToDo Giovanni
                    servizioService.updateServizio(idServizio,nome,descrizione,foto,prezzo,posti);
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
                obj.put("Mess","Utente inesistente");
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
